package com.hbm.items.weapon.sedna.factory;

import com.hbm.interfaces.IOrderedEnum;
import com.hbm.items.ItemEnumMulti;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;

import net.minecraft.item.Item;

public class GunFactory {

	public static BulletConfig ammo_debug;
	
	public static SpentCasing CASING44 = new SpentCasing(CasingType.STRAIGHT).setScale(1.5F, 1.0F, 1.5F).setColor(SpentCasing.COLOR_CASE_44);

	public static void init() {
		
		/// AMMO ITEMS ///
		ModItems.ammo_debug = new Item().setUnlocalizedName("ammo_debug").setTextureName(RefStrings.MODID + ":ammo_45");
		ModItems.ammo_standard = new ItemEnumMulti(EnumAmmo.class, true, true).setUnlocalizedName("ammo_standard").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_standard");
		ModItems.ammo_secret = new ItemEnumMulti(EnumAmmoSecret.class, true, true).setUnlocalizedName("ammo_secret").setCreativeTab(null).setTextureName(RefStrings.MODID + ":ammo_secret");

		/// BULLLET CFGS ///
		ammo_debug = new BulletConfig().setItem(ModItems.ammo_debug).setSpread(0.01F).setRicochetAngle(45).setCasing(CASING44.clone().register("DEBUG0"));

		/// GUNS ///
		ModItems.gun_debug = new ItemGunBaseNT(WeaponQuality.DEBUG, new GunConfig()
				.dura(600F).draw(15).inspect(23).crosshair(Crosshair.L_CLASSIC).smoke(Lego.LAMBDA_STANDARD_SMOKE).orchestra(Orchestras.DEBUG_ORCHESTRA)
				.rec(new Receiver(0)
						.dmg(10F).delay(14).reload(46).jam(23).sound("hbm:weapon.44Shoot", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 12).addConfigs(ammo_debug))
						.offset(0.75, -0.0625, -0.3125D)
						.canFire(Lego.LAMBDA_STANDARD_CAN_FIRE).fire(Lego.LAMBDA_STANDARD_FIRE))
				.pp(Lego.LAMBDA_STANDARD_CLICK_PRIMARY).pr(Lego.LAMBDA_STANDARD_RELOAD).pt(Lego.LAMBDA_TOGGLE_AIM)
				.decider(GunStateDecider.LAMBDA_STANDARD_DECIDER)
				.anim(Lego.LAMBDA_DEBUG_ANIMS)
				).setUnlocalizedName("gun_debug");
		
		XFactoryBlackPowder.init();
		XFactory357.init();
		XFactory44.init();
		XFactory9mm.init();
		XFactory12ga.init();
		XFactory40mm.init();
		XFactory762mm.init();
		XFactory22lr.init();
		XFactoryFlamer.init();
		XFactoryRocket.init();
		XFactory556mm.init();
		XFactory50.init();
		XFactoryEnergy.init();
		XFactoryAccelerator.init();
		XFactoryCatapult.init();
		XFactory75Bolt.init();
		XFactoryFolly.init();
		XFactoryTurret.init();
		XFactory10ga.init();
		XFactory35800.init();
		XFactory45.init();
		
		ModItems.weapon_mod_test = new ItemEnumMulti(EnumModTest.class, true, true).setUnlocalizedName("weapon_mod_test").setMaxStackSize(1);
		ModItems.weapon_mod_generic = new ItemEnumMulti(EnumModGeneric.class, true, true).setUnlocalizedName("weapon_mod_generic").setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab);
		ModItems.weapon_mod_special = new ItemEnumMulti(EnumModSpecial.class, true, true).setUnlocalizedName("weapon_mod_special").setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab);
		ModItems.weapon_mod_caliber = new ItemEnumMulti(EnumModCaliber.class, true, true).setUnlocalizedName("weapon_mod_caliber").setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab);

		/// PROXY BULLSHIT ///
		MainRegistry.proxy.registerGunCfg();
	}
	
	public static enum EnumAmmo implements IOrderedEnum {
		STONE, STONE_AP, STONE_IRON, STONE_SHOT,
		M357_BP, M357_SP, M357_FMJ, M357_JHP, M357_AP, M357_EXPRESS,
		M44_BP, M44_SP, M44_FMJ, M44_JHP, M44_AP, M44_EXPRESS,
		P22_SP, P22_FMJ, P22_JHP, P22_AP,
		P9_SP, P9_FMJ, P9_JHP, P9_AP,
		R556_SP, R556_FMJ, R556_JHP, R556_AP,
		R762_SP, R762_FMJ, R762_JHP, R762_AP, R762_DU,
		BMG50_SP, BMG50_FMJ, BMG50_JHP, BMG50_AP, BMG50_DU,
		B75, B75_INC, B75_EXP,
		G12_BP, G12_BP_MAGNUM, G12_BP_SLUG, G12, G12_SLUG, G12_FLECHETTE, G12_MAGNUM, G12_EXPLOSIVE, G12_PHOSPHORUS,
		G26_FLARE, G26_FLARE_SUPPLY, G26_FLARE_WEAPON,
		G40_HE, G40_HEAT, G40_DEMO, G40_INC, G40_PHOSPHORUS,
		ROCKET_HE, ROCKET_HEAT, ROCKET_DEMO, ROCKET_INC, ROCKET_PHOSPHORUS,
		FLAME_DIESEL, FLAME_GAS, FLAME_NAPALM, FLAME_BALEFIRE,
		CAPACITOR, CAPACITOR_OVERCHARGE, CAPACITOR_IR,
		TAU_URANIUM,
		COIL_TUNGSTEN, COIL_FERROURANIUM,
		NUKE_STANDARD, NUKE_DEMO, NUKE_HIGH, NUKE_TOTS, NUKE_HIVE,
		G10, G10_SHRAPNEL, G10_DU, G10_SLUG,
		R762_HE, BMG50_HE, G10_EXPLOSIVE,
		P45_SP, P45_FMJ, P45_JHP, P45_AP, P45_DU,
		
		//ONLY ADD NEW ENTRIES AT THE BOTTOM TO AVOID SHIFTING!
		;
		
		/** used for creative tab order */
		public static EnumAmmo[] order = new EnumAmmo[] {
			STONE, STONE_AP, STONE_IRON, STONE_SHOT,
			M357_BP, M357_SP, M357_FMJ, M357_JHP, M357_AP, M357_EXPRESS,
			M44_BP, M44_SP, M44_FMJ, M44_JHP, M44_AP, M44_EXPRESS,
			P22_SP, P22_FMJ, P22_JHP, P22_AP,
			P9_SP, P9_FMJ, P9_JHP, P9_AP,
			P45_SP, P45_FMJ, P45_JHP, P45_AP, P45_DU,
			R556_SP, R556_FMJ, R556_JHP, R556_AP,
			R762_SP, R762_FMJ, R762_JHP, R762_AP, R762_DU, R762_HE,
			BMG50_SP, BMG50_FMJ, BMG50_JHP, BMG50_AP, BMG50_DU, BMG50_HE,
			B75, B75_INC, B75_EXP,
			G12_BP, G12_BP_MAGNUM, G12_BP_SLUG, G12, G12_SLUG, G12_FLECHETTE, G12_MAGNUM, G12_EXPLOSIVE, G12_PHOSPHORUS,
			G10, G10_SHRAPNEL, G10_DU, G10_SLUG, G10_EXPLOSIVE,
			G26_FLARE, G26_FLARE_SUPPLY, G26_FLARE_WEAPON,
			G40_HE, G40_HEAT, G40_DEMO, G40_INC, G40_PHOSPHORUS,
			ROCKET_HE, ROCKET_HEAT, ROCKET_DEMO, ROCKET_INC, ROCKET_PHOSPHORUS,
			FLAME_DIESEL, FLAME_GAS, FLAME_NAPALM, FLAME_BALEFIRE,
			CAPACITOR, CAPACITOR_OVERCHARGE, CAPACITOR_IR,
			TAU_URANIUM,
			COIL_TUNGSTEN, COIL_FERROURANIUM,
			NUKE_STANDARD, NUKE_DEMO, NUKE_HIGH, NUKE_TOTS, NUKE_HIVE,
		};
		
		public Enum[] getOrder() {
			return order;
		}
	}
	
	public static enum EnumAmmoSecret {
		FOLLY_SM, FOLLY_NUKE,
		M44_EQUESTRIAN, G12_EQUESTRIAN, BMG50_EQUESTRIAN,
		P35_800
	}
	
	public static enum EnumModTest {
		FIRERATE, DAMAGE, MULTI,
		OVERRIDE_2_5, OVERRIDE_5, OVERRIDE_7_5, OVERRIDE_10, OVERRIDE_12_5, OVERRIDE_15, OVERRIDE_20;
	}
	
	public static enum EnumModGeneric {
		IRON_DAMAGE, IRON_DURA,
		STEEL_DAMAGE, STEEL_DURA,
		DURA_DAMAGE, DURA_DURA,
		DESH_DAMAGE, DESH_DURA,
		WSTEEL_DAMAGE, WSTEEL_DURA,
		FERRO_DAMAGE, FERRO_DURA,
		TCALLOY_DAMAGE, TCALLOY_DURA,
		BIGMT_DAMAGE, BIGMT_DURA,
		BRONZE_DAMAGE, BRONZE_DURA,
	}
	
	public static enum EnumModSpecial {
		SILENCER, SCOPE, SAW, GREASEGUN, SLOWDOWN,
		SPEEDUP, CHOKE, SPEEDLOADER,
		FURNITURE_GREEN, FURNITURE_BLACK, BAYONET,
		STACK_MAG, SKIN_SATURNITE, LAS_SHOTGUN,
		LAS_CAPACITOR, LAS_AUTO
	}
	
	public static enum EnumModCaliber {
		P9, P45, P22, M357, M44, R556, R762, BMG50,
	}
}
