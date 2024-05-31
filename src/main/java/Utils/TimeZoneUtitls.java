package Utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import models.MovieTime;

public class TimeZoneUtitls {
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	public static String[] splitTimeZone(String timeZone)
	{
		String[] components = timeZone.split("-");
		return components;
	}
	
	
	
	public static boolean checkTimeFormat(String time)
	{
        try {
			LocalTime.parse(time, formatter);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}      
	}
	
	
    public static List<LocalTime> getTimeZoneFromString(String timeZone)
    {
    	
    	List<LocalTime> timeComponents = new ArrayList<>();
    	LocalTime fromTimeLocalTime = null;
    	LocalTime toTimeLocalTime = null;
    	
    	String[] components = splitTimeZone(timeZone);
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
    
    public static List<String> getListTimeZoneAsString(List<MovieTime> listMovieTimes)
    {
     List<String> movieTimeAsString = new ArrayList<>();	 
     if(listMovieTimes != null)
     {
       for(MovieTime movieTime : listMovieTimes)
       {
       	movieTimeAsString.add(TimeZoneUtitls.getTimeZoneValueAsString(movieTime.getFromTime().format(formatter), movieTime.getToTime().format(formatter)));
       } 
     }
     
     // Sort ASC 
     Collections.sort(movieTimeAsString, new Comparator<String>() {

		@Override
		public int compare(String o1, String o2) {
			// TODO Auto-generated method stub
			return o1.compareTo(o2);
		}
    	 
	 });
   	 return movieTimeAsString;
    }
    
    public static String getTimeZoneValueAsString(String fromTime, String toTime)
    {
    	return fromTime + "-" + toTime;
    }
     
    public static List<Object> getBookingInfoFromString(String bookingData)
    {
    	List<Object> bookingInfo = new ArrayList<>();
    	String[] components = bookingData.split("\\|");
        String username = components[0];
        String timeZone = components[1];
        String seatId = components[2];
       
        String[] localTimeComponents = splitTimeZone(timeZone); 
        List<Object> seatIdComponents = SeatUtils.splitSeatId(seatId);
        
        bookingInfo.add(username);
        bookingInfo.add(LocalTime.parse(localTimeComponents[0], formatter));
        bookingInfo.add(LocalTime.parse(localTimeComponents[1], formatter));
        bookingInfo.add(seatIdComponents.get(0));
        bookingInfo.add(seatIdComponents.get(1));
        bookingInfo.add(seatIdComponents.get(2));
        
        return bookingInfo;
    }
    
    public static void main(String[] args) {
		getBookingInfoFromString("minhhoang|07:00:00-08:00:00|A-1-2");
	}
   
}
