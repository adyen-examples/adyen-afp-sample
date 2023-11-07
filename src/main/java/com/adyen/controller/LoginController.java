package com.adyen.controller;

import com.adyen.model.UserLogin;
import com.adyen.model.balanceplatform.AccountHolder;
import com.adyen.service.ConfigurationAPIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Perform user login and logout
 */
@RestController
@RequestMapping("/api")
public class LoginController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ConfigurationAPIService configurationAPIService;

    /**
     * Perform user login.
     * Username should be a valid AccountHolder id, password is ignored for demo purposes
     * @param userLogin User credentials from Login form
     * @return
     */
    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody UserLogin userLogin)  {

        Optional<AccountHolder> accountHolder = getConfigurationAPIService().getAccountHolder(userLogin.getUsername());

        if(accountHolder.isPresent()) {
            setUserIdOnSession(accountHolder.get().getId());
            return ResponseEntity.ok().body("");
        } else {
            return ResponseEntity.status(403).body("Invalid username or password");
        }
    }

    /**
     * Perform user logout, removing userId from HTTP Session.
     * @return
     */
    @PostMapping("/logout")
    ResponseEntity<String>  logout()  {
        removeUserIdFromSession();
        return ResponseEntity.ok().body("");
    }

    public ConfigurationAPIService getConfigurationAPIService() {
        return configurationAPIService;
    }

    public void setConfigurationAPIService(ConfigurationAPIService configurationAPIService) {
        this.configurationAPIService = configurationAPIService;
    }
}
