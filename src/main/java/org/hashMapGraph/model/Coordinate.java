package org.hashMapGraph.model;

public class Coordinate {
	private double latitude;
	private double longitude;
	
	public Coordinate(double latitude, double longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double getLatitude(){
		return this.latitude;
	}
	
	public double getLongitude(){
		return this.longitude;
	}
	
	public String toString(){
		return "Latitude: "+latitude+" Longitude: "+longitude;
	}
	
	public double distanceTo(Coordinate B){
		return distance(this.latitude,this.longitude,B.getLatitude(),B.getLongitude());	   
	}
	
	private double distance(double lat1, double lat2, double lon1,double lon2) {

	    final int R = 6371; // Radius of the earth

	    Double latDistance = Math.toRadians(lat2 - lat1);
	    Double lonDistance = Math.toRadians(lon2 - lon1);
	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    
	    return R * c * 1000; // convert to meters
	}
}
