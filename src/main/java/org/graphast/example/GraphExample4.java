package org.graphast.example;

import java.util.Date;

import org.graphast.config.Configuration;
import org.graphast.model.Edge;
import org.graphast.model.EdgeImpl;
import org.graphast.model.Graph;
import org.graphast.model.GraphImpl;
import org.graphast.model.NodeImpl;
import org.graphast.query.route.shortestpath.ShortestPathService;
import org.graphast.query.route.shortestpath.dijkstra.DijkstraLinearFunction;
import org.graphast.query.route.shortestpath.model.Path;
import org.graphast.util.DateUtils;

public class GraphExample4 {

	public static Graph generateExample() {

		Graph graph = new GraphImpl(Configuration.USER_HOME + "/graphast/example5");

		Edge e;
		NodeImpl v;

		v = new NodeImpl(0l, 1d, 10d, 0);
		graph.addNode(v);

		v = new NodeImpl(1l, 30d, 20d, 2);
		graph.addNode(v);

		v = new NodeImpl(2l, 40d, 20d, 4);
		graph.addNode(v);

		v = new NodeImpl(3l, 50d, 30d, 0);
		graph.addNode(v);

		v = new NodeImpl(4l, 60d, 20d);
		graph.addNode(v);

		
		int[] costs = {1};
		e = new EdgeImpl(0l, 1l, 1, costs, null, "Rua AB");
		graph.addEdge(e);

		costs = new int[]{3};
		e = new EdgeImpl(1l, 2l, 3, costs, null, "Rua BC");
		graph.addEdge(e);

		costs = new int[]{2};
		e = new EdgeImpl(1l, 3l, 2, costs, null, "Rua BD");
		graph.addEdge(e);

		costs = new int[]{1};
		e = new EdgeImpl(3l, 2l, 1, costs, null, "Rua DC");
		graph.addEdge(e);

		costs = new int[]{1};
		e = new EdgeImpl(2l, 3l, 1, costs, null, "Rua CD");
		graph.addEdge(e);

		costs = new int[]{5};
		e = new EdgeImpl(3l, 4l, 5, costs, null, "Rua DE");
		graph.addEdge(e);

		costs = new int[]{1};
		e = new EdgeImpl(2l, 4l, 1, costs, null, "Rua CE");
		graph.addEdge(e);


		return graph;

	}
	
	public static void main(String[] args) throws Exception {
		Graph graph = generateExample();
		graph.save(); // Save the graph for future use.
		ShortestPathService shortestPath = new DijkstraLinearFunction(graph);

		Path path = shortestPath.shortestPath(0,4);
		System.out.println("The Shortest Path distance between 0 and 6 is " + path.getTotalDistance());
		System.out.println("The Shortest Path distance between 0 and 6 is " + path.getPath());
	}

}