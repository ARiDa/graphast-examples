package org.hashMapGraph.model;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class Node implements java.io.Serializable{

	private HashMap<String, Object> attributes;
	//Edge in = edges arrives in the node
	private Set<Edge> EdgeIn = null;
	//Edge out = edges departures from node
	private Set<Edge> EdgeOut = null;
	
	// Attributes creates for support big graph example 
	// attribute id; 
	// attribute externalId;
	
	// Attributes model
	// attribute latitude
	// attribute longitude
	// attribute categoryId
	// attribute label
	
	
	//Five types of constructors
	
	public Node(Edge edgeIn, Edge edgeOut, double latitude, double longitude, int categoryId, String label) {
		
		this.EdgeIn = new HashSet<Edge>();
		this.EdgeOut = new HashSet<Edge>();
		
		this.EdgeIn.add(edgeIn);
		this.EdgeOut.add(edgeOut);
		
		attributes = new HashMap<String, Object>();
		
		attributes.put("latitude", latitude);
		attributes.put("longitude", longitude);
		attributes.put("categoryId", categoryId);
		attributes.put("label", label);
		
	}
	
	public Node(double latitude, double longitude, int categoryId, String label) {
		
		this.EdgeIn = new HashSet<Edge>();
		this.EdgeOut = new HashSet<Edge>();
		
		attributes = new HashMap<String, Object>();
		
		attributes.put("latitude", latitude);
		attributes.put("longitude", longitude);
		attributes.put("categoryId", categoryId);
		attributes.put("label", label);
		
	}
	
	public Node(double latitude, double longitude, int categoryId) {
		
		this.EdgeIn = new HashSet<Edge>();
		this.EdgeOut = new HashSet<Edge>();
		
		attributes = new HashMap<String, Object>();
		
		attributes.put("latitude", latitude);
		attributes.put("longitude", longitude);
		attributes.put("categoryId", categoryId);
		
		attributes.put("label", null);
	}
	
	public Node(double latitude, double longitude) {
		
		this.EdgeIn = new HashSet<Edge>();
		this.EdgeOut = new HashSet<Edge>();
		
		
		attributes = new HashMap<String, Object>();
		
		attributes.put("latitude", latitude);
		attributes.put("longitude", longitude);
		
		attributes.put("label", null);
		
	}
	
/*	public Node(long externalId, double latitude, double longitude) {
		
		this.EdgeIn = new HashSet<Edge>();
		this.EdgeOut = new HashSet<Edge>();
		
		attributes = new HashMap<String, Object>();
		
		attributes.put("externalId", externalId);
		attributes.put("latitude", latitude);
		attributes.put("longitude", longitude);
		
		attributes.put("label", null);
		
	}*/
	
	public Node(long id, double latitude, double longitude) {
		
		this.EdgeIn = new HashSet<Edge>();
		this.EdgeOut = new HashSet<Edge>();
		
		attributes = new HashMap<String, Object>();
		
		attributes.put("id", id);
		attributes.put("latitude", latitude);
		attributes.put("longitude", longitude);
		
		attributes.put("label", null);
		
	}
	
	public String getLabel(){
		String label = (String) attributes.get("label");
		if (label==null)
			label="#-#";
		return label;
	}

	public Set<Edge> getEdgein() {
		return EdgeIn;
	}
	

	public void setEdgein(Set<Edge> edgein) {
		EdgeIn = edgein;
	}
	
	//Add one edge for the hashSet of edges in
	public void addEdgeIn(Edge edge){
		this.EdgeIn.add(edge);
	}

	public  Set<Edge> getEdgeout() {
		
		return this.EdgeOut;
	}

	public void setEdgeout(Set<Edge> edgeout) {
		EdgeOut = edgeout;
	}
	
	//Add one edge for the hashSet of edges out
	public void addEdgeOut(Edge edge){
		this.EdgeOut.add(edge);
	}
	
	public void setId(Long id){
		attributes.put("id", id);
	}
	
	public Long getId(){
		return (Long)attributes.get("id");
	}
	
	public double getLatitude(){
		return (Double) attributes.get("latitude");
	}

	public double getLongitude(){
		return (Double) attributes.get("longitude");
	}
	
}
