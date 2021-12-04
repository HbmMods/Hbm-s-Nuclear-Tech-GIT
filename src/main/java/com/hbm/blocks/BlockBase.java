package com.hbm.blocks;

import com.hbm.lib.RefStrings;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockBase extends Block {
	
	private boolean beaconable = false;

	public BlockBase() {
		super(Material.rock);
	}

	public BlockBase(Material material) {
		super(material);
	}
	
	@Override
	public Block setBlockName(String name) {
		super.setBlockName(name);
		this.setBlockTextureName(RefStrings.MODID + ":" + name);
		return this;
	}
	
	public BlockBase setBeaconable() {
		this.beaconable = true;
		return this;
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
		return this.beaconable;
	}
}
