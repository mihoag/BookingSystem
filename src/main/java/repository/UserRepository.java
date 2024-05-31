package repository;

import java.util.ArrayList;
import java.util.List;

import models.MovieTime;
import models.User;
import storeage.UserDataStorage;


public class UserRepository {

	public static UserRepository repo = null;
	
	public static UserRepository getInstance()
	{
		if(repo == null)
		{
			repo = new UserRepository();
		}
		return repo;
	}
	
	public List<User> getUsers() throws ClassNotFoundException
	{
	   UserDataStorage connection = UserDataStorage.getInstance();
	   List<User> listUsers = (ArrayList<User>) connection.readFile();
	   return listUsers;
	}
	   
	public void writeUsersData(List<User> users)
	{
	  UserDataStorage connection = UserDataStorage.getInstance();
      connection.writeFile(users);
	}
}
