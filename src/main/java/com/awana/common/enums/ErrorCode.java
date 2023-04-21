package com.awana.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Error Code enum
 *
 * @author Sam Butler
 * @since April 21, 2023
 */
public enum ErrorCode {
    REQUIRED("REQUIRED", false, "NotBlank", "NotNull", "NotEmpty"),
    INVALID_FORMAT("INVALID_FORMAT", true, "Pattern", "Email", "Digits"),
    MESSAGE_UNREADABLE("MESSAGE_UNREADABLE"),
    LENGTH("LENGTH", true, "Length", "Size"),
    MAX_VALUE("MAX_VALUE", true, "Max", "DecimalMax"),
    MIN_VALUE("MIN_VALUE", true, "Min", "DecimalMin"),
    ENUM_CONSTRAINT("ENUM_CONSTRAINT"),
    GENERAL("GENERAL_ERROR"),
    INVALID("INVALID", false, "invalid"),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS"),
    DOES_NOT_EXIST("DOES_NOT_EXIST", true, "Exists");

    private String textId;
    private boolean additionalInfoNeeded = false;
    private List<String> errorCodes = new ArrayList<>();

    /**
     * Constructs an instance of the {@link ErrorCode} object.
     *
     * @param textId used to identify the error.
     */
    private ErrorCode(String textId) {
        this.textId = textId;
    }

    /**
     * Constructs an instance of the {@link ErrorCode} object.
     *
     * @param textId               used to identify the error.
     * @param additionalInfoNeeded Determines whether additional error information
     *                             should be provided
     * @param errorCodes           List of Spring validation codes that correlate
     *                             with the enum error code
     */
    private ErrorCode(String textId, boolean additionalInfoNeeded, String... errorCodes) {
        this.textId = textId;
        this.additionalInfoNeeded = additionalInfoNeeded;
        for(String code : errorCodes) {
            this.errorCodes.add(code);
        }
    }

    /**
     * Returns the text id to identify the error.
     *
     * @return the text id
     */
    public String getTextId() {
        return textId;
    }

    /**
     * Returns the Spring error codes associated with the error code.
     *
     * @return list of Spring error code values
     */
    public List<String> getErrorCodes() {
        return errorCodes;
    }

    /**
     * Returns the true/false value of whether additional information should be sent
     * back with the error code
     *
     * @return if additional information should be sent
     */
    public boolean isAdditionalInfoNeeded() {
        return additionalInfoNeeded;
    }

    /**
     * Returns the error code enum from the provided string value.
     * 
     * @param errorCode The string value to cast
     * @return The ErrorCode object.
     */
    public static ErrorCode fromErrorCode(String errorCode) {
        for(ErrorCode code : ErrorCode.values()) {
            if(code.getErrorCodes().contains(errorCode)) {
                return code;
            }
        }
        return ErrorCode.GENERAL;
    }
}
