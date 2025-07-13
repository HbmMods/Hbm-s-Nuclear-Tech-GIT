package com.hbm.uninos;

import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

public class GenNode<N extends NodeNet> {
	
	public BlockPos[] positions;
	public DirPos[] connections;
	public N net;
	public boolean expired = false;
	public boolean recentlyChanged = true;
	/** Used for distinguishing the node type when saving it to UNINOS' node map */
	public INetworkProvider networkProvider;
	
	public GenNode(INetworkProvider<N> provider, BlockPos... positions) {
		this.networkProvider = provider;
		this.positions = positions;
	}
	
	public GenNode<N> setConnections(DirPos... connections) {
		this.connections = connections;
		return this;
	}
	
	public GenNode<N> addConnection(DirPos connection) {
		DirPos[] newCons = new DirPos[this.connections.length + 1];
		for(int i = 0; i < this.connections.length; i++) newCons[i] = this.connections[i];
		newCons[newCons.length - 1] = connection;
		this.connections = newCons;
		return this;
	}
	
	public boolean hasValidNet() {
		return this.net != null && this.net.isValid();
	}
	
	public void setNet(N net) {
		this.net = net;
		this.recentlyChanged = true;
	}
}
