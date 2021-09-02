package com.example.demo.configs.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Component
@Aspect
public class MyLogAspect {
    private final Logger logger = LoggerFactory.getLogger(MyLogAspect.class);

    /**
     *   execution(* com.example.demo.*.*(..))
     *   第一个 * 表示任意返回值
     *   第二个 * 表示demo包中的所有包
     *   第三个 * 表示包中的所有类
     *   第四个 * 表示类中所有方法
     *   .. 表示任意参数
     */
    @Pointcut("execution(* com.example.demo.*.*.*(..))")
    public void pointcut(){

    }
    @Before(value = "pointcut()")
    public void before(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        logger.info(name+"():方法开始执行");

    }
    @After(value = "pointcut()")
    public void after(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        logger.info(name+"():执行结束");
    }
    @AfterReturning(value = "pointcut()",returning = "result")
    public void afterRunning(JoinPoint joinPoint,Object result){
        String name = joinPoint.getSignature().getName();
        logger.info(name+"()返回值:"+result);
    }
    @AfterThrowing(value = "pointcut()",throwing = "e")
    private void afterThrowing(JoinPoint joinPoint,Exception e){
        String name = joinPoint.getSignature().getName();
        logger.info(name+"()出现异常:"+e.getMessage());
    }
    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        return joinPoint.proceed();
    }

}
