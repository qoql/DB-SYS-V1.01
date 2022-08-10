package com.cy.pj.common.aspect;


import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class SysExceptionAspect {

    @AfterThrowing("execution(* com.cy.pj..*.*(..))")
    public void logExp(){
        System.out.println("出现了异常");
    }


}
