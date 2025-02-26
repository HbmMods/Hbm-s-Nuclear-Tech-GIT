package com.hbm.uninos;

import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

public class GenNode<T extends INetworkProvider> {
	
	public BlockPos[] positions;
	public DirPos[] connections;
	public INodeNet<T> net;
	public boolean expired = false;
	public boolean recentlyChanged = true;
	public T networkProvider;
	
	public GenNode(T provider, BlockPos... positions) {
		this.networkProvider = provider;
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
