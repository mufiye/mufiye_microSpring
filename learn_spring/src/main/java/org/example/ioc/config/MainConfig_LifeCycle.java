package org.example.ioc.config;

import org.example.ioc.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
    construct --> init --> destroy
 */
@Configuration
public class MainConfig_LifeCycle {
    /* 初始化方法和销毁方法 */
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Car car() {
        return new Car();
    }
}
