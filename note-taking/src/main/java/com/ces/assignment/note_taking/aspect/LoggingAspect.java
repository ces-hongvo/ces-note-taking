package com.ces.assignment.note_taking.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.ces.assignment.note_taking.service..*.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = methodSignature.getName();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        StopWatch stopWatch = new StopWatch();
        
        try {
            // Log method entry
            logger.info("==> {}.{}() started with parameters: {}", 
                className, methodName, formatParameters(parameterNames, args));
            
            stopWatch.start();
            Object result = joinPoint.proceed();
            stopWatch.stop();

            // Log method exit
            /*
            logger.info("<== {}.{}() completed in {}ms with result: {}", 
                className, methodName, stopWatch.getTotalTimeMillis(), 
                result != null ? result : "void");
             */

            logger.info("<== {}.{}() completed in {}ms",
                    className, methodName, stopWatch.getTotalTimeMillis());

            return result;
        } catch (Exception e) {
            // Log error
            logger.error("!!! {}.{}() failed with exception: {}", 
                className, methodName, e.getMessage());
            logger.error("Exception stack trace:", e);
            throw e;
        }
    }

    private String formatParameters(String[] parameterNames, Object[] args) {
        StringBuilder params = new StringBuilder();
        for (int i = 0; i < parameterNames.length; i++) {
            if (i > 0) {
                params.append(", ");
            }
            params.append(parameterNames[i]).append("=");
            
            // Handle sensitive data
            if (isSensitiveParameter(parameterNames[i])) {
                params.append("*****");
            } else {
                params.append(formatValue(args[i]));
            }
        }
        return params.toString();
    }

    private boolean isSensitiveParameter(String paramName) {
        return paramName != null && (
            paramName.toLowerCase().contains("password") ||
            paramName.toLowerCase().contains("token") ||
            paramName.toLowerCase().contains("secret")
        );
    }

    private String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value.getClass().isArray()) {
            return Arrays.toString((Object[]) value);
        }
        return value.toString();
    }
} 