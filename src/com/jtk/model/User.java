package com.jtk.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "users")
public class User {
	@Id
	private String id;
	private String username;

	private List<User> friendList;
	private List<Notification> notifList;
	
	public User(String id, String username) {
		this.id = id;
		this.username = username;
		this.friendList = new ArrayList<>();
		this.notifList = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<User> getFriendList() {
		return friendList;
	}

	public void setFriendList(List<User> friendList) {
		this.friendList = friendList;
	}

	public List<Notification> getNotifList() {
		return notifList;
	}

	public void setNotifList(List<Notification> notifList) {
		this.notifList = notifList;
	}
}
