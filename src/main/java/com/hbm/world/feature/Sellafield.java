package com.hbm.world.feature;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class Sellafield {
	
	private double depthFunc(double x, double rad, double depth) {
		
		return -Math.pow(x, 2) / Math.pow(rad, 2) * depth + depth;
	}
	
	public void generate(World world, int x, int z, double radius, double depth) {
		
		if(world.isRemote)
			return;
		
		Random rand = new Random();
		
		int iRad = (int)Math.round(radius);
		
		for(int a = -iRad - 5; a <= iRad + 5; a++) {
			
			for(int b = -iRad - 5; b <= iRad + 5; b++) {
				
				double r = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
				
				if(r - rand.nextInt(3) <= radius) {
					
					int dep = (int)depthFunc(r, radius, depth);
					dig(world, x + a, z + b, dep);

					if(r + rand.nextInt(3) <= radius / 3D) {
						place(world, x + a, z + b, 3, ModBlocks.sellafield, 1);
					} else if(r - rand.nextInt(3) <= radius / 3D * 2D) {
						place(world, x + a, z + b, 3, ModBlocks.sellafield);
					} else {
						place(world, x + a, z + b, 3, ModBlocks.sellafield_slaked);
					}
				}
			}
		}
	}
	
	private void dig(World world, int x, int z, int depth) {
		
		int y = world.getHeightValue(x, z) - 1;
		
		if(y < depth * 2)
			return;
		
		for(int i = 0; i < depth; i++)
			world.setBlockToAir(x, y - i, z);
	}
	
	private void place(World world, int x, int z, int depth, Block block) { place(world, x, z, depth, block, 0); }
	
	private void place(World world, int x, int z, int depth, Block block, int meta) {
		
		int y = world.getHeightValue(x, z) - 1;
		
		for(int i = 0; i < depth; i++)
			world.setBlock(x, y - i, z, block, meta, 2);
	}
	
	//private void placeCore(World world, int x, int z, double rad) { }
}
