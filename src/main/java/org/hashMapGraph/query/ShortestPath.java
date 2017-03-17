package org.hashMapGraph.query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.hashMapGraph.model.Edge;
import org.hashMapGraph.model.Graph;
import org.hashMapGraph.model.Node;


public class ShortestPath {
	
	//Structure for store distance during Dijkstra
	private HashMap<Long, Integer> distance;
	
	//Structure for store the last node for origin path
	private HashMap<Long, Integer> shortestDistances;
	
	//Sets to separates settled nodes and unsettled nodes
	private Set<Long> settledNodes;
	private PriorityQueue<QueueEntry> unsettledNodes;
	
	//Structure for reconstruct the path (he saves a node with your Neighbor with minimal cost)
	private HashMap<Node, Node> origin;
	
	//Graph received
	Graph graph;
	
	int aux = 0;
		
	//Initiate the ShortestPath with the Graph that will be used 
	public ShortestPath(Graph graph) {
		this.graph = graph;
		//this.nodes = graph.getNodes();
		//this.edges = graph.getEdges();
	}

	public Path executeDijkstra(Long source, Long target){
		
		
		//initiate
		distance = new HashMap<Long, Integer>();
		shortestDistances = new HashMap<Long, Integer>();
		settledNodes = new HashSet<Long>();
		unsettledNodes = new PriorityQueue<QueueEntry>();
		origin = new HashMap<Node,Node>();
		
		shortestDistances.put(source, 0);;
		distance.put(source, 0);
		QueueEntry e = new QueueEntry(source, 0);
		
		unsettledNodes.add(e);
		
		//Series of edges relaxations
		while(!unsettledNodes.isEmpty()){
			
			e = unsettledNodes.poll();
			if(!settledNodes.contains(e.getId())){
				settledNodes.add(e.getId());
				System.out.println("Adding to settled: " + e.getId());
				
				distance.put(e.getId(), e.getTravelTime());
				
				//Done because i need the whole structure
				Node min = graph.getNode(e.getId());
				
				//find minimal distances
				List<Node> adjacentNodes = getNeighbors(min);

				
				if(adjacentNodes != null){
					for(Node node : adjacentNodes){
						//System.out.println(node.getId());
						if(!settledNodes.contains(node.getId())){
							int d = getShortestDistance(min.getId(), shortestDistances) + getDistance(min,node);
							if(getShortestDistance(node.getId(), shortestDistances) > d){
								
								//Save the best node for now
								origin.put(node, min);
								
								QueueEntry l = new QueueEntry(node.getId(), d);
								unsettledNodes.remove(l);
								unsettledNodes.add(l);
								
								shortestDistances.put(node.getId(), d);
							}
						}	
					}
				}	
			}
			
		}	
			
		return getPath(graph.getNode(target));
	}
	


	//Method to pick the shortest distance
	public int getShortestDistance(long id, HashMap<Long, Integer> minCost)
    {
		if(minCost.containsKey(id))
			return minCost.get(id);
		else
			return Integer.MAX_VALUE;
    }
	
	
	//Method to create a List with all neighbors
	private List<Node> getNeighbors(Node min) {
		//System.out.println(aux++);
		List<Node> neighbors = new ArrayList<Node>();
		
	    if(min == null || min.getEdgeout().isEmpty()) return null;

	    Long id = min.getId();
	    
		for(Edge edge : min.getEdgeout()){
			if (edge.getFromNodeId().equals(id))
				if (!settledNodes.contains(edge.getToNode().getId()))
					neighbors.add(edge.getToNode());
			
			else if (edge.isBidirectional() && edge.getToNodeId().equals(id)) {
				System.out.println(edge.getFromNode().getId());
				if (!settledNodes.contains(edge.getFromNode().getId()))
					neighbors.add(edge.getFromNode());
			}

			System.out.println(min.getId()+" "+edge.getFromNodeId()+"->"+edge.getToNodeId());
		}
		System.out.println(neighbors.size());
		return neighbors;
	}
	
	private int getDistance(Node min, Node node) {
		
		for (Edge edge : min.getEdgein()) {	
			if (edge.getFromNode().equals(min))
				return edge.getDistance();
	    }
		
		for (Edge edge : min.getEdgeout()) {	
			if (edge.getToNode().equals(min))
				return edge.getDistance();
	    }
	    return -1;

	}
	
	//Method to get the path for a determinate node target
	public Path getPath(Node target) {
		Path path = new Path();
		Node step = target;
		// check if a path exists
		System.out.println(origin.size());
		if (origin.get(step) == null) {
			return null;
	    }
	    path.getPathMin().add(step);
	    while ((step = origin.get(step)) != null) {
	    	path.getPathMin().add(step);
	    }
	    
	    // Put it into the correct order
	    Collections.reverse(path.getPathMin());
	    return path;
	}

}
