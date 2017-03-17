package org.hashMapGraph.model;

import java.util.ArrayList;
import java.util.List;

//import com.github.davidmoten.rtree.Entry;
//import com.github.davidmoten.rtree.RTree;
//import com.github.davidmoten.rtree.geometry.Geometries;
//import com.github.davidmoten.rtree.geometry.Geometry;
//import com.github.davidmoten.rtree.geometry.Point;
//import com.graphhopper.util.StopWatch;



public class Graph implements java.io.Serializable {

	private List<Node> nodes; //Indide do array identifica o id do node
	private List<Edge> edges; //Indice do array identifica o id do edge
	private long id = 1;
	private long idEdge = 1;
//	private RTree<Long, Point> tree;
//	private RTree<Long, Point> stree;
	
	//For round latitude and longitude
	public static int LAT_LONG_CONVERTION_FACTOR = 100000;
	
	
	public Graph() {
		this.nodes = new ArrayList<Node>();
		this.edges = new ArrayList<Edge>();
//		this.tree = RTree.star().create();
	}
	
	public void addNode(Node node){
		node.setId(id);
		id++;
		nodes.add(node);
	}
	
	public Node getNode(Long id){
		for(int i=0; i < nodes.size() ; i++){
			if(nodes.get(i).getId().longValue()==id){
				return nodes.get(i);
			}
		}
		return null;
	}
	
	public Node getNode(Integer pos){
		return nodes.get(pos);
	}
	
	public Edge getEdge(Long e){
		for(int i=0; i < edges.size() ; i++){
			if(edges.get(i).getId() == id){
				return edges.get(i);
			}
		}
		return null;
	}
	
	public void addEdge(Edge edge){
		
		edge.setId(idEdge);
		idEdge++;
		edges.add(edge);
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	
	public long getNumberOfNodes() {
		return getNodes().size();
	}

	public long getNumberOfEdges() {
		return getEdges().size();
	}
	
	public Long getNodeId(double latitude, double longitude){

		Node nearest_node;
		
		nearest_node = getNearestNode(latitude, longitude);
		
		return nearest_node.getId();
	}
	
	//RTree is coming from GraphGenerator
	public Node getNearestNode(double latitude, double longitude) {
		
		Node nearestNode = this.nodes.get(0);
		double distance = distance(latitude, longitude, nearestNode.getLatitude(), nearestNode.getLongitude());
		
		for(Node node:this.nodes){
			double d = distance(latitude,longitude,node.getLatitude(),node.getLongitude());
			if(d<distance)
				nearestNode = node;
		}
		
		return nearestNode;
	}
	
	private double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		
		dist = dist * 1.609344;

		return (dist);
	}

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	
	
//	public RTree<Long,Point> getRTree(){
//		return this.tree;
//	}
//	
//	public void setRTree(RTree<Long,Point> tree){
//		this.tree = tree;
//	}
//	
//	public RTree<Long,Point> getRStarTree(){
//		return this.stree;
//	}
//	
//	public void setRStarTree(RTree<Long,Point> tree){
//		this.stree = tree;
//	}
//	
//	//For round latitude and longitude
//	public static long convert(double number, int factor) {
//        number = number * factor;
//        if (number > 0) {
//            number = Math.round(number);
//        } else {
//            number = -Math.round(-number);
//        }
//        System.out.println(number);
//        return (long)number;
//	}
	
}
