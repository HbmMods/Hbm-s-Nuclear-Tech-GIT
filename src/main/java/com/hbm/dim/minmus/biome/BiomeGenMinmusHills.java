package com.hbm.dim.minmus.biome;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenMinmusHills extends BiomeGenBaseMinmus {
	
    public static final BiomeGenBase.Height height = new BiomeGenBase.Height(0.325F, 0.08F);

	public BiomeGenMinmusHills(int id) {
		super(id);
		this.setBiomeName("Minmus Hills");
        
        this.setHeight(height);
	}
	
}