package org.vanilaGraph;


public class DistanceElement implements Comparable<DistanceElement>{
	private Long realId;
	private int index, previousIndex;
	private Double distance;
	private boolean visited;
	
	public DistanceElement(Long realId, int index) {
		this.realId = realId;
		this.index = index;
		this.distance = Double.POSITIVE_INFINITY;
		previousIndex = -1;
		visited = false;
	}
	
	public void changePrevious(int newPreviousIndex) {
		previousIndex = newPreviousIndex;
	}
	
	public void changeDistance(Double newDistance) {
		distance = newDistance;
	}
	
	public Long getRealId() {
		return realId;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public Double getDistance() {
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
		return Double.compare(this.distance, o.getDistance());
	}
	
}
