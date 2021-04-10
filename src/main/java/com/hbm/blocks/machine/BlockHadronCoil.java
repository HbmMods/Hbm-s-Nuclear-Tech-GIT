package com.hbm.blocks.machine;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockHadronCoil extends Block {
	
	public int factor;

	public BlockHadronCoil(Material mat, int factor) {
		super(mat);
		this.factor = factor;
	}
}
