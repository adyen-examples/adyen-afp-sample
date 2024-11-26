package com.adyen.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolicyResource {

    private String accountHolderId;
    private String legalEntityId;
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

    public String getLegalEntityId() {
        return legalEntityId;
    }

    public void setLegalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    public PolicyResource accountHolderId(String accountHolderId) {
        this.accountHolderId = accountHolderId;
        return this;
    }

    public PolicyResource type(String type) {
        this.type = type;
        return this;
    }

    public PolicyResource legalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
        return this;
    }

}
