package com.jtk.aspects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class AppAspects {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	@Before("execution(* *.*(..))")
    public void runBefore(JoinPoint joinPoint) {
        log.info("Method " + joinPoint.getSignature().getName() + " () telah dijalankan");
    }
}
