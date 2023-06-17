/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.exception.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fbl.common.enums.ErrorCode;
import com.fbl.exception.domain.DataValidationExceptionError;
import com.fbl.exception.domain.ExceptionError;
import com.fbl.exception.domain.FieldValidationError;
import com.fbl.exception.types.BaseException;
import com.fbl.exception.types.InsufficientPermissionsException;
import com.fbl.exception.types.InvalidCredentialsException;
import com.fbl.exception.types.JwtTokenException;
import com.fbl.exception.types.NotFoundException;
import com.fbl.exception.types.UserNotFoundException;

/**
 * Exception Helper class for returning response entitys of the errored objects.
 * 
 * @author Sam Butler
 * @since August 24, 2021
 */
@RestControllerAdvice
public class BaseExceptionHandlerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseExceptionHandlerController.class);

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionError handleInvalidCredentialsException(Exception ex) {
        LOGGER.error(ex.getMessage());
        return new ExceptionError(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({NotFoundException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionError handleNotFoundException(Exception ex) {
        LOGGER.error(ex.getMessage());
        return new ExceptionError(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DataValidationExceptionError handleValidationErrors(MethodArgumentNotValidException ex) {
        List<FieldValidationError> errors = ex.getBindingResult().getFieldErrors().stream().map(this::convertFieldError)
                .collect(Collectors.toList());
        LOGGER.error("Field Validation Errors: {}",
                     errors.stream().map(e -> e.getField()).collect(Collectors.joining(",")));
        return new DataValidationExceptionError("Validation Error", HttpStatus.BAD_REQUEST, errors, null);
    }

    @ExceptionHandler(JwtTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Object handleJwtTokenException(Exception ex) {
        LOGGER.error(ex.getMessage());
        return new ExceptionError(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InsufficientPermissionsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionError handleInsufficientPermissionsException(Exception ex) {
        LOGGER.error(ex.getMessage());
        return new ExceptionError(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionError handleBaseException(Exception ex) {
        LOGGER.error(ex.getMessage());
        return new ExceptionError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionError handleException(Exception ex) {
        LOGGER.error(ex.getMessage());
        return new ExceptionError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private FieldValidationError convertFieldError(FieldError e) {
        return new FieldValidationError(ErrorCode.INVALID, e.getField(), e.getRejectedValue(), e.getDefaultMessage());
    }
}
