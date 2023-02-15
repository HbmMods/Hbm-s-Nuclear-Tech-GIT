package com.hbm.blocks;

public interface IBlockMulti {

	public int getSubCount();
	
	public default int rectify(int meta) {
		return Math.abs(meta % getSubCount());
	}
}
