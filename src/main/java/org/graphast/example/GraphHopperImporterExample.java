package org.graphast.example;

import org.graphast.importer.OSMToGraphHopperReader;

import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.AllEdgesIterator;
import com.graphhopper.util.StopWatch;

public class GraphHopperImporterExample {

	public static void main(String[] args) {
		
		int edgeCounter = 0;
		
		String osmFile = args[0]; 
		String graphDir = args[1];

		boolean mmap = Boolean.parseBoolean(args[2]);
		
		System.out.println("MMap: " + mmap);
		
		StopWatch sw = new StopWatch();
		
		sw.start();
		GraphHopper hopper = OSMToGraphHopperReader.createGraph(osmFile, graphDir, false, mmap);
		sw.stop();
		
		System.out.println("Execution time(ms):" + sw.getTime());
		System.out.println("Number of nodes generated: " + hopper.getGraph().getNodes());
		
		AllEdgesIterator edgeIterator = hopper.getGraph().getAllEdges();

		while(edgeIterator.next()) {
			edgeCounter++;
		}
		
		System.out.println("Number of edges generated: " + edgeCounter);
		
	}

}
