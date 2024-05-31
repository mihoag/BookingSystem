package models;

import java.io.Serializable;

public class Seat implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private boolean status;
	private User user;

	public Seat()
	{
		// Initialize status's value is false ( not be booked)
		this.status = false;
		user = null;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
