package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiFunction;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.lib.RefStrings;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.item.ItemStack;

public class XFactory9mm {

	public static BulletConfig p9_sp;
	public static BulletConfig p9_fmj;
	public static BulletConfig p9_jhp;
	public static BulletConfig p9_ap;

	public static void init() {
		SpentCasing casing9 = new SpentCasing(CasingType.STRAIGHT).setColor(SpentCasing.COLOR_CASE_BRASS);
		p9_sp = new BulletConfig().setItem(EnumAmmo.P9_SP)
				.setCasing(casing9.clone().register("p9"));
		p9_fmj = new BulletConfig().setItem(EnumAmmo.P9_FMJ).setDamage(0.8F).setArmorPiercing(0.1F)
				.setCasing(casing9.clone().register("p9fmj"));
		p9_jhp = new BulletConfig().setItem(EnumAmmo.P9_JHP).setDamage(1.5F).setArmorPiercing(-0.25F)
				.setCasing(casing9.clone().register("p9jhp"));
		p9_ap = new BulletConfig().setItem(EnumAmmo.P9_AP).setDoesPenetrate(true).setDamageFalloutByPen(false).setDamage(1.5F)
				.setCasing(casing9.clone().setColor(SpentCasing.COLOR_CASE_44).register("p9ap"));

		ModItems.gun_greasegun = new ItemGunBaseNT(new GunConfig()
				.dura(300).draw(15).inspect(23).jam(45).crosshair(Crosshair.L_CIRCLE).smoke(true).orchestra(Orchestras.ORCHESTRA_GREASEGUN)
				.rec(new Receiver(0)
						.dmg(5F).delay(5).auto(true).spread(0.015F).reload(25, 11, 18).sound("hbm:weapon.fire.blackPowder", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 30).addConfigs(p9_sp, p9_fmj, p9_jhp, p9_ap))
						.offset(0.75, -0.0625, -0.3125D)
						.canFire(Lego.LAMBDA_STANDARD_CAN_FIRE).fire(Lego.LAMBDA_STANDARD_FIRE).recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.setupStandardConfiguration().anim(LAMBDA_GREASEGUN_ANIMS)
				).setUnlocalizedName("gun_greasegun").setTextureName(RefStrings.MODID + ":gun_darter");
	}

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_GREASEGUN_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation();
		case CYCLE: return new BusAnimation();
		case CYCLE_DRY: return new BusAnimation();
		case RELOAD: return new BusAnimation();
		case RELOAD_CYCLE: return new BusAnimation();
		case RELOAD_END: return new BusAnimation();
		case JAMMED: return new BusAnimation();
		case INSPECT: return new BusAnimation();
		}
		
		return null;
	};
}
