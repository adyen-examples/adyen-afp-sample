package com.adyen.controller;

import com.adyen.model.IndividualSignup;
import com.adyen.model.OrganisationSignup;
import com.adyen.model.SoleProprietorshipSignup;
import com.adyen.model.balanceplatform.AccountHolder;
import com.adyen.service.ConfigurationAPIService;
import com.adyen.service.LegalEntityManagementAPIService;
import com.adyen.service.ManagementAPIService;
import com.adyen.service.SignupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SignupController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(SignupController.class);

    @Autowired
    private SignupService signupService;
    @Autowired
    private LegalEntityManagementAPIService legalEntityManagementAPIService;

    @Autowired
    private ConfigurationAPIService configurationAPIService;

    @Autowired
    private ManagementAPIService managementAPIService;

    @PostMapping("/signup/individual")
    ResponseEntity<String> signupIndividual(@RequestBody IndividualSignup individualSignup)  {

        ResponseEntity<String> response = null;
        try {

            AccountHolder accountHolder = getSignupService().signup(individualSignup);

            // log in user (to enable redirect to the dashboard)
            setUserIdOnSession(accountHolder.getId());

            response = ResponseEntity.ok().body("");
        } catch (Exception e) {
            log.error(e.toString(), e);
            response = ResponseEntity.status(500).body("An error has occurred");
        }

        return response;
    }

    @PostMapping("/signup/organisation")
    ResponseEntity<String> signupOrganisation(@RequestBody OrganisationSignup organisationSignup)  {

        ResponseEntity<String> response = null;
        try {

            AccountHolder accountHolder = getSignupService().signup(organisationSignup);

            // log in user (to enable redirect to the dashboard)
            setUserIdOnSession(accountHolder.getId());

            response = ResponseEntity.ok().body("");
        } catch (Exception e) {
            log.error(e.toString(), e);
            response = ResponseEntity.status(500).body("An error has occurred");
        }

        return response;
    }

    @PostMapping("/signup/soleproprietorship")
    ResponseEntity<String> signupSoleproprietorship(@RequestBody SoleProprietorshipSignup soleProprietorshipSignup)  {

        ResponseEntity<String> response = null;
        try {

            AccountHolder accountHolder = getSignupService().signup(soleProprietorshipSignup);

            // log in user (to enable redirect to the dashboard)
            setUserIdOnSession(accountHolder.getId());

            response = ResponseEntity.ok().body("");
        } catch (Exception e) {
            log.error(e.toString(), e);
            response = ResponseEntity.status(500).body("An error has occurred");
        }

        return response;
    }


    public SignupService getSignupService() {
        return signupService;
    }

    public void setSignupService(SignupService signupService) {
        this.signupService = signupService;
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

    public ManagementAPIService getManagementAPIService() {
        return managementAPIService;
    }

    public void setManagementAPIService(ManagementAPIService managementAPIService) {
        this.managementAPIService = managementAPIService;
    }
}
