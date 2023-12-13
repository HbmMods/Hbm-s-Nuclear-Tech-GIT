package com.hbm.world;

import com.hbm.config.SpaceConfig;
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
import com.hbm.dim.eve.GenLayerEve.WorldGeneratorEve;
import com.hbm.dim.laythe.WorldProviderLaythe;
import com.hbm.dim.minmus.WorldGeneratorMinmus;
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
	public static WorldGeneratorMinmus WorldGenMinmus = new WorldGeneratorMinmus(); 
	public static WorldGeneratorEve WorldGenEve = new WorldGeneratorEve(); 

	public static WorldGeneratorMoho worldGenMoho = new WorldGeneratorMoho(); 
    public static void init()
    {
		GameRegistry.registerWorldGenerator(worldGenMoon, 0);
		DimensionManager.registerProviderType(SpaceConfig.moonDimension, WorldProviderMoon.class, false);
	    DimensionManager.registerDimension(SpaceConfig.moonDimension, SpaceConfig.moonDimension);

		GameRegistry.registerWorldGenerator(worldGenDuna, 1);
		DimensionManager.registerProviderType(SpaceConfig.dunaDimension, WorldProviderDuna.class, false);
	    DimensionManager.registerDimension(SpaceConfig.dunaDimension, SpaceConfig.dunaDimension);
	    

		GameRegistry.registerWorldGenerator(worldGenIke, 1);
		DimensionManager.registerProviderType(SpaceConfig.ikeDimension, WorldProviderIke.class, false);
	    DimensionManager.registerDimension(SpaceConfig.ikeDimension, SpaceConfig.ikeDimension);
	    
		DimensionManager.registerProviderType(SpaceConfig.eveDimension, WorldProviderEve.class, false);
	    DimensionManager.registerDimension(SpaceConfig.eveDimension, SpaceConfig.eveDimension);
		GameRegistry.registerWorldGenerator(WorldGenEve, 0);

		DimensionManager.registerProviderType(SpaceConfig.dresDimension, WorldProviderDres.class, false);
	    DimensionManager.registerDimension(SpaceConfig.dresDimension, SpaceConfig.dresDimension);
		GameRegistry.registerWorldGenerator(worldGenDres, 1);

	    
		GameRegistry.registerWorldGenerator(worldGenMoho, 1);
		DimensionManager.registerProviderType(SpaceConfig.mohoDimension, WorldProviderMoho.class, false);
	    DimensionManager.registerDimension(SpaceConfig.mohoDimension, SpaceConfig.mohoDimension);
	    
		DimensionManager.registerProviderType(SpaceConfig.minmusDimension, WorldProviderMinmus.class, false);
	    DimensionManager.registerDimension(SpaceConfig.minmusDimension, SpaceConfig.minmusDimension);
		GameRegistry.registerWorldGenerator(WorldGenMinmus, 1);
		
		DimensionManager.registerProviderType(SpaceConfig.laytheDimension, WorldProviderLaythe.class, false);
	    DimensionManager.registerDimension(SpaceConfig.laytheDimension, SpaceConfig.laytheDimension);
	    

    }
}

