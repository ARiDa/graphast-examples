package org.vanilaGraph;

import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.storage.GraphStorage;
import com.graphhopper.util.EdgeIterator;

import java.util.HashMap;
import java.util.HashSet;

import org.graphast.config.Configuration;
import org.graphast.importer.OSMToGraphHopperReader;

public class TableParserUtils {
	
	public static Graph getYuriGraph() {
		Graph g = new Graph();
		g.addNode(new Node(1l));
		g.addNode(new Node(2l));
		g.addNode(new Node(3l));
		g.addNode(new Node(4l));
		g.addNode(new Node(5l));
		g.addNode(new Node(6l));
		g.addNode(new Node(7l));
		g.addEdge(new Edge(1l, 0, 1, 6));
		g.addEdge(new Edge(2l, 1, 2, 2));
		g.addEdge(new Edge(3l, 0, 5, 3));
		g.addEdge(new Edge(4l, 1, 3, 7));
		g.addEdge(new Edge(5l, 1, 6, 12));
		g.addEdge(new Edge(6l, 3, 4, 11));
		g.addEdge(new Edge(7l, 4, 6, 3));
		g.addEdge(new Edge(8l, 3, 5, 15));
		return g;
	}
	
	public static Graph getSmallTestGraph() {
		Graph g = new Graph();
		g.addNode(new Node(1l));
		g.addNode(new Node(2l));
		g.addNode(new Node(3l));
		g.addNode(new Node(4l));
		g.addNode(new Node(5l));
		g.addNode(new Node(6l));
		g.addEdge(new Edge(1l, 0, 1, 1));
		g.addEdge(new Edge(2l, 0, 2, 10));
		g.addEdge(new Edge(3l, 1, 2, 2));
		g.addEdge(new Edge(4l, 2, 3, 3));
		g.addEdge(new Edge(5l, 2, 4, 10));
		g.addEdge(new Edge(6l, 3, 4, 2));
		g.addEdge(new Edge(7l, 3, 5, 20));
		g.addEdge(new Edge(8l, 4, 5, 1));
		return g;
	}
	
	public static Graph getMediumTestGraph() {
		Graph g = new Graph();
		g.addNode(new Node(1l));
		g.addNode(new Node(2l));
		g.addNode(new Node(3l));
		g.addNode(new Node(4l));
		g.addNode(new Node(5l));
		g.addNode(new Node(6l));
		g.addNode(new Node(7l));
		g.addNode(new Node(8l));
		g.addNode(new Node(9l));
		g.addNode(new Node(10l));
		g.addNode(new Node(11l));
		g.addNode(new Node(12l));
		g.addEdge(new Edge(1l, 0, 1, 1306));
		g.addEdge(new Edge(2l, 0, 4, 2161));
		g.addEdge(new Edge(3l, 0, 6, 2661));
		g.addEdge(new Edge(4l, 1, 2, 629));
		g.addEdge(new Edge(5l, 1, 3, 919));
		g.addEdge(new Edge(6l, 2, 3, 435));
		g.addEdge(new Edge(7l, 3, 4, 1225));
		g.addEdge(new Edge(8l, 3, 5, 1983));
		g.addEdge(new Edge(9l, 4, 5, 1258));
		g.addEdge(new Edge(10l, 4, 6, 1483));
		g.addEdge(new Edge(11l, 5, 6, 1532));
		g.addEdge(new Edge(12l, 5, 8, 2113));
		g.addEdge(new Edge(13l, 5, 9, 2161));
		g.addEdge(new Edge(14l, 6, 7, 661));
		g.addEdge(new Edge(15l, 7, 8, 1145));
		g.addEdge(new Edge(16l, 7, 10, 1613));
		g.addEdge(new Edge(17l, 8, 9, 1709));
		g.addEdge(new Edge(18l, 8, 10, 725));
		g.addEdge(new Edge(19l, 8, 11, 383));
		g.addEdge(new Edge(20l, 9, 11, 2145));
		g.addEdge(new Edge(21l, 10, 11, 338));
		return g;
	}
	
	public static Graph getOSMGraph(String filename) {
		Graph graph = new Graph();
		//double initialTime = System.currentTimeMillis();
		String osmFile = filename;
		String hopperDir = Configuration.USER_HOME + "/graphhopper/berlin";
		GraphHopper gh = OSMToGraphHopperReader.createGraph("../../berlin-latest.osm.pbf", hopperDir, false, false);
		GraphStorage gs = gh.getGraph();
		EdgeIterator edgeIterator = gs.getAllEdges();
		
		HashSet<Long> set = new HashSet<>(); 
		HashMap<Long, Integer> idToGraphIndex = new HashMap<>();
		
		while(edgeIterator.next()) {
			long externalFromNodeId = edgeIterator.getBaseNode();
			long externalToNodeId = edgeIterator.getAdjNode();
			long externalEdgeId = edgeIterator.getEdge();
			double distance = edgeIterator.getDistance();
			
			double fromLat = gs.getNodeAccess().getLatitude((int)externalFromNodeId);
			double fromLng = gs.getNodeAccess().getLongitude((int)externalFromNodeId);	

			double toLat = gs.getNodeAccess().getLatitude((int)externalToNodeId);
			double toLng = gs.getNodeAccess().getLongitude((int)externalToNodeId);
			// Nodes
			Node node;
			if (!set.contains(externalFromNodeId)) {
				node = new Node(externalFromNodeId, new Coordinate(fromLat, fromLng));
				graph.addNode(node);
				set.add(externalFromNodeId);
				idToGraphIndex.put(externalFromNodeId, node.getGraphIndex());
			}
			if (!set.contains(externalToNodeId)) {
				node = new Node(externalToNodeId, new Coordinate(toLat, toLng));
				graph.addNode(node);
				set.add(externalToNodeId);
				idToGraphIndex.put(externalToNodeId, node.getGraphIndex());
			}
			// Edges
			int direction = 9999;
			try {
				direction = getDirection(edgeIterator.getFlags());
			} catch (Exception e) {}
			if(externalFromNodeId == externalToNodeId) {
				//System.out.println("Edge not created");
				continue;
			}
			if (direction == 0 || direction == 1)
				graph.addEdge(new Edge(externalEdgeId, idToGraphIndex.get(externalFromNodeId), idToGraphIndex.get(externalToNodeId), distance));
			if (direction == 0 || direction == -1)
				graph.addEdge(new Edge(externalEdgeId, idToGraphIndex.get(externalToNodeId), idToGraphIndex.get(externalFromNodeId), distance));
			//if (direction == 9999)
				//System.out.println("Edge not created. Invalid direction");
		}
		//System.out.println("Time = " + (System.currentTimeMillis() - initialTime));
		return graph;
    }
	
	public static int getDirection(long flags) {
		long direction = (flags & 3);

		if(direction ==  1) {
			return 1;   // One direction: From --> To 
		} else if(direction ==  2) {
			return -1;  // One direction: To --> From
		} else if(direction == 3) {
			return 0;   // Bidirectional: To <--> From
		}
		else {
			throw new IllegalArgumentException("Invalid flag: " + direction);
		}
	}
	
}