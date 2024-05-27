package storeage;

import Utils.FileOperationUtils;

public class UserDataStorage implements DataStoreInterface{

	private static UserDataStorage connection = null;
	private final String storageFile = "UserData.dat";
	
	public static UserDataStorage getInstance()
	{
	   if(connection == null)
	   {
	    connection = new UserDataStorage();
	   }
	   return connection;
	}
	
	@Override
	public void writeFile(Object obj) {
		// TODO Auto-generated method stub
		FileOperationUtils.writeFile(obj, storageFile);
	}

	@Override
	public Object readFile() throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return FileOperationUtils.readFile(storageFile);
	}
}
