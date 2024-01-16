package com.hbm.world.feature;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

public class SchistStratum {

	NoiseGeneratorPerlin noise;

	@SubscribeEvent
	public void onDecorate(DecorateBiomeEvent.Pre event) {
		
		if(this.noise == null) {
			this.noise = new NoiseGeneratorPerlin(new Random(event.world.getSeed()), 4);
		}

		World world = event.world;
		
		if(world.provider == null || world.provider.dimensionId != 0)
			return;
		
		int cX = event.chunkX;
		int cZ = event.chunkZ;
		
		double scale = 0.01D;
		int threshold = 5;
		
		for(int x = cX + 8; x < cX + 24; x++) {
			for(int z = cZ + 8; z < cZ + 24; z++) {
				
				double n = noise.func_151601_a(x * scale, z * scale);
				
				if(n > threshold) {
					int range = (int)((n - threshold) * 3);
					
					if(range > 4)
						range = 8 - range;
					
					if(range < 0)
						continue;
					
					for(int y = 30 - range; y <= 30 + range; y++) {
						
						Block target = world.getBlock(x, y, z);
						
						if(target.isNormalCube() && target.getMaterial() == Material.rock && target.isReplaceableOreGen(world, x, y, z, Blocks.stone)) {
							world.setBlock(x, y, z, ModBlocks.stone_gneiss, 0, 2);
						}
					}
				}
			}
		}
	}
}
