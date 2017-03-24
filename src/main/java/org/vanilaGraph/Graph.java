package org.vanilaGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class Graph {
	
	private List<Node> nodes = new ArrayList<>();
	private List<Edge> edges = new ArrayList<>();
	private HashMap<Long, Integer> idToIndex = new HashMap<>();
	private HashMap<Coordinate, Integer> coordToIndex = new HashMap<>();
	
	public void addNode(Node node) {
		nodes.add(node);
		node.setGraphIndex(nodes.size() - 1);
		idToIndex.put(node.getId(), node.getGraphIndex());
		Coordinate c = node.getCoord();
		if (c != null) {
			coordToIndex.put(c, node.getGraphIndex());
		}
	}
	
	public void addEdge(Edge edge) {
		edges.add(edge);
		edge.setGraphIndex(edges.size() - 1);
		nodes.get(edge.getFromNodeIndex()).addAdjacent(edge);
	}
	
	public int getNodeIndexById(Long nodeId) {
		return idToIndex.get(nodeId);
	}
	
	public int getNodeIndexByCoord(Coordinate c) {
		Node best = null;
		double min = Double.POSITIVE_INFINITY;
		for (Node n : nodes) {
			double d = n.getCoord().distanceTo(c);
			if (d < min) {
				best = n;
				min = d;
			}
		}
		
		return best.getGraphIndex();
	}
	
	public Node getNode(int index) {
		return nodes.get(index);
	}
	
	public Integer getNumberOfNodes() {
		return nodes.size();
	}
	
	public Integer getNumberOfEdges() {
		return edges.size();
	}
	
	public DistanceVector runDijkstra(int sourceIndex) {
		return runDijkstra(sourceIndex, -1);
	}
		
	public DistanceVector runDijkstra(int sourceIndex, int targetIndex) {
		DistanceVector vector = new DistanceVector(sourceIndex, this);
		Queue<DistanceElement> toVisit = new PriorityQueue<>();
				
		toVisit.add(vector.getElement(sourceIndex));
		while (!toVisit.isEmpty()) {
			DistanceElement min = toVisit.remove();
			if (targetIndex != -1 && targetIndex == min.getIndex()) {
				return vector;
			}
			
			if (min.isVisited()) continue;
			min.setVisited(true);
			
			for (Edge e : nodes.get(min.getIndex()).getAdjacents()) {
				DistanceElement neighbor = vector.getElement(e.getToNodeIndex());
				
				double newDistance = min.getDistance() + e.getCost();
				if (neighbor.getDistance() > newDistance && !neighbor.isVisited()) {
					neighbor.changeDistance(newDistance);
					neighbor.changePrevious(min.getIndex());
					toVisit.add(neighbor);
				}
			}
		}
		
		return vector;
	}
	
	public int getRandomNodeIndex() {
		return Math.abs(new Random().nextInt()%this.getNumberOfNodes());
	}
	
	public List<Long> getRandomPath() {
		List<Long> path = new ArrayList<>();
		int counter = 0;
		int currentPoint = Math.abs(new Random().nextInt()%this.getNumberOfNodes());
		System.out.println(currentPoint);
		path.add(nodes.get(currentPoint).getId());
		counter++;
		while (counter <= 40 && !this.getNode(currentPoint).getAdjacents().isEmpty()) {
			counter++;
			currentPoint = this.getNode(currentPoint).getAdjacents().get(0).getToNodeIndex();
			path.add(nodes.get(currentPoint).getId());
		}
		return path;
	}
}
