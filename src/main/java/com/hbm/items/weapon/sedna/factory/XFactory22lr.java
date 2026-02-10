package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.hbm.config.ClientConfig;
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
import com.hbm.items.weapon.sedna.mods.XWeaponModManager;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.anim.AnimationEnums.GunAnimation;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;

import net.minecraft.item.ItemStack;

public class XFactory22lr {

	public static BulletConfig p22_sp;
	public static BulletConfig p22_fmj;
	public static BulletConfig p22_jhp;
	public static BulletConfig p22_ap;

	public static void init() {
		SpentCasing casing22 = new SpentCasing(CasingType.STRAIGHT).setColor(SpentCasing.COLOR_CASE_BRASS).setScale(0.5F);
		p22_sp = new BulletConfig().setItem(EnumAmmo.P22_SP).setCasing(EnumCasingType.SMALL, 24).setKnockback(0F)
				.setCasing(casing22.clone().register("p22"));
		p22_fmj = new BulletConfig().setItem(EnumAmmo.P22_FMJ).setCasing(EnumCasingType.SMALL, 24).setKnockback(0F).setDamage(0.8F).setThresholdNegation(1F).setArmorPiercing(0.1F)
				.setCasing(casing22.clone().register("p22fmj"));
		p22_jhp = new BulletConfig().setItem(EnumAmmo.P22_JHP).setCasing(EnumCasingType.SMALL, 24).setKnockback(0F).setDamage(1.5F).setHeadshot(1.5F).setArmorPiercing(-0.25F)
				.setCasing(casing22.clone().register("p22jhp"));
		p22_ap = new BulletConfig().setItem(EnumAmmo.P22_AP).setCasing(EnumCasingType.SMALL_STEEL, 24).setKnockback(0F).setDoesPenetrate(true).setDamageFalloffByPen(false).setDamage(1.25F).setThresholdNegation(2.5F).setArmorPiercing(0.15F)
				.setCasing(casing22.clone().setColor(SpentCasing.COLOR_CASE_44).register("p22ap"));

		ModItems.gun_am180 = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(177 * 25).draw(15).inspect(38).crosshair(Crosshair.L_CIRCLE).smoke(LAMBDA_SMOKE)
				.rec(new Receiver(0)
						.dmg(2F).delay(1).dry(10).auto(true).spread(0.01F).reload(66).jam(30).sound("hbm:weapon.fire.greaseGun", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 177).addConfigs(p22_sp, p22_fmj, p22_jhp, p22_ap))
						.offset(1, -0.0625 * 1.5, -0.1875D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_AM180))
				.setupStandardConfiguration()
				.anim(LAMBDA_AM180_ANIMS).orchestra(Orchestras.ORCHESTRA_AM180)
				).setDefaultAmmo(EnumAmmo.P22_SP, 35).setNameMutator(LAMBDA_NAME_SILENCED)
				.setUnlocalizedName("gun_am180");

		ModItems.gun_star_f = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(15 * 25).draw(15).inspect(38).crosshair(Crosshair.CIRCLE).smoke(LAMBDA_SMOKE)
				.rec(new Receiver(0)
						.dmg(12.5F).delay(5).dry(17).spread(0.01F).reload(40).jam(32).sound("hbm:weapon.fire.pistolLight", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 15).addConfigs(p22_sp, p22_fmj, p22_jhp, p22_ap))
						.offset(1, -0.0625 * 1.5, -0.1875D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_STAR_F))
				.setupStandardConfiguration()
				.anim(LAMBDA_STAR_F_ANIMS).orchestra(Orchestras.ORCHESTRA_STAR_F)
				).setDefaultAmmo(EnumAmmo.P22_SP, 15).setNameMutator(LAMBDA_NAME_SILENCED)
				.setUnlocalizedName("gun_star_f");
	}

	public static Function<ItemStack, String> LAMBDA_NAME_SILENCED = (stack) -> {
		if(XWeaponModManager.hasUpgrade(stack, 0, XWeaponModManager.ID_SILENCER)) return stack.getUnlocalizedName() + "_silenced";
		return null;
	};

	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_SMOKE = (stack, ctx) -> {
		Lego.handleStandardSmoke(ctx.entity, stack, 3000, 0.05D, 1.1D, 0);
	};

	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_AM180 = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil((float) (ctx.getPlayer().getRNG().nextGaussian() * 0.25), (float) (ctx.getPlayer().getRNG().nextGaussian() * 0.25));
	};

	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_STAR_F = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil(2.5F, (float) (ctx.getPlayer().getRNG().nextGaussian() * 0.5));
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, GunAnimation, BusAnimation> LAMBDA_AM180_ANIMS = (stack, type) -> {
		if(ClientConfig.GUN_ANIMS_LEGACY.get()) {
			switch(type) {
			case EQUIP: return new BusAnimation()
					.addBus("EQUIP", new BusAnimationSequence().addPos(45, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_FULL));
			case CYCLE: return new BusAnimation()
					.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, ItemGunBaseNT.getIsAiming(stack) ? -0.125 : -0.25, 15, IType.SIN_DOWN).addPos(0, 0, 0, 35, IType.SIN_FULL));
			case CYCLE_DRY: return new BusAnimation()
					.addBus("BOLT", new BusAnimationSequence().addPos(0, 0, 0, 550).addPos(0, 0, -1.5, 100, IType.SIN_UP).addPos(0, 0, 0, 100, IType.SIN_UP))
					.addBus("TURN", new BusAnimationSequence().addPos(0, 0, 0, 300).addPos(0, 0, 15, 250, IType.SIN_FULL).addPos(0, 0, 15, 400).addPos(0, 0, 0, 250, IType.SIN_FULL));
			case RELOAD:
				return new BusAnimation()
					.addBus("MAGTURN", new BusAnimationSequence().addPos(15, 0, 0, 250, IType.SIN_FULL).addPos(15, 0, 0, 250).addPos(15, 0, 70, 300, IType.SIN_FULL).addPos(15, 0, 0, 0).addPos(15, 0, 0, 750).addPos(0, 0, 0, 250, IType.SIN_FULL))
					.addBus("MAG", new BusAnimationSequence().addPos(0, 0, 0, 250).addPos(2, 0, -4, 250, IType.SIN_FULL).addPos(-10, 2, -4, 300, IType.SIN_UP).addPos(3, -6, -4, 0).addPos(2, 0, -4, 500, IType.SIN_FULL).addPos(0, 0, 0, 250, IType.SIN_FULL))
					.addBus("BOLT", new BusAnimationSequence().addPos(0, 0, 0, 2250).addPos(0, 0, -1.5, 100, IType.SIN_UP).addPos(0, 0, 0, 100, IType.SIN_UP))
					.addBus("TURN", new BusAnimationSequence().addPos(0, 0, 0, 2000).addPos(0, 0, 15, 250, IType.SIN_FULL).addPos(0, 0, 15, 400).addPos(0, 0, 0, 250, IType.SIN_FULL));
			case JAMMED: return new BusAnimation()
					.addBus("BOLT", new BusAnimationSequence().addPos(0, 0, 0, 750).addPos(0, 0, -1.5, 100, IType.SIN_UP).addPos(0, 0, 0, 100, IType.SIN_UP))
					.addBus("TURN", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, 45, 250, IType.SIN_FULL).addPos(0, 0, 45, 400).addPos(0, 0, 0, 250, IType.SIN_FULL));
			case INSPECT: return new BusAnimation()
					.addBus("MAGTURN", new BusAnimationSequence().addPos(15, 0, 0, 250, IType.SIN_FULL).addPos(15, 0, 0, 1400).addPos(0, 0, 0, 250, IType.SIN_FULL))
					.addBus("MAG", new BusAnimationSequence().addPos(0, 0, 0, 200).addPos(4, -1, -4, 200, IType.SIN_FULL).addPos(4, -1.5, -4, 50).addPos(4, 0, -4, 100).addPos(4, 6, -4, 250, IType.SIN_DOWN).addPos(4, 0, -4, 150, IType.SIN_UP).addPos(4, -1, -4, 100, IType.SIN_DOWN).addPos(4, -1, -4, 250).addPos(0, 0, 0, 250, IType.SIN_FULL))
					.addBus("MAGSPIN", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(-400, 0, 0, 500, IType.SIN_FULL).addPos(-400, 0, 0, 250).addPos(-360, 0, 0, 250));
			}
		} else {
			switch(type) {
			case EQUIP: return new BusAnimation()
					.addBus("EQUIP", new BusAnimationSequence().addPos(45, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_FULL));
			case CYCLE: return ResourceManager.am180_anim.get("Fire");
			case CYCLE_DRY: return ResourceManager.am180_anim.get("FireDry");
			case RELOAD: return ResourceManager.am180_anim.get("Reload");
			case JAMMED: return ResourceManager.am180_anim.get("Jammed");
			case INSPECT: return ResourceManager.am180_anim.get("Inspect");
			}
		}

		return null;
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, GunAnimation, BusAnimation> LAMBDA_STAR_F_ANIMS = (stack, type) -> {
		int ammo = ((ItemGunBaseNT) stack.getItem()).getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, MainRegistry.proxy.me().inventory);
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(45, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_DOWN));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, 0, 50).addPos(0, 0, ItemGunBaseNT.getIsAiming(stack) ? -0.125 : -0.5, 15, IType.SIN_DOWN).addPos(0, 0, 0, 35, IType.SIN_FULL))
				.addBus("SLIDE", new BusAnimationSequence().addPos(0, 0, 0, 50).addPos(0, 0, ItemGunBaseNT.getIsAiming(stack) ? -0.5 : -1, 25, IType.SIN_DOWN).addPos(0, 0, 0, 75, IType.SIN_UP))
				.addBus("HAMMER", new BusAnimationSequence().addPos(1, 0, 0, 50, IType.SIN_UP).addPos(0, 0, 0, 50, IType.SIN_DOWN))
				.addBus("BULLET", ammo <= 1 ? new BusAnimationSequence().setPos(100, 0, 0) : new BusAnimationSequence().addPos(0, 0, 0, 90).addPos(0, 0.5, 2.25, 50));
		case CYCLE_DRY: return new BusAnimation()
				.addBus("HAMMER", new BusAnimationSequence().addPos(1, 0, 0, 50, IType.SIN_UP).hold(450).addPos(0, 0, 0, 50, IType.SIN_DOWN))
				.addBus("SLIDE", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, ItemGunBaseNT.getIsAiming(stack) ? -0.5 : -1, 100, IType.SIN_FULL).hold(100).addPos(0, 0, 0, 75, IType.SIN_UP))
				.addBus("EQUIP", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(-3, 0, 0, 175, IType.SIN_DOWN).addPos(0, 0, 0, 100, IType.SIN_FULL))
				.addBus("BULLET", new BusAnimationSequence().setPos(100, 0, 0));
		case RELOAD:
			return new BusAnimation()
					.addBus("TILT", new BusAnimationSequence().addPos(-30, 0, 0, 250, IType.SIN_FULL).hold(1500).addPos(0, 0, 0, 250, IType.SIN_FULL))
					.addBus("SLIDE", new BusAnimationSequence().addPos(0, 0, 0, 250).addPos(0, 0, -1, 100, IType.SIN_FULL).hold(1125).addPos(0, 0, 0, 100, IType.SIN_UP))
					.addBus("MAG", new BusAnimationSequence().addPos(0, 0, 0, 250).addPos(0, -7, -1.5, 300, IType.SIN_UP).hold(400).addPos(0, 0, 0, 300, IType.SIN_UP))
					.addBus("EQUIP", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(3, 0, 0, 750, IType.SIN_FULL).addPos(-3, 0, 0, 50, IType.SIN_DOWN).addPos(0, 0, 0, 100, IType.SIN_FULL))
					.addBus("TURN", new BusAnimationSequence().addPos(0, 0, 0, 200).addPos(0, 0, 15, 300, IType.SIN_FULL).hold(900).addPos(0, 0, 0, 150, IType.SIN_FULL))
					.addBus("BULLET", new BusAnimationSequence().setPos(ammo <= 1 ? 100 : 0, 0, 0).hold(750).setPos(0, 0, 0).hold(750).addPos(0, 0.5, 2.25, 50));
		case JAMMED: return new BusAnimation()
				.addBus("TILT", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(-30, 0, 0, 150, IType.SIN_FULL).hold(800).addPos(0, 0, 0, 150, IType.SIN_FULL))
				.addBus("TURN", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, 25, 150, IType.SIN_FULL).hold(800).addPos(0, 0, 0, 150, IType.SIN_FULL))
				.addBus("SLIDE", new BusAnimationSequence().addPos(0, 0, 0, 750).addPos(0, 0, -0.5, 100, IType.SIN_FULL).hold(100).addPos(0, 0, 0, 100, IType.SIN_UP).hold(100).addPos(0, 0, -0.5, 100, IType.SIN_FULL).hold(100).addPos(0, 0, 0, 100, IType.SIN_UP))
				.addBus("BULLET", new BusAnimationSequence().setPos(0, 0.5, 2.25).hold(750).addPos(0, 0.5, 1.25, 100, IType.SIN_FULL).hold(100).addPos(0, 0.5, 2.25, 100, IType.SIN_UP).hold(100).addPos(0, 0.5, 1.25, 100, IType.SIN_FULL).hold(100).addPos(0, 0.5, 2.25, 100, IType.SIN_UP));
		case INSPECT: return new BusAnimation()
				.addBus("TILT", new BusAnimationSequence().addPos(-30, 0, 0, 250, IType.SIN_FULL).hold(1500).addPos(0, 0, 0, 250, IType.SIN_FULL))
				.addBus("TURN", new BusAnimationSequence().addPos(0, 0, 25, 250, IType.SIN_FULL).hold(1500).addPos(0, 0, 0, 250, IType.SIN_FULL))
				.addBus("SLIDE", new BusAnimationSequence().addPos(0, 0, 0, 350).addPos(0, 0, -0.5, 100, IType.SIN_FULL).hold(1125).addPos(0, 0, 0, 100, IType.SIN_UP))
				.addBus("BULLET", ammo <= 1 ? new BusAnimationSequence().setPos(100, 0, 0) : new BusAnimationSequence().setPos(0, 0.5, 2.25).hold(350).addPos(0, 0.5, 1.25, 100, IType.SIN_FULL).hold(1125).addPos(0, 0.5, 2.25, 100, IType.SIN_UP));
		}

		return null;
	};
}
