package com.example.insulinpump.service;

import com.example.insulinpump.model.FaultStatus;

import org.springframework.stereotype.Service;

/**
 * FaultSimulationService
 *
 * 故障仿真控制中心。
 *
 * 可以人为控制：
 *
 * 1. Glucose Sensor Failure
 * 2. Blood Pressure Sensor Failure
 * 3. Heart Rate Sensor Failure
 * 4. Pump Failure
 * 5. Power Failure
 *
 * 用于测试系统在异常情况下
 * 是否能够安全处理错误。
 */
@Service
public class FaultSimulationService {

    /*
     * volatile 用于保证多个 HTTP 请求
     * 访问这些状态变量时能够及时看到最新值。
     */

    private volatile boolean glucoseSensorFailure =
            false;

    private volatile boolean bloodPressureSensorFailure =
            false;

    private volatile boolean heartRateSensorFailure =
            false;

    private volatile boolean pumpFailure =
            false;

    private volatile boolean powerFailure =
            false;


    /**
     * 获取所有故障状态。
     */
    public FaultStatus getStatus() {

        return new FaultStatus(

                glucoseSensorFailure,

                bloodPressureSensorFailure,

                heartRateSensorFailure,

                pumpFailure,

                powerFailure
        );
    }


    /**
     * 设置血糖传感器故障。
     */
    public void setGlucoseSensorFailure(
            boolean enabled) {

        glucoseSensorFailure =
                enabled;
    }


    /**
     * 设置血压传感器故障。
     */
    public void setBloodPressureSensorFailure(
            boolean enabled) {

        bloodPressureSensorFailure =
                enabled;
    }


    /**
     * 设置心率传感器故障。
     */
    public void setHeartRateSensorFailure(
            boolean enabled) {

        heartRateSensorFailure =
                enabled;
    }


    /**
     * 设置 Pump 故障。
     */
    public void setPumpFailure(
            boolean enabled) {

        pumpFailure =
                enabled;
    }


    /**
     * 设置模拟电源故障。
     */
    public void setPowerFailure(
            boolean enabled) {

        powerFailure =
                enabled;
    }


    /**
     * 一键恢复所有设备。
     */
    public void resetAll() {

        glucoseSensorFailure =
                false;

        bloodPressureSensorFailure =
                false;

        heartRateSensorFailure =
                false;

        pumpFailure =
                false;

        powerFailure =
                false;
    }


    public boolean isGlucoseSensorFailure() {
        return glucoseSensorFailure;
    }


    public boolean isBloodPressureSensorFailure() {
        return bloodPressureSensorFailure;
    }


    public boolean isHeartRateSensorFailure() {
        return heartRateSensorFailure;
    }


    public boolean isPumpFailure() {
        return pumpFailure;
    }


    public boolean isPowerFailure() {
        return powerFailure;
    }
}