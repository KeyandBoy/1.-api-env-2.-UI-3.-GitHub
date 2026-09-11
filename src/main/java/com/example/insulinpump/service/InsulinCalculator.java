package com.example.insulinpump.service;

import com.example.insulinpump.model.InsulinDoseResult;
import com.example.insulinpump.record.SimulationRecord;
import com.example.insulinpump.repository.SimulationRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * InsulinCalculator
 *
 * 仿真胰岛素控制量计算模块。
 *
 * ==============================
 * 重要说明
 * ==============================
 *
 * 本算法只用于软件工程课程中的
 * 控制流程仿真。
 *
 * simulationDose 是 Simulation Unit，
 * 不是真实医疗胰岛素剂量。
 *
 * 不能用于患者治疗或真实给药。
 */
@Service
public class InsulinCalculator {

    private static final int HISTORY_LIMIT = 5;

    /*
     * 该阈值只是课程仿真中的“近期已有较大控制量”判断线，
     * 不代表真实医疗给药规则。
     */
    private static final double HISTORICAL_DOSE_LIMIT = 4.0;

    private final SimulationRecordRepository repository;

    public InsulinCalculator(
            SimulationRecordRepository repository) {
        this.repository = repository;
    }

    /**
     * 课程仿真目标参数。
     *
     * 这里只是软件模拟阈值，
     * 不是临床治疗目标。
     */
    private static final double SIMULATION_TARGET = 6.0;


    /**
     * 仿真比例系数。
     *
     * 仅用于产生可观察的模拟控制量。
     */
    private static final double SIMULATION_FACTOR = 0.5;


    /**
     * 计算仿真控制量。
     *
     * 仿真公式：
     *
     * 当 glucose <= simulationTarget：
     *
     * simulationDose = 0
     *
     * 否则：
     *
     * simulationDose
     * =
     * (glucose - simulationTarget)
     * × simulationFactor
     *
     * @param glucose 模拟血糖读数
     *
     * @return 仿真计算结果
     */
    public InsulinDoseResult calculate(
            double glucose) {

        /*
         * 读取最近记录，使历史数据真正参与 Controller 决策。
         * 仓库按 ID 倒序返回，因此前五条就是近期记录。
         */
        List<SimulationRecord> records =
                repository.findAllByOrderByIdDesc();
        int historyRecordCount =
                Math.min(records.size(), HISTORY_LIMIT);
        boolean historicalDoseLimitApplied = false;

        for (int i = 0; i < historyRecordCount; i++) {
            if (records.get(i).getSimulationDose()
                    >= HISTORICAL_DOSE_LIMIT) {
                historicalDoseLimitApplied = true;
                break;
            }
        }

        /*
         * 第一种情况：
         * 输入数据非法。
         */
        if (glucose <= 0) {

            return buildResult(
                    glucose,
                    0,
                    "INVALID_DATA",
                    "传感器数据无效，禁止生成泵控制参数。",
                    historyRecordCount,
                    false
            );
        }


        /*
         * 第二种情况：
         * 当前仿真条件下不产生控制量。
         */
        if (glucose <= SIMULATION_TARGET) {

            return buildResult(
                    glucose,
                    0,
                    "NO_DELIVERY",
                    "当前仿真条件下无需生成泵输注控制量。",
                    historyRecordCount,
                    false
            );
        }

        /*
         * 如果近期已经执行过较大的仿真控制量，
         * 本次仅返回 HISTORY_LIMIT，不再生成新的控制量。
         * 这是可解释的软件仿真安全机制，不是医疗算法。
         */
        if (historicalDoseLimitApplied) {
            return buildResult(
                    glucose,
                    0,
                    "HISTORY_LIMIT",
                    "近期历史记录已有较大的仿真控制量，本次按课程规则暂缓生成控制量。",
                    historyRecordCount,
                    true
            );
        }


        /*
         * 第三种情况：
         * 生成仿真控制量。
         *
         * 注意：
         * 这里只是在模拟 Controller
         * 根据 Sensor 数据产生一个数值。
         */
        double simulationDose =
                (glucose - SIMULATION_TARGET)
                        * SIMULATION_FACTOR;


        /*
         * 保留两位小数。
         */
        simulationDose =
                Math.round(
                        simulationDose * 100.0
                ) / 100.0;


        /*
         * 增加一个软件层面的最大值限制。
         *
         * 目的不是模拟真实医疗剂量，
         * 而是避免仿真变量无限增大。
         */
        double simulationMaximum = 5.0;

        if (simulationDose > simulationMaximum) {

            simulationDose =
                    simulationMaximum;
        }


        return buildResult(
                glucose,
                simulationDose,
                "READY",
                "已生成课程仿真泵控制量，等待 Pump Controller 处理。",
                historyRecordCount,
                false
        );
    }

    /**
     * 统一构造结果，明确返回历史数据是否参与了本次判断。
     */
    private InsulinDoseResult buildResult(
            double glucose,
            double simulationDose,
            String status,
            String message,
            int historyRecordCount,
            boolean historicalDoseLimitApplied) {
        InsulinDoseResult result = new InsulinDoseResult(
                glucose, simulationDose, status, message);
        result.setHistoryRecordCount(historyRecordCount);
        result.setHistoricalDoseLimitApplied(
                historicalDoseLimitApplied);
        return result;
    }
}
