package com.hbm.dim.mapgen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.MapGenRavine;

public class MapgenRavineButBased extends MapGenRavine {

	public Block stoneBlock;

	public MapgenRavineButBased() {
		super();
		this.stoneBlock = Blocks.stone;
	}

	@Override
	protected void digBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop) {
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
		Block top = biome.topBlock;
		Block filler = biome.fillerBlock;
		Block block = data[index];

		if(block == stoneBlock || block == filler || block == top) {
			if(y < 10) {
				data[index] = Blocks.flowing_lava;
			} else {
				data[index] = null;

				if (foundTop && data[index - 1] == filler) {
					data[index - 1] = top;
				}
			}
		}
	}

}
