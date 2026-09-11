package com.example.insulinpump.controller;

import com.example.insulinpump.model.PumpStatus;
import com.example.insulinpump.service.PumpCommandService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * PumpController
 *
 * 模拟胰岛素泵控制接口。
 *
 * 浏览器或前端通过 HTTP 请求，
 * 向 Pump Controller 发送控制命令。
 */
@RestController
@RequestMapping("/api/pump")
public class PumpController {

    /**
     * 模拟 Pump。
     */
    private final PumpCommandService pumpCommandService;


    /**
     * Spring Boot 构造函数注入。
     */
    public PumpController(
            PumpCommandService pumpCommandService) {

        this.pumpCommandService = pumpCommandService;
    }


    /**
     * 获取当前 Pump 状态。
     *
     * 地址：
     *
     * GET /api/pump/status
     */
    @GetMapping("/status")
    public PumpStatus getStatus() {

        return pumpCommandService.getStatus();
    }


    /**
     * 执行一次模拟输注。
     *
     * 地址：
     *
     * POST /api/pump/deliver?dose=3.0
     */
    @PostMapping("/deliver")
    public PumpStatus deliver(

            @RequestParam double dose) {

        return pumpCommandService.execute(
                pumpCommandService.createDeliverCommand(dose));
    }


    /**
     * 停止模拟 Pump。
     *
     * 地址：
     *
     * POST /api/pump/stop
     */
    @PostMapping("/stop")
    public PumpStatus stop() {

        return pumpCommandService.execute(
                pumpCommandService.createStopCommand());
    }


    /**
     * 重置 Pump。
     *
     * 地址：
     *
     * POST /api/pump/reset
     */
    @PostMapping("/reset")
    public PumpStatus reset() {

        return pumpCommandService.reset();
    }
}
