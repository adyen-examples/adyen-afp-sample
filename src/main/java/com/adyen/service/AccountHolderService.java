package com.adyen.service;

import com.adyen.model.AccountHolderStatus;
import com.adyen.model.balanceplatform.AccountHolder;
import com.adyen.model.balanceplatform.AccountHolderCapability;
import com.adyen.model.balanceplatform.AccountHolderInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Service of the AccountHolder entity
 */
@Service
public class AccountHolderService {

    private final Logger log = LoggerFactory.getLogger(AccountHolderService.class);

    @Autowired
    private ApiClient apiClient;

    public Optional<AccountHolder> getAccountHolder(String accountHolderId) {

        Optional<AccountHolder> accountHolder = Optional.empty();

        try {
            accountHolder = Optional.of(getApiClient().getAccountHoldersApi().getAccountHolder(accountHolderId));
            log.info(accountHolder.toString());
        } catch (Exception e) {
            log.error(e.toString(), e);
            //log.error(e.getMessage(), e);
        }

        return accountHolder;
    }

    /**
     * Get status of the Account Holder:
     * valid: when all capabilities have verification status VALID
     * invalid: when there is at least one capability with verification status INVALID
     * pending: when there are no INVALID capability and there is at least one capability with verification status PENDING
     *
     * @param accountHolder
     * @return
     */
    public AccountHolderStatus getStatus(AccountHolder accountHolder) {
        AccountHolderStatus status;

        boolean pending = false;
        boolean invalid = false;

        for (Map.Entry<String, AccountHolderCapability> entry : accountHolder.getCapabilities().entrySet()) {
            if (entry.getValue().getVerificationStatus().equals(AccountHolderCapability.VerificationStatusEnum.PENDING)) {
                pending = true;
            } else if (entry.getValue().getVerificationStatus().equals(AccountHolderCapability.VerificationStatusEnum.INVALID)) {
                invalid = true;
            }
        }

        if (invalid) {
            status = AccountHolderStatus.INVALID;
        } else if (pending) {
            status = AccountHolderStatus.PENDING;
        } else {
            status = AccountHolderStatus.VALID;
        }

        return status;
    }

    public AccountHolder create(String legalEntityId) {
        AccountHolder accountHolder = null;

        try {
            AccountHolderInfo accountHolderInfo = new AccountHolderInfo()
                    .legalEntityId(legalEntityId)
                    .description("Liable account holder")
                    .reference("SAMPLE-APP-" + UUID.randomUUID());
            accountHolder = getApiClient().getAccountHoldersApi().createAccountHolder(accountHolderInfo);

        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new RuntimeException("Cannot create AccountHolder: " + e.getMessage());
        }

        return accountHolder;
    }

    protected ApiClient getApiClient() {
        return apiClient;
    }

    protected void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }
}
