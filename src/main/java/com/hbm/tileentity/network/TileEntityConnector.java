package com.hbm.tileentity.network;

import net.minecraft.util.Vec3;

public class TileEntityConnector extends TileEntityPylonBase {

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.SINGLE;
	}

	@Override
	public Vec3 getMountPos() {
		return Vec3.createVectorHelper(0.5, 0.5, 0.5);
	}

	@Override
	public double getMaxWireLength() {
		return 10;
	}

}
