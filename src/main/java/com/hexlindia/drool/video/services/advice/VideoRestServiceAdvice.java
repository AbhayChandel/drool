package com.hexlindia.drool.video.services.advice;

import com.hexlindia.drool.video.exception.VideoNotFoundException;
import com.hexlindia.drool.video.services.impl.rest.VideoRestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = VideoRestServiceImpl.class)
@Slf4j
public class VideoRestServiceAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(VideoNotFoundException.class)
    @ResponseBody
    String handleVideoNotFoundException(VideoNotFoundException e) {
        log.warn(e.getMessage());
        return e.getMessage();
    }
}
