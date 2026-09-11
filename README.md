# Medical Instrument Simulation / Insulin Pump

智能胰岛素泵仿真系统是软件工程课程项目。系统在 PC 上模拟 Sensor、Analyzer、Simulation Dose、Pump、Alarm、H2 历史记录、ECharts 和 Rule-Based AI Agent，不连接真实硬件，也不提供真实医疗给药算法。

## 技术栈

- Windows、Java 1.8.0_202、Maven 3.9.11
- Spring Boot 2.7.18、Spring Web、Spring Data JPA
- H2 文件数据库、HTML/CSS/JavaScript、ECharts
- 服务端口：`9100`

## 快速启动

```text
cd /d E:\computerProgram2\insulin-pump-simulation
mvn spring-boot:run
```

页面：

- 控制台：`http://localhost:9100/`
- 图表：`http://localhost:9100/charts.html`
- AI Agent：`http://localhost:9100/ai-agent.html`
- H2 Console：`http://localhost:9100/h2-console`

H2 JDBC：`jdbc:h2:file:./data/insulin_pump_db`，用户名 `sa`，密码为空。

## 系统架构

```text
Sensor -> SensorAnalyzer -> Alarm / InsulinCalculator
                         -> PumpCommandService -> InsulinPump
                         -> RecordService -> H2
                         -> ECharts / AI Agent
```

## 功能模块

- 三类模拟 Sensor：血糖、血压、心率
- 生理数据分析和三等级 Alarm
- 标记为 SU（Simulation Unit）的课程仿真控制量
- Historical Records 参与近期控制量限制
- `PumpCommand` 的 `DELIVER`、`STOP`、`NO_ACTION`
- Pump 状态、Reservoir、故障仿真和统一错误处理
- 历史记录表格、四张 ECharts 图、Rule-Based AI Agent

## API 概览

| 方法 | URL | 作用 |
|---|---|---|
| GET | `/api/sensor/current` | 采集模拟数据 |
| GET | `/api/analysis/analyze` | 分析生理数据 |
| GET | `/api/insulin/calculate?glucose=...` | 计算仿真控制量 |
| GET/POST | `/api/pump/status`, `/api/pump/deliver`, `/api/pump/stop`, `/api/pump/reset` | 控制 Pump |
| GET | `/api/alarm/check` | 检查报警 |
| GET/POST | `/api/records`, `/api/records/count` | 保存和读取历史记录 |
| GET | `/api/ai/analyze` | AI Agent 综合分析 |
| GET/POST | `/api/fault/*` | 开关和恢复模拟故障 |

## 项目结构

`controller` 提供 REST 接口，`sensor` 生成数据，`service` 实现业务规则，`pump` 模拟设备，`record/repository` 负责 JPA 持久化，`model` 保存接口模型，`static` 保存三个页面和本地 ECharts。

## 注意事项

所有阈值、控制量和 Reservoir 规则仅用于课程软件仿真。`SU` 是 Simulation Unit，不是真实胰岛素单位；禁止用于真实诊断、治疗或给药。故障测试结束后使用 `POST /api/fault/reset` 恢复所有故障。
