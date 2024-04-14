package com.ip_position.ipposition.logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.ip_position.ipposition.utils.RequestCounter;

@Aspect
@Component
public class LoggerAspect {

    private final RequestCounter requestCounter;
    private Logger logger;

    public LoggerAspect(RequestCounter requestCounter) {
        this.logger = Logger.getLogger(this.getClass().getName());
        this.requestCounter = requestCounter;
        try {
            FileHandler fh = new FileHandler("logs.log", true);
            this.logger.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());
            this.logger.info("Log message");
        } catch (SecurityException | IOException e) {
            this.logger.log(Level.SEVERE, "Произошла ошибка при работе с FileHandler.", e);
        }
    }

    @Before("execution(* com.ip_position.ipposition.services.*.*(..))")
    public void logBeforeServiceCommand(JoinPoint joinPoint) {
        logger.info(() -> String.format("Invoke %s with args %s", joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs())));
    }

    @AfterReturning(pointcut = "execution(* com.ip_position.ipposition.services.IpInfoService.*(..))", returning = "result")
    public void incrementCounter(JoinPoint joinPoint, Object result) {
        requestCounter.increment();
        String methodName = joinPoint + " "
                + joinPoint.getSignature().getName();
        logger.info(() -> String.format("Request Counter: %d - %s%n", requestCounter.getCount(), methodName));
    }

    @AfterReturning(pointcut = "execution(* com.ip_position.ipposition.services.*.*(..))", returning = "result")
    public void logAfterServiceCommand(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint + " "
                + joinPoint.getSignature().getName();
        if (result != null)
            logger.info(() -> String.format("Result of %s with args %s: %s", methodName,
                    Arrays.toString(joinPoint.getArgs()), result.toString()));
        else
            logger.info(() -> String.format("Result of %s with args %s: success", methodName,
                    Arrays.toString(joinPoint.getArgs())));
    }

    @AfterThrowing(pointcut = "execution(* com.ip_position.ipposition.*.*.*(..))", throwing = "exception")
    public void logAfterError(JoinPoint joinPoint, Exception exception) {
        logger.warning(
                () -> String.format("Error until running %s with args %s: %s", joinPoint.getSignature().getName(),
                        Arrays.toString(joinPoint.getArgs()), exception.getMessage()));
    }
}