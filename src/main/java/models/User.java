package models;

public class User {
   public String username;
   public String password;
   public String fullname;
   
   public User() {
	super();
	// TODO Auto-generated constructor stub
   }
  
   public User(String username, String password, String fullname) {
	super();
	this.username = username;
	this.password = password;
	this.fullname = fullname;
   }
   public String getUsername() {
	 return username;
   }
   public void setUsername(String username) {
	 this.username = username;
   }
   public String getPassword() {
	 return password;
   }
   public void setPassword(String password) {
	 this.password = password;
   }
   public String getFullname() {
	 return fullname;
   }
   public void setFullname(String fullname) {
	 this.fullname = fullname;
   }
  
}
