package com.example.insulinpump.service;

import com.example.insulinpump.model.SensorAnalysisResult;
import org.springframework.stereotype.Service;

/**
 * SensorAnalyzer
 *
 * 模拟生理数据分析模块。
 *
 * 功能：
 *
 * 1. 分析血糖数据
 * 2. 分析血压数据
 * 3. 分析心率数据
 * 4. 生成综合状态
 *
 * 所有阈值仅为课程软件仿真参数，
 * 不用于真实医疗诊断。
 */
@Service
public class SensorAnalyzer {


    /**
     * 分析一组模拟生理数据。
     */
    public SensorAnalysisResult analyze(
            double glucose,
            int systolic,
            int diastolic,
            int heartRate) {


        /*
         * =========================
         * 1. 血糖分析
         * =========================
         */

        String glucoseStatus;

        if (glucose < 4.0) {

            glucoseStatus = "偏低";

        } else if (glucose <= 7.8) {

            glucoseStatus = "正常";

        } else if (glucose <= 11.0) {

            glucoseStatus = "偏高";

        } else {

            glucoseStatus = "明显偏高";
        }


        /*
         * =========================
         * 2. 血压分析
         * =========================
         */

        String bloodPressureStatus;

        if (systolic < 90 || diastolic < 60) {

            bloodPressureStatus = "偏低";

        } else if (
                systolic <= 129 &&
                diastolic <= 84) {

            bloodPressureStatus = "正常";

        } else if (
                systolic <= 139 &&
                diastolic <= 89) {

            bloodPressureStatus = "轻度偏高";

        } else {

            bloodPressureStatus = "偏高";
        }


        /*
         * =========================
         * 3. 心率分析
         * =========================
         */

        String heartRateStatus;

        if (heartRate < 60) {

            heartRateStatus = "偏低";

        } else if (heartRate <= 100) {

            heartRateStatus = "正常";

        } else {

            heartRateStatus = "偏高";
        }


        /*
         * =========================
         * 4. 综合状态判断
         * =========================
         */

        int abnormalCount = 0;


        if (!glucoseStatus.equals("正常")) {
            abnormalCount++;
        }

        if (!bloodPressureStatus.equals("正常")) {
            abnormalCount++;
        }

        if (!heartRateStatus.equals("正常")) {
            abnormalCount++;
        }


        String overallStatus;
        String message;


        if (abnormalCount == 0) {

            overallStatus = "正常";

            message =
                    "当前模拟生理数据未发现明显异常。";

        } else if (abnormalCount == 1) {

            overallStatus = "注意";

            message =
                    "检测到一项模拟生理数据异常，请继续观察。";

        } else {

            overallStatus = "警告";

            message =
                    "检测到多项模拟生理数据异常，系统已进入警告状态。";
        }


        /*
         * 返回完整分析结果。
         */
        return new SensorAnalysisResult(
                glucoseStatus,
                bloodPressureStatus,
                heartRateStatus,
                overallStatus,
                message
        );
    }
}