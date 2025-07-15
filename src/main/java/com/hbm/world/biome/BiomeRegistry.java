package com.hbm.world.biome;

import com.hbm.config.WorldConfig;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeRegistry {
    
    public static BiomeGenBase noMansLand;
    
    public static void registerBiomes() {
        // Register No Man's Land biome
        if(WorldConfig.enableNoMansLand) {
            noMansLand = new BiomeGenNoMansLand(WorldConfig.noMansLandBiomeId)
                    .setColor(0x696F59)
                    .setBiomeName("No Man's Land")
                    .setDisableRain();
            
            BiomeGenNoMansLand.initDictionary();
        }
    }
}
