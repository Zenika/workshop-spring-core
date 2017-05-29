/**
 * 
 */
package com.zenika;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.zenika.aop.LogAspect;

/**
 * @author acogoluegnes
 *
 */
@Configuration
@EnableAspectJAutoProxy
public class AopConfiguration {

	@Bean public LogAspect logAspect() {
		return new LogAspect();
	}
	
}
