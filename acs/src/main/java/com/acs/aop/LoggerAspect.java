package com.acs.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Around("execution(* com.ssv..controller.*Controller.*(..)) or execution(* com.ssv..service.*Impl.*(..)) or execution(* com.ssv..dao.*Dao.*(..))")
	public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName();

		if (name.contains("Controller") == true) {
			type = "Controller ===> ";

		} else if (name.contains("Service") == true) {
			type = "ServiceImpl ===> ";

		} else if (name.contains("Mapper") == true) {
			type = "Mapper ===> ";
		}

		logger.info(type + name + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}

}