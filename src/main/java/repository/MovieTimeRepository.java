package repository;

import java.util.ArrayList;
import java.util.List;

import models.MovieTime;
import storeage.BookingDataStoreage;

public class MovieTimeRepository {
   private static MovieTimeRepository repo = null;
   
   private MovieTimeRepository()
   {
   }
   
   public static MovieTimeRepository getInstance()
   {
	   if(repo == null)
	   {
		   repo = new MovieTimeRepository();
	   }
	   return repo;
   }
   
   public List<MovieTime> getMovieTimes() throws ClassNotFoundException
   {
		 BookingDataStoreage connection = BookingDataStoreage.getInstance();
		 List<MovieTime> listMovieTime = (ArrayList<MovieTime>) connection.readFile();
		 return listMovieTime;
   }
   
   public void writeMovieTimeData(List<MovieTime> movieTimes)
   {
	    BookingDataStoreage connection = BookingDataStoreage.getInstance();
	    connection.writeFile(movieTimes);
   }
}
