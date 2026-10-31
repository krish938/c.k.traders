package com.labdistrib.exception;

import java.time.LocalDateTime;

/**
 * ErrorResponse — The JSON structure returned when an error occurs.
 * 
 * Instead of returning a raw error message, we return a structured object:
 * {
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "Category not found with id: 99",
 *   "timestamp": "2026-10-24T01:00:00"
 * }
 * 
 * This is a DTO specifically for error responses.
 */
public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
