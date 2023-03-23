package org.example.aop;

import org.springframework.stereotype.Component;

@Component
public class User {
    public void add() {
        System.out.println("mufiye user does add");
    }
}
