package com.hbm.uninos.networkproviders;

import com.hbm.uninos.INetworkProvider;

public class KlystronNetworkProvider implements INetworkProvider<KlystronNetwork> {
	
	public static KlystronNetworkProvider THE_PROVIDER = new KlystronNetworkProvider();

	@Override
	public KlystronNetwork provideNetwork() {
		return new KlystronNetwork();
	}
}
