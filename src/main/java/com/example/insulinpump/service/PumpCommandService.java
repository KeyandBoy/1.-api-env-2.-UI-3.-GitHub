package com.example.insulinpump.service;

import com.example.insulinpump.model.PumpCommand;
import com.example.insulinpump.model.PumpStatus;
import com.example.insulinpump.pump.InsulinPump;

import org.springframework.stereotype.Service;

/**
 * PumpCommandService
 *
 * 将上层计算结果转换为明确的 PumpCommand，
 * 再把命令交给模拟 Pump 执行。
 */
@Service
public class PumpCommandService {

    private final InsulinPump insulinPump;

    public PumpCommandService(InsulinPump insulinPump) {
        this.insulinPump = insulinPump;
    }

    /**
     * 创建一次输注命令。
     */
    public PumpCommand createDeliverCommand(double dose) {
        return new PumpCommand("DELIVER", dose);
    }

    /**
     * 创建停止命令。
     */
    public PumpCommand createStopCommand() {
        return new PumpCommand("STOP", 0);
    }

    /**
     * 查询 Pump 状态，不伪造一个执行命令。
     */
    public PumpStatus getStatus() {
        return insulinPump.getStatus();
    }

    /**
     * 重置属于设备维护动作，保持原有 Pump reset 行为。
     */
    public PumpStatus reset() {
        return insulinPump.reset();
    }

    /**
     * 执行命令，并防御未知命令，避免静默执行错误操作。
     */
    public PumpStatus execute(PumpCommand command) {
        if (command == null || command.getCommand() == null) {
            return invalidCommand("PumpCommand 不能为空。");
        }

        String name = command.getCommand().trim().toUpperCase();
        if ("DELIVER".equals(name)) {
            return insulinPump.deliver(command.getSimulationDose());
        }
        if ("STOP".equals(name)) {
            return insulinPump.stop();
        }
        if ("NO_ACTION".equals(name)) {
            PumpStatus current = insulinPump.getStatus();
            current.setMessage("NO_ACTION：本次未向 Pump 发送执行动作。");
            return current;
        }
        return invalidCommand("不支持的 PumpCommand：" + command.getCommand());
    }

    private PumpStatus invalidCommand(String message) {
        PumpStatus current = insulinPump.getStatus();
        current.setStatus("ERROR");
        current.setMessage(message);
        return current;
    }
}
