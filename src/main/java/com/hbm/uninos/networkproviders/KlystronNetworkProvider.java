package com.hbm.uninos.networkproviders;

import com.hbm.uninos.GenNode;
import com.hbm.uninos.INetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;

public class KlystronNetworkProvider implements INetworkProvider<KlystronNetwork> {
	
	public static KlystronNetworkProvider THE_PROVIDER = new KlystronNetworkProvider();

	@Override
	public KlystronNetwork provideNetwork() {
		return new KlystronNetwork();
	}

	@Override
	public GenNode<KlystronNetwork> provideNode(BlockPos... positions) {
		return new GenNode(this, positions);
	}
}
