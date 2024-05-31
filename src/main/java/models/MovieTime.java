package models;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

public class MovieTime implements Serializable{
	
	private static final long serialVersionUID = 1L;
    private LocalTime fromTime;
    private LocalTime toTime;
    private MovieTheater movieTheater;
    
    
    
    
	public MovieTime() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public MovieTime(LocalTime fromTime, LocalTime toTime) {
		super();
		this.fromTime = fromTime;
		this.toTime = toTime;
		movieTheater = new MovieTheater();
	}



	public LocalTime getFromTime() {
		return fromTime;
	}
	public void setFromTime(LocalTime fromTime) {
		this.fromTime = fromTime;
	}
	public LocalTime getToTime() {
		return toTime;
	}
	public void setToTime(LocalTime toTime) {
		this.toTime = toTime;
	}
	public MovieTheater getMovieTheater() {
		return movieTheater;
	}
	public void setMovieTheater(MovieTheater movieTheater) {
		this.movieTheater = movieTheater;
	}



	@Override
	public int hashCode() {
		return Objects.hash(fromTime, toTime);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovieTime other = (MovieTime) obj;
		return Objects.equals(fromTime, other.fromTime) && Objects.equals(toTime, other.toTime);
	}
	
	
}
