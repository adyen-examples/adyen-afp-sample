package com.adyen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperty {

    @Value("${ADYEN_API_KEY}")
    private String apiKey;

    @Value("${ADYEN_MERCHANT_ACCOUNT}")
    private String merchantAccount;

    @Value("${ADYEN_LEM_API_KEY}")
    private String lemApiKey;

    @Value("${ADYEN_BCL_API_KEY}")
    private String bclApiKey;

    @Value("${ADYEN_HMAC_KEY}")
    private String hmacKey;

    @Value("${ADYEN_HOSTED_ONBOARDING_THEME_ID}")
    private String hostedOnboardingThemeId;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getMerchantAccount() {
        return merchantAccount;
    }

    public void setMerchantAccount(String merchantAccount) {
        this.merchantAccount = merchantAccount;
    }

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
