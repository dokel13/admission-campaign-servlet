package com.campaign.admission.exception;

public class UserValidatorRuntimeException extends RuntimeException {

    private String message;
    private String wrongValue;

    public UserValidatorRuntimeException() {
    }

    public UserValidatorRuntimeException(String message) {
        this.message = message;
    }

    public UserValidatorRuntimeException(String message, String wrongValue) {
        this.message = message;
        this.wrongValue = wrongValue;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getWrongValue() {
        return wrongValue;
    }
}
