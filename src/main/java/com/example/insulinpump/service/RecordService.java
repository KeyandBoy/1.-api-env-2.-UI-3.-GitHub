package com.example.insulinpump.service;

import com.example.insulinpump.record.SimulationRecord;
import com.example.insulinpump.repository.SimulationRecordRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * RecordService
 *
 * 系统数据记录服务。
 *
 * 负责：
 *
 * 1. 保存系统运行记录
 * 2. 查询全部历史记录
 * 3. 查询记录数量
 */
@Service
public class RecordService {

    /**
     * 数据库 Repository。
     */
    private final SimulationRecordRepository repository;


    /**
     * 构造函数注入。
     */
    public RecordService(
            SimulationRecordRepository repository) {

        this.repository = repository;
    }


    /**
     * 保存一条系统记录。
     *
     * @param record 前端提交的系统运行数据
     *
     * @return 数据库最终保存的记录
     */
    public SimulationRecord save(
            SimulationRecord record) {

        /*
         * 生成数据库记录时间。
         */
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd HH:mm:ss"
                );

        String recordTime =
                LocalDateTime.now()
                        .format(formatter);


        /*
         * 不相信前端提供的 recordTime，
         * 统一由后端生成。
         */
        record.setRecordTime(
                recordTime
        );


        /*
         * 保存到 H2 数据库。
         */
        return repository.save(
                record
        );
    }


    /**
     * 查询全部记录。
     *
     * 最新的数据排在前面。
     */
    public List<SimulationRecord>
        findAll() {

        return repository
                .findAllByOrderByIdDesc();
    }


    /**
     * 获取数据库中的记录总数。
     */
    public long count() {

        return repository.count();
    }
}