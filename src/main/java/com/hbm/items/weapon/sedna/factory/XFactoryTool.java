package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorBulkie;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockMutatorDebris;
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
import com.hbm.items.weapon.sedna.impl.ItemGunChargeThrower;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.particle.helper.ExplosionCreator;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.util.Vec3NT;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

public class XFactoryTool {
	
	public static BulletConfig ct_hook;
	public static BulletConfig ct_mortar;
	public static BulletConfig ct_mortar_charge;

	public static Consumer<Entity> LAMBDA_SET_HOOK = (entity) -> {
		EntityBulletBaseMK4 bullet = (EntityBulletBaseMK4) entity;
		if(!bullet.worldObj.isRemote && bullet.ticksExisted < 2 && bullet.getThrower() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) bullet.getThrower();
			if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.gun_charge_thrower) {
				ItemGunChargeThrower.setLastHook(player.getHeldItem(), bullet.getEntityId());
			}
		}
		bullet.ignoreFrustumCheck = true;
	};
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_HOOK = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.BLOCK) {
			Vec3NT vec = new Vec3NT(-bullet.motionX, -bullet.motionY, -bullet.motionZ).normalizeSelf().multiply(0.05);
			bullet.setPosition(mop.hitVec.xCoord + vec.xCoord, mop.hitVec.yCoord + vec.yCoord, mop.hitVec.zCoord + vec.zCoord);
			bullet.getStuck(mop.blockX, mop.blockY, mop.blockZ, mop.sideHit);
		}
	};
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_MORTAR = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3 && mop.entityHit == bullet.getThrower()) return;
		ExplosionVNT vnt = new ExplosionVNT(bullet.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 5, bullet.getThrower());
		vnt.setBlockAllocator(new BlockAllocatorBulkie(60, 8));
		vnt.setBlockProcessor(new BlockProcessorStandard());
		vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, bullet.damage).setupPiercing(bullet.config.armorThresholdNegation, bullet.config.armorPiercingPercent));
		vnt.setPlayerProcessor(new PlayerProcessorStandard());
		vnt.setSFX(new ExplosionEffectWeapon(10, 2.5F, 1F));
		vnt.explode();
	};
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_MORTAR_CHARGE = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3 && mop.entityHit == bullet.getThrower()) return;
		ExplosionVNT vnt = new ExplosionVNT(bullet.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 15, bullet.getThrower());
		vnt.setBlockAllocator(new BlockAllocatorStandard());
		vnt.setBlockProcessor(new BlockProcessorStandard().setNoDrop().withBlockEffect(new BlockMutatorDebris(ModBlocks.block_slag, 1)));
		vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, bullet.damage).setupPiercing(bullet.config.armorThresholdNegation, bullet.config.armorPiercingPercent));
		vnt.setPlayerProcessor(new PlayerProcessorStandard());
		ExplosionCreator.composeEffectSmall(bullet.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord + 0.5, mop.hitVec.zCoord);
		vnt.explode();
	};

	public static void init() {

		ct_hook = new BulletConfig().setItem(EnumAmmo.CT_HOOK).setRenderRotations(false).setLife(6_000).setVel(3F).setGrav(0.035D).setDoesPenetrate(true).setDamageFalloffByPen(false)
				.setOnUpdate(LAMBDA_SET_HOOK).setOnImpact(LAMBDA_HOOK);
		ct_mortar = new BulletConfig().setItem(EnumAmmo.CT_MORTAR).setLife(200).setVel(3F).setGrav(0.035D)
				.setOnImpact(LAMBDA_MORTAR);
		ct_mortar_charge = new BulletConfig().setItem(EnumAmmo.CT_MORTAR_CHARGE).setLife(200).setVel(3F).setGrav(0.035D)
				.setOnImpact(LAMBDA_MORTAR_CHARGE);
		
		ModItems.gun_charge_thrower = new ItemGunChargeThrower(WeaponQuality.UTILITY, new GunConfig()
				.dura(3_000).draw(20).inspect(31).reloadChangeType(true).hideCrosshair(false).crosshair(Crosshair.L_CIRCUMFLEX)
				.rec(new Receiver(0)
						.dmg(5F).delay(4).dry(40).auto(true).spread(0F).spreadHipfire(0F).reload(60).jam(55).sound("hbm:weapon.fire.grenade", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 1).addConfigs(ct_hook, ct_mortar, ct_mortar_charge))
						.offset(1, -0.0625 * 2.5, -0.25D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_CT))
				.setupStandardConfiguration()
				.anim(LAMBDA_CT_ANIMS).orchestra(Orchestras.ORCHESTRA_CHARGE_THROWER)
				).setUnlocalizedName("gun_charge_thrower");
	}
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_CT = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil(10, (float) (ctx.getPlayer().getRNG().nextGaussian() * 1.5));
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_CT_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(-90, 0, 0, 0).addPos(0, 0, 0, 350, IType.SIN_DOWN));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, 0, 50).addPos(0, 0, -3, 50).addPos(0, 0, 0, 250))
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
}
