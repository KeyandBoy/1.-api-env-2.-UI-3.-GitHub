package com.example.insulinpump.controller;

import com.example.insulinpump.model.SensorAnalysisResult;
import com.example.insulinpump.service.SensorAnalyzer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AnalysisController
 *
 * 生理数据分析 API。
 */
@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    private final SensorAnalyzer sensorAnalyzer;


    public AnalysisController(
            SensorAnalyzer sensorAnalyzer) {

        this.sensorAnalyzer = sensorAnalyzer;
    }


    /**
     * 分析当前生理数据。
     *
     * 示例：
     *
     * /api/analysis/analyze
     * ?glucose=8.5
     * &systolic=125
     * &diastolic=80
     * &heartRate=75
     */
    @GetMapping("/analyze")
    public SensorAnalysisResult analyze(

            @RequestParam double glucose,

            @RequestParam int systolic,

            @RequestParam int diastolic,

            @RequestParam int heartRate) {


        return sensorAnalyzer.analyze(
                glucose,
                systolic,
                diastolic,
                heartRate
        );
    }
}