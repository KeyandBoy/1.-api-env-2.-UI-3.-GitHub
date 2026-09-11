package com.example.insulinpump.controller;

import com.example.insulinpump.record.SimulationRecord;
import com.example.insulinpump.service.RecordService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RecordController
 *
 * 历史数据记录接口。
 */
@RestController
@RequestMapping("/api/records")
public class RecordController {

    /**
     * 数据记录服务。
     */
    private final RecordService recordService;


    /**
     * 构造函数注入。
     */
    public RecordController(
            RecordService recordService) {

        this.recordService =
                recordService;
    }


    /**
     * 保存一条完整系统记录。
     *
     * HTTP：
     *
     * POST /api/records
     *
     * 前端需要发送 JSON。
     */
    @PostMapping
    public SimulationRecord saveRecord(

            @RequestBody
            SimulationRecord record) {

        return recordService.save(
                record
        );
    }


    /**
     * 查询所有历史记录。
     *
     * GET /api/records
     */
    @GetMapping
    public List<SimulationRecord>
        getAllRecords() {

        return recordService.findAll();
    }


    /**
     * 查询当前记录数量。
     *
     * GET /api/records/count
     */
    @GetMapping("/count")
    public long countRecords() {

        return recordService.count();
    }
}