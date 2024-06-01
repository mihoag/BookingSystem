package models;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable{
   
   private static final long serialVersionUID = 1L;
   private String username;
   private String password;
   private String tel;
   private String fullname;
  
   public User() {
	super();
	// TODO Auto-generated constructor stub
   }

   public User(String username, String password, String tel, String fullname) {
	 super();
	 this.username = username;
	 this.password = password;
	 this.tel = tel;
	 this.fullname = fullname;
   }
   
   public User(String username, String password) {
	 super();
	 this.username = username;
	 this.password = password;
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
   public String getTel() {
	 return tel;
   }
   public void setTel(String tel) {
	 this.tel = tel;
   }
   @Override
   public int hashCode() {
	 return Objects.hash(password, username);
   }
   @Override
   public boolean equals(Object obj) {
	 if (this == obj)
		return true;
   	 if (obj == null)
		return false;
	 if (getClass() != obj.getClass())
		return false;
	User other = (User) obj;
	return Objects.equals(password, other.password) && Objects.equals(username, other.username);
  }  
}
