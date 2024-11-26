package com.adyen.model;

/**
 * User of the platform
 */
public class User {

    private String name;
    private String location;
    private AccountHolderStatus status;
    private String type;
    private String accountHolderId;
    private String legalEntityId;

    public AccountHolderStatus getStatus() {
        return status;
    }

    public void setStatus(AccountHolderStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(String accountHolderId) {
        this.accountHolderId = accountHolderId;
    }

    public String getLegalEntityId() {
        return legalEntityId;
    }

    public void setLegalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
    }
}
