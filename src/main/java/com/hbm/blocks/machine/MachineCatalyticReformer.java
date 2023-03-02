package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.machine.oil.TileEntityMachineCatalyticReformer;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineCatalyticReformer extends BlockDummyable {

	public MachineCatalyticReformer(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12) return new TileEntityMachineCatalyticReformer();
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {6, 0, 1, 1, 2, 2};
	}

	@Override
	public int getOffset() {
		return 1;
	}
}
