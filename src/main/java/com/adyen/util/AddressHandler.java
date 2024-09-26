package com.adyen.util;

import com.adyen.model.StoreConfigurationAddress;
import com.adyen.model.legalentitymanagement.Address;
import com.adyen.model.management.StoreLocation;
import org.springframework.stereotype.Service;

/**
 * Helper class to convert Address models
 */
@Service
public class AddressHandler {

    /**
     * Create a StoreConfigurationAddress from the given LegalEntity Address
     * @param address
     * @return
     */
    public StoreConfigurationAddress getStoreLocation(Address address) {

        return new StoreConfigurationAddress()
                .city(address.getCity())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .stateOrProvince(address.getStateOrProvince())
                .street(address.getStreet())
                .street2(address.getStreet2());
    }

    /**
     * Create a StoreLocation from the given StoreConfigurationAddress
     * @param address
     * @return
     */
    public StoreLocation getStoreLocation(StoreConfigurationAddress address) {

        return new StoreLocation()
                .city(address.getCity())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .stateOrProvince(address.getStateOrProvince())
                .line1(address.getStreet())
                .line2(address.getStreet2());
    }
}