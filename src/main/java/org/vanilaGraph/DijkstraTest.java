package org.vanilaGraph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class DijkstraTest {
	public static void main(String[] args) {
		//Graph g = TableParserUtils.getBeijingGraph();
		Graph g = TableParserUtils.getOSMGraph("../../berlin-latest.osm.pbf");
		
		if (args.length == 5) {
			Coordinate fromCoord = new Coordinate(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
			Coordinate toCoord = new Coordinate(Double.parseDouble(args[2]), Double.parseDouble(args[3]));
			int sourceIndex = g.getNodeIndexByCoord(fromCoord);
			int targetIndex = g.getNodeIndexByCoord(toCoord);
			long init = System.currentTimeMillis();
			g.runDijkstra(sourceIndex, targetIndex);
			long time = System.currentTimeMillis() - init;
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(new File(args[4])));
				writer.write(String.valueOf(time));
				writer.flush();
				writer.close();
			} catch (IOException e) { e.printStackTrace(); }
		}
		
		else {
			
			int sourceIndex = g.getRandomNodeIndex();
			int targetIndex = g.getRandomNodeIndex();
			System.out.println(g.getNode(sourceIndex).getCoord().getLat() + " | " + g.getNode(sourceIndex).getCoord().getLng());
			System.out.println(g.getNode(targetIndex).getCoord().getLat() + " | " + g.getNode(targetIndex).getCoord().getLng());
			System.out.println("Finding path (" + g.getNode(sourceIndex).getId() + " -> " + g.getNode(targetIndex).getId() + ")");
			Long prevTime = System.currentTimeMillis();
			DistanceVector path = g.runDijkstra(sourceIndex, targetIndex);
			System.out.println("Dijkstra took " + (System.currentTimeMillis() - prevTime) + " ms to run!");
			long initialTime = System.currentTimeMillis();
			path.print(sourceIndex, targetIndex);
			for (int i = 0; i < 0; i++)
				path.print(sourceIndex, g.getRandomNodeIndex());
			long endTime = System.currentTimeMillis();
			System.out.println("All requests took " + (endTime - initialTime) + " ms");
		
		}

	}
}
