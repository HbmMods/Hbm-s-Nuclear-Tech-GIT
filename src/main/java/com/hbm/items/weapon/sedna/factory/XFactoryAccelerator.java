package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.entity.projectile.EntityBulletBeamBase;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.mags.MagazineBelt;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

public class XFactoryAccelerator {
	
	public static MagazineBelt tauChargeMag = new MagazineBelt();

	public static BulletConfig tau_uranium;
	public static BulletConfig tau_uranium_charge;
	
	public static void init() {

		tau_uranium = new BulletConfig().setItem(EnumAmmo.TAU_URANIUM).setLife(5).setRenderRotations(false).setDoesPenetrate(true).setDamageFalloutByPen(false)
				.setOnBeamImpact(BulletConfig.LAMBDA_BEAM_HIT);
		tau_uranium_charge = new BulletConfig().setItem(EnumAmmo.TAU_URANIUM).setLife(5).setRenderRotations(false).setDoesPenetrate(true).setDamageFalloutByPen(false).setSpectral(true)
				.setOnBeamImpact(BulletConfig.LAMBDA_BEAM_HIT);
		
		tauChargeMag.addConfigs(tau_uranium_charge);

		ModItems.gun_tau = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(6_400).draw(10).inspect(10).crosshair(Crosshair.CIRCLE)
				.rec(new Receiver(0)
						.dmg(10F).delay(4).auto(true).spread(0F)
						.mag(new MagazineBelt().addConfigs(tau_uranium))
						.offset(1, -0.0625 * 2.5, -0.25D)
						.setupBeamFire().recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.pp(Lego.LAMBDA_STANDARD_CLICK_PRIMARY)
				.rp(LAMBDA_TAU_PRIMARY_RELEASE)
				.ps(LAMBDA_TAU_SECONDARY_PRESS)
				.rs(LAMBDA_TAU_SECONDARY_RELEASE)
				.pr(Lego.LAMBDA_STANDARD_RELOAD)
				.decider(GunStateDecider.LAMBDA_STANDARD_DECIDER)
				.anim(LAMBDA_TAU_ANIMS).orchestra(Orchestras.ORCHESTRA_TAU)
				).setUnlocalizedName("gun_tau");
	}
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_TAU_PRIMARY_RELEASE = (stack, ctx) -> {
		if(ctx.getPlayer() == null || ItemGunBaseNT.getLastAnim(stack, ctx.configIndex) != AnimType.CYCLE) return;
		ctx.getPlayer().worldObj.playSoundEffect(ctx.getPlayer().posX, ctx.getPlayer().posY, ctx.getPlayer().posZ, "hbm:weapon.fire.tauRelease", 1F, 1F);
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_TAU_SECONDARY_PRESS = (stack, ctx) -> {
		if(ctx.getPlayer() == null) return;
		if(ctx.config.getReceivers(stack)[0].getMagazine(stack).getAmount(stack, ctx.inventory) <= 0) return;
		ItemGunBaseNT.playAnimation(ctx.getPlayer(), stack, AnimType.SPINUP, ctx.configIndex);
		tauChargeMag.getMagType(stack); //caches the last loaded ammo
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_TAU_SECONDARY_RELEASE = (stack, ctx) -> {
		if(ctx.getPlayer() == null) return;
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		
		if(timer >= 10 && ItemGunBaseNT.getLastAnim(stack, ctx.configIndex) == AnimType.SPINUP) {
			ItemGunBaseNT.playAnimation(ctx.getPlayer(), stack, AnimType.ALT_CYCLE, ctx.configIndex);
			int unitsUsed = 1 + Math.min(12, timer / 10);
			
			EntityLivingBase entity = ctx.entity;
			int index = ctx.configIndex;
			
			float aim = ItemGunBaseNT.getIsAiming(stack) ? 0.25F : 1F;
			Receiver primary = ctx.config.getReceivers(stack)[0];
			BulletConfig config = tauChargeMag.getFirstConfig(stack, ctx.inventory);
			
			Vec3 offset = primary.getProjectileOffset(stack);
			double forwardOffset = offset.xCoord;
			double heightOffset = offset.yCoord;
			double sideOffset = offset.zCoord;
			
			float damage = Lego.getStandardWearDamage(stack, ctx.config, index) * unitsUsed * 5;
			float spread = Lego.calcSpread(ctx, stack, primary, true, index, aim);
			EntityBulletBeamBase mk4 = new EntityBulletBeamBase(entity, config, damage, spread, sideOffset, heightOffset, forwardOffset);
			entity.worldObj.spawnEntityInWorld(mk4);
			
			ItemGunBaseNT.setWear(stack, index, Math.min(ItemGunBaseNT.getWear(stack, index) + config.wear * unitsUsed, ctx.config.getDurability(stack)));
			
		} else {
			ItemGunBaseNT.playAnimation(ctx.getPlayer(), stack, AnimType.CYCLE_DRY, ctx.configIndex);
		}
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_TAU_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(45, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_FULL));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, -0.5, 50).addPos(0, 0, 0, 150, IType.SIN_FULL))
				.addBus("ROTATE", new BusAnimationSequence().addPos(0, 0, -5, 50, IType.SIN_DOWN).addPos(0, 0, 5, 100, IType.SIN_FULL).addPos(0, 0, 0, 50, IType.SIN_UP));
		case ALT_CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, -3, 100, IType.SIN_DOWN).addPos(0, 0, 0, 250, IType.SIN_FULL))
				.addBus("ROTATE", new BusAnimationSequence().addPos(0, 0, -5, 50, IType.SIN_DOWN).addPos(0, 0, 5, 100, IType.SIN_FULL).addPos(0, 0, 0, 50, IType.SIN_UP));
		case CYCLE_DRY: return new BusAnimation();
		case INSPECT: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(2, 0, 0, 150, IType.SIN_DOWN).addPos(0, 0, 0, 100, IType.SIN_FULL))
				.addBus("ROTATE", new BusAnimationSequence().addPos(0, 0, -360, 500, IType.SIN_DOWN));
		case SPINUP: return new BusAnimation()
				.addBus("ROTATE", new BusAnimationSequence().addPos(0, 0, 360 * 6, 3000, IType.SIN_UP).addPos(0, 0, 0, 0).addPos(0, 0, 360 * 40, 500 * 20));
		}
		
		return null;
	};
}
