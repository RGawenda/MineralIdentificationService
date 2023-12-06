package com.mineralidentificationservice.exceptions;

public class CorruptedRabbitmqMessageException extends Exception {
    public CorruptedRabbitmqMessageException(String message) {
        super(message);
    }
}
