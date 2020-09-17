package com.hbm.blocks.generic;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class DecoPoleTop extends Block {

	public DecoPoleTop(Material p_i45386_1_) {
		super(p_i45386_1_);
	}
	
	@Override
	public int getRenderType(){
		return 334084;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

}
