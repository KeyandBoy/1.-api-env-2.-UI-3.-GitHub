package com.example.insulinpump.model;

/**
 * InsulinDoseResult
 *
 * 仿真胰岛素剂量计算结果。
 *
 * 注意：
 * 本类中的 dose 并不代表真实医疗胰岛素剂量，
 * 只是课程设计中用于验证
 * Sensor -> Controller -> Pump
 * 软件控制流程的仿真控制参数。
 */
public class InsulinDoseResult {

    /**
     * 当前输入的模拟血糖值。
     */
    private double glucose;

    /**
     * 仿真控制量。
     *
     * 单位：
     * SU = Simulation Unit
     *
     * 不是实际胰岛素单位。
     */
    private double simulationDose;

    /**
     * 当前计算状态。
     *
     * 示例：
     * NO_DELIVERY
     * READY
     */
    private String calculationStatus;

    /**
     * 中文说明。
     */
    private String message;

    /**
     * 本次计算读取的近期历史记录数量。
     */
    private int historyRecordCount;

    /**
     * 是否因为近期历史控制量较大而触发仿真限制。
     */
    private boolean historicalDoseLimitApplied;


    /**
     * 无参构造函数。
     */
    public InsulinDoseResult() {
    }


    /**
     * 完整构造函数。
     */
    public InsulinDoseResult(
            double glucose,
            double simulationDose,
            String calculationStatus,
            String message) {

        this.glucose = glucose;
        this.simulationDose = simulationDose;
        this.calculationStatus = calculationStatus;
        this.message = message;
    }


    // =========================
    // Getter / Setter
    // =========================

    public double getGlucose() {
        return glucose;
    }

    public void setGlucose(double glucose) {
        this.glucose = glucose;
    }

    public double getSimulationDose() {
        return simulationDose;
    }

    public void setSimulationDose(double simulationDose) {
        this.simulationDose = simulationDose;
    }

    public String getCalculationStatus() {
        return calculationStatus;
    }

    public void setCalculationStatus(String calculationStatus) {
        this.calculationStatus = calculationStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getHistoryRecordCount() {
        return historyRecordCount;
    }

    public void setHistoryRecordCount(int historyRecordCount) {
        this.historyRecordCount = historyRecordCount;
    }

    public boolean isHistoricalDoseLimitApplied() {
        return historicalDoseLimitApplied;
    }

    public void setHistoricalDoseLimitApplied(
            boolean historicalDoseLimitApplied) {
        this.historicalDoseLimitApplied = historicalDoseLimitApplied;
    }
}
