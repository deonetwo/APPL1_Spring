package com.jtk.core;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.bson.types.ObjectId;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.jtk.config.MongoConfig;
import com.jtk.model.Notification;
import com.jtk.model.User;
import com.jtk.aspects.AppAspects;
//import org.springframework.context.support.GenericXmlApplicationContext;

public class AppController {
	public static final int SIGN_IN = 1;
	public static final int SIGN_UP = 2;
	
	public static final int ADD_FRIEND = 1;
	public static final int VIEW_FRIEND = 2;
	public static final int VIEW_NOTIF = 3;
	public static final int MENTION = 4;
	public static final int SIGN_OUT = 5;
	
	private static User signedUser;
	private static AppAspects aspects;
	
	// For Annotation
	static ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
	static MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
	
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("file:src/resources/beans.xml");
	//static MongoOperations user = (MongoOperations) context.getBean("mongoTemplate", MongoConfig.class);
    
    public static void frontMenu() {
    	System.out.println("[ H E L L O ! ]");
    	System.out.println("1. Sign in");
    	System.out.println("2. Sign up");
    	System.out.println("Choose a number: ");
    }
    
    public static void displayMenu() {
    	System.out.println("[ M A I N  M E N U ]");
    	System.out.println("1. Add friend");
    	System.out.println("2. View friendlist");
    	System.out.println("3. View notification(s)");
    	System.out.println("4. Mention friend(s)");
    	System.out.println("5. Sign out");
    	System.out.println("Choose a number: ");
    }

    public static void run() {
    	String username;
    	int choice;
    	Scanner in = new Scanner(System.in);
    	
    	while(true) {
    		frontMenu();
    		choice = in.nextInt();
    		in.nextLine();
    		
    		switch(choice) {
    			case SIGN_IN:
    				System.out.println("Username: ");
    				username = in.nextLine();
    				
    				System.out.println("\nSigning in..");
    				Query searchUserQuery = new Query(Criteria.where("username").is(username));
    				
    				//if user exist, perform app
    				if(mongoOperation.exists(searchUserQuery, User.class)) {
    					signedUser = mongoOperation.findOne(searchUserQuery, User.class);
    					perform();
    				} else {
    					System.out.println("User doesn't exist.");
    				}
    			break;
    			
    			case SIGN_UP:
    				System.out.println("Username: ");
    				username = in.nextLine();
    				
    				searchUserQuery = new Query(Criteria.where("username").is(username));
    				
    				if(mongoOperation.exists(searchUserQuery, "users")) {
    					System.out.println("Username is already exist.\n");
    				} else {
    					String id = new ObjectId().toString();
        				User newUser = new User(id, username);
        				
        				System.out.println("\nCreating..");
        				mongoOperation.save(newUser);
        				System.out.println("\nUser created.");
    				}
    				
    			break;
    			
    			default:
    				System.out.println("Please enter the right number.\n");
    			break;
    		}
    	}
    }
    
    public static void perform() {
    	String username;
    	boolean exit = false;
    	
    	System.out.println("\nSign in success\n");
    	
    	while(!exit) {
    		displayMenu();
    		Scanner in = new Scanner(System.in);
    		int choice = in.nextInt();
    		in.nextLine();
    		
    		switch(choice) {
    			case ADD_FRIEND:
    				System.out.println("Username: ");
    				username = in.nextLine();
    				
    				System.out.println("\nAdding..");
    				Query searchUserQuery = new Query(Criteria.where("username").is(username));
    				
    				//if user exist, perform app
    				if(mongoOperation.exists(searchUserQuery, User.class)) {
    					
    					User user = mongoOperation.findOne(searchUserQuery, User.class);
    					
    					List<User> friendList = signedUser.getFriendList();
    					List<User> hisFriendList = user.getFriendList();
    					
    					//TODO if friend is not in friendList && not in hisFriendLsit
    					if(true) {
    						friendList.add(user);
        					signedUser.setFriendList(friendList);
        					searchUserQuery = new Query(Criteria.where("username").is(signedUser.getUsername()));
        					mongoOperation.updateFirst(searchUserQuery, Update.update("friendList", friendList), User.class);
        					
        					hisFriendList.add(signedUser);
        					user.setFriendList(hisFriendList);
        					searchUserQuery = new Query(Criteria.where("username").is(user.getUsername()));
        					
        					//TODO fix error ini :(
//        					mongoOperation.updateFirst(searchUserQuery, Update.update("friendList", hisFriendList), User.class);
        					
        					System.out.println("\nFriend with username [" + username + "] added\n");
    					}
    				} else {
    					System.out.println("User cannot be added.\n");
    				}
    			break;
    			
    			case VIEW_FRIEND:
    				System.out.println("My Friend List");
    				List<User> friendList = signedUser.getFriendList();
    				Iterator<User> fListIter = friendList.iterator();
    				
    				while(fListIter.hasNext()) {
    					System.out.println(fListIter.next().getUsername());
    				}
    				
    				System.out.println("");
    			break;
    			
    			case VIEW_NOTIF:
    				System.out.println("Notifications");
    				List<Notification> notifList = signedUser.getNotifList();
    				Iterator<Notification> nListIter = notifList.iterator();
    				
    				while(nListIter.hasNext()) {
    					System.out.println("[" + nListIter.next().getTime() + "] " +
			    							nListIter.next().getFrom() + ": " +
			    							nListIter.next().getMessage());
    				}
    				
    				System.out.println("");
        		break;
        		
    			case MENTION:
    				System.out.println("To: ");
    				String user = in.nextLine();
    				System.out.println("Message: ");
    				String message = in.nextLine();
    				
    				System.out.println("Processing..");
    				searchUserQuery = new Query(Criteria.where("username").is(user));
					User recipient = mongoOperation.findOne(searchUserQuery, User.class);
					mongoOperation.updateFirst(searchUserQuery, Update.update("notifList", recipient.getNotifList()), User.class);
    				
    				aspects.mention(signedUser, recipient, message);
    				
    				System.out.println("Mention success\n");
        		break;
        		
    			case SIGN_OUT:
    				System.out.println("\nSigning out..\n");
    				signedUser = null;
    				
    				exit = true;
        		break;
    			
    			default:
    				System.out.println("Please enter the right number.\n");
    			break;
    		}
    	}
    }
}