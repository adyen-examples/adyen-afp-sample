package com.adyen.model;

import java.security.Policy;

public class SessionRequest {

    private String allowOrigin;
    private String product;
    private SessionRequestPolicy policy;

    public String getAllowOrigin() {
        return allowOrigin;
    }

    public void setAllowOrigin(String allowOrigin) {
        this.allowOrigin = allowOrigin;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public SessionRequestPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(SessionRequestPolicy policy) {
        this.policy = policy;
    }

    public SessionRequest allowOrigin(String allowOrigin) {
        this.allowOrigin = allowOrigin;
        return this;
    }

    public SessionRequest product(String product) {
        this.product = product;
        return this;
    }

    public SessionRequest policy(SessionRequestPolicy policy) {
        this.policy = policy;
        return this;
    }

}
