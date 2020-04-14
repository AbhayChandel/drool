package com.hexlindia.drool.user.services.advice;

import com.hexlindia.drool.user.exception.UserProfileNotFoundException;
import com.hexlindia.drool.user.exception.UsernameExistException;
import com.hexlindia.drool.user.services.impl.rest.UserProfileRestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = UserProfileRestServiceImpl.class)
@Slf4j
public class UserProfileRestServiceAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserProfileNotFoundException.class)
    @ResponseBody
    String handleUserProfileNotFoundException(UserProfileNotFoundException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UsernameExistException.class)
    @ResponseBody
    String handleUserProfileNotFoundException(UsernameExistException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }
}
