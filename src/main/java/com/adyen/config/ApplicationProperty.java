package com.adyen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperty {

    @Value("${LEM_API_KEY}")
    private String lemApiKey;

    @Value("${BCL_API_KEY}")
    private String bclApiKey;

    @Value("${ADYEN_HMAC_KEY}")
    private String hmacKey;

    @Value("${HOSTED_ONBOARDING_THEME_ID}")
    private String hostedOnboardingThemeId;

    public String getLemApiKey() {
        return lemApiKey;
    }

    public void setLemApiKey(String lemApiKey) {
        this.lemApiKey = lemApiKey;
    }

    public String getHostedOnboardingThemeId() {
        return hostedOnboardingThemeId;
    }

    public void setHostedOnboardingThemeId(String hostedOnboardingThemeId) {
        this.hostedOnboardingThemeId = hostedOnboardingThemeId;
    }

    public String getBclApiKey() {
        return bclApiKey;
    }

    public void setBclApiKey(String bclApiKey) {
        this.bclApiKey = bclApiKey;
    }

    public String getHmacKey() {
        return hmacKey;
    }

    public void setHmacKey(String hmacKey) {
        this.hmacKey = hmacKey;
    }
}
