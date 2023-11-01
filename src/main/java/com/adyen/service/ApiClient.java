package com.adyen.service;

import com.adyen.Client;
import com.adyen.config.ApplicationProperty;
import com.adyen.enums.Environment;
import com.adyen.service.balanceplatform.AccountHoldersApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiClient {

    @Autowired
    private ApplicationProperty applicationProperty;

    public AccountHoldersApi getAccountHoldersApi() {
        Client client = new Client(
                applicationProperty.getLemApiKey(),
                Environment.valueOf(applicationProperty.getEnvironment()));

        return new AccountHoldersApi(client);

    }
}
