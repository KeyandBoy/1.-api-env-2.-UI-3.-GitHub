package com.example.insulinpump.service;

import com.example.insulinpump.model.AlarmResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DeepSeekService
 *
 * 可选的文字解释服务。规则引擎始终先完成判断，
 * DeepSeek 只在 WARNING/CRITICAL 时生成短文本，
 * 不参与剂量、Pump 或 Alarm 的决策。
 */
@Service
public class DeepSeekService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${deepseek.api-url}")
    private String apiUrl;

    @Value("${deepseek.model:deepseek-chat}")
    private String model;

    /**
     * 读取系统环境变量；本地开发时也支持项目根目录 .env。
     */
    private String readApiKey() {
        String key = System.getenv("DEEPSEEK_API_KEY");
        if (key != null && !key.trim().isEmpty()) {
            return key.trim();
        }

        try {
            List<String> lines = Files.readAllLines(
                    Paths.get(".env"), StandardCharsets.UTF_8);
            for (String line : lines) {
                String text = line.trim();
                if (text.startsWith("DEEPSEEK_API_KEY=")) {
                    return text.substring("DEEPSEEK_API_KEY=".length())
                            .trim().replace("\"", "");
                }
            }
        } catch (IOException ignored) {
            // 没有 .env 时使用本地降级逻辑，不影响系统主流程。
        }
        return "";
    }

    /**
     * 生成不超过约 80 字的课程仿真辅助解释。
     */
    public String explain(
            double glucose,
            int systolic,
            int diastolic,
            int heartRate,
            double reservoir,
            String pumpStatus,
            AlarmResult alarmResult) {

        String fallback = buildFallback(alarmResult);
        String apiKey = readApiKey();
        if (apiKey.isEmpty()) {
            return fallback;
        }

        try {
            Map<String, Object> userData = new HashMap<>();
            userData.put("glucose", glucose);
            userData.put("bloodPressure", systolic + "/" + diastolic);
            userData.put("heartRate", heartRate);
            userData.put("reservoir", reservoir);
            userData.put("pumpStatus", pumpStatus);
            userData.put("alarm", alarmResult.getLevel());

            Map<String, String> system = new HashMap<>();
            system.put("role", "system");
            system.put("content", "你是课程软件仿真辅助分析器。只做简短异常解释。禁止计算真实医疗剂量，禁止提供治疗方案。最多80字，只返回一句中文。");

            Map<String, String> user = new HashMap<>();
            user.put("role", "user");
            user.put("content", objectMapper.writeValueAsString(userData));

            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(system);
            messages.add(user);

            Map<String, Object> request = new HashMap<>();
            request.put("model", model);
            request.put("messages", messages);
            request.put("temperature", 0.1);
            request.put("max_tokens", 120);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);
            JsonNode response = restTemplate.postForObject(
                    apiUrl,
                    new HttpEntity<Map<String, Object>>(request, headers),
                    JsonNode.class);

            JsonNode content = response == null ? null
                    : response.path("choices").path(0)
                            .path("message").path("content");
            if (content != null && !content.isMissingNode()
                    && !content.asText().trim().isEmpty()) {
                return content.asText().trim();
            }
        } catch (Exception ignored) {
            // 外部服务不可用时必须保持原有规则 Agent 正常返回。
        }
        return fallback;
    }

    private String buildFallback(AlarmResult alarmResult) {
        if ("CRITICAL".equalsIgnoreCase(alarmResult.getLevel())) {
            return "规则系统已识别严重仿真报警，请优先检查异常数据和设备状态。";
        }
        return "规则系统已识别普通仿真异常，建议继续采集并观察后续趋势。";
    }
}
