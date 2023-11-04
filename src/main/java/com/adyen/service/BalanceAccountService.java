package com.adyen.service;

import com.adyen.model.IndividualSignup;
import com.adyen.model.OrganisationSignup;
import com.adyen.model.balanceplatform.BalanceAccount;
import com.adyen.model.balanceplatform.BalanceAccountInfo;
import com.adyen.model.legalentitymanagement.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to create and manage Balance Accounts
 */
@Service
public class BalanceAccountService {

    private final Logger log = LoggerFactory.getLogger(BalanceAccountService.class);

    @Autowired
    private ApiClient apiClient;

    public BalanceAccount create(String accountHolderId) {

        BalanceAccount balanceAccount = null;

        try {
            BalanceAccountInfo bankAccountInfo = new BalanceAccountInfo();
            bankAccountInfo
                    .accountHolderId(accountHolderId)
                    .description("Main balance account");

            balanceAccount = getApiClient().getBalanceAccountsApi().createBalanceAccount(bankAccountInfo);

        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new RuntimeException("Cannot create BalanceAccount: " + e.getMessage());
        }

        return balanceAccount;
    }

    protected ApiClient getApiClient() {
        return apiClient;
    }

    protected void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }
}
