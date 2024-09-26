package com.adyen.service;

import com.adyen.Client;
import com.adyen.config.ApplicationProperty;
import com.adyen.enums.Environment;
import com.adyen.model.AccountHolderStatus;
import com.adyen.model.TransactionItem;
import com.adyen.model.balanceplatform.*;
import com.adyen.model.transfers.TransactionSearchResponse;
import com.adyen.service.balanceplatform.AccountHoldersApi;
import com.adyen.service.balanceplatform.BalanceAccountsApi;
import com.adyen.service.transfers.TransactionsApi;
import com.adyen.util.TransactionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Wraps the Adyen Configuration API: accountHolders, balanceAccounts, etc..
 * It requires the BalancePlatform API key
 *
 * https://docs.adyen.com/api-explorer/balanceplatform
 */
@Service
public class ConfigurationAPIService {

    private final Logger log = LoggerFactory.getLogger(ConfigurationAPIService.class);

    private Client apiClient = null;

    @Autowired
    private ApplicationProperty applicationProperty;

    @Autowired
    private TransactionHandler transactionHandler;

    public Optional<AccountHolder> getAccountHolder(String accountHolderId) {

        Optional<AccountHolder> accountHolder = Optional.empty();

        try {
            accountHolder = Optional.of(getAccountHoldersApi().getAccountHolder(accountHolderId));
            log.info(accountHolder.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
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
    public AccountHolderStatus getAccountHolderStatus(AccountHolder accountHolder) {
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

    /**
     * Create AccountHolder
     * @param legalEntityId
     * @return
     */
    public AccountHolder createAccountHolder(String legalEntityId) {
        AccountHolder accountHolder = null;

        try {
            AccountHolderInfo accountHolderInfo = new AccountHolderInfo()
                    .legalEntityId(legalEntityId)
                    .description("Liable account holder")
                    .reference("SAMPLE-APP-" + UUID.randomUUID());
            accountHolder = getAccountHoldersApi().createAccountHolder(accountHolderInfo);

        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new RuntimeException("Cannot create AccountHolder: " + e.getMessage());
        }

        return accountHolder;
    }

    /**
     * Create BalanceAccount
     * @param accountHolderId
     * @return
     */
    public BalanceAccount createBalanceAccount(String accountHolderId) {

        BalanceAccount balanceAccount = null;

        try {
            BalanceAccountInfo bankAccountInfo = new BalanceAccountInfo();
            bankAccountInfo
                    .accountHolderId(accountHolderId)
                    .description("Main balance account");

            balanceAccount = getBalanceAccountsApi().createBalanceAccount(bankAccountInfo);

        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new RuntimeException("Cannot create BalanceAccount: " + e.getMessage());
        }

        return balanceAccount;
    }

    /**
     * Get all transactions for the user (accountHolder)
     * @param accountHolderId
     * @return
     */
    public List<TransactionItem> getTransactions(String accountHolderId) {

        List<TransactionItem> transactionItems = null;

        try {

            // in the last X days
            OffsetDateTime createdSince = OffsetDateTime.now().minus(365, ChronoUnit.DAYS);
            // until today
            OffsetDateTime createdUntil = OffsetDateTime.now();
            // max number of transactions to fetch
            Integer limit = 100;

            TransactionSearchResponse transactionSearchResponse = getTransactionsApi().getAllTransactions(
                    null, null, accountHolderId, null,
                null, createdSince, createdUntil, limit, null);

            transactionItems = getTransactionHandler().getTransactionItems(transactionSearchResponse.getData());
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new RuntimeException("Cannot get transactions: " + e.getMessage());
        }

        return transactionItems;

    }

    // AccountHoldersApi handler
    private AccountHoldersApi getAccountHoldersApi() {
        return new AccountHoldersApi(getApiClient());
    }

    // BalanceAccountsApi handler
    private BalanceAccountsApi getBalanceAccountsApi() {
        return new BalanceAccountsApi(getApiClient());
    }

    private TransactionsApi getTransactionsApi() {
        return new TransactionsApi(getApiClient());
    }

    // create client to access the Configuration API
    private Client getApiClient() {
        if (apiClient == null) {
            // create once
            apiClient = new Client(
                    applicationProperty.getBclApiKey(),
                    Environment.TEST); // change to LIVE on prod
        }

        return apiClient;
    }

    public ApplicationProperty getApplicationProperty() {
        return applicationProperty;
    }

    public void setApplicationProperty(ApplicationProperty applicationProperty) {
        this.applicationProperty = applicationProperty;
    }

    public TransactionHandler getTransactionHandler() {
        return transactionHandler;
    }

    public void setTransactionHandler(TransactionHandler transactionHandler) {
        this.transactionHandler = transactionHandler;
    }
}
