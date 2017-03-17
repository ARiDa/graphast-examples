package org.vanilaGraph;

import java.util.ArrayList;
import java.util.List;

public class Node extends GraphEntity {
	
	private Long id;
	private Coordinate coord;
	private List<Edge> adjacents;
	
	public Node(Long id) {
		this.id = id;
		adjacents  = new ArrayList<>();
	}
	
	public Node(Long id, Coordinate c) {
		this(id);
		this.coord = c;
	}
	
	public void addAdjacent(Edge e) {
		adjacents.add(e);
	}
	
	public Long getId() {
		return id;
	}
	
	public Coordinate getCoord() {
		return coord;
	}
	
	public List<Edge> getAdjacents() {
		return adjacents;
	}
	
	public Double getCostToNode(int otherIndex) {
		for (Edge e: adjacents) {
			if (this.getGraphIndex() == e.getToNodeIndex())
				return e.getCost();
		}
		return null;
	}
	
}
