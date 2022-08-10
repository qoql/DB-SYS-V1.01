package com.cy.pj.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * SysLogAspect类为系统中的某些业务操作添加日志扩展功能
 * 其中：@Aspect描述的类为一个切面对象
 *      这样的类中通常会有两大部分构造
 *      1）Pointcut--切入点：植入扩展功能的点
 *      2）Advice--通知：要扩展的功能
 */
@Service
@Aspect
public class SysLogAspect {
    /**
     * 借助 @Pointcut注解定义切入点
     * 其中bean(SysUserServiceImp)为切入点表达式（说明切入的类）
     * 切入点表达式语法结构有两种：
     *      1）具体表达式，bean(表达式)，例如bean(SysUserServiceImp)，代表切入具体类
     *      2）通配符表达式，bean(表达式)，例如bean(*ServiceImp)，代表切入以ServiceImp结尾的类
     */
    //为SysUserServiceImp类植入日志功能
    @Pointcut("bean(sysUserServiceImp)")
    public void doLog(){}

    /**
     *  @Around 标识该方法为一个环绕通知，这个通知能在目标方法执行前后做一些操作
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around("doLog()")
    public Object around(ProceedingJoinPoint jp) throws Throwable{
        long l1 = System.currentTimeMillis();
        /*jp.proceed()
            执行下一个切面方法
            没有下一个切面方法则执行目标方法
         */
        Object result = jp.proceed();
        long l2 = System.currentTimeMillis();
        Method tMethod = getTMethod(jp);
        //System.out.println(tMethod.getDeclaringClass().getName());
        //System.out.println(tMethod.getName());
        String s = getAllMethodName(tMethod);
        System.out.println(s+" execute time:"+(l2-l1));
        return result;

    }

    private String getAllMethodName(Method tMethod) {
        StringBuilder s = new StringBuilder(tMethod.getDeclaringClass().getName()+".");
        s.append(tMethod.getName());
        return s.toString();
    }

    private Method getTMethod(ProceedingJoinPoint jp) throws NoSuchMethodException {
        //获取目标类对象
        Class<?> aClass = jp.getTarget().getClass();
        //System.out.println(aClass);
        //获取方法签名信息
        MethodSignature signature = (MethodSignature)jp.getSignature();
        //System.out.println(signature.getMethod());
        //获取运行的方法
        Method method = aClass.getDeclaredMethod(signature.getName(),signature.getParameterTypes());
        return method;
    }


}
