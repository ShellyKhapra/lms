package com.lms.common;

public class LMSException extends RuntimeException {
    private final Integer statusCode;
    private final String category;

    public LMSException(Integer statusCode, String category, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.category = category;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getCategory() {
        return category;
    }
}
