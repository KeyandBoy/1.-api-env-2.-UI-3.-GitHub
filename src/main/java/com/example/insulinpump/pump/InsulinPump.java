package com.example.insulinpump.pump;

import com.example.insulinpump.model.PumpStatus;
import com.example.insulinpump.service.FaultSimulationService;

import org.springframework.stereotype.Component;

/**
 * InsulinPump
 *
 * 软件模拟 Pump。
 *
 * 负责模拟：
 *
 * READY
 * RUNNING
 * STOPPED
 * ERROR
 *
 * 以及 Reservoir 的变化。
 *
 * 注意：
 * SU = Simulation Unit。
 * 不是真实胰岛素给药单位。
 */
@Component
public class InsulinPump {

    /**
     * 当前 Pump 状态。
     */
    private String status =
            "READY";


    /**
     * 模拟 Reservoir。
     */
    private double reservoir =
            100.0;


    /**
     * 最近一次已执行模拟控制量。
     */
    private double lastDose =
            0.0;


    /**
     * 故障模拟服务。
     */
    private final FaultSimulationService faultService;


    /**
     * 构造函数注入。
     */
    public InsulinPump(
            FaultSimulationService faultService) {

        this.faultService =
                faultService;
    }


    /**
     * 执行模拟输注。
     */
    public synchronized PumpStatus deliver(
            double simulationDose) {


        // =====================================================
        // 1. Power Failure
        // =====================================================

        if (faultService
                .isPowerFailure()) {

            status =
                    "ERROR";

            return new PumpStatus(

                    status,

                    reservoir,

                    lastDose,

                    "模拟系统 Power Failure，Pump 禁止执行控制命令。"
            );
        }


        // =====================================================
        // 2. Pump Failure
        // =====================================================

        if (faultService
                .isPumpFailure()) {

            status =
                    "ERROR";

            return new PumpStatus(

                    status,

                    reservoir,

                    lastDose,

                    "Pump Failure：模拟 Pump 当前故障，控制命令已被阻止。"
            );
        }


        // =====================================================
        // 3. 非法控制量检查
        // =====================================================

        if (simulationDose <= 0) {

            return new PumpStatus(

                    status,

                    reservoir,

                    lastDose,

                    "仿真控制量必须大于 0，Pump 未执行。"
            );
        }


        // =====================================================
        // 4. NaN / Infinity 防御
        // =====================================================

        if (Double.isNaN(
                simulationDose)
                ||
                Double.isInfinite(
                        simulationDose)) {

            status =
                    "ERROR";

            return new PumpStatus(

                    status,

                    reservoir,

                    lastDose,

                    "检测到非法仿真控制参数，Pump 已拒绝执行。"
            );
        }


        // =====================================================
        // 5. 防止异常过大的控制参数
        // =====================================================

        /*
         * 这是软件课程仿真中的
         * 输入边界保护。
         *
         * 并非真实医疗剂量限制。
         */
        if (simulationDose > 5.0) {

            status =
                    "ERROR";

            return new PumpStatus(

                    status,

                    reservoir,

                    lastDose,

                    "仿真控制参数超过软件允许范围，Pump 已拒绝执行。"
            );
        }


        // =====================================================
        // 6. Reservoir 检查
        // =====================================================

        if (reservoir
                <
                simulationDose) {

            status =
                    "ERROR";

            return new PumpStatus(

                    status,

                    reservoir,

                    lastDose,

                    "模拟 Reservoir 不足，Pump 禁止执行。"
            );
        }


        // =====================================================
        // 7. 正常模拟执行
        // =====================================================

        status =
                "RUNNING";


        reservoir =
                reservoir
                -
                simulationDose;


        /*
         * 保留两位小数。
         */
        reservoir =
                Math.round(
                        reservoir
                        *
                        100.0
                )
                /
                100.0;


        lastDose =
                simulationDose;


        return new PumpStatus(

                status,

                reservoir,

                lastDose,

                "模拟 Pump 已执行本次控制命令。"
        );
    }


    /**
     * 停止 Pump。
     */
    public synchronized PumpStatus stop() {

        status =
                "STOPPED";

        return new PumpStatus(

                status,

                reservoir,

                lastDose,

                "模拟 Pump 已停止。"
        );
    }


    /**
     * 获取 Pump 状态。
     */
    public synchronized PumpStatus getStatus() {

        /*
         * 如果当前故障模拟器表示 Pump
         * 或 Power 已发生故障，
         * 查询状态时也应该显示 ERROR。
         */
        if (
            faultService.isPumpFailure()
            ||
            faultService.isPowerFailure()
        ) {

            status =
                    "ERROR";
        }


        return new PumpStatus(

                status,

                reservoir,

                lastDose,

                "当前模拟 Pump 状态。"
        );
    }


    /**
     * 重置 Pump。
     *
     * 注意：
     * reset Pump 本身并不会关闭
     * FaultSimulationService 中的故障。
     *
     * 如果故障开关仍然开启，
     * 后续查询依然会得到 ERROR。
     */
    public synchronized PumpStatus reset() {

        status =
                "READY";

        reservoir =
                100.0;

        lastDose =
                0.0;


        return new PumpStatus(

                status,

                reservoir,

                lastDose,

                "模拟 Pump 已重置。"
        );
    }
}