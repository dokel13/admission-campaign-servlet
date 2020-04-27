package com.campaign.admission.exception;

public class AdmissionValidatorRuntimeException extends RuntimeException {

    private String message;
    private String wrongValue;

    public AdmissionValidatorRuntimeException() {
    }

    public AdmissionValidatorRuntimeException(String message) {
        this.message = message;
    }

    public AdmissionValidatorRuntimeException(String message, String wrongValue) {
        this.message = message;
        this.wrongValue = wrongValue;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
