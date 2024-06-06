package utils;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import models.Movie;
import models.MovieTime;

public class MovieUtils {

	 public static List<String> getMoviesAsString(List<Movie> listMovie)
	 {
	      List<String> movieAsString = new ArrayList<>();	 
	      if(listMovie != null)
	      {
	        for(Movie movie : listMovie)
	        {
	        	movieAsString.add(movie.getMovieName());
	        } 
	      }
	      return movieAsString;
	 }
	 
	 public static boolean checkExistMovieName(List<Movie> movies, String movieName)
	 {
		 for(Movie movie : movies)
		 {
			 if(movie.getMovieName().equals(movieName))
			 {
				 return true;
			 }
		 }
		 return false;
	 }
	 
	 public static Movie getMovieFromName(List<Movie> movies, String movieName)
	 {
		 Movie movie = new Movie(movieName);
		 if(movies.contains(movie))
		 {
		    Integer index = movies.indexOf(movie);
		    return movies.get(index);
		 }
		 return null;
	 }
	 
	 public static MovieTime getMovieTimeFromTimeZone(List<MovieTime> movieTimes,LocalTime fromTimeLocalTime, LocalTime endTimeLocalTime)
	 {
	   MovieTime movieTime = new MovieTime(fromTimeLocalTime, endTimeLocalTime);
	   if(movieTimes.contains(movieTime))
	   {
		  Integer index = movieTimes.indexOf(movieTime);
		  return movieTimes.get(index);
	   }
	   return null;
	 }
}
