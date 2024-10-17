package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.mags.MagazineSingleReload;
import com.hbm.lib.RefStrings;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

public class XFactoryRocket {

	public static BulletConfig rocket_rpzb_he;
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_EXPLODE = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3) return;
		Lego.standardExplode(bullet, mop, 5F); bullet.setDead();
	};
	
	public static void init() {
		
		rocket_rpzb_he = new BulletConfig().setItem(EnumAmmo.ROCKET_HE).setOnImpact(LAMBDA_STANDARD_EXPLODE).setLife(300).setSelfDamageDelay(10).setVel(0F).setGrav(0D).setOnUpdate((bullet) -> {
			if(bullet.accel < 7) bullet.accel += 0.4D;
		});

		ModItems.gun_panzerschreck = new ItemGunBaseNT(new GunConfig()
				.dura(300).draw(7).inspect(39).crosshair(Crosshair.L_CIRCUMFLEX)
				.rec(new Receiver(0)
						.dmg(15F).delay(20).reload(28).jam(33).sound("hbm:weapon.hkShoot", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 1).addConfigs(rocket_rpzb_he))
						.offset(1, -0.0625 * 1.5, -0.1875D)
						.canFire(Lego.LAMBDA_STANDARD_CAN_FIRE).fire(Lego.LAMBDA_STANDARD_FIRE).recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.setupStandardConfiguration()
				.anim(LAMBDA_PANZERSCHRECK_ANIMS).orchestra(Orchestras.ORCHESTRA_FLAREGUN)
				).setUnlocalizedName("gun_panzerschreck").setTextureName(RefStrings.MODID + ":gun_darter");
	}

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_PANZERSCHRECK_ANIMS = (stack, type) -> {
		return null;
	};
}
