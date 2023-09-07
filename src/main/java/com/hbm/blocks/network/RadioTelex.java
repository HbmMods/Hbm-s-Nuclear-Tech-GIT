package com.hbm.blocks.network;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.network.TileEntityRadioTelex;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RadioTelex extends BlockDummyable {

	public RadioTelex() {
		super(Material.wood);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityRadioTelex();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 0, 0, 1, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}
}
