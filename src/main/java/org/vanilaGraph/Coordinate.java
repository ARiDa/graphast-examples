package org.vanilaGraph;


public class Coordinate {
	private double lat;
	private double lng;
	
	public Coordinate(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}
	
	public double getLat() {
		return lat;
	}
	
	public double getLng() {
		return lng;
	}
	
	@Override
	public boolean equals(Object obj) {
		Coordinate other = (Coordinate) obj;
		return this.lat == other.lat && this.lng == other.lng;
	}
	
	@Override
	public int hashCode() {
		double hash = 1;
		hash = hash * 17 + lat;
		hash = hash * 31 + lng;
		hash *= 1000;
		return (int)hash;
	}

	public double distanceTo(Coordinate other) {
		return Math.sqrt(Math.pow(this.lat - other.getLat(), 2) + Math.pow(this.lng - other.getLng(), 2));
	}
	
//	private double distance(double lat1, double lat2, double lon1,double lon2) {
//
////	    final int R = 6371; // Radius of the earth
////
////	    Double latDistance = Math.toRadians(lat2 - lat1);
////	    Double lonDistance = Math.toRadians(lon2 - lon1);
////	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
////	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
////	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
////	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
////	    
////	    return R * c * 1000; // convert to meters
//		return Math.sqrt((lat1-lat2)*(lat1-lat2) + (lon1-lon2)*(lon1-lon2));
//	}
}
