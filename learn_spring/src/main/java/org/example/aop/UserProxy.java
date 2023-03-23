package org.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(2)
public class UserProxy {

    // 提取公共点
    @Pointcut(value = "execution(* org.example.aop.User.add(..))")
    public void pointDemo() {

    }

    @Before(value = "pointDemo()")
    public void before() {
        System.out.println("before...");
    }

    @After(value = "pointDemo()")
    public void after() {
        System.out.println("after...");
    }

    @AfterThrowing(value = "pointDemo()")
    public void AfterThrowing(){
        System.out.println("afterThrowing...");
    }

    @Around(value = "pointDemo()")
    public void Around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("around before...");
        proceedingJoinPoint.proceed();  // 被增强的方法
        System.out.println("around after...");
    }
}
