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
// TODO 03 compléter la classe pour configurer l'aspect
// (annotation @Aspect, annotation @Around et expression du pointcut)
// l'aspect doit s'appliquer sur la couche service
public class LogAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);
	
	public Object log(ProceedingJoinPoint jp) throws Throwable {
		LOGGER.info("Appel à {}",jp.getSignature().toShortString());
		Object res = jp.proceed();
		LOGGER.info("Fin de l'appel");
		return res;
	}
	
}
