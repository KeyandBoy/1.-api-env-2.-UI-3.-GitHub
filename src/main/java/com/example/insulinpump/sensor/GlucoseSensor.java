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
         *
         * 采用便于课程实验的概率分布：
         *
         * 55%：5.00 - 8.00，正常演示数据
         * 30%：3.50 - 3.90，触发 WARNING
         * 15%：11.10 - 13.00，触发 CRITICAL
         *
         * 本传感器作为一轮演示的主要报警场景来源，
         * 这样系统级结果更接近课程实验指定的
         * 55% NORMAL、30% WARNING、15% CRITICAL。
         * 血压和心率保持正常范围，避免独立异常叠加。
         * 异常场景仍可通过 Analyzer / Alarm API 精确构造。
         */
        int profile = random.nextInt(100);
        double glucose;

        if (profile < 55) {
            glucose = 5.0 + random.nextDouble() * 3.0;
        } else if (profile < 85) {
            glucose = 3.5 + random.nextDouble() * 0.4;
        } else {
            glucose = 11.1 + random.nextDouble() * 1.9;
        }


        /*
         * 保留两位小数。
         */
        return Math.round(
                glucose * 100.0
        ) / 100.0;
    }
}
