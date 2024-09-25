package com.adyen.util;

import com.adyen.model.StoreConfigurationAddress;
import com.adyen.model.legalentitymanagement.Address;
import org.springframework.stereotype.Service;

/**
 * Helper class to work with the Address class
 */
@Service
public class AddressHandler {

    /**
     * Create a StoreConfigurationAddress from the given LegalEntity Address
     * @param address
     * @return
     */
    public StoreConfigurationAddress getStoreConfigurationAddress(Address address) {

        return new StoreConfigurationAddress()
                .city(address.getCity())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .stateOrProvince(address.getStateOrProvince())
                .street(address.getStreet())
                .street2(address.getStreet2());
    }

}