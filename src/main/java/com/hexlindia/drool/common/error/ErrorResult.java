package com.hexlindia.drool.common.error;

import java.util.ArrayList;
import java.util.List;

public class ErrorResult {

    private final List<FieldValidationError> fieldValidationErrors = new ArrayList<>();

    public ErrorResult(String field, String message) {
        this.fieldValidationErrors.add(new FieldValidationError(field, message));
    }

    public ErrorResult() {
    }

    public List<FieldValidationError> getFieldValidationErrors() {
        return fieldValidationErrors;
    }
}
