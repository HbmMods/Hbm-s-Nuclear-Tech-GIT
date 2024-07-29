

package com.hbm.dim.minmus.biome;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.BiomeDecoratorCelestial;
import com.hbm.dim.BiomeGenBaseCelestial;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class BiomeGenBaseMinmus extends BiomeGenBaseCelestial {

    public static final BiomeGenBase minmusPlains = new BiomeGenMinmusHills(SpaceConfig.minmusBiome).setTemperatureRainfall(-1.0F, 0.0F);
    public static final BiomeGenBase minmusCanyon = new BiomeGenMinmusBasin(SpaceConfig.minmusBasins).setTemperatureRainfall(-1.0F, 0.0F);
    
    public BiomeGenBaseMinmus(int id) {
        super(id);
		this.setDisableRain();
        
        this.theBiomeDecorator = new BiomeDecoratorCelestial(Blocks.snow);
        this.theBiomeDecorator.generateLakes = false;
        
        this.topBlock = Blocks.snow;
        this.fillerBlock = Blocks.snow;
    }
}