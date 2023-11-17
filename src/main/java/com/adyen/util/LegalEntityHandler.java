package com.adyen.util;

import com.adyen.model.User;
import com.adyen.model.legalentitymanagement.Individual;
import com.adyen.model.legalentitymanagement.LegalEntity;
import com.adyen.model.legalentitymanagement.OnboardingLink;
import com.adyen.model.legalentitymanagement.Organization;
import org.springframework.stereotype.Service;

/**
 * Helper class to work with the LegalEntity class
 */
@Service
public class LegalEntityHandler {

    /**
     * Create a User from the given Legal Entity
     * @param legalEntity
     * @return
     */
    public User getUserFromLegalEntity(LegalEntity legalEntity) {
        User user = new User();

        user.setType(legalEntity.getType().getValue());

        if(legalEntity.getIndividual() != null) {
            user.setName(getFullName(legalEntity.getIndividual()));
            user.setLocation(getLocation(legalEntity.getIndividual()));
        } else if(legalEntity.getOrganization() != null) {
            user.setName(legalEntity.getOrganization().getLegalName());
            user.setLocation(getLocation(legalEntity.getOrganization()));
        }

        return user;
    }

    private String getFullName(Individual individual) {
        return individual.getName().getFirstName() + " " + individual.getName().getLastName();
    }

    private String getLocation(Individual individual) {
        return individual.getResidentialAddress().getCity() + " (" + individual.getResidentialAddress().getCountry() + ")";
    }

    private String getLocation(Organization organization) {
        return organization.getRegisteredAddress().getCity() + " (" + organization.getRegisteredAddress().getCountry() + ")";
    }

}