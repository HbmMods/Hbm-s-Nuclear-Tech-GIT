package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import com.hbm.entity.effect.EntityFireLingering;
import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectWeapon;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.impl.ItemGunStinger;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.items.weapon.sedna.mags.MagazineSingleReload;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.util.EntityDamageUtil;
import com.hbm.util.DamageResistanceHandler.DamageClass;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class XFactoryRocket {

	public static BulletConfig[] rocket_template;
	
	public static BulletConfig[] rocket_rpzb;
	public static BulletConfig[] rocket_qd;
	public static BulletConfig[] rocket_ml;

	// FLYING
	public static Consumer<Entity> LAMBDA_STANDARD_ACCELERATE = (entity) -> {
		EntityBulletBaseMK4 bullet = (EntityBulletBaseMK4) entity;
		if(bullet.accel < 7) bullet.accel += 0.4D;
	};
	public static Consumer<Entity> LAMBDA_STEERING_ACCELERATE = (entity) -> {
		EntityBulletBaseMK4 bullet = (EntityBulletBaseMK4) entity;
		if(bullet.accel < 4) bullet.accel += 0.4D;
		if(bullet.getThrower() == null || !(bullet.getThrower() instanceof EntityPlayer)) return;
		
		EntityPlayer player = (EntityPlayer) bullet.getThrower();
		if(Vec3.createVectorHelper(bullet.posX - player.posX, bullet.posY - player.posY, bullet.posZ - player.posZ).lengthVector() > 100) return;
		if(player.getHeldItem() == null || !(player.getHeldItem().getItem() instanceof ItemGunBaseNT) || !ItemGunBaseNT.getIsAiming(player.getHeldItem())) return;
		
		MovingObjectPosition mop = Library.rayTrace(player, 200, 1);
		if(mop == null || mop.hitVec == null) return;
		
		Vec3 vec = Vec3.createVectorHelper(mop.hitVec.xCoord - bullet.posX, mop.hitVec.yCoord - bullet.posY, mop.hitVec.zCoord - bullet.posZ);
		if(vec.lengthVector() < 3) return;
		vec = vec.normalize();
		
		double speed = Vec3.createVectorHelper(bullet.motionX, bullet.motionY, bullet.motionZ).lengthVector();
		bullet.motionX = vec.xCoord * speed;
		bullet.motionY = vec.yCoord * speed;
		bullet.motionZ = vec.zCoord * speed;
	};
	
	// IMPACT
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_EXPLODE = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3) return;
		Lego.standardExplode(bullet, mop, 5F); bullet.setDead();
	};
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_EXPLODE_HEAT = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3) return;
		Lego.standardExplode(bullet, mop, 3.5F); bullet.setDead();
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && mop.entityHit instanceof EntityLivingBase) {
			EntityLivingBase living = (EntityLivingBase) mop.entityHit;
			EntityDamageUtil.attackEntityFromNT(living, bullet.config.getDamage(bullet, bullet.getThrower(), DamageClass.EXPLOSIVE), bullet.damage * 3F, true, true, 0.5F, 5F, 0.2F);
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
		spawnFire(bullet, mop, false, 300);
	};
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_EXPLODE_PHOSPHORUS = (bullet, mop) -> {
		spawnFire(bullet, mop, true, 600);
	};
	
	public static void spawnFire(EntityBulletBaseMK4 bullet, MovingObjectPosition mop, boolean phosphorus, int duration) {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3) return;
		World world = bullet.worldObj;
		Lego.standardExplode(bullet, mop, 3F);
		EntityFireLingering fire = new EntityFireLingering(world).setArea(6, 2).setDuration(duration).setType(phosphorus ? EntityFireLingering.TYPE_PHOSPHORUS : EntityFireLingering.TYPE_DIESEL);
		fire.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
		world.spawnEntityInWorld(fire);
		bullet.setDead();
		for(int dx = -2; dx <= 2; dx++) {
			for(int dy = -2; dy <= 2; dy++) {
				for(int dz = -2; dz <= 2; dz++) {
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
	
	public static BulletConfig makeRPZB(BulletConfig original) { return original.clone(); }
	public static BulletConfig makeQD(BulletConfig original) { return original.clone().setLife(400).setOnUpdate(LAMBDA_STEERING_ACCELERATE); }
	public static BulletConfig makeML(BulletConfig original) { return original.clone(); }
	
	//this is starting to get messy but we need to put this crap *somewhere* and fragmenting it into a billion classes with two methods each just isn't gonna help
	public static void init() {

		rocket_template = new BulletConfig[5];
		
		BulletConfig baseRocket = new BulletConfig().setLife(300).setSelfDamageDelay(10).setVel(0F).setGrav(0D).setOnEntityHit(null).setOnRicochet(null).setOnUpdate(LAMBDA_STANDARD_ACCELERATE);
		
		rocket_template[0] = baseRocket.clone().setItem(EnumAmmo.ROCKET_HE).setOnImpact(LAMBDA_STANDARD_EXPLODE);
		rocket_template[1] = baseRocket.clone().setItem(EnumAmmo.ROCKET_HEAT).setDamage(0.5F).setOnImpact(LAMBDA_STANDARD_EXPLODE_HEAT);
		rocket_template[2] = baseRocket.clone().setItem(EnumAmmo.ROCKET_DEMO).setDamage(0.75F).setOnImpact(LAMBDA_STANDARD_EXPLODE_DEMO);
		rocket_template[3] = baseRocket.clone().setItem(EnumAmmo.ROCKET_INC).setDamage(0.75F).setOnImpact(LAMBDA_STANDARD_EXPLODE_INC);
		rocket_template[4] = baseRocket.clone().setItem(EnumAmmo.ROCKET_PHOSPHORUS).setDamage(0.75F).setOnImpact(LAMBDA_STANDARD_EXPLODE_PHOSPHORUS);

		rocket_rpzb = new BulletConfig[rocket_template.length];
		rocket_qd = new BulletConfig[rocket_template.length];
		rocket_ml = new BulletConfig[rocket_template.length];
		
		for(int i = 0; i < rocket_template.length; i++) {
			rocket_rpzb[i] = makeRPZB(rocket_template[i]);
			rocket_qd[i] = makeQD(rocket_template[i]);
			rocket_ml[i] = makeML(rocket_template[i]);
		}

		ModItems.gun_panzerschreck = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(300).draw(7).inspect(40).crosshair(Crosshair.L_CIRCUMFLEX)
				.rec(new Receiver(0)
						.dmg(25F).delay(5).reload(50).jam(40).sound("hbm:weapon.rpgShoot", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 1).addConfigs(rocket_rpzb))
						.offset(1, -0.0625 * 1.5, -0.1875D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_ROCKET))
				.setupStandardConfiguration()
				.anim(LAMBDA_PANZERSCHRECK_ANIMS).orchestra(Orchestras.ORCHESTRA_PANERSCHRECK)
				).setUnlocalizedName("gun_panzerschreck");

		ModItems.gun_stinger = new ItemGunStinger(WeaponQuality.A_SIDE, new GunConfig()
				.dura(300).draw(7).inspect(40).crosshair(Crosshair.L_BOX_OUTLINE)
				.rec(new Receiver(0)
						.dmg(35F).delay(5).reload(50).jam(40).sound("hbm:weapon.rpgShoot", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 1).addConfigs(rocket_rpzb))
						.offset(1, -0.0625 * 1.5, -0.1875D)
						.setupLockonFire().recoil(LAMBDA_RECOIL_ROCKET))
				.setupStandardConfiguration().ps(LAMBDA_STINGER_SECONDARY_PRESS).rs(LAMBDA_STINGER_SECONDARY_RELEASE)
				.anim(LAMBDA_PANZERSCHRECK_ANIMS).orchestra(Orchestras.ORCHESTRA_STINGER)
				).setUnlocalizedName("gun_stinger");

		ModItems.gun_quadro = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(400).draw(7).inspect(40).crosshair(Crosshair.L_CIRCUMFLEX).hideCrosshair(false)
				.rec(new Receiver(0)
						.dmg(40F).delay(10).reload(55).jam(40).sound("hbm:weapon.rpgShoot", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 4).addConfigs(rocket_qd))
						.offset(1, -0.0625 * 1.5, -0.1875D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_ROCKET))
				.setupStandardConfiguration()
				.anim(LAMBDA_QUADRO_ANIMS).orchestra(Orchestras.ORCHESTRA_QUADRO)
				).setUnlocalizedName("gun_quadro");

		ModItems.gun_missile_launcher = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(500).draw(20).inspect(40).crosshair(Crosshair.L_CIRCUMFLEX).hideCrosshair(false)
				.rec(new Receiver(0)
						.dmg(50F).delay(5).reload(48).jam(33).sound("hbm:weapon.rpgShoot", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 1).addConfigs(rocket_ml))
						.offset(1, -0.0625 * 1.5, -0.1875D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_ROCKET))
				.setupStandardConfiguration().pp(LAMBDA_MISSILE_LAUNCHER_PRIMARY_PRESS)
				.anim(LAMBDA_MISSILE_LAUNCHER_ANIMS).orchestra(Orchestras.ORCHESTRA_MISSILE_LAUNCHER)
				).setUnlocalizedName("gun_missile_launcher");
	}

	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STINGER_SECONDARY_PRESS = (stack, ctx) -> { ItemGunStinger.setIsLockingOn(stack, true); };
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STINGER_SECONDARY_RELEASE = (stack, ctx) -> { ItemGunStinger.setIsLockingOn(stack, false); };
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_MISSILE_LAUNCHER_PRIMARY_PRESS = (stack, ctx) -> {
		if(ItemGunBaseNT.getIsAiming(stack)) {
			int target = ItemGunStinger.getLockonTarget(ctx.getPlayer(), 150D, 20D);
			if(target != -1) {
				ItemGunBaseNT.setLockonTarget(stack, target);
				ItemGunBaseNT.setIsLockedOn(stack, true);
			}
		}
		Lego.LAMBDA_STANDARD_CLICK_PRIMARY.accept(stack, ctx);
		ItemGunBaseNT.setIsLockedOn(stack, false);
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_ROCKET = (stack, ctx) -> { };

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_PANZERSCHRECK_ANIMS = (stack, type) -> {
		boolean empty = ((ItemGunBaseNT) stack.getItem()).getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, MainRegistry.proxy.me().inventory) <= 0;
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(60, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_DOWN));
		case RELOAD: return new BusAnimation()
				.addBus("RELOAD", new BusAnimationSequence().addPos(90, 0, 0, 750, IType.SIN_FULL).addPos(90, 0, 0, 1000).addPos(0, 0, 0, 750, IType.SIN_FULL))
				.addBus("ROCKET", new BusAnimationSequence().addPos(0, -3, -6, 0).addPos(0, -3, -6, 750).addPos(0, 0, -6.5, 500, IType.SIN_DOWN).addPos(0, 0, 0, 350, IType.SIN_UP));
		case JAMMED: empty = false;
		case INSPECT:
			return new BusAnimation()
				.addBus("RELOAD", new BusAnimationSequence().addPos(90, 0, 0, 750, IType.SIN_FULL).addPos(90, 0, 0, 500).addPos(0, 0, 0, 750, IType.SIN_FULL))
				.addBus("ROCKET", new BusAnimationSequence().addPos(0, empty ? -3 : 0, 0, 0));
		}
		return null;
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_QUADRO_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(60, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_DOWN));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, -0.5, 50).addPos(0, 0, 0, 50));
		case RELOAD: return new BusAnimation()
				.addBus("RELOAD_ROTATE", new BusAnimationSequence().addPos(0, 0, 60, 500, IType.SIN_FULL).addPos(0, 0, 60, 1500).addPos(0, 0, 0, 750, IType.SIN_FULL))
				.addBus("RELOAD_PUSH", new BusAnimationSequence().addPos(-1, -1, 0, 0).addPos(-1, -1, 0, 500).addPos(-1, 0, 0, 350).addPos(0, 0, 0, 1000));
		case JAMMED:
		case INSPECT: return new BusAnimation()
				.addBus("RELOAD_ROTATE", new BusAnimationSequence().addPos(0, 0, 60, 750, IType.SIN_FULL).addPos(0, 0, 60, 500).addPos(0, 0, 0, 750, IType.SIN_FULL));
		}
		return null;
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_MISSILE_LAUNCHER_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(60, 0, 0, 0).addPos(0, 0, 0, 1000, IType.SIN_DOWN));
		case RELOAD: return new BusAnimation()
				.addBus("BARREL", new BusAnimationSequence().addPos(0, 0, 1.5, 150).addPos(0, 0, 1.5, 2100).addPos(0, 0, 0, 150))
				.addBus("OPEN", new BusAnimationSequence().addPos(0, 0, 0, 250).addPos(90, 0, 0, 500, IType.SIN_FULL).addPos(90, 0, 0, 1000).addPos(0, 0, 0, 500, IType.SIN_FULL))
				.addBus("EQUIP", new BusAnimationSequence().addPos(0, 0, 0, 2250).addPos(-1, 0, 0, 150, IType.SIN_DOWN).addPos(0, 0, 0, 150, IType.SIN_UP))
				.addBus("MISSILE", new BusAnimationSequence().addPos(-10, 0, 0, 0).addPos(-10, 0, 0, 750).addPos(3, 0, 2, 0).addPos(0, 0, -6, 350, IType.SIN_FULL).addPos(0, 0, 0, 350, IType.SIN_UP));
		case JAMMED:
		case INSPECT: return new BusAnimation()
				.addBus("BARREL", new BusAnimationSequence().addPos(0, 0, 1.5, 150).addPos(0, 0, 1.5, 1350).addPos(0, 0, 0, 150))
				.addBus("OPEN", new BusAnimationSequence().addPos(0, 0, 0, 250).addPos(90, 0, 0, 500, IType.SIN_FULL).addPos(90, 0, 0, 250).addPos(0, 0, 0, 500, IType.SIN_FULL))
				.addBus("EQUIP", new BusAnimationSequence().addPos(0, 0, 0, 1500).addPos(-1, 0, 0, 150, IType.SIN_DOWN).addPos(0, 0, 0, 150, IType.SIN_UP));
		}
		return null;
	};
}
