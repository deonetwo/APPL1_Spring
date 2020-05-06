package com.jtk.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.jtk.config.MongoConfig;

public class App {
	public static final int ADD_FRIEND = 1;
	public static final int VIEW_FRIEND = 2;
	public static final int VIEW_NOTIF = 3;
	public static final int MENTION = 4;
	public static final int SIGN_OUT = 5;

	public static void main(String[] args) {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

		ApplicationContext context = new ClassPathXmlApplicationContext("resources/beans.xml");
		AppInterface app = (AppInterface) context.getBean("app");

		String loggedUsername = null;
		while (true) {
			loggedUsername = app.userLogin(mongoOperation);
			while (loggedUsername != null) {
				switch (app.userMainMenu()) {
					case ADD_FRIEND:
						app.userAddFriend(mongoOperation);
						break;
					case VIEW_FRIEND:
						app.userFriends(mongoOperation);
						break;
					case VIEW_NOTIF:
						app.userNotification(mongoOperation);
						break;
					case MENTION:
						app.userMentioned(mongoOperation);
						break;
					case SIGN_OUT:
						loggedUsername = app.userLogout();
						break;
				}
			}
		}
	}
}