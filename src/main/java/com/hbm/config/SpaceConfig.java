package com.hbm.config;

import net.minecraftforge.common.config.Configuration;

public class SpaceConfig {

	//thanks minecraft, for making the biome id limit 128 because apparently anything after that spawns in the overworld.
	//THANKS MOJANG..
	public static int dunaoilSpawn = 100;

	public static int moonDimension = 15;
	public static int dunaDimension = 16;
	public static int ikeDimension = 17;
	public static int eveDimension = 18;
	public static int dresDimension = 19;
	public static int mohoDimension = 20;
	public static int minmusDimension = 21;
	public static int laytheDimension = 22;

	public static int minmusBiome = 40;
	public static int minmusBasins = 41;
	// why are we squished into the ceiling
	public static int moonBiome = 111;
	public static int dunaBiome = 112;
	public static int dunaLowlandsBiome = 113;
	public static int dunaPolarBiome = 114;
	public static int dunaHillsBiome = 115;
	public static int dunaPolarHillsBiome = 116;
	public static int eveBiome = 117;
	public static int eveMountainsBiome = 118;
	public static int eveOceanBiome = 119;
	public static int dresBiome = 120;
	public static int dresBasins = 121;
	public static int mohoBiome = 122;
	public static int laytheBiome = 123;
	public static int laytheOceanBiome = 124;
	public static int eveSeismicBiome = 125;
	public static int laythePolarBiome = 126; //fuck my stupid chungus life
	public static int ikeBiome = 127;

	

	

	


	public static boolean allowNetherPortals = false;

	public static int maxProbeDistance = 32_000;
	
	public static void loadFromConfig(Configuration config) {
	
		final String CATEGORY_DIM = CommonConfig.CATEGORY_DIMS;
		allowNetherPortals = CommonConfig.createConfigBool(config, CATEGORY_DIM, "17.00_allowNetherPortals", "Should Nether portals function on other celestial bodies?", false);
		
		moonDimension = CommonConfig.createConfigInt(config, CATEGORY_DIM, "17.01_moonDimension", "Mun dimension ID", moonDimension);
		dunaDimension = CommonConfig.createConfigInt(config, CATEGORY_DIM, "17.02_dunaDimension", "Duna dimension ID", dunaDimension);
		ikeDimension = CommonConfig.createConfigInt(config, CATEGORY_DIM, "17.03_ikeDimension", "Ike dimension ID", ikeDimension);
		eveDimension = CommonConfig.createConfigInt(config, CATEGORY_DIM, "17.04_eveDimension", "Eve dimension ID", eveDimension);
		dresDimension = CommonConfig.createConfigInt(config, CATEGORY_DIM, "17.05_dresDimension", "Dres dimension ID", dresDimension);
		mohoDimension = CommonConfig.createConfigInt(config, CATEGORY_DIM, "17.06_mohoDimension", "Moho dimension ID", mohoDimension);
		minmusDimension = CommonConfig.createConfigInt(config, CATEGORY_DIM, "17.07_minmusDimension", "Minmus dimension ID", minmusDimension);
		laytheDimension = CommonConfig.createConfigInt(config, CATEGORY_DIM, "17.08_laytheDimension", "Laythe dimension ID", laytheDimension);
		
		final String CATEGORY_GENERAL = CommonConfig.CATEGORY_GENERAL;
		maxProbeDistance = CommonConfig.createConfigInt(config, CATEGORY_GENERAL, "1.90_maxProbeDistance", "How far from the center of the dimension can probes generate landing coordinates", maxProbeDistance);

		final String CATEGORY_BIOME = CommonConfig.CATEGORY_BIOMES;
		moonBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.02_moonBiome", "Mun Biome ID", moonBiome);
		dunaBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.03_dunaBiome", "Duna Biome ID", dunaBiome);
		dunaLowlandsBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.04_dunaLowlandsBiome", "Duna Lowlands Biome ID", dunaLowlandsBiome);
		dunaPolarBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.05_dunaPolarBiome", "Duna Polar Biome ID", dunaPolarBiome);
		dunaHillsBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.06_dunaHillsBiome", "Duna Hills Biome ID", dunaHillsBiome);
		dunaPolarHillsBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.07_dunaPolarHillsBiome", "Duna Polar Hills Biome ID", dunaPolarHillsBiome);
		eveBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.08_eveBiome", "Eve Biome ID", eveBiome);
		eveMountainsBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.09_eveMountainsBiome", "Eve Mountains Biome ID", eveMountainsBiome);
		eveOceanBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.10_eveOceanBiome", "Eve Ocean Biome ID", eveOceanBiome);
		eveSeismicBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.12_eveSeismicBiome", "Eve Seismic Biome ID", eveSeismicBiome);
		ikeBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.13_ikeBiome", "Ike Biome ID", ikeBiome);
		laytheBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.14_laytheBiome", "Laythe Biome ID", laytheBiome);
		laytheOceanBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.15_laytheOceanBiome", "Laythe Ocean Biome ID", laytheOceanBiome);
		laythePolarBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.16_laythePolarBiome", "Laythe Polar Biome ID", laythePolarBiome);
		minmusBasins = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.17_minmusBasinsBiome", "Minmus Basins Biome ID", minmusBasins);
		minmusBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.18_minmusBiome", "Minmus Biome ID", minmusBiome);
		mohoBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.19_mohoBiome", "Moho Biome ID", mohoBiome);
		dresBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.20_dresBiome", "Dres Biome ID", dresBiome);
		dresBasins = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.21_dresBasinsBiome", "Dres Basins Biome ID", dresBasins);
	}

}
