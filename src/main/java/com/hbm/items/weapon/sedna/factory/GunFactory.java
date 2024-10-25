package com.hbm.items.weapon.sedna.factory;

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
	public static BulletConfig ammo_debug_buckshot;
	
	public static SpentCasing CASING44 = new SpentCasing(CasingType.STRAIGHT).setScale(1.5F, 1.0F, 1.5F).setBounceMotion(0.01F, 0.05F).setColor(SpentCasing.COLOR_CASE_44);

	public static void init() {
		
		/// AMMO ITEMS ///
		ModItems.ammo_debug = new Item().setUnlocalizedName("ammo_debug").setTextureName(RefStrings.MODID + ":ammo_45");
		ModItems.ammo_standard = new ItemEnumMulti(EnumAmmo.class, true, true).setUnlocalizedName("ammo_standard").setTextureName(RefStrings.MODID + ":ammo_standard");

		/// BULLLET CFGS ///
		ammo_debug = new BulletConfig().setItem(ModItems.ammo_debug).setSpread(0.01F).setRicochetAngle(45).setCasing(CASING44.clone().register("DEBUG0"));
		ammo_debug_buckshot = new BulletConfig().setItem(ModItems.ammo_12gauge).setSpread(0.1F).setRicochetAngle(45).setProjectiles(6, 6).setCasing(CASING44.clone().register("DEBUG1"));

		/// GUNS ///
		ModItems.gun_debug = new ItemGunBaseNT(WeaponQuality.DEBUG, new GunConfig()
				.dura(600F).draw(15).inspect(23).crosshair(Crosshair.L_CLASSIC).smoke(Lego.LAMBDA_STANDARD_SMOKE).orchestra(Orchestras.DEBUG_ORCHESTRA)
				.rec(new Receiver(0)
						.dmg(10F).delay(14).reload(46).jam(23).sound("hbm:weapon.44Shoot", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 12).addConfigs(ammo_debug, ammo_debug_buckshot))
						.offset(0.75, -0.0625, -0.3125D)
						.canFire(Lego.LAMBDA_STANDARD_CAN_FIRE).fire(Lego.LAMBDA_STANDARD_FIRE))
				.pp(Lego.LAMBDA_STANDARD_CLICK_PRIMARY) .pr(Lego.LAMBDA_STANDARD_RELOAD) .pt(Lego.LAMBDA_TOGGLE_AIM)
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

		/// PROXY BULLSHIT ///
		MainRegistry.proxy.registerGunCfg();
	}
	
	public static enum EnumAmmo {
		STONE, STONE_AP, STONE_IRON, STONE_SHOT,
		M357_SP, M357_FMJ, M357_JHP, M357_AP, M357_EXPRESS,
		M44_SP, M44_FMJ, M44_JHP, M44_AP, M44_EXPRESS,
		P22_SP, P22_FMJ, P22_JHP, P22_AP,
		P9_SP, P9_FMJ, P9_JHP, P9_AP,
		R556_SP, R556_FMJ, R556_JHP, R556_AP,
		R762_SP, R762_FMJ, R762_JHP, R762_AP, R762_DU,
		BMG50_SP, BMG50_FMJ, BMG50_JHP, BMG50_AP, BMG50_DU,
		G12_BP, G12_BP_MAGNUM, G12_BP_SLUG, G12, G12_SLUG, G12_FLECHETTE, G12_MAGNUM, G12_EXPLOSIVE, G12_PHOSPHORUS, G12_ANTHRAX,
		G40_FLARE, G40,
		ROCKET_HE, ROCKET_HEAT,
		FLAME_DIESEL,
		M44_EQUESTRIAN, G12_EQUESTRIAN, BMG50_EQUESTRIAN
	}
}
