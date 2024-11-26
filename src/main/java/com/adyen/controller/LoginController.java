package com.adyen.controller;

import com.adyen.model.UserLogin;
import com.adyen.service.ConfigurationAPIService;
import com.adyen.service.UserService;
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
    @Autowired
    private UserService userService;

    /**
     * Perform user login.
     * Username should be a valid AccountHolder id, password is ignored for demo purposes
     * @param userLogin User credentials from Login form
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLogin userLogin) {

        return getConfigurationAPIService().getAccountHolder(userLogin.getUsername())
                .map(accountHolder -> {
                    setUserIdOnSession(accountHolder.getId());

                    getUserService().getUserByAccountHolderId(getUserIdOnSession())
                            .ifPresent(this::setUserOnSession);

                    return ResponseEntity.ok().body("");
                })
                .orElseGet(() -> ResponseEntity.status(403).body("Invalid username or password"));
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

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
