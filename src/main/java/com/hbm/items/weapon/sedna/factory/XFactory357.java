package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.item.ItemStack;

public class XFactory357 {

	public static BulletConfig m357_bp;
	public static BulletConfig m357_sp;
	public static BulletConfig m357_fmj;
	public static BulletConfig m357_jhp;
	public static BulletConfig m357_ap;
	public static BulletConfig m357_express;

	public static void init() {
		m357_bp = new BulletConfig().setItem(EnumAmmo.M357_BP).setDamage(0.75F).setBlackPowder(true);
		m357_sp = new BulletConfig().setItem(EnumAmmo.M357_SP);
		m357_fmj = new BulletConfig().setItem(EnumAmmo.M357_FMJ).setDamage(0.8F).setThresholdNegation(2F).setArmorPiercing(0.1F);
		m357_jhp = new BulletConfig().setItem(EnumAmmo.M357_JHP).setDamage(1.5F).setHeadshot(1.5F).setArmorPiercing(-0.25F);
		m357_ap = new BulletConfig().setItem(EnumAmmo.M357_AP).setDoesPenetrate(true).setDamageFalloutByPen(false).setDamage(1.5F).setThresholdNegation(5F).setArmorPiercing(0.15F);
		m357_express = new BulletConfig().setItem(EnumAmmo.M357_EXPRESS).setDoesPenetrate(true).setDamage(1.5F).setThresholdNegation(2F).setArmorPiercing(0.1F).setWear(1.5F);
		
		ModItems.gun_light_revolver = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(300).draw(4).inspect(23).crosshair(Crosshair.CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(7.5F).delay(16).reload(55).jam(45).sound("hbm:weapon.fire.pistol", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 6).addConfigs(m357_bp, m357_sp, m357_fmj, m357_jhp, m357_ap, m357_express))
						.offset(0.75, -0.0625, -0.3125D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_ATLAS))
				.setupStandardConfiguration()
				.anim(LAMBDA_ATLAS_ANIMS).orchestra(Orchestras.ORCHESTRA_ATLAS)
				).setUnlocalizedName("gun_light_revolver");
		ModItems.gun_light_revolver_atlas = new ItemGunBaseNT(WeaponQuality.B_SIDE, new GunConfig()
				.dura(300).draw(4).inspect(23).crosshair(Crosshair.CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(12.5F).delay(16).reload(55).jam(45).sound("hbm:weapon.fire.pistol", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 6).addConfigs(m357_bp, m357_sp, m357_fmj, m357_jhp, m357_ap, m357_express))
						.offset(0.75, -0.0625, -0.3125D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_ATLAS))
				.setupStandardConfiguration()
				.anim(LAMBDA_ATLAS_ANIMS).orchestra(Orchestras.ORCHESTRA_ATLAS)
				).setUnlocalizedName("gun_light_revolver_atlas");
		ModItems.gun_light_revolver_dani = new ItemGunBaseNT(WeaponQuality.LEGENDARY,
				new GunConfig().dura(30_000).draw(20).inspect(23).crosshair(Crosshair.CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(15F).delay(11).reload(55).jam(45).sound("hbm:weapon.fire.pistol", 1.0F, 1.1F)
						.mag(new MagazineFullReload(0, 6).addConfigs(m357_bp, m357_sp, m357_fmj, m357_jhp, m357_ap, m357_express))
						.offset(0.75, -0.0625, 0.3125D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_DANI))
				.pp(Lego.LAMBDA_STANDARD_CLICK_PRIMARY).pr(Lego.LAMBDA_STANDARD_RELOAD)
				.decider(GunStateDecider.LAMBDA_STANDARD_DECIDER)
				.anim(LAMBDA_DANI_ANIMS).orchestra(Orchestras.ORCHESTRA_DANI),
				new GunConfig().dura(30_000).draw(20).inspect(23).crosshair(Crosshair.CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(15F).delay(11).reload(55).jam(45).sound("hbm:weapon.fire.pistol", 1.0F, 0.9F)
						.mag(new MagazineFullReload(1, 6).addConfigs(m357_bp, m357_sp, m357_fmj, m357_jhp, m357_ap, m357_express))
						.offset(0.75, -0.0625, -0.3125D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_DANI))
				.ps(Lego.LAMBDA_STANDARD_CLICK_PRIMARY).pr(Lego.LAMBDA_STANDARD_RELOAD)
				.decider(GunStateDecider.LAMBDA_STANDARD_DECIDER)
				.anim(LAMBDA_DANI_ANIMS).orchestra(Orchestras.ORCHESTRA_DANI)
				).setUnlocalizedName("gun_light_revolver_dani");
	}
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_ATLAS = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil(10, (float) (ctx.getPlayer().getRNG().nextGaussian() * 1.5));
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_DANI = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil(5, (float) (ctx.getPlayer().getRNG().nextGaussian() * 0.75));
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_ATLAS_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(-90, 0, 0, 0).addPos(0, 0, 0, 350, IType.SIN_DOWN));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, 0, 50).addPos(0, 0, -3, 50).addPos(0, 0, 0, 250))
				.addBus("HAMMER", new BusAnimationSequence().addPos(0, 0, 1, 50).addPos(0, 0, 1, 550).addPos(0, 0, 0, 200))
				.addBus("DRUM", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(0, 0, 1, 200));
		case CYCLE_DRY: return new BusAnimation()
				.addBus("HAMMER", new BusAnimationSequence().addPos(0, 0, 1, 50).addPos(0, 0, 1, 550).addPos(0, 0, 0, 200))
				.addBus("DRUM", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(0, 0, 1, 200));
		case RELOAD: return new BusAnimation()
				.addBus("LATCH", new BusAnimationSequence().addPos(0, 0, 90, 300).addPos(0, 0, 90, 2000).addPos(0, 0, 0, 150))
				.addBus("FRONT", new BusAnimationSequence().addPos(0, 0, 0, 200).addPos(0, 0, 45, 150).addPos(0, 0, 45, 2000).addPos(0, 0, 0, 75))
				.addBus("RELOAD_ROT", new BusAnimationSequence().addPos(0, 0, 0, 300).addPos(60, 0, 0, 500).addPos(60, 0, 0, 500).addPos(0, -90, -90, 0).addPos(0, -90, -90, 600).addPos(0, 0, 0, 300).addPos(0, 0, 0, 100).addPos(-45, 0, 0, 50).addPos(-45, 0, 0, 100).addPos(0, 0, 0, 300))
				.addBus("RELOAD_MOVE", new BusAnimationSequence().addPos(0, 0, 0, 300).addPos(0, -15, 0, 1000).addPos(0, 0, 0, 450))
				.addBus("DRUM_PUSH", new BusAnimationSequence().addPos(0, 0, 0, 1600).addPos(0, 0, -5, 0).addPos(0, 0, 0, 300));
		case INSPECT: return new BusAnimation()
				.addBus("LATCH", new BusAnimationSequence().addPos(0, 0, 90, 300).addPos(0, 0, 90, 1000).addPos(0, 0, 0, 150))
				.addBus("FRONT", new BusAnimationSequence().addPos(0, 0, 0, 200).addPos(0, 0, 45, 150).addPos(0, 0, 45, 1000).addPos(0, 0, 0, 75))
				.addBus("RELOAD_ROT", new BusAnimationSequence().addPos(0, 0, 0, 300).addPos(45, 0, 0, 500, IType.SIN_FULL).addPos(45, 0, 0, 500).addPos(-45, 0, 0, 50).addPos(-45, 0, 0, 100).addPos(0, 0, 0, 300))
				.addBus("RELOAD_MOVE", new BusAnimationSequence().addPos(0, 0, 0, 300).addPos(0, -2.5, 0, 500, IType.SIN_FULL).addPos(0, -2.5, 0, 500).addPos(0, 0, 0, 350));
		case JAMMED: return new BusAnimation()
				.addBus("LATCH", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, 90, 300).addPos(0, 0, 90, 1000).addPos(0, 0, 0, 150))
				.addBus("FRONT", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, 0, 200).addPos(0, 0, 45, 150).addPos(0, 0, 45, 1000).addPos(0, 0, 0, 75))
				.addBus("RELOAD_ROT", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, 0, 300).addPos(45, 0, 0, 500, IType.SIN_FULL).addPos(45, 0, 0, 500).addPos(-45, 0, 0, 50).addPos(-45, 0, 0, 100).addPos(0, 0, 0, 300))
				.addBus("RELOAD_MOVE", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, 0, 300).addPos(0, -2.5, 0, 500, IType.SIN_FULL).addPos(0, -2.5, 0, 500).addPos(0, 0, 0, 350));
		}
		
		return null;
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_DANI_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation().addBus("EQUIP", new BusAnimationSequence().addPos(360 * 3, 0, 0, 1000, IType.SIN_DOWN));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, 0, 50).addPos(0, 0, -3, 50).addPos(0, 0, 0, 250))
				.addBus("HAMMER", new BusAnimationSequence().addPos(0, 0, 1, 50).addPos(0, 0, 1, 300).addPos(0, 0, 0, 200))
				.addBus("DRUM", new BusAnimationSequence().addPos(0, 0, 0, 350).addPos(0, 0, 1, 200));
		case CYCLE_DRY: return new BusAnimation()
				.addBus("HAMMER", new BusAnimationSequence().addPos(0, 0, 1, 50).addPos(0, 0, 1, 200).addPos(0, 0, 0, 200))
				.addBus("DRUM", new BusAnimationSequence().addPos(0, 0, 0, 350).addPos(0, 0, 1, 200));
		}
		
		return LAMBDA_ATLAS_ANIMS.apply(stack, type);
	};
}
