package org.example.ioc.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Boss {
    private Car car;

    @Autowired  // 构造器要用的组件也从ioc容器中获取，如果只有一个构造器，不加@Autowired也可以
    public Boss boss(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    @Autowired  // 标志在方法上，spring容器创建当前对象就会调用方法完成赋值；方法使用的参数，自定义类型的值从ioc容器中获取
    public void setCar(Car car) {
        this.car = car;
    }
}
