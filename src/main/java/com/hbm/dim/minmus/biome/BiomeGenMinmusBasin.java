package com.hbm.dim.minmus.biome;

import com.hbm.blocks.ModBlocks;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenMinmusBasin extends BiomeGenBaseMinmus {
	
    public static final BiomeGenBase.Height height = new BiomeGenBase.Height(-1F, 0.02F);

	public BiomeGenMinmusBasin(int id) {
		super(id);
		this.setBiomeName("Minmus Basins");
        
        this.setHeight(height);
        this.topBlock = ModBlocks.minmus_smooth; 
        this.fillerBlock = ModBlocks.minmus_regolith;
	}

}