package com.hbm.blocks.generic;

import com.hbm.blocks.IBlockSideRotation;
import net.minecraft.block.material.Material;

public class LogicBlockInvis extends LogicBlock{

	public LogicBlockInvis() {
		super();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}
}
