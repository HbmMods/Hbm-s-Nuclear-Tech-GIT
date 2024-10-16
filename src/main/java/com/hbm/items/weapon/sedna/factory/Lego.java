package com.hbm.items.weapon.sedna.factory;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectWeapon;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.GunState;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.SmokeNode;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

/**
 * "LEGO" - i.e. standardized building blocks which can be used to set up gun configs easily.
 * 
 * @author hbm
 */
public class Lego {
	
	public static final Random ANIM_RAND = new Random();
	
	/**
	 * If IDLE and the mag of receiver 0 can be loaded, set state to RELOADING. Used by keybinds. */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_RELOAD = (stack, ctx) -> {
		
		EntityPlayer player = ctx.player;
		Receiver rec = ctx.config.getReceivers(stack)[0];
		GunState state = ItemGunBaseNT.getState(stack, ctx.configIndex);
		
		if(state == GunState.IDLE) {
			
			ItemGunBaseNT.setIsAiming(stack, false);
			IMagazine mag = rec.getMagazine(stack);
			
			if(mag.canReload(stack, ctx.player)) {
				int loaded = mag.getAmount(stack);
				mag.setAmountBeforeReload(stack, loaded);
				ItemGunBaseNT.setState(stack, ctx.configIndex, GunState.RELOADING);
				ItemGunBaseNT.setTimer(stack, ctx.configIndex, rec.getReloadBeginDuration(stack) + (loaded <= 0 ? rec.getReloadCockOnEmptyPre(stack) : 0));
				ItemGunBaseNT.playAnimation(player, stack, AnimType.RELOAD, ctx.configIndex);
			} else {
				ItemGunBaseNT.playAnimation(player, stack, AnimType.INSPECT, ctx.configIndex);
			}
		}
	};
	
	/**
	 * If IDLE and ammo is loaded, fire and set to JUST_FIRED. */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_CLICK_PRIMARY = (stack, ctx) -> {

		EntityPlayer player = ctx.player;
		Receiver rec = ctx.config.getReceivers(stack)[0];
		int index = ctx.configIndex;
		GunState state = ItemGunBaseNT.getState(stack, index);
		
		if(state == GunState.IDLE) {
			
			if(rec.getCanFire(stack).apply(stack, ctx)) {
				rec.getOnFire(stack).accept(stack, ctx);
				
				if(rec.getFireSound(stack) != null)
					player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, rec.getFireSound(stack), rec.getFireVolume(stack), rec.getFirePitch(stack));
				
				int remaining = rec.getRoundsPerCycle(stack) - 1;
				for(int i = 0; i < remaining; i++) if(rec.getCanFire(stack).apply(stack, ctx)) rec.getOnFire(stack).accept(stack, ctx);
				
				ItemGunBaseNT.setState(stack, index, GunState.COOLDOWN);
				ItemGunBaseNT.setTimer(stack, index, rec.getDelayAfterFire(stack));
			} else {
				
				if(rec.getDoesDryFire(stack)) {
					ItemGunBaseNT.playAnimation(player, stack, AnimType.CYCLE_DRY, index);
					ItemGunBaseNT.setState(stack, index, GunState.DRAWING);
					ItemGunBaseNT.setTimer(stack, index, rec.getDelayAfterDryFire(stack));
				}
			}
		}
	};
	
	/** Toggles isAiming. Used by keybinds. */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_RECOIL = (stack, ctx) -> {
		//ItemGunBaseNT.recoilVertical += 10;
		//ItemGunBaseNT.recoilHorizontal += ctx.player.getRNG().nextGaussian() * 1.5;
	};
	
	/** Default smoke. */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_SMOKE = (stack, ctx) -> {
		handleStandardSmoke(ctx.player, stack, 2000, 0.025D, 1.15D, ctx.configIndex);
	};
	
	public static void handleStandardSmoke(EntityPlayer player, ItemStack stack, int smokeDuration, double alphaDecay, double widthGrowth, int index) {
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		long lastShot = gun.lastShot[index];
		List<SmokeNode> smokeNodes = gun.getConfig(stack, index).smokeNodes;

		boolean smoking = lastShot + smokeDuration > System.currentTimeMillis();
		if(!smoking && !smokeNodes.isEmpty()) smokeNodes.clear();
		
		if(smoking) {
			Vec3 prev = Vec3.createVectorHelper(-player.motionX, -player.motionY, -player.motionZ);
			prev.rotateAroundY((float) (player.rotationYaw * Math.PI / 180D));
			double accel = 15D;
			double side = (player.rotationYaw - player.prevRotationYawHead) * 0.1D;
			double waggle = 0.025D;
			
			for(SmokeNode node : smokeNodes) {
				node.forward += -prev.zCoord * accel + player.worldObj.rand.nextGaussian() * waggle;
				node.lift += prev.yCoord + 1.5D;
				node.side += prev.xCoord * accel + player.worldObj.rand.nextGaussian() * waggle + side;
				if(node.alpha > 0) node.alpha -= alphaDecay;
				node.width *= widthGrowth;
			}
			
			double alpha = (System.currentTimeMillis() - lastShot) / (double) smokeDuration;
			alpha = (1 - alpha) * 0.5D;
			
			if(gun.getState(stack, index) == GunState.RELOADING || smokeNodes.size() == 0) alpha = 0;
			smokeNodes.add(new SmokeNode(alpha));
		}
	}
	
	/** Toggles isAiming. Used by keybinds. */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_TOGGLE_AIM = (stack, ctx) -> { ItemGunBaseNT.setIsAiming(stack, !ItemGunBaseNT.getIsAiming(stack)); };
	
	/** Returns true if the mag has ammo in it. Used by keybind functions on whether to fire, and deciders on whether to trigger a refire, */
	public static BiFunction<ItemStack, LambdaContext, Boolean> LAMBDA_STANDARD_CAN_FIRE = (stack, ctx) -> { return ctx.config.getReceivers(stack)[0].getMagazine(stack).getAmount(stack) > 0; };

	
	
	
	/** JUMPER - bypasses mag testing and just allows constant fire */
	public static BiFunction<ItemStack, LambdaContext, Boolean> LAMBDA_DEBUG_CAN_FIRE = (stack, ctx) -> { return true; };
	
	/** Spawns an EntityBulletBaseMK4 with the loaded bulletcfg */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_FIRE = (stack, ctx) -> {
		doStandardFire(stack, ctx, AnimType.CYCLE);
	};
	
	public static void doStandardFire(ItemStack stack, LambdaContext ctx, AnimType anim) {
		EntityPlayer player = ctx.player;
		int index = ctx.configIndex;
		if(anim != null) ItemGunBaseNT.playAnimation(player, stack, anim, ctx.configIndex);
		
		float aim = ItemGunBaseNT.getIsAiming(stack) ? 0.25F : 1F;
		Receiver primary = ctx.config.getReceivers(stack)[0];
		IMagazine mag = primary.getMagazine(stack);
		BulletConfig config = (BulletConfig) mag.getType(stack);
		
		Vec3 offset = primary.getProjectileOffset(stack);
		double forwardOffset = offset.xCoord;
		double heightOffset = offset.yCoord;
		double sideOffset = ItemGunBaseNT.getIsAiming(stack) ? 0 : offset.zCoord;
		
		/*forwardOffset = 1;
		heightOffset = -0.0625 * 1.5;
		sideOffset = -0.1875D;*/
		
		int projectiles = config.projectilesMin;
		if(config.projectilesMax > config.projectilesMin) projectiles += player.getRNG().nextInt(config.projectilesMax - config.projectilesMin + 1);
		
		for(int i = 0; i < projectiles; i++) {
			float damage = primary.getBaseDamage(stack) * getStandardWearDamage(stack, ctx.config, index);
			float spread = primary.getGunSpread(stack) * aim + getStandardWearSpread(stack, ctx.config, index) * 0.125F;
			EntityBulletBaseMK4 mk4 = new EntityBulletBaseMK4(player, config, damage, spread, sideOffset, heightOffset, forwardOffset);
			player.worldObj.spawnEntityInWorld(mk4);
		}
		
		mag.setAmount(stack, mag.getAmount(stack) - 1);
		ItemGunBaseNT.setWear(stack, index, Math.min(ItemGunBaseNT.getWear(stack, index) + config.wear, ctx.config.getDurability(stack)));
	}
	
	public static float getStandardWearSpread(ItemStack stack, GunConfig config, int index) {
		float percent = (float) ItemGunBaseNT.getWear(stack, index) / config.getDurability(stack);
		if(percent < 0.5F) return 0F;
		return (percent - 0.5F) * 2F;
	}
	
	public static float getStandardWearDamage(ItemStack stack, GunConfig config, int index) {
		float percent = (float) ItemGunBaseNT.getWear(stack, index) / config.getDurability(stack);
		if(percent < 0.75F) return 1F;
		return 1F - (percent - 0.75F) * 2F;
	}
	
	public static void standardExplode(EntityBulletBaseMK4 bullet, MovingObjectPosition mop, float range) {
		ExplosionVNT vnt = new ExplosionVNT(bullet.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, range);
		vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, bullet.damage));
		vnt.setPlayerProcessor(new PlayerProcessorStandard());
		vnt.setSFX(new ExplosionEffectWeapon(10, 2.5F, 1F));
		vnt.explode();
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
