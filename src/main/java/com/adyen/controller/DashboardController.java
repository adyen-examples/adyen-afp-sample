package com.adyen.controller;

import com.adyen.config.ApplicationProperty;
import com.adyen.model.*;
import com.adyen.model.balanceplatform.AccountHolder;
import com.adyen.model.legalentitymanagement.LegalEntity;
import com.adyen.model.legalentitymanagement.OnboardingLink;
import com.adyen.service.ConfigurationAPIService;
import com.adyen.service.LegalEntityManagementAPIService;
import com.adyen.util.LegalEntityHandler;
import com.adyen.util.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private ConfigurationAPIService configurationAPIService;
    @Autowired
    private LegalEntityManagementAPIService legalEntityManagementAPIService;
    @Autowired
    private LegalEntityHandler legalEntityHandler;
    @Autowired
    private RestClient restClient;
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
     * Displays the AccountHolder transactions using the Adyen Transactions component
     *
     * This demonstrates how to integrate the Adyen web component that fetches and
     * displays the transactions
     *
     * @return
     */
    @PostMapping("/getTransactions")
    ResponseEntity<SessionResponse> getTransactions() {

        if (getUserIdOnSession() == null) {
            log.warn("User is not logged in");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Perform session call to obtain a valid Session token
        SessionRequest sessionRequest = new SessionRequest()
                .allowOrigin(getApplicationProperty().getComponentsAllowOrigin())
                .product("platform")
                .policy(new SessionRequestPolicy()
                        .resources(List.of(new PolicyResource()
                                .accountHolderId(getUserIdOnSession())
                                .type("accountHolder")))
                        .roles(List.of("Transactions Overview Component: View")));

        SessionResponse response = restClient.call(getApplicationProperty().getSessionAuthenticationApiUrl(), sessionRequest);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
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

    public RestClient getRestClient() {
        return restClient;
    }

    public void setRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public ApplicationProperty getApplicationProperty() {
        return applicationProperty;
    }

    public void setApplicationProperty(ApplicationProperty applicationProperty) {
        this.applicationProperty = applicationProperty;
    }
}
