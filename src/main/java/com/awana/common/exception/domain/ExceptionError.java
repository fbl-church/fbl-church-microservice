/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.common.exception.domain;

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
public class ExceptionError {

    @Schema(description = "Exception Message")
    private String message;

    @Schema(description = "The Http Status of the exception")
    private int status;

    @Schema(description = "List of errors associated to the exception")
    private List<String> errors;

    @Schema(description = "When the exception Occured")
    private Date timestamp;

    public ExceptionError() {}

    public ExceptionError(String message) {
        this.status = HttpStatus.BAD_REQUEST.value();
        this.timestamp = new Date();
        this.message = message;
    }

    public ExceptionError(String message, HttpStatus status) {
        this.status = status.value();
        this.timestamp = new Date();
        this.message = message;
    }

    public ExceptionError(String message, HttpStatus status, List<String> errors, Date timestamp) {
        this.timestamp = timestamp;
        this.status = status.value();
        this.errors = errors;
        this.message = message;
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

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> error) {
        this.errors = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
