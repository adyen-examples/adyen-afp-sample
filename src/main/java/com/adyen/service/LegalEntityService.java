package com.adyen.service;

import com.adyen.model.IndividualSignup;
import com.adyen.model.OrganisationSignup;
import com.adyen.model.legalentitymanagement.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to create and manage Legal Entities
 */
@Service
public class LegalEntityService {

    private final Logger log = LoggerFactory.getLogger(LegalEntityService.class);

    @Autowired
    private ApiClient apiClient;

    public LegalEntity create(IndividualSignup individualSignup) {

        LegalEntity legalEntity = null;

        try {
            LegalEntityInfoRequiredType legalEntityInfoRequiredType = new LegalEntityInfoRequiredType();
            legalEntityInfoRequiredType
                    .type(LegalEntityInfoRequiredType.TypeEnum.INDIVIDUAL)
                    .individual(new Individual()
                            .name(new Name()
                                    .firstName(individualSignup.getFirstName())
                                    .lastName(individualSignup.getLastName()))
                            .residentialAddress(
                                    new Address()
                                            .country(individualSignup.getCountryCode())
                            ));

            legalEntity = getApiClient().getLegalEntitiesApi().createLegalEntity(legalEntityInfoRequiredType);

        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new RuntimeException("Cannot create LegalEntity: " + e.getMessage());
        }

        return legalEntity;
    }

    public LegalEntity create(OrganisationSignup organisationSignup) {

        LegalEntity legalEntity = null;

        try {
            LegalEntityInfoRequiredType legalEntityInfoRequiredType = new LegalEntityInfoRequiredType();
            legalEntityInfoRequiredType
                    .type(LegalEntityInfoRequiredType.TypeEnum.ORGANIZATION)
                    .organization(new Organization()
                            .legalName(organisationSignup.getLegalName())
                            .registeredAddress(
                                    new Address()
                                            .country(organisationSignup.getCountryCode())
                            ));

            legalEntity = getApiClient().getLegalEntitiesApi().createLegalEntity(legalEntityInfoRequiredType);

        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new RuntimeException("Cannot create LegalEntity: " + e.getMessage());
        }

        return legalEntity;
    }

    protected ApiClient getApiClient() {
        return apiClient;
    }

    protected void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }
}
