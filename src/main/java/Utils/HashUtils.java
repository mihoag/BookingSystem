package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
	public static String hashString(String input) {
	   try {
	       MessageDigest digest = MessageDigest.getInstance("SHA-256");
	       byte[] hashBytes = digest.digest(input.getBytes());
	       StringBuilder sb = new StringBuilder();
	       for (byte b : hashBytes) {
	           sb.append(String.format("%02x", b));
	       }
	       return sb.toString();
	      } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	      }
	}
	
	public static boolean compareHashes(String originalHash, String newHash) {
        return originalHash.equals(newHash);
    }
}
