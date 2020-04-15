package com.hexlindia.drool.user.services.advice;

import com.hexlindia.drool.user.exception.EmailExistException;
import com.hexlindia.drool.user.exception.UserAccountNotFoundException;
import com.hexlindia.drool.user.services.impl.rest.UserAccountRestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = UserAccountRestServiceImpl.class)
@Slf4j
public class UserAccountRestServiceAdvice {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserAccountNotFoundException.class)
    @ResponseBody
    String handleUserAccountNotFoundException(UserAccountNotFoundException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailExistException.class)
    @ResponseBody
    String handleUserProfileNotFoundException(EmailExistException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }
}
