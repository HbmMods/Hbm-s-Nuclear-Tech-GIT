package com.hbm.tileentity.network;

import com.hbm.blocks.BlockDummyable;

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
		
		Vec3 vec = Vec3.createVectorHelper(sideOff, 0, 0);

		switch(getBlockMetadata() - BlockDummyable.offset) {
		case 2: vec.rotateAroundY((float) Math.PI * 0.0F); break;
		case 4: vec.rotateAroundY((float) Math.PI * 0.25F); break;
		case 3: vec.rotateAroundY((float) Math.PI * 0.5F); break;
		case 5: vec.rotateAroundY((float) Math.PI * 0.75F); break;
		}
		
		return new Vec3[] {
				Vec3.createVectorHelper(0.5 + vec.xCoord, 11.5 + topOff, 0.5 + vec.zCoord),
				Vec3.createVectorHelper(0.5 + vec.xCoord, 11.5 - topOff, 0.5 + vec.zCoord),
				Vec3.createVectorHelper(0.5 - vec.xCoord, 11.5 + topOff, 0.5 - vec.zCoord),
				Vec3.createVectorHelper(0.5 - vec.xCoord, 11.5 - topOff, 0.5 - vec.zCoord),
		};
	}

	@Override
	public double getMaxWireLength() {
		return 100;
	}
}
