package service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import models.Movie;
import models.MovieTheater;
import models.MovieTime;
import models.Seat;
import models.User;
import models.Zone;
import repository.MovieTimeRepository;
import utils.SeatUtils;
import utils.TimeZoneUtitls;

public class MovieTimeService {
	

     /*public MovieTime getMovieTimeFromTimeZone(LocalTime fromTimeLocalTime, LocalTime endTimeLocalTime)
     {
    
		try {
			 List<MovieTime> movieTimes;
		     
	    	 for(MovieTime movieTime : movieTimes)
	    	 {
	    	   if(movieTime.getFromTime().equals(fromTimeLocalTime) && movieTime.getToTime().equals(endTimeLocalTime))
	    	   {
	    		   return movieTime;
	    	   }
	    	 }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	return null;
     }
     */
     
     
     /*public boolean deleteZoneByName(String name)
     {
    	boolean check = false;
    	Zone zone = new Zone(name);
        try {
			List<MovieTime> movieTimes = MovieTimeRepository.getInstance().getMovieTimes();
			if(movieTimes != null)
			{
				for(MovieTime movieTime : movieTimes)
				{
			       movieTime.getMovieTheater().getListZone().remove(zone);
			    }
				// update file
		        MovieTimeRepository.getInstance().writeMovieTimeData(movieTimes);
				check = true;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return check;
     }*/
  
     
     /*public synchronized void bookMovieSeat(String bookingInfo)
     {
    	 List<Object> bookingInfoComponents = TimeZoneUtitls.getBookingInfoFromString(bookingInfo);
    	 String username = (String) bookingInfoComponents.get(0);
    	 MovieTime movieTime = getMovieTimeFromTimeZone((LocalTime)bookingInfoComponents.get(1),(LocalTime)bookingInfoComponents.get(2));
    	 String zoneName = (String) bookingInfoComponents.get(3);
    	 Integer row = (Integer) bookingInfoComponents.get(4);
    	 Integer col = (Integer) bookingInfoComponents.get(5);
    	 if(movieTime != null)
    	 {
    		 List<Zone> listZones = movieTime.getMovieTheater().getListZone();
    		 for(Zone zone : listZones)
    		 {
    			 if(zone.getName().equals(zoneName))
    			 {
    				 User user = userService.getUserByUsername(username);
    				 Seat seat = zone.getSeats().get(row).get(col);
    				 System.out.println(seat.isStatus());
    				 if(!seat.isStatus())
    				 {
    					seat.setStatus(true);
    					seat.setUser(user);
    				 }
    				 break;
    			 }
    		 }
    		 updateSeat(movieTime);
    	 }
     }*/
     
     /*public synchronized void updateSeat(MovieTime movieTime)
     {
    	 try {
			List<MovieTime> movieTimes = MovieTimeRepository.getInstance().getMovieTimes();
			if(movieTimes.contains(movieTime))
			{
				int index = movieTimes.indexOf(movieTime);
				movieTimes.set(index, movieTime);
			}
			MovieTimeRepository.getInstance().writeMovieTimeData(movieTimes);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }*/
  
}
