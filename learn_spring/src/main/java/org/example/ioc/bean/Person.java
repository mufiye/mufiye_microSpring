package org.example.ioc.bean;

import org.springframework.beans.factory.annotation.Value;

public class Person {
    @Value("ZhangSan")
    private String name;

    @Value("#{20-2}")
    private int age;

    @Value("${person.nickName}")
    private String nickName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
