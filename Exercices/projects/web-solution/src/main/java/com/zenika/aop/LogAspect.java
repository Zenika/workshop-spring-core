/**
 * 
 */
package com.zenika.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author acogoluegnes
 *
 */
@Aspect
public class LogAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);
	
	@Around("execution(* com.zenika.business.*.*(..))")
	public Object log(ProceedingJoinPoint jp) throws Throwable {
		LOGGER.info("Calling {}",jp.getSignature().toShortString());
		Object res = jp.proceed();
		LOGGER.info("Calling done");
		return res;
	}
	
}
