package com.dragon.featureexercise.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class CustomGlobalExceptionHandler{

    @ExceptionHandler(FeatureDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handledFeatureDoesNotExitException() {}

    @ExceptionHandler(FeatureAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleFeatureAlreadyExistException() {}

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void constraintViolationException() {}

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleInternalServerException() {}

}
