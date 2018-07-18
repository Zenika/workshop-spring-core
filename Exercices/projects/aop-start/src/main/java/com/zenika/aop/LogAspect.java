/**
 * 
 */
package com.zenika.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author acogoluegnes
 *
 */
// TODO 03 configure the aspect
// (@Aspect, @Around and pointcut expression)
// the aspect must be applied on service layer
public class LogAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);
	
	public Object log(ProceedingJoinPoint jp) throws Throwable {
		LOGGER.info("Calling {}",jp.getSignature().toShortString());
		Object res = jp.proceed();
		LOGGER.info("Calling done");
		return res;
	}
	
}
