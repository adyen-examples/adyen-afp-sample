package com.adyen.model;

import java.util.List;

public class PolicyResource {

    private String accountHolderId;
    private String type;

    public String getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(String accountHolderId) {
        this.accountHolderId = accountHolderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PolicyResource accountHolderId(String accountHolderId) {
        this.accountHolderId = accountHolderId;
        return this;
    }

    public PolicyResource type(String type) {
        this.type = type;
        return this;
    }

}
