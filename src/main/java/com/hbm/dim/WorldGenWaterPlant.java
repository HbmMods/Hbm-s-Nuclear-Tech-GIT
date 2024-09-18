package com.hbm.dim;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenWaterPlant extends WorldGenerator {

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
	    boolean flag = false;

	    for (int l = 0; l < 64; ++l) {
	        int px = x + rand.nextInt(8) - rand.nextInt(8);
	        int py = y + rand.nextInt(4) - rand.nextInt(4);
	        int pz = z + rand.nextInt(8) - rand.nextInt(8);

	        boolean submerged = true;
	        for (int i = 0; i < 3; i++) {
	            if (world.getBlock(px, py + i, pz).getMaterial() != Material.water) {
	                submerged = false;
	            }
	        }

	        if (submerged && (!world.provider.hasNoSky || py < 254) && world.getBlock(px, py - 1, pz) == ModBlocks.laythe_silt) {
	            
	            if (rand.nextBoolean()) {
	                world.setBlock(px, py, pz, ModBlocks.plant_tall_laythe, 0, 2);
	                world.setBlock(px, py + 1, pz, ModBlocks.plant_tall_laythe, 8, 2);
	            } else {
	                int height = 2 + rand.nextInt(4); 
	                if(py <= 57) {
		                for (int h = 0; h < height; ++h) {
		                    world.setBlock(px, py + h, pz, ModBlocks.laythe_kelp, 0, 1);
		                }	                	
	                }

	            }

	            int px2 = x + rand.nextInt(8) - rand.nextInt(8);
	            int py2 = y + rand.nextInt(4) - rand.nextInt(4);
	            int pz2 = z + rand.nextInt(8) - rand.nextInt(8);

	            if (world.getBlock(px2, py2 - 1, pz2) == ModBlocks.laythe_silt) {
	                world.setBlock(px2, py2, pz2, ModBlocks.laythe_short, 0, 2);
	            }

	            flag = true;
	        }
	    }

	    return flag;
	}

}
