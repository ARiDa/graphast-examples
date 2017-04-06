package org.graphast.example;

import static org.graphast.util.GeoUtils.latLongToDouble;
import static org.graphast.util.GeoUtils.latLongToInt;

import org.graphast.exception.GraphastException;
import org.graphast.model.NodeImpl;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.ints.IntBigArrayBigList;

public class NodeImplLazy extends NodeImpl {
	
	IntBigArrayBigList nodes;

	private long position;
	
	private long externalId = -1;

	private int category = -1;

	private int latitude = -1;

	private int longitude = -1;

	private long firstEdge = -1;

	private long labelIndex = -1;

	private long costsIndex = -1;
	
	public NodeImplLazy(long position, IntBigArrayBigList nodes) {
		this.position = position;
		this.nodes = nodes;
	}
	
	@Override
	public void validate(){

		if(latitude == 0 && longitude == 0 && firstEdge == 0){
			throw new GraphastException("Invalid vertex");
		}

	}
	
	public long getExternalId() {
		if (externalId == -1)
			externalId = BigArrays.index(nodes.getInt(position),
					nodes.getInt(position + 1));
		return externalId;
	}
	
	@Override
	public int getCategory() {
		if (category == -1)
			category = nodes.getInt(position + 2);
		return category;
	}
	
	@Override
	public double getLatitude() {
		if (latitude == -1)
			setLatitude(latLongToDouble(nodes.getInt(position + 3)));
		return latLongToDouble(latitude);
	}
	
	@Override
	public double getLongitude() {
		if (longitude == -1)
			setLongitude(latLongToDouble(nodes.getInt(position + 4)));
		return latLongToDouble(longitude);
	}
	
	public long getFirstEdge() {
		if (firstEdge == -1)
			firstEdge = BigArrays.index(nodes.getInt(position + 5),
					nodes.getInt(position + 6));
		return firstEdge;
	}
	
	public long getLabelIndex() {
		if (labelIndex == -1)
			labelIndex = BigArrays.index(nodes.getInt(position + 7),
					nodes.getInt(position + 8));
		return labelIndex;
	}
	
	@Override
	public long getCostsIndex() {
		if (costsIndex == -1)
			costsIndex = BigArrays.index(nodes.getInt(position + 9),
					nodes.getInt(position + 10));
		return costsIndex;
	}
	
	@Override
	public void setLatitude(double latitude) {
		this.latitude = latLongToInt(latitude);
	}

	@Override
	public void setLongitude(double longitude) {
		this.longitude = latLongToInt(longitude);
	}
	
	public int getFirstEdgeSegment(){
		return BigArrays.segment(firstEdge);
	}

	public int getFirstEdgeOffset(){
		return BigArrays.displacement(firstEdge);
	}
	
}
