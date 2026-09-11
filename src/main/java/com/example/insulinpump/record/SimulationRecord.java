package com.example.insulinpump.record;

import javax.persistence.*;

/**
 * SimulationRecord
 *
 * 智能胰岛素泵仿真系统历史记录实体。
 *
 * 一个 SimulationRecord
 * 表示一次完整系统运行数据。
 *
 * 数据将通过 JPA 保存到 H2 数据库。
 */
@Entity
@Table(name = "simulation_record")
public class SimulationRecord {

    /**
     * 数据库主键。
     *
     * GenerationType.IDENTITY
     * 表示数据库自动生成 ID。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ==========================================
    // Sensor 数据
    // ==========================================

    /**
     * 模拟血糖值。
     */
    private double glucose;

    /**
     * 收缩压。
     */
    private int systolicPressure;

    /**
     * 舒张压。
     */
    private int diastolicPressure;

    /**
     * 心率。
     */
    private int heartRate;

    /**
     * 原始传感器采集时间。
     */
    private String collectTime;


    // ==========================================
    // Sensor Analyzer 分析结果
    // ==========================================

    /**
     * 血糖分析状态。
     */
    private String glucoseStatus;

    /**
     * 血压分析状态。
     */
    private String bloodPressureStatus;

    /**
     * 心率分析状态。
     */
    private String heartRateStatus;

    /**
     * 综合生理状态。
     */
    private String overallStatus;


    // ==========================================
    // Insulin Calculator
    // ==========================================

    /**
     * Simulation Dose。
     *
     * 单位：
     * SU = Simulation Unit
     *
     * 不是真实医疗胰岛素剂量。
     */
    private double simulationDose;


    // ==========================================
    // Pump
    // ==========================================

    /**
     * Pump 当前状态。
     *
     * READY
     * RUNNING
     * STOPPED
     * ERROR
     */
    private String pumpStatus;

    /**
     * 当前 Pump 模拟储液量。
     */
    private double reservoir;


    // ==========================================
    // Alarm
    // ==========================================

    /**
     * Alarm 报警等级。
     *
     * NORMAL
     * WARNING
     * CRITICAL
     */
    private String alarmLevel;


    // ==========================================
    // Database
    // ==========================================

    /**
     * 本条记录写入数据库的时间。
     */
    private String recordTime;


    /**
     * JPA 要求保留无参构造函数。
     */
    public SimulationRecord() {
    }


    // ==========================================
    // Getter / Setter
    // ==========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getGlucoseStatus() {
        return glucoseStatus;
    }

    public void setGlucoseStatus(String glucoseStatus) {
        this.glucoseStatus = glucoseStatus;
    }

    public String getBloodPressureStatus() {
        return bloodPressureStatus;
    }

    public void setBloodPressureStatus(
            String bloodPressureStatus) {

        this.bloodPressureStatus =
                bloodPressureStatus;
    }

    public String getHeartRateStatus() {
        return heartRateStatus;
    }

    public void setHeartRateStatus(
            String heartRateStatus) {

        this.heartRateStatus =
                heartRateStatus;
    }

    public String getOverallStatus() {
        return overallStatus;
    }

    public void setOverallStatus(
            String overallStatus) {

        this.overallStatus =
                overallStatus;
    }

    public double getSimulationDose() {
        return simulationDose;
    }

    public void setSimulationDose(
            double simulationDose) {

        this.simulationDose =
                simulationDose;
    }

    public String getPumpStatus() {
        return pumpStatus;
    }

    public void setPumpStatus(
            String pumpStatus) {

        this.pumpStatus =
                pumpStatus;
    }

    public double getReservoir() {
        return reservoir;
    }

    public void setReservoir(
            double reservoir) {

        this.reservoir =
                reservoir;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(
            String alarmLevel) {

        this.alarmLevel =
                alarmLevel;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(
            String recordTime) {

        this.recordTime =
                recordTime;
    }
}