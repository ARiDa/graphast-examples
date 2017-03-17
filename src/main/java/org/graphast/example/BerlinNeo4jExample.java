package org.graphast.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import org.hashMapGraph.main.GraphGenarator;
import org.hashMapGraph.model.Edge;
import org.hashMapGraph.model.Graph;
import org.neo4j.cypher.internal.compiler.v2_3.mutation.CreateRelationship;
import org.neo4j.graphalgo.CommonEvaluators;
import org.neo4j.graphalgo.CostEvaluator;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PathExpander;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.UniqueFactory;

import com.graphhopper.util.StopWatch;

public class BerlinNeo4jExample{
	
	private static enum RelTypes implements RelationshipType
	{
	    NEIG
	}
	
	public static void main(String[] args){
		if(args.length!=5){
			System.out.println("Usage: lat1 long1 lat2 long2 file");
			
		}
		else{
			//GraphDatabaseService graphDb = generateGraph();
			System.out.println("Importando grafo");
			GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File("../../berlin.db"));
			System.out.println("importado");
			PathExpander expander = PathExpanders.forTypeAndDirection(RelTypes.NEIG,Direction.OUTGOING);
			CostEvaluator<Double> cost = CommonEvaluators.doubleCostEvaluator("weight");
			PathFinder<WeightedPath> shortestPath = GraphAlgoFactory.dijkstra(expander, cost);
			
			
			try (Transaction tx = graphDb.beginTx()){
				//System.out.println("Nodes: "+nNodes(graphDb));
				//System.out.println("Edges: "+nRelationships(graphDb));
				//System.out.println(Double.parseDouble(args[0])+" , "+Double.parseDouble(args[1]));
				//System.out.println(Double.parseDouble(args[2])+" , "+Double.parseDouble(args[3]));
				Node from = findClosestNode(graphDb, Double.parseDouble(args[0]),Double.parseDouble(args[1]));
				Node to = findClosestNode(graphDb, Double.parseDouble(args[2]),Double.parseDouble(args[3]));
				//Node from = getNodeByProperty(graphDb, "label", "a");
				//Node to = getNodeByProperty(graphDb, "label", "g");
				//System.out.println(from.getProperty("lat")+" "+from.getProperty("lon"));
				//System.out.println(to.getProperty("lat")+" "+to.getProperty("lon"));
//				System.out.println(from.getProperty("id")+" -> "+to.getProperty("id"));
				
				StopWatch sw = new StopWatch();
				sw.start();
				
				WeightedPath path = shortestPath.findSinglePath(from, to);
				sw.stop();
				
				//System.out.println(path.weight()/1000);
//				System.out.println(path.length());
//				drawPath(path);
				
				try{
					FileWriter fw = new FileWriter(args[4]);
				    BufferedWriter bw = new BufferedWriter(fw);
				    PrintWriter out = new PrintWriter(bw);
				    out.println(sw.getTime());
				    out.println(path.weight()/1000);
				    out.close();
				    //System.out.println("Salvo!");
				} catch (IOException e) {}
				
				tx.success();
			}
			
	//		PathExpander<STATE> expander = Traversal.expanderForTypes(
	//	              ExampleGraphService.MyDijkstraTypes.neighbor, Direction.OUTGOING );
	//		  CostEvaluator<Double> costEvaluator = CommonEvaluators.doubleCostEvaluator("weight");
	//		 
	//		  //dijkstra
	//		 PathFinder<WeightedPath> pathFinder = GraphAlgoFactory.dijkstra(expander, costEvaluator);
	//		
			graphDb.shutdown();
		}
	}
	
	public static void drawPath(WeightedPath path){
		Iterator<PropertyContainer> it = path.iterator();
		while(it.hasNext()){
			PropertyContainer prop = it.next();
			System.out.print(prop.getProperty("label"));
			if(it.hasNext())
				System.out.print(" -> ");
		}
	}
	
 	public static Node getNodeByProperty(GraphDatabaseService graphDb, String prop, Object obj){
		Node node = null;
		
		Iterator<Node> it = graphDb.getAllNodes().iterator();
		
		while(it.hasNext()){
			Node n = it.next();
			//System.out.println(n.getProperty(prop)+" "+obj);
			if(obj.equals(n.getProperty(prop))){
				node = n;
				break;
			}
		}
		
		return node;
	}
	
	public static int getPathCost(WeightedPath path){
		int cost = 0;
		
		for(Relationship rel: path.relationships()){
			cost += (int)rel.getProperty("weight");
		}
		
		return cost;
	}
	
	public static long nNodes(GraphDatabaseService graphDb){
		long i = 0;
		ResourceIterator<Node> it = graphDb.getAllNodes().iterator();
		while(it.hasNext()){
			it.next();
			i+=1;
		}
		return i;
	}
	
	public static long nRelationships(GraphDatabaseService graphDb){
		long i = 0;
		ResourceIterator<Relationship> it = graphDb.getAllRelationships().iterator();
		while(it.hasNext()){
			it.next();
			i+=1;
		}
		return i;
	}
	
	public static Node findClosestNode(GraphDatabaseService graphDb, double lat, double lon){
		
		Node node = null;
		double distance = Double.MAX_VALUE;
		ResourceIterator<Node> it = graphDb.getAllNodes().iterator();
		while(it.hasNext()){
			Node n = it.next();
			double d = distance((double)n.getProperty("lat"),(double)n.getProperty("lon"),
					lat,lon);
			if (d<distance){
				node = n;
				distance = d;
			}
		}
		
		return node;
	}
	
	private static double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		
		dist = dist * 1.609344;

		return (dist);
	}
	
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	
	
    public static Node getOrCreateNodeWithUniqueFactory(long id, GraphDatabaseService graphDb ){
	    UniqueFactory<Node> factory = new UniqueFactory.UniqueNodeFactory(graphDb,"points") {
			
			@Override
			protected void initialize(Node created, Map<String, Object> properties) {
				// TODO Auto-generated method stub
				created.setProperty( "id", properties.get( "id" ) );
			}
		};
	
	    return factory.getOrCreate( "id", id );
    }
	
//    public static Relationship getOrCreateRelationshipWithUniqueFactory(long id, GraphDatabaseService graphDb ){
//	    UniqueFactory<Relationship> factory = new UniqueFactory.UniqueRelationshipFactory(graphDb,"points") {
//			
//			@Override
//			protected void initialize(Relationship created, Map<String, Object> properties) {
//				// TODO Auto-generated method stub
//				created.setProperty( "id", properties.get( "id" ) );
//			}
//		};
//	
//	    return factory.getOrCreate( "id", id );
//    }
    
    public static GraphDatabaseService generateGraph(){
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File("../../berlin.db"));
		String filePath = "../../";
		GraphGenarator generator = new GraphGenarator();
		Graph graph = generator.generateBerlin(filePath);
		//Graph graph = generator.generateMiniTest();
		//Graph graph = generator.generateTest1();
		//Graph graph = generator.generateMonaco(filePath);
		
		for(Edge edge: graph.getEdges()){
			
			try( Transaction tx = graphDb.beginTx()){
			
				Node from = getOrCreateNodeWithUniqueFactory(edge.getFromNode().getId(),graphDb);
				
				from.setProperty("lat", edge.getFromNode().getLatitude());
				from.setProperty("lon", edge.getFromNode().getLongitude());
				from.setProperty("id", edge.getFromNode().getId());
				from.setProperty("label", edge.getFromNode().getLabel());
				//System.out.println(from.getProperty("lat")+" "+from.getProperty("lon"));
				
				Node to = getOrCreateNodeWithUniqueFactory(edge.getToNode().getId(),graphDb);
				to.setProperty("lat", edge.getToNode().getLatitude());
				to.setProperty("lon", edge.getToNode().getLongitude());
				to.setProperty("id", edge.getToNode().getId());
				to.setProperty("label", edge.getToNode().getLabel());
				
				Relationship rel = from.createRelationshipTo(to, RelTypes.NEIG);
				rel.setProperty("weight", edge.getDistance());
				rel.setProperty("label", edge.getLabel());
				rel.setProperty("id", edge.getId());
				
//				if(edge.getBidirectional()){
//					Relationship rel2 = to.createRelationshipTo(from, RelTypes.NEIG);
//					rel2.setProperty("weight", edge.getDistance());
//					rel2.setProperty("label", edge.getLabel()+"(Inverted)");
//					rel2.setProperty("id", edge.getId());
//				}
				tx.success();
			}

		}
		System.out.println("grafo Berlin Neo4j gerado");
		return graphDb;
	}
	
}
