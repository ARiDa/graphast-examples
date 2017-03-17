package org.vanilaGraph;

import java.util.ArrayList;
import java.util.List;

public class DistanceVector {
	private List<DistanceElement> vector;
	
	public DistanceVector(int sourceIndex, List<Node> nodeList) {
		vector = new ArrayList<>();
		for (Node node: nodeList) {
			vector.add(new DistanceElement(node.getId(), node.getGraphIndex()));
		}
		vector.get(sourceIndex).changeDistance(0.);
	}
	
	public void addElement(DistanceElement element) {
		vector.add(element.getIndex(), element);
	}
	
	public DistanceElement getElement(int index) {
		return vector.get(index);
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
		l.add(vector.get(target).getRealId());
		int parent = target;
		while ((parent = vector.get(parent).getPreviousIndex()) != -1) {
			l.add(0, vector.get(parent).getRealId());
			if (parent == source) break;
		}
		for (Long id : l) {
			System.out.print(" -> " + id);
		}
		System.out.println();
	}
	
	public double getDistance(int target) {
		return vector.get(target).getDistance();
	}
}
