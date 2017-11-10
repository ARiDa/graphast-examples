package org.graphast.example;

import org.graphast.importer.OSMToGraphHopperReader;

import com.graphhopper.GraphHopper;
import com.graphhopper.util.StopWatch;

public class GraphHopperImporterExample {

	public static void main(String[] args) {
		
		String osmFile = args[0]; 
		String graphDir = args[1]; 
		
		StopWatch sw = new StopWatch();
		
		sw.start();
		GraphHopper hopper = OSMToGraphHopperReader.createGraph(osmFile, graphDir, false, false);
		sw.stop();
		
		System.out.println("Execution time(ms):" + sw.getTime());
		System.out.println("Number of nodes generated: " + hopper.getGraph().getNodes());
		
	}

}
