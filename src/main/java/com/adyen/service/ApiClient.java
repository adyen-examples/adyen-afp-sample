package com.adyen.service;

import com.adyen.Client;
import com.adyen.config.ApplicationProperty;
import com.adyen.enums.Environment;
import com.adyen.model.legalentitymanagement.OnboardingLink;
import com.adyen.service.balanceplatform.AccountHoldersApi;
import com.adyen.service.balanceplatform.BalanceAccountsApi;
import com.adyen.service.legalentitymanagement.HostedOnboardingApi;
import com.adyen.service.legalentitymanagement.LegalEntitiesApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiClient {

    @Autowired
    private ApplicationProperty applicationProperty;

    public AccountHoldersApi getAccountHoldersApi() {
        Client client = new Client(
                applicationProperty.getBclApiKey(),
                Environment.valueOf(applicationProperty.getEnvironment()));

        return new AccountHoldersApi(client);
    }

    public HostedOnboardingApi getHostedOnboardingApi() {
        Client client = new Client(
                applicationProperty.getLemApiKey(),
                Environment.valueOf(applicationProperty.getEnvironment()));

        return new HostedOnboardingApi(client);
    }

    public LegalEntitiesApi getLegalEntitiesApi() {
        Client client = new Client(
                applicationProperty.getLemApiKey(),
                Environment.valueOf(applicationProperty.getEnvironment()));

        return new LegalEntitiesApi(client);
    }

    public BalanceAccountsApi getBalanceAccountsApi() {
        Client client = new Client(
                applicationProperty.getBclApiKey(),
                Environment.valueOf(applicationProperty.getEnvironment()));

        return new BalanceAccountsApi(client);
    }

}
