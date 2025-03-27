package com.adyen.service;

import com.adyen.Client;
import com.adyen.config.ApplicationProperty;
import com.adyen.enums.Environment;
import com.adyen.model.sessionauthentication.*;
import com.adyen.service.exception.ApiException;
import com.adyen.service.sessionauthentication.SessionAuthenticationApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Wraps the Adyen Session Authentication API: create session token for AfP components
 * It requires the BalancePlatform API key
 *
 * https://docs.adyen.com/api-explorer/sessionauthentication/1/overview
 */
@Service
public class SessionAuthenticationAPIService {

    private final Logger log = LoggerFactory.getLogger(SessionAuthenticationAPIService.class);

    private Client apiClient = null;

    @Autowired
    private ApplicationProperty applicationProperty;

    /**
     * Create the session token required to integrate the AfP components
     * @param accountHolderId
     * @param allowOriginUrl
     * @return
     */
    public Optional<AuthenticationSessionResponse> getSession(String accountHolderId, String allowOriginUrl) {

        Optional<AuthenticationSessionResponse> session = Optional.empty();

        // Roles available within the session
        Set<String> roles = Set.of(
                "Transactions Overview Component: View",
                "Payouts Overview Component: View",
                "Reports Overview Component: View");

        try {
            AuthenticationSessionRequest request = new AuthenticationSessionRequest()
                    .allowOrigin(allowOriginUrl)
                    .product(ProductType.PLATFORM)
                    .policy(new Policy()
                            .addResourcesItem(new AccountHolderResource()
                                    .accountHolderId(accountHolderId)
                                    .type(ResourceType.ACCOUNTHOLDER)
                            )
                            .roles(roles));

            session = Optional.of(getSessionAuthenticationApi().createAuthenticationSession(request));

            log.info(session.toString());
        } catch (ApiException e) {
            // Adyen API has returned an error
            log.error(e.getError().toString());
        } catch (IOException e) {
            // An unexpected error has occurred
            log.error(e.getMessage(), e);
        }

        return session;
    }

    private SessionAuthenticationApi getSessionAuthenticationApi() {
        return new SessionAuthenticationApi(getApiClient());
    }

    // create client to access the Session Authentication API
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

}
