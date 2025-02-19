package com.hbm.uninos.networkproviders;

import com.hbm.uninos.INetworkProvider;
import com.hbm.uninos.networks.PowerNetwork;

public class PowerProvider implements INetworkProvider<PowerNetwork> {

	@Override
	public PowerNetwork provideNetwork() {
		return new PowerNetwork();
	}
}
