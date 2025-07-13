package com.hbm.uninos.networkproviders;

import com.hbm.uninos.INetworkProvider;

public class PneumaticNetworkProvider implements INetworkProvider<PneumaticNetwork>{
	
	public static PneumaticNetworkProvider THE_PROVIDER = new PneumaticNetworkProvider();

	@Override
	public PneumaticNetwork provideNetwork() {
		return new PneumaticNetwork();
	}
}
