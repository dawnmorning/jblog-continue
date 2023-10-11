package com.poscodx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.poscodx.config.web.FileuploadConfig;
import com.poscodx.config.web.MVCConfig;
import com.poscodx.config.web.MessageSourceConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({ "com.poscodx.jblog.controller", "com.posocdx.jblog.exception" })
@Import({ MVCConfig.class, MessageSourceConfig.class, FileuploadConfig.class, MessageSourceConfig.class })
public class WebConfig {
}
