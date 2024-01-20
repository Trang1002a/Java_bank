package com.example.dbsservice.configuration;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("within(com.example.dbsservice.presentation.controller.*)")
    public void logAspect(){}

    @Around("logAspect()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        final long timeStart = System.currentTimeMillis();
        final Object proceed = joinPoint.proceed();
        final long executeTime = System.currentTimeMillis() - timeStart;
        logger.info(String.format("LOG ASPECT: %s.%s excuted in %s ms",
                joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(),
                executeTime));
        return proceed;
    }
}
