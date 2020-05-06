package com.jtk.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.jtk.config.MongoConfig;
import com.jtk.model.User;

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

		boolean userLogged;
		
		while (true) {
			userLogged = app.userLogin(mongoOperation);
			
			while (userLogged) {
				switch (app.userMainMenu()) {
					case ADD_FRIEND:
						app.userAddFriend(mongoOperation);
					break;
						
					case VIEW_FRIEND:
						app.userFriends();
					break;
						
					case VIEW_NOTIF:
						app.userNotification();
					break;
						
					case MENTION:
						app.userMentioned(mongoOperation);
					break;
						
					case SIGN_OUT:
						app.signOut();
						userLogged = false;
					break;
					
					default:
						System.out.println("Please enter the right number.\n");
					break;
				}
			}
		}
	}
}