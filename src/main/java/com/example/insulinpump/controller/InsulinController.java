package com.example.insulinpump.controller;

import com.example.insulinpump.model.InsulinDoseResult;
import com.example.insulinpump.service.InsulinCalculator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * InsulinController
 *
 * 仿真控制量计算接口。
 */
@RestController
@RequestMapping("/api/insulin")
public class InsulinController {

    /**
     * 仿真计算服务。
     */
    private final InsulinCalculator insulinCalculator;


    /**
     * 使用构造函数注入。
     */
    public InsulinController(
            InsulinCalculator insulinCalculator) {

        this.insulinCalculator =
                insulinCalculator;
    }


    /**
     * 计算仿真控制量。
     *
     * 示例：
     *
     * /api/insulin/calculate?glucose=10.5
     *
     * @param glucose 当前模拟血糖值
     *
     * @return 仿真计算结果
     */
    @GetMapping("/calculate")
    public InsulinDoseResult calculate(

            @RequestParam double glucose) {

        return insulinCalculator.calculate(
                glucose
        );
    }
}