package com.example.insulinpump.repository;

import com.example.insulinpump.record.SimulationRecord;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SimulationRecordRepository
 *
 * 历史记录数据库访问接口。
 *
 * JpaRepository 已经自动提供：
 *
 * save()
 * findAll()
 * findById()
 * delete()
 * count()
 *
 * 等常用数据库操作。
 */
@Repository
public interface SimulationRecordRepository
        extends JpaRepository<
            SimulationRecord,
            Long> {

    /**
     * 根据 ID 倒序查询。
     *
     * 最新记录排在最前面。
     */
    List<SimulationRecord>
        findAllByOrderByIdDesc();
}
