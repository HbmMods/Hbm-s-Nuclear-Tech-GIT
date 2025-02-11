package com.hbm.uninos.networkproviders;

import com.hbm.uninos.INetworkProvider;
import com.hbm.uninos.INodeNet;
import com.hbm.uninos.networks.PowerNetwork;

public class PowerProvider implements INetworkProvider {

	@Override
	public INodeNet<PowerProvider> provideNetwork() {
		return new PowerNetwork();
	}
}
