package com.hbm.lib;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.AmmoHandGrenade;
import com.hbm.items.machine.ItemBreedingRod.BreedingRodType;
import com.hbm.items.weapon.ItemAmmo.AmmoItemTrait;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
/** Collection for commonly used data **/
public final class HbmCollection
{
	private HbmCollection() throws InstantiationException
	{
		throw new InstantiationException("Class cannot be instantiated!");
	}
	public static final char alpha = '\u03b1';
	public static final char beta = '\u03b2';
	public static final char gamma = '\u03b3';
	public static final char eta = '\u03b7';
	public static final char delta = '\u03b4';
	public static final char theta = '\u03b8';
	public static final char digamma = '\u03dc';
	public static final char radiation = '\u2622';
	public static final char bio = '\u2623';
	/// ARMORS
	/** HAZMAT TIERS **/
	public static Item[][] hazmats;
	/** T45 **/
	public static Item[] t45;
	/** LUNAR POWER ARMOR **/
	public static Item[] lunar;
	/// FINDABLE KITS
	/** STARTER NUKE KIT **/
	public static ItemStack[] nukeStarterKit;
	/** ADVANCED NUKE KIT **/
	public static ItemStack[] nukeAdvancedKit;
	/** COMMERCIAL NUKE KIT **/
	public static ItemStack[] nukeCommercialKit;
	/** ELECTRICAL KIT **/
	public static ItemStack[] electricKit;
	/** GADGET KIT **/
	public static ItemStack[] gadgetKit;
	/** LITTLE BOY KIT **/
	public static ItemStack[] boyKit;
	/** FAT MAN KIT **/
	public static ItemStack[] manKit;
	/** IVY MIKE KIT **/
	public static ItemStack[] mikeKit;
	/** TSAR BOMBA KIT **/
	public static ItemStack[] tsarKit;
	/** MULTI-PURPOSE BOMB KIT **/
	public static ItemStack[] multiKit;
	/** CUSTOM NUKE KIT **/
	public static ItemStack[] customKit;
	/** GRENADE KIT **/
	public static ItemStack[] grenadeKit;
	/** F.L.E.I.J.A. KIT **/
	public static ItemStack[] fleijaKit;
	/** BLUE RINSE KIT **/
	public static ItemStack[] soliniumKit;
	/** PROTOTYPE KIT **/
	public static ItemStack[] prototypeKit;
	/** MISSILE KIT **/
	public static ItemStack[] missileKit;
	/** T45 POWER ARMOR KIT **/
	public static ItemStack[] t45Kit;
	/** EUPHEMIUM KIT **/
	public static ItemStack[] euphemiumKit;
	/** FLOPPY DISC 10-PACK **/
	public static ItemStack[] fddPack = new ItemStack[10];
	/** COMPACT DISC 8-PACK **/
	public static ItemStack[] cdPack = new ItemStack[8];
	/** LUNAR POWER ARMOR KIT **/
	public static ItemStack[] lunarKit;
	/// COMMON AMMO TRAITS
	public static final Set<AmmoItemTrait> APType = ImmutableSet.of(AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.CON_WEAR);
	public static final Set<AmmoItemTrait> FlechetteType = ImmutableSet.of(AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_LESS_BOUNCY, AmmoItemTrait.CON_WEAR);
	public static final Set<AmmoItemTrait> IncendiaryType = ImmutableSet.of(AmmoItemTrait.PRO_INCENDIARY, AmmoItemTrait.CON_WEAR);
	public static final Set<AmmoItemTrait> PhosphorusType = ImmutableSet.of(AmmoItemTrait.PRO_PHOSPHORUS, AmmoItemTrait.NEU_WARCRIME1, AmmoItemTrait.CON_WEAR, AmmoItemTrait.CON_PENETRATION);
	public static final Set<AmmoItemTrait> PhosphorusTypeSpecial = ImmutableSet.of(AmmoItemTrait.PRO_PHOSPHORUS_SPLASH, AmmoItemTrait.NEU_WARCRIME1, AmmoItemTrait.CON_WEAR);
	public static final Set<AmmoItemTrait> ExplosiveType = ImmutableSet.of(AmmoItemTrait.PRO_EXPLOSIVE, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.CON_HEAVY_WEAR);
	public static final Set<AmmoItemTrait> DUType = ImmutableSet.of(AmmoItemTrait.PRO_HEAVY_DAMAGE, AmmoItemTrait.NEU_HEAVY_METAL, AmmoItemTrait.CON_HEAVY_WEAR);
	public static final Set<AmmoItemTrait> StarmetalType = ImmutableSet.of(AmmoItemTrait.PRO_HEAVY_DAMAGE, AmmoItemTrait.NEU_STARMETAL, AmmoItemTrait.CON_HEAVY_WEAR);
	public static final Set<AmmoItemTrait> ChlorophyteType = ImmutableSet.of(AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_WEAR, AmmoItemTrait.NEU_CHLOROPHYTE, AmmoItemTrait.NEU_HOMING, AmmoItemTrait.CON_PENETRATION);
	
	public static final int tickDay = 48000;
	public static final int tickYear = tickDay * 100;
	
	public static final World worldObj = Minecraft.getMinecraft().theWorld;
	/// COMMON NUKE TYPES
	public static final EntityNukeExplosionMK3 folkvangrField = new EntityNukeExplosionMK3(worldObj);
	public static final EntityBalefire balefire = new EntityBalefire(worldObj);
	static
	{
		folkvangrField.destructionRange = BombConfig.aSchrabRadius;
		folkvangrField.speed = 25;
		folkvangrField.coefficient = 1.0F;
		folkvangrField.waste = false;
	}
	
	/// COMMON NUKE CLOUDS
	public static final EntityCloudFleija folkvangrEffect = new EntityCloudFleija(worldObj, BombConfig.aSchrabRadius);
	
	/// BULLET COLLECTIONS
	// SHOTGUNS
	/** 12 GAUGE **/
	public static final List<Integer> twelveGauge = ImmutableList.of(BulletConfigSyncingUtil.G12_NORMAL, BulletConfigSyncingUtil.G12_INCENDIARY, BulletConfigSyncingUtil.G12_SHRAPNEL, BulletConfigSyncingUtil.G12_DU, BulletConfigSyncingUtil.G12_AM, BulletConfigSyncingUtil.G12_SLEEK);
	/** 20 GAUGE **/
	public static final List<Integer> twentyGauge = ImmutableList.of(BulletConfigSyncingUtil.G20_NORMAL, BulletConfigSyncingUtil.G20_SLUG, BulletConfigSyncingUtil.G20_FLECHETTE, BulletConfigSyncingUtil.G20_FIRE, BulletConfigSyncingUtil.G20_SHRAPNEL, BulletConfigSyncingUtil.G20_EXPLOSIVE, BulletConfigSyncingUtil.G20_CAUSTIC, BulletConfigSyncingUtil.G20_SHOCK, BulletConfigSyncingUtil.G20_WITHER, BulletConfigSyncingUtil.G20_SLEEK);
	/** 4 GAUGE **/
	public static final List<Integer> fourGauge = ImmutableList.of(BulletConfigSyncingUtil.G4_NORMAL, BulletConfigSyncingUtil.G4_SLUG, BulletConfigSyncingUtil.G4_FLECHETTE, BulletConfigSyncingUtil.G4_FLECHETTE_PHOSPHORUS, BulletConfigSyncingUtil.G4_EXPLOSIVE, BulletConfigSyncingUtil.G4_SEMTEX, BulletConfigSyncingUtil.G4_BALEFIRE, BulletConfigSyncingUtil.G4_KAMPF, BulletConfigSyncingUtil.G4_CANISTER, BulletConfigSyncingUtil.G4_CLAW, BulletConfigSyncingUtil.G4_VAMPIRE, BulletConfigSyncingUtil.G4_VOID, BulletConfigSyncingUtil.G4_TITAN, BulletConfigSyncingUtil.G4_SLEEK);
	// PISTOL CALIBER
	/** .22 LONG RIFLE **/
	public static final List<Integer> twentyTwoLR = ImmutableList.of(BulletConfigSyncingUtil.LR22_NORMAL_FIRE, BulletConfigSyncingUtil.LR22_AP_FIRE, BulletConfigSyncingUtil.CHL_LR22_FIRE);
	/** .44 MAGNUM (BASIC) **/
	public static final List<Integer> fourtyFourMagBasic = ImmutableList.of(BulletConfigSyncingUtil.M44_NORMAL, BulletConfigSyncingUtil.M44_AP, BulletConfigSyncingUtil.M44_DU, BulletConfigSyncingUtil.M44_PHOSPHORUS, BulletConfigSyncingUtil.M44_STAR, BulletConfigSyncingUtil.CHL_M44, BulletConfigSyncingUtil.M44_ROCKET);
	/** .44 MAGNUM (ALL) **/
	public static final List<Integer> fourtyFourMagAll = ImmutableList.of(BulletConfigSyncingUtil.M44_NORMAL, BulletConfigSyncingUtil.M44_AP, BulletConfigSyncingUtil.M44_DU, BulletConfigSyncingUtil.M44_PHOSPHORUS, BulletConfigSyncingUtil.M44_STAR, BulletConfigSyncingUtil.CHL_M44, BulletConfigSyncingUtil.M44_ROCKET, BulletConfigSyncingUtil.M44_PIP, BulletConfigSyncingUtil.M44_BJ, BulletConfigSyncingUtil.M44_SILVER);
	/** .50 ACTION EXPRESS **/
	public static final List<Integer> fiftyAE = ImmutableList.of(BulletConfigSyncingUtil.AE50_NORMAL, BulletConfigSyncingUtil.AE50_AP, BulletConfigSyncingUtil.AE50_DU, BulletConfigSyncingUtil.AE50_STAR, BulletConfigSyncingUtil.CHL_AE50);
	/** 9MM Parabellum **/
	public static final List<Integer> nineMM = ImmutableList.of(BulletConfigSyncingUtil.P9_NORMAL, BulletConfigSyncingUtil.P9_AP, BulletConfigSyncingUtil.P9_DU, BulletConfigSyncingUtil.CHL_P9, BulletConfigSyncingUtil.P9_ROCKET);
	/** .45 AUTOMATIC COLT PISTOL **/
	public static final List<Integer> fourtyFiveACP = ImmutableList.of(BulletConfigSyncingUtil.ACP_45);
	// RIFLE CALIBER
	/** .50 BROWNING MACHINE GUN **/
	public static final List<Integer> fiftyBMG = ImmutableList.of(BulletConfigSyncingUtil.BMG50_NORMAL, BulletConfigSyncingUtil.BMG50_INCENDIARY, BulletConfigSyncingUtil.BMG50_PHOSPHORUS, BulletConfigSyncingUtil.BMG50_EXPLOSIVE, BulletConfigSyncingUtil.BMG50_AP, BulletConfigSyncingUtil.BMG50_DU, BulletConfigSyncingUtil.BMG50_STAR, BulletConfigSyncingUtil.CHL_BMG50, BulletConfigSyncingUtil.BMG50_SLEEK);
	/** .50 BROWNING MACHINE GUN (FLECHETTE) **/
	public static final List<Integer> fiftyBMGFlechette = ImmutableList.of(BulletConfigSyncingUtil.BMG50_FLECHETTE_AM, BulletConfigSyncingUtil.BMG50_FLECHETTE_NORMAL, BulletConfigSyncingUtil.BMG50_FLECHETTE_PO);
	/** 5.56MMx45 NATO (BASIC) **/
	public static final List<Integer> NATO = ImmutableList.of(BulletConfigSyncingUtil.R556_NORMAL, BulletConfigSyncingUtil.R556_GOLD, BulletConfigSyncingUtil.R556_TRACER, BulletConfigSyncingUtil.R556_PHOSPHORUS, BulletConfigSyncingUtil.R556_AP, BulletConfigSyncingUtil.R556_DU, BulletConfigSyncingUtil.R556_STAR, BulletConfigSyncingUtil.CHL_R556, BulletConfigSyncingUtil.R556_SLEEK, BulletConfigSyncingUtil.R556_K);
	/** 5.56MMx45 NATO (FLECHETTE) **/
	public static final List<Integer> NATOFlechette = ImmutableList.of(BulletConfigSyncingUtil.R556_FLECHETTE, BulletConfigSyncingUtil.R556_FLECHETTE_INCENDIARY, BulletConfigSyncingUtil.R556_FLECHETTE_PHOSPHORUS, BulletConfigSyncingUtil.R556_FLECHETTE_DU, BulletConfigSyncingUtil.CHL_R556_FLECHETTE, BulletConfigSyncingUtil.R556_FLECHETTE_SLEEK, BulletConfigSyncingUtil.R556_K);
	/** 7.62x51mm NATO **/
	public static final List<Integer> threeZeroEight = ImmutableList.of(BulletConfigSyncingUtil.W308);
	/** 5MM **/
	public static final List<Integer> fiveMM = ImmutableList.of(BulletConfigSyncingUtil.R5_NORMAL_BOLT, BulletConfigSyncingUtil.R5_EXPLOSIVE_BOLT, BulletConfigSyncingUtil.R5_DU_BOLT, BulletConfigSyncingUtil.R5_STAR_BOLT, BulletConfigSyncingUtil.CHL_R5_BOLT);
	// MISC
	/** .75 **/
	public static final List<Integer> seventyFive = ImmutableList.of(BulletConfigSyncingUtil.B75_NORMAL, BulletConfigSyncingUtil.B75_INCENDIARY, BulletConfigSyncingUtil.B75_HE);
	/** 240MM SHELL **/
	public static final List<Integer> cannon = ImmutableList.of(BulletConfigSyncingUtil.SHELL_NORMAL, BulletConfigSyncingUtil.SHELL_EXPLOSIVE, BulletConfigSyncingUtil.SHELL_AP, BulletConfigSyncingUtil.SHELL_DU, BulletConfigSyncingUtil.SHELL_W9, BulletConfigSyncingUtil.SHELL_W9_FULL);
	/** FLAMETHROWER FUEL **/
	public static final List<Integer> flamer = ImmutableList.of(BulletConfigSyncingUtil.FLAMER_NORMAL, BulletConfigSyncingUtil.FLAMER_NAPALM, BulletConfigSyncingUtil.FLAMER_WP, BulletConfigSyncingUtil.FLAMER_VAPORIZER, BulletConfigSyncingUtil.FLAMER_GAS);
	/** MINI-NUKES **/
	public static final List<Integer> fatman = ImmutableList.of(BulletConfigSyncingUtil.NUKE_NORMAL, BulletConfigSyncingUtil.NUKE_LOW, BulletConfigSyncingUtil.NUKE_HIGH, BulletConfigSyncingUtil.NUKE_TOTS, BulletConfigSyncingUtil.NUKE_SAFE, BulletConfigSyncingUtil.NUKE_PUMPKIN, BulletConfigSyncingUtil.NUKE_BARREL);
	/** MIRV MINI-NUKES **/
	public static final List<Integer> fatmanMIRV = ImmutableList.of(BulletConfigSyncingUtil.NUKE_MIRV_NORMAL, BulletConfigSyncingUtil.NUKE_MIRV_LOW, BulletConfigSyncingUtil.NUKE_MIRV_HIGH, BulletConfigSyncingUtil.NUKE_MIRV_SAFE, BulletConfigSyncingUtil.NUKE_MIRV_SPECIAL);
	/** 40MM GRENADE **/
	public static final List<Integer> grenade = ImmutableList.of(BulletConfigSyncingUtil.GRENADE_NORMAL, BulletConfigSyncingUtil.GRENADE_HE, BulletConfigSyncingUtil.GRENADE_INCENDIARY, BulletConfigSyncingUtil.GRENADE_PHOSPHORUS, BulletConfigSyncingUtil.GRENADE_SMOKE, BulletConfigSyncingUtil.GRENADE_CHEMICAL, BulletConfigSyncingUtil.GRENADE_CONCUSSION, BulletConfigSyncingUtil.GRENADE_FINNED, BulletConfigSyncingUtil.GRENADE_SLEEK, BulletConfigSyncingUtil.GRENADE_NUCLEAR, BulletConfigSyncingUtil.GRENADE_TRACER, BulletConfigSyncingUtil.GRENADE_KAMPF, BulletConfigSyncingUtil.GRENADE_LUNATIC);
	/** 84MM ROCKET **/
	public static final List<Integer> rocket = ImmutableList.of(BulletConfigSyncingUtil.ROCKET_NORMAL, BulletConfigSyncingUtil.ROCKET_HE, BulletConfigSyncingUtil.ROCKET_INCENDIARY, BulletConfigSyncingUtil.ROCKET_PHOSPHORUS, BulletConfigSyncingUtil.ROCKET_SHRAPNEL, BulletConfigSyncingUtil.ROCKET_EMP, BulletConfigSyncingUtil.ROCKET_GLARE, BulletConfigSyncingUtil.ROCKET_TOXIC, BulletConfigSyncingUtil.ROCKET_CANISTER, BulletConfigSyncingUtil.ROCKET_SLEEK, BulletConfigSyncingUtil.ROCKET_NUKE, BulletConfigSyncingUtil.ROCKET_CHAINSAW);
	/// HASH SETS
	// PRIMES
	public static final Set<Integer> primes = ImmutableSet.of(3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997, 1009, 1013, 1019, 1021, 1031, 1033, 1039, 1049, 1051, 1061, 1063, 1069, 1087, 1091, 1093, 1097, 1103, 1109, 1117, 1123, 1129, 1151, 1153, 1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223);
	// ENERGY BULLET SPECTRAL-ABLE
	public static Set<Block> energyRoundBlock;
	public static Set<Material> energyRoundMaterial;
	/// DATA STORAGE TYPES
	public static enum EnumDataTypes
	{
		RESEARCH_ASSEMBLER("DATA_RECIPE_ASSEMBLER"),
		RESEARCH_CHEMPLANT("DATA_RECIPE_CHEMPLANT"),
		RECIPE("DATA_RECIPE"),
		MESSAGE("DATA_MESSAGE"),
		PROGRAM("DATA_PROGRAM"),
		COMMAND_TASK("DATA_COMMAND_TASK");

		public final String key;
		EnumDataTypes(String keyIn)
		{
			key = keyIn;
		}
	}
	/// FREQUENTLY USED TRANSLATION KEYS
	// GUN MANUFACTURERS
	public static enum EnumGunManufacturer
	{
		/**Armalite**/
		ARMALITE,
		/**Auto-Ordnance Corporation**/
		AUTO_ORDINANCE,
		/**BAE Systems plc**/
		BAE,
		/**Benelli Armi SpA**/
		BENELLI,
		/**Black Mesa Research Facility**/
		BLACK_MESA,
		/**Cerix Magnus**/
		CERIX,
		/**Colt's Manufacturing Company**/
		COLT,
		/**The Universal Union**/
		COMBINE,
		/**Cube 2: Sauerbraten**/
		CUBE,
		/**Enzinger Union**/
		ENZINGER,
		/**Equestria Missile Systems**/
		EQUESTRIA,
		/**Fisher Price**/
		F_PRICE,
		/**Fort Strong**/
		F_STRONG,
		/**FlimFlam Industries**/
		FLIMFLAM,
		/**Gloria GmbH**/
		GLORIA,
		/**Heckler & Koch**/
		H_AND_K,
		/**Harrington & Richardson**/
		H_AND_R,
		/**Hasbro**/
		HASBRO,
		/**Ironshod Firearms**/
		IF,
		/**Israel Military Industries**/
		IMI,
		/**IMI / Big MT**/
		IMI_BIGMT,
		/**Langford Research Laboratories**/
		LANGFORD,
		/**Magnum Research / Israel Military Industries**/
		MAGNUM_R_IMI,
		/**Open Mann Co.**/
		MANN,
		/**Hiram Maxim**/
		MAXIM,
		/**Metro Gunsmiths**/
		METRO,
		/**MWT Prototype Labs**/
		MWT,
		/**Erfurter Maschinenfabrik Geipel**/
		NAZI,
		/**No manufacturer, just puts "-" **/
		NONE,
		/**OxfordEM Technologies**/
		OXFORD,
		/**Lunar Defense Corp**/
		LUNA,
		/**Raytheon Missile Systems**/
		RAYTHEON,
		/**Rockwell International Corporation**/
		ROCKWELL,
		/**Rockwell International Corporation?**/
		ROCKWELL_U,
		/**Ryan Industries**/
		RYAN,
		/**Saab Bofors Dynamics**/
		SAAB,
		/**Saco Defense / US Ordnance**/
		SACO,
		/**Tulsky Oruzheiny Zavod**/
		TULSKY,
		/**Union Aerospace Corporation**/
		UAC,
		/**Unknown manufacturer, puts "???"**/
		UNKNOWN,
		/**WestTek**/
		WESTTEK,
		/**Wilhelm-Gustloff-Werke**/
		WGW,
		/**Winchester Repeating Arms Company**/
		WINCHESTER,
		/**Winchester Repeating Arms Company / Big MT**/
		WINCHESTER_BIGMT;
		
		public String getKey()
		{
			return "gun.make." + toString();
		}
	}
	// GUN NAMES
	// GUN DETAILS
	public static final String ammo = "desc.item.gun.ammo";
	public static final String ammoMag = "desc.item.gun.ammoMag";
	public static final String ammoBelt = "desc.item.gun.ammoBelt";
	public static final String ammoEnergy = "desc.item.gun.ammoEnergy";
	public static final String altAmmoEnergy = "desc.item.gun.ammoEnergyAlt";
	public static final String ammoType = "desc.item.gun.ammoType";
	public static final String altAmmoType = "desc.item.gun.ammoTypeAlt";
	public static final String gunName = "desc.item.gun.name";
	public static final String gunMaker = "desc.item.gun.manufacturer";
	public static final String gunDamage = "desc.item.gun.damage";
	// ITEM TRAITS
	public static final String asbestos = "trait.asbestos";
	public static final String beryllium = "trait.BERYLLIUM";
	public static final String blinding = "trait.blinding";
	public static final String drop = "trait.drop";
	public static final String drx = "trait.digamma";
	public static final String explosive = "trait.explosive";
	public static final String hot = "trait.hot";
	public static final String heavyMetal = "trait.HEAVY_METAL";
	public static final String hydro = "trait.hydro";
	public static final String radioactive = "trait.radioactive";
	// FLUID TRAITS
	public static final String antimatter = "desc.fTrait.antimatter";
	public static final String biohazard = "desc.fTrait.biohazard";
	public static final String chemical = "desc.fTrait.chemical";
	public static final String corrosive = "desc.fTrait.corrosive1";
	public static final String corrosiveStrong = "desc.fTrait.corrosive2";
	public static final String radioactiveFluid = "desc.fTrait.radioactive";
	public static final String toxicGeneric = "desc.fTrait.toxic";
	// BATTERY
	public static final String charge = "desc.item.battery.charge";
	public static final String chargePerc = "desc.item.battery.chargePerc";
	public static final String chargeRate = "desc.item.battery.chargeRate";
	public static final String dischargeRate = "desc.item.battery.dischargeRate";
	// SAVED POSITIONS
	public static final String noPos = "desc.misc.noPos";
	public static final String pos = "desc.misc.pos";
	public static final String posSet = "desc.misc.posSet";
	public static final String tarCoord = "desc.misc.tarCoord";
	public static final String tarSet = "desc.misc.tarSet";
	// MISC
	public static final String capacity = "desc.block.barrel.capacity";
	public static final String durability = "desc.item.durability";
	public static final String meltPoint = "desc.misc.meltPoint";
	public static final String lctrl = "desc.misc.lctrl";
	public static final String lshift = "desc.misc.lshift";
	/** LORE HEADING **/
	public static final String lore = "desc.misc.lore";
	/** "FUNCTION" HEADING **/
	public static final String func = "desc.misc.func";
	/// Damage sources that entities can be immune to
	/** ROBOTS / INORGANIC ENTITIES **/
	public static final Set<String> damageImmuneRobot = ImmutableSet.of("oxygenSuffocation", "thermal", "lead", "mudPoisoning", "acid", "monoxide", "bleed", "asbestos", "broadcast", "lead", "electricity", "drown", "starve", "wither", "inWall");
	/// Potions that entities can be immune to
	/** ROBOTS / INORGANIC ENTITIES **/
	public static final Set<Integer> potionImmuneRobot = ImmutableSet.of(HbmPotion.radiation.id, HbmPotion.lead.id, HbmPotion.phosphorus.id, HbmPotion.potionsickness.id, Potion.harm.id, Potion.heal.id, Potion.hunger.id, Potion.poison.id, Potion.wither.id);
	/** Do AFTER blocks and items are initialized  **/
	public static final void initKits()
	{
		MainRegistry.logger.info("[HbmCollection] kits and non-final fields initializing...");
		/// MISC
		energyRoundBlock = ImmutableSet.of(Blocks.glass, Blocks.glass_pane, Blocks.stained_glass, Blocks.stained_glass_pane, Blocks.ice, Blocks.packed_ice, ModBlocks.glass_polonium, ModBlocks.glass_quartz, ModBlocks.glass_trinitite, ModBlocks.glass_uranium, ModBlocks.hadron_analysis_glass, ModBlocks.hadron_plating_glass, ModBlocks.reinforced_glass);	
		energyRoundMaterial = ImmutableSet.of(Material.glass, Material.ice, Material.packedIce, Material.water, Material.web, Material.fire, ModBlocks.materialGas);
		/** ARMORS **/
		// HAZMAT TIERS
		hazmats = new Item[][]
				{
						{ModItems.hazmat_helmet, ModItems.hazmat_plate, ModItems.hazmat_legs, ModItems.hazmat_boots},
						{ModItems.hazmat_helmet_red, ModItems.hazmat_plate_red, ModItems.hazmat_legs_red, ModItems.hazmat_boots_red},
						{ModItems.hazmat_helmet_grey, ModItems.hazmat_plate_grey, ModItems.hazmat_legs_grey, ModItems.hazmat_boots_grey}
				};
		// OTHER ARMORS
		t45 = new Item[] {ModItems.t45_helmet, ModItems.t45_plate, ModItems.t45_legs, ModItems.t45_boots};
		lunar = new Item[] {ModItems.bj_helmet, ModItems.bj_plate, ModItems.bj_legs, ModItems.bj_boots};
		/** KITS **/
		// STARTER KITS
		nukeStarterKit = new ItemStack[]
				{
						new ItemStack(ModItems.ingot_uranium, 32),
						new ItemStack(ModItems.powder_yellowcake, 32),
						new ItemStack(ModItems.template_folder),
						new ItemStack(ModBlocks.machine_press),
						new ItemStack(ModBlocks.machine_difurnace_off),
						new ItemStack(ModBlocks.machine_gascent),
						new ItemStack(ModBlocks.machine_puf6_tank),
						new ItemStack(ModBlocks.machine_reactor_breeding),
						new ItemStack(ModBlocks.machine_nuke_furnace_off),
						new ItemStack(ModBlocks.machine_assembler),
						new ItemStack(ModBlocks.machine_chemplant),
						new ItemStack(ModBlocks.reactor_research),
						new ItemStack(ModBlocks.machine_turbine, 2),
						new ItemStack(ModItems.radaway, 8),
						new ItemStack(ModItems.radx, 2),
						new ItemStack(ModItems.stamp_titanium_flat, 3),
						new ItemStack(ModItems.ingot_steel, 64),
						new ItemStack(ModItems.ingot_lead, 64),
						new ItemStack(ModItems.ingot_copper, 64),
						new ItemStack(ModItems.gas_mask_m65),
						new ItemStack(ModItems.geiger_counter)
				};
		nukeAdvancedKit = new ItemStack[]
				{
						new ItemStack(ModItems.powder_yellowcake, 64),
						new ItemStack(ModItems.ingot_plutonium, 64),
						new ItemStack(ModItems.ingot_steel, 64),
						new ItemStack(ModItems.ingot_copper, 64),
						new ItemStack(ModItems.ingot_tungsten, 64),
						new ItemStack(ModItems.ingot_lead, 64),
						new ItemStack(ModItems.ingot_polymer, 64),
						new ItemStack(ModBlocks.machine_difurnace_off, 3),
						new ItemStack(ModBlocks.machine_gascent, 3),
						new ItemStack(ModBlocks.machine_centrifuge, 2),
						new ItemStack(ModBlocks.machine_uf6_tank, 2),
						new ItemStack(ModBlocks.machine_puf6_tank, 2),
						new ItemStack(ModBlocks.machine_reactor_breeding, 2),
						new ItemStack(ModBlocks.machine_rtg_furnace_off, 2),
						new ItemStack(ModBlocks.reactor_research, 4),
						new ItemStack(ModBlocks.machine_turbine, 4),
						new ItemStack(ModBlocks.machine_radgen),
						new ItemStack(ModBlocks.machine_rtg_grey),
						new ItemStack(ModBlocks.machine_assembler, 3),
						new ItemStack(ModBlocks.machine_chemplant, 2),
						new ItemStack(ModBlocks.machine_fluidtank),
						new ItemStack(ModItems.pellet_rtg, 3),
						new ItemStack(ModItems.pellet_rtg_weak, 3),
						new ItemStack(ModItems.cell_empty, 32),
						new ItemStack(ModItems.rod_empty, 32),
						new ItemStack(ModItems.fluid_barrel_full, 4, Fluids.COOLANT.getID()),
						new ItemStack(ModItems.radaway_strong, 4),
						new ItemStack(ModItems.radx, 4),
						new ItemStack(ModItems.pill_iodine),
						new ItemStack(ModItems.tritium_deuterium_cake),
						new ItemStack(ModItems.geiger_counter),
						new ItemStack(ModItems.survey_scanner),
						new ItemStack(ModItems.gas_mask_m65)
				};
		nukeCommercialKit = new ItemStack[]
				{
						new ItemStack(ModItems.ingot_pu238, 8),
						new ItemStack(ModItems.ingot_uranium_fuel, 32),
						new ItemStack(ModItems.ingot_plutonium_fuel, 8),
						new ItemStack(ModItems.ingot_mox_fuel, 6),
						new ItemStack(ModItems.rtg_unit, 6),
						new ItemStack(ModItems.motor, 3),
						new ItemStack(ModItems.reactor_core),
						new ItemStack(ModItems.cell_empty, 32),
						new ItemStack(ModItems.rod_empty, 64),
						new ItemStack(ModItems.fluid_barrel_full, 6, Fluids.WATER.getID()),
						new ItemStack(ModItems.fluid_barrel_full, 8, Fluids.COOLANT.getID()),
						new ItemStack(ModBlocks.machine_assembler),
						new ItemStack(ModBlocks.machine_chemplant, 3),
						new ItemStack(ModBlocks.machine_gascent, 2),
						new ItemStack(ModBlocks.machine_nuke_furnace_off, 2),
						new ItemStack(ModBlocks.machine_rtg_furnace_off, 3),
						new ItemStack(ModBlocks.machine_rtg_grey, 2),
						new ItemStack(ModBlocks.reactor_research, 8),
						new ItemStack(ModBlocks.machine_turbine, 6),
						new ItemStack(ModBlocks.machine_lithium_battery, 4),
						new ItemStack(ModBlocks.red_cable, 32),
						new ItemStack(ModBlocks.red_wire_coated, 8),
						new ItemStack(ModItems.pellet_rtg, 6),
						new ItemStack(ModItems.pellet_rtg_weak, 3),
						new ItemStack(ModItems.radaway_strong, 8),
						new ItemStack(ModItems.radaway_flush),
						new ItemStack(ModItems.radx, 2),
						new ItemStack(ModItems.pill_iodine),
						new ItemStack(ModItems.geiger_counter)
				};
		electricKit = new ItemStack[]
				{
						new ItemStack(ModItems.coil_copper, 6),
						new ItemStack(ModItems.coil_gold, 8),
						new ItemStack(ModItems.coil_tungsten, 8),
						new ItemStack(ModItems.motor, 4),
						new ItemStack(ModItems.circuit_aluminium, 6),
						new ItemStack(ModItems.circuit_copper, 8),
						new ItemStack(ModItems.circuit_red_copper, 4),
						new ItemStack(ModItems.wiring_red_copper),
						new ItemStack(ModItems.magnetron, 5),
						new ItemStack(ModItems.piston_selenium,3 ),
						new ItemStack(ModItems.canister_fuel, 6),
						new ItemStack(ModItems.canister_biofuel, 6),
						new ItemStack(ModItems.battery_advanced_cell_4, 2),
						new ItemStack(ModItems.battery_lithium, 2),
						new ItemStack(ModItems.battery_potato),
						new ItemStack(ModItems.screwdriver),
						new ItemStack(ModBlocks.machine_coal_off, 3),
						new ItemStack(ModBlocks.machine_diesel, 2),
						new ItemStack(ModBlocks.machine_selenium),
						new ItemStack(ModBlocks.red_cable, 64),
						new ItemStack(ModBlocks.red_wire_coated, 6),
						new ItemStack(ModBlocks.red_pylon, 8),
						new ItemStack(ModBlocks.machine_battery, 4),
						new ItemStack(ModBlocks.machine_lithium_battery, 2),
						new ItemStack(ModBlocks.machine_converter_he_rf),
						new ItemStack(ModBlocks.machine_converter_rf_he),
				};
		// BOMB KITS
		gadgetKit = new ItemStack[]
				{
						new ItemStack(ModBlocks.nuke_gadget),
						new ItemStack(ModItems.gadget_wireing),
						new ItemStack(ModItems.gadget_core),
				};
		boyKit = new ItemStack[]
				{
						new ItemStack(ModBlocks.nuke_boy),
						new ItemStack(ModItems.boy_shielding),
						new ItemStack(ModItems.boy_target),
						new ItemStack(ModItems.boy_bullet),
						new ItemStack(ModItems.boy_propellant),
						new ItemStack(ModItems.boy_igniter),
				};
		manKit = new ItemStack[]
				{
						new ItemStack(ModBlocks.nuke_man),
						new ItemStack(ModItems.man_igniter),
						new ItemStack(ModItems.man_core),
				};
		mikeKit = new ItemStack[]
				{
						new ItemStack(ModBlocks.nuke_mike),
						new ItemStack(ModItems.man_core),
						new ItemStack(ModItems.mike_core),
						new ItemStack(ModItems.mike_deut),
						new ItemStack(ModItems.mike_cooling_unit),
				};
		tsarKit = new ItemStack[]
				{
						new ItemStack(ModBlocks.nuke_tsar),
						new ItemStack(ModItems.man_core),
						new ItemStack(ModItems.tsar_core),
				};
		multiKit = new ItemStack[]
				{
						new ItemStack(ModBlocks.bomb_multi, 6),
						new ItemStack(Blocks.tnt, 26),
						new ItemStack(Items.gunpowder, 2),
						new ItemStack(ModItems.pellet_cluster, 2),
						new ItemStack(ModItems.powder_fire, 2),
						new ItemStack(ModItems.powder_poison, 2),
						new ItemStack(ModItems.pellet_gas, 2),
				};
		customKit = new ItemStack[]
				{
						new ItemStack(ModBlocks.nuke_custom),
						new ItemStack(ModItems.custom_tnt, 6),
						new ItemStack(ModItems.custom_nuke, 4),
						new ItemStack(ModItems.custom_hydro, 2),
						new ItemStack(ModItems.custom_amat, 2),
						new ItemStack(ModItems.custom_dirty, 3),
						new ItemStack(ModItems.custom_schrab),
						new ItemStack(ModItems.custom_fall),
				};
		grenadeKit = new ItemStack[]
				{// TODO Finish
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.GENERIC),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.STRONG),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.FRAG),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.FIRE),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.SHRAPNEL),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.CLUSTER),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.FLARE),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.ELECTRIC),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.POISON),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.GAS),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.CLOUD),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.PINK_CLOUD),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.SMART),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.STUNNING),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.MIRV),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.BREACH),
//						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.BURST),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.PULSE),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.PLASMA),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.TAU),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.SCHRABIDIUM),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.LEMON),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.GASCAN),
//						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.MK2),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.LUNATIC),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.ASCHRAB),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.NUKE),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.NUCLEAR),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.ZOMG),
						ModItems.grenade.stackFromEnum(6, AmmoHandGrenade.BLACK_HOLE),
				};
		fleijaKit = new ItemStack[]
				{
						new ItemStack(ModBlocks.nuke_fleija),
						new ItemStack(ModItems.fleija_igniter, 2),
						new ItemStack(ModItems.fleija_propellant, 3),
						new ItemStack(ModItems.fleija_core, 6),
				};
		soliniumKit = new ItemStack[]
				{
						new ItemStack(ModBlocks.nuke_solinium),
						new ItemStack(ModItems.solinium_igniter, 4),
						new ItemStack(ModItems.solinium_propellant, 4),
						new ItemStack(ModItems.solinium_core),
				};
		prototypeKit = new ItemStack[]
				{
						new ItemStack(ModBlocks.nuke_prototype),
						new ItemStack(ModItems.igniter),
						new ItemStack(ModItems.cell_sas3, 4),
						new ItemStack(ModItems.rod_quad, 4, BreedingRodType.URANIUM.ordinal()),
						new ItemStack(ModItems.rod_quad, 4, BreedingRodType.LEAD.ordinal()),
						new ItemStack(ModItems.rod_quad, 2, BreedingRodType.NP237.ordinal()),
				};
		missileKit = new ItemStack[]
				{
						new ItemStack(ModBlocks.launch_pad),
						new ItemStack(ModItems.designator),
						new ItemStack(ModItems.designator_range),
						new ItemStack(ModItems.designator_manual),
						new ItemStack(ModItems.battery_schrabidium_cell_4),
						new ItemStack(ModItems.missile_generic),
						new ItemStack(ModItems.missile_strong),
						new ItemStack(ModItems.missile_burst),
						new ItemStack(ModItems.missile_incendiary),
						new ItemStack(ModItems.missile_incendiary_strong),
						new ItemStack(ModItems.missile_inferno),
						new ItemStack(ModItems.missile_cluster),
						new ItemStack(ModItems.missile_cluster_strong),
						new ItemStack(ModItems.missile_rain),
						new ItemStack(ModItems.missile_buster),
						new ItemStack(ModItems.missile_buster_strong),
						new ItemStack(ModItems.missile_drill),
						new ItemStack(ModItems.missile_nuclear),
						new ItemStack(ModItems.missile_nuclear_cluster),
						new ItemStack(ModItems.missile_endo),
						new ItemStack(ModItems.missile_exo),
						new ItemStack(ModItems.missile_doomsday),
						new ItemStack(ModItems.missile_taint),
						new ItemStack(ModItems.missile_micro),
						new ItemStack(ModItems.missile_bhole),
						new ItemStack(ModItems.missile_schrabidium),
						new ItemStack(ModItems.missile_emp),
				};
		// MISC KITS
		t45Kit = new ItemStack[]
				{
						new ItemStack(ModItems.t45_helmet),
						new ItemStack(ModItems.t45_plate),
						new ItemStack(ModItems.t45_legs),
						new ItemStack(ModItems.t45_boots),
						new ItemStack(ModItems.fusion_core, 7)
				};
		euphemiumKit = new ItemStack[]
				{
						new ItemStack(ModItems.euphemium_helmet),
						new ItemStack(ModItems.euphemium_plate),
						new ItemStack(ModItems.euphemium_legs),
						new ItemStack(ModItems.euphemium_boots),
						new ItemStack(ModBlocks.statue_elb),
						new ItemStack(ModItems.gun_revolver_cursed),
						new ItemStack(ModItems.watch),
				};
//		ItemStack fdd = ItemStorageMedium.getBlankMedium(ModItems.storage_magnetic_fdd);
//		ItemStack cd = ItemStorageMedium.getBlankMedium(ModItems.storage_optical_cd);
//		ItemStack fdd = IDataStorageUser.getBlankMedium(ModItems.storage_magnetic_fdd);
//		ItemStack cd = IDataStorageUser.getBlankMedium(ModItems.storage_optical_cd);
//		fdd.stackSize = 10;
//		cd.stackSize = 8;
//		fddPack = new ItemStack[] {fdd};
//		cdPack = new ItemStack[] {cd};
//		Arrays.fill(fddPack, fdd);
//		Arrays.fill(cdPack, cd);
		lunarKit = new ItemStack[]
				{
						new ItemStack(ModItems.bj_helmet),
						new ItemStack(ModItems.bj_plate),
						new ItemStack(ModItems.bj_legs),
						new ItemStack(ModItems.bj_boots),
//						new ItemStack(ModItems.grenade_lunatic, 8),
						new ItemStack(ModItems.ingot_starmetal, 32),
						new ItemStack(ModItems.crystal_starmetal, 8),
						new ItemStack(ModItems.pancake, 8),
				};
		MainRegistry.logger.info("[HbmCollection] Initializing complete!");
	}
}