package org.example.ioc.config;

import org.example.aop.User;
import org.example.ioc.bean.Color;
import org.example.ioc.condition.LinuxCondition;
import org.example.ioc.condition.WindowsCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

@Configuration
@Import({Color.class})  // 快速给容器导入一个组件, id默认为全类名
                        // 等同于 @Import(MyImportSelector.class)，MyImportSelector是自定义的selector
public class MainConfig_Import {
    @Lazy  // 单例，懒加载
    @Bean
    public User user() {  // id是方法名即user，类型是User
        return new User();
    }

    /*
        @Conditional({Condition})：按照一定条件进行判断，满足条件给容器中注册bean
                                   该注解还可以放在类上
     */
    @Conditional({WindowsCondition.class})
    @Bean
    public User user01() {
        return new User();
    }

    @Conditional({LinuxCondition.class})
    @Bean
    public User user02() {
        return new User();
    }
}
