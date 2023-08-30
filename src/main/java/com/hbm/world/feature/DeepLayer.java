package com.hbm.world.feature;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

public class DeepLayer {

	NoiseGeneratorPerlin noise;

	@SubscribeEvent
	public void onDecorate(PopulateChunkEvent.Pre event) {

		World world = event.world;
		if(world.provider == null || world.provider.dimensionId != 0) return;
		
		if(this.noise == null) {
			this.noise = new NoiseGeneratorPerlin(new Random(event.world.getSeed() + 19), 4);
		}
		
		int cX = event.chunkX;
		int cZ = event.chunkZ;
		
		double scale = 0.01D;
		int threshold = 2;
		
		for(int x = cX + 8; x < cX + 24; x++) {
			for(int z = cZ + 8; z < cZ + 24; z++) {
				
				double n = noise.func_151601_a(x * scale, z * scale);
				
				if(n > threshold) {
					int range = (int)((n - threshold) * 8);
					
					if(range > 24)
						range = 48 - range;
					
					if(range < 0)
						continue;
					
					for(int y = 1; y <= range; y++) {
						
						Block target = world.getBlock(x, y, z);
						
						if(target.isNormalCube() && target.getMaterial() == Material.rock && target != Blocks.bedrock) {
							
							boolean lava = false;
							
							for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
								Block neighbor = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
								if(neighbor.getMaterial() == Material.lava) {
									lava = true;
									break;
								}
							}
							
							if(lava || world.rand.nextInt(10) == 0) {
								world.setBlock(x, y, z, ModBlocks.stone_deep_cobble, 1, 2);
							} else if(world.rand.nextInt(10) == 0 && world.getBlock(x, y + 1, z).getMaterial() == Material.air) {
								world.setBlock(x, y, z, ModBlocks.stone_deep_cobble, 2, 2);
							} else {
								world.setBlock(x, y, z, ModBlocks.stone_deep_cobble, 0, 2);
							}
						}
					}
				}
			}
		}
	}
}
