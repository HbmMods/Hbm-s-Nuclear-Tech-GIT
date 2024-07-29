package com.hbm.dim;

import net.minecraft.world.biome.BiomeGenBase;

public abstract class BiomeGenBaseCelestial extends BiomeGenBase {

    public BiomeGenBaseCelestial(int id) {
        super(id);
		
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
    }
    
}
