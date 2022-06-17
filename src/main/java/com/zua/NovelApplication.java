package com.zua;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Arrow
 * @date 2022/6/15 16:38
 */
@MapperScan("com.zua.dao.mapper")
@SpringBootApplication
@Slf4j
@EnableCaching
public class NovelApplication {
    public static void main(String[] args) {
        SpringApplication.run(NovelApplication.class,args);
    }
}
