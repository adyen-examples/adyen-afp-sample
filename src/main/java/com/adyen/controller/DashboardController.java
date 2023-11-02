package com.adyen.controller;

import com.adyen.model.balanceplatform.AccountHolder;
import com.adyen.service.AccountHolderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private AccountHolderService accountHolderService;

    /**
     * Get AccountHolder by id
     * @return
     */
    @PostMapping("/getAccountHolder")
    ResponseEntity<AccountHolder> getAccountHolder()  {

        if(getUserIdOnSession() == null) {
            log.warn("User is not logged in");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<AccountHolder> accountHolder = getAccountHolderService().getAccountHolder(getUserIdOnSession());

        return accountHolder.map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> {
                        log.warn("AccountHolder not found id: " + getUserIdOnSession());
                        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                        }
                );
    }


    public AccountHolderService getAccountHolderService() {
        return accountHolderService;
    }

    public void setAccountHolderService(AccountHolderService accountHolderService) {
        this.accountHolderService = accountHolderService;
    }
}
