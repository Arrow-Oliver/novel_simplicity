package com.zua;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Arrow
 * @date 2022/6/15 16:38
 */
@MapperScan("com.zua.dao.mapper")
@SpringBootApplication
@Slf4j
public class NovelApplication {
    public static void main(String[] args) {
        SpringApplication.run(NovelApplication.class,args);
    }
}
