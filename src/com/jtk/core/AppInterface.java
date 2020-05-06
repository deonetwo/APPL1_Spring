package com.jtk.core;

import org.springframework.data.mongodb.core.MongoOperations;

import com.jtk.model.User;

public interface AppInterface {
	public boolean userLogin(MongoOperations mongoOperation);
	public int userMainMenu();
	public void userAddFriend(MongoOperations mongoOperation);
	public void userNotification();
	public void userFriends();
	public void userMentioned(MongoOperations mongoOperation);
	public void signOut();
}
