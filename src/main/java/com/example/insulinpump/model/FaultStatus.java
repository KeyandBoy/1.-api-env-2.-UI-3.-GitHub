package com.example.insulinpump.model;

/**
 * FaultStatus
 *
 * 系统故障仿真状态模型。
 *
 * 用于描述当前软件仿真环境中
 * 哪些设备被人为设置为故障状态。
 *
 * 本模块仅用于软件工程课程中的
 * 异常处理和可靠性测试。
 */
public class FaultStatus {

    /**
     * 血糖传感器是否故障。
     */
    private boolean glucoseSensorFailure;

    /**
     * 血压传感器是否故障。
     */
    private boolean bloodPressureSensorFailure;

    /**
     * 心率传感器是否故障。
     */
    private boolean heartRateSensorFailure;

    /**
     * Pump 是否故障。
     */
    private boolean pumpFailure;

    /**
     * 模拟系统电源是否故障。
     */
    private boolean powerFailure;


    /**
     * 无参构造函数。
     */
    public FaultStatus() {
    }


    /**
     * 完整构造函数。
     */
    public FaultStatus(
            boolean glucoseSensorFailure,
            boolean bloodPressureSensorFailure,
            boolean heartRateSensorFailure,
            boolean pumpFailure,
            boolean powerFailure) {

        this.glucoseSensorFailure =
                glucoseSensorFailure;

        this.bloodPressureSensorFailure =
                bloodPressureSensorFailure;

        this.heartRateSensorFailure =
                heartRateSensorFailure;

        this.pumpFailure =
                pumpFailure;

        this.powerFailure =
                powerFailure;
    }


    public boolean isGlucoseSensorFailure() {
        return glucoseSensorFailure;
    }

    public void setGlucoseSensorFailure(
            boolean glucoseSensorFailure) {

        this.glucoseSensorFailure =
                glucoseSensorFailure;
    }


    public boolean isBloodPressureSensorFailure() {
        return bloodPressureSensorFailure;
    }

    public void setBloodPressureSensorFailure(
            boolean bloodPressureSensorFailure) {

        this.bloodPressureSensorFailure =
                bloodPressureSensorFailure;
    }


    public boolean isHeartRateSensorFailure() {
        return heartRateSensorFailure;
    }

    public void setHeartRateSensorFailure(
            boolean heartRateSensorFailure) {

        this.heartRateSensorFailure =
                heartRateSensorFailure;
    }


    public boolean isPumpFailure() {
        return pumpFailure;
    }

    public void setPumpFailure(
            boolean pumpFailure) {

        this.pumpFailure =
                pumpFailure;
    }


    public boolean isPowerFailure() {
        return powerFailure;
    }

    public void setPowerFailure(
            boolean powerFailure) {

        this.powerFailure =
                powerFailure;
    }
}