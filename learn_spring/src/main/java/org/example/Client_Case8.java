package org.example;

import org.example.ioc.config.MainConfig_FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Client_Case8 {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MainConfig_FactoryBean.class);
        Object bean = context.getBean("colorFactoryBean");
        System.out.println("bean的类型：" + bean.getClass());
        Object factoryBean = context.getBean("&colorFactoryBean");  // 通过添加&符号获取到工厂本身
        System.out.println("factory bean的类型：" + factoryBean.getClass());
    }
}
