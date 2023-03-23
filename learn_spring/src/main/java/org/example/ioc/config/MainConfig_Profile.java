package org.example.ioc.config;

import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Profile: spring为我们提供的可以根据当前环境，动态地激活和切换一系列组件的功能
 *
 * 开发环境/测试环境/运行环境
 *
 * @Profile：指定组件在哪个环境的情况下才能被注册到容器中，不指定，任何环境下都能注册这个组件
 * eg.   @Profile("default")
 *       @Profile("dev")
 *       @Profile("test")
 *       @Profile("prod")
 */

/**
 * 激活方式：
 *    1. 使用命令行动态参数：-Dspring.profiles.active=test
 *    2. 使用代码的形式：
 *          AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();  // 创建context
 *          applicationContext.getEnvironment().setActiveProfiles("test", "dev");  // 设置一个需要激活的环境
 *          applicationContext.register(MainConfig_Profile.class);  // 注册主配置类
 *          applicationContext.refresh();  // 启动刷新容器
 */

@Configuration
public class MainConfig_Profile {

}
