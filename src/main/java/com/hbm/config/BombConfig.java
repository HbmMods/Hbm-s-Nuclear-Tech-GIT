package com.hbm.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class BombConfig {

	public static int gadgetRadius = 150;
	public static int boyRadius = 120;
	public static int manRadius = 175;
	public static int mikeRadius = 250;
	public static int tsarRadius = 500;
	public static int prototypeRadius = 150;
	public static int fleijaRadius = 50;
	public static int soliniumRadius = 150;
	public static int n2Radius = 200;
	public static int missileRadius = 100;
	public static int mirvRadius = 100;
	public static int fatmanRadius = 35;
	public static int nukaRadius = 25;
	public static int aSchrabRadius = 20;

	public static int mk5 = 50;
	public static int blastSpeed = 1024;
	public static int falloutRange = 100;
	public static int fDelay = 4;
	public static int limitExplosionLifespan = 0;
	public static boolean chunkloading = true;
	public static int explosionAlgorithm = 2;

	public static void loadFromConfig(Configuration config) {

		final String CATEGORY_NUKES = CommonConfig.CATEGORY_NUKES;
		Property propGadget = config.get(CATEGORY_NUKES, "3.00_gadgetRadius", 150);
		propGadget.comment = "Radius of the Gadget";
		gadgetRadius = propGadget.getInt();
		Property propBoy = config.get(CATEGORY_NUKES, "3.01_boyRadius", 120);
		propBoy.comment = "Radius of Little Boy";
		boyRadius = propBoy.getInt();
		Property propMan = config.get(CATEGORY_NUKES, "3.02_manRadius", 175);
		propMan.comment = "Radius of Fat Man";
		manRadius = propMan.getInt();
		Property propMike = config.get(CATEGORY_NUKES, "3.03_mikeRadius", 250);
		propMike.comment = "Radius of Ivy Mike";
		mikeRadius = propMike.getInt();
		Property propTsar = config.get(CATEGORY_NUKES, "3.04_tsarRadius", 500);
		propTsar.comment = "Radius of the Tsar Bomba";
		tsarRadius = propTsar.getInt();
		Property propPrototype = config.get(CATEGORY_NUKES, "3.05_prototypeRadius", 150);
		propPrototype.comment = "Radius of the Prototype";
		prototypeRadius = propPrototype.getInt();
		Property propFleija = config.get(CATEGORY_NUKES, "3.06_fleijaRadius", 50);
		propFleija.comment = "Radius of F.L.E.I.J.A.";
		fleijaRadius = propFleija.getInt();
		Property propMissile = config.get(CATEGORY_NUKES, "3.07_missileRadius", 100);
		propMissile.comment = "Radius of the nuclear missile";
		missileRadius = propMissile.getInt();
		Property propMirv = config.get(CATEGORY_NUKES, "3.08_mirvRadius", 100);
		propMirv.comment = "Radius of a MIRV";
		mirvRadius = propMirv.getInt();
		Property propFatman = config.get(CATEGORY_NUKES, "3.09_fatmanRadius", 35);
		propFatman.comment = "Radius of the Fatman Launcher";
		fatmanRadius = propFatman.getInt();
		Property propNuka = config.get(CATEGORY_NUKES, "3.10_nukaRadius", 25);
		propNuka.comment = "Radius of the nuka grenade";
		nukaRadius = propNuka.getInt();
		Property propASchrab = config.get(CATEGORY_NUKES, "3.11_aSchrabRadius", 20);
		propASchrab.comment = "Radius of dropped anti schrabidium";
		aSchrabRadius = propASchrab.getInt();
		Property propSolinium = config.get(CATEGORY_NUKES, "3.12_soliniumRadius", 150);
		propSolinium.comment = "Radius of the blue rinse";
		soliniumRadius = propSolinium.getInt();
		Property propN2 = config.get(CATEGORY_NUKES, "3.13_n2Radius", 200);
		propN2.comment = "Radius of the N2 mine";
		n2Radius = propN2.getInt();

		final String CATEGORY_NUKE = CommonConfig.CATEGORY_EXPLOSIONS;
		Property propLimitExplosionLifespan = config.get(CATEGORY_NUKE, "6.00_limitExplosionLifespan", 0);
		propLimitExplosionLifespan.comment = "How long an explosion can be unloaded until it dies in seconds. Based of system time. 0 disables the effect";
		limitExplosionLifespan = propLimitExplosionLifespan.getInt();
		// explosion speed
		Property propBlastSpeed = config.get(CATEGORY_NUKE, "6.01_blastSpeed", 1024);
		propBlastSpeed.comment = "Base speed of MK3 system (old and schrabidium) detonations (Blocks / tick)";
		blastSpeed = propBlastSpeed.getInt();
		// new explosion speed
		Property propFalloutRange = config.get(CATEGORY_NUKE, "6.02_mk5BlastTime", 50);
		propFalloutRange.comment = "Minimum amount of milliseconds per tick allocated for mk5 chunk processing";
		mk5 = propFalloutRange.getInt();
		// fallout range
		Property falloutRangeProp = config.get(CATEGORY_NUKE, "6.03_falloutRange", 100);
		falloutRangeProp.comment = "Radius of fallout area (base radius * value in percent)";
		falloutRange = falloutRangeProp.getInt();
		Property falloutDelayProp = config.get(CATEGORY_NUKE, "6.04_falloutDelay", 4);
		falloutDelayProp.comment = "How many ticks to wait for the next fallout chunk computation";
		fDelay = falloutDelayProp.getInt();

		chunkloading = CommonConfig.createConfigBool(config, CATEGORY_NUKE, "6.05_enableChunkLoading", "Allows all types of procedural explosions to keep the central chunk loaded and to generate new chunks.", true);
		explosionAlgorithm = CommonConfig.createConfigInt(config, CATEGORY_NUKE, "6.06_explosionAlgorithm", "Configures the algorithm of mk5 explosion. \n0 = Legacy, 1 = Threaded DDA, 2 = Threaded DDA with damage accumulation.", 2);
	}
}
