package com.adyen.controller;

import com.adyen.config.ApplicationProperty;
import com.adyen.model.*;
import com.adyen.model.balanceplatform.AccountHolder;
import com.adyen.model.legalentitymanagement.LegalEntity;
import com.adyen.model.legalentitymanagement.OnboardingLink;
import com.adyen.model.sessionauthentication.AuthenticationSessionResponse;
import com.adyen.service.ConfigurationAPIService;
import com.adyen.service.LegalEntityManagementAPIService;
import com.adyen.service.SessionAuthenticationAPIService;
import com.adyen.util.LegalEntityHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private SessionAuthenticationAPIService sessionAuthenticationAPIService;
    @Autowired
    private ConfigurationAPIService configurationAPIService;
    @Autowired
    private LegalEntityManagementAPIService legalEntityManagementAPIService;
    @Autowired
    private LegalEntityHandler legalEntityHandler;
    @Autowired
    private ApplicationProperty applicationProperty;


    /**
     * Get User who has logged in (user id found in Session)
     *
     * @return
     */
    @PostMapping("/getUser")
    ResponseEntity<User> getUser() {

        if (getUserIdOnSession() == null) {
            log.warn("User is not logged in");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // retrieve Account Holder
        Optional<AccountHolder> accountHolder = getConfigurationAPIService().getAccountHolder(getUserIdOnSession());

        return accountHolder.map(response -> {
                    // get Legal Entity
                    LegalEntity legalEntity = getLegalEntityManagementAPIService().get(accountHolder.get().getLegalEntityId());

                    User user = getLegalEntityHandler().getUserFromLegalEntity(legalEntity);
                    user.setStatus(getConfigurationAPIService().getAccountHolderStatus(accountHolder.get()));
                    return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
                })
                .orElseGet(() -> {
                            log.warn("User not found id: " + getUserIdOnSession());
                            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                        }
                );
    }

    @PostMapping("/getOnboardingLink")
    ResponseEntity<String> getOnboardingLink(@RequestBody OnboardingLinkProperties onboardingLinkProperties) {

        String legalEntityId;

        if (getUserIdOnSession() == null) {
            log.warn("User is not logged in");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // get host (used to generate the returnUrl)
        String host = onboardingLinkProperties.getHost();

        Optional<AccountHolder> accountHolder = getConfigurationAPIService().getAccountHolder(getUserIdOnSession());

        if(accountHolder.isPresent()) {
            legalEntityId = accountHolder.get().getLegalEntityId();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<OnboardingLink> onboardingLink = getLegalEntityManagementAPIService().getOnboardingLink(legalEntityId, host);

        return onboardingLink.map(response -> new ResponseEntity<>(response.getUrl(), HttpStatus.ACCEPTED))
                .orElseGet(() -> {
                            log.warn("OnboardingLink not found legalEntityId: " + legalEntityId);
                            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                        }
                );
    }

    /**
     * Displays the AccountHolder transactions in a custom view
     *
     * This demonstrates how to fetch the AccountHolder transactions via API and
     * display them in a custom-built view
     *
     * @return
     */
    @PostMapping("/getTransactionsCustomView")
    ResponseEntity<List<TransactionItem>> getTransactionsCustomView() {

        if (getUserIdOnSession() == null) {
            log.warn("User is not logged in");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(
                getConfigurationAPIService().getTransactions(getUserIdOnSession()), HttpStatus.ACCEPTED);
    }

    /**
     * Returns a valid session token that can be used by the Adyen web components
     *
     * @return
     */
    @PostMapping("/getSessionToken")
    ResponseEntity<AuthenticationSessionResponse> getSessionToken() {

        AuthenticationSessionResponse token;

        if (getUserIdOnSession() == null) {
            log.warn("User is not logged in");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {

            // Obtain a valid Session token
            Optional<AuthenticationSessionResponse> authenticationSessionResponse = getSessionAuthenticationAPIService()
                    .getSession("getUserIdOnSession()", getApplicationProperty().getComponentsAllowOrigin());

            if (authenticationSessionResponse.isPresent()) {
                token = authenticationSessionResponse.get();
            } else {
                log.error("Session token is not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return new ResponseEntity<>(new AuthenticationSessionResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public SessionAuthenticationAPIService getSessionAuthenticationAPIService() {
        return sessionAuthenticationAPIService;
    }

    public void setSessionAuthenticationAPIService(SessionAuthenticationAPIService sessionAuthenticationAPIService) {
        this.sessionAuthenticationAPIService = sessionAuthenticationAPIService;
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

    public ApplicationProperty getApplicationProperty() {
        return applicationProperty;
    }

    public void setApplicationProperty(ApplicationProperty applicationProperty) {
        this.applicationProperty = applicationProperty;
    }
}
