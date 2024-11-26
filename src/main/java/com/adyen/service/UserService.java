package com.adyen.service;

import com.adyen.model.User;
import com.adyen.model.balanceplatform.AccountHolder;
import com.adyen.model.legalentitymanagement.LegalEntity;
import com.adyen.util.LegalEntityHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private ConfigurationAPIService configurationAPIService;
    @Autowired
    private LegalEntityManagementAPIService legalEntityManagementAPIService;
    @Autowired
    private LegalEntityHandler legalEntityHandler;

    public Optional<User> getUserByAccountHolderId(String accountHolderId) {
        Optional<User> userHolder = Optional.empty();

        // retrieve Account Holder
        Optional<AccountHolder> accountHolder = getConfigurationAPIService().getAccountHolder(accountHolderId);

        if(accountHolder.isEmpty()) {
            log.warn("User not found - accountHolderId: " + accountHolderId);
            return userHolder;
        }

        LegalEntity legalEntity = getLegalEntityManagementAPIService().get(accountHolder.get().getLegalEntityId());

        User user = getLegalEntityHandler().getUserFromLegalEntity(legalEntity);
        user.setStatus(getConfigurationAPIService().getAccountHolderStatus(accountHolder.get()));
        user.setAccountHolderId(accountHolderId);
        user.setLegalEntityId(accountHolder.get().getLegalEntityId());

        return Optional.of(user);
    }

    public ConfigurationAPIService getConfigurationAPIService() {
        return configurationAPIService;
    }

    public void setConfigurationAPIService(ConfigurationAPIService configurationAPIService) {
        this.configurationAPIService = configurationAPIService;
    }

    public LegalEntityManagementAPIService getLegalEntityManagementAPIService() {
        return legalEntityManagementAPIService;
    }

    public void setLegalEntityManagementAPIService(LegalEntityManagementAPIService legalEntityManagementAPIService) {
        this.legalEntityManagementAPIService = legalEntityManagementAPIService;
    }

    public LegalEntityHandler getLegalEntityHandler() {
        return legalEntityHandler;
    }

    public void setLegalEntityHandler(LegalEntityHandler legalEntityHandler) {
        this.legalEntityHandler = legalEntityHandler;
    }
}
