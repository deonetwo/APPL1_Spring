package com.jtk.aspects;

import java.util.List;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Pointcut;

import com.jtk.model.Notification;
import com.jtk.model.User;

public class AppAspects {
	@Pointcut(
			"within(com.jtk.core.App.perform()) && args(sender, recipient, message)")
	public void mention(User sender, User recipient, String message) { }
	
	@After("execution(public void mention(sender, recipient, message)")
	public void sendNotification(User sender, User recipient, String message) {
		System.out.println("Sending notification to " + recipient.getUsername());
		Notification notif = new Notification(sender, message);
		List<Notification> notificationList = recipient.getNotifList();
		notificationList.add(notif);
		recipient.setNotifList(notificationList);
		System.out.println("Notification to: " + recipient.getUsername() + " sent");
	}
}
