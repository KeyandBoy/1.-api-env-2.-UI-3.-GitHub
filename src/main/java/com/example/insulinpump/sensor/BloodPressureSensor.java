package com.example.insulinpump.sensor;

import com.example.insulinpump.exception.SensorException;
import com.example.insulinpump.service.FaultSimulationService;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * BloodPressureSensor
 *
 * 模拟血压传感器。
 */
@Component
public class BloodPressureSensor {

    private final Random random =
            new Random();


    private final FaultSimulationService faultService;


    /**
     * 构造函数注入。
     */
    public BloodPressureSensor(
            FaultSimulationService faultService) {

        this.faultService =
                faultService;
    }


    /**
     * 在每次读取数据前检查设备状态。
     */
    private void checkSensorStatus() {

        /*
         * 系统断电。
         */
        if (faultService
                .isPowerFailure()) {

            throw new SensorException(
                    "模拟系统 Power Failure，血压传感器无法工作。"
            );
        }


        /*
         * 血压 Sensor 故障。
         */
        if (faultService
                .isBloodPressureSensorFailure()) {

            throw new SensorException(
                    "Blood Pressure Sensor Failure：无法获得模拟血压数据。"
            );
        }
    }


    /**
     * 读取模拟收缩压。
     */
    public int readSystolicPressure() {

        checkSensorStatus();

        /*
         * 日常演示使用 105 - 139 的课程仿真范围，
         * 避免随机值频繁达到 Alarm 的高压阈值 140。
         */
        return 105
                +
                random.nextInt(
                        35
                );
    }


    /**
     * 读取模拟舒张压。
     */
    public int readDiastolicPressure() {

        checkSensorStatus();

        /*
         * 日常演示使用 65 - 84 的课程仿真范围，
         * 避免随机值频繁达到 Alarm 的高压阈值 90。
         */
        return 65
                +
                random.nextInt(
                        20
                );
    }
}
