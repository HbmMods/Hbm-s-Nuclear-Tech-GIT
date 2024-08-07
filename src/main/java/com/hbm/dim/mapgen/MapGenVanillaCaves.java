package com.hbm.dim.mapgen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.MapGenCaves;

public class MapGenVanillaCaves extends MapGenCaves {

	// Same as vanilla cavegen but supports celestial stone
	private final Block stoneBlock;

	private Block lavaBlock = Blocks.lava;

	public MapGenVanillaCaves(Block stoneBlock) {
		this.stoneBlock = stoneBlock;
	}

	public MapGenVanillaCaves withLava(Block lavaBlock) {
		this.lavaBlock = lavaBlock;
		return this;
	}

	@Override
	protected void digBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop) {
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
		Block block = data[index];

		if(block == stoneBlock || block == biome.fillerBlock || block == biome.topBlock) {
			if(y < 10) {
				data[index] = lavaBlock;
			} else {
				data[index] = null;

				if(foundTop && data[index - 1] == biome.fillerBlock) {
					data[index - 1] = biome.topBlock;
				}
			}
		}
	}

}
