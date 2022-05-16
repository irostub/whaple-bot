package com.irostub.telegramtapbot.bot.command.food;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class FoodCommandAspect {
    @Around("execution(* com.irostub.telegramtapbot.bot.command.food.FoodService.run(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String className = joinPoint.getTarget().getClass().getName();
        for (Object arg : args) {
            if (arg instanceof String) {
                log.debug("["+className+".run] option = {}", arg);
            }
        }
        joinPoint.proceed();
        return this;
    }
}