package com.hbm.tileentity.network;

import net.minecraft.util.Vec3;

public class TileEntityPylonLarge extends TileEntityPylonBase {

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.QUAD;
	}

	@Override
	public Vec3[] getMountPos() {
		return null;
	}

	@Override
	public double getMaxWireLength() {
		return 100;
	}

}
