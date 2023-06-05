package com.infogen.loyalty.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CustomerServiceLogAspect {

    @Before("execution(* com.infogen.loyalty.service.CustomerService.*(..))")
    public void beforeAdvice(JoinPoint joinPoint){
        System.out.println("Before method: " + joinPoint.getSignature());
    }

    @After("execution(* com.infogen.loyalty.service.CustomerService.*(..))")
    public void afterAdvice(JoinPoint joinPoint){
        System.out.println("After method: " + joinPoint.getArgs());
    }
}