package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
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

	public static void init() {

		ct_hook = new BulletConfig().setItem(EnumAmmo.CT_HOOK).setRenderRotations(false).setLife(1200).setVel(2F).setGrav(0.035D).setDoesPenetrate(true).setDamageFalloffByPen(false)
				.setOnUpdate(LAMBDA_SET_HOOK).setOnImpact(LAMBDA_HOOK);
		
		ModItems.gun_charge_thrower = new ItemGunChargeThrower(WeaponQuality.UTILITY, new GunConfig()
				.dura(3_000).draw(20).inspect(31).reloadChangeType(true).hideCrosshair(false).crosshair(Crosshair.L_CIRCUMFLEX)
				.rec(new Receiver(0)
						.dmg(5F).delay(4).dry(40).auto(true).spread(0F).spreadHipfire(0F).reload(60).jam(55).sound("hbm:weapon.fire.grenade", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 1).addConfigs(ct_hook))
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
