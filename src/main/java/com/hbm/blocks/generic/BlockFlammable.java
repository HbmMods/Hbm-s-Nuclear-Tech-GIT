package com.hbm.blocks.generic;

import com.hbm.blocks.BlockBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFlammable extends BlockBase {
	
	public int encouragement;
	public int flammability;

	public BlockFlammable(Material mat, int en, int flam) {
		super(mat);
		this.encouragement = en;
		this.flammability = flam;
	}
	
	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return flammability;
	}
	
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return encouragement;
	}
}
