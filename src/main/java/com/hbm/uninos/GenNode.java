package com.hbm.uninos;

import com.hbm.uninos.UniNodespace.UniNodeWorld;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

public class GenNode<T> {
	
	public long id;
	public BlockPos[] positions;
	public DirPos[] connections;
	public INodeNet<T> net;
	public boolean expired = false;
	public boolean recentlyChanged = true;
	
	public GenNode(BlockPos... positions) {
		this.id = UniNodeWorld.nextId++;
		this.positions = positions;
	}
	
	public GenNode<T> setConnections(DirPos... connections) {
		this.connections = connections;
		return this;
	}
	
	public GenNode<T> addConnection(DirPos connection) {
		DirPos[] newCons = new DirPos[this.connections.length + 1];
		for(int i = 0; i < this.connections.length; i++) newCons[i] = this.connections[i];
		newCons[newCons.length - 1] = connection;
		this.connections = newCons;
		return this;
	}
	
	public boolean hasValidNet() {
		return this.net != null && this.net.isValid();
	}
	
	public void setNet(INodeNet<T> net) {
		this.net = net;
		this.recentlyChanged = true;
	}
}

/*
 * 
 * ok so here's the deal: attempt #1 SUCKED.
 * making a central nodespaces hashmap that holds one instance of each possible nodespace sounds like a great and simple solution
 * until you realize that every single fucking fluid under the sun needs to be its own nodespace. which means the update function
 * has to iterate over every world instance, and for every world instance there's 150 or so nodespaces for fluids alone. not good.
 * 
 */
