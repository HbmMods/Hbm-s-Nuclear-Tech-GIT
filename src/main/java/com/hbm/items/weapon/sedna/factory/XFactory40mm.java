package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import com.hbm.entity.effect.EntityFireLingering;
import com.hbm.entity.logic.EntityC130;
import com.hbm.entity.logic.EntityC130.C130PayloadType;
import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectWeapon;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.items.ItemEnums.EnumCasingType;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.mags.MagazineSingleReload;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.util.EntityDamageUtil;
import com.hbm.util.TrackerUtil;
import com.hbm.util.DamageResistanceHandler.DamageClass;
import com.hbm.world.WorldUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class XFactory40mm {

	public static BulletConfig g26_flare;
	public static BulletConfig g26_flare_supply;
	public static BulletConfig g26_flare_weapon;
	
	public static BulletConfig g40_he;
	public static BulletConfig g40_heat;
	public static BulletConfig g40_demo;
	public static BulletConfig g40_inc;
	public static BulletConfig g40_phosphorus;

	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_IGNITE = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
			if(mop.entityHit instanceof EntityLivingBase) {
				HbmLivingProps props = HbmLivingProps.getData((EntityLivingBase) mop.entityHit);
				props.fire += 200;
			}
		}
	};
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_EXPLODE = (bullet, mop) -> {
		Lego.standardExplode(bullet, mop, 5F); bullet.setDead();
	};
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_EXPLODE_HEAT = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3) return;
		Lego.standardExplode(bullet, mop, 3.5F); bullet.setDead();
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && mop.entityHit instanceof EntityLivingBase) {
			EntityLivingBase living = (EntityLivingBase) mop.entityHit;
			EntityDamageUtil.attackEntityFromNT(living, bullet.config.getDamage(bullet, bullet.getThrower(), DamageClass.EXPLOSIVE), bullet.damage * 3F, true, true, 0.5F, 3F, 0.15F);
		} else if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
			mop.entityHit.attackEntityFrom(bullet.config.getDamage(bullet, bullet.getThrower(), DamageClass.EXPLOSIVE), bullet.damage * 3F);
		}
	};
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_EXPLODE_DEMO = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3) return;
		ExplosionVNT vnt = new ExplosionVNT(bullet.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 5F, bullet.getThrower());
		vnt.setBlockAllocator(new BlockAllocatorStandard());
		vnt.setBlockProcessor(new BlockProcessorStandard());
		vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, bullet.damage));
		vnt.setPlayerProcessor(new PlayerProcessorStandard());
		vnt.setSFX(new ExplosionEffectWeapon(10, 2.5F, 1F));
		vnt.explode();
		bullet.setDead();
	};
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_EXPLODE_INC = (bullet, mop) -> {
		spawnFire(bullet, mop, false, 200);
	};
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_EXPLODE_PHOSPHORUS = (bullet, mop) -> {
		spawnFire(bullet, mop, true, 400);
	};
	
	public static void spawnFire(EntityBulletBaseMK4 bullet, MovingObjectPosition mop, boolean phosphorus, int duration) {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3) return;
		World world = bullet.worldObj;
		Lego.standardExplode(bullet, mop, 3F);
		EntityFireLingering fire = new EntityFireLingering(world).setArea(5, 2).setDuration(duration).setType(phosphorus ? EntityFireLingering.TYPE_PHOSPHORUS : EntityFireLingering.TYPE_DIESEL);
		fire.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
		world.spawnEntityInWorld(fire);
		bullet.setDead();
		for(int dx = -1; dx <= 1; dx++) {
			for(int dy = -1; dy <= 1; dy++) {
				for(int dz = -1; dz <= 1; dz++) {
					int x = (int) Math.floor(mop.hitVec.xCoord) + dx;
					int y = (int) Math.floor(mop.hitVec.yCoord) + dy;
					int z = (int) Math.floor(mop.hitVec.zCoord) + dz;
					if(world.getBlock(x, y, z).isAir(bullet.worldObj, x, y, z)) for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
						if(world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ).isFlammable(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir.getOpposite())) {
							world.setBlock(x, y, z, Blocks.fire);
							break;
						}
					}
				}
			}
		}
	}

	public static Consumer<Entity> LAMBDA_SPAWN_C130_SUPPLIESS = (entity) -> { spawnPlane(entity, C130PayloadType.SUPPLIES); };
	public static Consumer<Entity> LAMBDA_SPAWN_C130_WEAPONS = (entity) -> { spawnPlane(entity, C130PayloadType.WEAPONS); };
	
	public static void spawnPlane(Entity entity, C130PayloadType payload) {
		if(!entity.worldObj.isRemote && entity.ticksExisted == 40) {
			EntityBulletBaseMK4 bullet = (EntityBulletBaseMK4) entity;
			if(bullet.getThrower() != null) bullet.worldObj.playSoundAtEntity(bullet.getThrower(), "hbm:item.techBleep", 1.0F, 1.0F);
			EntityC130 c130 = new EntityC130(bullet.worldObj);
			int x = (int) Math.floor(bullet.posX);
			int z = (int) Math.floor(bullet.posZ);
			int y = bullet.worldObj.getHeightValue(x, z);
			c130.fac(bullet.worldObj, x, y, z, payload);
			WorldUtil.loadAndSpawnEntityInWorld(c130);
			TrackerUtil.setTrackingRange(bullet.worldObj, c130, 250);
		}
	}
	
	public static void init() {
		
		g26_flare = new BulletConfig().setItem(EnumAmmo.G26_FLARE).setCasing(EnumCasingType.LARGE, 4).setLife(100).setVel(2F).setGrav(0.015D).setRenderRotations(false).setOnImpact(LAMBDA_STANDARD_IGNITE).setCasing(new SpentCasing(CasingType.STRAIGHT).setColor(0x9E1616).setScale(2F).register("g26Flare"));
		g26_flare_supply = new BulletConfig().setItem(EnumAmmo.G26_FLARE_SUPPLY).setCasing(EnumCasingType.LARGE, 4).setLife(100).setVel(2F).setGrav(0.015D).setRenderRotations(false).setOnImpact(LAMBDA_STANDARD_IGNITE).setOnUpdate(LAMBDA_SPAWN_C130_SUPPLIESS).setCasing(new SpentCasing(CasingType.STRAIGHT).setColor(0x3C80F0).setScale(2F).register("g26FlareSupply"));
		g26_flare_weapon = new BulletConfig().setItem(EnumAmmo.G26_FLARE_WEAPON).setCasing(EnumCasingType.LARGE, 4).setLife(100).setVel(2F).setGrav(0.015D).setRenderRotations(false).setOnImpact(LAMBDA_STANDARD_IGNITE).setOnUpdate(LAMBDA_SPAWN_C130_WEAPONS).setCasing(new SpentCasing(CasingType.STRAIGHT).setColor(0x278400).setScale(2F).register("g26FlareWeapon"));
		
		BulletConfig g40_base = new BulletConfig().setLife(200).setVel(2F).setGrav(0.035D);
		g40_he = g40_base.clone().setItem(EnumAmmo.G40_HE).setCasing(EnumCasingType.LARGE, 4).setOnImpact(LAMBDA_STANDARD_EXPLODE).setCasing(new SpentCasing(CasingType.STRAIGHT).setColor(0x777777).setScale(2, 2F, 1.5F).register("g40"));
		g40_heat = g40_base.clone().setItem(EnumAmmo.G40_HEAT).setCasing(EnumCasingType.LARGE, 4).setOnImpact(LAMBDA_STANDARD_EXPLODE_HEAT).setDamage(0.5F).setCasing(new SpentCasing(CasingType.STRAIGHT).setColor(0x5E6854).setScale(2, 2F, 1.5F).register("g40heat"));
		g40_demo = g40_base.clone().setItem(EnumAmmo.G40_DEMO).setCasing(EnumCasingType.LARGE, 4).setOnImpact(LAMBDA_STANDARD_EXPLODE_DEMO).setDamage(0.75F).setCasing(new SpentCasing(CasingType.STRAIGHT).setColor(0xE30000).setScale(2, 2F, 1.5F).register("g40demo"));
		g40_inc = g40_base.clone().setItem(EnumAmmo.G40_INC).setCasing(EnumCasingType.LARGE, 4).setOnImpact(LAMBDA_STANDARD_EXPLODE_INC).setDamage(0.75F).setCasing(new SpentCasing(CasingType.STRAIGHT).setColor(0xE86F20).setScale(2, 2F, 1.5F).register("g40inc"));
		g40_phosphorus = g40_base.clone().setItem(EnumAmmo.G40_PHOSPHORUS).setCasing(EnumCasingType.LARGE, 4).setOnImpact(LAMBDA_STANDARD_EXPLODE_PHOSPHORUS).setDamage(0.75F).setCasing(new SpentCasing(CasingType.STRAIGHT).setColor(0xC8C8C8).setScale(2, 2F, 1.5F).register("g40phos"));

		ModItems.gun_flaregun = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(100).draw(7).inspect(39).crosshair(Crosshair.L_CIRCUMFLEX).smoke(LAMBDA_SMOKE)
				.rec(new Receiver(0)
						.dmg(15F).delay(20).reload(28).jam(33).sound("hbm:weapon.hkShoot", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 1).addConfigs(g26_flare, g26_flare_supply, g26_flare_weapon))
						.offset(0.75, -0.0625, -0.1875D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_GL))
				.setupStandardConfiguration()
				.anim(LAMBDA_FLAREGUN_ANIMS).orchestra(Orchestras.ORCHESTRA_FLAREGUN)
				).setUnlocalizedName("gun_flaregun");
		
		ModItems.gun_congolake = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(400).draw(7).inspect(39).reloadSequential(true).reloadChangeType(true).crosshair(Crosshair.L_CIRCUMFLEX).smoke(LAMBDA_SMOKE)
				.rec(new Receiver(0)
						.dmg(20F).delay(24).reload(16, 16, 16, 0).jam(0).sound("hbm:weapon.glShoot", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 4).addConfigs(g40_he, g40_heat, g40_demo, g40_inc, g40_phosphorus))
						.offset(0.75, -0.0625, -0.1875D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_GL))
				.setupStandardConfiguration()
				.anim(LAMBDA_CONGOLAKE_ANIMS).orchestra(Orchestras.ORCHESTRA_CONGOLAKE)
				).setUnlocalizedName("gun_congolake");
	}
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_SMOKE = (stack, ctx) -> {
		Lego.handleStandardSmoke(ctx.entity, stack, 1500, 0.025D, 1.05D, 0);
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_GL = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil(10, (float) (ctx.getPlayer().getRNG().nextGaussian() * 1.5));
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_FLAREGUN_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(-90, 0, 0, 0).addPos(0, 0, 0, 350, IType.SIN_DOWN));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, 0, 50).addPos(0, 0, -3, 50).addPos(0, 0, 0, 250))
				.addBus("HAMMER", new BusAnimationSequence().addPos(15, 0, 0, 50).addPos(15, 0, 0, 550).addPos(0, 0, 0, 100));
		case CYCLE_DRY: return new BusAnimation()
				.addBus("HAMMER", new BusAnimationSequence().addPos(15, 0, 0, 50).addPos(15, 0, 0, 550).addPos(0, 0, 0, 100));
		case RELOAD: return new BusAnimation()
				.addBus("OPEN", new BusAnimationSequence().addPos(45, 0, 0, 200, IType.SIN_FULL).addPos(45, 0, 0, 750).addPos(0, 0, 0, 200, IType.SIN_UP))
				.addBus("SHELL", new BusAnimationSequence().addPos(4, -8, -4, 0).addPos(4, -8, -4, 200).addPos(0, 0, -5, 500, IType.SIN_DOWN).addPos(0, 0, 0, 200, IType.SIN_UP))
				.addBus("FLIP", new BusAnimationSequence().addPos(0, 0, 0, 200).addPos(25, 0, 0, 200, IType.SIN_DOWN).addPos(25, 0, 0, 800).addPos(0, 0, 0, 200, IType.SIN_DOWN));
		case JAMMED: return new BusAnimation()
				.addBus("OPEN", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(45, 0, 0, 200, IType.SIN_FULL).addPos(45, 0, 0, 500).addPos(0, 0, 0, 200, IType.SIN_UP))
				.addBus("FLIP", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, 0, 200).addPos(25, 0, 0, 200, IType.SIN_DOWN).addPos(25, 0, 0, 550).addPos(0, 0, 0, 200, IType.SIN_DOWN));
		case INSPECT: return new BusAnimation()
				.addBus("FLIP", new BusAnimationSequence().addPos(-360 * 3, 0, 0, 1500, IType.SIN_FULL));
		}
		
		return null;
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_CONGOLAKE_ANIMS = (stack, type) -> {
		int ammo = ((ItemGunBaseNT) stack.getItem()).getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, MainRegistry.proxy.me().inventory);
		switch(type) {
		case EQUIP: return ResourceManager.congolake_anim.get("Equip");
		case CYCLE: return ResourceManager.congolake_anim.get(ammo <= 1 ? "FireEmpty" : "Fire");
		case RELOAD: return ResourceManager.congolake_anim.get(ammo == 0 ? "ReloadEmpty": "ReloadStart");
		case RELOAD_CYCLE: return ResourceManager.congolake_anim.get("Reload");
		case RELOAD_END: return ResourceManager.congolake_anim.get("ReloadEnd");
		case JAMMED: return ResourceManager.congolake_anim.get("Jammed");
		case INSPECT: return ResourceManager.congolake_anim.get("Inspect");
		}
		
		return null;
	};
}
