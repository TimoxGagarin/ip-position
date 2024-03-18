package com.ip_position.ipposition.logger;

import java.util.Arrays;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

    private Logger logger;

    public LoggerAspect() {
        this.logger = Logger.getLogger(this.getClass().getName());
    }

    @Before("execution(* com.ip_position.ipposition.services.*.*(..))")
    public void logBeforeServiceCommand(JoinPoint joinPoint) {
        logger.info(() -> String.format("Invoke %s with args %s", joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs())));
    }

    @AfterReturning(pointcut = "execution(* com.ip_position.ipposition.services.*.*(..))", returning = "result")
    public void logAfterServiceCommand(JoinPoint joinPoint, Object result) {
        if (result != null)
            logger.info(() -> String.format("Result of %s with args %s: %s", joinPoint.getSignature().getName(),
                    Arrays.toString(joinPoint.getArgs()), result.toString()));
        else
            logger.info(() -> String.format("Result of %s with args %s: success", joinPoint.getSignature().getName(),
                    Arrays.toString(joinPoint.getArgs())));
    }

    @AfterThrowing(pointcut = "execution(* com.ip_position.ipposition.*.*.*(..))", throwing = "exception")
    public void logAfterError(JoinPoint joinPoint, Exception exception) {
        logger.warning(
                () -> String.format("Error until running %s with args %s: %s", joinPoint.getSignature().getName(),
                        Arrays.toString(joinPoint.getArgs()), exception.getMessage()));
    }
}