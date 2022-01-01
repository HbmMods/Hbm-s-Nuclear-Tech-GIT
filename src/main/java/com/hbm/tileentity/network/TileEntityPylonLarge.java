package com.hbm.tileentity.network;

import net.minecraft.util.Vec3;

public class TileEntityPylonLarge extends TileEntityPylonBase {

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.QUAD;
	}

	@Override
	public Vec3[] getMountPos() {
		double topOff = 0.75 + 0.0625;
		double sideOff = 3.375;
		return new Vec3[] {
				Vec3.createVectorHelper(0.5 + sideOff, 11.5 + topOff, 0.5),
				Vec3.createVectorHelper(0.5 + sideOff, 11.5 - topOff, 0.5),
				Vec3.createVectorHelper(0.5 - sideOff, 11.5 + topOff, 0.5),
				Vec3.createVectorHelper(0.5 - sideOff, 11.5 - topOff, 0.5),
		};
	}

	@Override
	public double getMaxWireLength() {
		return 100;
	}

}
