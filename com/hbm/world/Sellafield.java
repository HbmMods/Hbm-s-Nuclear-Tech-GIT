package com.hbm.world;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.bomb.TileEntitySellafield;

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

					if(r + rand.nextInt(3) <= radius / 6D) {
						place(world, x + a, z + b, 3, ModBlocks.sellafield_4);
					} else if(r - rand.nextInt(3) <= radius / 6D * 2D) {
						place(world, x + a, z + b, 3, ModBlocks.sellafield_3);
					} else if(r - rand.nextInt(3) <= radius / 6D * 3D) {
						place(world, x + a, z + b, 3, ModBlocks.sellafield_2);
					} else if(r - rand.nextInt(3) <= radius / 6D * 4D) {
						place(world, x + a, z + b, 3, ModBlocks.sellafield_1);
					} else if(r - rand.nextInt(3) <= radius / 6D * 5D) {
						place(world, x + a, z + b, 3, ModBlocks.sellafield_0);
					} else {
						place(world, x + a, z + b, 3, ModBlocks.sellafield_slaked);
					}
				}
			}
		}

		placeCore(world, x, z, radius * 0.3D);
	}
	
	private void dig(World world, int x, int z, int depth) {
		
		int y = world.getHeightValue(x, z) - 1;
		
		if(y < depth * 2)
			return;
		
		for(int i = 0; i < depth; i++)
			world.setBlockToAir(x, y - i, z);
	}
	
	private void place(World world, int x, int z, int depth, Block block) {
		
		int y = world.getHeightValue(x, z) - 1;
		
		for(int i = 0; i < depth; i++)
			world.setBlock(x, y - i, z, block);
	}
	
	private void placeCore(World world, int x, int z, double rad) {
		
		int y = world.getHeightValue(x, z) - 1;
		
		world.setBlock(x, y, z, ModBlocks.sellafield_core);
		
		try {
			
			TileEntitySellafield te = (TileEntitySellafield) world.getTileEntity(x, y, z);
			te.radius = rad;
			
		} catch(Exception ex) { }
	}
}
