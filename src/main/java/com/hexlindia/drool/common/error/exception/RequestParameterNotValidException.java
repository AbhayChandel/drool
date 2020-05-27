package com.hexlindia.drool.common.error.exception;

public class RequestParameterNotValidException extends RuntimeException {
    public RequestParameterNotValidException(String parameterName, String message) {
        super("Invalid parameter " + parameterName + ": " + message);
    }
}
