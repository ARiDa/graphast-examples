package org.graphast.example;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.graphast.app.AppGraph;
import org.graphast.app.GraphInfo;
import org.graphast.app.GraphService;
import org.graphast.geometry.Point;
import org.graphast.model.Graph;
import org.graphast.model.GraphBounds;
//import org.graphast.model.GraphBoundsImpl;
import org.graphast.query.route.osr.BoundsRoute;
import org.graphast.query.route.osr.OSRSearch;
import org.graphast.query.route.shortestpath.AbstractShortestPathService;
import org.graphast.query.route.shortestpath.dijkstra.DijkstraConstantWeight;
import org.graphast.query.route.shortestpath.dijkstra.DijkstraLinearFunction;
import org.graphast.query.route.shortestpath.model.Path;
import org.graphast.util.DateUtils;
import org.graphast.util.StringUtils;

public class SeattleTest {

	protected static AbstractShortestPathService serviceSeattle;

	private static OSRSearch osr;
	private static GraphBounds graphBoundsPoI, graphBoundsPoIReverse;

	public static void main( String[] args ) throws NumberFormatException, IOException, ParseException {

		osrSeattle();	

	}

	public static void osrSeattle() throws NumberFormatException, IOException {

		GraphService service = new GraphService();
		GraphInfo gi = new GraphInfo();
		gi.setAppName("seattle-osr");
		gi.setNetwork("http://download.bbbike.org/osm/bbbike/Seattle/Seattle.osm.pbf");
		gi.setPoiCategoryFilter(StringUtils.splitIntToList(",","6,46,34,33,29,25,23,22,162,13,105"));
		service.create(gi);
		service.load("seattle-osr");
		System.out.println("Test 1");
		short graphType = 0;

		graphBoundsPoI = AppGraph.getGraph();
		System.out.println("Test 2");
		serviceSeattle = new DijkstraLinearFunction(graphBoundsPoI);
		System.out.println("Test 3");
		osr = new OSRSearch(graphBoundsPoI, graphType);
		System.out.println("Test 4");
		ArrayList<Integer> categories = new ArrayList<Integer>();
		categories.add(23);
		categories.add(25);
		System.out.println("Test 5");
		Date date = DateUtils.parseDate(16, 0, 0);
		System.out.println("Test 6");
		Graph graph = osr.getGraphAdapter();
		System.out.println("Test 7");
		Long source = graph.getNodeId(47.641336,-122.391472);
		Long target = graph.getNodeId(47.550811,-122.297401);
		System.out.println("Test 8");
		Path resultPath = osr.getFullPath(graph.getNode(source), graph.getNode(target), date, categories);
		System.out.println("Test 9");
		int counter = 0;
		System.out.println("sequence,latitude,longitude");
		for(Point points : resultPath.getGeometry()) {
			counter ++;
			System.out.println(counter + "," + points.getLatitude() + "," + points.getLongitude());
		}
	}

}
