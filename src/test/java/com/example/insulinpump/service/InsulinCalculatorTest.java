package com.example.insulinpump.service;

import com.example.insulinpump.record.SimulationRecord;
import com.example.insulinpump.repository.SimulationRecordRepository;
import com.example.insulinpump.model.InsulinDoseResult;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * InsulinCalculator 单元测试。
 *
 * 验证历史记录确实能够影响课程仿真控制量判断。
 */
public class InsulinCalculatorTest {

    @Test
    public void shouldReturnReadyWhenHistoryHasNoLargeDose() {
        SimulationRecordRepository repository = mock(
                SimulationRecordRepository.class);
        when(repository.findAllByOrderByIdDesc())
                .thenReturn(Collections.<SimulationRecord>emptyList());

        InsulinDoseResult result = new InsulinCalculator(repository)
                .calculate(11.99);

        assertEquals("READY", result.getCalculationStatus());
        assertEquals(3.0, result.getSimulationDose());
        assertEquals(0, result.getHistoryRecordCount());
        assertTrue(!result.isHistoricalDoseLimitApplied());
    }

    @Test
    public void shouldApplyHistoryLimitAfterLargeRecentDose() {
        SimulationRecord record = new SimulationRecord();
        record.setSimulationDose(4.5);
        SimulationRecordRepository repository = mock(
                SimulationRecordRepository.class);
        when(repository.findAllByOrderByIdDesc())
                .thenReturn(Collections.singletonList(record));

        InsulinDoseResult result = new InsulinCalculator(repository)
                .calculate(11.99);

        assertEquals("HISTORY_LIMIT", result.getCalculationStatus());
        assertEquals(0.0, result.getSimulationDose());
        assertEquals(1, result.getHistoryRecordCount());
        assertTrue(result.isHistoricalDoseLimitApplied());
    }
}
