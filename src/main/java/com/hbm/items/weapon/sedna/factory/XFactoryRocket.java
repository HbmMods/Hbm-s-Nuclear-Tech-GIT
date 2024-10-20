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
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.impl.ItemGunStinger;
import com.hbm.items.weapon.sedna.mags.MagazineSingleReload;
import com.hbm.lib.RefStrings;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

public class XFactoryRocket {

	public static BulletConfig rocket_rpzb_he;
	public static BulletConfig rocket_rpzb_heat;
	
	public static Consumer<EntityBulletBaseMK4> LAMBDA_STANDARD_ACCELERATE = (bullet) -> {
		if(bullet.accel < 7) bullet.accel += 0.4D;
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

		ModItems.gun_panzerschreck = new ItemGunBaseNT(new GunConfig()
				.dura(300).draw(7).inspect(40).crosshair(Crosshair.L_CIRCUMFLEX)
				.rec(new Receiver(0)
						.dmg(25F).delay(5).reload(50).jam(40).sound("hbm:weapon.rpgShoot", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 1).addConfigs(rocket_rpzb_he, rocket_rpzb_heat))
						.offset(1, -0.0625 * 1.5, -0.1875D)
						.setupStandardFire().recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.setupStandardConfiguration()
				.anim(LAMBDA_PANZERSCHRECK_ANIMS).orchestra(Orchestras.ORCHESTRA_PANERSCHRECK)
				).setUnlocalizedName("gun_panzerschreck").setTextureName(RefStrings.MODID + ":gun_darter");

		ModItems.gun_stinger = new ItemGunStinger(new GunConfig()
				.dura(300).draw(7).inspect(40).crosshair(Crosshair.L_BOX_OUTLINE)
				.rec(new Receiver(0)
						.dmg(25F).delay(5).reload(50).jam(40).sound("hbm:weapon.rpgShoot", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 1).addConfigs(rocket_rpzb_he, rocket_rpzb_heat))
						.offset(1, -0.0625 * 1.5, -0.1875D)
						.setupStandardFire().recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.setupStandardConfiguration().ps(LAMBDA_STINGER_SECONDARY_PRESS).rs(LAMBDA_STINGER_SECONDARY_RELEASE)
				.anim(LAMBDA_PANZERSCHRECK_ANIMS).orchestra(Orchestras.ORCHESTRA_STINGER)
				).setUnlocalizedName("gun_stinger").setTextureName(RefStrings.MODID + ":gun_darter");
	}

	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STINGER_SECONDARY_PRESS = (stack, ctx) -> { ItemGunStinger.setIsLockingOn(stack, true); };
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STINGER_SECONDARY_RELEASE = (stack, ctx) -> { ItemGunStinger.setIsLockingOn(stack, false); };

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_PANZERSCHRECK_ANIMS = (stack, type) -> {
		boolean empty = ((ItemGunBaseNT) stack.getItem()).getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack) <= 0;
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
}
