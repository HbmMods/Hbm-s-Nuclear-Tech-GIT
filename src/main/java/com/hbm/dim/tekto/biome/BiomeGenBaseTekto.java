

package com.hbm.dim.tekto.biome;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.dim.BiomeDecoratorCelestial;
import com.hbm.dim.BiomeGenBaseCelestial;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public abstract class BiomeGenBaseTekto extends BiomeGenBaseCelestial {

	public static final BiomeGenBase polyvinylPlains = new BiomeGenPolyvinylPlains(SpaceConfig.tektoPolyvinyl).setTemperatureRainfall(1.0F, 0.5F);
	public static final BiomeGenBase halogenHills = new BiomeGenHalogenHills(SpaceConfig.HalogenHill).setTemperatureRainfall(1.0F, 0.5F);
	public static final BiomeGenBase tetrachloricRiver = new BiomeGenTetrachloricRiver(SpaceConfig.TektoRiver).setTemperatureRainfall(1.0F, 0.5F);

	public BiomeGenBaseTekto(int id) {
		super(id);
		this.waterColorMultiplier = 0x5b009a;

		this.theBiomeDecorator = new BiomeDecoratorCelestial(ModBlocks.sand_uranium);
		
		this.topBlock = ModBlocks.sand_boron;
		this.fillerBlock = ModBlocks.sand_boron_layer;
		BiomeDictionary.registerBiomeType(this, BiomeDictionary.Type.HOT, BiomeDictionary.Type.DRY, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.SPOOKY);
	}
}