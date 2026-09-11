package com.example.insulinpump.model;

/**
 * PumpCommand
 *
 * 胰岛素泵控制命令模型。
 *
 * 这个类用于表示 Controller
 * 向模拟 Pump 发送的控制指令。
 *
 * 目前支持的命令：
 *
 * DELIVER
 * 表示执行一次模拟输注。
 *
 * STOP
 * 表示停止 Pump。
 *
 * 注意：
 * simulationDose 使用 SU（Simulation Unit），
 * 不是实际医疗胰岛素单位。
 */
public class PumpCommand {

    /**
     * 控制命令。
     *
     * 例如：
     * DELIVER
     * STOP
     */
    private String command;

    /**
     * 本次仿真控制量。
     *
     * 单位：
     * SU = Simulation Unit
     */
    private double simulationDose;


    /**
     * 无参构造函数。
     */
    public PumpCommand() {
    }


    /**
     * 完整构造函数。
     */
    public PumpCommand(
            String command,
            double simulationDose) {

        this.command = command;
        this.simulationDose = simulationDose;
    }


    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public double getSimulationDose() {
        return simulationDose;
    }

    public void setSimulationDose(double simulationDose) {
        this.simulationDose = simulationDose;
    }
}