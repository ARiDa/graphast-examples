package org.hashMapGraph.main;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;
import com.graphhopper.GraphHopper;
import com.graphhopper.storage.GraphStorage;
import com.graphhopper.util.EdgeIterator;
import com.graphhopper.util.StopWatch;

import it.unimi.dsi.fastutil.ints.Int2LongOpenHashMap;
import org.hashMapGraph.model.Edge;
import org.hashMapGraph.model.Graph;
import org.hashMapGraph.model.Node;
import org.hashMapGraph.util.MemMon;
import org.hashMapGraph.util.NumberUtils;
import org.hashMapGraph.util.OSMToGraphHopperReader;

public class GraphGenarator {
	
	//Conversion
	public static int LAT_LONG_CONVERTION_FACTOR = 1000000;
	
	public GraphGenarator(){
		
	}
	
	public Graph generateMiniTest(){
		Graph graph = new Graph();
		
		Node va = new Node(1, 1, 0, "a");
		Node vb = new Node(1, 2, 0, "b");
		Node vc = new Node(1, 3, 0, "c");
		Node vd = new Node(1, 4, 0, "d");
		Node ve = new Node(1, 5, 0, "e");
		
		Edge e1 = new Edge(va,vb,1,"ab");
		graph.addEdge(e1);
		va.addEdgeOut(e1);
		vb.addEdgeIn(e1);
		
		Edge e2 = new Edge(vb,vd,2,"bd");
		graph.addEdge(e2);
		vb.addEdgeOut(e2);
		vd.addEdgeIn(e2);
		
		Edge e3 = new Edge(vb,vc,3,"bc");
		graph.addEdge(e3);
		vb.addEdgeOut(e3);
		vc.addEdgeIn(e3);
		
		Edge e4 = new Edge(vd,vc,1,"dc");
		graph.addEdge(e4);
		vd.addEdgeOut(e4);
		vc.addEdgeIn(e4);
		
		Edge e5 = new Edge(vc,vd,1,"cd");
		graph.addEdge(e5);
		vc.addEdgeOut(e5);
		vd.addEdgeIn(e5);
		
		Edge e6 = new Edge(vd,ve,5,"de");
		graph.addEdge(e6);
		vd.addEdgeOut(e6);
		ve.addEdgeIn(e6);
		
		Edge e7 = new Edge(vc,ve,1,"ce");
		graph.addEdge(e7);
		vc.addEdgeOut(e7);
		ve.addEdgeIn(e7);
		
		graph.addNode(va);
		graph.addNode(vb);
		graph.addNode(vc);
		graph.addNode(vd);
		graph.addNode(ve);

		System.out.println("----------Dados do Grafo teste gerado----------");
		System.out.println("Number of Nodes: " + graph.getNumberOfNodes());
		System.out.println("Number of Edges: " + graph.getNumberOfEdges());
		return graph;
	}
	
	public Graph generateTest1(){
		Graph graph = new Graph();
		
		Node va = new Node(1, 1, 0, "a");
		Node vb = new Node(1, 2, 0, "b");
		Node vc = new Node(1, 3, 0, "c");
		Node vd = new Node(1, 4, 0, "d");
		Node ve = new Node(1, 5, 0, "e");
		Node vf = new Node(1, 5, 0, "f");
		Node vg = new Node(1, 5, 0, "g");
		
		Edge e1 = new Edge(va,vb,1,"Rua A");
		graph.addEdge(e1);
		va.addEdgeOut(e1);
		vb.addEdgeIn(e1);
		
		Edge e2 = new Edge(va,vc,5,"Rua B");
		graph.addEdge(e2);
		va.addEdgeOut(e2);
		vc.addEdgeIn(e2);
		
		Edge e3 = new Edge(vb,vc,3,"Rua C");
		graph.addEdge(e3);
		vb.addEdgeOut(e3);
		vc.addEdgeIn(e3);
		
		Edge e4 = new Edge(vc,vd,3,"Rua D");
		graph.addEdge(e4);
		vc.addEdgeOut(e4);
		vd.addEdgeIn(e4);
		
		Edge e5 = new Edge(vd,ve,3,"Rua E");
		graph.addEdge(e5);
		vd.addEdgeOut(e5);
		ve.addEdgeIn(e5);
		
		Edge e6 = new Edge(vd,vf,4,"Rua F");
		graph.addEdge(e6);
		vd.addEdgeOut(e6);
		vf.addEdgeIn(e6);
		
		Edge e7 = new Edge(ve,vf,2,"Rua G");
		graph.addEdge(e7);
		ve.addEdgeOut(e7);
		vf.addEdgeIn(e7);
		
		Edge e8 = new Edge(vg,vf,1,"Rua H");
		graph.addEdge(e8);
		vg.addEdgeOut(e8);
		vf.addEdgeIn(e8);
		
		graph.addNode(va);
		graph.addNode(vb);
		graph.addNode(vc);
		graph.addNode(vd);
		graph.addNode(ve);
		graph.addNode(vf);
		graph.addNode(vg);
		
		return graph;
	}
	
	//Berlin
	public Graph generateBerlin(String path){
			
			Date date = new Date();
			String initialDate = date.toString();
			double initialTime = System.currentTimeMillis();
			
			Graph graph = null;
			//checking if the graph already exists
			try{
				ObjectInputStream in = new ObjectInputStream(
						new FileInputStream("BerlinHashMapGraph"));
				graph = (Graph) in.readObject();
				in.close();
				
			}
			catch(IOException io){
				//graph doesn't exists
				System.out.println("Berlin graph doesn't exist yet.");
				graph = new Graph();

				//Linux
				//GraphHopper gh = OSMToGraphHopperReader.createGraph("/home/lucasvasconcelos/Downloads/berlin-latest.osm.pbf", "/home/lucasvasconcelos/Berlin", false, false);

				//Linux (Bremen)
				//GraphHopper gh = OSMToGraphHopperReader.createGraph("/home/lucasvasconcelos/Downloads/bremen-latest.osm.pbf", "/home/lucasvasconcelos/Bremen", false, false);
				
				//Windows
				//GraphHopper gh = OSMToGraphHopperReader.createGraph("/users/vasco/Downloads/berlin-latest.osm.pbf", "/users/vasco/Downloads/Graphast-Graph-Test/Berlin", false, false);
				
				//Mac Peres
				GraphHopper gh = OSMToGraphHopperReader.createGraph(path+"berlin-latest.osm.pbf", "Berlin", false, false);
				
				GraphStorage gs = gh.getGraph();
				EdgeIterator edgeIterator = gs.getAllEdges();
				
				//To know which node has already check
				//Int2LongOpenHashMap hashExternalIdToId = new Int2LongOpenHashMap();
				HashMap<Long, Integer> idToNodePosition = new HashMap<Long, Integer>();
				
				//Statistics
				int count = 0;
				int countInvalidDirection = 0;
				int countBidirectional= 0;
				int countOneWay = 0;
				int countOneWayInverse = 0;
				
				while(edgeIterator.next()) {
					count++;
					
					Long externalFromNodeId = new Long(edgeIterator.getBaseNode());
					Long externalToNodeId = new Long(edgeIterator.getAdjNode());
					
					int distance = (int)NumberUtils.round(edgeIterator.getDistance() * 1000, 0); // Convert distance from meters to millimeters
					
					String label = edgeIterator.getName();
					
					double latitudeFrom = latLongToDouble(latLongToInt(gs.getNodeAccess().getLatitude(externalFromNodeId.intValue())));
					double longitudeFrom = latLongToDouble(latLongToInt(gs.getNodeAccess().getLongitude(externalFromNodeId.intValue())));	

					double latitudeTo = latLongToDouble(latLongToInt(gs.getNodeAccess().getLatitude(externalToNodeId.intValue())));
					double longitudeTo = latLongToDouble(latLongToInt(gs.getNodeAccess().getLongitude(externalToNodeId.intValue())));
					
					Node fromNode = null,toNode = null;
					
					long fromNodeId, toNodeId;
					
					//if(!hashExternalIdToId.containsKey(externalFromNodeId)){
					if(!idToNodePosition.containsKey(externalFromNodeId)){

						fromNode = new Node(externalFromNodeId, latitudeFrom, longitudeFrom);
						graph.addNode(fromNode);
						fromNodeId = (long)fromNode.getId();
						//hashExternalIdToId.put(externalFromNodeId, fromNodeId);
						idToNodePosition.put(externalFromNodeId, graph.getNodes().size()-1);
					} 
					else {
						fromNode = graph.getNode(idToNodePosition.get(externalFromNodeId));
					}

					if(!idToNodePosition.containsKey(externalToNodeId)){
						toNode = new Node(externalToNodeId, latitudeTo, longitudeTo);
						graph.addNode(toNode);
						toNodeId = (long)toNode.getId();
						//hashExternalIdToId.put(externalToNodeId, toNodeId);
						idToNodePosition.put(externalToNodeId, graph.getNodes().size()-1);
					} 
					else {
						toNode = graph.getNode(idToNodePosition.get(externalToNodeId));
					}
					
					if(externalFromNodeId.equals(externalToNodeId)) {
						//System.out.println("Edge not created, because fromNodeId:"+ fromNodeId + "== toNodeId:" +toNodeId);
						continue;
					}
					
					
					//Direction (To Do)
					int direction = 9999;
					try {
						direction = getDirection(edgeIterator.getFlags());
					} catch (Exception e) {
						countInvalidDirection++;
					}
					if (direction != 9999) {
						Edge edge;
				
						if(direction == 0) {          // Bidirectional	
							//Edge invEdge = new Edge(toNode, fromNode, distance, label, true);
							//graph.addEdge(invEdge);
							edge = new Edge(fromNode, toNode, distance, label, false);
							graph.addEdge(edge);
							fromNode.addEdgeOut(edge);
							toNode.addEdgeIn(edge);
							
							edge = new Edge(toNode, fromNode, distance, label, false);
							graph.addEdge(edge);
							fromNode.addEdgeIn(edge);
							toNode.addEdgeOut(edge);
							countBidirectional++;
							
						} else if(direction == 1) {   // One direction: base -> adj
							edge = new Edge(fromNode, toNode, distance, label, false);
							graph.addEdge(edge);
							fromNode.addEdgeOut(edge);
							toNode.addEdgeIn(edge);
							countOneWay++;
						} else if(direction == -1) {  // One direction: adj -> base
							edge = new Edge(toNode, fromNode, distance, label, false);
							graph.addEdge(edge);
							fromNode.addEdgeIn(edge);
							toNode.addEdgeOut(edge);
							countOneWayInverse++;
						}
					}
					else {
						//System.out.println("Edge not created. Invalid direction: " + direction);
					}
					
//					if(fromNode != null){
//						graph.addNode(fromNode);
//						fromNodeId = (long)fromNode.getId();
//						hashExternalIdToId.put(externalFromNodeId, fromNodeId);
//					}
//					if(toNode != null){
//						graph.addNode(toNode);
//						toNodeId = (long)toNode.getId();
//						hashExternalIdToId.put(externalToNodeId, toNodeId);
//					}
					
				}
				//Results
				
				System.out.println("----------Dados do Grafo de Berlin gerado----------");
				System.out.println("Number of Nodes: " + graph.getNumberOfNodes());
				System.out.println("Number of Edges: " + graph.getNumberOfEdges());
				System.out.println("Count: " + count);
				System.out.println("Number of invalid direction in original edges: " + countInvalidDirection);
				System.out.println("Number of Bidirectional edges: " + countBidirectional);
				System.out.println("Number of OneWay edges: " + countOneWay);
				System.out.println("Number of OneWayInverse edges: " + countOneWayInverse);

				double finalTime = System.currentTimeMillis();
				double total = finalTime - initialTime;
				System.out.println("Initial date: " + initialDate);
				System.out.println("Final date: " + new Date());
				System.out.println("Total time: " + total);
				System.out.println(MemMon.memoryInfo());
				
//				try{
//					ObjectOutputStream out = new ObjectOutputStream(
//							new FileOutputStream("BerlinHashMapGraph"));
//					out.writeObject(graph);
//					out.close();
//				}catch(Exception e){
//					e.printStackTrace();
//				}
				System.out.println("Grafo HashMap de Berlin gerado");
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return graph;
		}
	
	//Monaco
	public Graph generateMonaco(String path){
		
		Date date = new Date();
		String initialDate = date.toString();
		double initialTime = System.currentTimeMillis();
		
		Graph graph = null;
		//checking if the graph already exists
		try{
			ObjectInputStream in = new ObjectInputStream(
					new FileInputStream("MonacoHashMapGraph"));
			graph = (Graph) in.readObject();
			in.close();
			
		}
		catch(IOException io){
			//graph doesn't exists
			System.out.println("Monaco graph doesn't exist yet.");
			graph = new Graph();

			//Linux
			//GraphHopper gh = OSMToGraphHopperReader.createGraph("/home/lucasvasconcelos/Downloads/berlin-latest.osm.pbf", "/home/lucasvasconcelos/Berlin", false, false);

			//Linux (Bremen)
			//GraphHopper gh = OSMToGraphHopperReader.createGraph("/home/lucasvasconcelos/Downloads/bremen-latest.osm.pbf", "/home/lucasvasconcelos/Bremen", false, false);
			
			//Windows
			//GraphHopper gh = OSMToGraphHopperReader.createGraph("/users/vasco/Downloads/berlin-latest.osm.pbf", "/users/vasco/Downloads/Graphast-Graph-Test/Berlin", false, false);
			
			//Mac Peres
			GraphHopper gh = OSMToGraphHopperReader.createGraph(path+"monaco-150112.osm.pbf", "Monaco", false, false);
			
			GraphStorage gs = gh.getGraph();
			EdgeIterator edgeIterator = gs.getAllEdges();
			
			//To know which node has already check
			//Int2LongOpenHashMap hashExternalIdToId = new Int2LongOpenHashMap();
			HashMap<Long, Integer> idToNodePosition = new HashMap<Long, Integer>();
			
			//Statistics
			int count = 0;
			int countInvalidDirection = 0;
			int countBidirectional= 0;
			int countOneWay = 0;
			int countOneWayInverse = 0;
			
			while(edgeIterator.next()) {
				count++;
				
				Long externalFromNodeId = new Long(edgeIterator.getBaseNode());
				Long externalToNodeId = new Long(edgeIterator.getAdjNode());
				
				int distance = (int)NumberUtils.round(edgeIterator.getDistance() * 1000, 0); // Convert distance from meters to millimeters
				
				String label = edgeIterator.getName();
				
				double latitudeFrom = latLongToDouble(latLongToInt(gs.getNodeAccess().getLatitude(externalFromNodeId.intValue())));
				double longitudeFrom = latLongToDouble(latLongToInt(gs.getNodeAccess().getLongitude(externalFromNodeId.intValue())));	

				double latitudeTo = latLongToDouble(latLongToInt(gs.getNodeAccess().getLatitude(externalToNodeId.intValue())));
				double longitudeTo = latLongToDouble(latLongToInt(gs.getNodeAccess().getLongitude(externalToNodeId.intValue())));
				
				Node fromNode = null,toNode = null;
				
				long fromNodeId, toNodeId;
				
				//if(!hashExternalIdToId.containsKey(externalFromNodeId)){
				if(!idToNodePosition.containsKey(externalFromNodeId)){

					fromNode = new Node(externalFromNodeId, latitudeFrom, longitudeFrom);
					graph.addNode(fromNode);
					fromNodeId = (long)fromNode.getId();
					//hashExternalIdToId.put(externalFromNodeId, fromNodeId);
					idToNodePosition.put(externalFromNodeId, graph.getNodes().size()-1);
				} 
				else {
					fromNode = graph.getNode(idToNodePosition.get(externalFromNodeId));
				}

				if(!idToNodePosition.containsKey(externalToNodeId)){
					toNode = new Node(externalToNodeId, latitudeTo, longitudeTo);
					graph.addNode(toNode);
					toNodeId = (long)toNode.getId();
					//hashExternalIdToId.put(externalToNodeId, toNodeId);
					idToNodePosition.put(externalToNodeId, graph.getNodes().size()-1);
				} 
				else {
					toNode = graph.getNode(idToNodePosition.get(externalToNodeId));
				}
				
				if(externalFromNodeId.equals(externalToNodeId)) {
					//System.out.println("Edge not created, because fromNodeId:"+ fromNodeId + "== toNodeId:" +toNodeId);
					continue;
				}
				
				
				//Direction (To Do)
				int direction = 9999;
				try {
					direction = getDirection(edgeIterator.getFlags());
				} catch (Exception e) {
					countInvalidDirection++;
				}
				if (direction != 9999) {
					Edge edge;
			
					if(direction == 0) {          // Bidirectional	
						//Edge invEdge = new Edge(toNode, fromNode, distance, label, true);
						//graph.addEdge(invEdge);
						edge = new Edge(fromNode, toNode, distance, label, false);
						graph.addEdge(edge);
						fromNode.addEdgeOut(edge);
						toNode.addEdgeIn(edge);
						
						edge = new Edge(toNode, fromNode, distance, label, false);
						graph.addEdge(edge);
						fromNode.addEdgeIn(edge);
						toNode.addEdgeOut(edge);
						countBidirectional++;
						
					} else if(direction == 1) {   // One direction: base -> adj
						edge = new Edge(fromNode, toNode, distance, label, false);
						graph.addEdge(edge);
						fromNode.addEdgeOut(edge);
						toNode.addEdgeIn(edge);
						countOneWay++;
					} else if(direction == -1) {  // One direction: adj -> base
						edge = new Edge(toNode, fromNode, distance, label, false);
						graph.addEdge(edge);
						fromNode.addEdgeIn(edge);
						toNode.addEdgeOut(edge);
						countOneWayInverse++;
					}
				}
				else {
					//System.out.println("Edge not created. Invalid direction: " + direction);
				}
				
//				if(fromNode != null){
//					graph.addNode(fromNode);
//					fromNodeId = (long)fromNode.getId();
//					hashExternalIdToId.put(externalFromNodeId, fromNodeId);
//				}
//				if(toNode != null){
//					graph.addNode(toNode);
//					toNodeId = (long)toNode.getId();
//					hashExternalIdToId.put(externalToNodeId, toNodeId);
//				}
				
			}
			//Results
			
			System.out.println("----------Dados do Grafo de Monaco gerado----------");
			System.out.println("Number of Nodes: " + graph.getNumberOfNodes());
			System.out.println("Number of Edges: " + graph.getNumberOfEdges());
			System.out.println("Count: " + count);
			System.out.println("Number of invalid direction in original edges: " + countInvalidDirection);
			System.out.println("Number of Bidirectional edges: " + countBidirectional);
			System.out.println("Number of OneWay edges: " + countOneWay);
			System.out.println("Number of OneWayInverse edges: " + countOneWayInverse);

			double finalTime = System.currentTimeMillis();
			double total = finalTime - initialTime;
			System.out.println("Initial date: " + initialDate);
			System.out.println("Final date: " + new Date());
			System.out.println("Total time: " + total);
			System.out.println(MemMon.memoryInfo());
			
//			try{
//				ObjectOutputStream out = new ObjectOutputStream(
//						new FileOutputStream("BerlinHashMapGraph"));
//				out.writeObject(graph);
//				out.close();
//			}catch(Exception e){
//				e.printStackTrace();
//			}
			System.out.println("Grafo HashMap de Monaco gerado");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return graph;
	}

	
	//Bremen
	public Graph generateBremen(){
				
				Date date = new Date();
				String initialDate = date.toString();
				double initialTime = System.currentTimeMillis();
				
				Graph graph = new Graph();
				
				//Linux (Bremen)
				GraphHopper gh = OSMToGraphHopperReader.createGraph("/home/lucasvasconcelos/Downloads/bremen-latest.osm.pbf", "/home/lucasvasconcelos/Bremen", false, false);
				
				//Windows
				//GraphHopper gh = OSMToGraphHopperReader.createGraph("/users/vasco/Downloads/bremen-latest.osm.pbf", "/users/vasco/Downloads/Graphast-Graph-Test/Bremen", false, false);
				
				
				GraphStorage gs = gh.getGraph();
				EdgeIterator edgeIterator = gs.getAllEdges();
				
				//To know which node has already check
				Int2LongOpenHashMap hashExternalIdToId = new Int2LongOpenHashMap();
				
				//Statistics
				int count = 0;
				int countInvalidDirection = 0;
				int countBidirectional= 0;
				int countOneWay = 0;
				int countOneWayInverse = 0;
				
				while(edgeIterator.next()) {
					count++;
					
					int externalFromNodeId = edgeIterator.getBaseNode();
					int externalToNodeId = edgeIterator.getAdjNode();
					
					int distance = (int)NumberUtils.round(edgeIterator.getDistance() * 1000, 0); // Convert distance from meters to millimeters
					
					String label = edgeIterator.getName();
					
					double latitudeFrom = latLongToDouble(latLongToInt(gs.getNodeAccess().getLatitude(externalFromNodeId)));
					double longitudeFrom = latLongToDouble(latLongToInt(gs.getNodeAccess().getLongitude(externalFromNodeId)));	

					double latitudeTo = latLongToDouble(latLongToInt(gs.getNodeAccess().getLatitude(externalToNodeId)));
					double longitudeTo = latLongToDouble(latLongToInt(gs.getNodeAccess().getLongitude(externalToNodeId)));
					
					Node fromNode = null,toNode = null;
					
					long fromNodeId, toNodeId;
					
					if(!hashExternalIdToId.containsKey(externalFromNodeId)){

						fromNode = new Node(externalFromNodeId, latitudeFrom, longitudeFrom);
						graph.addNode(fromNode);
						fromNodeId = (long)fromNode.getId();
						hashExternalIdToId.put(externalFromNodeId, fromNodeId);
					} 
					else {
						fromNodeId = hashExternalIdToId.get(externalFromNodeId);
						
						//Nodes search for the complete structure (test used to maintain the structure) 
						for(int i=0; i< graph.getNodes().size() ; i++){
							if(graph.getNodes().get(i).getId() == fromNodeId){
								fromNode = graph.getNodes().get(i);
							}
						}
					}

					if(!hashExternalIdToId.containsKey(externalToNodeId)){
						toNode = new Node(externalToNodeId, latitudeTo, longitudeTo);
						graph.addNode(toNode);
						toNodeId = (long)toNode.getId();
						hashExternalIdToId.put(externalToNodeId, toNodeId);
					} 
					else {
						toNodeId = hashExternalIdToId.get(externalToNodeId);
						
						//Nodes search for the complete structure (test used to maintain the structure)
						for(int i=0; i< graph.getNodes().size() ; i++){
							if(graph.getNodes().get(i).getId() == toNodeId){
								toNode = graph.getNodes().get(i);
							}
						}
					}
					
					if(fromNodeId == toNodeId) {
						//System.out.println("Edge not created, because fromNodeId:"+ fromNodeId + "== toNodeId:" +toNodeId);
						continue;
					}
					
					
					//Direction (To Do)
					int direction = 9999;
					try {
						direction = getDirection(edgeIterator.getFlags());
					} catch (Exception e) {
						countInvalidDirection++;
					}
					
					if(direction == 0) {          // Bidirectional
						Edge edge = new Edge(fromNode, toNode, distance, label, true);
						graph.addEdge(edge);
						fromNode.addEdgeOut(edge);
						toNode.addEdgeIn(edge);
						fromNode.addEdgeIn(edge);
						toNode.addEdgeOut(edge);
						countBidirectional++;
						
					} else if(direction == 1) {   // One direction: base -> adj
						Edge edge = new Edge(fromNode, toNode, distance, label, false);
						graph.addEdge(edge);
						fromNode.addEdgeOut(edge);
						toNode.addEdgeIn(edge);
						countOneWay++;
					} else if(direction == -1) {  // One direction: adj -> base
						Edge edge = new Edge(toNode, fromNode, distance, label, false);
						graph.addEdge(edge);
						fromNode.addEdgeIn(edge);
						toNode.addEdgeOut(edge);
						countOneWayInverse++;
					} else {
						//System.out.println("Edge not created. Invalid direction: " + direction);
					}
					
					if(fromNode != null){
						graph.addNode(fromNode);
						fromNodeId = (long)fromNode.getId();
						hashExternalIdToId.put(externalFromNodeId, fromNodeId);
					}
					if(toNode != null){
						graph.addNode(toNode);
						toNodeId = (long)toNode.getId();
						hashExternalIdToId.put(externalToNodeId, toNodeId);
					}
					
				}
				//Results
				
				System.out.println("----------Dados do Grafo de Berlin gerado----------");
				System.out.println("Number of Nodes: " + graph.getNumberOfNodes());
				System.out.println("Number of Edges: " + graph.getNumberOfEdges());
				System.out.println("Count: " + count);
				System.out.println("Number of invalid direction in original edges: " + countInvalidDirection);
				System.out.println("Number of Bidirectional edges: " + countBidirectional);
				System.out.println("Number of OneWay edges: " + countOneWay);
				System.out.println("Number of OneWayInverse edges: " + countOneWayInverse);

				double finalTime = System.currentTimeMillis();
				double total = finalTime - initialTime;
				System.out.println("Initial date: " + initialDate);
				System.out.println("Final date: " + new Date());
				System.out.println("Total time: " + total);


	/*			for (long node = 0; node < graph.getNumberOfNodes(); node++) {
					Point p = Geometries.point(graph.getNode(node).getLatitude(), graph.getNode(node).getLongitude());
					graph.setRTree(graph.getRTree().add(node, p));
				}*/
				
				return graph;
			
	}

	//Berlin or Bremen
	public Graph generate(String name){
				
				Date date = new Date();
				String initialDate = date.toString();
				double initialTime = System.currentTimeMillis();
				
				Graph graph = new Graph();
				
				//Linux
				//GraphHopper gh = OSMToGraphHopperReader.createGraph("/home/lucasvasconcelos/Downloads/"+name.toLowerCase()+"-latest.osm.pbf", "/home/lucasvasconcelos/"+name, false, false);
	
				//Windows
				GraphHopper gh = OSMToGraphHopperReader.createGraph("/users/vasco/Downloads/"+name.toLowerCase()+"-latest.osm.pbf", "/users/vasco/Downloads/Graphast-Graph-Test/"+name, false, false);
				
				
				GraphStorage gs = gh.getGraph();
				EdgeIterator edgeIterator = gs.getAllEdges();
				
				//To know which node has already check
				Int2LongOpenHashMap hashExternalIdToId = new Int2LongOpenHashMap();
				
				//Statistics
				int count = 0;
				int countInvalidDirection = 0;
				int countBidirectional= 0;
				int countOneWay = 0;
				int countOneWayInverse = 0;
				
				while(edgeIterator.next()) {
					count++;
					
					int externalFromNodeId = edgeIterator.getBaseNode();
					int externalToNodeId = edgeIterator.getAdjNode();
					
					int distance = (int)NumberUtils.round(edgeIterator.getDistance() * 1000, 0); // Convert distance from meters to millimeters
					
					String label = edgeIterator.getName();
					
					double latitudeFrom = latLongToDouble(latLongToInt(gs.getNodeAccess().getLatitude(externalFromNodeId)));
					double longitudeFrom = latLongToDouble(latLongToInt(gs.getNodeAccess().getLongitude(externalFromNodeId)));	

					double latitudeTo = latLongToDouble(latLongToInt(gs.getNodeAccess().getLatitude(externalToNodeId)));
					double longitudeTo = latLongToDouble(latLongToInt(gs.getNodeAccess().getLongitude(externalToNodeId)));
					
					Node fromNode = null,toNode = null;
					
					long fromNodeId, toNodeId;
					
					if(!hashExternalIdToId.containsKey(externalFromNodeId)){

						fromNode = new Node(externalFromNodeId, latitudeFrom, longitudeFrom);
						graph.addNode(fromNode);
						fromNodeId = (long)fromNode.getId();
						hashExternalIdToId.put(externalFromNodeId, fromNodeId);
					} 
					else {
						fromNodeId = hashExternalIdToId.get(externalFromNodeId);
						
						//Nodes search for the complete structure (test used to maintain the structure) 
						for(int i=0; i< graph.getNodes().size() ; i++){
							if(graph.getNodes().get(i).getId() == fromNodeId){
								fromNode = graph.getNodes().get(i);
							}
						}
					}

					if(!hashExternalIdToId.containsKey(externalToNodeId)){
						toNode = new Node(externalToNodeId, latitudeTo, longitudeTo);
						graph.addNode(toNode);
						toNodeId = (long)toNode.getId();
						hashExternalIdToId.put(externalToNodeId, toNodeId);
					} 
					else {
						toNodeId = hashExternalIdToId.get(externalToNodeId);
						
						//Nodes search for the complete structure (test used to maintain the structure)
						for(int i=0; i< graph.getNodes().size() ; i++){
							if(graph.getNodes().get(i).getId() == toNodeId){
								toNode = graph.getNodes().get(i);
							}
						}
					}
					
					if(fromNodeId == toNodeId) {
						System.out.println("Edge not created, because fromNodeId:"+ fromNodeId + "== toNodeId:" +toNodeId);
						continue;
					}
					
					
					//Direction (To Do)
					int direction = 9999;
					try {
						direction = getDirection(edgeIterator.getFlags());
					} catch (Exception e) {
						countInvalidDirection++;
					}
					
					if(direction == 0) {          // Bidirectional
						Edge edge = new Edge(fromNode, toNode, distance, label, true);
						graph.addEdge(edge);
						fromNode.addEdgeOut(edge);
						toNode.addEdgeIn(edge);
						fromNode.addEdgeIn(edge);
						toNode.addEdgeOut(edge);
						countBidirectional++;
						
					} else if(direction == 1) {   // One direction: base -> adj
						Edge edge = new Edge(fromNode, toNode, distance, label, false);
						graph.addEdge(edge);
						fromNode.addEdgeOut(edge);
						toNode.addEdgeIn(edge);
						countOneWay++;
					} else if(direction == -1) {  // One direction: adj -> base
						Edge edge = new Edge(toNode, fromNode, distance, label, false);
						graph.addEdge(edge);
						fromNode.addEdgeIn(edge);
						toNode.addEdgeOut(edge);
						countOneWayInverse++;
					} else {
						System.out.println("Edge not created. Invalid direction: " + direction);
					}
					
					if(fromNode != null){
						graph.addNode(fromNode);
						fromNodeId = (long)fromNode.getId();
						hashExternalIdToId.put(externalFromNodeId, fromNodeId);
					}
					if(toNode != null){
						graph.addNode(toNode);
						toNodeId = (long)toNode.getId();
						hashExternalIdToId.put(externalToNodeId, toNodeId);
					}
					
				}
				//Results
				
				System.out.println("----------Dados do Grafo de "+name+" gerado----------");
				System.out.println("Number of Nodes: " + graph.getNumberOfNodes());
				System.out.println("Number of Edges: " + graph.getNumberOfEdges());
				System.out.println("Count: " + count);
				System.out.println("Number of invalid direction in original edges: " + countInvalidDirection);
				System.out.println("Number of Bidirectional edges: " + countBidirectional);
				System.out.println("Number of OneWay edges: " + countOneWay);
				System.out.println("Number of OneWayInverse edges: " + countOneWayInverse);

				double finalTime = System.currentTimeMillis();
				double total = finalTime - initialTime;
				System.out.println("Initial date: " + initialDate);
				System.out.println("Final date: " + new Date());
				System.out.println("Total time: " + total);


	/*			for (long node = 0; node < graph.getNumberOfNodes(); node++) {
					Point p = Geometries.point(graph.getNode(node).getLatitude(), graph.getNode(node).getLongitude());
					graph.setRTree(graph.getRTree().add(node, p));
				}*/
				
				return graph;
			}
	
	
	private int getDirection(long flags) {
		
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
	
	//Methods extract from Graphast.util.GeoUtils
	public static int latLongToInt(double number) {
		return (int) NumberUtils.convert(number, LAT_LONG_CONVERTION_FACTOR);
	}
	
	public static double latLongToDouble(int number) {
		return number / (double) LAT_LONG_CONVERTION_FACTOR;
	}
	
	
}
