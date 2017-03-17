package org.graphast.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import org.graphast.config.Configuration;
import org.graphast.model.Graph;
import org.graphast.model.GraphImpl;
import org.graphast.model.Node;
import org.graphast.query.route.shortestpath.ShortestPathService;
import org.graphast.query.route.shortestpath.dijkstra.DijkstraConstantWeight;
import org.graphast.query.route.shortestpath.model.Path;

import com.graphhopper.util.StopWatch;

import it.unimi.dsi.fastutil.ints.IntBigArrayBigList;

public class MonacoGraphastExample {
	public static void main( String[] args ) {
		
//		if(args.length!=5){
//			System.out.println("Usage: lat1 long1 lat2 long2 file");
//			
////			Graph graph = new GraphImpl(Configuration.USER_HOME + "/graphast/berlin");
////			graph.load();
////			generateQueries(graph,100,"tests.txt");
////			System.out.println("casos gerados");
//		}
//		
//		else{
			// The graph in ${USER_HOME}/graphast/example must already exist.
			Graph graph = new GraphImpl(Configuration.USER_HOME + "/graphast/monaco");
			graph.load();
//			long from = graph.getNodeId(Double.parseDouble(args[0]),Double.parseDouble(args[1]));
//			long to = graph.getNodeId(Double.parseDouble(args[2]),Double.parseDouble(args[3]));
			long from = graph.getNodeId(43.723389,7.410663);
			long to = graph.getNodeId(43.727184,7.407121);
			System.out.println(from+" -> "+graph.getNode(from).getLatitude()+" "+graph.getNode(from).getLongitude());
			System.out.println(to+" -> "+graph.getNode(to).getLatitude()+" "+graph.getNode(to).getLongitude());
			ShortestPathService shortestPath = new DijkstraConstantWeight(graph);
	
			StopWatch sw = new StopWatch();
			sw.start();
			Path path = shortestPath.shortestPath(from, to);		
			sw.stop();
			
//			try{
//				FileWriter fw = new FileWriter(args[4]);
//			    BufferedWriter bw = new BufferedWriter(fw);
//			    PrintWriter out = new PrintWriter(bw);
//			    out.print(sw.getTime());
//			    out.close();
//			} catch (IOException e) {}
			
			System.out.println("Distance: "+path.getTotalDistance());
			System.out.println("Length: "+path.getEdges().size());
			System.out.println(path.getInstructions());
			
	//		System.out.println("Mean execution time(ms):" + sumTimes/numExperiments);
	//		System.out.println(MemMon.memoryInfo());
	//		System.out.println(from);
	//		System.out.println(to);
	//		System.out.println(path);
		//}
		
	}
	
	public static void generateQueries(Graph graph,int amount, String file){
		try{
			FileWriter fw = new FileWriter(file);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);
		    IntBigArrayBigList nodes = graph.getNodes();
		    long nNodes = nodes.size64();
		    Random randomGenerator = new Random();
		    ShortestPathService shortestPath = new DijkstraConstantWeight(graph);
		    
		    int i = 0;
		    while(i<amount){
		    	long from = nodes.get(Math.abs(randomGenerator.nextLong()%nNodes));
		    	long to = nodes.get(Math.abs(randomGenerator.nextLong()%nNodes));
		    	try{
			    	shortestPath.shortestPath(from, to);
			    	
			    	Node fromNode = graph.getNode(from);
			    	Node toNode = graph.getNode(to);
			    	out.println(fromNode.getLatitude()+","+fromNode.getLongitude()+","+
			    			toNode.getLatitude()+","+toNode.getLongitude());
			    	i++;
		    	}catch(Exception e){}
		    }
		    out.close();
		} catch (IOException e) {}
	}
}
