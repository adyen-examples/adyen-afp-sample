package com.adyen.controller;

import com.adyen.config.ApplicationProperty;
import com.adyen.exception.InvalidWebhookTypeException;
import com.adyen.model.configurationwebhooks.AccountHolder;
import com.adyen.model.configurationwebhooks.AccountHolderNotificationRequest;
import com.adyen.model.configurationwebhooks.BalanceAccountNotificationRequest;
import com.adyen.model.configurationwebhooks.PaymentNotificationRequest;
import com.adyen.notification.BankingWebhookHandler;
import com.adyen.service.ConfigurationAPIService;
import com.adyen.service.SignupService;
import com.adyen.util.AfpEventHandler;
import com.adyen.util.HMACValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * REST controller receiving the Adyen webhook events
 */
@RestController
@RequestMapping("/api")
public class WebhookController {
    private final Logger log = LoggerFactory.getLogger(WebhookController.class);

    @Autowired
    private SignupService signupService;

    @Autowired
    private ApplicationProperty applicationProperty;
    private HMACValidator hmacValidator = new HMACValidator();

    /**
     * Process incoming
     *
     * @param json Payload of the webhook
     * @return
     */
    @PostMapping("/webhooks/notifications")
    public ResponseEntity<String> webhooks(@RequestHeader Map<String, String> headers, @RequestBody String json) throws Exception {
        log.info("/webhooks/notifications");

        log.info("payload " + json);

        // find and validate HMAC signature
        String hmacsignature = headers.get("hmacsignature");
        log.info("hmacsignature " + hmacsignature);

        if (hmacsignature == null || hmacsignature.isBlank()) {
            log.warn("HMAC Signature not found");
            throw new RuntimeException("HMAC Signature not found");
        }

        // disabled for convenience: HMAC validation should be enabled on your integration
//        if (!hmacValidator.validateHMAC(json, hmacsignature, applicationProperty.getHmacKey())) {
//            log.warn("Invalid HMAC signature");
//            throw new RuntimeException("Invalid HMAC signature");
//        }

        // Deserialise json payload
        AfpEventHandler afpEventHandler = new AfpEventHandler();
        BankingWebhookHandler webhookHandler = new BankingWebhookHandler(json);

        String type = afpEventHandler.getEventType(json);
        String environment = afpEventHandler.getEventEnvironment(json);

        log.info("Event " + type + " on " + environment);

        switch (type) {
            case "balancePlatform.accountHolder.created":
                webhookHandler.getAccountHolderNotificationRequest().ifPresent((AccountHolderNotificationRequest event) -> {
                    // new AccountHolder created
                });
                break;
            case "balancePlatform.accountHolder.updated":
                webhookHandler.getAccountHolderNotificationRequest().ifPresent((AccountHolderNotificationRequest event) -> {
                    //  AccountHolder updated: complete signup
                    getSignupService().completeSignup(event.getData().getAccountHolder().getId());
                });
                break;
            case "balancePlatform.balanceAccount.created":
                webhookHandler.getBalanceAccountNotificationRequest().ifPresent((BalanceAccountNotificationRequest event) -> {
                    // new BalanceAccount created
                });
                break;
            case "balancePlatform.balanceAccount.updated":
                webhookHandler.getBalanceAccountNotificationRequest().ifPresent((BalanceAccountNotificationRequest event) -> {
                    // BalanceAccount updated
                });
                break;
            case "balancePlatform.paymentInstrument.created":
                webhookHandler.getPaymentNotificationRequest().ifPresent((PaymentNotificationRequest event) -> {
                    // new PaymentInstrument created
                });
                break;
            case "balancePlatform.paymentInstrument.updated":
                webhookHandler.getPaymentNotificationRequest().ifPresent((PaymentNotificationRequest event) -> {
                    // PaymentInstrument updated
                });
                break;

//          implement other events...
//
//
            default:
                // deal with unexpected event (ie there is a new event that must be processed?)
                log.error("Unexpected event type: " + type);
                throw new InvalidWebhookTypeException("Unexpected event type: " + type);
        }

        // Acknowledge event has been consumed
        return ResponseEntity.status(202).body("");

    }

    public ApplicationProperty getApplicationProperty() {
        return applicationProperty;
    }

    public void setApplicationProperty(ApplicationProperty applicationProperty) {
        this.applicationProperty = applicationProperty;
    }

    public SignupService getSignupService() {
        return signupService;
    }

    public void setSignupService(SignupService signupService) {
        this.signupService = signupService;
    }

}
