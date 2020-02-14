package com.hexlindia.drool.user.services.impl.rest.advice;

import com.hexlindia.drool.user.exception.UserAccountNotFoundException;
import com.hexlindia.drool.user.exception.UserProfileNotFoundException;
import com.hexlindia.drool.user.services.impl.rest.JwtUserAuthenticationRestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = JwtUserAuthenticationRestServiceImpl.class)
@Slf4j
public class UserAuthenticationRestServiceAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    String handleMethodArgumentNotValidException(BadCredentialsException e) {
        return "Wrong Username or Password";
    }

    /*@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    String handleMethodArgumentNotValidException(DataAccessException e) {
        log.error("DataAccessException is thrown with following message: {}" + e.getMessage());
        return "Not able to register user at this time. Try again in some time.";
    }*/

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserAccountNotFoundException.class)
    @ResponseBody
    String handleUserAccountNotFoundException(UserAccountNotFoundException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserProfileNotFoundException.class)
    @ResponseBody
    String handleUserProfileNotFoundException(UserProfileNotFoundException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }
}
