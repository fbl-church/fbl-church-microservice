package com.awana.exception.domain;

import com.awana.common.enums.ErrorCode;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Object to hold information about each individual field error
 *
 * @author Sam Butler
 * @since April 21, 2023
 */
@Schema(description = "Field Validation Error Object")
public class FieldValidationError {

    @Schema(description = "errorCode", example = "REQUIRED")
    private ErrorCode errorCode = ErrorCode.INVALID;

    @Schema(description = "field", example = "contactUsLeadType")
    private String field;

    @Schema(description = "providedValue", example = "null")
    private String providedValue;

    @Schema(description = "message", example = "null")
    private String message;

    public FieldValidationError() {

    }

    public FieldValidationError(ErrorCode code, String field) {
        this.errorCode = code;
        this.field = field;
    }

    public FieldValidationError(ErrorCode code) {
        this.errorCode = code;
    }

    public FieldValidationError(ErrorCode code, String field, Object providedValue, String message) {
        this.errorCode = code;
        this.field = field;
        this.providedValue = providedValue == null ? null : providedValue.toString();
        this.message = message;
    }

    public FieldValidationError(String field) {
        this.field = field;
    }

    /**
     * Returns the {@link ErrorCode} for the field error.
     *
     * @return the {@link ErrorCode} of the field
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the {@link ErrorCode} for the field error.
     *
     * @param errorCode The {@link ErrorCode} of the field
     */
    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Returns the name of the field with the error
     *
     * @return the field name
     */
    public String getField() {
        return field;
    }

    /**
     * Sets the name of the field with the error
     *
     * @param field The field name
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * Returns the value of the field provided in the request
     *
     * @return the provided value
     */
    public String getProvidedValue() {
        return providedValue;
    }

    /**
     * Sets the value of the field provided in the request
     *
     * @param providedValue The provided value that resulted in the error
     */
    public void setProvidedValue(String providedValue) {
        this.providedValue = providedValue;
    }

    /**
     * Returns the additional information regarding the error
     *
     * @return the additional info
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the additional information regarding the error
     *
     * @param message The additional info for the error
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
