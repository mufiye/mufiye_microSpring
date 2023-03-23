package org.example.ioc.beanProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

// 流程：construct --> BeforeInitialization --> init --> AfterInitialization
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcess Before Initialization" + beanName);
        return bean;  // 如果有需求，可以将bean包装后再返回
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcess After Initialization" + beanName);
        return bean;  // 如果有需求，可以将bean包装后再返回
    }
}
