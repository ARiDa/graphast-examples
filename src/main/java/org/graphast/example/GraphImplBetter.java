package org.graphast.example;

import java.util.ArrayList;
import java.util.List;

import org.graphast.model.Edge;
import org.graphast.model.GraphImpl;
import org.graphast.model.Node;

import it.unimi.dsi.fastutil.ints.IntBigArrayBigList;
import it.unimi.dsi.fastutil.objects.ObjectBigList;

public class GraphImplBetter extends GraphImpl {
	
	private ObjectBigList<String> nodesLabels;
	private ObjectBigList<String> edgesLabels;
	private IntBigArrayBigList edgesCosts;

	public GraphImplBetter(String directory) {
		super(directory);
	}
	
	public ObjectBigList<String> getNodesLabels() {
		return nodesLabels;
	}
	
	@Override
	public Node getNode(long id) {

		long position = id * Node.NODE_BLOCKSIZE;
		NodeImplLazy node = new NodeImplLazy(position,getNodes());

		node.setId(id);
		long labelIndex = node.getLabelIndex();
		if (labelIndex >= 0) {
			node.setLabel(getNodesLabels().get(labelIndex));
		}

		long costsIndex = node.getCostsIndex();
		if (costsIndex >= 0) {
			node.setCosts(getNodeCostsByCostsIndex(costsIndex));
		}

		node.validate();

		return node;
	}
	
	public List<Edge> getOutEdgesBetter(long nodeId) {

		List<Edge> outEdges = new ArrayList<>();
		NodeImplLazy v = (NodeImplLazy) getNode(nodeId);

		long firstEdgeId = v.getFirstEdge();
		Edge nextEdge = getEdge(firstEdgeId);
		long next = 0;

		while (next != -1) {

			if (nodeId == nextEdge.getFromNode()) {
				outEdges.add(nextEdge);
				next = nextEdge.getFromNodeNextEdge();
			} else if (nodeId == nextEdge.getToNode()) {
				next = nextEdge.getToNodeNextEdge();
			}

			if (next != -1) {
				nextEdge = getEdge(next);
			}
		}
		return outEdges;
	}
	
	public ObjectBigList<String> getEdgesLabels() {
		return edgesLabels;
	}
	
	public int[] getEdgeCostsByCostsIndex(long costsIndex) {

		int size = edgesCosts.getInt(costsIndex);
		int[] c = new int[size];
		int i = 0;
		while (size > 0) {
			costsIndex++;
			c[i] = edgesCosts.getInt(costsIndex);
			size--;
			i++;
		}
		return c;
	}
	
	@Override
	public Edge getEdge(long id) {

		long pos = id * Edge.EDGE_BLOCKSIZE;

		EdgeImplLazy edge = new EdgeImplLazy(pos,getEdges());

		edge.setId(id);

		//edge.validate();
		return edge;

	}
}
