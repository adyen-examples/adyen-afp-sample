package com.adyen.service;

import com.adyen.model.balanceplatform.AccountHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    protected ApiClient getApiClient() {
        return apiClient;
    }

    protected void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }
}
