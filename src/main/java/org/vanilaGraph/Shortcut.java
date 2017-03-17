package org.vanilaGraph;

public class Shortcut extends Edge{
	
	private int contractedNodeIndex;

	public Shortcut(Long edgeId, int fromIndex, int toIndex, Double cost, int contractedNodeIndex) {
		super(edgeId, fromIndex, toIndex, cost);
		this.contractedNodeIndex = contractedNodeIndex;
	}

}
