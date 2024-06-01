package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileOperationUtils {
	
    public static void writeFile(Object obj, String filePath)
    {
    	 try {
			ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream(filePath));
			objectOutput.writeObject(obj);
			objectOutput.close();
		 } catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
			e.printStackTrace();
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
    }
    
    public static Object readFile(String filePath) throws ClassNotFoundException
    {
    	try (ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(filePath));) {
			return objectInput.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
}
