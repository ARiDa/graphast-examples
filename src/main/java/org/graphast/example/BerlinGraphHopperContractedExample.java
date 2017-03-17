package org.graphast.example;
import org.hashMapGraph.util.MemMon;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.graphast.config.Configuration;
import org.graphast.importer.OSMToGraphHopperReader;
import org.hashMapGraph.model.Coordinate;
import org.hashMapGraph.model.Graph;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.storage.GraphStorage;
import com.graphhopper.util.StopWatch;

public class BerlinGraphHopperContractedExample {

	public static void main(String[] args) {
		if(args.length!=5){
			System.out.println("Usage: lat1 long1 lat2 long2 file");
		}
		else{
			String graphhopperMonacoDir = Configuration.USER_HOME + "/graphhopper/berlinContracted";
			GraphHopper hopper = OSMToGraphHopperReader.createGraph("../../berlin-latest.osm.pbf", graphhopperMonacoDir, true, false);
	
			GHRequest req = null;
			GHResponse res = null;
			StopWatch sw = new StopWatch();
			
			sw.start();
			req = new GHRequest(Double.parseDouble(args[0]),Double.parseDouble(args[1]),
					Double.parseDouble(args[2]),Double.parseDouble(args[3]))
											.setVehicle("car").setAlgorithm("dijkstra");
			res = hopper.route(req);
			sw.stop();
				
			try{
				FileWriter fw = new FileWriter(args[4]);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw);
			    out.println(sw.getTime());
			    out.println(res.getDistance());
			    out.close();
			} catch (IOException e) {}
			
			//System.out.println("mean execution time(ms):" +sumTimes/numExperiments );
			//System.out.println(MemMon.memoryInfo());
	//		System.out.println("distance:" + res.getDistance());
	//		System.out.println("points:" + res.getPoints().getSize());
	//		System.out.println("time(min):" + res.getMillis() / 1000.0 / 60.0);
	//		System.out.println(res.getInstructions());
		}
	}
	

}
