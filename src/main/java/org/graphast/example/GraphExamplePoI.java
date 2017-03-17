package org.graphast.example;

import org.graphast.config.Configuration;
import org.graphast.model.Edge;
import org.graphast.model.EdgeImpl;
import org.graphast.model.GraphBounds;
import org.graphast.model.GraphImpl;
import org.graphast.model.Node;
import org.graphast.model.NodeImpl;

public class GraphExamplePoI {
	public GraphBounds generateExample() {
		
		GraphBounds graph = new GraphImpl(Configuration.USER_HOME + "/graphast/test/examplePoI");
		
		Node node;
		Edge edge;

		//NODES

		node = new NodeImpl(0l, 0.0d, 1.0d);
		graph.addNode(node);

		node = new NodeImpl(1l, 0.0d, 10.0d);
		int[] costs = new int[]{30300000, 1500000, 1500000, 1500000, 900000, 900000, 59100000, 30300000};
		node.setCategory(1);
		node.setLabel("Bradesco");
		node.setCosts(costs);
		graph.addNode(node);

		node = new NodeImpl(2l, 0.0d, 20.0d);
		graph.addNode(node);

		node = new NodeImpl(3l, 0.0d, 30.0d);
		graph.addNode(node);

		node = new NodeImpl(4l, 0.0d, 40.0d);
		costs = new int[]{29700000, 900000, 900000, 900000, 1500000, 1500000, 58500000, 17700000};
		node.setCategory(2);
		node.setLabel("Padaria Costa Mendes");
		node.setCosts(costs);
		graph.addNode(node);

		node = new NodeImpl(5l, 10.0d, 0.0d);
		graph.addNode(node);

		node = new NodeImpl(6l, 10.0d, 10.0d);
		graph.addNode(node);

		node = new NodeImpl(7l, 10.0d, 20.0d);
		graph.addNode(node);

		node = new NodeImpl(8l, 10.0d, 30.0d);
		graph.addNode(node);

		node = new NodeImpl(9l, 10.0d, 40.0d);
		costs = new int[]{30300000, 1500000, 1500000, 1500000, 900000, 900000, 59100000, 30300000};
		node.setCategory(3);
		node.setLabel("Escola Vila");
		node.setCosts(costs);
		graph.addNode(node);

		//TODO Create a constructor for edge without the distance

		//EDGES

		edge = new EdgeImpl(1l, 0l, 1l, 10);
		costs = new int[]{900000, 900000, 900000, 900000, 900000, 900000, 900000, 900000, 900000, 1200000,
				1200000, 1200000, 900000, 900000, 900000, 1500000, 1500000, 1500000, 1500000, 900000, 900000,
				900000,	900000, 900000};
		edge.setCosts(costs);
		graph.addEdge(edge);

		edge = new EdgeImpl(2l, 1l, 2l, 20);
		costs = new int[]{900000, 900000, 900000, 900000, 900000, 900000, 900000, 900000, 900000,
				900000, 900000, 900000, 900000, 900000, 900000, 900000, 900000, 900000, 
				900000, 900000, 900000, 900000, 900000, 900000};
		edge.setCosts(costs);
		graph.addEdge(edge);

		edge = new EdgeImpl(3l, 1l, 7l, 30);
		costs = new int[]{720000, 720000, 720000, 720000, 720000, 720000, 720000, 720000, 720000, 1320000,
				1320000, 1320000, 720000, 720000, 720000, 1800000, 1800000, 1800000, 1800000, 720000, 720000,
				720000, 720000,	720000};
		edge.setCosts(costs);
		graph.addEdge(edge);

		edge = new EdgeImpl(4l, 2l, 3l, 40);
		costs = new int[]{600000, 600000, 600000, 600000, 600000, 600000, 600000, 600000, 600000, 600000,
				600000,	600000,	600000,	600000,	600000,	600000,	600000,	600000,	600000,	600000,	600000,
				600000,	600000,	600000};
		edge.setCosts(costs);
		graph.addEdge(edge);

		edge = new EdgeImpl(5l, 3l, 4l, 50);
		costs = new int[]{720000, 720000, 720000, 720000, 720000, 720000, 720000, 720000, 720000, 1320000,
				1320000, 1320000, 720000, 720000, 720000, 1800000, 1800000, 1800000, 1800000, 720000,
				720000, 720000, 720000, 720000};
		edge.setCosts(costs);
		graph.addEdge(edge);

		edge = new EdgeImpl(6l, 4l, 8l, 60);
		costs = new int[]{720000, 720000, 720000, 720000, 720000, 720000, 720000, 720000, 720000, 720000,
				720000,	720000,	720000,	720000,	720000,	720000,	720000,	720000,	720000,	720000,	720000,
				720000,	720000,	720000};
		edge.setCosts(costs);
		graph.addEdge(edge);

		edge = new EdgeImpl(7l, 4l, 9l, 70);
		costs = new int[]{720000, 720000, 600000, 600000, 600000, 600000, 600000, 600000, 600000, 1080000,
				1080000, 1080000, 600000, 600000, 600000, 1500000, 1500000, 1500000, 1500000, 600000, 600000,
				600000,	600000,	600000};
		edge.setCosts(costs);
		graph.addEdge(edge);

		edge = new EdgeImpl(8l, 5l, 0l, 80);
		costs = new int[]{720000, 720000, 600000, 600000, 600000, 600000, 600000, 600000, 600000,
				1080000, 1080000, 1080000, 600000, 600000, 600000, 1080000,	1080000, 1080000,
				1080000, 600000, 600000, 600000, 600000, 600000};
		edge.setCosts(costs);
		graph.addEdge(edge);

		edge = new EdgeImpl(9l, 6l, 5l, 90);
		costs = new int[]{900000, 900000, 900000, 900000, 900000, 900000, 900000, 900000, 900000,
				1200000, 1200000, 1200000, 900000, 900000, 900000, 1500000, 1500000, 1500000, 1500000,
				900000, 900000, 900000, 900000, 900000};
		edge.setCosts(costs);
		graph.addEdge(edge);

		edge = new EdgeImpl(10l, 7l, 2l, 100);
		costs = new int[]{900000, 900000, 900000, 900000, 900000, 900000, 900000, 900000, 900000,
				900000, 900000, 900000, 900000, 900000, 900000, 900000, 900000, 900000, 
				900000, 900000, 900000, 900000, 900000, 900000};
		edge.setCosts(costs);
		graph.addEdge(edge);

		edge = new EdgeImpl(11l, 7l, 6l, 110);
		costs = new int[]{720000, 720000, 600000, 600000, 600000, 600000, 600000, 600000, 600000, 1080000,
				1080000, 1080000, 600000, 600000, 600000, 1080000, 1080000, 1080000, 1080000, 600000, 600000,
				600000, 600000,	600000};
		edge.setCosts(costs);
		graph.addEdge(edge);

		edge = new EdgeImpl(12l, 8l, 7l, 120);
		costs = new int[]{720000, 720000, 720000, 720000, 720000, 720000, 720000, 720000, 720000, 1320000,
				1320000, 1320000, 720000, 720000, 720000, 1800000, 1800000, 1800000, 1800000, 720000, 720000,
				720000, 720000,	720000};
		edge.setCosts(costs);
		graph.addEdge(edge);

		edge = new EdgeImpl(13l, 9l, 8l, 130);
		costs = new int[]{900000, 900000, 900000, 900000, 900000, 900000, 900000, 900000, 900000,
				900000, 900000, 900000, 900000, 900000, 900000, 900000, 900000, 900000, 
				900000, 900000, 900000, 900000, 900000, 900000};
		edge.setCosts(costs);
		graph.addEdge(edge);

		graph.createBounds();

		return graph;
		
	}
	
	public static void main(String[] args) throws Exception {
		GraphBounds graph = new GraphExamplePoI().generateExample();
		graph.save(); // Save the graph for future use.
		System.out.println("finished");
	}

}
