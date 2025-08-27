package com.hbm.uninos.networkproviders;

import com.hbm.uninos.INetworkProvider;

public class RebarNetworkProvider implements INetworkProvider<RebarNetwork> {
	
	public static RebarNetworkProvider THE_PROVIDER = new RebarNetworkProvider();

	@Override
	public RebarNetwork provideNetwork() {
		return new RebarNetwork();
	}
}
