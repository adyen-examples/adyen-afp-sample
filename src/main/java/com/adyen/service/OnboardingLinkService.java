package com.adyen.service;

import com.adyen.config.ApplicationProperty;
import com.adyen.model.legalentitymanagement.OnboardingLink;
import com.adyen.model.legalentitymanagement.OnboardingLinkInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * Service of the OnboardingLink entity
 */
@Service
public class OnboardingLinkService {

    private final Logger log = LoggerFactory.getLogger(OnboardingLinkService.class);

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private ApplicationProperty applicationProperty;

    /**
     * Generate the Hosted Onboarding link for the Legal entity
     * @param legalEntityId
     * @return
     */
    public Optional<OnboardingLink> getOnboardingLink(String legalEntityId) {

        Optional<OnboardingLink> onboardingLink = Optional.empty();

        try {

            onboardingLink = Optional.of(getApiClient().getHostedOnboardingApi()
                    .getLinkToAdyenhostedOnboardingPage(legalEntityId, getOnboardingLinkConfiguration()));
            log.info(onboardingLink.toString());

        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return onboardingLink;
    }

    /**
     * retrieve the desired customisation for the Hosted Onboarding app
     * @return
     */
    private OnboardingLinkInfo getOnboardingLinkConfiguration() {
        OnboardingLinkInfo onboardingLinkInfo = null;

        // control the page language
        onboardingLinkInfo.setLocale("en-US");
        // link to bring users back to the platform
        onboardingLinkInfo.setRedirectUrl("http://locahost:3000/dashboard");
        // custom theme applied to the Hosted Onboarding app. Must be already created in the Customer Area
        onboardingLinkInfo.setThemeId(getApplicationProperty().getHostedOnboardingThemeId());
        // additional settings
        onboardingLinkInfo.setSettings(
                Map.of("changeLegalEntityType", true,
                        "editPrefilledCountry", false)
        );

        return onboardingLinkInfo;
    }
    protected ApiClient getApiClient() {
        return apiClient;
    }

    protected void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApplicationProperty getApplicationProperty() {
        return applicationProperty;
    }

    public void setApplicationProperty(ApplicationProperty applicationProperty) {
        this.applicationProperty = applicationProperty;
    }
}
