package com.adyen.model;

public class StoreConfiguration {

    private String businessLineId;
    private String balanceAccountId;
    private String countryCode;
    private String storeName;

    public String getBusinessLineId() {
        return businessLineId;
    }

    public void setBusinessLineId(String businessLineId) {
        this.businessLineId = businessLineId;
    }

    public StoreConfiguration businessLineId(String businessLineId) {
        this.businessLineId = businessLineId;
        return this;
    }

    public String getBalanceAccountId() {
        return balanceAccountId;
    }

    public void setBalanceAccountId(String balanceAccountId) {
        this.balanceAccountId = balanceAccountId;
    }

    public StoreConfiguration balanceAccountId(String balanceAccountId) {
        this.balanceAccountId = balanceAccountId;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public StoreConfiguration countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public StoreConfiguration storeName(String storeName) {
        this.storeName = storeName;
        return this;
    }


}
