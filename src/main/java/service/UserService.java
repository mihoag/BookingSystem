package service;

import java.util.ArrayList;
import java.util.List;

import models.User;
import repository.UserRepository;

public class UserService {

	
    public List<User> getListUser()
    {
        try {
			List<User> users =  UserRepository.getInstance().getUsers();
	        if(users != null)
	        {
	           return users;
	        }
	        return new ArrayList<>();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ArrayList<>();
    }
	
    public User getUserByUsername(String username)
    {
    	try {
			List<User> listUser = UserRepository.getInstance().getUsers();
			for(User user : listUser)
			{
				if(user.getUsername().equals(username))
				{
					return user;
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    boolean isUserNameExist(String username)
    {
    	List<User> listUsers = getListUser();
    	for(User user : listUsers)
    	{
    	     if(user.getUsername().equals(username))
    	     {
    	    	return true;
    	     }
    	}
    	return false;
    }
    
    public boolean addUser(User user)
    {
       boolean isExisted = isUserNameExist(user.getUsername());
       if(!isExisted)
       {
    	    List<User> users = getListUser();
    	    users.add(user);
    	    UserRepository.getInstance().writeUsersData(users);
    	    return true;
       }
       else
       {
    	   return false;
       } 
    }
    
    
    
   
}
