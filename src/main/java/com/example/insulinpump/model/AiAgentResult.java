package com.example.insulinpump.model;

import java.util.List;

/**
 * AiAgentResult
 *
 * AI Agent 最终分析结果模型。
 *
 * AI Agent 会综合：
 *
 * 1. 当前模拟血糖
 * 2. 当前模拟血压
 * 3. 当前模拟心率
 * 4. Pump 状态
 * 5. Reservoir 状态
 * 6. Alarm 状态
 * 7. 数据库历史记录
 *
 * 最终生成：
 *
 * - 当前状态分析
 * - 生理数据摘要
 * - 历史趋势分析
 * - 异常信息
 * - 教学仿真建议
 *
 * 注意：
 * 本模型仅用于软件工程课程仿真。
 */
public class AiAgentResult {

    /**
     * AI Agent 当前综合状态。
     *
     * 可能值：
     *
     * STABLE
     * ATTENTION
     * ALERT
     */
    private String agentStatus;

    /**
     * 当前生理状态摘要。
     */
    private String physiologicalSummary;

    /**
     * 历史趋势分析。
     */
    private String trendSummary;

    /**
     * Pump 状态分析。
     */
    private String pumpSummary;

    /**
     * Alarm 分析。
     */
    private String alarmSummary;

    /**
     * 当前检测到的异常信息。
     *
     * 可能同时存在多个异常。
     */
    private List<String> abnormalFindings;

    /**
     * AI Agent 生成的教学仿真建议。
     */
    private List<String> suggestions;

    /**
     * AI Agent 使用的历史记录数量。
     */
    private int historyRecordCount;

    /**
     * AI Agent 分析时间。
     */
    private String analysisTime;

    /**
     * DeepSeek 可选生成的简短辅助解释；无 Key 或调用失败时为本地降级文本。
     */
    private String aiExplanation;


    /**
     * 无参构造函数。
     */
    public AiAgentResult() {
    }


    /**
     * 完整构造函数。
     */
    public AiAgentResult(
            String agentStatus,
            String physiologicalSummary,
            String trendSummary,
            String pumpSummary,
            String alarmSummary,
            List<String> abnormalFindings,
            List<String> suggestions,
            int historyRecordCount,
            String analysisTime) {

        this.agentStatus = agentStatus;
        this.physiologicalSummary = physiologicalSummary;
        this.trendSummary = trendSummary;
        this.pumpSummary = pumpSummary;
        this.alarmSummary = alarmSummary;
        this.abnormalFindings = abnormalFindings;
        this.suggestions = suggestions;
        this.historyRecordCount = historyRecordCount;
        this.analysisTime = analysisTime;
    }


    // =========================================================
    // Getter / Setter
    // =========================================================

    public String getAgentStatus() {
        return agentStatus;
    }

    public void setAgentStatus(String agentStatus) {
        this.agentStatus = agentStatus;
    }

    public String getPhysiologicalSummary() {
        return physiologicalSummary;
    }

    public void setPhysiologicalSummary(
            String physiologicalSummary) {

        this.physiologicalSummary =
                physiologicalSummary;
    }

    public String getTrendSummary() {
        return trendSummary;
    }

    public void setTrendSummary(
            String trendSummary) {

        this.trendSummary =
                trendSummary;
    }

    public String getPumpSummary() {
        return pumpSummary;
    }

    public void setPumpSummary(
            String pumpSummary) {

        this.pumpSummary =
                pumpSummary;
    }

    public String getAlarmSummary() {
        return alarmSummary;
    }

    public void setAlarmSummary(
            String alarmSummary) {

        this.alarmSummary =
                alarmSummary;
    }

    public List<String> getAbnormalFindings() {
        return abnormalFindings;
    }

    public void setAbnormalFindings(
            List<String> abnormalFindings) {

        this.abnormalFindings =
                abnormalFindings;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(
            List<String> suggestions) {

        this.suggestions =
                suggestions;
    }

    public int getHistoryRecordCount() {
        return historyRecordCount;
    }

    public void setHistoryRecordCount(
            int historyRecordCount) {

        this.historyRecordCount =
                historyRecordCount;
    }

    public String getAnalysisTime() {
        return analysisTime;
    }

    public void setAnalysisTime(
            String analysisTime) {

        this.analysisTime =
                analysisTime;
    }

    public String getAiExplanation() {
        return aiExplanation;
    }

    public void setAiExplanation(String aiExplanation) {
        this.aiExplanation = aiExplanation;
    }
}
