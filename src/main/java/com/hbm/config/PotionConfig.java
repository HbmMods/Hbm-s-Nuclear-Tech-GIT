package com.hbm.config;

import net.minecraftforge.common.config.Configuration;

public class PotionConfig {

	public static int taintID = 62;
	public static int radiationID = 63;
	public static int bangID = 64;
	public static int mutationID = 65;
	public static int radxID = 66;
	public static int leadID = 67;
	public static int radawayID = 68;
	public static int telekinesisID = 69;
	public static int phosphorusID = 70;
	public static int stabilityID = 71;
	public static int potionsicknessID = 72;
	
	public static int potionSickness = 0;
	
	public static void loadFromConfig(Configuration config) {

		final String CATEGORY_POTION = "08_potion_effects";
		taintID = CommonConfig.createConfigInt(config, CATEGORY_POTION, "8.00_taintPotionID", "What potion ID the taint effect will have", 62);
		radiationID = CommonConfig.createConfigInt(config, CATEGORY_POTION, "8.01_radiationPotionID", "What potion ID the radiation effect will have", 63);
		bangID = CommonConfig.createConfigInt(config, CATEGORY_POTION, "8.02_bangPotionID", "What potion ID the B93 timebomb effect will have", 64);
		mutationID = CommonConfig.createConfigInt(config, CATEGORY_POTION, "8.03_mutationPotionID", "What potion ID the taint mutation effect will have", 65);
		radxID = CommonConfig.createConfigInt(config, CATEGORY_POTION, "8.04_radxPotionID", "What potion ID the Rad-X effect will have", 66);
		leadID = CommonConfig.createConfigInt(config, CATEGORY_POTION, "8.05_leadPotionID", "What potion ID the lead poisoning effect will have", 67);
		radawayID = CommonConfig.createConfigInt(config, CATEGORY_POTION, "8.06_radawayPotionID", "What potion ID the radaway effect will have", 68);
		telekinesisID = CommonConfig.createConfigInt(config, CATEGORY_POTION, "8.07_telekinesisPotionID", "What potion ID the telekinesis effect will have", 69);
		phosphorusID = CommonConfig.createConfigInt(config, CATEGORY_POTION, "8.08_phosphorusPotionID", "What potion ID the phosphorus effect will have", 70);
		stabilityID = CommonConfig.createConfigInt(config, CATEGORY_POTION, "8.09_stabilityPotionID", "What potion ID the stability effect will have", 71);
		potionsicknessID = CommonConfig.createConfigInt(config, CATEGORY_POTION, "8.10_potionsicknessID", "What potion ID the potion sickness effect will have", 72);

		String s = CommonConfig.createConfigString(config, CATEGORY_POTION, "8.S0_potionSickness", "Valid configs include \"NORMAL\" and \"TERRARIA\", otherwise potion sickness is turned off", "OFF");

		if("normal".equals(s.toLowerCase()))
			potionSickness = 1;
		if("terraria".equals(s.toLowerCase()))
			potionSickness = 2;
		
	}
}
