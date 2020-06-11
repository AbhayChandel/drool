package com.hexlindia.drool.common.error.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class DataAccessControllerAdvice {

    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    ResponseEntity<String> handleMethodArgumentNotValidException(DataAccessException e) {
        return prepareResponse(e);
    }

    private ResponseEntity<String> prepareResponse(DataAccessException e) {
        String responseMessage = "Not able to perform action at this time. Try again in some time.";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e.getCause() != null) {
            String errorMessage = e.getCause().getCause().getMessage();
            if (errorMessage.contains("Unique index or primary key violation")) {
                httpStatus = HttpStatus.CONFLICT;
                if (errorMessage.contains("EMAIL_UNIQUE")) {
                    responseMessage = "Email already registered";
                } else if (errorMessage.contains("USERNAME_UNIQUE")) {
                    responseMessage = "Username already registered";
                } else if (errorMessage.contains("MOBILE_UNIQUE")) {
                    responseMessage = "Mobile already registered";
                }
            }
        }
        return new ResponseEntity<>(responseMessage, httpStatus);
    }
}
