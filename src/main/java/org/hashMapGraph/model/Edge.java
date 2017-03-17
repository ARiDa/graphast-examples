package org.hashMapGraph.model;
import java.util.HashMap;


public class Edge implements java.io.Serializable{

	//Choose ID cost least memory
	private Node fromNode;
	private Node toNode;
	
 	private boolean bidirectional;
	private HashMap<String, Object> attributes;
	
	//Attributes for big graph example
	// fromNodeId
	// toNodeId
	// attribute id; 
	
	// distance
	// label	
	
	//Two types of constructors
	public Edge(Node fromNode, Node toNode, int distance, String label) {
		
		this.fromNode = fromNode;
		this.toNode = toNode;
		
		attributes = new HashMap<String, Object>();
		
		attributes.put("distance", distance);
		attributes.put("label", label);
	}
	
	public Edge(Node fromNode, Node toNode, int distance, String label, boolean bidirectional) {
		
		this.fromNode = fromNode;
		this.toNode = toNode;
		
		attributes = new HashMap<String, Object>();
		
		attributes.put("distance", distance);
		attributes.put("label", label);
		
		this.bidirectional = bidirectional;
	}
	
	
	public Edge(Node fromNode, Node toNode, int distance) {
		
		this.fromNode = fromNode;
		this.toNode = toNode;
		
		attributes = new HashMap<String, Object>();
		
		attributes.put("distance", distance);
	}
	
	public Edge(int distance, String label) {
		
		attributes = new HashMap<String, Object>();
		
		attributes.put("distance", distance);
		attributes.put("label", label);
	}
	
	public String getLabel(){
		return (String) attributes.get("label");
	}
	
	public int getDistance(){
		return (Integer) attributes.get("distance");
	}

	public Node getFromNode() {
		return fromNode;
	}
	
	public Long getFromNodeId() {
		return fromNode.getId();
	}

	public void setFrom(Node fromNode) {
		this.fromNode = fromNode;
	}

	public Node getToNode() {
		return toNode;
	}
	
	public Long getToNodeId() {
		return toNode.getId();
	}

	public void setTo(Node toNode) {
		this.toNode = toNode;
	}

	public boolean isBidirectional() {
		return bidirectional;
	}

	public void setBidirectional(boolean bidirectional) {
		this.bidirectional = bidirectional;
	}

	public void setId(Long id){
		attributes.put("id", id);
	}
	
	public Long getId(){
		return (Long)attributes.get("id");
	}
	
	public boolean getBidirectional(){
		return bidirectional;
	}
	
}
