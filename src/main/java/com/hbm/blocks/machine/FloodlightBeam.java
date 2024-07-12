package com.hbm.blocks.machine;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class FloodlightBeam extends BlockBeamBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFloodlightBeam();
	}
	
	public static class TileEntityFloodlightBeam extends TileEntity {
		
	}
}
