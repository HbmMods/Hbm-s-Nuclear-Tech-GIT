package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiFunction;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.lib.RefStrings;
import com.hbm.particle.helper.FlameCreator;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.item.ItemStack;

public class XFactoryFlamer {
	
	public static BulletConfig flame_diesel;

	public static void init() {
		flame_diesel = new BulletConfig().setItem(EnumAmmo.FLAME_DIESEL).setLife(100).setVel(1F).setGrav(0.02D).setReloadCount(200)
				.setOnUpdate((bullet) -> {
			if(!bullet.worldObj.isRemote) FlameCreator.composeEffect(bullet.worldObj, bullet.posX, bullet.posY - 0.25, bullet.posZ);
		});
		
		ModItems.gun_flamer = new ItemGunBaseNT(new GunConfig()
				.dura(20_000).draw(4).inspect(23).crosshair(Crosshair.CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(10F).delay(1).auto(true).reload(55).jam(0)
						.mag(new MagazineFullReload(0, 200).addConfigs(flame_diesel))
						.offset(0.75, -0.0625, -0.3125D)
						.canFire(Lego.LAMBDA_STANDARD_CAN_FIRE).fire(Lego.LAMBDA_STANDARD_FIRE).recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.setupStandardConfiguration()
				.anim(LAMBDA_FLAMER_ANIMS).orchestra(Orchestras.ORCHESTRA_FLAMER)
				).setUnlocalizedName("gun_flamer").setTextureName(RefStrings.MODID + ":gun_darter");
	}

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_FLAMER_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(-90, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_DOWN));
		case RELOAD: return new BusAnimation();
		case INSPECT: return new BusAnimation();
		case JAMMED: return new BusAnimation();
		}
		
		return null;
	};
}
