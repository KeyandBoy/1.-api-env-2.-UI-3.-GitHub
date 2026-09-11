package com.example.insulinpump.controller;

import com.example.insulinpump.model.AiAgentResult;
import com.example.insulinpump.service.AiAgentService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AiAgentController
 *
 * 智能胰岛素泵 AI Agent HTTP 接口。
 *
 * 前端向本接口提交：
 *
 * blood glucose
 * blood pressure
 * heart rate
 * reservoir
 * pump status
 *
 * AI Agent 会自动读取数据库历史记录，
 * 最终返回综合分析结果。
 */
@RestController
@RequestMapping("/api/ai")
public class AiAgentController {

    /**
     * AI Agent 服务。
     */
    private final AiAgentService aiAgentService;


    /**
     * 构造函数注入。
     */
    public AiAgentController(
            AiAgentService aiAgentService) {

        this.aiAgentService =
                aiAgentService;
    }


    /**
     * AI Agent 综合分析接口。
     *
     * 示例：
     *
     * GET /api/ai/analyze
     * ?glucose=8.5
     * &systolic=120
     * &diastolic=80
     * &heartRate=75
     * &reservoir=90
     * &pumpStatus=READY
     */
    @GetMapping("/analyze")
    public AiAgentResult analyze(

            @RequestParam
            double glucose,

            @RequestParam
            int systolic,

            @RequestParam
            int diastolic,

            @RequestParam
            int heartRate,

            @RequestParam
            double reservoir,

            @RequestParam
            String pumpStatus) {

        return aiAgentService.analyze(
                glucose,
                systolic,
                diastolic,
                heartRate,
                reservoir,
                pumpStatus
        );
    }
}