package com.hbm.blocks.network.pneumatic;

import com.hbm.blocks.network.BasePipeAnchor;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumaticPipeAnchor;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PneumoPipeAnchor extends BasePipeAnchor {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPneumaticPipeAnchor();
	}
}
