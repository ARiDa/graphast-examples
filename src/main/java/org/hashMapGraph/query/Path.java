package org.hashMapGraph.query;
import java.util.ArrayList;
import java.util.List;

import org.hashMapGraph.model.Node;

public class Path {
	private List<Node> pathMin;
	private int totalDistance;
	
	public Path(){
		pathMin = new ArrayList<Node>();
		totalDistance = 0;
	}

	public List<Node> getPathMin() {
		if(this.pathMin == null){
			return null;
		}
		else{
			return this.pathMin;
		}
	}

	public int getTotalDistance() {
		return this.totalDistance;
	}

	public void setTotalDistance(int totalDistance) {
		this.totalDistance = totalDistance;
	}
}
