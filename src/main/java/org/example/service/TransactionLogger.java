package org.example.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TransactionLogger {

    @Around(value = "execution(* org.example.service.TransactionServiceImpl.*(..))")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {

        System.out.println("Start transfer.");
        Object returnValue = null;
        try {
            returnValue = proceedingJoinPoint.proceed();
            System.out.println("Transfer end.");
        } catch (Throwable throwable) {
            System.out.println("Transfer error!");
            throwable.printStackTrace();
        }

        return returnValue;
    }
}
