package com.hbm.uninos.networkproviders;

import com.hbm.uninos.INetworkProvider;

import api.hbm.fluidmk2.FluidNetMK2;

public class FluidNetProvider implements INetworkProvider<FluidNetMK2> {

	@Override
	public FluidNetMK2 provideNetwork() {
		return new FluidNetMK2();
	}
}
