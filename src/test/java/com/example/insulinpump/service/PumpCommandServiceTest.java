package com.example.insulinpump.service;

import com.example.insulinpump.model.PumpCommand;
import com.example.insulinpump.model.PumpStatus;
import com.example.insulinpump.pump.InsulinPump;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * PumpCommandService 单元测试。
 */
public class PumpCommandServiceTest {

    @Test
    public void shouldExecuteDeliverCommandThroughPump() {
        InsulinPump pump = mock(InsulinPump.class);
        PumpStatus expected = new PumpStatus(
                "RUNNING", 97.0, 3.0, "ok");
        when(pump.deliver(3.0)).thenReturn(expected);

        PumpStatus actual = new PumpCommandService(pump).execute(
                new PumpCommand("DELIVER", 3.0));

        assertEquals("RUNNING", actual.getStatus());
        verify(pump).deliver(3.0);
    }

    @Test
    public void shouldRejectUnknownCommand() {
        InsulinPump pump = mock(InsulinPump.class);
        when(pump.getStatus()).thenReturn(new PumpStatus(
                "READY", 100.0, 0.0, "ready"));

        PumpStatus result = new PumpCommandService(pump).execute(
                new PumpCommand("UNKNOWN", 1.0));

        assertEquals("ERROR", result.getStatus());
    }
}
