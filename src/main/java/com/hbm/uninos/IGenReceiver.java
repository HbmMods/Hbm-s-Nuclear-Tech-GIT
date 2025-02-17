package com.hbm.uninos;

import api.hbm.energymk2.IEnergyReceiverMK2.ConnectionPriority;

public interface IGenReceiver<T extends INetworkProvider> {
	
	public default ConnectionPriority getPriority() {
		return ConnectionPriority.NORMAL;
	}
}
