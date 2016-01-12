package com.hbm.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityDecoBlockAltG extends TileEntity {
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return this.INFINITE_EXTENT_AABB;
	}

}
