package com.example.insulinpump.exception;

import com.example.insulinpump.model.ApiError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * GlobalExceptionHandler
 *
 * REST API 全局异常处理器。
 *
 * 负责将 Java Exception
 * 转换为统一 JSON 返回。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 专门处理传感器故障。
     */
    @ExceptionHandler(
            SensorException.class
    )
    public ResponseEntity<ApiError>
        handleSensorException(
            SensorException exception) {

        ApiError error =
                createError(

                        503,

                        "SENSOR_FAILURE",

                        exception.getMessage()
                );

        return ResponseEntity
                .status(
                        HttpStatus
                                .SERVICE_UNAVAILABLE
                )
                .body(
                        error
                );
    }


    /**
     * 其他未处理异常。
     *
     * 防止直接向前端暴露
     * Java 堆栈信息。
     */
    @ExceptionHandler(
            Exception.class
    )
    public ResponseEntity<ApiError>
        handleException(
            Exception exception) {

        ApiError error =
                createError(

                        500,

                        "SYSTEM_ERROR",

                        exception.getMessage()
                );

        return ResponseEntity
                .status(
                        HttpStatus
                                .INTERNAL_SERVER_ERROR
                )
                .body(
                        error
                );
    }


    /**
     * 创建统一错误对象。
     */
    private ApiError createError(
            int status,
            String errorType,
            String message) {

        String time =
                LocalDateTime.now()
                        .format(
                                DateTimeFormatter
                                        .ofPattern(
                                                "yyyy-MM-dd HH:mm:ss"
                                        )
                        );

        return new ApiError(
                status,
                errorType,
                message,
                time
        );
    }
}