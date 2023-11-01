package com.adyen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperty {

    @Value("${ENVIRONMENT:TEST}")
    private String environment;

    @Value("${LEM_API_KEY}")
    private String lemApiKey;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getLemApiKey() {
        return lemApiKey;
    }

    public void setLemApiKey(String lemApiKey) {
        this.lemApiKey = lemApiKey;
    }
}
