package com.campaign.admission.exception;

public class EncryptorRuntimeException extends RuntimeException {

    private String message;

    public EncryptorRuntimeException() {
    }

    public EncryptorRuntimeException(Throwable cause, String message) {
        super(cause);
        this.message = message;
    }

    public EncryptorRuntimeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

