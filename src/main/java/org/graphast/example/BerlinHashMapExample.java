package org.graphast.example;

import com.graphhopper.util.StopWatch;

import org.hashMapGraph.model.Node;

import org.hashMapGraph.model.Coordinate;
import org.hashMapGraph.model.Graph;
import org.hashMapGraph.model.Edge;
import org.hashMapGraph.query.Path;
import org.hashMapGraph.query.ShortestPath;
import org.hashMapGraph.main.GraphGenarator;

public class BerlinHashMapExample {
	static long start;
	static long now;
	static double end;
	
	public static void main( String[] args ) {
		GraphGenarator generator = new GraphGenarator();
		String berlinFilePath = "../../";
		Graph graph = generator.generateBerlin(berlinFilePath);
		
//		System.out.println(findExtremeNorthNode(graph));
//		System.out.println(findExtremeSouthNode(graph));
//		System.out.println(findExtremeEastNode(graph));
//		System.out.println(findExtremeWestNode(graph));
		
		Coordinate extremeNorth = new Coordinate(52.671235,13.279887);
		Coordinate extremeSouth = new Coordinate(52.34046,13.647459);
		Coordinate extremeEast = new Coordinate(52.43818, 13.75809);
		Coordinate extremeWest = new Coordinate(52.435348,13.074094);
		
		Long source1 = graph.getNodeId(extremeNorth.getLatitude(), extremeNorth.getLongitude());
		Long target1 = graph.getNodeId(extremeSouth.getLatitude(), extremeSouth.getLongitude());
		
		Long source2 = graph.getNodeId(extremeEast.getLatitude(), extremeEast.getLongitude());
		Long target2 = graph.getNodeId(extremeWest.getLatitude(), extremeWest.getLongitude());
		
		ShortestPath shortestPath = new ShortestPath(graph);
		Path path;
		
		StopWatch sw2 = new StopWatch();
		sw2.start();
		
		path = shortestPath.executeDijkstra(source1,target1);
		System.out.println(path.getPathMin().size());
		sw2.stop();


	}
	

	public static Edge findEdge(Node source, Node target){
		for(Edge edge : source.getEdgeout()){
			if(edge.isBidirectional()){
				if(edge.getToNode() == target){
					return edge;
				}
				else if(edge.getFromNode() == target){
					return edge;
				}
			}
			else{
				if(edge.getToNode() == target){
					return edge;
				}
			}
		}
		return null;
	}
	
	public static Coordinate findExtremeNorthNode(Graph graph){
		double lat = -100;
		double lng = 0;
		for (Node node : graph.getNodes()){
			if(node.getLatitude()>lat){
				lat = node.getLatitude();
				lng = node.getLongitude();
			}
		}
		
		return new Coordinate(lat, lng);
	}

	public static Coordinate findExtremeSouthNode(Graph graph){
		double lat = 100;
		double lng = 0;
		for (Node node : graph.getNodes()){
			if(node.getLatitude()<lat){
				lat = node.getLatitude();
				lng = node.getLongitude();
			}
		}
		
		return new Coordinate(lat, lng);
	}

	public static Coordinate findExtremeEastNode(Graph graph){
		double lng = -100;
		double lat = 0;
		for (Node node : graph.getNodes()){
			if(node.getLongitude()>lng){
				lng = node.getLongitude();
				lat = node.getLatitude();
			}
		}
		
		return new Coordinate(lat, lng);
	}
	
	public static Coordinate findExtremeWestNode(Graph graph){
		double lng = 100;
		double lat = 0;
		for (Node node : graph.getNodes()){
			if(node.getLongitude()<lng){
				lng = node.getLongitude();
				lat = node.getLatitude();
			}
		}
		
		return new Coordinate(lat, lng);
	}
}
