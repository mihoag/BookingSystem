package service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import models.Movie;
import models.MovieTime;
import models.Seat;
import models.User;
import models.Zone;
import repository.MovieRepository;
import repository.MovieTimeRepository;
import utils.MovieUtils;
import utils.TimeZoneUtitls;

public class MovieService {
	 private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	 private UserService userService = new UserService();
	 
	 private static MovieService movieService = null;
	 
	 private MovieService()
	 {	 
	 }
	 
	 public static MovieService getInstance()
	 {
		 if(movieService == null)
		 {
			 movieService = new MovieService();
		 }
		 return movieService;
	 }
	 
	 
	 public List<Movie> getMovies()
	 {
		 List<Movie> movies = null;
		 try {
			movies =  MovieRepository.getInstance().getMovies();
			if(movies == null)
			{
				return new ArrayList<>();
			}
		 } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		return movies;
	 }
	 
	 public List<String> getMovieAsString() throws ClassNotFoundException
     {
    	 List<Movie> listMovieTimes = getMovies();
    	 return MovieUtils.getMoviesAsString(listMovieTimes);
     }
	 
	 public List<MovieTime> getListMovieTimesFromMovie(String name)
	 {
		 Movie movie = new Movie(name);
		 Movie movieInFile = null;
		 List<Movie> movies = getMovies();
		 if(movies.contains(movie))
		 {
			 Integer index = movies.indexOf(movie);
			 movieInFile = movies.get(index);
		 }
		 return movieInFile.getMovieTimes();
	 }
	 
	 public boolean addMovie(String name, LocalDate date)
	 {
		 List<Movie> movies = getMovies();
		 Movie movie = new Movie(name, date);
		 if(movies.contains(movie))
		 {
			 return false;
		 }
		 movies.add(movie);
		 MovieRepository.getInstance().writeMovieData(movies);
		 return true;
	 }
	 
	 public boolean deleteMovie(String name)
	 {
		 Movie movie = new Movie(name);
		 List<Movie> movies = getMovies();
		 if(!movies.contains(movie))
		 {
			 return false;
		 }
		 movies.remove(movie);
		 MovieRepository.getInstance().writeMovieData(movies);
		 return true;
	 }
	 
	 public Movie getMovieFromMovieName(String name)
	 {
		 List<Movie> movies = getMovies();
		 Movie movie = new Movie(name);
		 if(movies.contains(movie))
		 {
			 Integer index = movies.indexOf(movie);
			 return movies.get(index);
		 }
		 return null;
	 }
	 
	 public boolean checkTimeZone(LocalDate date ,LocalTime fromLocalTime, LocalTime toLocalTime)
	 {
		if(fromLocalTime.isAfter(toLocalTime) || fromLocalTime.equals(toLocalTime))
		{
		   return false;
	    }
		
		List<Movie> listMovies = getMovies();
		for(Movie movie : listMovies)
		{
			if(date.equals(movie.getDate()))
			{
			    List<MovieTime> movieTimes = movie.getMovieTimes();
			    for(MovieTime movieTime : movieTimes)
				{
				   if(((movieTime.getFromTime().isAfter(fromLocalTime) || movieTime.getFromTime().equals(fromLocalTime) )&& movieTime.getFromTime().isBefore(toLocalTime)) || (movieTime.getToTime().isAfter(fromLocalTime)  && (movieTime.getToTime().isBefore(toLocalTime) || movieTime.getToTime().equals(toLocalTime))))
				   {
				      return false;
				   }
				}
			}
		}
		return true;
	 }
	 
	 
	 public boolean addMovieTime(String movieName, String fromTime, String toTime)
	 {
		 Movie movie = getMovieFromMovieName(movieName);
		 if(movie != null)
		 {
			 try {
				LocalTime fromLocalTime = LocalTime.parse(fromTime, formatter);
				LocalTime toLocalTime = LocalTime.parse(toTime, formatter);
				   
				boolean check = checkTimeZone(movie.getDate(), fromLocalTime, toLocalTime);
				if(!check)
				{
					return check;
				}
				
				MovieTime newMovieTime = new MovieTime(fromLocalTime, toLocalTime);
				movie.addMovieTime(newMovieTime);
			    
				// 
				List<Movie> movies = getMovies();
				Integer index = movies.indexOf(movie);
				movies.set(index, movie);
				MovieRepository.getInstance().writeMovieData(movies);
				
				return true;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					return false;
				}  
		 }
		 return false;
	 }
	 

     public List<String> getMovieTimeAsString(String movieName) throws ClassNotFoundException
     {
    	 if(movieName == null)
    	 {
    		 return new ArrayList<>();
    	 }
    	 List<MovieTime> movieTimes = getListMovieTimesFromMovie(movieName);
    	 return TimeZoneUtitls.getListTimeZoneAsString(movieTimes);
     }
     
     public boolean deleteMovieTime(String movieName, String fromTime, String toTime)
     {
    	 try {
    	    Movie movie = getMovieFromMovieName(movieName);
    		MovieTime movieTime = new MovieTime(LocalTime.parse(fromTime, formatter), LocalTime.parse(toTime, formatter));
    		
	        if(movie.getMovieTimes().contains(movieTime))
	        {
	        	movie.getMovieTimes().remove(movieTime);
	        	
	        	// 
				List<Movie> movies = getMovies();
				Integer index = movies.indexOf(movie);
				movies.set(index, movie);
				MovieRepository.getInstance().writeMovieData(movies);
	        	
	        	return true;
	        }
		 } catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
    	return false;
    }
     

     public boolean addZone(String movieName, String startTime, String toTime, String zoneName, Integer rowNum, Integer seatPerRow, Double price)
     {
    	 Movie movie = getMovieFromMovieName(movieName);
		 if(movie != null)
		 {
		   List<MovieTime> movieTimes = movie.getMovieTimes();
		   MovieTime movieTime = new MovieTime(LocalTime.parse(startTime, formatter), LocalTime.parse(toTime, formatter));
		   if(movieTimes.contains(movieTime))
		   {
			   Integer index = movieTimes.indexOf(movieTime);
			   Boolean check =  movieTimes.get(index).getMovieTheater().addZone(new Zone(zoneName, rowNum, seatPerRow, price));
			   
			   List<Movie> movies = getMovies();
			   Integer index1 = movies.indexOf(movie);
			   movies.set(index1, movie);
			   MovieRepository.getInstance().writeMovieData(movies);
			   
			   return check;
		   }
   
		 }
		 return false;
     }
     
     
     public List<Zone> getListZoneFromMovieTime(String movieName, String fromTime, String toTime) {
    	 MovieTime movieTime = getMovieTimeFromTimeZone(movieName, LocalTime.parse(fromTime, formatter), LocalTime.parse(toTime, formatter));
    	 if(movieTime != null)
    	 {
    		 return movieTime.getMovieTheater().getListZone();
    	 }
    	 return new ArrayList<>();
     }
     
     public boolean deleteZoneByName(String movieName, String fromTime, String toTime, String zoneName)
     {
    	Zone zone = new Zone(zoneName);
        Movie movie = getMovieFromMovieName(movieName);
		List<MovieTime> movieTimes = movie.getMovieTimes();
		
		if(movieTimes != null)
		{
			MovieTime movieTime = new MovieTime(LocalTime.parse(fromTime, formatter), LocalTime.parse(toTime, formatter));
			MovieTime movieTimeInFile = null;
			if(movieTimes.contains(movieTime))
			{
				Integer index = movieTimes.indexOf(movieTime);
			    movieTimeInFile = movieTimes.get(index);
			    
			}
			if(movieTimeInFile != null)
			{
				List<Zone> listZone = movieTimeInFile.getMovieTheater().getListZone();
				if(listZone != null && !listZone.isEmpty())
				{
				      if(listZone.contains(zone))
				      {
				    	  listZone.remove(zone);
				    	  
				    	  // Write to file
				    	  List<Movie> movies = getMovies();
						  Integer index1 = movies.indexOf(movie);
						  movies.set(index1, movie);
						  MovieRepository.getInstance().writeMovieData(movies);
				    	  
				    	  return true;
				      }
				}
			}
			
		}
    	return false;
     }
     
     public MovieTime getMovieTimeFromTimeZone(String movieName, LocalTime fromTimeLocalTime, LocalTime endTimeLocalTime)
     {        
		Movie movie = getMovieFromMovieName(movieName);
		List<MovieTime> movieTimes = movie.getMovieTimes();
		 
		for(MovieTime movieTime : movieTimes)
		{
		  if(movieTime.getFromTime().equals(fromTimeLocalTime) && movieTime.getToTime().equals(endTimeLocalTime))
		  {
			return movieTime;
		  }
		}
    	return null;
     }
     
     public synchronized void bookMovieSeat(String bookingInfo)
     {
    	 List<Object> bookingInfoComponents = TimeZoneUtitls.getBookingInfoFromString(bookingInfo);
    	 String username = (String) bookingInfoComponents.get(0);
    	 
    	 String movieName =  (String) bookingInfoComponents.get(6);
    	 Movie movie = MovieService.getInstance().getMovieFromMovieName(movieName);
    	 
    	 MovieTime movieTime = MovieUtils.getMovieTimeFromTimeZone(movie.getMovieTimes(),(LocalTime)bookingInfoComponents.get(1),(LocalTime)bookingInfoComponents.get(2));
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
    		 updateSeat(movie);
    	 }
     }
     
     public synchronized void updateSeat(Movie movie)
     {
    	List<Movie> movies = getMovies();
		if(movies.contains(movie))
		{
			int index = movies.indexOf(movie);
			movies.set(index, movie);
		}
		MovieRepository.getInstance().writeMovieData(movies);
     }
     
}
