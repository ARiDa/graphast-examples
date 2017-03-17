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

public class GraphExample5 {

	public Graph generateExample() {

		Graph graph = new GraphImpl(Configuration.USER_HOME + "/graphast/example");

		Edge e;
		NodeImpl v;

		v = new NodeImpl(0l, 0d, 10d, 0);
		graph.addNode(v);

		v = new NodeImpl(1l, 10d, 0d, 1);
		graph.addNode(v);

		v = new NodeImpl(2l, 30d, 20d, 2);
		graph.addNode(v);

		v = new NodeImpl(3l, 40d, 20d, 4);
		graph.addNode(v);

		v = new NodeImpl(4l, 50d, 30d, 0);
		graph.addNode(v);

		v = new NodeImpl(5l, 60d, 20d);
		graph.addNode(v);

		v = new NodeImpl(6l, 60d, 0d);
		graph.addNode(v);

		int[] costs = {1};
		e = new EdgeImpl(0l, 1l, 1, costs, null, "Rua A");
		graph.addEdge(e);

		costs = new int[]{5};
		e = new EdgeImpl(0l, 2l, 5, costs, null, "Rua B");
		graph.addEdge(e);

		costs = new int[]{3};
		e = new EdgeImpl(1l, 2l, 3, costs, null, "Rua C");
		graph.addEdge(e);

		costs = new int[]{3};
		e = new EdgeImpl(2l, 3l, 3, costs, null, "Rua D");

		graph.addEdge(e);

		costs = new int[]{3};
		e = new EdgeImpl(3l, 4l, 3, costs, null, "Rua E");
		graph.addEdge(e);

		costs = new int[]{4};
		e = new EdgeImpl(3l, 5l, 4, costs, null, "Rua F");
		graph.addEdge(e);

		costs = new int[]{2};
		e = new EdgeImpl(4l, 5l, 2, costs, null, "Rua G");
		graph.addEdge(e);

		costs = new int[]{1};
		e = new EdgeImpl(5l, 6l, 1, costs, null, "Rua H");
		graph.addEdge(e);

		return graph;

	}
	
	public static void main(String[] args) throws Exception {
		Graph graph = new GraphExample5().generateExample();
		graph.save(); // Save the graph for future use.
		ShortestPathService shortestPath = new DijkstraLinearFunction(graph);
		Path path = shortestPath.shortestPath(0, 6);
		System.out.println("The Shortest Path distance between 0 and 6 is " + path.getTotalDistance());
		System.out.println("The Shortest Path distance between 0 and 6 is " + path.getPath());
	}

}