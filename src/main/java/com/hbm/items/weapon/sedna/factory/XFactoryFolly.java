package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.GunState;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.mags.MagazineSingleReload;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.item.ItemStack;

public class XFactoryFolly {

	public static BulletConfig folly_sm;
	
	public static void init() {
		
		folly_sm = new BulletConfig().setItem(EnumAmmo.G26_FLARE).setLife(100).setVel(2F).setGrav(0.015D).setRenderRotations(false);

		ModItems.gun_folly = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(100).draw(40).crosshair(Crosshair.NONE)
				.rec(new Receiver(0)
						.dmg(15F).delay(26).dry(5).reload(160).jam(0).sound("hbm:weapon.fire.loudestNoiseOnEarth", 100.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 1).addConfigs(folly_sm))
						.offset(0.75, -0.0625, -0.1875D)
						.setupBeamFire().recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.setupStandardConfiguration().pt(LAMBDA_TOGGLE_AIM)
				.anim(LAMBDA_FOLLY_ANIMS).orchestra(Orchestras.ORCHESTRA_FOLLY)
				).setUnlocalizedName("gun_folly");
	}
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_TOGGLE_AIM = (stack, ctx) -> {
		if(ItemGunBaseNT.getState(stack, ctx.configIndex) == GunState.IDLE) {
			ItemGunBaseNT.setIsAiming(stack, !ItemGunBaseNT.getIsAiming(stack));
		}
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_FOLLY_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(-60, 0, 0, 0).addPos(5, 0, 0, 1500, IType.SIN_DOWN).addPos(0, 0, 0, 500, IType.SIN_FULL));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, -4.5, 50).addPos(0, 0, -4.5, 500).addPos(0, 0, 0, 500, IType.SIN_UP))
				.addBus("LOAD", new BusAnimationSequence().addPos(0, 0, 0, 50).addPos(-25, 0, 0, 250, IType.SIN_DOWN).addPos(0, 0, 0, 1000, IType.SIN_FULL));
		case RELOAD: return new BusAnimation()
				.addBus("LOAD", new BusAnimationSequence().addPos(60, 0, 0, 1000, IType.SIN_FULL).addPos(60, 0, 0, 6000).addPos(0, 0, 0, 1000, IType.SIN_FULL))
				.addBus("SCREW", new BusAnimationSequence().addPos(0, 0, 0, 1000).addPos(0, 0, -135, 1000, IType.SIN_FULL).addPos(0, 0, -135, 4000).addPos(0, 0, 0, 1000, IType.SIN_FULL))
				.addBus("BREECH", new BusAnimationSequence().addPos(0, 0, 0, 1000).addPos(0, 0, -0.5, 1000, IType.SIN_FULL).addPos(0, -4, -0.5, 1000, IType.SIN_FULL).addPos(0, -4, -0.5, 2000).addPos(0, 0, -0.5, 1000, IType.SIN_FULL).addPos(0, 0, 0, 1000, IType.SIN_FULL))
				.addBus("SHELL", new BusAnimationSequence().addPos(0, -4, -4.5, 0).addPos(0, -4, -4.5, 3000).addPos(0, 0, -4.5, 1000, IType.SIN_FULL).addPos(0, 0, 0, 500, IType.SIN_UP));
		}
		
		return null;
	};
}
