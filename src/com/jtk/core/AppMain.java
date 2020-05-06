package com.jtk.core;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.jtk.model.Notification;
import com.jtk.model.User;

public class AppMain implements AppInterface {

	public static final int SIGN_IN = 1;
	public static final int SIGN_UP = 2;

	int choice;
	Scanner in = new Scanner(System.in);

	public void frontMenu() {
    	System.out.println("*** Hello!App ***");
    	System.out.println("1. Sign in");
    	System.out.println("2. Sign up");
    	System.out.println("Choose a number: ");
    }
	
	public void displayMenu() {
		System.out.println("*** Main Menu ***");
    	System.out.println("1. Add friend");
    	System.out.println("2. View friendlist");
    	System.out.println("3. View notification(s)");
    	System.out.println("4. Mention friend(s)");
    	System.out.println("5. Sign out");
    	System.out.println("Choose a number: ");
	}
	
	
	public User userLogin(MongoOperations mongoOperation) {
		frontMenu();
		choice = in.nextInt();
		in.nextLine();
		String username;
		User signedUser;
		
		switch(choice) {
			case SIGN_IN:
			{
				System.out.println("\n*** Sign In ***\n");
				System.out.println("Username: ");
				username = in.nextLine();

				System.out.println("\nSigning in..");
				Query searchUserQuery = new Query(Criteria.where("username").is(username));

				//if user exist, perform app
				if(mongoOperation.exists(searchUserQuery, User.class)) {
					signedUser = mongoOperation.findOne(searchUserQuery, User.class);
					return signedUser;
				} else {
					System.out.println("User doesn't exist.");
					return null;
				}
			}
			
			case SIGN_UP:
			{
				System.out.println("\n*** Sign Up ***\n");
				System.out.println("Username: ");
				username = in.nextLine();

				Query searchUserQuery = new Query(Criteria.where("username").is(username));

				if(mongoOperation.exists(searchUserQuery, "users")) {
					System.out.println("Username is already exist.\n");
				} else {
					String id = new ObjectId().toString();
    				User newUser = new User(id, username);

    				System.out.println("\nCreating..");
    				mongoOperation.save(newUser);
    				System.out.println("User created\n");
				}
			}	
			break;

			default:
				System.out.println("Please enter the right number.\n");
			break;
		}
		
		return null;
	}
	
	public int userMainMenu(int newNotif) {
		displayMenu();
		choice = in.nextInt();
		in.nextLine();
		return choice;
	}

	@Override
	public void userAddFriend(MongoOperations mongoOperation, User signedUser) {
		System.out.println("\n*** Add Friend ***\n");
		System.out.println("Username: ");
		String username = in.nextLine();
		
		System.out.println("\nAdding..");
		Query searchUserQuery = new Query(Criteria.where("username").is(username));
		
		if(mongoOperation.exists(searchUserQuery, User.class)) {
			
			User user = mongoOperation.findOne(searchUserQuery, User.class);
			
			List<User> friendList = signedUser.getFriendList();
			List<User> hisFriendList = user.getFriendList();
			
			friendList.add(user);
			signedUser.setFriendList(friendList);
			searchUserQuery = new Query(Criteria.where("username").is(signedUser.getUsername()));
			mongoOperation.updateFirst(searchUserQuery, Update.update("friendList", friendList), User.class);
			
			System.out.println("Friend with username [" + username + "] added\n");
		} else {
			System.out.println("User cannot be added.\n");
		}
	}
	
	@Override
	public void userNotification(User signedUser) {
		System.out.println("\n*** Notifications ***\n");
		List<Notification> notifList = signedUser.getNotifList();
		Iterator<Notification> nListIter = notifList.iterator();
		
		while(nListIter.hasNext()) {
			Notification current = nListIter.next();
			System.out.println("[" + current.getTime() + "] " +
									current.getFrom().getUsername() + ": " +
									current.getMessage());
		}
		signedUser.setNewNotif(0);
		System.out.println("");
	}
	
	@Override
	public void userFriends(User signedUser) {
		System.out.println("\n*** My Friend List ***\n");
		List<User> friendList = signedUser.getFriendList();
		Iterator<User> fListIter = friendList.iterator();
		
		while(fListIter.hasNext()) {
			System.out.println(fListIter.next().getUsername());
		}
		
		System.out.println("");
	}
	
	public void sendNotif(User recipient, String message, MongoOperations mongoOperation, User signedUser) {
		Notification notif = new Notification(signedUser, "@" + recipient.getUsername() + " " + message);
		List<Notification> notificationList = recipient.getNotifList();
		notificationList.add(notif);
		recipient.setNotifList(notificationList);
		int newNotif = recipient.getNewNotif() + 1;
		
		Query searchUserQuery = new Query(Criteria.where("username").is(recipient.getUsername()));
		mongoOperation.updateFirst(searchUserQuery, Update.update("notifList", notificationList), User.class);
		mongoOperation.updateFirst(searchUserQuery, Update.update("newNotif", newNotif), User.class);
	}

	public void userMentioned(MongoOperations mongoOperation, User signedUser) {
		System.out.println("\n*** Mention ***\n");
		Scanner in = new Scanner(System.in);
		System.out.println("To: ");
		String user = in.nextLine();
		System.out.println("Message: ");
		String message = in.nextLine();
		
		System.out.println("Processing..");
		Query searchUserQuery = new Query(Criteria.where("username").is(user));
		User recipient = mongoOperation.findOne(searchUserQuery, User.class);
		if(recipient != null) {
			sendNotif(recipient, message, mongoOperation, signedUser);
		}
		else {
			System.out.println("Mention failed. User not found.");
		}
	}
	
	public User signOut() {
		System.out.println("\nSigning out..\n");
		return null;
	}
}
