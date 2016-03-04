package com.yonyou.mcloud.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Service切面
 * Created by hubo on 2016/2/27
 */
@Aspect
@Component
public class ServiceLogAspect {

    private static Logger log = LoggerFactory.getLogger(ServiceLogAspect.class);

    @Pointcut("execution(* com.yonyou.mcloud.service..*(..))")
    public void aspect(){}

    @Around("aspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("exec " + joinPoint + "\tUse time : " + (end - start) + " ms!");
        }
        return result;
    }

}
