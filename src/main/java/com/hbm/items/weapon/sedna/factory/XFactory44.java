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
		m44_sp = new BulletConfig().setItem(EnumAmmo.M357_SP);
		m44_fmj = new BulletConfig().setItem(EnumAmmo.M357_FMJ).setDamage(0.8F).setArmorPiercing(0.1F);
		m44_jhp = new BulletConfig().setItem(EnumAmmo.M357_JHP).setDamage(1.5F).setArmorPiercing(-0.25F);
		m44_ap = new BulletConfig().setItem(EnumAmmo.M357_AP).setDoesPenetrate(true).setDamageFalloutByPen(false).setDamage(1.5F);
		m44_express = new BulletConfig().setItem(EnumAmmo.M357_EXPRESS).setDoesPenetrate(true).setDamage(1.5F).setArmorPiercing(0.1F).setWear(1.5F);

		ModItems.gun_henry = new ItemGunBaseNT(new GunConfig()
				.dura(300).draw(15).inspect(23).jam(45).crosshair(Crosshair.CIRCLE).smoke(true).orchestra(Orchestras.ORCHESTRA_HENRY)
				.rec(new Receiver(0)
						.dmg(12F).delay(16).reload(55).sound("hbm:weapon.fire.blackPowder", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 6).addConfigs(m44_sp, m44_fmj, m44_jhp, m44_ap, m44_express))
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
		case CYCLE_DRY: return new BusAnimation();
		case RELOAD: return new BusAnimation();
		case INSPECT: return new BusAnimation();
		case JAMMED: return new BusAnimation();
		}
		
		return null;
	};
}
