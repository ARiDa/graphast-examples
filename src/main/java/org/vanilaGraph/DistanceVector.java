package org.vanilaGraph;

import java.util.ArrayList;
import java.util.List;

public class DistanceVector {
	private DistanceElement[] vector;
	
	public DistanceVector(int sourceIndex, Graph g) {
		vector = new DistanceElement[g.getNumberOfNodes()];
		DistanceElement first = new DistanceElement(sourceIndex);
		first.changeDistance(0);
		vector[sourceIndex] = first;
	}

	public DistanceElement getElement(int index) {
		if (vector[index] == null)
			vector[index] = new DistanceElement(index);
		return vector[index];
	}
	
	public void print() {
		for (DistanceElement element : vector) {
			System.out.println("Distance to node " + element.getIndex() + ": " + element.getDistance() + " | Previous node: " + element.getPreviousIndex());
		}
	}
	
	public void print(int source, int target) {
		if (getElement(target).getPreviousIndex() == -1) {
			System.out.println("No path between them");
			return;
		}
		List<Long> l = new ArrayList<>();
		l.add(vector[target].getRealId());
		int parent = target;
		while ((parent = vector[parent].getPreviousIndex()) != -1) {
			l.add(0, vector[parent].getRealId());
			if (parent == source) break;
		}
		for (Long id : l) {
			System.out.print(" -> " + id);
		}
		System.out.println();
	}
	
	public double getDistance(int target) {
		return vector[target].getDistance();
	}
}
