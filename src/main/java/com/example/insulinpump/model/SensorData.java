package com.example.insulinpump.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * SensorData
 *
 * 模拟传感器数据实体类。
 *
 * 这个类用于保存一次完整的患者生理数据采集结果，
 * 目前包括：
 *
 * 1. 血糖
 * 2. 收缩压
 * 3. 舒张压
 * 4. 心率
 * 5. 数据采集时间
 *
 * 后续数据库模块也会使用这个类。
 */
public class SensorData {

    /**
     * 血糖值。
     *
     * 单位：
     * mmol/L
     */
    private double glucose;

    /**
     * 收缩压。
     *
     * 例如：
     * 120 / 80 mmHg 中的 120。
     */
    private int systolicPressure;

    /**
     * 舒张压。
     *
     * 例如：
     * 120 / 80 mmHg 中的 80。
     */
    private int diastolicPressure;

    /**
     * 心率。
     *
     * 单位：
     * BPM（每分钟心跳次数）
     */
    private int heartRate;

    /**
     * 当前数据的采集时间。
     *
     * 为了方便网页直接显示，
     * 这里暂时保存为字符串。
     */
    private String collectTime;


    /**
     * 无参构造函数。
     *
     * Spring Boot 将 Java 对象转换成 JSON 时，
     * 保留无参构造函数是一个比较好的习惯。
     */
    public SensorData() {
    }


    /**
     * 完整构造函数。
     *
     * @param glucose          血糖
     * @param systolicPressure 收缩压
     * @param diastolicPressure 舒张压
     * @param heartRate        心率
     */
    public SensorData(double glucose,
                      int systolicPressure,
                      int diastolicPressure,
                      int heartRate) {

        this.glucose = glucose;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.heartRate = heartRate;

        /*
         * 创建本次数据的采集时间。
         *
         * 最终格式例如：
         * 2026-09-10 13:50:25
         */
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        this.collectTime =
                LocalDateTime.now().format(formatter);
    }


    // =========================
    // Getter 和 Setter
    // =========================

    public double getGlucose() {
        return glucose;
    }

    public void setGlucose(double glucose) {
        this.glucose = glucose;
    }

    public int getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public int getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }
}