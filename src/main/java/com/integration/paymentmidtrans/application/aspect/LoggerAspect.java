package com.integration.paymentmidtrans.application.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LoggerAspect {

    //    @Around("execution(* com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.*.*(..))")
    @Around(
        "@within(org.springframework.web.bind.annotation.RestController) && " +
            "execution(public * *(..))"
    )
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        log.info("Starting method: {}.{}", className, methodName);

        try {
            Object result = joinPoint.proceed();
            log.info("Method completed successfully: {}.{}", className, methodName);
            return result;
        } catch (Exception e) {
            log.error("Method failed: {}.{} - Error: {}", className, methodName, e.getMessage());
            throw e;
        }
    }
}
