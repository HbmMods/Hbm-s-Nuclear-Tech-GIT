package com.hbm.dim.laythe.biome;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenLaytheIslands extends BiomeGenBaseLaythe {
	
    public static final BiomeGenBase.Height height = new BiomeGenBase.Height(0.125F, 0.05F);

	public BiomeGenLaytheIslands(int id) {
		super(id);
		this.setBiomeName("Laythe Islands");
		this.waterColorMultiplier=0x5b209a;
        
        this.setHeight(height);
	}
}