package com.hbm.blocks.generic;

import com.hbm.blocks.BlockBase;
import com.hbm.interfaces.IBlockRarity;

import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockBeaconable extends BlockBase implements IBlockRarity
{

	public BlockBeaconable(Material mat) {
		super(mat);
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
		return true;
	}
}
