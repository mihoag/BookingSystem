package repository;

import java.util.ArrayList;
import java.util.List;

import models.Movie;
import storeage.BookingDataStoreage;

public class MovieRepository {
	private static MovieRepository repo = null;
	   
    private MovieRepository() {
	}
    
	public static MovieRepository getInstance()
	{
	   if(repo == null)
	   {
		  repo = new MovieRepository();
	   }
	   return repo;
	}
	   
	public List<Movie> getMovies() throws ClassNotFoundException
	{
	   BookingDataStoreage connection = BookingDataStoreage.getInstance();
	   List<Movie> listMovies = (ArrayList<Movie>) connection.readFile();
	   return listMovies;
	}
	   
	public void writeMovieData(List<Movie> movies)
	{
	   BookingDataStoreage connection = BookingDataStoreage.getInstance();
	   connection.writeFile(movies);
	}
}
