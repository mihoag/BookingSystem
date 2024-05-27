package Utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TimeZoneUtitls {
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
    public static List<LocalTime> getTimeZoneFromString(String timeZone)
    {
    	
    	List<LocalTime> timeComponents = new ArrayList<>();
    	LocalTime fromTimeLocalTime = null;
    	LocalTime toTimeLocalTime = null;
    	
    	String[] components = timeZone.split("-");
    	try {
    		fromTimeLocalTime = LocalTime.parse(components[0], formatter);
        	toTimeLocalTime = LocalTime.parse(components[1], formatter);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	timeComponents.add(fromTimeLocalTime);
    	timeComponents.add(toTimeLocalTime);
    	
    	return timeComponents;
    }
    
    public static String getTimeZoneValueAsString(String fromTime, String toTime)
    {
    	return fromTime + "-" + toTime;
    }
   
}
