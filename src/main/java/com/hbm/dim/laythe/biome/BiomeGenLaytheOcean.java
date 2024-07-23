package com.hbm.dim.laythe.biome;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenLaytheOcean extends BiomeGenBaseLaythe {
	
    public static final BiomeGenBase.Height height = new BiomeGenBase.Height(-0.6F, 0.01F);

	public BiomeGenLaytheOcean(int id) {
		super(id);
		this.setBiomeName("Sagan Sea");

        this.setHeight(height);
	}
}

	