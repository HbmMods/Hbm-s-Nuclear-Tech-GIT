package com.hbm.uninos.networkproviders;

import com.hbm.uninos.GenNode;
import com.hbm.uninos.INetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;

public class RebarNetworkProvider implements INetworkProvider<RebarNetwork> {
	
	public static RebarNetworkProvider THE_PROVIDER = new RebarNetworkProvider();

	@Override
	public RebarNetwork provideNetwork() {
		return new RebarNetwork();
	}

	@Override
	public GenNode<RebarNetwork> provideNode(BlockPos... positions) {
		return new GenNode(this, positions);
	}
}
