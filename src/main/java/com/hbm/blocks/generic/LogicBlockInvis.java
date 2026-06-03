package com.hbm.blocks.generic;

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
