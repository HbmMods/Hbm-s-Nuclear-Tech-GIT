package com.hbm.uninos.networkproviders;

import com.hbm.uninos.INetworkProvider;

public class PlasmaNetworkProvider implements INetworkProvider<PlasmaNetwork> {
	
	public static PlasmaNetworkProvider THE_PROVIDER = new PlasmaNetworkProvider();

	@Override
	public PlasmaNetwork provideNetwork() {
		return new PlasmaNetwork();
	}
}
