package com.example.insulinpump;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 智能胰岛素泵仿真系统
 *
 * Spring Boot 主启动类
 */
@SpringBootApplication
public class InsulinPumpApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                InsulinPumpApplication.class,
                args
        );

    }
}