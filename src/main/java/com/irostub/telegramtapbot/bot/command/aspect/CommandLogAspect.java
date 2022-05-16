package com.irostub.telegramtapbot.bot.command.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class CommandLogAspect {
    @Around("execution(* com.irostub.telegramtapbot.bot.command.Commandable.execute(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String className = joinPoint.getTarget().getClass().getName();
        for (Object arg : args) {
            if (arg instanceof String) {
                log.debug("["+className+".execute] commandableString = {}", arg);
            }
        }
        joinPoint.proceed();
        return this;
    }
}
