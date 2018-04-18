package com.sunny;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 貌似此类需要放在扫描的包的父目录
 *
 * @author sunny
 * @version 1.0.0
 * @since 2018-04-18
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
@EnableCaching
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}