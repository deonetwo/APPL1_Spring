package com.jtk.model;

public class Notification {
	private User from;
	private String message;
	
	public User getFrom() {
		return from;
	}
	public void setFrom(User from) {
		this.from = from;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	} 
}
