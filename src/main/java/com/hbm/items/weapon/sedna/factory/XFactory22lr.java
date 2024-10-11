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
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.item.ItemStack;

public class XFactory22lr {

	public static BulletConfig p22_sp;
	public static BulletConfig p22_fmj;
	public static BulletConfig p22_jhp;
	public static BulletConfig p22_ap;

	public static void init() {
		SpentCasing casing22 = new SpentCasing(CasingType.STRAIGHT).setColor(SpentCasing.COLOR_CASE_BRASS).setScale(0.5F);
		p22_sp = new BulletConfig().setItem(EnumAmmo.P22_SP)
				.setCasing(casing22.clone().register("p22"));
		p22_fmj = new BulletConfig().setItem(EnumAmmo.P22_FMJ).setDamage(0.8F).setArmorPiercing(0.1F)
				.setCasing(casing22.clone().register("p22fmj"));
		p22_jhp = new BulletConfig().setItem(EnumAmmo.P22_JHP).setDamage(1.5F).setArmorPiercing(-0.25F)
				.setCasing(casing22.clone().register("p22jhp"));
		p22_ap = new BulletConfig().setItem(EnumAmmo.P22_AP).setDoesPenetrate(true).setDamageFalloutByPen(false).setDamage(1.5F).setArmorPiercing(0.15F)
				.setCasing(casing22.clone().setColor(SpentCasing.COLOR_CASE_44).register("p22ap"));

		ModItems.gun_am180 = new ItemGunBaseNT(new GunConfig()
				.dura(177 * 25).draw(15).inspect(38).crosshair(Crosshair.L_CIRCLE).smoke(LAMBDA_SMOKE)
				.rec(new Receiver(0)
						.dmg(5F).delay(1).dry(10).auto(true).spread(0.02F).reload(66).jam(30).sound("hbm:weapon.fire.blackPowder", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 177).addConfigs(p22_sp, p22_fmj, p22_jhp, p22_ap))
						.offset(1, -0.0625 * 1.5, -0.1875D)
						.canFire(Lego.LAMBDA_STANDARD_CAN_FIRE).fire(Lego.LAMBDA_STANDARD_FIRE).recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.setupStandardConfiguration()
				.anim(LAMBDA_AM180_ANIMS).orchestra(Orchestras.ORCHESTRA_AM180)
				).setUnlocalizedName("gun_am180").setTextureName(RefStrings.MODID + ":gun_darter");
	}
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_SMOKE = (stack, ctx) -> {
		Lego.handleStandardSmoke(ctx.player, stack, 3000, 0.05D, 1.1D, 0);
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_AM180_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(45, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_FULL));
		case CYCLE: return ResourceManager.am180_anim.get("Fire");
		case CYCLE_DRY: return ResourceManager.am180_anim.get("FireDry");
		case RELOAD: return ResourceManager.am180_anim.get("Reload");
		case JAMMED: return ResourceManager.am180_anim.get("Jammed");
		case INSPECT: return ResourceManager.am180_anim.get("Inspect");
		}
		
		return null;
	};
}
