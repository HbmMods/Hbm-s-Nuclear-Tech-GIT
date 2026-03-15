package com.hbm.blocks.machine.fusion;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.machine.fusion.TileEntityFusionPlasmaForge;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineFusionPlasmaForge extends BlockDummyable {

	public MachineFusionPlasmaForge() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityFusionPlasmaForge();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 4, 0, 5, 5, 5, 5 };
	}

	@Override
	public int getOffset() {
		return 5;
	}
}
