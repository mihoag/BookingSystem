package service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Utils.SeatUtils;
import Utils.TimeZoneUtitls;
import models.MovieTheater;
import models.MovieTime;
import models.Seat;
import models.User;
import models.Zone;
import repository.MovieTimeRepository;

public class MovieTimeService {
	
	 private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	 private UserService userService = new UserService();
	 
	 public List<MovieTime> getMovieTimes()
	 {
		 try {
			return MovieTimeRepository.getInstance().getMovieTimes();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<>();
	 }
	 
     public List<String> getMovieTimeAsString() throws ClassNotFoundException
     {
    	 List<MovieTime> listMovieTimes = MovieTimeRepository.getInstance().getMovieTimes();
    	 return TimeZoneUtitls.getListTimeZoneAsString(listMovieTimes);
     }
     
     public boolean  addMovieTime(String fromTime, String toTime) throws ClassNotFoundException
     {
    	 List<MovieTime> listMovieTimes = MovieTimeRepository.getInstance().getMovieTimes();
    	 if(listMovieTimes == null)
    	 {
    		 listMovieTimes = new ArrayList<>();
    	 }
    	
    	 try {
			LocalTime fromLocalTime = LocalTime.parse(fromTime, formatter);
		    LocalTime toLocalTime = LocalTime.parse(toTime, formatter);
		    
		    if(fromLocalTime.isAfter(toLocalTime) || fromLocalTime.equals(toLocalTime))
		    {
		    	return false;
		    }
		    
		    for(MovieTime movieTime : listMovieTimes)
		    {
		    	if(((movieTime.getFromTime().isAfter(fromLocalTime) || movieTime.getFromTime().equals(fromLocalTime) )&& movieTime.getFromTime().isBefore(toLocalTime)) || (movieTime.getToTime().isAfter(fromLocalTime)  && (movieTime.getToTime().isBefore(toLocalTime) || movieTime.getToTime().equals(toLocalTime))))
		    	{
		    		return false;
		    	}
		    }
		    
		    MovieTime newMovieTime = new MovieTime(fromLocalTime, toLocalTime);
		    newMovieTime.getMovieTheater().setListZone(cloneZoneMap());
		    listMovieTimes.add(newMovieTime);
		    MovieTimeRepository.getInstance().writeMovieTimeData(listMovieTimes);
		    return true;
	    	 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		} 
     }
     
     public MovieTime getMovieTimeFromTimeZone(LocalTime fromTimeLocalTime, LocalTime endTimeLocalTime)
     {
    
		try {
			 List<MovieTime> movieTimes;
		     movieTimes = MovieTimeRepository.getInstance().getMovieTimes();
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
     
     public boolean deleteMovieTime(String fromTime, String toTime)
     {
    	 try {
    		MovieTime movieTime = new MovieTime(LocalTime.parse(fromTime, formatter), LocalTime.parse(toTime, formatter));
    		List<MovieTime> movieTimes = MovieTimeRepository.getInstance().getMovieTimes();
	        if(movieTimes.contains(movieTime))
	        {
	        	movieTimes.remove(movieTime);
	        	MovieTimeRepository.getInstance().writeMovieTimeData(movieTimes);
	        	return true;
	        }
		 } catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
    	return false;
    }
     
     public boolean addZone(String name, Integer rowNum, Integer seatPerRow, Double price)
     {
    	 try {
			 List<MovieTime> movieTimes;
		     movieTimes = MovieTimeRepository.getInstance().getMovieTimes();
		     boolean check = true;
	    	 for(MovieTime movieTime : movieTimes)
	    	 {
	    	    check = movieTime.getMovieTheater().addZone(new Zone(name, rowNum, seatPerRow, price)) && check;
	    	    if(!check)
	    	    {
	    	    	break;
	    	    }
	    	 }   	 
	    	 MovieTimeRepository.getInstance().writeMovieTimeData(movieTimes);
	    	 return check;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
     }
     
     public List<Zone> getListZones()
     {
    	List<MovieTime> movieTimes;
		try {
			movieTimes = MovieTimeRepository.getInstance().getMovieTimes();
		    if(movieTimes == null || movieTimes.size() == 0)
		    {
		    	return new ArrayList<>();
		    }
			return movieTimes.get(0).getMovieTheater().getListZone();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<>();
		}

     }
     
     public boolean deleteZoneByName(String name)
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
     }
    
     public List<Zone> cloneZoneMap()
     {
    	List<Zone> listZone = new ArrayList<>();
        
    	try {
			List<MovieTime> listMovieTime = MovieTimeRepository.getInstance().getMovieTimes();
			if(listMovieTime != null && listMovieTime.size() > 0)
			{
				MovieTime movieTime = listMovieTime.get(0);
				for(Zone zone : movieTime.getMovieTheater().getListZone())
				{
				    listZone.add(new Zone(zone.getName(), zone.getRowNum(), zone.getSeatsPerRow(), zone.getPrice()));
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return listZone;
     }
     
     
     public void writeAllMovieTimesToFile(List<MovieTime> movieTimes)
     {
    	 MovieTimeRepository.getInstance().writeMovieTimeData(movieTimes);
     }
     
     public synchronized void bookMovieSeat(String bookingInfo)
     {
    	 List<Object> bookingInfoComponents = TimeZoneUtitls.getBookingInfoFromString(bookingInfo);
    	 String username = (String) bookingInfoComponents.get(0);
    	 MovieTime movieTime = getMovieTimeFromTimeZone((LocalTime)bookingInfoComponents.get(1),(LocalTime)bookingInfoComponents.get(2));
    	 String zoneName = (String) bookingInfoComponents.get(3);
    	 Integer row = (Integer) bookingInfoComponents.get(4);
    	 Integer col = (Integer) bookingInfoComponents.get(4);
    	 if(movieTime != null)
    	 {
    		 List<Zone> listZones = movieTime.getMovieTheater().getListZone();
    		 for(Zone zone : listZones)
    		 {
    			 if(zone.getName().equals(zoneName))
    			 {
    				 User user = userService.getUserByUsername(username);
    				 Seat seat = zone.getSeats().get(row).get(col);
    				 if(!seat.isStatus())
    				 {
    					System.out.println("ok");
    					seat.setStatus(true);
    					seat.setUser(user);
    				 }
    				 break;
    			 }
    		 }
    		 updateSeat(movieTime);
    	 }
     }
     
     public synchronized void updateSeat(MovieTime movieTime)
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
     }
     
     public static void main(String[] args) throws ClassNotFoundException {
	    MovieTimeService ser = new MovieTimeService();
	    System.out.println(ser.addMovieTime("15:00:00", "17:00:00"));
	    List<String> ls = ser.getMovieTimeAsString();
	    System.out.println(ls.size());
	}
     
     
     
     
     
     
}
