package com.example.insulinpump.service;

import com.example.insulinpump.model.AlarmResult;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * AlarmService
 *
 * 智能胰岛素泵仿真系统报警服务。
 *
 * 负责检查：
 *
 * 1. 模拟血糖数据
 * 2. 模拟血压数据
 * 3. 模拟心率数据
 * 4. Pump 状态
 * 5. Pump 模拟储液量
 *
 * 并最终生成：
 *
 * NORMAL
 * WARNING
 * CRITICAL
 *
 * 三种报警级别。
 *
 * ==============================
 * 重要说明
 * ==============================
 *
 * 本系统中的全部判断阈值均为
 * 软件工程课程教学仿真参数。
 *
 * 不构成真实医疗诊断标准。
 */
@Service
public class AlarmService {

    /**
     * 执行一次完整报警检查。
     *
     * @param glucose   模拟血糖
     * @param systolic  收缩压
     * @param diastolic 舒张压
     * @param heartRate 心率
     * @param reservoir Pump 模拟储液量
     * @param pumpStatus Pump 当前状态
     *
     * @return 报警检查结果
     */
    public AlarmResult checkAlarm(
            double glucose,
            int systolic,
            int diastolic,
            int heartRate,
            double reservoir,
            String pumpStatus) {

        /*
         * 保存本次检测得到的所有报警信息。
         */
        List<String> messages =
                new ArrayList<>();


        /*
         * 是否发现严重报警。
         *
         * 只要出现一个严重报警，
         * 最终级别就设置为 CRITICAL。
         */
        boolean critical = false;


        // =====================================
        // 1. 检查血糖
        // =====================================

        /*
         * 模拟规则：
         *
         * 小于 4.0：
         * WARNING
         *
         * 大于 11.0：
         * CRITICAL
         *
         * 仅作为课程仿真阈值。
         */
        if (glucose < 4.0) {

            messages.add(
                    "模拟血糖值偏低，请持续观察。"
            );

        } else if (glucose > 11.0) {

            messages.add(
                    "模拟血糖值明显偏高，触发严重报警。"
            );

            critical = true;
        }


        // =====================================
        // 2. 检查血压
        // =====================================

        /*
         * 仿真规则：
         *
         * 收缩压 >= 140
         * 或
         * 舒张压 >= 90
         *
         * 触发 WARNING。
         */
        if (systolic >= 140
                || diastolic >= 90) {

            messages.add(
                    "模拟血压数据偏高。"
            );
        }


        /*
         * 仿真低血压检查。
         */
        if (systolic < 90
                || diastolic < 60) {

            messages.add(
                    "模拟血压数据偏低。"
            );
        }


        // =====================================
        // 3. 检查心率
        // =====================================

        /*
         * 心率低于 60
         * 或高于 100，
         * 触发 WARNING。
         */
        if (heartRate < 60) {

            messages.add(
                    "模拟心率数据偏低。"
            );

        } else if (heartRate > 100) {

            messages.add(
                    "模拟心率数据偏高。"
            );
        }


        // =====================================
        // 4. 检查 Pump 储液量
        // =====================================

        /*
         * reservoir <= 5：
         * CRITICAL
         *
         * reservoir <= 20：
         * WARNING
         */
        if (reservoir <= 5.0) {

            messages.add(
                    "模拟 Pump 储液量严重不足。"
            );

            critical = true;

        } else if (reservoir <= 20.0) {

            messages.add(
                    "模拟 Pump 储液量较低，请准备补充。"
            );
        }


        // =====================================
        // 5. 检查 Pump 状态
        // =====================================

        /*
         * 如果 Pump 自身进入 ERROR，
         * 应直接进入严重报警状态。
         */
        if ("ERROR".equalsIgnoreCase(
                pumpStatus)) {

            messages.add(
                    "模拟 Pump 检测到设备错误。"
            );

            critical = true;
        }


        // =====================================
        // 6. 根据结果生成最终报警级别
        // =====================================

        boolean alarm =
                !messages.isEmpty();

        String level;

        String title;


        /*
         * 没有任何异常。
         */
        if (!alarm) {

            level = "NORMAL";

            title =
                    "系统运行正常";

            messages.add(
                    "当前模拟生理数据和 Pump 状态未触发报警。"
            );

        /*
         * 存在严重异常。
         */
        } else if (critical) {

            level = "CRITICAL";

            title =
                    "严重报警";

        /*
         * 存在普通异常。
         */
        } else {

            level = "WARNING";

            title =
                    "系统警告";
        }


        // =====================================
        // 7. 生成报警时间
        // =====================================

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd HH:mm:ss"
                );

        String alarmTime =
                LocalDateTime.now()
                        .format(formatter);


        /*
         * 返回完整报警结果。
         */
        return new AlarmResult(
                alarm,
                level,
                title,
                messages,
                alarmTime
        );
    }
}