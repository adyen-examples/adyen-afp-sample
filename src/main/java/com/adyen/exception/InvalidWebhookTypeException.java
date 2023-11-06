package com.adyen.exception;

public class InvalidWebhookTypeException extends RuntimeException {

    public InvalidWebhookTypeException(String s) {
        super(s);
    }
}