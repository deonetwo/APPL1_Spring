package com.jtk.aspects;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.mongodb.core.MongoOperations;

import com.jtk.model.Notification;
import com.jtk.model.User;

@Aspect
public class AppAspects {

	private Log log = LogFactory.getLog(this.getClass());

	@Before("execution(* *.*(..))")
	public void runBefore(JoinPoint joinPoint) {
		log.info("Method " + joinPoint.getSignature().getName() + " () telah dijalankan");
	}

	@Pointcut("execution(** com.jtk.core.AppMain.userMainMenu(int))" + "&& args(notif)")
	public void showNotification(int notif) {
	}

	@Before("showNotification(notif)")
	public void notifShow(int notif) {
		System.out.println("You have " + notif + " new notification!");
	}

	@After("execution(* com.jtk.core.AppMain.userMentioned(..))")
	public void stateAfter(JoinPoint joinPoint) {
		System.out.println("Notification to: " + joinPoint.getArgs()[0].toString() + " sent\n");
	}
}
