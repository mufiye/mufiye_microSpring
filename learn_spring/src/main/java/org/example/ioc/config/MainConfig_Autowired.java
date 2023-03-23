package org.example.ioc.config;

import org.example.ioc.bean.Car;
import org.example.ioc.bean.Color;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.example")
public class MainConfig_Autowired {
    @Bean  // 标注的方法创建对象时，方法参数的值从容器中获取
    public Color color(Car car) {
        car.destroy();
        return new Color();
    }
}
