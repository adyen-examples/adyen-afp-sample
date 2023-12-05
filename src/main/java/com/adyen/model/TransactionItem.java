package com.adyen.model;

public class TransactionItem {
    private String id;
    private String status;
    private String type;
    private String created;
    private String amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TransactionItem id(String id) {
        this.id = id;
        return this;
    }

    public TransactionItem status(String status) {
        this.status = status;
        return this;
    }

    public TransactionItem created(String created) {
        this.created = created;
        return this;
    }

    public TransactionItem amount(String amount) {
        this.amount = amount;
        return this;
    }

    public TransactionItem type(String type) {
        this.type = type;
        return this;
    }

}
