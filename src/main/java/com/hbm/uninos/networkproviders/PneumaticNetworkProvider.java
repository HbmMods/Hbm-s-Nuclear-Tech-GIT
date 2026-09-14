package com.hbm.uninos.networkproviders;

import com.hbm.uninos.GenNode;
import com.hbm.uninos.INetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;

public class PneumaticNetworkProvider implements INetworkProvider<PneumaticNetwork> {
	
	public static PneumaticNetworkProvider THE_PROVIDER = new PneumaticNetworkProvider();

	@Override
	public PneumaticNetwork provideNetwork() {
		return new PneumaticNetwork();
	}

	@Override
	public GenNode<PneumaticNetwork> provideNode(BlockPos... positions) {
		return new GenNode(this, positions);
	}
}
