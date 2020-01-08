package com.cpf.xunwu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cpf.xunwu.mapper")
public class XunwuApplication {

    public static void main(String[] args) {
        SpringApplication.run(XunwuApplication.class, args);
    }

}
