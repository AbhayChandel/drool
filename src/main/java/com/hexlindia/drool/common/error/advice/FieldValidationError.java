package com.hexlindia.drool.common.error.advice;

public class FieldValidationError {

    private final String field;
    private final String errorMessage;

    public FieldValidationError(String field, String errorMessage) {
        this.field = field;
        this.errorMessage = errorMessage;
    }

    public String getField() {
        return field;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
