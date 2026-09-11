package com.example.insulinpump.sensor;

import com.example.insulinpump.exception.SensorException;
import com.example.insulinpump.service.FaultSimulationService;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * GlucoseSensor
 *
 * 模拟血糖传感器。
 *
 * 正常状态：
 * 返回随机模拟血糖值。
 *
 * 故障状态：
 * 抛出 SensorException。
 */
@Component
public class GlucoseSensor {

    /**
     * 随机数生成器。
     */
    private final Random random =
            new Random();


    /**
     * 故障状态服务。
     */
    private final FaultSimulationService faultService;


    /**
     * 构造函数注入。
     */
    public GlucoseSensor(
            FaultSimulationService faultService) {

        this.faultService =
                faultService;
    }


    /**
     * 读取模拟血糖。
     */
    public double readGlucose() {

        /*
         * 模拟整个系统断电。
         */
        if (faultService
                .isPowerFailure()) {

            throw new SensorException(
                    "模拟系统 Power Failure，血糖传感器无法工作。"
            );
        }


        /*
         * 模拟 Glucose Sensor 独立故障。
         */
        if (faultService
                .isGlucoseSensorFailure()) {

            throw new SensorException(
                    "Glucose Sensor Failure：无法获得模拟血糖数据。"
            );
        }


        /*
         * 正常情况下产生课程仿真数据。
         *
         * 仅用于软件测试，
         * 不代表医学数据生成标准。
         */
        double glucose =
                3.0
                +
                random.nextDouble()
                *
                12.0;


        /*
         * 保留两位小数。
         */
        return Math.round(
                glucose * 100.0
        ) / 100.0;
    }
}