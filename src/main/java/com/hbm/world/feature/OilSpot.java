package com.hbm.world.feature;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class OilSpot {

	public static void generateOilSpot(World world, int x, int z, int width, int count) {
		
		for(int i = 0; i < count; i++) {
			int rX = x + (int)(world.rand.nextGaussian() * width);
			int rZ = z + (int)(world.rand.nextGaussian() * width);
			int rY = world.getHeightValue(rX, rZ) - 1;
			
			Block ground = world.getBlock(rX, rY, rZ);
			
			if(ground == Blocks.grass || ground == Blocks.dirt) {
				world.setBlock(rX, rY, rZ, world.rand.nextInt(10) == 0 ? ModBlocks.dirt_oily : ModBlocks.dirt_dead);
				
			} else if(ground == Blocks.sand || ground == ModBlocks.ore_oil_sand) {
				
				if(world.getBlockMetadata(rX, rY, rZ) == 1)
					world.setBlock(rX, rY, rZ, ModBlocks.sand_dirty_red);
				else
					world.setBlock(rX, rY, rZ, ModBlocks.sand_dirty);
				
			} else if(ground.getMaterial() == Material.leaves) {
				world.setBlockToAir(rX, rY, rZ);
			}
		}
	}
}
