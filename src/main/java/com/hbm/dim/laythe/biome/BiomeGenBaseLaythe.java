/*******************************************************************************
 * Copyright 2015 SteveKunG - More Planets Mod
 *
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package com.hbm.dim.laythe.biome;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.dim.BiomeDecoratorCelestial;
import com.hbm.dim.BiomeGenBaseCelestial;
import com.hbm.entity.mob.EntityScutterfish;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public abstract class BiomeGenBaseLaythe extends BiomeGenBaseCelestial {

	public static final BiomeGenBase laytheIsland = new BiomeGenLaytheIslands(SpaceConfig.laytheBiome).setTemperatureRainfall(0.2F, 0.2F);
	public static final BiomeGenBase laytheOcean = new BiomeGenLaytheOcean(SpaceConfig.laytheOceanBiome).setTemperatureRainfall(0.2F, 0.2F);
	public static final BiomeGenBase laythePolar = new BiomeGenLaythePolar(SpaceConfig.laythePolarBiome).setTemperatureRainfall(0.2F, 0.2F);

	public BiomeGenBaseLaythe(int id) {
		super(id);
		this.waterColorMultiplier = 0x5b009a;

        this.waterCreatures.add(new BiomeGenBase.SpawnListEntry(EntityScutterfish.class, 10, 4, 4));

		BiomeDecoratorCelestial decorator = new BiomeDecoratorCelestial(Blocks.stone);
		decorator.waterPlantsPerChunk = 32;
		this.theBiomeDecorator = decorator;
		this.theBiomeDecorator.generateLakes = false;
        
        this.topBlock = ModBlocks.laythe_silt;
        this.fillerBlock = ModBlocks.laythe_silt;
		BiomeDictionary.registerBiomeType(this, BiomeDictionary.Type.COLD, BiomeDictionary.Type.WET, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.SPOOKY);
	}
}