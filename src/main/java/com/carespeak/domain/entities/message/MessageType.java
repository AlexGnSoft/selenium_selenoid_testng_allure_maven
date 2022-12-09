package com.carespeak.domain.entities.message;

public enum MessageType {
    SMS("SMS"),
    MMS("MMS"),
    EMAIL("E-mail");


    private String messageType;

    private MessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getValue() {
        return messageType;
    }
}
