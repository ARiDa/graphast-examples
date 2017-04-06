package org.utils;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

import org.graphast.example.GraphImplBetter;
import org.graphast.model.Edge;
import org.graphast.model.Graph;
import org.graphast.model.GraphBounds;
import org.graphast.model.Node;
import org.graphast.query.route.shortestpath.AbstractShortestPathService;
import org.graphast.query.route.shortestpath.model.Path;
import org.graphast.query.route.shortestpath.model.RouteEntry;

public class VanilaDjikstra extends AbstractShortestPathService {
	
	private GraphImplBetter graph;
	
	public VanilaDjikstra(Graph graph) {
		super(graph);
		this.graph = (GraphImplBetter) graph;
	}
	
	public VanilaDjikstra(GraphBounds graphBounds) {
		super(graphBounds);
	}
	
	private Edge getEdge(long fromNodeId, long toNodeId, int distance) {
		Edge edge = null;
		for(Long outEdge : graph.getOutEdges(fromNodeId)) {
			edge = graph.getEdge(outEdge);
			if ((int) edge.getToNode() == toNodeId && edge.getDistance() == distance) {
				break;
			}
		}
		return edge;
	}

	@Override
	public Path shortestPath(Node source, Node target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path shortestPath(long source, long target) {
		Path path = new Path();
		HashMap<Long, RouteEntry> parents = new HashMap<>();
		DistanceVector vector = new DistanceVector(source, graph.getNumberOfNodes());
		Queue<DistanceElement> toVisit = new PriorityQueue<>();
		
		toVisit.add(vector.getElement(source));
		while(!toVisit.isEmpty()){
			DistanceElement min = toVisit.poll();
			if(target!=-1 && target==min.getIndex()){
				path.constructPath(target, parents, graph);
				//System.out.println(vector.getDistance(target));
				return path;
			}
			
			if(min.isVisited()) continue;
			min.setVisited(true);
			//System.out.println(min.getIndex());
			for(Edge e: graph.getOutEdgesBetter(min.getIndex())){
				DistanceElement neighbor = vector.getElement(e.getToNode());
				
				int newDistance = min.getDistance() + e.getDistance();
				if(neighbor.getDistance() > newDistance && !neighbor.isVisited()) {
					neighbor.changeDistance(newDistance);
					neighbor.changePrevious(min.getIndex());
					parents.put(neighbor.getIndex(), new RouteEntry(min.getIndex(), e.getDistance(), e.getId(), e.getLabel()));
					toVisit.add(neighbor);
				}
			}
		}
		//System.out.println(vector.getDistance(target));
		path.constructPath(target, parents, graph);
		return path;
	}

	@Override
	public Path shortestPath(Node source, Node target, Date time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path shortestPath(long source, long target, Date time) {
		// TODO Auto-generated method stub
		return null;
	}
}
