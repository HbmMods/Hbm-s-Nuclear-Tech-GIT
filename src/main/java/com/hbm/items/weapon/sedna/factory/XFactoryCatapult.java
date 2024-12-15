package com.hbm.items.weapon.sedna.factory;

import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectWeapon;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.handler.radiation.ChunkRadiationManager;
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
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class XFactoryCatapult {

	public static BulletConfig nuke_standard;
	public static BulletConfig nuke_demo;
	public static BulletConfig nuke_high;
	public static BulletConfig nuke_tots;
	public static BulletConfig nuke_hive;
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_NUKE_STANDARD = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3) return;
		if(bullet.isDead) return;
		bullet.setDead();
		
		ExplosionVNT vnt = new ExplosionVNT(bullet.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 10);
		vnt.setEntityProcessor(new EntityProcessorCrossSmooth(2, bullet.damage).withRangeMod(1.5F));
		vnt.setPlayerProcessor(new PlayerProcessorStandard());
		vnt.explode();
		
		incrementRad(bullet.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 1F);
		spawnMush(bullet, mop);
	};
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_NUKE_DEMO = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3) return;
		if(bullet.isDead) return;
		bullet.setDead();
		
		ExplosionVNT vnt = new ExplosionVNT(bullet.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 10);
		vnt.setBlockAllocator(new BlockAllocatorStandard(64));
		vnt.setBlockProcessor(new BlockProcessorStandard());
		vnt.setEntityProcessor(new EntityProcessorCrossSmooth(2, bullet.damage).withRangeMod(1.5F));
		vnt.setPlayerProcessor(new PlayerProcessorStandard());
		vnt.explode();
		
		incrementRad(bullet.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 1.5F);
		spawnMush(bullet, mop);
	};
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_NUKE_HIGH = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3) return;
		if(bullet.isDead) return;
		bullet.setDead();
		bullet.worldObj.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(bullet.worldObj, 35, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord));
		spawnMush(bullet, mop);
	};
	
	public static void incrementRad(World world, double posX, double posY, double posZ, float mult) {
		for(int i = -2; i <= 2; i++) { for(int j = -2; j <= 2; j++) {
				if(Math.abs(i) + Math.abs(j) < 4) {
					ChunkRadiationManager.proxy.incrementRad(world, (int) Math.floor(posX + i * 16), (int) Math.floor(posY), (int) Math.floor(posZ + j * 16), 50F / (Math.abs(i) + Math.abs(j) + 1) * mult);
				}
			}
		}
	}
	
	public static void spawnMush(EntityBulletBaseMK4 bullet, MovingObjectPosition mop) {
		bullet.worldObj.playSoundEffect(mop.hitVec.xCoord, mop.hitVec.yCoord + 0.5, mop.hitVec.zCoord, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "muke");
		data.setBoolean("balefire", MainRegistry.polaroidID == 11 || bullet.worldObj.rand.nextInt(100) == 0);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, mop.hitVec.xCoord, mop.hitVec.yCoord + 0.5, mop.hitVec.zCoord), new TargetPoint(bullet.dimension, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 250));
	}
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_NUKE_TINYTOT = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3) return;
		if(bullet.isDead) return;
		bullet.setDead();
		
		ExplosionVNT vnt = new ExplosionVNT(bullet.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 5);
		vnt.setEntityProcessor(new EntityProcessorCrossSmooth(2, bullet.damage).withRangeMod(1.5F));
		vnt.setPlayerProcessor(new PlayerProcessorStandard());
		vnt.explode();
		
		incrementRad(bullet.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 0.25F);
		bullet.worldObj.playSoundEffect(mop.hitVec.xCoord, mop.hitVec.yCoord + 0.5, mop.hitVec.zCoord, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "tinytot");
		data.setBoolean("balefire", MainRegistry.polaroidID == 11 || bullet.worldObj.rand.nextInt(100) == 0);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, mop.hitVec.xCoord, mop.hitVec.yCoord + 0.5, mop.hitVec.zCoord), new TargetPoint(bullet.dimension, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 250));
	};
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_NUKE_HIVE = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3) return;
		if(bullet.isDead) return;
		bullet.setDead();
		ExplosionVNT vnt = new ExplosionVNT(bullet.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 5);
		vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, bullet.damage).withRangeMod(1.5F));
		vnt.setPlayerProcessor(new PlayerProcessorStandard());
		vnt.setSFX(new ExplosionEffectWeapon(10, 2.5F, 1F));
		vnt.explode();
	};
	
	public static void init() {

		nuke_standard = new BulletConfig().setItem(EnumAmmo.NUKE_STANDARD).setLife(300).setVel(3F).setGrav(0.025F).setOnImpact(LAMBDA_NUKE_STANDARD);
		nuke_demo = new BulletConfig().setItem(EnumAmmo.NUKE_DEMO).setLife(300).setVel(3F).setGrav(0.025F).setOnImpact(LAMBDA_NUKE_DEMO);
		nuke_high = new BulletConfig().setItem(EnumAmmo.NUKE_HIGH).setLife(300).setVel(3F).setGrav(0.025F).setOnImpact(LAMBDA_NUKE_HIGH);
		nuke_tots = new BulletConfig().setItem(EnumAmmo.NUKE_TOTS).setProjectiles(8).setLife(300).setVel(3F).setGrav(0.025F).setSpread(0.1F).setDamage(0.35F).setOnImpact(LAMBDA_NUKE_TINYTOT);
		nuke_hive = new BulletConfig().setItem(EnumAmmo.NUKE_HIVE).setProjectiles(12).setLife(300).setVel(1F).setGrav(0.025F).setSpread(0.15F).setDamage(0.25F).setOnImpact(LAMBDA_NUKE_HIVE);

		ModItems.gun_fatman = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(300).draw(20).inspect(30).crosshair(Crosshair.L_CIRCUMFLEX).hideCrosshair(false)
				.rec(new Receiver(0)
						.dmg(100F).delay(10).reload(57).jam(40).sound("hbm:weapon.fire.fatman", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 1).addConfigs(nuke_standard, nuke_demo, nuke_high, nuke_tots, nuke_hive))
						.offset(1, -0.0625 * 1.5, -0.1875D).offsetScoped(1, -0.0625 * 1.5, -0.125D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_FATMAN))
				.setupStandardConfiguration()
				.anim(LAMBDA_FATMAN_ANIMS).orchestra(Orchestras.ORCHESTRA_FATMAN)
				).setUnlocalizedName("gun_fatman");
	}
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_FATMAN = (stack, ctx) -> { };

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_FATMAN_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(60, 0, 0, 0).addPos(0, 0, 0, 1000, IType.SIN_DOWN));
		case CYCLE:
			Random rand = MainRegistry.proxy.me().getRNG();
			return new BusAnimation()
				.addBus("GAUGE", new BusAnimationSequence().addPos(0, 0, 135 + rand.nextInt(136), 100, IType.SIN_DOWN).addPos(0, 0, 0, 500, IType.SIN_DOWN))
				.addBus("PISTON", new BusAnimationSequence().addPos(0, 0, 3, 100, IType.SIN_UP))
				.addBus("NUKE", new BusAnimationSequence().addPos(0, 0, 3, 100, IType.SIN_UP).addPos(0, 0, 0, 0));
		case RELOAD: return new BusAnimation()
				.addBus("LID", new BusAnimationSequence().addPos(0, 0, 0, 250).addPos(0, 0, -45, 250, IType.SIN_UP).addPos(0, 0, -45, 1200).addPos(0, 0, 0, 250, IType.SIN_UP))
				.addBus("HANDLE", new BusAnimationSequence().addPos(0, 0, -2, 500, IType.SIN_FULL).addPos(0, 0, -2, 1700).addPos(0, 0, 0, 750, IType.SIN_FULL))
				.addBus("NUKE", new BusAnimationSequence().addPos(5, -4, 3, 0).addPos(5, -4, 3, 750).addPos(2, 0.5, 3, 500, IType.SIN_UP).addPos(1, 0.5, 3, 100).addPos(0, 0, 3, 100).addPos(0, 0, 3, 750).addPos(0, 0, 0, 750, IType.SIN_FULL))
				.addBus("PISTON", new BusAnimationSequence().addPos(0, 0, 3, 0).addPos(0, 0, 3, 2200).addPos(0, 0, 0, 750, IType.SIN_FULL))
				.addBus("EQUIP", new BusAnimationSequence().addPos(5, 0, 0, 500, IType.SIN_FULL).addPos(0, 0, 0, 500, IType.SIN_FULL).addPos(0, 0, 0, 450).addPos(3, 0, 0, 100, IType.SIN_DOWN).addPos(0, 0, 0, 100, IType.SIN_FULL).addPos(0, 0, 0, 500).addPos(-10, 0, 0, 375, IType.SIN_DOWN).addPos(0, 0, 0, 375, IType.SIN_UP));
		case JAMMED: return new BusAnimation()
				.addBus("HANDLE", new BusAnimationSequence().addPos(0, 0, 0, 750).addPos(0, 0, -2, 250, IType.SIN_FULL).addPos(0, 0, 0, 250, IType.SIN_FULL).addPos(0, 0, -2, 250, IType.SIN_FULL).addPos(0, 0, 0, 250, IType.SIN_FULL))
				.addBus("EQUIP", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(-15, 0, 0, 250, IType.SIN_FULL).addPos(-15, 0, 0, 1000).addPos(0, 0, 0, 250, IType.SIN_FULL));
		case INSPECT: return new BusAnimation()
				.addBus("HANDLE", new BusAnimationSequence().addPos(0, 0, 0, 250).addPos(0, 0, -2, 250, IType.SIN_FULL).addPos(0, 0, 0, 250, IType.SIN_FULL).addPos(0, 0, -2, 250, IType.SIN_FULL).addPos(0, 0, 0, 250, IType.SIN_FULL))
				.addBus("EQUIP", new BusAnimationSequence().addPos(-15, 0, 0, 250, IType.SIN_FULL).addPos(-15, 0, 0, 1000).addPos(0, 0, 0, 250, IType.SIN_FULL));
		}
		return null;
	};
}
