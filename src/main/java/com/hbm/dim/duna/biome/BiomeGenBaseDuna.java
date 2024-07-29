
package com.hbm.dim.duna.biome;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.dim.BiomeDecoratorCelestial;
import com.hbm.dim.BiomeGenBaseCelestial;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public abstract class BiomeGenBaseDuna extends BiomeGenBaseCelestial {
    
    public static final BiomeGenBase dunaPlains = new BiomeGenDunaPlains(SpaceConfig.dunaBiome).setTemperatureRainfall(-1.0F, 0.0F);
    public static final BiomeGenBase dunaLowlands = new BiomeGenDunaLowlands(SpaceConfig.dunaLowlandsBiome).setTemperatureRainfall(-1.0F, 0.0F);
    public static final BiomeGenBase dunaPolar = new BiomeGenDunaPolar(SpaceConfig.dunaPolarBiome).setTemperatureRainfall(-1.0F, 0.0F);
    public static final BiomeGenBase dunaHills = new BiomeGenDunaHills(SpaceConfig.dunaHillsBiome).setTemperatureRainfall(-1.0F, 0.0F);
    public static final BiomeGenBase dunaPolarHills = new BiomeGenDunaPolarHills(SpaceConfig.dunaPolarHillsBiome).setTemperatureRainfall(-1.0F, 0.0F);
    
    public BiomeGenBaseDuna(int id) {
        super(id);
        
		this.setDisableRain();

        this.theBiomeDecorator = new BiomeDecoratorCelestial(ModBlocks.duna_rock);
        this.theBiomeDecorator.generateLakes = false;
		
		this.topBlock = ModBlocks.duna_sands;
		this.fillerBlock = ModBlocks.duna_rock;
		BiomeDictionary.registerBiomeType(this, BiomeDictionary.Type.COLD, BiomeDictionary.Type.DRY, BiomeDictionary.Type.DEAD);
    }
}