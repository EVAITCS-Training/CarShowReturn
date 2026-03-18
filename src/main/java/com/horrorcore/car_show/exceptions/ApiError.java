package com.horrorcore.car_show.exceptions;

import java.time.LocalDateTime;

/**
 * Simple DTO to return structured API error responses.
 */
public class ApiError {

    private final String endpoint;
    private final String status;
    private final int statusCode;
    private final String message;
    private final LocalDateTime timestamp;

    public ApiError(String endpoint, String status, int statusCode, String message, LocalDateTime timestamp) {
        this.endpoint = endpoint;
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getStatus() {
        return status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

