package com.jtk.core;

import java.util.Scanner;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.jtk.model.User;

public class AppMain implements AppInterface{

	public static final int SIGN_IN = 1;
	public static final int SIGN_UP = 2;
	
	int choice;
	Scanner in = new Scanner(System.in);
	
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

	public boolean userLogin(MongoOperations mongoOperation) {
		frontMenu();
		choice = in.nextInt();
		in.nextLine();
		String username;
		switch(choice) {
			case SIGN_IN:{
				System.out.println("Username: ");
				username = in.nextLine();
				
				System.out.println("\nSigning in..");
				Query searchUserQuery = new Query(Criteria.where("username").is(username));
				
				//if user exist, perform app
				if(mongoOperation.exists(searchUserQuery, User.class)) {
					User signedUser = mongoOperation.findOne(searchUserQuery, User.class);
					return true;
				} else {
					System.out.println("User doesn't exist.");
					return false;
				}
			}
			case SIGN_UP:{
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
    				System.out.println("\nUser created.");
				}
			}
			
			default:
				System.out.println("Please enter the right number.\n");
			break;
		}
		return false;
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

	public int userMainMenu() {
		displayMenu();
		choice = in.nextInt();
		in.nextLine();
		return choice;
	}

	public void userAddFriend() {
		// TODO Auto-generated method stub
		System.out.println("Adding Friends.");
	}

	public void userNotification() {
		// TODO Auto-generated method stub
		
	}

	public void userFriends() {
		// TODO Auto-generated method stub
		
	}

	public void userMentioned() {
		// TODO Auto-generated method stub
		
	}

}
