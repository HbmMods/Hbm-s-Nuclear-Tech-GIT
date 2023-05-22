package com.hbm.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Custom handling that tells TileEntityProxyBase where the core for a given proxy block is.
 * BlockDummyable doesn't implement this since it already has its own standardized core finding code.
 * 
 * @author hbm
 */
public interface IProxyController {

	public TileEntity getCore(World world, int x, int y, int z);
}
