package org.utils;

public class DistanceElement implements Comparable<DistanceElement>{
	private Long realId;
	private long index, previousIndex;
	private int distance;
	private boolean visited;
	
	public DistanceElement(long index) {
		this.index = index;
		this.distance = Integer.MAX_VALUE;
		previousIndex = -1;
		visited = false;
	}
	
	public DistanceElement(int index, Long realId) {
		this(index);
		this.realId = realId;
	}
	
	public void changePrevious(long newPreviousIndex) {
		previousIndex = newPreviousIndex;
	}
	
	public void changeDistance(int newDistance) {
		distance = newDistance;
	}
	
	public Long getRealId() {
		return realId;
	}
	
	public long getIndex() {
		return this.index;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public long getPreviousIndex() {
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
		return this.distance - o.getDistance();
	}
	
}
