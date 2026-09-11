package com.example.insulinpump.model;

/**
 * SensorAnalysisResult
 *
 * 保存模拟传感器数据的分析结果。
 *
 * 注意：
 * 本类中的状态判断仅用于软件工程课程仿真，
 * 不作为真实医疗诊断依据。
 */
public class SensorAnalysisResult {

    /**
     * 血糖状态
     */
    private String glucoseStatus;

    /**
     * 血压状态
     */
    private String bloodPressureStatus;

    /**
     * 心率状态
     */
    private String heartRateStatus;

    /**
     * 综合状态
     */
    private String overallStatus;

    /**
     * 系统提示信息
     */
    private String message;


    public SensorAnalysisResult() {
    }


    public SensorAnalysisResult(
            String glucoseStatus,
            String bloodPressureStatus,
            String heartRateStatus,
            String overallStatus,
            String message) {

        this.glucoseStatus = glucoseStatus;
        this.bloodPressureStatus = bloodPressureStatus;
        this.heartRateStatus = heartRateStatus;
        this.overallStatus = overallStatus;
        this.message = message;
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

    public void setBloodPressureStatus(String bloodPressureStatus) {
        this.bloodPressureStatus = bloodPressureStatus;
    }


    public String getHeartRateStatus() {
        return heartRateStatus;
    }

    public void setHeartRateStatus(String heartRateStatus) {
        this.heartRateStatus = heartRateStatus;
    }


    public String getOverallStatus() {
        return overallStatus;
    }

    public void setOverallStatus(String overallStatus) {
        this.overallStatus = overallStatus;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}