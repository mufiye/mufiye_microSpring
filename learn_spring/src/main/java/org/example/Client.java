package org.example;

import org.example.aop.User;
import org.example.ioc.config.MainConfig_Import;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Client {
    public static void main(String[] args) {
    /*
        加载配置文件的方式
        ApplicationContext context = new ClassPathXmlApplicationContext("configurationFile.xml");
    */
        // 加载配置类的方式
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig_Import.class);
        String[] names = context.getBeanDefinitionNames();
        for(String name : names) {
            System.out.println(name);
        }
        User user = context.getBean("user", User.class);
        user.add();
        context.close();
    }
}
