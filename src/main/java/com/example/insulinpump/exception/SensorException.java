package com.example.insulinpump.exception;

/**
 * SensorException
 *
 * 模拟传感器发生故障时抛出的异常。
 *
 * 使用自定义异常的好处是：
 *
 * Controller 可以清楚地区分
 * “传感器故障”
 * 和
 * “普通程序错误”。
 */
public class SensorException
        extends RuntimeException {

    public SensorException(
            String message) {

        super(message);
    }
}