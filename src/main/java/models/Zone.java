package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Zone implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private Integer rowNum;
	private Integer seatsPerRow;
	private ArrayList<ArrayList<Seat>> seats;
	private Double price;
	

	public Zone() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Zone(String name)
	{
		this.name = name;
	}
	
	public Zone(String name,Integer rowNum, Integer seatsPerRow, Double price) {
		super();
		this.name = name;
		this.rowNum = rowNum;
		this.seatsPerRow = seatsPerRow;
		this.seats = new ArrayList<>(rowNum);
		for(int i = 0 ; i < rowNum ; i++)
		{
		   this.seats.add(new ArrayList<Seat>(seatsPerRow));
		}
		this.price = price;
	}
	public Integer getRowNum() {
		return rowNum;
	}
	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}
	public Integer getSeatsPerRow() {
		return seatsPerRow;
	}
	public void setSeatsPerRow(Integer seatsPerRow) {
		this.seatsPerRow = seatsPerRow;
	}
	public ArrayList<ArrayList<Seat>> getSeats() {
		return seats;
	}
	public void setSeats(ArrayList<ArrayList<Seat>> seats) {
		this.seats = seats;
	}
	public void setBookingStatusForSeat(int i, int j)
	{
		this.seats.get(i).get(j).setStatus(true);
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Zone other = (Zone) obj;
		return Objects.equals(name, other.name);
	}
}
