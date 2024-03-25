package com.naraci;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication(scanBasePackages = "com.naraci")
//@MapperScan({"com.naraci.app.mapper","com.naraci.**.mapper"})
@MapperScan("com.naraci.**.mapper")
public class YuziApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(YuziApiApplication.class, args);
    }

}

