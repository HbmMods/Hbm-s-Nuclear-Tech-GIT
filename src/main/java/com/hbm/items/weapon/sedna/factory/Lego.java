package com.hbm.items.weapon.sedna.factory;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.entity.projectile.EntityBulletBaseMK4CL;
import com.hbm.entity.projectile.EntityBulletBeamBase;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectTiny;
import com.hbm.explosion.vanillant.standard.ExplosionEffectWeapon;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.interfaces.NotableComments;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.BulletConfig.ProjectileType;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.GunState;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.SmokeNode;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.main.MainRegistry;
import com.hbm.particle.helper.BlackPowderCreator;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * "LEGO" - i.e. standardized building blocks which can be used to set up gun configs easily.
 * 
 * small update, 24/11/03: this turned into fucking spaghetti. fuuuuuuuck.
 * 
 * @author hbm
 */
@NotableComments
public class Lego {
	
	public static final Random ANIM_RAND = new Random();
	
	/**
	 * If IDLE and the mag of receiver 0 can be loaded, set state to RELOADING. Used by keybinds. */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_RELOAD = (stack, ctx) -> {
		
		EntityPlayer player = ctx.getPlayer();
		Receiver rec = ctx.config.getReceivers(stack)[0];
		GunState state = ItemGunBaseNT.getState(stack, ctx.configIndex);
		
		if(state == GunState.IDLE) {
			
			ItemGunBaseNT.setIsAiming(stack, false);
			IMagazine mag = rec.getMagazine(stack);
			
			if(mag.canReload(stack, ctx.inventory)) {
				int loaded = mag.getAmount(stack, ctx.inventory);
				mag.setAmountBeforeReload(stack, loaded);
				ItemGunBaseNT.setState(stack, ctx.configIndex, GunState.RELOADING);
				ItemGunBaseNT.setTimer(stack, ctx.configIndex, rec.getReloadBeginDuration(stack) + (loaded <= 0 ? rec.getReloadCockOnEmptyPre(stack) : 0));
				ItemGunBaseNT.playAnimation(player, stack, AnimType.RELOAD, ctx.configIndex);
				if(ctx.config.getReloadChangesType(stack)) mag.initNewType(stack, ctx.inventory);
			} else {
				ItemGunBaseNT.playAnimation(player, stack, AnimType.INSPECT, ctx.configIndex);
				if(!ctx.config.getInspectCancel(stack)) {
					ItemGunBaseNT.setState(stack, ctx.configIndex, GunState.DRAWING);
					ItemGunBaseNT.setTimer(stack, ctx.configIndex, ctx.config.getInspectDuration(stack));
				}
			}
		}
	};
	
	/** If IDLE and ammo is loaded, fire and set to JUST_FIRED. */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_CLICK_PRIMARY = (stack, ctx) -> { clickReceiver(stack, ctx, 0); };
	
	public static void clickReceiver(ItemStack stack, LambdaContext ctx, int receiver) {

		EntityLivingBase entity = ctx.entity;
		EntityPlayer player = ctx.getPlayer();
		Receiver rec = ctx.config.getReceivers(stack)[receiver];
		int index = ctx.configIndex;
		GunState state = ItemGunBaseNT.getState(stack, index);
		
		if(state == GunState.IDLE) {
			
			if(rec.getCanFire(stack).apply(stack, ctx)) {
				rec.getOnFire(stack).accept(stack, ctx);
				
				if(rec.getFireSound(stack) != null)
					entity.worldObj.playSoundEffect(entity.posX, entity.posY, entity.posZ, rec.getFireSound(stack), rec.getFireVolume(stack), rec.getFirePitch(stack));
				
				int remaining = rec.getRoundsPerCycle(stack) - 1;
				for(int i = 0; i < remaining; i++) if(rec.getCanFire(stack).apply(stack, ctx)) rec.getOnFire(stack).accept(stack, ctx);
				
				ItemGunBaseNT.setState(stack, index, GunState.COOLDOWN);
				ItemGunBaseNT.setTimer(stack, index, rec.getDelayAfterFire(stack));
			} else {
				
				if(rec.getDoesDryFire(stack)) {
					ItemGunBaseNT.playAnimation(player, stack, AnimType.CYCLE_DRY, index);
					ItemGunBaseNT.setState(stack, index, rec.getRefireAfterDry(stack) ? GunState.COOLDOWN : GunState.DRAWING);
					ItemGunBaseNT.setTimer(stack, index, rec.getDelayAfterDryFire(stack));
				}
			}
		}
		
		if(state == GunState.RELOADING) {
			ItemGunBaseNT.setReloadCancel(stack, true);
		}
	}
	
	/** If IDLE, switch mode between 0 and 1. */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_CLICK_SECONDARY = (stack, ctx) -> {

		EntityLivingBase entity = ctx.entity;
		int index = ctx.configIndex;
		GunState state = ItemGunBaseNT.getState(stack, index);
		
		if(state == GunState.IDLE) {
			int mode = ItemGunBaseNT.getMode(stack, 0);
			ItemGunBaseNT.setMode(stack, index, 1 - mode);
			if(mode == 0)
				entity.worldObj.playSoundEffect(entity.posX, entity.posY, entity.posZ, "hbm:weapon.switchmode1", 1F, 1F);
			else
				entity.worldObj.playSoundEffect(entity.posX, entity.posY, entity.posZ, "hbm:weapon.switchmode2", 1F, 1F);
		}
	};
	
	/** Default smoke. */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_SMOKE = (stack, ctx) -> {
		handleStandardSmoke(ctx.entity, stack, 2000, 0.025D, 1.15D, ctx.configIndex);
	};
	
	public static void handleStandardSmoke(EntityLivingBase entity, ItemStack stack, int smokeDuration, double alphaDecay, double widthGrowth, int index) {
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		long lastShot = gun.lastShot[index];
		List<SmokeNode> smokeNodes = gun.getConfig(stack, index).smokeNodes;

		boolean smoking = lastShot + smokeDuration > System.currentTimeMillis();
		if(!smoking && !smokeNodes.isEmpty()) smokeNodes.clear();
		
		if(smoking) {
			Vec3 prev = Vec3.createVectorHelper(-entity.motionX, -entity.motionY, -entity.motionZ);
			prev.rotateAroundY((float) (entity.rotationYaw * Math.PI / 180D));
			double accel = 15D;
			double side = (entity.rotationYaw - entity.prevRotationYawHead) * 0.1D;
			double waggle = 0.025D;
			
			for(SmokeNode node : smokeNodes) {
				node.forward += -prev.zCoord * accel + entity.worldObj.rand.nextGaussian() * waggle;
				node.lift += prev.yCoord + 1.5D;
				node.side += prev.xCoord * accel + entity.worldObj.rand.nextGaussian() * waggle + side;
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

	/** Returns true if the mag has ammo in it. Used by keybind functions on whether to fire, and deciders on whether to trigger a refire. */
	public static BiFunction<ItemStack, LambdaContext, Boolean> LAMBDA_STANDARD_CAN_FIRE = (stack, ctx) -> { return ctx.config.getReceivers(stack)[0].getMagazine(stack).getAmount(stack, ctx.inventory) > 0; };
	
	/** Returns true if the mag has ammo in it, and the gun is in the locked on state */
	public static BiFunction<ItemStack, LambdaContext, Boolean> LAMBDA_LOCKON_CAN_FIRE = (stack, ctx) -> { return ctx.config.getReceivers(stack)[0].getMagazine(stack).getAmount(stack, ctx.inventory) > 0 && ItemGunBaseNT.getIsLockedOn(stack); };

	
	
	
	/** JUMPER - bypasses mag testing and just allows constant fire */
	public static BiFunction<ItemStack, LambdaContext, Boolean> LAMBDA_DEBUG_CAN_FIRE = (stack, ctx) -> { return true; };

	/** Spawns an EntityBulletBaseMK4 with the loaded bulletcfg */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_FIRE = (stack, ctx) -> {
		doStandardFire(stack, ctx, AnimType.CYCLE, true);
	};
	/** Spawns an EntityBulletBaseMK4 with the loaded bulletcfg, ignores wear */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_NOWEAR_FIRE = (stack, ctx) -> {
		doStandardFire(stack, ctx, AnimType.CYCLE, false);
	};
	/** Spawns an EntityBulletBaseMK4 with the loaded bulletcfg, then resets lockon progress */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_LOCKON_FIRE = (stack, ctx) -> {
		doStandardFire(stack, ctx, AnimType.CYCLE, true);
		ItemGunBaseNT.setIsLockedOn(stack, false);
	};
	
	public static void doStandardFire(ItemStack stack, LambdaContext ctx, AnimType anim, boolean calcWear) {
		EntityLivingBase entity = ctx.entity;
		EntityPlayer player = ctx.getPlayer();
		int index = ctx.configIndex;
		if(anim != null) ItemGunBaseNT.playAnimation(player, stack, anim, ctx.configIndex);
		
		boolean aim = ItemGunBaseNT.getIsAiming(stack);
		Receiver primary = ctx.config.getReceivers(stack)[0];
		IMagazine mag = primary.getMagazine(stack);
		BulletConfig config = (BulletConfig) mag.getType(stack, ctx.inventory);

		Vec3 offset = ItemGunBaseNT.getIsAiming(stack) ? primary.getProjectileOffsetScoped(stack) : primary.getProjectileOffset(stack);
		double forwardOffset = offset.xCoord;
		double heightOffset = offset.yCoord;
		double sideOffset = offset.zCoord;
		
		/*forwardOffset = 0.75;
		heightOffset = -0.125;
		sideOffset = -0.25D;*/
		
		int projectiles = config.projectilesMin;
		if(config.projectilesMax > config.projectilesMin) projectiles += entity.getRNG().nextInt(config.projectilesMax - config.projectilesMin + 1);
		projectiles = (int) (projectiles * primary.getSplitProjectiles(stack));
		
		for(int i = 0; i < projectiles; i++) {
			float damage = calcDamage(ctx, stack, primary, calcWear, index);
			float spread = calcSpread(ctx, stack, primary, config, calcWear, index, aim);
			
			if(config.pType == ProjectileType.BULLET) {
				EntityBulletBaseMK4 mk4 = new EntityBulletBaseMK4(entity, config, damage, spread, sideOffset, heightOffset, forwardOffset);
				if(ItemGunBaseNT.getIsLockedOn(stack)) mk4.lockonTarget = entity.worldObj.getEntityByID(ItemGunBaseNT.getLockonTarget(stack));
				if(i == 0 && config.blackPowder) BlackPowderCreator.composeEffect(entity.worldObj, mk4.posX, mk4.posY, mk4.posZ, mk4.motionX, mk4.motionY, mk4.motionZ, 10, 0.25F, 0.5F, 10, 0.25F);
				entity.worldObj.spawnEntityInWorld(mk4);
			} else if(config.pType == ProjectileType.BULLET_CHUNKLOADING) {
				EntityBulletBaseMK4CL mk4 = new EntityBulletBaseMK4CL(entity, config, damage, spread, sideOffset, heightOffset, forwardOffset);
				if(ItemGunBaseNT.getIsLockedOn(stack)) mk4.lockonTarget = entity.worldObj.getEntityByID(ItemGunBaseNT.getLockonTarget(stack));
				if(i == 0 && config.blackPowder) BlackPowderCreator.composeEffect(entity.worldObj, mk4.posX, mk4.posY, mk4.posZ, mk4.motionX, mk4.motionY, mk4.motionZ, 10, 0.25F, 0.5F, 10, 0.25F);
				entity.worldObj.spawnEntityInWorld(mk4);
			} else if(config.pType == ProjectileType.BEAM) {
				EntityBulletBeamBase mk4 = new EntityBulletBeamBase(entity, config, damage, spread, sideOffset, heightOffset, forwardOffset);
				entity.worldObj.spawnEntityInWorld(mk4);
			}
		}
		
		if(player != null) player.addStat(MainRegistry.statBullets, 1);
		mag.useUpAmmo(stack, ctx.inventory, 1);
		if(calcWear) ItemGunBaseNT.setWear(stack, index, Math.min(ItemGunBaseNT.getWear(stack, index) + config.wear, ctx.config.getDurability(stack)));
	}
	
	public static float getStandardWearSpread(ItemStack stack, GunConfig config, int index) {
		float percent = (float) ItemGunBaseNT.getWear(stack, index) / config.getDurability(stack);
		if(percent < 0.5F) return 0F;
		return (percent - 0.5F) * 2F;
	}
	
	/** Returns the standard multiplier for damage based on wear */
	public static float getStandardWearDamage(ItemStack stack, GunConfig config, int index) {
		float percent = (float) ItemGunBaseNT.getWear(stack, index) / config.getDurability(stack);
		if(percent < 0.75F) return 1F;
		return 1F - (percent - 0.75F) * 2F;
	}

	/** Returns the full calculated damage based on guncfg and wear */
	public static float calcDamage(LambdaContext ctx, ItemStack stack, Receiver primary, boolean calcWear, int index) {
		return primary.getBaseDamage(stack) * (calcWear ? getStandardWearDamage(stack, ctx.config, index) : 1);
	}
	
	public static float calcSpread(LambdaContext ctx, ItemStack stack, Receiver primary, BulletConfig config, boolean calcWear, int index, boolean aim) {
		// the gun's innate spread, SMGs will have poor accuracy no matter what
		float spreadInnate = primary.getInnateSpread(stack);
		// the ammo's spread (for example for buckshot) multiplied with the gun's ammo modifier (choke or sawed off barrel)
		float spreadAmmo = config.spread * primary.getAmmoSpread(stack);
		// hipfire penalty, i.e. extra spread when not aiming
		float spreadHipfire = aim ? 0F : primary.getHipfireSpread(stack);
		// extra spread caused by weapon durability, [0;0.125] by default
		float spreadWear = !calcWear ? 0F : (getStandardWearSpread(stack, ctx.config, index) * primary.getDurabilitySpread(stack));
		
		return spreadInnate + spreadAmmo + spreadHipfire + spreadWear;
	}
	
	public static void standardExplode(EntityBulletBaseMK4 bullet, MovingObjectPosition mop, float range) { standardExplode(bullet, mop, range, 1F); }
	public static void standardExplode(EntityBulletBaseMK4 bullet, MovingObjectPosition mop, float range, float damageMod) {
		ExplosionVNT vnt = new ExplosionVNT(bullet.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, range, bullet.getThrower());
		vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, bullet.damage * damageMod).setupPiercing(bullet.config.armorThresholdNegation, bullet.config.armorPiercingPercent));
		vnt.setPlayerProcessor(new PlayerProcessorStandard());
		vnt.setSFX(new ExplosionEffectWeapon(10, 2.5F, 1F));
		vnt.explode();
	}

	public static void tinyExplode(EntityBulletBaseMK4 bullet, MovingObjectPosition mop, float range) { tinyExplode(bullet, mop, range, 1F); }
	public static void tinyExplode(EntityBulletBaseMK4 bullet, MovingObjectPosition mop, float range, float damageMod) {
		ForgeDirection dir = ForgeDirection.getOrientation(mop.sideHit);
		double x = mop.hitVec.xCoord + dir.offsetX * 0.25D;
		double y = mop.hitVec.yCoord + dir.offsetY * 0.25D;
		double z = mop.hitVec.zCoord + dir.offsetZ * 0.25D;
		ExplosionVNT vnt = new ExplosionVNT(bullet.worldObj, x, y, z, range, bullet.getThrower());
		vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, bullet.damage * damageMod)
				.setupPiercing(bullet.config.armorThresholdNegation, bullet.config.armorPiercingPercent).setKnockback(0.25D));
		vnt.setPlayerProcessor(new PlayerProcessorStandard());
		vnt.setSFX(new ExplosionEffectTiny());
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
		case INSPECT:
		case JAMMED: return new BusAnimation()
					.addBus("RELAOD_TILT", new BusAnimationSequence().addPos(-15, 0, 0, 100).addPos(65, 0, 0, 100).addPos(45, 0, 0, 50).addPos(0, 0, 0, 200).addPos(0, 0, 0, 200).addPos(-80, 0, 0, 100).addPos(-80, 0, 0, 100).addPos(0, 0, 0, 200))
					.addBus("RELOAD_CYLINDER", new BusAnimationSequence().addPos(0, 0, 0, 200).addPos(90, 0, 0, 100).addPos(90, 0, 0, 450).addPos(0, 0, 0, 70));
		}
		
		return null;
	};
	
	/*
	 * Be honest. Do you genuinely think posting a random screenshot of your game with absolutely ZERO context of what modpack, what
	 * Shaders if any or literally any context at all would come to a magic solution?
	 * For all we know you accidentally rubbed Vaseline all over your monitor and jizzed in the hdmi socket of your pc
	 * 
	 * ~ u/Wolfyy47_, 2024
	 */
}
