package com.hbm.uninos.networkproviders;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.uninos.INetworkProvider;

import api.hbm.fluidmk2.FluidNetMK2;

public class FluidNetProvider implements INetworkProvider<FluidNetMK2> {
	
	protected FluidType type;
	
	public FluidNetProvider(FluidType type) {
		this.type = type;
	}

	@Override
	public FluidNetMK2 provideNetwork() {
		return new FluidNetMK2(type);
	}
}
