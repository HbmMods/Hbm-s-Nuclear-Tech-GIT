package com.hbm.config;

import net.minecraftforge.common.config.Configuration;

public class WorldConfig {

	public static boolean overworldOre = true;
	public static boolean netherOre = true;
	public static boolean endOre = true;

	public static int uraniumSpawn = 6;
	public static int thoriumSpawn = 7;
	public static int titaniumSpawn = 8;
	public static int sulfurSpawn = 5;
	public static int aluminiumSpawn = 7;
	public static int copperSpawn = 12;
	public static int fluoriteSpawn = 6;
	public static int niterSpawn = 6;
	public static int tungstenSpawn = 10;
	public static int leadSpawn = 6;
	public static int berylliumSpawn = 6;
	public static int ligniteSpawn = 2;
	public static int asbestosSpawn = 4;
	public static int rareSpawn = 6;
	public static int lithiumSpawn = 6;
	public static int cinnebarSpawn = 1;
	public static int gassshaleSpawn = 5;
	public static int gasbubbleSpawn = 12;
	public static int explosivebubbleSpawn = 0;
	public static int cobaltSpawn = 2;
	public static int oilSpawn = 100;
	public static int bedrockOilSpawn = 200;
	public static int meteoriteSpawn = 500;

	public static boolean newBedrockOres = true;
	public static int bedrockIronSpawn = 100;
	public static int bedrockCopperSpawn = 200;
	public static int bedrockBoraxSpawn = 50;
	public static int bedrockChlorocalciteSpawn = 35;
	public static int bedrockAsbestosSpawn = 50;
	public static int bedrockNiobiumSpawn = 50;
	public static int bedrockNeodymiumSpawn = 50;
	public static int bedrockTitaniumSpawn = 100;
	public static int bedrockTungstenSpawn = 100;
	public static int bedrockGoldSpawn = 50;
	public static int bedrockUraniumSpawn = 35;
	public static int bedrockThoriumSpawn = 50;
	public static int bedrockCoalSpawn = 200;
	public static int bedrockNiterSpawn = 50;
	public static int bedrockFluoriteSpawn = 50;
	public static int bedrockRedstoneSpawn = 50;
	public static int bedrockRareEarthSpawn = 50;
	public static int bedrockBauxiteSpawn = 100;
	public static int bedrockEmeraldSpawn = 50;
	public static int bedrockGlowstoneSpawn = 100;
	public static int bedrockPhosphorusSpawn = 50;
	public static int bedrockQuartzSpawn = 100;

	public static int ironClusterSpawn = 4;
	public static int titaniumClusterSpawn = 2;
	public static int aluminiumClusterSpawn = 3;
	public static int copperClusterSpawn = 4;
	public static int alexandriteSpawn = 100;

	public static int limestoneSpawn = 1;

	public static int netherUraniumuSpawn = 8;
	public static int netherTungstenSpawn = 10;
	public static int netherSulfurSpawn = 26;
	public static int netherPhosphorusSpawn = 24;
	public static int netherCoalSpawn = 8;
	public static int netherPlutoniumSpawn = 8;
	public static int netherCobaltSpawn = 2;

	public static int endTikiteSpawn = 8;

	public static boolean enableHematite = true;
	public static boolean enableMalachite = true;
	public static boolean enableBauxite = true;

	public static boolean enableSulfurCave = true;
	public static boolean enableAsbestosCave = true;

	public static int radioStructure = 500;
	public static int antennaStructure = 250;
	public static int atomStructure = 500;
	public static int dungeonStructure = 64;
	public static int relayStructure = 500;
	public static int satelliteStructure = 500;
	public static int factoryStructure = 1000;
	public static int dudStructure = 500;
	public static int spaceshipStructure = 1000;
	public static int barrelStructure = 5000;
	public static int geyserWater = 3000;
	public static int geyserChlorine = 3000;
	public static int geyserVapor = 500;
	public static int capsuleStructure = 100;
	public static int arcticStructure = 500;
	public static int jungleStructure = 2000;
	public static int pyramidStructure = 4000;

	public static int broadcaster = 5000;
	public static int minefreq = 64;
	public static int radfreq = 5000;
	public static int vaultfreq = 2500;

	public static boolean enableMeteorStrikes = true;
	public static boolean enableMeteorShowers = true;
	public static boolean enableMeteorTails = true;
	public static boolean enableSpecialMeteors = true;
	public static int meteorStrikeChance = 20 * 60 * 180;
	public static int meteorShowerChance = 20 * 60 * 5;
	public static int meteorShowerDuration = 6000;

	public static boolean enableCraterBiomes = true;
	public static int craterBiomeId = 80;
	public static int craterBiomeInnerId = 81;
	public static int craterBiomeOuterId = 82;
	public static float craterBiomeRad = 5F;
	public static float craterBiomeInnerRad = 25F;
	public static float craterBiomeOuterRad = 0.5F;
	public static float craterBiomeWaterMult = 5F;

	public static void loadFromConfig(Configuration config) {

		final String CATEGORY_OREGEN = CommonConfig.CATEGORY_ORES;

		overworldOre = CommonConfig.createConfigBool(config, CATEGORY_OREGEN, "2.D00_overworldOres", "General switch for whether overworld ores should be generated. Does not include special structures like oil.", true);
		netherOre = CommonConfig.createConfigBool(config, CATEGORY_OREGEN, "2.D01_netherOres", "General switch for whether nether ores should be generated.", true);
		endOre = CommonConfig.createConfigBool(config, CATEGORY_OREGEN, "2.D02_endOres", "General switch for whether end ores should be generated. Does not include special structures like trixite crystals.", true);

		uraniumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.00_uraniumSpawnrate", "Amount of uranium ore veins per chunk", 7);
		titaniumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.01_titaniumSpawnrate", "Amount of titanium ore veins per chunk", 8);
		sulfurSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.02_sulfurSpawnrate", "Amount of sulfur ore veins per chunk", 5);
		aluminiumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.03_aluminiumSpawnrate", "Amount of aluminium ore veins per chunk", 7);
		copperSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.04_copperSpawnrate", "Amount of copper ore veins per chunk", 12);
		fluoriteSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.05_fluoriteSpawnrate", "Amount of fluorite ore veins per chunk", 6);
		niterSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.06_niterSpawnrate", "Amount of niter ore veins per chunk", 6);
		tungstenSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.07_tungstenSpawnrate", "Amount of tungsten ore veins per chunk", 10);
		leadSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.08_leadSpawnrate", "Amount of lead ore veins per chunk", 6);
		berylliumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.09_berylliumSpawnrate", "Amount of beryllium ore veins per chunk", 6);
		thoriumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.10_thoriumSpawnrate", "Amount of thorium ore veins per chunk", 7);
		ligniteSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.11_ligniteSpawnrate", "Amount of lignite ore veins per chunk", 2);
		asbestosSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.12_asbestosSpawnRate", "Amount of asbestos ore veins per chunk", 2);
		lithiumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.13_lithiumSpawnRate", "Amount of schist lithium ore veins per chunk", 6);
		rareSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.14_rareEarthSpawnRate", "Amount of rare earth ore veins per chunk", 6);
		gassshaleSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.16_gasShaleSpawnRate", "Amount of oil shale veins per chunk", 5);
		gasbubbleSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.17_gasBubbleSpawnRate", "Spawns a gas bubble every nTH chunk", 12);
		cinnebarSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.18_cinnebarSpawnRate", "Amount of cinnebar ore veins per chunk", 1);
		cobaltSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.18_cobaltSpawnRate", "Amount of cobalt ore veins per chunk", 2);
		explosivebubbleSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.19_explosiveBubbleSpawnRate", "Spawns an explosive gas bubble every nTH chunk", 0);
		alexandriteSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.20_alexandriteSpawnRate", "Spawns an alexandrite vein every nTH chunk", 100);
		oilSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.21_oilSpawnRate", "Spawns an oil bubble every nTH chunk", 100);
		bedrockOilSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.22_bedrockOilSpawnRate", "Spawns a bedrock oil node every nTH chunk", 200);
		meteoriteSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.23_meteoriteSpawnRate", "Spawns a fallen meteorite every nTH chunk", 200);

		newBedrockOres = CommonConfig.createConfigBool(config, CATEGORY_OREGEN, "2.NB_newBedrockOres", "Enables the newer genreric bedrock ores", true);
		bedrockIronSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B00_bedrockIronWeight", "Spawn weight for iron bedrock ore", 100);
		bedrockCopperSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B01_bedrockCopperWeight", "Spawn weight for copper bedrock ore", 200);
		bedrockBoraxSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B02_bedrockBoraxWeight", "Spawn weight for borax bedrock ore", 50);
		bedrockAsbestosSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B03_bedrockAsbestosWeight", "Spawn weight for asbestos bedrock ore", 50);
		bedrockNiobiumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B04_bedrockNiobiumWeight", "Spawn weight for niobium bedrock ore", 50);
		bedrockTitaniumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B05_bedrockTitaniumWeight", "Spawn weight for titanium bedrock ore", 100);
		bedrockTungstenSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B06_bedrockTungstenWeight", "Spawn weight for tungsten bedrock ore", 100);
		bedrockGoldSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B07_bedrockGoldWeight", "Spawn weight for gold bedrock ore", 50);
		bedrockUraniumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B08_bedrockUraniumWeight", "Spawn weight for uranium bedrock ore", 35);
		bedrockThoriumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B09_bedrockThoriumWeight", "Spawn weight for thorium bedrock ore", 50);
		bedrockCoalSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B10_bedrockCoalWeight", "Spawn weight for coal bedrock ore", 200);
		bedrockNiterSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B11_bedrockNiterWeight", "Spawn weight for niter bedrock ore", 50);
		bedrockFluoriteSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B12_bedrockFluoriteWeight", "Spawn weight for fluorite bedrock ore", 50);
		bedrockRedstoneSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B13_bedrockRedstoneWeight", "Spawn weight for redstone bedrock ore", 50);
		bedrockChlorocalciteSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B14_bedrockChlorocalciteWeight", "Spawn weight for chlorocalcite bedrock ore", 35);
		bedrockNeodymiumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B15_bedrockNeodymiumWeight", "Spawn weight for neodymium bedrock ore", 50);
		bedrockRareEarthSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B16_bedrockRareEarthWeight", "Spawn weight for rare earth bedrock ore", 50);
		bedrockBauxiteSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B17_bedrockBauxiteWeight", "Spawn weight for bauxite bedrock ore", 100);
		bedrockEmeraldSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.B18_bedrockEmeraldWeight", "Spawn weight for emerald bedrock ore", 50);

		bedrockGlowstoneSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.BN00_bedrockGlowstoneWeight", "Spawn weight for glowstone bedrock ore", 100);
		bedrockPhosphorusSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.BN01_bedrockPhosphorusWeight", "Spawn weight for phosphorus bedrock ore", 50);
		bedrockQuartzSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.BN01_bedrockQuartzWeight", "Spawn weight for quartz bedrock ore", 100);

		ironClusterSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.C00_ironClusterSpawn", "Amount of iron cluster veins per chunk", 4);
		titaniumClusterSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.C01_titaniumClusterSpawn", "Amount of titanium cluster veins per chunk", 2);
		aluminiumClusterSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.C02_aluminiumClusterSpawn", "Amount of aluminium cluster veins per chunk", 3);
		copperClusterSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.C03_copperClusterSpawn", "Amount of copper cluster veins per chunk", 4);

		limestoneSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.L02_limestoneSpawn", "Amount of limestone block veins per chunk", 1);

		netherUraniumuSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.N00_uraniumSpawnrate", "Amount of nether uranium per chunk", 8);
		netherTungstenSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.N01_tungstenSpawnrate", "Amount of nether tungsten per chunk", 10);
		netherSulfurSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.N02_sulfurSpawnrate", "Amount of nether sulfur per chunk", 26);
		netherPhosphorusSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.N03_phosphorusSpawnrate", "Amount of nether phosphorus per chunk", 24);
		netherCoalSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.N04_coalSpawnrate", "Amount of nether coal per chunk", 8);
		netherPlutoniumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.N05_plutoniumSpawnrate", "Amount of nether plutonium per chunk, if enabled", 8);
		netherCobaltSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.N06_cobaltSpawnrate", "Amount of nether cobalt per chunk", 2);

		endTikiteSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.E00_tikiteSpawnrate", "Amount of end trixite per chunk", 8);

		enableHematite = CommonConfig.createConfigBool(config, CATEGORY_OREGEN, "2.L00_enableHematite", "Toggles hematite deposits", true);
		enableMalachite = CommonConfig.createConfigBool(config, CATEGORY_OREGEN, "2.L01_enableMalachite", "Toggles malachite deposits", true);
		enableBauxite = CommonConfig.createConfigBool(config, CATEGORY_OREGEN, "2.L02_enableBauxite", "Toggles bauxite deposits", true);

		enableSulfurCave = CommonConfig.createConfigBool(config, CATEGORY_OREGEN, "2.C00_enableSulfurCave", "Toggles sulfur caves", true);
		enableAsbestosCave = CommonConfig.createConfigBool(config, CATEGORY_OREGEN, "2.C01_enableAsbestosCave", "Toggles asbestos caves", true);

		final String CATEGORY_DUNGEON = CommonConfig.CATEGORY_DUNGEONS;
		radioStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.00_radioSpawn", "Spawn radio station on every nTH chunk", 500);
		antennaStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.01_antennaSpawn", "Spawn antenna on every nTH chunk", 250);
		atomStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.02_atomSpawn", "Spawn power plant on every nTH chunk", 500);
		dungeonStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.04_dungeonSpawn", "Spawn library dungeon on every nTH chunk", 64);
		relayStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.05_relaySpawn", "Spawn relay on every nTH chunk", 500);
		satelliteStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.06_satelliteSpawn", "Spawn satellite dish on every nTH chunk", 500);
		factoryStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.09_factorySpawn", "Spawn factory on every nTH chunk", 1000);
		dudStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.10_dudSpawn", "Spawn dud on every nTH chunk", 500);
		spaceshipStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.11_spaceshipSpawn", "Spawn spaceship on every nTH chunk", 1000);
		barrelStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.12_barrelSpawn", "Spawn waste tank on every nTH chunk", 5000);
		broadcaster = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.13_broadcasterSpawn", "Spawn corrupt broadcaster on every nTH chunk", 5000);
		minefreq = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.14_landmineSpawn", "Spawn AP landmine on every nTH chunk", 64);
		radfreq = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.15_radHotspotSpawn", "Spawn radiation hotspot on every nTH chunk", 5000);
		vaultfreq = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.16_vaultSpawn", "Spawn locked safe on every nTH chunk", 2500);
		geyserWater = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.17_geyserWaterSpawn", "Spawn water geyser on every nTH chunk", 3000);
		geyserChlorine = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.18_geyserChlorineSpawn", "Spawn poison geyser on every nTH chunk", 3000);
		geyserVapor = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.19_geyserVaporSpawn", "Spawn vapor geyser on every nTH chunk", 500);
		capsuleStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.21_capsuleSpawn", "Spawn landing capsule on every nTH chunk", 100);
		arcticStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.22_arcticVaultSpawn", "Spawn arctic code vault on every nTH chunk", 500);
		jungleStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.23_jungleDungeonSpawn", "Spawn jungle dungeon on every nTH chunk", 2000);
		pyramidStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.24_pyramidSpawn", "Spawn pyramid on every nTH chunk", 4000);

		final String CATEGORY_METEOR = CommonConfig.CATEGORY_METEORS;
		enableMeteorStrikes = CommonConfig.createConfigBool(config, CATEGORY_METEOR, "5.00_enableMeteorStrikes", "Toggles the spawning of meteors", true);
		enableMeteorShowers = CommonConfig.createConfigBool(config, CATEGORY_METEOR, "5.01_enableMeteorShowers", "Toggles meteor showers, which start with a 1% chance for every spawned meteor", true);
		enableMeteorTails = CommonConfig.createConfigBool(config, CATEGORY_METEOR, "5.02_enableMeteorTails", "Toggles the particle effect created by falling meteors", true);
		enableSpecialMeteors = CommonConfig.createConfigBool(config, CATEGORY_METEOR, "5.03_enableSpecialMeteors", "Toggles rare, special meteor types with different impact effects", true);
		meteorStrikeChance = CommonConfig.createConfigInt(config, CATEGORY_METEOR, "5.03_meteorStrikeChance", "The probability of a meteor spawning (an average of once every nTH ticks)", 20 * 60 * 60 * 5);
		meteorShowerChance = CommonConfig.createConfigInt(config, CATEGORY_METEOR, "5.04_meteorShowerChance", "The probability of a meteor spawning during meteor shower (an average of once every nTH ticks)", 20 * 60 * 15);
		meteorShowerDuration = CommonConfig.createConfigInt(config, CATEGORY_METEOR, "5.05_meteorShowerDuration", "Max duration of meteor shower in ticks", 20 * 60 * 30);

		final String CATEGORY_BIOMES = CommonConfig.CATEGORY_BIOMES;
		enableCraterBiomes = CommonConfig.createConfigBool(config, CATEGORY_BIOMES, "17.B_toggle", "Enables the biome change caused by nuclear explosions", true);
		craterBiomeId = CommonConfig.createConfigInt(config, CATEGORY_BIOMES, "17.B00_craterBiomeId", "The numeric ID for the crater biome", 80);
		craterBiomeInnerId = CommonConfig.createConfigInt(config, CATEGORY_BIOMES, "17.B01_craterBiomeInnerId", "The numeric ID for the inner crater biome", 81);
		craterBiomeOuterId = CommonConfig.createConfigInt(config, CATEGORY_BIOMES, "17.B02_craterBiomeOuterId", "The numeric ID for the outer crater biome", 82);
		craterBiomeRad = (float) CommonConfig.createConfigDouble(config, CATEGORY_BIOMES, "17.R00_craterBiomeRad", "RAD/s for the crater biome", 5D);
		craterBiomeInnerRad = (float) CommonConfig.createConfigDouble(config, CATEGORY_BIOMES, "17.R01_craterBiomeInnerRad", "RAD/s for the inner crater biome", 25D);
		craterBiomeOuterRad = (float) CommonConfig.createConfigDouble(config, CATEGORY_BIOMES, "17.R02_craterBiomeOuterRad", "RAD/s for the outer crater biome", 0.5D);
		craterBiomeWaterMult = (float) CommonConfig.createConfigDouble(config, CATEGORY_BIOMES, "17.R03_craterBiomeWaterMult", "Multiplier for RAD/s in crater biomes when in water", 5D);

		radioStructure = CommonConfig.setDefZero(radioStructure, 1000);
		antennaStructure = CommonConfig.setDefZero(antennaStructure, 1000);
		atomStructure = CommonConfig.setDefZero(atomStructure, 1000);
		dungeonStructure = CommonConfig.setDefZero(dungeonStructure, 1000);
		relayStructure = CommonConfig.setDefZero(relayStructure, 1000);
		satelliteStructure = CommonConfig.setDefZero(satelliteStructure, 1000);
		factoryStructure = CommonConfig.setDefZero(factoryStructure, 1000);
		dudStructure = CommonConfig.setDefZero(dudStructure, 1000);
		spaceshipStructure = CommonConfig.setDefZero(spaceshipStructure, 1000);
		barrelStructure = CommonConfig.setDefZero(barrelStructure, 1000);
		geyserWater = CommonConfig.setDefZero(geyserWater, 1000);
		geyserChlorine = CommonConfig.setDefZero(geyserChlorine, 1000);
		geyserVapor = CommonConfig.setDefZero(geyserVapor, 1000);
		broadcaster = CommonConfig.setDefZero(broadcaster, 1000);
		minefreq = CommonConfig.setDefZero(minefreq, 1000);
		radfreq = CommonConfig.setDefZero(radfreq, 1000);
		vaultfreq = CommonConfig.setDefZero(vaultfreq, 1000);
		jungleStructure = CommonConfig.setDefZero(jungleStructure, 1000);
		capsuleStructure = CommonConfig.setDefZero(capsuleStructure, 100);
		arcticStructure = CommonConfig.setDefZero(arcticStructure, 500);

		meteorStrikeChance = CommonConfig.setDef(meteorStrikeChance, 1000);
		meteorShowerChance = CommonConfig.setDef(meteorShowerChance, 1000);
	}

}
