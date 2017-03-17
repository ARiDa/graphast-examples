package org.hashMapGraph.geometry;


/*
 * 
 * Class for Graphast
 * 
 */
public class Point implements Comparable <Point>{

	private double latitude;
	private double longitude;

	public Point(){}
	
	public Point(double latitude, double longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public String toString(){
		String s = "";
		s += "lat:" + this.latitude + " long:" + this.longitude;
		return s;
	}

	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) {
	        return false;
	    }
	    if (getClass() != obj.getClass()) {
	        return false;
	    }
	    final Point newPoint = (Point) obj;
	    
	    if ((this.getLatitude() == newPoint.getLatitude()) &&(this.getLongitude()==newPoint.getLongitude())) {
	    	return true;
	    }
	    
	    return false;
		
	}
	
	public int compareTo (Point other) {
		if (this.longitude == other.longitude) {
			if (this.latitude == other.latitude) return 0;
			else if (this.latitude < other.latitude) return 1;
			else return -1;
		}
		else if (this.longitude < other.longitude) {
			return 1;
		}
		else {
			return -1;
		}
	}

}