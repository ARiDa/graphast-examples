package org.vanilaGraph;

public class Edge extends GraphEntity {
	
	private Long id;
	private int fromNodeIndex, toNodeIndex;
	private Double cost;
	
	public Edge(Long edgeId, int fromIndex, int toIndex, Double cost) {
		this.id = edgeId;
		this.fromNodeIndex = fromIndex;
		this.toNodeIndex = toIndex;
		this.cost = cost;
	}
	
	public Long getId() {
		return id;
	}
	
	public Double getCost() {
		return cost;
	}
	
	public int getFromNodeIndex() {
		return fromNodeIndex;
	}
	
	public int getToNodeIndex() {
		return toNodeIndex;
	}
}
