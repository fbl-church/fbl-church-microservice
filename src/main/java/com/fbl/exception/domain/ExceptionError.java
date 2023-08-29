/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.exception.domain;

import java.util.Date;

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
    private String error;

    @Schema(description = "When the exception Occured")
    private Date timestamp;

    public ExceptionError() {
    }

    public ExceptionError(String message) {
        this.status = HttpStatus.BAD_REQUEST.value();
        this.timestamp = new Date();
        this.error = HttpStatus.BAD_REQUEST.getReasonPhrase();
        this.message = message;
    }

    public ExceptionError(String message, HttpStatus status) {
        this.status = status.value();
        this.timestamp = new Date();
        this.error = status.getReasonPhrase();
        this.message = message;
    }

    public ExceptionError(String message, HttpStatus status, String error, Date timestamp) {
        this.timestamp = timestamp;
        this.status = status.value();
        this.error = error;
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

    public String getErrors() {
        return error;
    }

    public void setErrors(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
