package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiFunction;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.mags.MagazineBelt;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.item.ItemStack;

public class XFactoryEnergy {

	public static BulletConfig energy_tesla;
	
	public static void init() {
		
		energy_tesla = new BulletConfig().setItem(EnumAmmo.P9_SP).setSpread(0.01F);

		ModItems.gun_tesla_cannon = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(2_000).draw(10).inspect(33).reloadSequential(true).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(15F).delay(10).reload(44).jam(19).sound("hbm:weapon.fire.blackPowder", 1.0F, 1.0F)
						.mag(new MagazineBelt().addConfigs(energy_tesla))
						.offset(0.75, -0.125, -0.25)
						.setupStandardFire().recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.setupStandardConfiguration()
				.anim(LAMBDA_TESLA_ANIMS).orchestra(Orchestras.ORCHESTRA_TESLA)
				).setUnlocalizedName("gun_tesla_cannon");
	}

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_TESLA_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(60, 0, 0, 0).addPos(0, 0, 0, 1000, IType.SIN_DOWN));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, -1, 50, IType.SIN_DOWN).addPos(0, 0, 0, 150, IType.SIN_FULL))
				.addBus("CYCLE", new BusAnimationSequence().addPos(0, 0, 0, 150).addPos(0, 0, 18, 100));
		case CYCLE_DRY: return new BusAnimation()
				.addBus("CYCLE", new BusAnimationSequence().addPos(0, 0, 0, 150).addPos(0, 0, 18, 100));
		case RELOAD: return new BusAnimation()
				.addBus("MAG", new BusAnimationSequence().addPos(0, -8, 0, 250, IType.SIN_UP).addPos(0, -8, 0, 1000).addPos(0, 0, 0, 300))
				.addBus("LIFT", new BusAnimationSequence().addPos(0, 0, 0, 750).addPos(-25, 0, 0, 300, IType.SIN_FULL).addPos(-25, 0, 0, 500).addPos(-27, 0, 0, 100, IType.SIN_DOWN).addPos(-25, 0, 0, 100, IType.SIN_FULL).addPos(-25, 0, 0, 150).addPos(0, 0, 0, 300, IType.SIN_FULL));
		case JAMMED: return new BusAnimation()
				.addBus("MAG", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, -2, 0, 150, IType.SIN_UP).addPos(0, 0, 0, 100))
				.addBus("LIFT", new BusAnimationSequence().addPos(0, 0, 0, 750).addPos(-2, 0, 0, 100, IType.SIN_DOWN).addPos(0, 0, 0, 100, IType.SIN_FULL));
		case INSPECT: return new BusAnimation()
				.addBus("MAG", new BusAnimationSequence()
						.addPos(0, -1, 0, 150).addPos(6, -1, 0, 150).addPos(6, 12, 0, 350, IType.SIN_DOWN).addPos(6, -2, 0, 350, IType.SIN_UP).addPos(6, -1, 0, 50)
						.addPos(6, -1, 0, 100).addPos(0, -1, 0, 150, IType.SIN_FULL).addPos(0, 0, 0, 150, IType.SIN_UP))
				.addBus("SPEEN", new BusAnimationSequence().addPos(0, 0, 0, 300).addPos(360, 0, 0, 700))
				.addBus("LIFT", new BusAnimationSequence().addPos(0, 0, 0, 1450).addPos(-2, 0, 0, 100, IType.SIN_DOWN).addPos(0, 0, 0, 100, IType.SIN_FULL));
		}
		
		return null;
	};
}
