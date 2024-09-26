package com.hbm.items.weapon.sedna.factory;

import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.GunState;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.hud.HUDComponentAmmoCounter;
import com.hbm.items.weapon.sedna.hud.HUDComponentDurabilityBar;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * "LEGO" - i.e. standardized building blocks which can be used to set up gun configs easily.
 * 
 * @author hbm
 */
public class Lego {
	
	public static final Random ANIM_RAND = new Random();

	public static HUDComponentDurabilityBar HUD_COMPONENT_DURABILITY = new HUDComponentDurabilityBar();
	public static HUDComponentAmmoCounter HUD_COMPONENT_AMMO = new HUDComponentAmmoCounter(0);
	
	/**
	 * If IDLE and the mag of receiver 0 can be loaded, set state to RELOADING. Used by keybinds. */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_RELOAD = (stack, ctx) -> {
		
		EntityPlayer player = ctx.player;
		Receiver rec = ctx.config.getReceivers(stack)[0];
		GunState state = ItemGunBaseNT.getState(stack);
		
		if(state == GunState.IDLE) {
			
			ItemGunBaseNT.setIsAiming(stack, false);
			
			if(rec.getMagazine(stack).canReload(stack, ctx.player)) {
				ItemGunBaseNT.setState(stack, GunState.RELOADING);
				ItemGunBaseNT.setTimer(stack, rec.getReloadDuration(stack));
				ItemGunBaseNT.playAnimation(player, stack, AnimType.RELOAD);
			} else {
				ItemGunBaseNT.playAnimation(player, stack, AnimType.INSPECT);
			}
		}
	};
	
	/**
	 * If IDLE and ammo is loaded, fire and set to JUST_FIRED. */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_CLICK_PRIMARY = (stack, ctx) -> {

		EntityPlayer player = ctx.player;
		Receiver rec = ctx.config.getReceivers(stack)[0];
		GunState state = ItemGunBaseNT.getState(stack);
		
		if(state == GunState.IDLE) {
			
			if(rec.getCanFire(stack).apply(stack, ctx)) {
				rec.getOnFire(stack).accept(stack, ctx);
				
				player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, rec.getFireSound(stack), rec.getFireVolume(stack), rec.getFirePitch(stack));
				
				int remaining = rec.getRoundsPerCycle(stack) - 1;
				for(int i = 0; i < remaining; i++) if(rec.getCanFire(stack).apply(stack, ctx)) rec.getOnFire(stack).accept(stack, ctx);
				
				ItemGunBaseNT.setState(stack, GunState.COOLDOWN);
				ItemGunBaseNT.setTimer(stack, rec.getDelayAfterFire(stack));
			} else {
				ItemGunBaseNT.playAnimation(player, stack, AnimType.CYCLE_DRY);
				
				if(rec.getDoesDryFire(stack)) {
					ItemGunBaseNT.setState(stack, GunState.COOLDOWN);
					ItemGunBaseNT.setTimer(stack, rec.getDelayAfterFire(stack));
				}
			}
		}
	};
	
	/** Toggles isAiming. Used by keybinds. */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_RECOIL = (stack, ctx) -> {
		ItemGunBaseNT.recoilVertical += 10;
		ItemGunBaseNT.recoilHorizontal += ctx.player.getRNG().nextGaussian() * 1.5;
	};
	
	/** Toggles isAiming. Used by keybinds. */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_TOGGLE_AIM = (stack, ctx) -> { ItemGunBaseNT.setIsAiming(stack, !ItemGunBaseNT.getIsAiming(stack)); };
	
	/** Returns true if the mag has ammo in it. Used by keybind functions on whether to fire, and deciders on whether to trigger a refire, */
	public static BiFunction<ItemStack, LambdaContext, Boolean> LAMBDA_STANDARD_CAN_FIRE = (stack, ctx) -> { return ctx.config.getReceivers(stack)[0].getMagazine(stack).getAmount(stack) > 0; };

	
	
	
	/** JUMPER - bypasses mag testing and just allows constant fire */
	public static BiFunction<ItemStack, LambdaContext, Boolean> LAMBDA_DEBUG_CAN_FIRE = (stack, ctx) -> { return true; };
	
	/** Spawns an EntityBulletBaseMK4 with the loaded bulletcfg */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_FIRE = (stack, ctx) -> {
		EntityPlayer player = ctx.player;
		ItemGunBaseNT.playAnimation(player, stack, AnimType.CYCLE);
		
		double sideOffset = ItemGunBaseNT.getIsAiming(stack) ? 0 : -0.3125D;
		float aim = ItemGunBaseNT.getIsAiming(stack) ? 0.25F : 1F;
		Receiver primary = ctx.config.getReceivers(stack)[0];
		IMagazine mag = primary.getMagazine(stack);
		BulletConfig config = (BulletConfig) mag.getType(stack);
		
		int projectiles = config.projectilesMin;
		if(config.projectilesMax > config.projectilesMin) projectiles += player.getRNG().nextInt(config.projectilesMax - config.projectilesMin + 1);
		
		for(int i = 0; i < projectiles; i++) {
			float damage = primary.getBaseDamage(stack) * getStandardWearDamage(stack, ctx.config);
			float spread = primary.getGunSpread(stack) * aim + getStandardWearSpread(stack, ctx.config) * 0.125F;
			EntityBulletBaseMK4 mk4 = new EntityBulletBaseMK4(player, config, damage, spread, sideOffset, -0.0625, 0.75);
			player.worldObj.spawnEntityInWorld(mk4);
		}
		
		mag.setAmount(stack, mag.getAmount(stack) - 1);
		ItemGunBaseNT.setWear(stack, Math.min(ItemGunBaseNT.getWear(stack) + config.wear, ctx.config.getDurability(stack)));
	};
	
	public static float getStandardWearSpread(ItemStack stack, GunConfig config) {
		float percent = (float) ItemGunBaseNT.getWear(stack) / config.getDurability(stack);
		if(percent < 0.5F) return 0F;
		return (percent - 0.5F) * 2F;
	}
	
	public static float getStandardWearDamage(ItemStack stack, GunConfig config) {
		float percent = (float) ItemGunBaseNT.getWear(stack) / config.getDurability(stack);
		if(percent < 0.75F) return 1F;
		return 1F - (percent - 0.75F) * 2F;
	}
	
	/** anims for the DEBUG revolver, mostly a copy of the li'lpip but with some fixes regarding the cylinder movement */
	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_DEBUG_ANIMS = (stack, type) -> {
		switch(type) {
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, 0, 50).addPos(0, 0, -3, 50).addPos(0, 0, 0, 250))
				.addBus("HAMMER", new BusAnimationSequence().addPos(0, 0, 1, 50).addPos(0, 0, 1, 400).addPos(0, 0, 0, 200))
				.addBus("DRUM", new BusAnimationSequence().addPos(0, 0, 0, 450).addPos(0, 0, 1, 200));
		case CYCLE_DRY: return new BusAnimation()
				.addBus("HAMMER", new BusAnimationSequence().addPos(0, 0, 1, 50).addPos(0, 0, 1, 300 + 100).addPos(0, 0, 0, 200))
				.addBus("DRUM", new BusAnimationSequence().addPos(0, 0, 0, 450).addPos(0, 0, 1, 200));
		case EQUIP: return new BusAnimation().addBus("ROTATE", new BusAnimationSequence().addPos(-360, 0, 0, 350));
		case RELOAD: return new BusAnimation()
					.addBus("RELAOD_TILT", new BusAnimationSequence().addPos(-15, 0, 0, 100).addPos(65, 0, 0, 100).addPos(45, 0, 0, 50).addPos(0, 0, 0, 200).addPos(0, 0, 0, 1450).addPos(-80, 0, 0, 100).addPos(-80, 0, 0, 100).addPos(0, 0, 0, 200))
					.addBus("RELOAD_CYLINDER", new BusAnimationSequence().addPos(0, 0, 0, 200).addPos(90, 0, 0, 100).addPos(90, 0, 0, 1700).addPos(0, 0, 0, 70))
					.addBus("RELOAD_LIFT", new BusAnimationSequence().addPos(0, 0, 0, 350).addPos(-45, 0, 0, 250).addPos(-45, 0, 0, 350).addPos(-15, 0, 0, 200).addPos(-15, 0, 0, 1050).addPos(0, 0, 0, 100))
					.addBus("RELOAD_JOLT", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(2, 0, 0, 50).addPos(0, 0, 0, 100))
					.addBus("RELOAD_BULLETS", new BusAnimationSequence().addPos(0, 0, 0, 650).addPos(10, 0, 0, 300).addPos(10, 0, 0, 200).addPos(0, 0, 0, 700))
					.addBus("RELOAD_BULLETS_CON", new BusAnimationSequence().addPos(1, 0, 0, 0).addPos(1, 0, 0, 950).addPos(0, 0, 0, 1 ) );
		case INSPECT: //if(ANIM_RAND.nextBoolean())  return new BusAnimation().addBus("ROTATE", new BusAnimationSequence().addPos(-360 * 5, 0, 0, 350 * 5));
		case JAMMED: return new BusAnimation()
					.addBus("RELAOD_TILT", new BusAnimationSequence().addPos(-15, 0, 0, 100).addPos(65, 0, 0, 100).addPos(45, 0, 0, 50).addPos(0, 0, 0, 200).addPos(0, 0, 0, 200).addPos(-80, 0, 0, 100).addPos(-80, 0, 0, 100).addPos(0, 0, 0, 200))
					.addBus("RELOAD_CYLINDER", new BusAnimationSequence().addPos(0, 0, 0, 200).addPos(90, 0, 0, 100).addPos(90, 0, 0, 450).addPos(0, 0, 0, 70));
		}
		
		return null;
	};
}
