package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiFunction;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.mags.MagazineSingleReload;
import com.hbm.lib.RefStrings;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.item.ItemStack;

public class XFactory44 {

	public static BulletConfig m44_sp;
	public static BulletConfig m44_fmj;
	public static BulletConfig m44_jhp;
	public static BulletConfig m44_ap;
	public static BulletConfig m44_express;

	public static void init() {
		SpentCasing casing44 = new SpentCasing(CasingType.STRAIGHT).setColor(SpentCasing.COLOR_CASE_BRASS).setupSmoke(1F, 0.5D, 60, 20);
		m44_sp = new BulletConfig().setItem(EnumAmmo.M44_SP)
				.setCasing(casing44.clone().register("m44"));
		m44_fmj = new BulletConfig().setItem(EnumAmmo.M44_FMJ).setDamage(0.8F).setArmorPiercing(0.1F)
				.setCasing(casing44.clone().register("m44fmj"));
		m44_jhp = new BulletConfig().setItem(EnumAmmo.M44_JHP).setDamage(1.5F).setArmorPiercing(-0.25F)
				.setCasing(casing44.clone().register("m44jhp"));
		m44_ap = new BulletConfig().setItem(EnumAmmo.M44_AP).setDoesPenetrate(true).setDamageFalloutByPen(false).setDamage(1.5F)
				.setCasing(casing44.clone().setColor(SpentCasing.COLOR_CASE_44).register("m44ap"));
		m44_express = new BulletConfig().setItem(EnumAmmo.M44_EXPRESS).setDoesPenetrate(true).setDamage(1.5F).setArmorPiercing(0.1F).setWear(1.5F)
				.setCasing(casing44.clone().register("m44express"));

		ModItems.gun_henry = new ItemGunBaseNT(new GunConfig()
				.dura(300).draw(15).inspect(23).jam(45).reloadSequential(true).crosshair(Crosshair.CIRCLE).smoke(true).orchestra(Orchestras.ORCHESTRA_HENRY)
				.rec(new Receiver(0)
						.dmg(12F).delay(20).reload(25, 11, 18).sound("hbm:weapon.fire.blackPowder", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 14).addConfigs(m44_sp, m44_fmj, m44_jhp, m44_ap, m44_express))
						.offset(0.75, -0.0625, -0.3125D)
						.canFire(Lego.LAMBDA_STANDARD_CAN_FIRE).fire(Lego.LAMBDA_STANDARD_FIRE).recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.setupStandardConfiguration().anim(LAMBDA_HENRY_ANIMS)
				).setUnlocalizedName("gun_henry").setTextureName(RefStrings.MODID + ":gun_darter");
	}

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_HENRY_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(-90, 0, 0, 0).addPos(0, 0, -3, 350, IType.SIN_DOWN))
				.addBus("SIGHT", new BusAnimationSequence().addPos(80, 0, 0, 0).addPos(80, 0, 0, 500).addPos(0, 0, -3, 250, IType.SIN_DOWN));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, 0, 50).addPos(0, 0, -1, 50).addPos(0, 0, 0, 250))
				.addBus("SIGHT", new BusAnimationSequence().addPos(35, 0, 0, 100, IType.SIN_DOWN).addPos(0, 0, 0, 100, IType.SIN_FULL))
				.addBus("LEVER", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(-90, 0, 0, 200).addPos(0, 0, 0, 200))
				.addBus("TURN", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(0, 0, 45, 200, IType.SIN_DOWN).addPos(0, 0, 0, 200, IType.SIN_UP))
				.addBus("HAMMER", new BusAnimationSequence().addPos(30, 0, 0, 50).addPos(30, 0, 0, 550).addPos(0, 0, 0, 200));
		case CYCLE_DRY: return new BusAnimation()
				.addBus("LEVER", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(-90, 0, 0, 200).addPos(0, 0, 0, 200))
				.addBus("TURN", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(0, 0, 45, 200, IType.SIN_DOWN).addPos(0, 0, 0, 200, IType.SIN_UP))
				.addBus("HAMMER", new BusAnimationSequence().addPos(30, 0, 0, 50).addPos(30, 0, 0, 550).addPos(0, 0, 0, 200));
		case RELOAD: return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(-60, 0, 0, 400, IType.SIN_FULL))
				.addBus("TWIST", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, -90, 200, IType.SIN_FULL))
				.addBus("BULLET", new BusAnimationSequence().addPos(0, 0, 0, 700).addPos(3, 0, -6, 0).addPos(0, 0, 1, 300, IType.SIN_FULL).addPos(0, 0, 0, 250, IType.SIN_FULL));
		case RELOAD_CYCLE: return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(-60, 0, 0, 0))
				.addBus("TWIST", new BusAnimationSequence().addPos(0, 0, -90, 0))
				.addBus("BULLET", new BusAnimationSequence().addPos(3, 0, -6, 0).addPos(0, 0, 1, 300, IType.SIN_FULL).addPos(0, 0, 0, 250, IType.SIN_FULL));
		case RELOAD_END: return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(-60, 0, 0, 0).addPos(-60, 0, 0, 300).addPos(0, 0, 0, 400, IType.SIN_FULL))
				.addBus("TWIST", new BusAnimationSequence().addPos(0, 0, -90, 0).addPos(0, 0, 0, 200, IType.SIN_FULL))
				.addBus("LEVER", new BusAnimationSequence().addPos(0, 0, 0, 700).addPos(-90, 0, 0, 200).addPos(0, 0, 0, 200))
				.addBus("TURN", new BusAnimationSequence().addPos(0, 0, 0, 700).addPos(0, 0, 45, 200, IType.SIN_DOWN).addPos(0, 0, 0, 200, IType.SIN_UP));
		case JAMMED: return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(-60, 0, 0, 0).addPos(-60, 0, 0, 300).addPos(0, 0, 0, 400, IType.SIN_FULL))
				.addBus("TWIST", new BusAnimationSequence().addPos(0, 0, -90, 0).addPos(0, 0, 0, 200, IType.SIN_FULL))
				.addBus("LEVER", new BusAnimationSequence().addPos(0, 0, 0, 700).addPos(-90, 0, 0, 200).addPos(0, 0, 0, 200).addPos(0, 0, 0, 500).addPos(-90, 0, 0, 200).addPos(0, 0, 0, 200).addPos(0, 0, 0, 200).addPos(-90, 0, 0, 200).addPos(0, 0, 0, 200))
				.addBus("TURN", new BusAnimationSequence().addPos(0, 0, 0, 700).addPos(0, 0, 45, 200, IType.SIN_DOWN).addPos(0, 0, 0, 200, IType.SIN_UP).addPos(0, 0, 0, 500).addPos(0, 0, 45, 200, IType.SIN_FULL).addPos(0, 0, 45, 600).addPos(0, 0, 0, 200, IType.SIN_FULL));
		case INSPECT: return new BusAnimation()
				.addBus("YEET", new BusAnimationSequence().addPos(0, 2, 0, 200, IType.SIN_DOWN).addPos(0, 0, 0, 200, IType.SIN_UP))
				.addBus("ROLL", new BusAnimationSequence().addPos(0, 0, 360, 400));
		}
		
		return null;
	};
}
