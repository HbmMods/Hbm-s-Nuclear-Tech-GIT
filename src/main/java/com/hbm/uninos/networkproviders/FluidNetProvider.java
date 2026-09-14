package com.hbm.uninos.networkproviders;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.uninos.INetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.fluidmk2.FluidNetMK2;
import api.hbm.fluidmk2.FluidNode;

public class FluidNetProvider implements INetworkProvider<FluidNetMK2> {
	
	protected FluidType type;
	
	public FluidNetProvider(FluidType type) {
		this.type = type;
	}

	@Override
	public FluidNetMK2 provideNetwork() {
		return new FluidNetMK2(type);
	}

	@Override
	public FluidNode provideNode(BlockPos... positions) {
		return new FluidNode(this, positions);
	}
}
