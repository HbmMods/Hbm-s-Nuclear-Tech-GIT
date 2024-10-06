package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiFunction;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.mags.MagazineSingleReload;
import com.hbm.lib.RefStrings;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.item.ItemStack;

public class XFactory12ga {

	public static BulletConfig g12_bp;
	public static BulletConfig g12_bp_magnum;
	public static BulletConfig g12_bp_slug;
	public static BulletConfig g12;
	
	public static void init() {
		
		g12_bp = new BulletConfig().setItem(EnumAmmo.G12_BP).setProjectiles(8, 8).setSpread(0.05F).setRicochetAngle(15).setCasing(new SpentCasing(CasingType.STRAIGHT).setColor(SpentCasing.COLOR_CASE_BRASS).setScale(1.5F).register("12GA_BP"));
		g12_bp_magnum = new BulletConfig().setItem(EnumAmmo.G12_BP_MAGNUM).setProjectiles(4, 4).setSpread(0.05F).setRicochetAngle(25).setCasing(new SpentCasing(CasingType.STRAIGHT).setColor(SpentCasing.COLOR_CASE_BRASS).setScale(1.5F).register("12GA_BP_MAGNUM"));
		g12_bp_slug = new BulletConfig().setItem(EnumAmmo.G12_BP_SLUG).setSpread(0.01F).setRicochetAngle(5).setCasing(new SpentCasing(CasingType.STRAIGHT).setColor(SpentCasing.COLOR_CASE_BRASS).setScale(1.5F).register("12GA_BP_SLUG"));
		g12 = new BulletConfig().setItem(EnumAmmo.G12).setProjectiles(8, 8).setSpread(0.05F).setRicochetAngle(15).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0xB52B2B, SpentCasing.COLOR_CASE_BRASS).setScale(0.75F).register("12GA"));

		ModItems.gun_maresleg = new ItemGunBaseNT(new GunConfig()
				.dura(300).draw(20).inspect(39).jam(45).reloadSequential(true).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE).orchestra(Orchestras.ORCHESTRA_MARESLEG)
				.rec(new Receiver(0)
						.dmg(12F).delay(20).reload(22, 10, 13, 0).sound("hbm:weapon.fire.blackPowder", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 6).addConfigs(g12_bp, g12_bp_magnum, g12_bp_slug, g12))
						.offset(0.75, -0.0625, -0.1875D)
						.canFire(Lego.LAMBDA_STANDARD_CAN_FIRE).fire(Lego.LAMBDA_STANDARD_FIRE).recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.setupStandardConfiguration().anim(LAMBDA_MARESLEG_ANIMS)
				).setUnlocalizedName("gun_maresleg").setTextureName(RefStrings.MODID + ":gun_darter");
	}

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_MARESLEG_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(-60, 0, 0, 0).addPos(0, 0, -3, 500, IType.SIN_DOWN));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, 0, 50).addPos(0, 0, -1, 50).addPos(0, 0, 0, 250))
				.addBus("SIGHT", new BusAnimationSequence().addPos(35, 0, 0, 100, IType.SIN_DOWN).addPos(0, 0, 0, 100, IType.SIN_FULL))
				.addBus("LEVER", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(-85, 0, 0, 200).addPos(0, 0, 0, 200))
				.addBus("TURN", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(0, 0, 45, 200, IType.SIN_DOWN).addPos(0, 0, 0, 200, IType.SIN_UP))
				.addBus("HAMMER", new BusAnimationSequence().addPos(30, 0, 0, 50).addPos(30, 0, 0, 550).addPos(0, 0, 0, 200));
		case CYCLE_DRY: return new BusAnimation()
				.addBus("LEVER", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(-90, 0, 0, 200).addPos(0, 0, 0, 200))
				.addBus("TURN", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(0, 0, 45, 200, IType.SIN_DOWN).addPos(0, 0, 0, 200, IType.SIN_UP))
				.addBus("HAMMER", new BusAnimationSequence().addPos(30, 0, 0, 50).addPos(30, 0, 0, 550).addPos(0, 0, 0, 200));
		case RELOAD:
			boolean empty = ((ItemGunBaseNT) stack.getItem()).getConfig(stack).getReceivers(stack)[0].getMagazine(stack).getAmount(stack) <= 0;
			return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(30, 0, 0, 400, IType.SIN_FULL))
				.addBus("LEVER", new BusAnimationSequence().addPos(0, 0, 0, 400).addPos(-85, 0, 0, 200))
				.addBus("SHELL", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(0, 0.25, -3, 0).addPos(0, empty ? 0.25 : 0.125, -1.5, 150, IType.SIN_UP).addPos(0, empty ? 0.25 : -0.25, 0, 150, IType.SIN_DOWN))
				.addBus("FLAG", new BusAnimationSequence().addPos(0, 0, 0, empty ? 900 : 0).addPos(1, 1, 1, 0));
		case RELOAD_CYCLE: return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(30, 0, 0, 0))
				.addBus("LEVER", new BusAnimationSequence().addPos(-85, 0, 0, 0))
				.addBus("SHELL", new BusAnimationSequence().addPos(0, 0.25, -3, 0).addPos(0, 0.125, -1.5, 150, IType.SIN_UP).addPos(0, -0.125, 0, 150, IType.SIN_DOWN))
				.addBus("FLAG", new BusAnimationSequence().addPos(1, 1, 1, 0));
		case RELOAD_END: return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(30, 0, 0, 0).addPos(30, 0, 0, 250).addPos(0, 0, 0, 400, IType.SIN_FULL))
				.addBus("LEVER", new BusAnimationSequence().addPos(-85, 0, 0, 0).addPos(0, 0, 0, 200))
				.addBus("FLAG", new BusAnimationSequence().addPos(1, 1, 1, 0));
		case JAMMED: return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(30, 0, 0, 0).addPos(30, 0, 0, 250).addPos(0, 0, 0, 400, IType.SIN_FULL))
				.addBus("LEVER", new BusAnimationSequence().addPos(-85, 0, 0, 0).addPos(-15, 0, 0, 200).addPos(-15, 0, 0, 650).addPos(-85, 0, 0, 200).addPos(-15, 0, 0, 200).addPos(-15, 0, 0, 200).addPos(-85, 0, 0, 200).addPos(0, 0, 0, 200))
				.addBus("TURN", new BusAnimationSequence().addPos(0, 0, 0, 850).addPos(0, 0, 45, 200, IType.SIN_DOWN).addPos(0, 0, 45, 800).addPos(0, 0, 0, 200, IType.SIN_UP))
				.addBus("FLAG", new BusAnimationSequence().addPos(1, 1, 1, 0));
		case INSPECT: return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(-35, 0, 0, 300, IType.SIN_FULL).addPos(-35, 0, 0, 1150).addPos(0, 0, 0, 500, IType.SIN_FULL))
				.addBus("TURN", new BusAnimationSequence().addPos(0, 0, 0, 450).addPos(0, 0, -90, 500, IType.SIN_FULL).addPos(0, 0, -90, 500).addPos(0, 0, 0, 500, IType.SIN_FULL));
		}
		
		return null;
	};
}
