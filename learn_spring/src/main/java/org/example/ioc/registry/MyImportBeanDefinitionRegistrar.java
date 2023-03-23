package org.example.ioc.registry;

import org.example.ioc.bean.Color;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     *
     * @param importingClassMetadata annotation metadata of the importing class
     * @param registry current bean definition registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean definition = registry.containsBeanDefinition("Color");
        if(!definition) {
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Color.class);
            registry.registerBeanDefinition("Color", rootBeanDefinition);  // 将Color类注册进去
        }
    }
}
