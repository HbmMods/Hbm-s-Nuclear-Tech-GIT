package com.hbm.blocks.bomb;

import com.hbm.blocks.machine.BlockMachineBase;
import com.hbm.tileentity.bomb.TileEntityNukeBalefire;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class NukeBalefire extends BlockMachineBase {

	protected NukeBalefire(Material mat, int guiID) {
		super(mat, guiID);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityNukeBalefire();
	}

}
