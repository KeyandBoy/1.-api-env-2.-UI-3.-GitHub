package com.example.insulinpump.model;

/**
 * PumpStatus
 *
 * 模拟胰岛素泵当前状态。
 *
 * 前端以后会读取这个对象，
 * 显示 Pump 当前：
 *
 * 1. 工作状态
 * 2. 储液量
 * 3. 最近一次仿真控制量
 * 4. 状态说明
 */
public class PumpStatus {

    /**
     * Pump 状态。
     *
     * READY
     * RUNNING
     * STOPPED
     * ERROR
     */
    private String status;

    /**
     * 当前模拟储液量。
     *
     * 单位：
     * SU
     */
    private double reservoir;

    /**
     * 最近一次执行的仿真控制量。
     */
    private double lastDose;

    /**
     * 状态说明。
     */
    private String message;


    public PumpStatus() {
    }


    public PumpStatus(
            String status,
            double reservoir,
            double lastDose,
            String message) {

        this.status = status;
        this.reservoir = reservoir;
        this.lastDose = lastDose;
        this.message = message;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getReservoir() {
        return reservoir;
    }

    public void setReservoir(double reservoir) {
        this.reservoir = reservoir;
    }

    public double getLastDose() {
        return lastDose;
    }

    public void setLastDose(double lastDose) {
        this.lastDose = lastDose;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}