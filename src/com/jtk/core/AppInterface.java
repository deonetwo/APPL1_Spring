package com.jtk.core;

import org.springframework.data.mongodb.core.MongoOperations;

public interface AppInterface {
	public int userMainMenu();
	public String userLogin(MongoOperations mongoOperation);
	public void userAddFriend(MongoOperations mongoOperation);
	public void userNotification(MongoOperations mongoOperation);
	public void userFriends(MongoOperations mongoOperation);
	public void userMentioned(MongoOperations mongoOperation);
	public String userLogout();
}
