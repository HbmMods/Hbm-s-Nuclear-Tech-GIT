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
import com.hbm.items.weapon.sedna.mags.MagazineBelt;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.item.ItemStack;

public class XFactory50 {

	public static BulletConfig bmg50_sp;
	public static BulletConfig bmg50_fmj;
	public static BulletConfig bmg50_jhp;
	public static BulletConfig bmg50_ap;
	public static BulletConfig bmg50_du;

	public static void init() {
		SpentCasing casing762 = new SpentCasing(CasingType.BOTTLENECK).setColor(SpentCasing.COLOR_CASE_BRASS).setScale(1.5F);
		bmg50_sp = new BulletConfig().setItem(EnumAmmo.BMG50_SP).setCasing(EnumCasingType.LARGE, 12)
				.setCasing(casing762.clone().register("bmg50"));
		bmg50_fmj = new BulletConfig().setItem(EnumAmmo.BMG50_FMJ).setCasing(EnumCasingType.LARGE, 12).setDamage(0.8F).setThresholdNegation(7F).setArmorPiercing(0.1F)
				.setCasing(casing762.clone().register("bmg50fmj"));
		bmg50_jhp = new BulletConfig().setItem(EnumAmmo.BMG50_JHP).setCasing(EnumCasingType.LARGE, 12).setDamage(1.5F).setHeadshot(1.5F).setArmorPiercing(-0.25F)
				.setCasing(casing762.clone().register("bmg50jhp"));
		bmg50_ap = new BulletConfig().setItem(EnumAmmo.BMG50_AP).setCasing(EnumCasingType.LARGE_STEEL, 12).setDoesPenetrate(true).setDamageFalloutByPen(false).setDamage(1.5F).setThresholdNegation(17.5F).setArmorPiercing(0.15F)
				.setCasing(casing762.clone().setColor(SpentCasing.COLOR_CASE_44).register("bmg50ap"));
		bmg50_du = new BulletConfig().setItem(EnumAmmo.BMG50_DU).setCasing(EnumCasingType.LARGE_STEEL, 12).setDoesPenetrate(true).setDamageFalloutByPen(false).setDamage(2.5F).setThresholdNegation(21F).setArmorPiercing(0.25F)
				.setCasing(casing762.clone().setColor(SpentCasing.COLOR_CASE_44).register("bmg50du"));

		ModItems.gun_m2 = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(3_000).draw(10).inspect(31).crosshair(Crosshair.L_CIRCLE).smoke(LAMBDA_SMOKE)
				.rec(new Receiver(0)
						.dmg(7.5F).delay(2).dry(10).auto(true).spread(0.005F).sound("hbm:turret.chekhov_fire", 1.0F, 1.0F)
						.mag(new MagazineBelt().addConfigs(bmg50_sp, bmg50_fmj, bmg50_jhp, bmg50_ap, bmg50_du))
						.offset(1, -0.0625 * 2.5, -0.25D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_M2))
				.setupStandardConfiguration()
				.anim(LAMBDA_M2_ANIMS).orchestra(Orchestras.ORCHESTRA_M2)
				).setUnlocalizedName("gun_m2");
	}
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_SMOKE = (stack, ctx) -> {
		Lego.handleStandardSmoke(ctx.entity, stack, 2000, 0.05D, 1.1D, 0);
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_M2 = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil((float) (ctx.getPlayer().getRNG().nextGaussian() * 0.5), (float) (ctx.getPlayer().getRNG().nextGaussian() * 0.5));
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_M2_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(80, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_FULL));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, -0.25, 25).addPos(0, 0, 0, 75));
		}
		
		return null;
	};
}
