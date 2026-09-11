package com.example.insulinpump.sensor;

import com.example.insulinpump.exception.SensorException;
import com.example.insulinpump.service.FaultSimulationService;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * HeartRateSensor
 *
 * 模拟心率传感器。
 */
@Component
public class HeartRateSensor {

    private final Random random =
            new Random();


    private final FaultSimulationService faultService;


    /**
     * 构造函数注入。
     */
    public HeartRateSensor(
            FaultSimulationService faultService) {

        this.faultService =
                faultService;
    }


    /**
     * 读取模拟心率。
     */
    public int readHeartRate() {

        /*
         * 模拟 Power Failure。
         */
        if (faultService
                .isPowerFailure()) {

            throw new SensorException(
                    "模拟系统 Power Failure，心率传感器无法工作。"
            );
        }


        /*
         * 模拟心率 Sensor 故障。
         */
        if (faultService
                .isHeartRateSensorFailure()) {

            throw new SensorException(
                    "Heart Rate Sensor Failure：无法获得模拟心率数据。"
            );
        }


        /*
         * 正常模拟数据。
         */
        return 50
                +
                random.nextInt(
                        70
                );
    }
}