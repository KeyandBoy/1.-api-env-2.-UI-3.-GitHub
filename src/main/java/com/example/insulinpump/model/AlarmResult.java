package com.example.insulinpump.model;

import java.util.List;

/**
 * AlarmResult
 *
 * 报警系统分析结果。
 *
 * 用于保存当前一次报警检查的结果，
 * 包括：
 *
 * 1. 是否触发报警
 * 2. 报警级别
 * 3. 报警标题
 * 4. 报警详细信息
 * 5. 报警时间
 *
 * 注意：
 * 所有报警规则均为课程教学仿真规则，
 * 不用于真实医疗诊断。
 */
public class AlarmResult {

    /**
     * 是否存在报警。
     *
     * true  = 存在报警
     * false = 当前无报警
     */
    private boolean alarm;

    /**
     * 报警级别。
     *
     * NORMAL   正常
     * WARNING  警告
     * CRITICAL 严重警告
     */
    private String level;

    /**
     * 报警标题。
     */
    private String title;

    /**
     * 所有报警信息。
     *
     * 一次检查可能同时出现多个异常，
     * 因此这里使用 List。
     */
    private List<String> messages;

    /**
     * 报警检查时间。
     */
    private String alarmTime;


    /**
     * 无参构造函数。
     */
    public AlarmResult() {
    }


    /**
     * 完整构造函数。
     */
    public AlarmResult(
            boolean alarm,
            String level,
            String title,
            List<String> messages,
            String alarmTime) {

        this.alarm = alarm;
        this.level = level;
        this.title = title;
        this.messages = messages;
        this.alarmTime = alarmTime;
    }


    // =========================
    // Getter / Setter
    // =========================

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }
}