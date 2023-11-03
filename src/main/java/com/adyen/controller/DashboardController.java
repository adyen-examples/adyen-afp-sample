package com.adyen.controller;

import com.adyen.model.User;
import com.adyen.model.balanceplatform.AccountHolder;
import com.adyen.model.legalentitymanagement.OnboardingLink;
import com.adyen.service.AccountHolderService;
import com.adyen.service.OnboardingLinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private AccountHolderService accountHolderService;

    @Autowired
    private OnboardingLinkService onboardingLinkService;

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

        Optional<AccountHolder> accountHolder = getAccountHolderService().getAccountHolder(getUserIdOnSession());

        return accountHolder.map(response -> {
                    User user = new User();
                    user.setStatus(getAccountHolderService().getStatus(accountHolder.get()));
                    return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
                })
                .orElseGet(() -> {
                            log.warn("User not found id: " + getUserIdOnSession());
                            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                        }
                );
    }

    @PostMapping("/getOnboardingLink")
    ResponseEntity<String> getOnboardingLink() {

        String legalEntityId;

        if (getUserIdOnSession() == null) {
            log.warn("User is not logged in");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<AccountHolder> accountHolder = getAccountHolderService().getAccountHolder(getUserIdOnSession());

        if(accountHolder.isPresent()) {
            legalEntityId = accountHolder.get().getLegalEntityId();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<OnboardingLink> onboardingLink = getOnboardingLinkService().getOnboardingLink(legalEntityId);

        return onboardingLink.map(response -> new ResponseEntity<>(response.getUrl(), HttpStatus.ACCEPTED))
                .orElseGet(() -> {
                            log.warn("OnboardingLink not found legalEntityId: " + legalEntityId);
                            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                        }
                );
    }

    public AccountHolderService getAccountHolderService() {
        return accountHolderService;
    }

    public void setAccountHolderService(AccountHolderService accountHolderService) {
        this.accountHolderService = accountHolderService;
    }

    public OnboardingLinkService getOnboardingLinkService() {
        return onboardingLinkService;
    }

    public void setOnboardingLinkService(OnboardingLinkService onboardingLinkService) {
        this.onboardingLinkService = onboardingLinkService;
    }
}
