package org.example.ioc.config;

import org.example.ioc.bean.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MainConfig_Scope {
    @Scope("prototype")  // prototype为多实例，singleton则为单实例，默认为单例
    @Bean
    public Book book() {  // id是方法名，类型是User
        return new Book();
    }
}
