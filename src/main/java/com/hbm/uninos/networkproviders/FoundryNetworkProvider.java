package com.hbm.uninos.networkproviders;

import com.hbm.uninos.INetworkProvider;

public class FoundryNetworkProvider implements INetworkProvider<FoundryNetwork> {

	public static FoundryNetworkProvider THE_PROVIDER = new FoundryNetworkProvider();

	@Override
	public FoundryNetwork provideNetwork() {
		return new FoundryNetwork();
	}

}
