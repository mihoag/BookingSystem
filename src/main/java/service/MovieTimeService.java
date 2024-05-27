package service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Utils.TimeZoneUtitls;
import models.MovieTheater;
import models.MovieTime;
import models.Zone;
import repository.MovieTimeRepository;

public class MovieTimeService {
	
	 private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
     public List<String> getMovieTimeAsString() throws ClassNotFoundException
     {
    	 List<MovieTime> listMovieTimes = MovieTimeRepository.getInstance().getMovieTimes();
    	 List<String> movieTimeAsString = new ArrayList<>();
    	 

         // Define a formatter with the desired pattern
         
         if(listMovieTimes != null)
         {
        	 for(MovieTime movieTime : listMovieTimes)
        	 {
        		//movieTime.getFromTime().format(formatter) + "-" + movieTime.getToTime().format(formatter)
        		movieTimeAsString.add(TimeZoneUtitls.getTimeZoneValueAsString(movieTime.getFromTime().format(formatter), movieTime.getToTime().format(formatter)));
        	 } 
         }
    	 return movieTimeAsString;
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
		    for(MovieTime movieTime : listMovieTimes)
		    {
		    	if(((movieTime.getFromTime().isAfter(fromLocalTime) || movieTime.getFromTime().equals(fromLocalTime) )&& movieTime.getFromTime().isBefore(toLocalTime)) || ((movieTime.getToTime().isAfter(fromLocalTime) || (movieTime.getToTime().equals(fromLocalTime) ) && movieTime.getToTime().isBefore(toLocalTime))))
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
		    if(movieTimes == null)
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
     
     public static void main(String[] args) throws ClassNotFoundException {
	    MovieTimeService ser = new MovieTimeService();
	    System.out.println(ser.addMovieTime("15:00:00", "17:00:00"));
	    List<String> ls = ser.getMovieTimeAsString();
	    System.out.println(ls.size());
	}
     
     
     
     
     
     
}
