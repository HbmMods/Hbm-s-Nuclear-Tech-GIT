package com.hbm.lib;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.items.weapon.ItemAmmo.AmmoItemTrait;

public class HbmCollection {
	
	public static final Set<AmmoItemTrait> APType = ImmutableSet.of(AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.CON_WEAR);
	public static final Set<AmmoItemTrait> FlechetteType = ImmutableSet.of(AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_LESS_BOUNCY, AmmoItemTrait.CON_WEAR);
	public static final Set<AmmoItemTrait> IncendiaryType = ImmutableSet.of(AmmoItemTrait.PRO_INCENDIARY, AmmoItemTrait.CON_WEAR);
	public static final Set<AmmoItemTrait> PhosphorusType = ImmutableSet.of(AmmoItemTrait.PRO_PHOSPHORUS, AmmoItemTrait.NEU_WARCRIME1, AmmoItemTrait.CON_WEAR, AmmoItemTrait.CON_PENETRATION);
	public static final Set<AmmoItemTrait> PhosphorusTypeSpecial = ImmutableSet.of(AmmoItemTrait.PRO_PHOSPHORUS_SPLASH, AmmoItemTrait.NEU_WARCRIME1, AmmoItemTrait.CON_WEAR);
	public static final Set<AmmoItemTrait> ExplosiveType = ImmutableSet.of(AmmoItemTrait.PRO_EXPLOSIVE, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.CON_HEAVY_WEAR);
	public static final Set<AmmoItemTrait> DUType = ImmutableSet.of(AmmoItemTrait.PRO_HEAVY_DAMAGE, AmmoItemTrait.NEU_HEAVY_METAL, AmmoItemTrait.CON_HEAVY_WEAR);
	public static final Set<AmmoItemTrait> StarmetalType = ImmutableSet.of(AmmoItemTrait.PRO_HEAVY_DAMAGE, AmmoItemTrait.NEU_STARMETAL, AmmoItemTrait.CON_HEAVY_WEAR);
	public static final Set<AmmoItemTrait> ChlorophyteType = ImmutableSet.of(AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_WEAR, AmmoItemTrait.NEU_CHLOROPHYTE, AmmoItemTrait.NEU_HOMING, AmmoItemTrait.CON_PENETRATION);
	
	/// BULLET COLLECTIONS
	// SHOTGUNS
	/** 12 GAUGE **/
	public static final List<Integer> g12 = ImmutableList.of(BulletConfigSyncingUtil.G12_NORMAL, BulletConfigSyncingUtil.G12_INCENDIARY, BulletConfigSyncingUtil.G12_SHRAPNEL, BulletConfigSyncingUtil.G12_DU, BulletConfigSyncingUtil.G12_AM, BulletConfigSyncingUtil.G12_SLEEK, BulletConfigSyncingUtil.G12_PERCUSSION);
	public static final List<Integer> g12hs = ImmutableList.of(BulletConfigSyncingUtil.G12HS_NORMAL, BulletConfigSyncingUtil.G12HS_INCENDIARY, BulletConfigSyncingUtil.G12HS_SHRAPNEL, BulletConfigSyncingUtil.G12HS_DU, BulletConfigSyncingUtil.G12HS_AM, BulletConfigSyncingUtil.G12HS_SLEEK, BulletConfigSyncingUtil.G12HS_PERCUSSION);
	/** 20 GAUGE **/
	public static final List<Integer> g20 = ImmutableList.of(BulletConfigSyncingUtil.G20_NORMAL, BulletConfigSyncingUtil.G20_SLUG, BulletConfigSyncingUtil.G20_FLECHETTE, BulletConfigSyncingUtil.G20_FIRE, BulletConfigSyncingUtil.G20_SHRAPNEL, BulletConfigSyncingUtil.G20_EXPLOSIVE, BulletConfigSyncingUtil.G20_CAUSTIC, BulletConfigSyncingUtil.G20_SHOCK, BulletConfigSyncingUtil.G20_WITHER, BulletConfigSyncingUtil.G20_SLEEK);
	/** 4 GAUGE **/
	public static final List<Integer> g4 = ImmutableList.of(BulletConfigSyncingUtil.G4_NORMAL, BulletConfigSyncingUtil.G4_SLUG, BulletConfigSyncingUtil.G4_FLECHETTE, BulletConfigSyncingUtil.G4_FLECHETTE_PHOSPHORUS, BulletConfigSyncingUtil.G4_EXPLOSIVE, BulletConfigSyncingUtil.G4_SEMTEX, BulletConfigSyncingUtil.G4_BALEFIRE, BulletConfigSyncingUtil.G4_KAMPF, BulletConfigSyncingUtil.G4_CANISTER, BulletConfigSyncingUtil.G4_CLAW, BulletConfigSyncingUtil.G4_VAMPIRE, BulletConfigSyncingUtil.G4_VOID, BulletConfigSyncingUtil.G4_TITAN, BulletConfigSyncingUtil.G4_SLEEK, BulletConfigSyncingUtil.G4_LTBL,BulletConfigSyncingUtil.G4_LTBL_SUPER);
	// PISTOL CALIBER
	/** .22 LONG RIFLE **/
	public static final List<Integer> lr22 = ImmutableList.of(BulletConfigSyncingUtil.LR22_NORMAL, BulletConfigSyncingUtil.LR22_AP, BulletConfigSyncingUtil.CHL_LR22);
	public static final List<Integer> lr22Inc = ImmutableList.of(BulletConfigSyncingUtil.LR22_NORMAL_FIRE, BulletConfigSyncingUtil.LR22_AP_FIRE, BulletConfigSyncingUtil.CHL_LR22_FIRE);
	/** .44 MAGNUM (BASIC) **/
	public static final List<Integer> m44Normal = ImmutableList.of(BulletConfigSyncingUtil.M44_NORMAL, BulletConfigSyncingUtil.M44_AP, BulletConfigSyncingUtil.M44_DU, BulletConfigSyncingUtil.M44_PHOSPHORUS, BulletConfigSyncingUtil.M44_STAR, BulletConfigSyncingUtil.CHL_M44, BulletConfigSyncingUtil.M44_ROCKET);
	/** .44 MAGNUM (ALL) **/
	public static final List<Integer> m44All = ImmutableList.of(BulletConfigSyncingUtil.M44_NORMAL, BulletConfigSyncingUtil.M44_AP, BulletConfigSyncingUtil.M44_DU, BulletConfigSyncingUtil.M44_PHOSPHORUS, BulletConfigSyncingUtil.M44_STAR, BulletConfigSyncingUtil.CHL_M44, BulletConfigSyncingUtil.M44_ROCKET, BulletConfigSyncingUtil.M44_PIP, BulletConfigSyncingUtil.M44_BJ, BulletConfigSyncingUtil.M44_SILVER);
	/** .50 ACTION EXPRESS **/
	public static final List<Integer> ae50 = ImmutableList.of(BulletConfigSyncingUtil.AE50_NORMAL, BulletConfigSyncingUtil.AE50_AP, BulletConfigSyncingUtil.AE50_DU, BulletConfigSyncingUtil.AE50_STAR, BulletConfigSyncingUtil.CHL_AE50);
	/** 9MM Parabellum **/
	public static final List<Integer> p9 = ImmutableList.of(BulletConfigSyncingUtil.P9_NORMAL, BulletConfigSyncingUtil.P9_AP, BulletConfigSyncingUtil.P9_DU, BulletConfigSyncingUtil.CHL_P9, BulletConfigSyncingUtil.P9_ROCKET);
	/** .45 AUTOMATIC COLT PISTOL **/
	public static final List<Integer> acp45 = ImmutableList.of(BulletConfigSyncingUtil.ACP_45, BulletConfigSyncingUtil.ACP_45_AP, BulletConfigSyncingUtil.ACP_45_DU);
	// RIFLE CALIBER
	/** .50 BROWNING MACHINE GUN **/
	public static final List<Integer> bmg50 = ImmutableList.of(BulletConfigSyncingUtil.BMG50_NORMAL, BulletConfigSyncingUtil.BMG50_INCENDIARY, BulletConfigSyncingUtil.BMG50_PHOSPHORUS, BulletConfigSyncingUtil.BMG50_EXPLOSIVE, BulletConfigSyncingUtil.BMG50_AP, BulletConfigSyncingUtil.BMG50_DU, BulletConfigSyncingUtil.BMG50_STAR, BulletConfigSyncingUtil.CHL_BMG50, BulletConfigSyncingUtil.BMG50_SLEEK);
	/** .50 BROWNING MACHINE GUN (FLECHETTE) **/
	public static final List<Integer> bmg50Flechette = ImmutableList.of(BulletConfigSyncingUtil.BMG50_FLECHETTE_AM, BulletConfigSyncingUtil.BMG50_FLECHETTE_NORMAL, BulletConfigSyncingUtil.BMG50_FLECHETTE_PO);
	/** 5.56MMx45 NATO (BASIC) **/
	public static final List<Integer> r556 = ImmutableList.of(BulletConfigSyncingUtil.R556_NORMAL, BulletConfigSyncingUtil.R556_TRACER, BulletConfigSyncingUtil.R556_PHOSPHORUS, BulletConfigSyncingUtil.R556_AP, BulletConfigSyncingUtil.R556_DU, BulletConfigSyncingUtil.R556_STAR, BulletConfigSyncingUtil.CHL_R556, BulletConfigSyncingUtil.R556_SLEEK, BulletConfigSyncingUtil.R556_K, BulletConfigSyncingUtil.R556_GOLD);
	/** 5.56MMx45 NATO (FLECHETTE) **/
	public static final List<Integer> r556Flechette = ImmutableList.of(BulletConfigSyncingUtil.R556_FLECHETTE, BulletConfigSyncingUtil.R556_FLECHETTE_INCENDIARY, BulletConfigSyncingUtil.R556_FLECHETTE_PHOSPHORUS, BulletConfigSyncingUtil.R556_FLECHETTE_DU, BulletConfigSyncingUtil.CHL_R556_FLECHETTE, BulletConfigSyncingUtil.R556_FLECHETTE_SLEEK, BulletConfigSyncingUtil.R556_K);
	/** 7.62x51mm NATO **/
	public static final List<Integer> r762 = ImmutableList.of(BulletConfigSyncingUtil.R762_NORMAL, BulletConfigSyncingUtil.R762_PHOSPHORUS, BulletConfigSyncingUtil.R762_AP, BulletConfigSyncingUtil.R762_DU, BulletConfigSyncingUtil.R762_TRACER, BulletConfigSyncingUtil.R762_K);
	public static final List<Integer> r762_hs = ImmutableList.of(BulletConfigSyncingUtil.R762_NORMAL_HS, BulletConfigSyncingUtil.R762_PHOSPHORUS_HS, BulletConfigSyncingUtil.R762_AP_HS, BulletConfigSyncingUtil.R762_DU_HS, BulletConfigSyncingUtil.R762_TRACER_HS, BulletConfigSyncingUtil.R762_K_HS);

	/** 5MM **/
	public static final List<Integer> r5 = ImmutableList.of(BulletConfigSyncingUtil.R5_NORMAL, BulletConfigSyncingUtil.R5_EXPLOSIVE, BulletConfigSyncingUtil.R5_DU, BulletConfigSyncingUtil.R5_STAR, BulletConfigSyncingUtil.CHL_R5);
	/** 5MM LACUNAE **/
	public static final List<Integer> r5Bolt = ImmutableList.of(BulletConfigSyncingUtil.R5_NORMAL_BOLT, BulletConfigSyncingUtil.R5_EXPLOSIVE_BOLT, BulletConfigSyncingUtil.R5_DU_BOLT, BulletConfigSyncingUtil.R5_STAR_BOLT, BulletConfigSyncingUtil.CHL_R5_BOLT);
	// MISC
	/** .75 **/
	public static final List<Integer> b75 = ImmutableList.of(BulletConfigSyncingUtil.B75_NORMAL, BulletConfigSyncingUtil.B75_INCENDIARY, BulletConfigSyncingUtil.B75_HE);
	/** 240MM SHELL **/
	public static final List<Integer> cannon = ImmutableList.of(BulletConfigSyncingUtil.SHELL_NORMAL, BulletConfigSyncingUtil.SHELL_EXPLOSIVE, BulletConfigSyncingUtil.SHELL_AP, BulletConfigSyncingUtil.SHELL_DU, BulletConfigSyncingUtil.SHELL_W9);
	/** FLAMETHROWER FUEL **/
	public static final List<Integer> flamer = ImmutableList.of(BulletConfigSyncingUtil.FLAMER_NORMAL, BulletConfigSyncingUtil.FLAMER_NAPALM, BulletConfigSyncingUtil.FLAMER_WP, BulletConfigSyncingUtil.FLAMER_VAPORIZER, BulletConfigSyncingUtil.FLAMER_GAS);
	/** MINI-NUKES **/
	public static final List<Integer> fatman = ImmutableList.of(BulletConfigSyncingUtil.NUKE_NORMAL, BulletConfigSyncingUtil.NUKE_LOW, BulletConfigSyncingUtil.NUKE_HIGH, BulletConfigSyncingUtil.NUKE_TOTS, BulletConfigSyncingUtil.NUKE_SAFE, BulletConfigSyncingUtil.NUKE_PUMPKIN, BulletConfigSyncingUtil.NUKE_BARREL);
	/** MIRV MINI-NUKES **/
	public static final List<Integer> fatmanMIRV = ImmutableList.of(BulletConfigSyncingUtil.NUKE_MIRV_NORMAL, BulletConfigSyncingUtil.NUKE_MIRV_LOW, BulletConfigSyncingUtil.NUKE_MIRV_HIGH, BulletConfigSyncingUtil.NUKE_MIRV_SAFE, BulletConfigSyncingUtil.NUKE_MIRV_SPECIAL);
	/** 40MM GRENADE **/
	public static final List<Integer> grenade = ImmutableList.of(BulletConfigSyncingUtil.GRENADE_NORMAL, BulletConfigSyncingUtil.GRENADE_HE, BulletConfigSyncingUtil.GRENADE_INCENDIARY, BulletConfigSyncingUtil.GRENADE_PHOSPHORUS, BulletConfigSyncingUtil.GRENADE_CHEMICAL, BulletConfigSyncingUtil.GRENADE_CONCUSSION, BulletConfigSyncingUtil.GRENADE_FINNED, BulletConfigSyncingUtil.GRENADE_SLEEK, BulletConfigSyncingUtil.GRENADE_NUCLEAR, BulletConfigSyncingUtil.GRENADE_TRACER, BulletConfigSyncingUtil.GRENADE_KAMPF);
	/** 84MM ROCKET **/
	public static final List<Integer> rocket = ImmutableList.of(BulletConfigSyncingUtil.ROCKET_NORMAL, BulletConfigSyncingUtil.ROCKET_HE, BulletConfigSyncingUtil.ROCKET_INCENDIARY, BulletConfigSyncingUtil.ROCKET_PHOSPHORUS, BulletConfigSyncingUtil.ROCKET_SHRAPNEL, BulletConfigSyncingUtil.ROCKET_EMP, BulletConfigSyncingUtil.ROCKET_GLARE, BulletConfigSyncingUtil.ROCKET_TOXIC, BulletConfigSyncingUtil.ROCKET_CANISTER, BulletConfigSyncingUtil.ROCKET_SLEEK, BulletConfigSyncingUtil.ROCKET_NUKE, BulletConfigSyncingUtil.ROCKET_CHAINSAW);
		
	/// FREQUENTLY USED TRANSLATION KEYS
	// GUN MANUFACTURERS
	public static enum EnumGunManufacturer {
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
		/**Deep Rock Galactic**/
		DRG,
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
		/**Remingotn Arms**/
		REMINGTON,
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
	
		public String getKey() {
			return "gun.make." + toString();
		}
	}
	
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
	public static final String gunPellets = "desc.item.gun.pellets";
	// MISC
	public static final String capacity = "desc.block.barrel.capacity";
	public static final String durability = "desc.item.durability";
	public static final String meltPoint = "desc.misc.meltPoint";
	public static final String lctrl = "desc.misc.lctrl";
	public static final String lshift = "desc.misc.lshift";
}
