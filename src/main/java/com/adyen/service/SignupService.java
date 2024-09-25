package com.adyen.service;

import com.adyen.controller.SignupController;
import com.adyen.model.*;
import com.adyen.model.balanceplatform.AccountHolder;
import com.adyen.model.balanceplatform.BalanceAccount;
import com.adyen.model.legalentitymanagement.Address;
import com.adyen.model.legalentitymanagement.BusinessLine;
import com.adyen.model.legalentitymanagement.LegalEntity;
import com.adyen.model.management.Store;
import com.adyen.util.AddressHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignupService {

    private final Logger log = LoggerFactory.getLogger(SignupService.class);

    @Autowired
    private LegalEntityManagementAPIService legalEntityManagementAPIService;

    @Autowired
    private ConfigurationAPIService configurationAPIService;

    @Autowired
    private ManagementAPIService managementAPIService;

    @Autowired
    private AddressHandler addressHandler;


    public AccountHolder signup(OrganisationSignup organisationSignup) {

        LegalEntity legalEntity = getLegalEntityManagementAPIService().create(organisationSignup);

        AccountHolder accountHolder = getConfigurationAPIService().createAccountHolder(legalEntity.getId());

        BalanceAccount balanceAccount = getConfigurationAPIService().createBalanceAccount(accountHolder.getId());

        log.info("Organisation ({}) signup successful legalEntity:{}, accountHolder:{}, balanceAccount:{}",
                organisationSignup.getLegalName(), legalEntity.getId(), balanceAccount.getAccountHolderId(), balanceAccount.getId());

        return accountHolder;
    }

    public AccountHolder signup(IndividualSignup individualSignup) {

        LegalEntity legalEntity = getLegalEntityManagementAPIService().create(individualSignup);

        AccountHolder accountHolder = getConfigurationAPIService().createAccountHolder(legalEntity.getId());

        BalanceAccount balanceAccount = getConfigurationAPIService().createBalanceAccount(accountHolder.getId());

        log.info("Individual ({} {}) signup successful legalEntity:{}, accountHolder:{}, balanceAccount:{}",
                individualSignup.getFirstName(), individualSignup.getLastName(), legalEntity.getId(), balanceAccount.getAccountHolderId(), balanceAccount.getId());

        return accountHolder;
    }

    public AccountHolder signup(SoleProprietorshipSignup soleProprietorshipSignup) {

        LegalEntity legalEntity = getLegalEntityManagementAPIService().create(soleProprietorshipSignup);

        AccountHolder accountHolder = getConfigurationAPIService().createAccountHolder(legalEntity.getId());

        BalanceAccount balanceAccount = getConfigurationAPIService().createBalanceAccount(accountHolder.getId());

        log.info("SoleProprietorshipSignup ({} {}) signup successful legalEntity:{}, accountHolder:{}, balanceAccount:{}",
                soleProprietorshipSignup.getFirstName(), soleProprietorshipSignup.getLastName(), legalEntity.getId(), balanceAccount.getAccountHolderId(), balanceAccount.getId());

        return accountHolder;
    }

    /**
     * Complete the signup after AccountHolder onboarding is completed
     * @param accountHolderId
     */
    public void completeSignup(String accountHolderId) {

        Optional<AccountHolder> optionalAccountHolder = getConfigurationAPIService().getAccountHolder(accountHolderId);

        if(!optionalAccountHolder.isPresent()) {
            // not found
            throw new RuntimeException("Cannot complete signup - AccountHolder with id " + accountHolderId + " not found");
        }

        AccountHolder accountHolder = optionalAccountHolder.get();

        if(getConfigurationAPIService().getAccountHolderStatus(accountHolder).equals(AccountHolderStatus.VALID)) {
            // Onboarding is completed: complete signup
            LegalEntity legalEntity = getLegalEntityManagementAPIService().get(accountHolder.getLegalEntityId());

            // create Business Line
            BusinessLine businessLine = getLegalEntityManagementAPIService().createBusinessLine(legalEntity.getId());

            Address address = null;

            if (legalEntity.getType().equals(LegalEntity.TypeEnum.ORGANIZATION)) {
                address = legalEntity.getOrganization().getPrincipalPlaceOfBusiness();
            } else if (legalEntity.getType().equals(LegalEntity.TypeEnum.SOLEPROPRIETORSHIP)) {
                address = legalEntity.getSoleProprietorship().getPrincipalPlaceOfBusiness();
            } else if (legalEntity.getType().equals(LegalEntity.TypeEnum.INDIVIDUAL)) {
                address = legalEntity.getIndividual().getResidentialAddress();
            } else {
                // unexpected type
                log.warn("Unexpected LegalEntity type {}", legalEntity);
            }

            StoreConfigurationAddress storeConfigurationAddress =
                    getAddressHandler().getStoreConfigurationAddress(address);

            // create Store
            Store store = getManagementAPIService().createStore(new StoreConfiguration()
                    .businessLineId(businessLine.getId())
                    .balanceAccountId(accountHolder.getPrimaryBalanceAccount())
                    .countryCode(storeConfigurationAddress.getCountry())
                    .storeName("Store " + storeConfigurationAddress.getCity())
                    .address(storeConfigurationAddress));

            log.info("Signup completed legalEntity:{}, businessLine:{}, store:{}",
                    legalEntity.getId(), businessLine.getId(), store.getId());

        }
    }

    public LegalEntityManagementAPIService getLegalEntityManagementAPIService() {
        return legalEntityManagementAPIService;
    }

    public void setLegalEntityManagementAPIService(LegalEntityManagementAPIService legalEntityManagementAPIService) {
        this.legalEntityManagementAPIService = legalEntityManagementAPIService;
    }

    public ConfigurationAPIService getConfigurationAPIService() {
        return configurationAPIService;
    }

    public void setConfigurationAPIService(ConfigurationAPIService configurationAPIService) {
        this.configurationAPIService = configurationAPIService;
    }

    public ManagementAPIService getManagementAPIService() {
        return managementAPIService;
    }

    public void setManagementAPIService(ManagementAPIService managementAPIService) {
        this.managementAPIService = managementAPIService;
    }

    public AddressHandler getAddressHandler() {
        return addressHandler;
    }

    public void setAddressHandler(AddressHandler addressHandler) {
        this.addressHandler = addressHandler;
    }
}
