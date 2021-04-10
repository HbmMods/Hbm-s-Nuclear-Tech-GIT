package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityHadronPower;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHadronPower extends BlockContainer {
	
	public long power;

	public BlockHadronPower(Material mat, long power) {
		super(mat);
		this.power = power;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityHadronPower();
	}
}
