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
         * 血糖传感器负责本轮主要报警概率，
         * 血压日常数据保持正常，避免独立随机异常
         * 把总体比例再次推高。异常血压仍可通过 API 测试。
         */
        return 110 + random.nextInt(20);
    }


    /**
     * 读取模拟舒张压。
     */
    public int readDiastolicPressure() {

        checkSensorStatus();

        /*
         * 与收缩压配套生成正常舒张压，
         * 保证日常演示的报警来源清晰可解释。
         */
        return 70 + random.nextInt(15);
    }
}
