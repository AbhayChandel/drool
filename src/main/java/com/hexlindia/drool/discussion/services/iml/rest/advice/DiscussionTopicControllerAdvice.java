package com.hexlindia.drool.discussion.services.iml.rest.advice;

import com.hexlindia.drool.discussion.exception.DiscussionTopicNotFoundException;
import com.hexlindia.drool.discussion.services.iml.rest.DiscussionTopicRestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = DiscussionTopicRestServiceImpl.class)
@Slf4j
public class DiscussionTopicControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DiscussionTopicNotFoundException.class)
    @ResponseBody
    String handleDiscussionTopicNotFoundException(DiscussionTopicNotFoundException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }
}
