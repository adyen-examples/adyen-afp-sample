package com.adyen.controller;

import com.adyen.config.ApplicationProperty;
import com.adyen.model.*;
import com.adyen.model.balanceplatform.AccountHolder;
import com.adyen.model.legalentitymanagement.OnboardingLink;
import com.adyen.service.ConfigurationAPIService;
import com.adyen.service.LegalEntityManagementAPIService;
import com.adyen.service.UserService;
import com.adyen.util.LegalEntityHandler;
import com.adyen.util.RestClient;
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
    private ConfigurationAPIService configurationAPIService;
    @Autowired
    private LegalEntityManagementAPIService legalEntityManagementAPIService;
    @Autowired
    private LegalEntityHandler legalEntityHandler;
    @Autowired
    private RestClient restClient;
    @Autowired
    private ApplicationProperty applicationProperty;
    @Autowired
    private UserService userService;


    /**
     * Get User who has logged in (user id found in Session)
     *
     * @return
     */
    @PostMapping("/getUser")
    public ResponseEntity<User> getUser() {

        if (getUserOnSession() == null) {
            log.warn("User is not logged in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(getUserOnSession());
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
        SessionRequest sessionRequest = getSessionRequest(getUserIdOnSession());
        SessionResponse response = restClient.call(getApplicationProperty().getSessionAuthenticationApiUrl(), getApplicationProperty().getBclApiKey(), sessionRequest);

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

    /**
     * Displays the Account Holder Payouts using the Adyen Payouts component
     *
     * This demonstrates how to integrate the Adyen web component that fetches and
     * displays the payouts
     *
     * @return
     */
    @PostMapping("/getPayouts")
    ResponseEntity<SessionResponse> getPayouts() {

        if (getUserIdOnSession() == null) {
            log.warn("User is not logged in");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Perform session call to obtain a valid Session token
        SessionRequest sessionRequest = getSessionRequest(getUserIdOnSession());
        SessionResponse response = restClient.call(getApplicationProperty().getSessionAuthenticationApiUrl(), getApplicationProperty().getBclApiKey(), sessionRequest);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    /**
     * Displays the Account Holder Reports using the Adyen Reports component
     *
     * This demonstrates how to integrate the Adyen web component that fetches and
     * displays the reports
     *
     * @return
     */
    @PostMapping("/getReports")
    ResponseEntity<SessionResponse> getReports() {

        if (getUserIdOnSession() == null) {
            log.warn("User is not logged in");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Perform session call to obtain a valid Session token
        SessionRequest sessionRequest = getSessionRequest(getUserIdOnSession());
        SessionResponse response = restClient.call(getApplicationProperty().getSessionAuthenticationApiUrl(), getApplicationProperty().getBclApiKey(), sessionRequest);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    /**
     * Displays the TransferInstruments using the Adyen Onboarding component
     *
     * This demonstrates how to integrate the Adyen web component that fetches and
     * displays the transfer instruments
     *
     * @return
     */
    @PostMapping("/getTransferInstruments")
    ResponseEntity<SessionResponse> getTransferInstruments() {

        if (getUserIdOnSession() == null) {
            log.warn("User is not logged in");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Perform session call to obtain a valid Session token
        SessionRequest sessionRequest = getOnboardingSessionRequest(getUserOnSession().getLegalEntityId());
        SessionResponse response = restClient.call(getApplicationProperty().getSessionAuthenticationApiUrl(), getApplicationProperty().getLemApiKey(), sessionRequest);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    // define SessionRequest object
    private SessionRequest getSessionRequest(String accountHolderId) {
        SessionRequest sessionRequest = new SessionRequest()
                .allowOrigin(getApplicationProperty().getComponentsAllowOrigin())
                .product("platform")
                .policy(new SessionRequestPolicy()
                        .resources(List.of(new PolicyResource()
                                .accountHolderId(accountHolderId)
                                .type("accountHolder")))
                        .roles(List.of(
                                "Transactions Overview Component: View",
                                "Payouts Overview Component: View",
                                "Reports Overview Component: View")));
        return sessionRequest;
    }

    // define SessionRequest object
    private SessionRequest getOnboardingSessionRequest(String legalEntityId) {
        SessionRequest sessionRequest = new SessionRequest()
                .allowOrigin(getApplicationProperty().getComponentsAllowOrigin())
                .product("onboarding")
                .policy(new SessionRequestPolicy()
                        .resources(List.of(new PolicyResource()
                                .legalEntityId(legalEntityId)
                                .type("legalEntity")))
                        .roles(List.of(
                                "createTransferInstrumentComponent",
                                "manageTransferInstrumentComponent")));
        return sessionRequest;
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

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
