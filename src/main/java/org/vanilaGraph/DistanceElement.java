package org.vanilaGraph;


public class DistanceElement implements Comparable<DistanceElement>{
	private Long realId;
	private int index, previousIndex;
	private double distance;
	private boolean visited;
	
	public DistanceElement(int index) {
		this.index = index;
		this.distance = Integer.MAX_VALUE;
		previousIndex = -1;
		visited = false;
	}
	
	public DistanceElement(int index, Long realId) {
		this(index);
		this.realId = realId;
	}
	
	public void changePrevious(int newPreviousIndex) {
		previousIndex = newPreviousIndex;
	}
	
	public void changeDistance(double newDistance) {
		distance = newDistance;
	}
	
	public Long getRealId() {
		return realId;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public int getPreviousIndex() {
		return previousIndex;
	}
	
	public boolean isVisited() {
		return visited;
	}
	
	public void setVisited(boolean b) {
		visited = b;
	}

	@Override
	public int compareTo(DistanceElement o) {
		return Double.compare(this.distance,o.getDistance());
	}
	
}
