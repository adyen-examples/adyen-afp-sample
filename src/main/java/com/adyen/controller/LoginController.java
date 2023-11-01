package com.adyen.controller;

import com.adyen.model.UserLogin;
import com.adyen.model.balanceplatform.AccountHolder;
import com.adyen.service.AccountHolderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AccountHolderService accountHolderService;

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody UserLogin userLogin)  {

        Optional<AccountHolder> accountHolder = getAccountHolderService().getAccountHolder(userLogin.getUsername());

        if(accountHolder.isPresent()) {
            return ResponseEntity.ok().body(accountHolder.get().getId());
        } else {
            return ResponseEntity.status(403).body("Invalid username or password");
        }
    }

    public AccountHolderService getAccountHolderService() {
        return accountHolderService;
    }

    public void setAccountHolderService(AccountHolderService accountHolderService) {
        this.accountHolderService = accountHolderService;
    }
}
