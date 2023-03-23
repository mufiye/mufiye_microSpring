package org.example.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class UserProxy_Other {
    @Before(value = "execution(* org.example.aop.User.add(..))")
    public void before() {
        System.out.println("personProxy: before");
    }
}
