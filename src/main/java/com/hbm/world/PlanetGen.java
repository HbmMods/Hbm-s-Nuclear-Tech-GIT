package com.hbm.world;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.dres.WorldGeneratorDres;
import com.hbm.dim.WorldGeneratorCelestial;
import com.hbm.dim.Ike.WorldGeneratorIke;
import com.hbm.dim.Ike.WorldProviderIke;
import com.hbm.dim.dres.WorldProviderDres;
import com.hbm.dim.duna.WorldGeneratorDuna;
import com.hbm.dim.duna.WorldProviderDuna;
import com.hbm.dim.eve.WorldGeneratorEve;
import com.hbm.dim.eve.WorldProviderEve;
import com.hbm.dim.laythe.WorldGeneratorLaythe;
import com.hbm.dim.laythe.WorldProviderLaythe;
import com.hbm.dim.minmus.WorldGeneratorMinmus;
import com.hbm.dim.minmus.WorldProviderMinmus;
import com.hbm.dim.moho.WorldGeneratorMoho;
import com.hbm.dim.moho.WorldProviderMoho;
import com.hbm.dim.moon.WorldGeneratorMoon;
import com.hbm.dim.moon.WorldProviderMoon;
import com.hbm.dim.orbit.WorldProviderOrbit;
import com.hbm.dim.tekto.WorldGeneratorTekto;
import com.hbm.dim.tekto.WorldProviderTekto;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;

public class PlanetGen {

    public static void init() {

		// Register our ore providers
		GameRegistry.registerWorldGenerator(new WorldGeneratorCelestial(), 2);

		GameRegistry.registerWorldGenerator(new WorldGeneratorMoon(), 1);
		GameRegistry.registerWorldGenerator(new WorldGeneratorDuna(), 1);
		GameRegistry.registerWorldGenerator(new WorldGeneratorIke(), 1);
		GameRegistry.registerWorldGenerator(new WorldGeneratorEve(), 1);
		GameRegistry.registerWorldGenerator(new WorldGeneratorDres(), 1);
		GameRegistry.registerWorldGenerator(new WorldGeneratorMoho(), 1);
		GameRegistry.registerWorldGenerator(new WorldGeneratorMinmus(), 1);
		GameRegistry.registerWorldGenerator(new WorldGeneratorLaythe(), 1);
		GameRegistry.registerWorldGenerator(new WorldGeneratorTekto(), 1);

		registerDimension(SpaceConfig.moonDimension, WorldProviderMoon.class);
		registerDimension(SpaceConfig.dunaDimension, WorldProviderDuna.class);
		registerDimension(SpaceConfig.ikeDimension, WorldProviderIke.class);
		registerDimension(SpaceConfig.eveDimension, WorldProviderEve.class);
		registerDimension(SpaceConfig.dresDimension, WorldProviderDres.class);
		registerDimension(SpaceConfig.mohoDimension, WorldProviderMoho.class);
		registerDimension(SpaceConfig.minmusDimension, WorldProviderMinmus.class);
		registerDimension(SpaceConfig.laytheDimension, WorldProviderLaythe.class);
		registerDimension(SpaceConfig.orbitDimension, WorldProviderOrbit.class);
		registerDimension(SpaceConfig.tektoDimension, WorldProviderTekto.class);

    }

	private static void registerDimension(int dimensionId, Class<? extends WorldProvider> clazz) {
		DimensionManager.registerProviderType(dimensionId, clazz, false);
		DimensionManager.registerDimension(dimensionId, dimensionId);
	}
	
}

