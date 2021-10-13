package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.machine.TileEntityWatz;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Watz extends BlockDummyable {

	public Watz() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityWatz();
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 3, 3, 3, 3};
	}

	@Override
	public int getOffset() {
		return 3;
	}
}
