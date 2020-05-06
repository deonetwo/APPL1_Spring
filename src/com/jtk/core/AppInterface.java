package com.jtk.core;

import org.springframework.data.mongodb.core.MongoOperations;

import com.jtk.model.User;

public interface AppInterface {
	public User userLogin(MongoOperations mongoOperation);
	public int userMainMenu(int newNotif);
	public void userAddFriend(MongoOperations mongoOperation, User signedUser);
	public void userNotification(User signedUser);
	public void userFriends(User signedUser);
	public void userMentioned(MongoOperations mongoOperation, User signedUser);
	public User signOut();
}
