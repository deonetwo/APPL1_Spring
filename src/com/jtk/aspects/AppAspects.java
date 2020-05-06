package com.jtk.aspects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.mongodb.core.MongoOperations;

@Aspect
public class AppAspects {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	@Before("execution(* *.*(..))")
    public void runBefore(JoinPoint joinPoint) {
        log.info("Method " + joinPoint.getSignature().getName() + " () telah dijalankan");
    }
	
	@AfterReturning(pointcut = "execution(** com.jtk.core.AppInterface.userLogin(..))", returning = "username")
    public void runAfterReturning(JoinPoint joinPoint, String username) {
        if(username != null) {
        	System.out.println("Selamat Datang! "+ username);
        }
    }
}
