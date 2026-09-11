package com.example.insulinpump.controller;

import com.example.insulinpump.model.SensorData;
import com.example.insulinpump.sensor.BloodPressureSensor;
import com.example.insulinpump.sensor.GlucoseSensor;
import com.example.insulinpump.sensor.HeartRateSensor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SensorController
 *
 * 模拟传感器接口控制器。
 *
 * 主要负责：
 *
 * 浏览器
 *     ↓
 * SensorController
 *     ↓
 * 三个模拟传感器
 *     ↓
 * SensorData
 *     ↓
 * JSON
 *     ↓
 * 浏览器
 */
@RestController
@RequestMapping("/api/sensor")
public class SensorController {

    /**
     * 血糖传感器。
     */
    private final GlucoseSensor glucoseSensor;

    /**
     * 血压传感器。
     */
    private final BloodPressureSensor bloodPressureSensor;

    /**
     * 心率传感器。
     */
    private final HeartRateSensor heartRateSensor;


    /**
     * 构造函数注入。
     *
     * Spring Boot 会自动找到三个
     * 标有 @Component 的传感器对象，
     * 然后传入这里。
     */
    public SensorController(
            GlucoseSensor glucoseSensor,
            BloodPressureSensor bloodPressureSensor,
            HeartRateSensor heartRateSensor) {

        this.glucoseSensor = glucoseSensor;
        this.bloodPressureSensor = bloodPressureSensor;
        this.heartRateSensor = heartRateSensor;
    }


    /**
     * 获取当前模拟传感器数据。
     *
     * 浏览器访问：
     *
     * http://localhost:9100/api/sensor/current
     *
     * 就会执行这个方法。
     *
     * @return 一组模拟生理数据
     */
    @GetMapping("/current")
    public SensorData getCurrentSensorData() {

        /*
         * 第一步：
         * 从三个模拟传感器分别读取数据。
         */
        double glucose =
                glucoseSensor.readGlucose();

        int systolic =
                bloodPressureSensor.readSystolicPressure();

        int diastolic =
                bloodPressureSensor.readDiastolicPressure();

        int heartRate =
                heartRateSensor.readHeartRate();


        /*
         * 第二步：
         * 把不同传感器的数据组合成
         * 一个 SensorData 对象。
         */
        SensorData sensorData =
                new SensorData(
                        glucose,
                        systolic,
                        diastolic,
                        heartRate
                );


        /*
         * 第三步：
         *
         * 因为这个类使用了 @RestController，
         * Spring Boot 会自动把 SensorData
         * 转换为 JSON 后返回浏览器。
         */
        return sensorData;
    }
}