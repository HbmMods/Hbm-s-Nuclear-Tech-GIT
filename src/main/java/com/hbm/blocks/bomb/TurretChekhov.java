package com.hbm.blocks.bomb;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.bomb.TileEntityTurretChekhov;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TurretChekhov extends BlockDummyable {

	public TurretChekhov(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityTurretChekhov();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 1, 0, 1, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}

}
