package com.hbm.uninos.networkproviders;

import com.hbm.uninos.GenNode;
import com.hbm.uninos.INetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;

public class PlasmaNetworkProvider implements INetworkProvider<PlasmaNetwork> {
	
	public static PlasmaNetworkProvider THE_PROVIDER = new PlasmaNetworkProvider();

	@Override
	public PlasmaNetwork provideNetwork() {
		return new PlasmaNetwork();
	}

	@Override
	public GenNode<PlasmaNetwork> provideNode(BlockPos... positions) {
		return new GenNode(this, positions);
	}
}
