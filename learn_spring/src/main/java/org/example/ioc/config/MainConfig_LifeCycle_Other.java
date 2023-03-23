package org.example.ioc.config;

import org.example.ioc.bean.Cat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig_LifeCycle_Other {
    @Bean
    public Cat cat() {
        return new Cat();
    }
}
