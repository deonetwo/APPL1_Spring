package com.jtk.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notifications")
public class Notification {
	private User sender;
	private String message;
	private LocalDateTime time;
	
	public Notification(User sender, String message) {
		this.sender = sender;
		this.message = message;
		this.time = LocalDateTime.now();
	}

	public User getFrom() {
		return sender;
	}

	public void setFrom(User from) {
		this.sender = from;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
}
