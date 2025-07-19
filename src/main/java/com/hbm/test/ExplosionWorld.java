package com.hbm.test;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class ExplosionWorld {

	//public Block[][][] blocks = new Block[500][256][500];
	
	public void setBlock(int x, int y, int z, Block block) {
		long nanos = System.nanoTime();
		while(System.nanoTime() < nanos + 30_000);
	} //NOP
	
	public Block getBlock(int x, int y, int z) {
		long nanos = System.nanoTime();
		while(System.nanoTime() < nanos + 1_000);
		if(y == 0) return Blocks.bedrock;
		if(y < 50) return Blocks.stone;
		if(y < 64) return Blocks.dirt;
		
		return Blocks.air;
	}
	
	public boolean isAirBlock(int x, int y, int z) {
		return getBlock(x, y, z) == Blocks.air;
	}
}
