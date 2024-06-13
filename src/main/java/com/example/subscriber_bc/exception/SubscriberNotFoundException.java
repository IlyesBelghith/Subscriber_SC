package com.example.subscriber_bc.exception;

public class SubscriberNotFoundException extends RuntimeException {
    public SubscriberNotFoundException(String message) {
        super(message);
    }
}
