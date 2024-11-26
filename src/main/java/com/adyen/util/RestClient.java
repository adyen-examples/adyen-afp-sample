package com.adyen.util;

import com.adyen.config.ApplicationProperty;
import com.adyen.model.PolicyResource;
import com.adyen.model.SessionRequest;
import com.adyen.model.SessionRequestPolicy;
import com.adyen.model.SessionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestClient {

    private final Logger log = LoggerFactory.getLogger(RestClient.class);

    public SessionResponse call(String url, String apiKey, SessionRequest payload) {

        log.info("call {}", url);

        RestTemplate restTemplate = new RestTemplate();

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-Key", this.getApplicationProperty().getBclApiKey());

        HttpEntity<SessionRequest> entity = new HttpEntity<>(payload, headers);

        // Perform the API request
        ResponseEntity<SessionResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, SessionResponse.class);
        log.info(response.toString());

        return response.getBody();
    }

    public void setApplicationProperty(ApplicationProperty applicationProperty) {
        this.applicationProperty = applicationProperty;
    }

    public ApplicationProperty getApplicationProperty() {
        return applicationProperty;
    }
}
