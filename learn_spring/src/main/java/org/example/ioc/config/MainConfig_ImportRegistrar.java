package org.example.ioc.config;

import org.example.ioc.registry.MyImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MyImportBeanDefinitionRegistrar.class)  // 通过控制注册类来添加组件
public class MainConfig_ImportRegistrar {

}
