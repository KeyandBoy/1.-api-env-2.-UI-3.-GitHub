package com.example.insulinpump.controller;

import com.example.insulinpump.model.AlarmResult;
import com.example.insulinpump.service.AlarmService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AlarmController
 *
 * 报警系统 HTTP 接口。
 *
 * 前端可以把：
 *
 * 血糖
 * 血压
 * 心率
 * Reservoir
 * Pump Status
 *
 * 发送到这里。
 *
 * AlarmService 会完成报警判断。
 */
@RestController
@RequestMapping("/api/alarm")
public class AlarmController {

    /**
     * 报警业务服务。
     */
    private final AlarmService alarmService;


    /**
     * Spring Boot 构造函数注入。
     */
    public AlarmController(
            AlarmService alarmService) {

        this.alarmService =
                alarmService;
    }


    /**
     * 执行一次报警检查。
     *
     * 示例：
     *
     * /api/alarm/check
     * ?glucose=8
     * &systolic=120
     * &diastolic=80
     * &heartRate=75
     * &reservoir=100
     * &pumpStatus=READY
     */
    @GetMapping("/check")
    public AlarmResult checkAlarm(

            @RequestParam double glucose,

            @RequestParam int systolic,

            @RequestParam int diastolic,

            @RequestParam int heartRate,

            @RequestParam double reservoir,

            @RequestParam String pumpStatus) {

        return alarmService.checkAlarm(
                glucose,
                systolic,
                diastolic,
                heartRate,
                reservoir,
                pumpStatus
        );
    }
}