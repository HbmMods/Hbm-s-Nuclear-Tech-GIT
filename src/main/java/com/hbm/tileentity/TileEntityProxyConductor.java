package com.hbm.tileentity;

import api.hbm.energymk2.IEnergyConnectorMK2;

public class TileEntityProxyConductor extends TileEntityProxyBase implements IEnergyConnectorMK2 {

	@Override
	public boolean canUpdate() {
		return false;
	}
}
