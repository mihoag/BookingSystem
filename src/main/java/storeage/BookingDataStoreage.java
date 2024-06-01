package storeage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import utils.FileOperationUtils;

public class BookingDataStoreage implements DataStoreInterface{
	    private static BookingDataStoreage connection = null;
	    private final String storageFile = "BookingInfoData.dat";
	    public static BookingDataStoreage getInstance()
	    {
	    	if(connection == null)
	    	{
	    		connection = new BookingDataStoreage();
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
