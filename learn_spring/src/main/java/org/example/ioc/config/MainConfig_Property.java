package org.example.ioc.config;

import org.example.ioc.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {"classpath:/person.properties"})  // 使用PropertySource读取配置文件
@Configuration
public class MainConfig_Property {
    @Bean
    public Person person() {
        return new Person();
    }
}
