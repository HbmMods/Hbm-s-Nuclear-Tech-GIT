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
import com.hbm.items.weapon.sedna.impl.ItemGunStinger;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.items.weapon.sedna.mags.MagazineSingleReload;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class XFactoryRocket {

	public static BulletConfig rocket_rpzb_he;
	public static BulletConfig rocket_rpzb_heat;
	public static BulletConfig rocket_qd_he;
	public static BulletConfig rocket_qd_heat;

	public static Consumer<EntityBulletBaseMK4> LAMBDA_STANDARD_ACCELERATE = (bullet) -> {
		if(bullet.accel < 7) bullet.accel += 0.4D;
	};
	public static Consumer<EntityBulletBaseMK4> LAMBDA_STEERING_ACCELERATE = (bullet) -> {
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
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_EXPLODE = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3) return;
		Lego.standardExplode(bullet, mop, 5F); bullet.setDead();
	};
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_EXPLODE_HEAT = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3) return;
		Lego.standardExplode(bullet, mop, 3F, 0.25F); bullet.setDead();
	};
	
	public static void init() {

		rocket_rpzb_he = new BulletConfig().setItem(EnumAmmo.ROCKET_HE).setLife(300).setSelfDamageDelay(10).setVel(0F).setGrav(0D)
				.setOnImpact(LAMBDA_STANDARD_EXPLODE).setOnEntityHit(null).setOnRicochet(null).setOnUpdate(LAMBDA_STANDARD_ACCELERATE);
		rocket_rpzb_heat = new BulletConfig().setItem(EnumAmmo.ROCKET_HEAT).setLife(300).setDamage(1.5F).setSelfDamageDelay(10).setVel(0F).setGrav(0D)
				.setOnImpact(LAMBDA_STANDARD_EXPLODE_HEAT).setOnEntityHit(null).setOnRicochet(null).setOnUpdate(LAMBDA_STANDARD_ACCELERATE);
		rocket_qd_he = new BulletConfig().setItem(EnumAmmo.ROCKET_HE).setLife(400).setSelfDamageDelay(10).setVel(0F).setGrav(0D)
				.setOnImpact(LAMBDA_STANDARD_EXPLODE).setOnEntityHit(null).setOnRicochet(null).setOnUpdate(LAMBDA_STEERING_ACCELERATE);
		rocket_qd_heat = new BulletConfig().setItem(EnumAmmo.ROCKET_HEAT).setLife(400).setDamage(1.5F).setSelfDamageDelay(10).setVel(0F).setGrav(0D)
				.setOnImpact(LAMBDA_STANDARD_EXPLODE_HEAT).setOnEntityHit(null).setOnRicochet(null).setOnUpdate(LAMBDA_STEERING_ACCELERATE);

		ModItems.gun_panzerschreck = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(300).draw(7).inspect(40).crosshair(Crosshair.L_CIRCUMFLEX)
				.rec(new Receiver(0)
						.dmg(25F).delay(5).reload(50).jam(40).sound("hbm:weapon.rpgShoot", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 1).addConfigs(rocket_rpzb_he, rocket_rpzb_heat))
						.offset(1, -0.0625 * 1.5, -0.1875D)
						.setupStandardFire().recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.setupStandardConfiguration()
				.anim(LAMBDA_PANZERSCHRECK_ANIMS).orchestra(Orchestras.ORCHESTRA_PANERSCHRECK)
				).setUnlocalizedName("gun_panzerschreck");

		ModItems.gun_stinger = new ItemGunStinger(WeaponQuality.A_SIDE, new GunConfig()
				.dura(300).draw(7).inspect(40).crosshair(Crosshair.L_BOX_OUTLINE)
				.rec(new Receiver(0)
						.dmg(25F).delay(5).reload(50).jam(40).sound("hbm:weapon.rpgShoot", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 1).addConfigs(rocket_rpzb_he, rocket_rpzb_heat))
						.offset(1, -0.0625 * 1.5, -0.1875D)
						.setupLockonFire().recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.setupStandardConfiguration().ps(LAMBDA_STINGER_SECONDARY_PRESS).rs(LAMBDA_STINGER_SECONDARY_RELEASE)
				.anim(LAMBDA_PANZERSCHRECK_ANIMS).orchestra(Orchestras.ORCHESTRA_STINGER)
				).setUnlocalizedName("gun_stinger");

		ModItems.gun_quadro = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(300).draw(7).inspect(40).crosshair(Crosshair.L_CIRCUMFLEX).hideCrosshair(false)
				.rec(new Receiver(0)
						.dmg(25F).delay(10).reload(55).jam(40).sound("hbm:weapon.rpgShoot", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 4).addConfigs(rocket_qd_he, rocket_qd_heat))
						.offset(1, -0.0625 * 1.5, -0.1875D)
						.setupStandardFire().recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.setupStandardConfiguration()
				.anim(LAMBDA_QUADRO_ANIMS).orchestra(Orchestras.ORCHESTRA_QUADRO)
				).setUnlocalizedName("gun_quadro");
	}

	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STINGER_SECONDARY_PRESS = (stack, ctx) -> { ItemGunStinger.setIsLockingOn(stack, true); };
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STINGER_SECONDARY_RELEASE = (stack, ctx) -> { ItemGunStinger.setIsLockingOn(stack, false); };

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
}
