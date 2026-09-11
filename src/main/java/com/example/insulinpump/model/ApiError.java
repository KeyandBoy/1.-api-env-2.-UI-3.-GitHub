package com.example.insulinpump.model;

/**
 * ApiError
 *
 * REST API 统一错误返回格式。
 */
public class ApiError {

    /**
     * HTTP 状态码。
     */
    private int status;

    /**
     * 错误类型。
     */
    private String error;

    /**
     * 详细错误信息。
     */
    private String message;

    /**
     * 错误发生时间。
     */
    private String time;


    public ApiError() {
    }


    public ApiError(
            int status,
            String error,
            String message,
            String time) {

        this.status = status;
        this.error = error;
        this.message = message;
        this.time = time;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}