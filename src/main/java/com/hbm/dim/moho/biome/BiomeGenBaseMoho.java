package com.hbm.dim.moho.biome;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.dim.BiomeDecoratorCelestial;
import com.hbm.dim.BiomeGenBaseCelestial;

import net.minecraft.world.biome.BiomeGenBase;

public abstract class BiomeGenBaseMoho extends BiomeGenBaseCelestial {

	public static final BiomeGenBase mohoCrag = new BiomeGenMohoCrag(SpaceConfig.mohoBiome);
	public static final BiomeGenBase mohoBasalt = new BiomeGenMohoBasalt(SpaceConfig.mohoBasaltBiome);

	public BiomeGenBaseMoho(int id) {
		super(id);
		this.setDisableRain();

		BiomeDecoratorCelestial decorator = new BiomeDecoratorCelestial(ModBlocks.moho_stone);
		decorator.lavaCount = 50;
		this.theBiomeDecorator = decorator;

		this.setTemperatureRainfall(1.0F, 0.0F);
	}

}
