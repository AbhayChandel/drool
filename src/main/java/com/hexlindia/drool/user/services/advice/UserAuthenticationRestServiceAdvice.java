package com.hexlindia.drool.user.services.advice;

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
}
