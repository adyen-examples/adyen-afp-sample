package com.adyen.model;

public class StoreConfigurationAddress {

    private String city;
    private String country;
    private String postalCode;
    private String stateOrProvince;
    private String street;
    private String street2;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public StoreConfigurationAddress city(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public StoreConfigurationAddress country(String country) {
        this.country = country;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public StoreConfigurationAddress postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public StoreConfigurationAddress stateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public StoreConfigurationAddress street(String street) {
        this.street = street;
        return this;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public StoreConfigurationAddress street2(String street2) {
        this.street2 = street2;
        return this;
    }

}
