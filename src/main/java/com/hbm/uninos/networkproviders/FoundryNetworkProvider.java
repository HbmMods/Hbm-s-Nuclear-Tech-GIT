package com.hbm.uninos.networkproviders;

import com.hbm.uninos.GenNode;
import com.hbm.uninos.INetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;

public class FoundryNetworkProvider implements INetworkProvider<FoundryNetwork> {

	public static FoundryNetworkProvider THE_PROVIDER = new FoundryNetworkProvider();

	@Override
	public FoundryNetwork provideNetwork() {
		return new FoundryNetwork();
	}

	@Override
	public GenNode<FoundryNetwork> provideNode(BlockPos... positions) {
		return new GenNode(this, positions);
	}
}
