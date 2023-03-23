package org.example.ioc.factoryBean;

import org.example.ioc.bean.Color;
import org.springframework.beans.factory.FactoryBean;

public class ColorFactoryBean implements FactoryBean<Color> {
    // 返回一个Color对象，这个对象会添加到容器中
    @Override
    public Color getObject() throws Exception {
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    // 如果为false，每次获取都会返回一个新的
    @Override
    public boolean isSingleton() {
        return true;
    }
}
