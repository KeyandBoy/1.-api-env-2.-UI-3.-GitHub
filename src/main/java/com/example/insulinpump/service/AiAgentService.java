package com.example.insulinpump.service;

import com.example.insulinpump.model.AiAgentResult;
import com.example.insulinpump.model.AlarmResult;
import com.example.insulinpump.model.SensorAnalysisResult;
import com.example.insulinpump.record.SimulationRecord;
import com.example.insulinpump.repository.SimulationRecordRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * AiAgentService
 *
 * 智能胰岛素泵仿真系统 AI Agent。
 *
 * ============================================
 * AI Agent 输入
 * ============================================
 *
 * 1. 当前血糖
 * 2. 当前收缩压
 * 3. 当前舒张压
 * 4. 当前心率
 * 5. Pump Reservoir
 * 6. Pump Status
 * 7. H2 历史记录
 *
 * ============================================
 * AI Agent 推理过程
 * ============================================
 *
 * SensorAnalyzer
 *      ↓
 * AlarmService
 *      ↓
 * Historical Trend Analysis
 *      ↓
 * Rule Reasoning
 *      ↓
 * Summary / Warning / Suggestion
 *
 * ============================================
 * 重要说明
 * ============================================
 *
 * 本 AI Agent 只用于课程软件仿真。
 *
 * 所有阈值、分析和建议都不属于
 * 真实医疗诊断、治疗或胰岛素给药建议。
 */
@Service
public class AiAgentService {

    /**
     * 复用现有 SensorAnalyzer。
     *
     * 避免 AI Agent 和系统原来的
     * 生理数据判断规则产生不一致。
     */
    private final SensorAnalyzer sensorAnalyzer;

    /**
     * 复用现有 AlarmService。
     */
    private final AlarmService alarmService;

    /**
     * 查询历史数据库。
     */
    private final SimulationRecordRepository repository;

    /** 可选的 DeepSeek 文字解释服务。 */
    private final DeepSeekService deepSeekService;


    /**
     * 构造函数注入。
     */
    public AiAgentService(
            SensorAnalyzer sensorAnalyzer,
            AlarmService alarmService,
            SimulationRecordRepository repository,
            DeepSeekService deepSeekService) {

        this.sensorAnalyzer =
                sensorAnalyzer;

        this.alarmService =
                alarmService;

        this.repository =
                repository;

        this.deepSeekService =
                deepSeekService;
    }


    /**
     * 执行 AI Agent 综合分析。
     *
     * @param glucose   当前模拟血糖
     * @param systolic  当前模拟收缩压
     * @param diastolic 当前模拟舒张压
     * @param heartRate 当前模拟心率
     * @param reservoir 当前模拟储液量
     * @param pumpStatus 当前 Pump 状态
     *
     * @return AI Agent 分析结果
     */
    public AiAgentResult analyze(
            double glucose,
            int systolic,
            int diastolic,
            int heartRate,
            double reservoir,
            String pumpStatus) {


        // =====================================================
        // 1. 使用 SensorAnalyzer 分析当前数据
        // =====================================================

        SensorAnalysisResult sensorResult =
                sensorAnalyzer.analyze(
                        glucose,
                        systolic,
                        diastolic,
                        heartRate
                );


        // =====================================================
        // 2. 使用 AlarmService 分析当前报警
        // =====================================================

        AlarmResult alarmResult =
                alarmService.checkAlarm(
                        glucose,
                        systolic,
                        diastolic,
                        heartRate,
                        reservoir,
                        pumpStatus
                );


        // =====================================================
        // 3. 查询历史数据库
        // =====================================================

        List<SimulationRecord> records =
                repository.findAllByOrderByIdDesc();


        /*
         * AI Agent 最多使用最近 5 条记录。
         *
         * 原因：
         * 这里只做简单的近期趋势判断，
         * 不需要遍历全部数据库。
         */
        int historyCount =
                Math.min(
                        records.size(),
                        5
                );


        // =====================================================
        // 4. 生成当前生理状态摘要
        // =====================================================

        String physiologicalSummary =
                buildPhysiologicalSummary(
                        glucose,
                        systolic,
                        diastolic,
                        heartRate,
                        sensorResult
                );


        // =====================================================
        // 5. 分析历史趋势
        // =====================================================

        String trendSummary =
                buildTrendSummary(
                        records,
                        historyCount
                );


        // =====================================================
        // 6. 分析 Pump
        // =====================================================

        String pumpSummary =
                buildPumpSummary(
                        pumpStatus,
                        reservoir
                );


        // =====================================================
        // 7. 分析 Alarm
        // =====================================================

        String alarmSummary =
                buildAlarmSummary(
                        alarmResult
                );


        // =====================================================
        // 8. 收集异常信息
        // =====================================================

        List<String> abnormalFindings =
                buildAbnormalFindings(
                        sensorResult,
                        alarmResult
                );


        // =====================================================
        // 9. 生成教学仿真建议
        // =====================================================

        List<String> suggestions =
                buildSuggestions(
                        sensorResult,
                        alarmResult,
                        trendSummary,
                        pumpStatus,
                        reservoir
                );


        // =====================================================
        // 10. 决定 AI Agent 综合状态
        // =====================================================

        String agentStatus;


        /*
         * CRITICAL：
         * AI Agent 进入 ALERT。
         */
        if ("CRITICAL".equalsIgnoreCase(
                alarmResult.getLevel())) {

            agentStatus =
                    "ALERT";

        /*
         * WARNING：
         * AI Agent 进入 ATTENTION。
         */
        } else if ("WARNING".equalsIgnoreCase(
                alarmResult.getLevel())) {

            agentStatus =
                    "ATTENTION";

        /*
         * 没有报警：
         * STABLE。
         */
        } else {

            agentStatus =
                    "STABLE";
        }

        /*
         * 外部 AI 只负责异常文字解释，正常状态不发起网络请求。
         * 即使没有 Key 或服务不可用，DeepSeekService 也会返回本地降级文本。
         */
        String aiExplanation = "当前没有触发外部 AI 解释调用。";
        if (!"NORMAL".equalsIgnoreCase(alarmResult.getLevel())) {
            aiExplanation = deepSeekService.explain(
                    glucose,
                    systolic,
                    diastolic,
                    heartRate,
                    reservoir,
                    pumpStatus,
                    alarmResult
            );
        }


        // =====================================================
        // 11. 生成分析时间
        // =====================================================

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd HH:mm:ss"
                );

        String analysisTime =
                LocalDateTime.now()
                        .format(formatter);


        // =====================================================
        // 12. 返回最终 Agent 结果
        // =====================================================

        AiAgentResult result = new AiAgentResult(
                agentStatus,
                physiologicalSummary,
                trendSummary,
                pumpSummary,
                alarmSummary,
                abnormalFindings,
                suggestions,
                historyCount,
                analysisTime
        );
        result.setAiExplanation(aiExplanation);
        return result;
    }


    /**
     * 构建当前生理状态摘要。
     */
    private String buildPhysiologicalSummary(
            double glucose,
            int systolic,
            int diastolic,
            int heartRate,
            SensorAnalysisResult result) {

        return "当前模拟血糖为 "
                + glucose
                + " mmol/L（"
                + result.getGlucoseStatus()
                + "），模拟血压为 "
                + systolic
                + "/"
                + diastolic
                + " mmHg（"
                + result.getBloodPressureStatus()
                + "），模拟心率为 "
                + heartRate
                + " BPM（"
                + result.getHeartRateStatus()
                + "）。综合状态："
                + result.getOverallStatus()
                + "。";
    }


    /**
     * 分析最近历史血糖变化趋势。
     *
     * 为了保持逻辑简单和可解释，
     * 我们比较最近记录与较早记录。
     */
    private String buildTrendSummary(
            List<SimulationRecord> records,
            int historyCount) {

        /*
         * 没有历史数据。
         */
        if (historyCount == 0) {

            return "当前数据库暂无历史记录，暂时无法进行趋势分析。";
        }


        /*
         * 只有一条历史数据。
         */
        if (historyCount == 1) {

            return "当前仅有 1 条历史记录，数据量不足以形成明显趋势。";
        }


        /*
         * repository 返回顺序：
         *
         * 最新
         * ↓
         * 最旧
         *
         * records.get(0)
         * 就是最近一条。
         */
        SimulationRecord newest =
                records.get(0);


        /*
         * 取最近 N 条中的最旧一条。
         */
        SimulationRecord oldest =
                records.get(
                        historyCount - 1
                );


        double glucoseDifference =
                newest.getGlucose()
                        -
                oldest.getGlucose();


        /*
         * 近期血糖差异比较小。
         */
        if (Math.abs(
                glucoseDifference) < 0.5) {

            return "最近 "
                    + historyCount
                    + " 条历史记录中的模拟血糖整体波动较小，近期趋势相对平稳。";
        }


        /*
         * 趋势上升。
         */
        if (glucoseDifference > 0) {

            return "最近 "
                    + historyCount
                    + " 条历史记录显示模拟血糖总体呈上升趋势，请在后续仿真中继续观察变化。";
        }


        /*
         * 趋势下降。
         */
        return "最近 "
                + historyCount
                + " 条历史记录显示模拟血糖总体呈下降趋势，请结合后续采集数据继续观察。";
    }


    /**
     * 生成 Pump 状态摘要。
     */
    private String buildPumpSummary(
            String pumpStatus,
            double reservoir) {

        return "当前模拟 Pump 状态为 "
                + pumpStatus
                + "，Reservoir 剩余 "
                + String.format(
                        "%.2f",
                        reservoir
                )
                + " SU。";
    }


    /**
     * 生成报警摘要。
     */
    private String buildAlarmSummary(
            AlarmResult alarmResult) {

        if ("NORMAL".equalsIgnoreCase(
                alarmResult.getLevel())) {

            return "当前 Alarm Level 为 NORMAL，未触发课程仿真报警。";
        }


        if ("WARNING".equalsIgnoreCase(
                alarmResult.getLevel())) {

            return "当前 Alarm Level 为 WARNING，AI Agent 检测到需要关注的模拟异常。";
        }


        return "当前 Alarm Level 为 CRITICAL，AI Agent 检测到严重课程仿真报警。";
    }


    /**
     * 收集当前所有异常信息。
     */
    private List<String> buildAbnormalFindings(
            SensorAnalysisResult sensorResult,
            AlarmResult alarmResult) {

        List<String> findings =
                new ArrayList<>();


        /*
         * 血糖异常。
         */
        if (!"正常".equals(
                sensorResult.getGlucoseStatus())) {

            findings.add(
                    "血糖状态："
                    +
                    sensorResult.getGlucoseStatus()
            );
        }


        /*
         * 血压异常。
         */
        if (!"正常".equals(
                sensorResult.getBloodPressureStatus())) {

            findings.add(
                    "血压状态："
                    +
                    sensorResult.getBloodPressureStatus()
            );
        }


        /*
         * 心率异常。
         */
        if (!"正常".equals(
                sensorResult.getHeartRateStatus())) {

            findings.add(
                    "心率状态："
                    +
                    sensorResult.getHeartRateStatus()
            );
        }


        /*
         * AlarmService 还可能发现：
         *
         * Reservoir
         * Pump ERROR
         *
         * 等设备异常。
         */
        if (alarmResult.isAlarm()) {

            for (String message :
                    alarmResult.getMessages()) {

                /*
                 * 避免完全相同内容重复出现。
                 */
                if (!findings.contains(
                        message)) {

                    findings.add(
                            message
                    );
                }
            }
        }


        /*
         * 如果没有异常，
         * 给页面一个明确结果。
         */
        if (findings.isEmpty()) {

            findings.add(
                    "当前未检测到课程仿真规则定义的明显异常。"
            );
        }


        return findings;
    }


    /**
     * 生成 AI Agent 教学仿真建议。
     *
     * 注意：
     *
     * 这里只生成软件系统中的
     * “继续监测、检查设备、关注趋势”
     * 等教学建议。
     *
     * 不提供真实诊疗方案。
     */
    private List<String> buildSuggestions(
            SensorAnalysisResult sensorResult,
            AlarmResult alarmResult,
            String trendSummary,
            String pumpStatus,
            double reservoir) {

        List<String> suggestions =
                new ArrayList<>();


        /*
         * 1. 当前完全正常。
         */
        if ("NORMAL".equalsIgnoreCase(
                alarmResult.getLevel())) {

            suggestions.add(
                    "当前仿真状态较稳定，可以继续进行周期性模拟数据采集。"
            );
        }


        /*
         * 2. 出现普通报警。
         */
        if ("WARNING".equalsIgnoreCase(
                alarmResult.getLevel())) {

            suggestions.add(
                    "当前存在需要关注的模拟异常，建议继续采集后续数据并观察变化。"
            );
        }


        /*
         * 3. 出现严重报警。
         */
        if ("CRITICAL".equalsIgnoreCase(
                alarmResult.getLevel())) {

            suggestions.add(
                    "当前触发严重课程仿真报警，建议优先检查异常数据来源和系统设备状态。"
            );
        }


        /*
         * 4. 血糖异常。
         */
        if (!"正常".equals(
                sensorResult.getGlucoseStatus())) {

            suggestions.add(
                    "模拟血糖状态存在异常，建议在下一轮仿真中继续进行数据监测和趋势比较。"
            );
        }


        /*
         * 5. 血压异常。
         */
        if (!"正常".equals(
                sensorResult.getBloodPressureStatus())) {

            suggestions.add(
                    "模拟血压状态存在异常，建议继续观察后续采集结果是否持续异常。"
            );
        }


        /*
         * 6. 心率异常。
         */
        if (!"正常".equals(
                sensorResult.getHeartRateStatus())) {

            suggestions.add(
                    "模拟心率状态存在异常，建议结合后续模拟数据判断是否为持续性变化。"
            );
        }


        /*
         * 7. Reservoir 较低。
         */
        if (reservoir <= 20.0) {

            suggestions.add(
                    "模拟 Reservoir 已处于较低水平，建议在后续系统仿真前检查 Pump 储液状态。"
            );
        }


        /*
         * 8. Pump ERROR。
         */
        if ("ERROR".equalsIgnoreCase(
                pumpStatus)) {

            suggestions.add(
                    "模拟 Pump 当前处于 ERROR 状态，应先检查设备仿真状态后再继续执行控制流程。"
            );
        }


        /*
         * 9. 历史趋势发生明显变化。
         */
        if (trendSummary.contains(
                "上升趋势")
                ||
                trendSummary.contains(
                        "下降趋势")) {

            suggestions.add(
                    "历史数据已经出现趋势变化，建议结合 ECharts 可视化继续进行对比观察。"
            );
        }


        /*
         * 理论上列表不会为空，
         * 但仍保留兜底。
         */
        if (suggestions.isEmpty()) {

            suggestions.add(
                    "建议继续进行课程仿真监测并记录后续数据。"
            );
        }


        return suggestions;
    }
}
