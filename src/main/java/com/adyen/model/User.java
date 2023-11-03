package com.adyen.model;

import com.adyen.service.AccountHolderStatus;

/**
 * User of the platform
 */
public class User {

    private AccountHolderStatus status;

    public AccountHolderStatus getStatus() {
        return status;
    }

    public void setStatus(AccountHolderStatus status) {
        this.status = status;
    }
}
