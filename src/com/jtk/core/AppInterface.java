package com.jtk.core;

import org.springframework.data.mongodb.core.MongoOperations;

public interface AppInterface {
	public void run();
	public boolean userLogin(MongoOperations mongoOperation);
	public int userMainMenu();
	public void userAddFriend();
	public void userNotification();
	public void userFriends();
	public void userMentioned();
}
