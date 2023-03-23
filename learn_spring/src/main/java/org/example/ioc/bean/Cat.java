package org.example.ioc.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Cat implements InitializingBean, DisposableBean {

    public Cat() {
        System.out.println("cat constructor...");
    }

    // construct之后进行调用
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("cat...init...afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("cat...destroy");
    }
}
