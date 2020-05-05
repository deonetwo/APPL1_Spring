package com.jtk.model;

import java.time.LocalDateTime;

public class Notification {
	private User from;
	private String message;
	private LocalDateTime time;
	
	public Notification(User sender, String message) {
		this.from = sender;
		this.message = message;
		this.time = LocalDateTime.now();
	}

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

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
}
