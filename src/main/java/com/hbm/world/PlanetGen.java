package com.hbm.world;

import com.hbm.config.WorldConfig;
import com.hbm.dim.WorldGeneratorMoon;
import com.hbm.dim.dres.WorldGeneratorDres;
import com.hbm.dim.WorldProviderMoon;
import com.hbm.dim.Ike.WorldGeneratorIke;
import com.hbm.dim.Ike.WorldProviderIke;
import com.hbm.dim.dres.WorldProviderDres;
import com.hbm.dim.duna.WorldGeneratorDuna;
import com.hbm.dim.duna.WorldProviderDuna;
import com.hbm.dim.duna.biome.BiomeGenBaseDuna;
import com.hbm.dim.eve.WorldProviderEve;
import com.hbm.dim.minmus.WorldProviderMinmus;
import com.hbm.dim.moho.WorldGeneratorMoho;
import com.hbm.dim.moho.WorldProviderMoho;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.BiomeDictionary.Type;

public class PlanetGen {
	public static WorldGeneratorMoon worldGenMoon = new WorldGeneratorMoon(); //eventually i will need to rewrite this shit.
	public static WorldGeneratorDuna worldGenDuna = new WorldGeneratorDuna(); 
	public static WorldGeneratorIke worldGenIke = new WorldGeneratorIke(); 
	public static WorldGeneratorDres worldGenDres = new WorldGeneratorDres(); //eventually i will need to rewrite this shit.

	public static WorldGeneratorMoho worldGenMoho = new WorldGeneratorMoho(); 
    public static void init()
    {
		GameRegistry.registerWorldGenerator(worldGenMoon, 0);
		DimensionManager.registerProviderType(WorldConfig.moonDimension, WorldProviderMoon.class, false);
	    DimensionManager.registerDimension(WorldConfig.moonDimension, WorldConfig.moonDimension);

		GameRegistry.registerWorldGenerator(worldGenDuna, 1);
		DimensionManager.registerProviderType(WorldConfig.dunaDimension, WorldProviderDuna.class, false);
	    DimensionManager.registerDimension(WorldConfig.dunaDimension, WorldConfig.dunaDimension);
	    

		GameRegistry.registerWorldGenerator(worldGenIke, 1);
		DimensionManager.registerProviderType(WorldConfig.ikeDimension, WorldProviderIke.class, false);
	    DimensionManager.registerDimension(WorldConfig.ikeDimension, WorldConfig.ikeDimension);
	    
		DimensionManager.registerProviderType(WorldConfig.eveDimension, WorldProviderEve.class, false);
	    DimensionManager.registerDimension(WorldConfig.eveDimension, WorldConfig.eveDimension);
	    
		DimensionManager.registerProviderType(WorldConfig.dresDimension, WorldProviderDres.class, false);
	    DimensionManager.registerDimension(WorldConfig.dresDimension, WorldConfig.dresDimension);
		GameRegistry.registerWorldGenerator(worldGenDres, 2);

	    
		GameRegistry.registerWorldGenerator(worldGenMoho, 1);
		DimensionManager.registerProviderType(WorldConfig.mohoDimension, WorldProviderMoho.class, false);
	    DimensionManager.registerDimension(WorldConfig.mohoDimension, WorldConfig.mohoDimension);
	    
		DimensionManager.registerProviderType(WorldConfig.minmusDimension, WorldProviderMinmus.class, false);
	    DimensionManager.registerDimension(WorldConfig.minmusDimension, WorldConfig.minmusDimension);
    }
}

