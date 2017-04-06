package org.graphast.example;

import org.graphast.exception.GraphastException;
import org.graphast.model.EdgeImpl;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.ints.IntBigArrayBigList;

public class EdgeImplLazy extends EdgeImpl {

	private long pos;
	private IntBigArrayBigList edges;
	
	private long externalId = -1;
	private long fromId = -1;
	private long toId = -1;
	private long fromNodeNextEdge = -1;
	private long toNodeNextEdge = -1;
	private int distance = -1;
	private long costsIndex = -1;
	private long geometryIndex = -1;
	private long labelIndex = -1;

	
	public EdgeImplLazy(long position, IntBigArrayBigList edges) {
		super(0,0,0);
		this.pos = position;
		this.edges = edges;
		getFromNode();
		getToNode();
		getDistance();
	}
	
	@Override
	public void validate(){
		
		if(distance < -1){
			throw new GraphastException("Invalid edge: distance(mm)=" + distance);
		}
		
		if(fromId == -1 && toId == -1 && fromNodeNextEdge == -1 && toNodeNextEdge == -1) {
			throw new GraphastException("Invalid edge");
		}

	}

	public long getExternalId() {
		if(externalId==-1)
			externalId = BigArrays.index(edges.getInt(pos),
					edges.getInt(pos+1));
		return externalId;
	}

	public long getFromNode() {
		if(fromId==-1)
			fromId = BigArrays.index(edges.getInt(pos+2),
					edges.getInt(pos+3));
		return fromId;
	}

	public long getToNode() {
		if(toId==-1)
			toId = BigArrays.index(edges.getInt(pos+4),
					edges.getInt(pos+5));
		return toId;
	}

	public long getFromNodeNextEdge() {
		if(fromNodeNextEdge==-1)
			fromNodeNextEdge = BigArrays.index(edges.getInt(pos+6),
					edges.getInt(pos+7));
		return fromNodeNextEdge;
	}

	public long getToNodeNextEdge() {
		if(toNodeNextEdge==-1)
			toNodeNextEdge = BigArrays.index(edges.getInt(pos+8),
					edges.getInt(pos+9));
		return toNodeNextEdge;
	}

	public int getDistance() {
		if(distance==-1)
			distance = edges.getInt(pos+10);
		return distance;
	}

	public long getCostsIndex() {
		if(costsIndex==-1)
			costsIndex = BigArrays.index(edges.getInt(pos+11),
					edges.getInt(pos+12));
		return costsIndex;
	}

	public long getGeometryIndex() {
		if(geometryIndex==-1)
			geometryIndex = BigArrays.index(edges.getInt(pos+13),
					edges.getInt(pos+14));
		return geometryIndex;
	}

	public long getLabelIndex() {
		if(labelIndex==-1)
			labelIndex = BigArrays.index(edges.getInt(pos+15),
					edges.getInt(pos+16));
		return labelIndex;
	}

}
