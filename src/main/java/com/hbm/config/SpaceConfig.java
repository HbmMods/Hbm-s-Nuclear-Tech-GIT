package com.hbm.config;

import net.minecraftforge.common.config.Configuration;

public class SpaceConfig {


	public static int dunaoilSpawn = 100;

	public static int moonDimension = 15;
	public static int dunaDimension = 16;
	public static int ikeDimension = 17;
	public static int eveDimension = 18;
	public static int mohoDimension = 20;
	public static int minmusDimension = 21;
	public static int laytheDimension = 22;

	public static int moonBiome = 111;
	public static int dunaBiome = 112;
	public static int dunaLowlandsBiome = 113;
	public static int dunaPolarBiome = 114;
	public static int dunaHillsBiome = 115;
	public static int dunaPolarHillsBiome = 116;
	public static int eveBiome = 117;
	public static int eveMountainsBiome = 118;
	public static int eveOceanBiome = 119;
	public static int laytheBiome = 123;
	public static int laytheOceanBiome = 124;
	public static int ikeBiome = 145;
	public static int ikecfreq = 90;
	public static int drescfreq = 90;

	
	public static int DresBiome = 120;
	public static int dreBasins = 121;
	public static int dresDimension = 19;
	
	public static int mohoBiome = 122;
	
	public static int minmusBiome = 146;
	public static int minmusBasins = 147;
	
	public static void loadFromConfig(Configuration config) {

	
		final String CATEGORY_DIM = CommonConfig.CATEGORY_DIMS;
		moonDimension = CommonConfig.createConfigInt(config, CATEGORY_DIM, "16.00_moonDimension", "Mun Dimension ID", 15);
		dunaDimension = CommonConfig.createConfigInt(config, CATEGORY_DIM, "16.01_dunaDimension", "Duna Dimension ID", 16);
		ikeDimension = CommonConfig.createConfigInt(config, CATEGORY_DIM, "16.01_ikeDimension", "Ike Dimension ID", 17);
		eveDimension = CommonConfig.createConfigInt(config, CATEGORY_DIM, "16.01_eveDimension", "Eve Dimension ID", 18);
		dresDimension = CommonConfig.createConfigInt(config, CATEGORY_DIM, "16.01_dresDimension", "Dres Dimension ID", 19);
		final String CATEGORY_BIOME = CommonConfig.CATEGORY_BIOMES;
		moonBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.02_moonBiome", "Mun Biome ID", 111);
		dunaBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.03_dunaBiome", "Duna Biome ID", 112);
		dunaLowlandsBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.04_dunaLowlandsBiome", "Duna Lowlands Biome ID", 113);
		dunaPolarBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.05_dunaPolarBiome", "Duna Polar Biome ID", 114);
		dunaHillsBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.06_dunaHillsBiome", "Duna Hills Biome ID", 115);
		dunaPolarHillsBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.07_dunaPolarHillsBiome", "Duna Polar Hills Biome ID", 116);
		eveBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.08_eveBiome", "Eve Biome ID", 117);
		eveMountainsBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.09_eveMountainsBiome", "Eve Mountains Biome ID", 118);
		eveOceanBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.10_eveOceanBiome", "Eve Ocean Biome ID", 119);
		ikeBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.11_IkeBiome", "Ike Biome ID", 145);
		laytheBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.12_LaytheBiome", "Laythe Biome ID", 123);
		laytheOceanBiome = CommonConfig.createConfigInt(config, CATEGORY_BIOME, "16.13_LaytheOceanBiome", "Laythe Ocean Biome ID", 124);
		
		
		ikecfreq = CommonConfig.setDefZero(ikecfreq, 90);
		drescfreq = CommonConfig.setDefZero(drescfreq, 90);

		
	}

}
