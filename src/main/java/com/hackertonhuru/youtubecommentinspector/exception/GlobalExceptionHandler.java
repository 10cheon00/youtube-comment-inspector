package com.hackertonhuru.youtubecommentinspector.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ExceptionHandler(YoutubeApiRequestException.class)
    public String resolveYoutubeApiRequestException() {
        return "Failed to get youtube data.";
    }
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String resolveIllegalArgumentException() {
        return "Used invalid query parameter.";
    }
}
