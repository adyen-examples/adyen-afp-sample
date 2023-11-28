package com.adyen;

import com.adyen.config.ApplicationProperty;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AfPSampleApplication {

    @Autowired
    private ApplicationProperty applicationProperty;

    private final Logger log = LoggerFactory.getLogger(AfPSampleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AfPSampleApplication.class, args);
    }

    @PostConstruct
    public void init() {
        log.info("Starting up");

        if(getApplicationProperty().getLemApiKey().isEmpty()) {
            throw new RuntimeException("LEM API key is undefined");
        }

        if(getApplicationProperty().getBclApiKey().isEmpty()) {
            throw new RuntimeException("BCL API key is undefined");
        }

    }

    public ApplicationProperty getApplicationProperty() {
        return applicationProperty;
    }

    public void setApplicationProperty(ApplicationProperty applicationProperty) {
        this.applicationProperty = applicationProperty;
    }
}
