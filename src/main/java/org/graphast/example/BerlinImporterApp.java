package org.graphast.example;

import org.graphast.config.Configuration;
import org.graphast.importer.OSMImporterImpl;
import org.graphast.model.Graph;

public class BerlinImporterApp {
	public static void main( String[] args ) {
		//String osmFile = BerlinImporterApp.class.getResource("berlin-latest.osm.pbf").getPath();
		String osmFile = "../../berlin-latest.osm.pbf";
		String graphastMonacoDir = Configuration.USER_HOME + "/graphast/berlin";
		String graphhopperMonacoDir = Configuration.USER_HOME + "/graphhopper/berlin";
		
		Graph graph = new OSMImporterImpl(osmFile, graphhopperMonacoDir, graphastMonacoDir).execute();
		System.out.println("Number of nodes:" + graph.getNumberOfNodes());
		System.out.println("Number of edges:" + graph.getNumberOfEdges());
	}
}
