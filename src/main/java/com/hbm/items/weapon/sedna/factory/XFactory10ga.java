package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumCasingType;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.item.ItemStack;

public class XFactory10ga {

	public static BulletConfig g10;
	public static BulletConfig g10_shrapnel;
	public static BulletConfig g10_du;
	public static BulletConfig g10_slug;

	public static void init() {
		
		g10 = new BulletConfig().setItem(EnumAmmo.G10).setCasing(EnumCasingType.BUCKSHOT_ADVANCED, 4).setProjectiles(10).setDamage(1F/10F).setSpread(0.05F).setRicochetAngle(15).setThresholdNegation(5F).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0xB52B2B, SpentCasing.COLOR_CASE_12GA).setScale(1F).register("10GA"));
		g10_shrapnel = new BulletConfig().setItem(EnumAmmo.G10_SHRAPNEL).setCasing(EnumCasingType.BUCKSHOT_ADVANCED, 4).setProjectiles(10).setDamage(1F/10F).setSpread(0.05F).setRicochetAngle(90).setRicochetCount(15).setThresholdNegation(5F).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0xE5DD00, SpentCasing.COLOR_CASE_12GA).setScale(1F).register("10GAShrapnel"));
		g10_du = new BulletConfig().setItem(EnumAmmo.G10_DU).setCasing(EnumCasingType.BUCKSHOT_ADVANCED, 4).setProjectiles(10).setDamage(1F/4F).setSpread(0.05F).setRicochetAngle(15).setThresholdNegation(10F).setArmorPiercing(0.2F).setDoesPenetrate(true).setDamageFalloutByPen(false).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0x538D53, SpentCasing.COLOR_CASE_12GA).setScale(1F).register("10GADU"));
		g10_slug = new BulletConfig().setItem(EnumAmmo.G10_SLUG).setCasing(EnumCasingType.BUCKSHOT_ADVANCED, 4).setRicochetAngle(15).setThresholdNegation(10F).setArmorPiercing(0.1F).setDoesPenetrate(true).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0x808080, SpentCasing.COLOR_CASE_12GA).setScale(1F).register("10GASlug"));

		ModItems.gun_double_barrel = new ItemGunBaseNT(WeaponQuality.SPECIAL, new GunConfig()
				.dura(1000).draw(10).inspect(39).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(30F).rounds(2).delay(10).reload(41).reloadOnEmpty(true).sound("hbm:weapon.fire.shotgun", 1.0F, 0.9F)
						.mag(new MagazineFullReload(0, 2).addConfigs(g10, g10_shrapnel, g10_du, g10_slug))
						.offset(0.75, -0.0625, -0.1875)
						.setupStandardFire().recoil(LAMBDA_RECOIL_DOUBLE_BARREL))
				.setupStandardConfiguration()
				.anim(LAMBDA_DOUBLE_BARREL_ANIMS).orchestra(Orchestras.ORCHESTRA_DOUBLE_BARREL)
				).setUnlocalizedName("gun_double_barrel");
		ModItems.gun_double_barrel_sacred_dragon = new ItemGunBaseNT(WeaponQuality.B_SIDE, new GunConfig()
				.dura(6000).draw(10).inspect(39).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(45F).spreadAmmo(1.35F).rounds(2).delay(10).reload(41).reloadOnEmpty(true).sound("hbm:weapon.fire.shotgun", 1.0F, 0.9F)
						.mag(new MagazineFullReload(0, 2).addConfigs(g10, g10_shrapnel, g10_du, g10_slug))
						.offset(0.75, -0.0625, -0.1875)
						.setupStandardFire().recoil(LAMBDA_RECOIL_DOUBLE_BARREL))
				.setupStandardConfiguration()
				.anim(LAMBDA_DOUBLE_BARREL_ANIMS).orchestra(Orchestras.ORCHESTRA_DOUBLE_BARREL)
				).setUnlocalizedName("gun_double_barrel_sacred_dragon");
	}
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_DOUBLE_BARREL = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil(10, (float) (ctx.getPlayer().getRNG().nextGaussian() * 1.5));
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_DOUBLE_BARREL_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(-60, 0, 0, 0).addPos(0, 0, -3, 500, IType.SIN_DOWN));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, -1, 50).addPos(0, 0, 0, 250))
				.addBus("BUCKLE", new BusAnimationSequence().addPos(0, -60, 0, 50).addPos(0, 0, 0, 250));
		case RELOAD: return new BusAnimation()
				.addBus("TURN", new BusAnimationSequence()
						.addPos(0, 30, 0, 350, IType.SIN_FULL)
						.addPos(0, 30, 0, 1150)
						.addPos(0, 0, 0, 350, IType.SIN_FULL))
				.addBus("LEVER", new BusAnimationSequence()
						.addPos(0, 0, 0, 250)
						.addPos(0, 0, -90, 100, IType.SIN_FULL)
						.addPos(0, 0, -90, 1300)
						.addPos(0, 0, 0, 100, IType.SIN_FULL))
				.addBus("BARREL", new BusAnimationSequence()
						.addPos(0, 0, 0, 300)
						.addPos(60, 0, 0, 150, IType.SIN_UP)
						.addPos(60, 0, 0, 1150)
						.addPos(0, 0, 0, 150, IType.SIN_UP))
				.addBus("LIFT", new BusAnimationSequence()
						.addPos(0, 0, 0, 350)
						.addPos(-5, 0, 0, 150, IType.SIN_FULL)
						.addPos(0, 0, 0, 100, IType.SIN_FULL)
						.addPos(0, 0, 0, 700)
						.addPos(-5, 0, 0, 100, IType.SIN_FULL)
						.addPos(0, 0, 0, 100, IType.SIN_UP) //1500
						.addPos(45, 0, 0, 150)
						.addPos(45, 0, 0, 150)
						.addPos(-5, 0, 0, 150, IType.SIN_DOWN)
						.addPos(0, 0, 0, 100, IType.SIN_FULL)) //2050
				.addBus("SHELLS", new BusAnimationSequence()
						.addPos(0, 0, 0, 450)
						.addPos(0, 0, -2.5, 100)
						.addPos(0, -5, -5, 350, IType.SIN_DOWN)
						.addPos(0, -3, -2, 0)
						.addPos(0, 0, -2, 250)
						.addPos(0, 0, 0, 150, IType.SIN_UP)) //1300
				.addBus("SHELL_FLIP", new BusAnimationSequence().addPos(0, 0, 0, 450).addPos(-360, 0, 0, 450).addPos(0, 0, 0, 0));
		case INSPECT: return new BusAnimation()
				.addBus("LEVER", new BusAnimationSequence()
						.addPos(0, 0, 0, 250)
						.addPos(0, 0, -90, 100, IType.SIN_FULL)
						.addPos(0, 0, -90, 800)
						.addPos(0, 0, 0, 100, IType.SIN_FULL))
				.addBus("BARREL", new BusAnimationSequence()
						.addPos(0, 0, 0, 300)
						.addPos(60, 0, 0, 150, IType.SIN_UP)
						.addPos(60, 0, 0, 650)
						.addPos(0, 0, 0, 150, IType.SIN_UP))
				.addBus("LIFT", new BusAnimationSequence()
						.addPos(0, 0, 0, 350)
						.addPos(-5, 0, 0, 150, IType.SIN_FULL)
						.addPos(0, 0, 0, 100, IType.SIN_FULL)
						.addPos(0, 0, 0, 200)
						.addPos(-5, 0, 0, 100, IType.SIN_FULL)
						.addPos(0, 0, 0, 100, IType.SIN_UP) //1500
						.addPos(45, 0, 0, 150)
						.addPos(45, 0, 0, 150)
						.addPos(-5, 0, 0, 150, IType.SIN_DOWN)
						.addPos(0, 0, 0, 100, IType.SIN_FULL));
		}
		
		return null;
	};
}
