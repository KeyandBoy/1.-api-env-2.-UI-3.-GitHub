package com.example.insulinpump.controller;

import com.example.insulinpump.model.FaultStatus;
import com.example.insulinpump.service.FaultSimulationService;

import org.springframework.web.bind.annotation.*;

/**
 * FaultController
 *
 * 系统故障仿真控制接口。
 *
 * 这些接口用于课程测试，
 * 可以人为开启或关闭设备故障。
 */
@RestController
@RequestMapping("/api/fault")
public class FaultController {

    /**
     * 故障仿真服务。
     */
    private final FaultSimulationService faultService;


    /**
     * 构造函数注入。
     */
    public FaultController(
            FaultSimulationService faultService) {

        this.faultService =
                faultService;
    }


    /**
     * 查看当前全部故障状态。
     *
     * GET /api/fault/status
     */
    @GetMapping("/status")
    public FaultStatus getStatus() {

        return faultService.getStatus();
    }


    /**
     * 控制血糖传感器故障。
     *
     * POST
     * /api/fault/glucose?enabled=true
     */
    @PostMapping("/glucose")
    public FaultStatus setGlucoseFailure(
            @RequestParam boolean enabled) {

        faultService
                .setGlucoseSensorFailure(
                        enabled
                );

        return faultService.getStatus();
    }


    /**
     * 控制血压传感器故障。
     */
    @PostMapping("/blood-pressure")
    public FaultStatus setBloodPressureFailure(
            @RequestParam boolean enabled) {

        faultService
                .setBloodPressureSensorFailure(
                        enabled
                );

        return faultService.getStatus();
    }


    /**
     * 控制心率传感器故障。
     */
    @PostMapping("/heart-rate")
    public FaultStatus setHeartRateFailure(
            @RequestParam boolean enabled) {

        faultService
                .setHeartRateSensorFailure(
                        enabled
                );

        return faultService.getStatus();
    }


    /**
     * 控制 Pump 故障。
     */
    @PostMapping("/pump")
    public FaultStatus setPumpFailure(
            @RequestParam boolean enabled) {

        faultService
                .setPumpFailure(
                        enabled
                );

        return faultService.getStatus();
    }


    /**
     * 控制模拟 Power Failure。
     */
    @PostMapping("/power")
    public FaultStatus setPowerFailure(
            @RequestParam boolean enabled) {

        faultService
                .setPowerFailure(
                        enabled
                );

        return faultService.getStatus();
    }


    /**
     * 恢复全部设备。
     *
     * POST /api/fault/reset
     */
    @PostMapping("/reset")
    public FaultStatus resetAll() {

        faultService.resetAll();

        return faultService.getStatus();
    }
}