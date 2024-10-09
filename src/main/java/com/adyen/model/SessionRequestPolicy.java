package com.adyen.model;

import java.util.List;

public class SessionRequestPolicy {

    private List<PolicyResource> resources;
    private List<String> roles;

    public List<PolicyResource> getResources() {
        return resources;
    }

    public void setResources(List<PolicyResource> resources) {
        this.resources = resources;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public SessionRequestPolicy resources(List<PolicyResource> resources) {
        this.resources = resources;
        return this;
    }

    public SessionRequestPolicy roles(List<String> roles) {
        this.roles = roles;
        return this;
    }

}
