package com.hexlindia.drool.discussion.services.iml.rest.advice;

import com.hexlindia.drool.discussion.exception.DiscussionReplyNotFoundException;
import com.hexlindia.drool.discussion.services.iml.rest.DiscussionReplyRestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = DiscussionReplyRestServiceImpl.class)
@Slf4j
public class DiscussionReplyControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DiscussionReplyNotFoundException.class)
    @ResponseBody
    String handleDiscussionReplyNotFoundException(DiscussionReplyNotFoundException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }
}
