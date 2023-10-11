package com.poscodx.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.poscodx.config.app.DBConfig;

@Configuration
@ComponentScan({""})
@Import({DBConfig.class})
public class AppConfig {

}
