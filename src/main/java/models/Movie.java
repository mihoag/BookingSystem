package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Movie implements Serializable {
 
   private static final long serialVersionUID = 1L;
   
   private String movieName;
   private LocalDate date;
   private List<MovieTime> movieTimes;
   
   
   
   public Movie() {
	super();
	// TODO Auto-generated constructor stub
   }
     
   public Movie(String movieName, LocalDate date, List<MovieTime> movieTimes) {
	super();
	this.movieName = movieName;
	this.date = date;
	this.movieTimes = movieTimes;
  }
  
  

  public Movie(String movieName, LocalDate date) {
	super();
	this.movieName = movieName;
	this.date = date;
	movieTimes = new ArrayList<>();
  }

  public Movie(String movieName) {
	super();
	this.movieName = movieName;
  }

  public String getMovieName() {
	 return movieName;
   } 
   
  public void setMovieName(String movieName) {
	this.movieName = movieName;
  }
  
  public LocalDate getDate() {
	return date;
  }

  public void setDate(LocalDate date) {
	this.date = date;
  }
  
  public List<MovieTime> getMovieTimes() {
	return movieTimes;
  }

  public void setMovieTimes(List<MovieTime> movieTimes) {
	this.movieTimes = movieTimes;
  }

  @Override
  public int hashCode() {
	return Objects.hash(movieName);
  }

  @Override
  public boolean equals(Object obj) {
	 if (this == obj)
		return true;
  	 if (obj == null)
		return false;
	 if (getClass() != obj.getClass())
		return false;
	 Movie other = (Movie) obj;
	 return Objects.equals(movieName, other.movieName);
 }
  
  public void addMovieTime(MovieTime movieTime)
  {
	 movieTimes.add(movieTime);
  }
}
