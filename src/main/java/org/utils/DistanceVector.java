package org.utils;

import java.util.ArrayList;
import java.util.List;

public class DistanceVector {
	private DistanceElement[] vector;
	
	public DistanceVector(long sourceIndex, long nNodes) {
		vector = new DistanceElement[(int)nNodes];
		DistanceElement first = new DistanceElement(sourceIndex);
		first.changeDistance(0);
		vector[(int)sourceIndex] = first;
	}

	public DistanceElement getElement(long index) {
		if (vector[(int)index] == null)
			vector[(int)index] = new DistanceElement(index);
		return vector[(int)index];
	}
	
	public void print() {
		for (DistanceElement element : vector) {
			System.out.println("Distance to node " + element.getIndex() + ": " + element.getDistance() + " | Previous node: " + element.getPreviousIndex());
		}
	}
	
	public void print(long source, long target) {
		if (getElement(target).getPreviousIndex() == -1) {
			System.out.println("No path between them");
			return;
		}
		List<Long> l = new ArrayList<>();
		l.add(getElement(target).getRealId());
		long parent = target;
		while ((parent = getElement(parent).getPreviousIndex()) != -1) {
			l.add(0, getElement(parent).getRealId());
			if (parent == source) break;
		}
		for (Long id : l) {
			System.out.print(" -> " + id);
		}
		System.out.println();
	}
	
	public long getDistance(long target) {
		return getElement(target).getDistance();
	}
}
