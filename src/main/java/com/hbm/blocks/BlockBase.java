package com.hbm.blocks;

import com.hbm.lib.RefStrings;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block {

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
}
