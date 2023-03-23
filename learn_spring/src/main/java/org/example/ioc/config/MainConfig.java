package org.example.ioc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.example.ioc.dao", "org.example.ioc.service"})
public class MainConfig {
}
