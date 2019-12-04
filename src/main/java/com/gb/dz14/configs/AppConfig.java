package com.gb.dz14.configs;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAutoConfiguration
@PropertySource("classpath:private.properties")
@ComponentScan("com.gb.dz14")
public class AppConfig implements WebMvcConfigurer {
}