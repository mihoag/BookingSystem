package models;

import java.io.Serializable;

public class Seat implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private boolean status;
	
	public Seat()
	{
		// Initialize status's value is false ( not be booked)
		this.status = false;
	}
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
