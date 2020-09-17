package com.hbm.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class WorldConfig {

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
	public static int asbestosSpawn = 2;
	public static int cobaltSpawn = 5;

	public static int radioStructure = 500;
	public static int antennaStructure = 250;
	public static int atomStructure = 500;
	public static int vertibirdStructure = 500;
	public static int dungeonStructure = 64;
	public static int relayStructure = 500;
	public static int satelliteStructure = 500;
	public static int bunkerStructure = 1000;
	public static int siloStructure = 1000;
	public static int factoryStructure = 1000;
	public static int dudStructure = 500;
	public static int spaceshipStructure = 1000;
	public static int barrelStructure = 5000;
	public static int geyserWater = 3000;
	public static int geyserChlorine = 3000;
	public static int geyserVapor = 500;
	public static int meteorStructure = 15000;
	public static int capsuleStructure = 100;

	public static int broadcaster = 5000;
	public static int minefreq = 64;
	public static int radfreq = 5000;
	public static int vaultfreq = 2500;

	public static int meteorStrikeChance = 20 * 60 * 180;
	public static int meteorShowerChance = 20 * 60 * 5;
	public static int meteorShowerDuration = 6000;
	
	public static void loadFromConfig(Configuration config) {

		final String CATEGORY_OREGEN = "02_ores";
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
		cobaltSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.13_cobaltSpawnrate", "Amount of cobalt ore veins per chunk", 3);

		final String CATEGORY_DUNGEON = "04_dungeons";
		radioStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.00_radioSpawn", "Spawn radio station on every nTH chunk", 500);
		antennaStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.01_antennaSpawn", "Spawn antenna on every nTH chunk", 250);
		atomStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.02_atomSpawn", "Spawn power plant on every nTH chunk", 500);
		vertibirdStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.03_vertibirdSpawn", "Spawn vertibird on every nTH chunk", 500);
		dungeonStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.04_dungeonSpawn", "Spawn library dungeon on every nTH chunk", 64);
		relayStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.05_relaySpawn", "Spawn relay on every nTH chunk", 500);
		satelliteStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.06_satelliteSpawn", "Spawn satellite dish on every nTH chunk", 500);
		bunkerStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.07_bunkerSpawn", "Spawn bunker on every nTH chunk", 1000);
		siloStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.08_siloSpawn", "Spawn missile silo on every nTH chunk", 1000);
		factoryStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.09_factorySpawn", "Spawn factory on every nTH chunk", 1000);
		dudStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.10_dudSpawn", "Spawn dud on every nTH chunk", 500);
		spaceshipStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.11_spaceshipSpawn", "Spawn spaceship on every nTH chunk", 1000);
		barrelStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.12_barrelSpawn", "Spawn waste tank on every nTH chunk", 5000);
		broadcaster = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.13_broadcasterSpawn", "Spawn corrupt broadcaster on every nTH chunk", 5000);
		minefreq = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.14_landmineSpawn", "Spawn AP landmine on every nTH chunk", 64);
		radfreq = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.15_radHotsoptSpawn", "Spawn radiation hotspot on every nTH chunk", 5000);
		vaultfreq = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.16_vaultSpawn", "Spawn locked safe on every nTH chunk", 2500);
		geyserWater = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.17_geyserWaterSpawn", "Spawn water geyser on every nTH chunk", 3000);
		geyserChlorine = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.18_geyserChlorineSpawn", "Spawn poison geyser on every nTH chunk", 3000);
		geyserVapor = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.19_geyserVaporSpawn", "Spawn vapor geyser on every nTH chunk", 500);
		meteorStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.20_meteorSpawn", "Spawn meteor dungeon on every nTH chunk", 15000);
		capsuleStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.21_capsuleSpawn", "Spawn landing capsule on every nTH chunk", 100);

		final String CATEGORY_METEOR = "05_meteors";
		Property propMeteorStrikeChance = config.get(CATEGORY_METEOR, "5.00_meteorStrikeChance", 20 * 60 * 60 * 5);
		propMeteorStrikeChance.comment = "The probability of a meteor spawning (an average of once every nTH ticks)";
		meteorStrikeChance = propMeteorStrikeChance.getInt();
		Property propMeteorShowerChance = config.get(CATEGORY_METEOR, "5.01_meteorShowerChance", 20 * 60 * 15);
		propMeteorShowerChance.comment = "The probability of a meteor spawning during meteor shower (an average of once every nTH ticks)";
		meteorShowerChance = propMeteorShowerChance.getInt();
		Property propMeteorShowerDuration = config.get(CATEGORY_METEOR, "5.02_meteorShowerDuration", 20 * 60 * 30);
		propMeteorShowerDuration.comment = "Max duration of meteor shower in ticks";
		meteorShowerDuration = propMeteorShowerDuration.getInt();

		radioStructure = CommonConfig.setDef(radioStructure, 1000);
		antennaStructure = CommonConfig.setDef(antennaStructure, 1000);
		atomStructure = CommonConfig.setDef(atomStructure, 1000);
		vertibirdStructure = CommonConfig.setDef(vertibirdStructure, 1000);
		dungeonStructure = CommonConfig.setDef(dungeonStructure, 1000);
		relayStructure = CommonConfig.setDef(relayStructure, 1000);
		satelliteStructure = CommonConfig.setDef(satelliteStructure, 1000);
		bunkerStructure = CommonConfig.setDef(bunkerStructure, 1000);
		siloStructure = CommonConfig.setDef(siloStructure, 1000);
		factoryStructure = CommonConfig.setDef(factoryStructure, 1000);
		dudStructure = CommonConfig.setDef(dudStructure, 1000);
		spaceshipStructure = CommonConfig.setDef(spaceshipStructure, 1000);
		barrelStructure = CommonConfig.setDef(barrelStructure, 1000);
		geyserWater = CommonConfig.setDef(geyserWater, 1000);
		geyserChlorine = CommonConfig.setDef(geyserChlorine, 1000);
		geyserVapor = CommonConfig.setDef(geyserVapor, 1000);
		broadcaster = CommonConfig.setDef(broadcaster, 1000);
		minefreq = CommonConfig.setDef(minefreq, 1000);
		radfreq = CommonConfig.setDef(radfreq, 1000);
		vaultfreq = CommonConfig.setDef(vaultfreq, 1000);
		meteorStructure = CommonConfig.setDef(meteorStructure, 15000);
		capsuleStructure = CommonConfig.setDef(capsuleStructure, 100);
		
		meteorStrikeChance = CommonConfig.setDef(meteorStrikeChance, 1000);
		meteorShowerChance = CommonConfig.setDef(meteorShowerChance, 1000);
	}

}
