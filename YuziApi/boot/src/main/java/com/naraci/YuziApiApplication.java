package com.naraci;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@MapperScan("com.naraci.**.mapper")
@Configuration
@SpringBootApplication(scanBasePackages = "com.naraci")
public class YuziApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(YuziApiApplication.class, args);
    }

}

