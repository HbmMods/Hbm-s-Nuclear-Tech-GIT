package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.item.ItemStack;

public class XFactory556mm {

	public static BulletConfig r556_sp;
	public static BulletConfig r556_fmj;
	public static BulletConfig r556_jhp;
	public static BulletConfig r556_ap;

	public static void init() {
		SpentCasing casing556 = new SpentCasing(CasingType.BOTTLENECK).setColor(SpentCasing.COLOR_CASE_BRASS).setScale(0.8F);
		r556_sp = new BulletConfig().setItem(EnumAmmo.R556_SP)
				.setCasing(casing556.clone().register("r556"));
		r556_fmj = new BulletConfig().setItem(EnumAmmo.R556_FMJ).setDamage(0.8F).setArmorPiercing(0.1F)
				.setCasing(casing556.clone().register("r556fmj"));
		r556_jhp = new BulletConfig().setItem(EnumAmmo.R556_JHP).setDamage(1.5F).setArmorPiercing(-0.25F)
				.setCasing(casing556.clone().register("r556jhp"));
		r556_ap = new BulletConfig().setItem(EnumAmmo.R556_AP).setDoesPenetrate(true).setDamageFalloutByPen(false).setDamage(1.5F).setArmorPiercing(0.15F)
				.setCasing(casing556.clone().setColor(SpentCasing.COLOR_CASE_44).register("r556ap"));

		ModItems.gun_g3 = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(3_000).draw(10).inspect(33).crosshair(Crosshair.CIRCLE).smoke(LAMBDA_SMOKE)
				.rec(new Receiver(0)
						.dmg(15F).delay(2).auto(true).dry(15).spread(0.0F).reload(50).jam(47).sound("hbm:weapon.fire.blackPowder", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 30).addConfigs(r556_sp, r556_fmj, r556_jhp, r556_ap))
						.offset(1, -0.0625 * 2.5, -0.25D)
						.setupStandardFire().recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.setupStandardConfiguration().ps(Lego.LAMBDA_STANDARD_CLICK_SECONDARY)
				.anim(LAMBDA_G3_ANIMS).orchestra(Orchestras.ORCHESTRA_G3)
				).setUnlocalizedName("gun_g3");
	}
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_SMOKE = (stack, ctx) -> {
		Lego.handleStandardSmoke(ctx.entity, stack, 1500, 0.075D, 1.1D, 0);
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_G3_ANIMS = (stack, type) -> {
		boolean empty = ((ItemGunBaseNT) stack.getItem()).getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack) <= 0;
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(45, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_FULL));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, ItemGunBaseNT.getIsAiming(stack) ? -0.5 : -0.75, 25, IType.SIN_DOWN).addPos(0, 0, 0, 75, IType.SIN_FULL));
		case CYCLE_DRY: return new BusAnimation()
				.addBus("BOLT", new BusAnimationSequence().addPos(0, 0, 0, 250).addPos(0, 0, -3.25, 150).addPos(0, 0, 0, 100));
		case RELOAD:
			return new BusAnimation()
				.addBus("MAG", new BusAnimationSequence()
						.addPos(0, -8, 0, 250, IType.SIN_UP)	//250
						.addPos(0, -8, 0, 1000)					//1250
						.addPos(0, 0, 0, 300))					//1550
				.addBus("BOLT", new BusAnimationSequence()
						.addPos(0, 0, 0, 250)					//250
						.addPos(0, 0, -3.25, 150)				//400
						.addPos(0, 0, -3.25, 1250)				//1750
						.addPos(0, 0, 0, 100))					//1850
				.addBus("HANDLE", new BusAnimationSequence()
						.addPos(0, 0, 0, 500)					//500
						.addPos(0, 0, 45, 50)					//550
						.addPos(0, 0, 45, 1150)					//1700
						.addPos(0, 0, 0, 50))					//1750
				.addBus("LIFT", new BusAnimationSequence()
						.addPos(0, 0, 0, 750)					//750
						.addPos(-25, 0, 0, 500, IType.SIN_FULL)	//1250
						.addPos(-25, 0, 0, 750)					//2000
						.addPos(0, 0, 0, 500, IType.SIN_FULL))	//3500
				.addBus("BULLET", new BusAnimationSequence().addPos(empty ? 1 : 0, 0, 0, 0).addPos(0, 0, 0, 1000));
		case INSPECT: return new BusAnimation()
				.addBus("MAG", new BusAnimationSequence()
						.addPos(0, -1, 0, 150)					//150
						.addPos(2, -1, 0, 150)					//300
						.addPos(2, 8, 0, 350, IType.SIN_DOWN)	//650
						.addPos(2, -2, 0, 350, IType.SIN_UP)	//1000
						.addPos(2, -1, 0, 50)					//1050
						.addPos(2, -1, 0, 100)					//1150
						.addPos(0, -1, 0, 150, IType.SIN_FULL)	//1300
						.addPos(0, 0, 0, 150, IType.SIN_UP))	//1450
				.addBus("SPEEN", new BusAnimationSequence().addPos(0, 0, 0, 300).addPos(0, 360, 360, 700))
				.addBus("LIFT", new BusAnimationSequence().addPos(0, 0, 0, 1450).addPos(-2, 0, 0, 100, IType.SIN_DOWN).addPos(0, 0, 0, 100, IType.SIN_FULL))
				.addBus("BULLET", new BusAnimationSequence().addPos(empty ? 1 : 0, 0, 0, 0));
		case JAMMED: return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(-25, 0, 0, 250, IType.SIN_FULL).addPos(-25, 0, 0, 1250).addPos(0, 0, 0, 350, IType.SIN_FULL))
				.addBus("BOLT", new BusAnimationSequence().addPos(0, 0, 0, 1000).addPos(0, 0, -3.25, 150).addPos(0, 0, 0, 100).addPos(0, 0, 0, 250).addPos(0, 0, -3.25, 150).addPos(0, 0, 0, 100));
		}
		
		return null;
	};
}
