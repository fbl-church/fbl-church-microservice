/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.exception.domain;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Custom exception object to be returned when endpoints have errors.
 * 
 * @author Sam Butler
 * @since August 24, 2021
 */
@Schema(description = "Exception Error object for managing exceptions")
public class DataValidationExceptionError {

    @Schema(description = "Exception Message")
    private String message;

    @Schema(description = "The Http Status of the exception")
    private int status;

    @Schema(description = "List of errors associated to the exception")
    private List<FieldValidationError> fieldErrors;

    @Schema(description = "When the exception Occured")
    private Date timestamp;

    public DataValidationExceptionError() {}

    public DataValidationExceptionError(String message) {
        this.status = HttpStatus.BAD_REQUEST.value();
        this.timestamp = new Date();
        this.message = message;
    }

    public DataValidationExceptionError(String message, HttpStatus status) {
        this.status = status.value();
        this.timestamp = new Date();
        this.message = message;
    }

    public DataValidationExceptionError(String msg, HttpStatus status, List<FieldValidationError> errors, Date time) {
        this.timestamp = time;
        this.status = status.value();
        this.fieldErrors = errors;
        this.message = msg;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<FieldValidationError> getErrors() {
        return fieldErrors;
    }

    public void setErrors(List<FieldValidationError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
