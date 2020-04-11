package com.hbm.world.generator;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class DungeonToolbox {

	
	public static void generateBox(World world, int x, int y, int z, int sx, int sy, int sz, List<Block> blocks) {
		
		if(blocks.isEmpty())
			return;
		
		for(int i = x; i < x + sx; i++) {
			
			for(int j = y; j < y + sy; j++) {
				
				for(int k = z; k < z + sz; k++) {
					
					Block b = getRandom(blocks, world.rand);
					world.setBlock(i, j, k, b, 0, 2);
				}
			}
		}
	}

	//i know it's copy paste, but it's a better strat than using a wrapper and generating single-entry lists for no good reason
	public static void generateBox(World world, int x, int y, int z, int sx, int sy, int sz, Block block) {
		
		for(int i = x; i < x + sx; i++) {
			
			for(int j = y; j < y + sy; j++) {
				
				for(int k = z; k < z + sz; k++) {
					
					world.setBlock(i, j, k, block, 0, 2);
				}
			}
		}
	}
	
	//now with vectors to provide handy rotations
	public static void generateBox(World world, int x, int y, int z, Vec3 size, List<Block> blocks) {
		
		generateBox(world, x, y, z, (int)size.xCoord, (int)size.yCoord, (int)size.zCoord, blocks);
	}
	
	public static <T> T getRandom(List<T> list, Random rand) {
		
		if(list.isEmpty())
			return null;

		return list.get(rand.nextInt(list.size()));
	}
}
