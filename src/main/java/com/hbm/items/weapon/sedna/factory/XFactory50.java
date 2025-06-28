package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.entity.projectile.EntityBuilding;
import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumCasingType;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmoSecret;
import com.hbm.items.weapon.sedna.mags.MagazineBelt;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.lib.RefStrings;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;

public class XFactory50 {

	public static final ResourceLocation scope = new ResourceLocation(RefStrings.MODID, "textures/misc/scope_amat.png");
	public static final ResourceLocation scope_thermal = new ResourceLocation(RefStrings.MODID, "textures/misc/scope_penance.png");
	
	public static BulletConfig bmg50_sp;
	public static BulletConfig bmg50_fmj;
	public static BulletConfig bmg50_jhp;
	public static BulletConfig bmg50_ap;
	public static BulletConfig bmg50_du;
	public static BulletConfig bmg50_he;
	public static BulletConfig bmg50_sm;
	public static BulletConfig bmg50_black;
	public static BulletConfig bmg50_equestrian;
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_BUILDING = (bullet, mop) -> {
		EntityBuilding silver = new EntityBuilding(bullet.worldObj);
		silver.posX = mop.hitVec.xCoord;
		silver.posY = mop.hitVec.yCoord + 50;
		silver.posZ = mop.hitVec.zCoord;;
		bullet.worldObj.spawnEntityInWorld(silver);
		bullet.setDead();
	};
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_EXPLODE = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3 && mop.entityHit == bullet.getThrower()) return;
		Lego.tinyExplode(bullet, mop, 2F); bullet.setDead();
	};

	public static void init() {
		SpentCasing casing50 = new SpentCasing(CasingType.BOTTLENECK).setColor(SpentCasing.COLOR_CASE_BRASS).setScale(1.5F);
		bmg50_sp = new BulletConfig().setItem(EnumAmmo.BMG50_SP).setCasing(EnumCasingType.LARGE, 12)
				.setCasing(casing50.clone().register("bmg50"));
		bmg50_fmj = new BulletConfig().setItem(EnumAmmo.BMG50_FMJ).setCasing(EnumCasingType.LARGE, 12).setDamage(0.8F).setThresholdNegation(7F).setArmorPiercing(0.1F)
				.setCasing(casing50.clone().register("bmg50fmj"));
		bmg50_jhp = new BulletConfig().setItem(EnumAmmo.BMG50_JHP).setCasing(EnumCasingType.LARGE, 12).setDamage(1.5F).setHeadshot(1.5F).setArmorPiercing(-0.25F)
				.setCasing(casing50.clone().register("bmg50jhp"));
		bmg50_ap = new BulletConfig().setItem(EnumAmmo.BMG50_AP).setCasing(EnumCasingType.LARGE_STEEL, 12).setDoesPenetrate(true).setDamageFalloffByPen(false).setDamage(1.25F).setThresholdNegation(17.5F).setArmorPiercing(0.15F)
				.setCasing(casing50.clone().setColor(SpentCasing.COLOR_CASE_44).register("bmg50ap"));
		bmg50_du = new BulletConfig().setItem(EnumAmmo.BMG50_DU).setCasing(EnumCasingType.LARGE_STEEL, 12).setDoesPenetrate(true).setDamageFalloffByPen(false).setDamage(1.5F).setThresholdNegation(21F).setArmorPiercing(0.25F)
				.setCasing(casing50.clone().setColor(SpentCasing.COLOR_CASE_44).register("bmg50du"));
		bmg50_he = new BulletConfig().setItem(EnumAmmo.BMG50_HE).setCasing(EnumCasingType.LARGE_STEEL, 12).setWear(3F).setDoesPenetrate(true).setDamageFalloffByPen(false).setDamage(1.75F).setOnImpact(LAMBDA_STANDARD_EXPLODE)
				.setCasing(casing50.clone().setColor(SpentCasing.COLOR_CASE_44).register("bmg50he"));
		bmg50_sm = new BulletConfig().setItem(EnumAmmo.BMG50_SM).setCasing(EnumCasingType.LARGE_STEEL, 6).setWear(10F).setDoesPenetrate(true).setDamageFalloffByPen(false).setDamage(2.5F).setThresholdNegation(30F).setArmorPiercing(0.35F)
				.setCasing(casing50.clone().setColor(SpentCasing.COLOR_CASE_44).register("bmg50sm"));
		bmg50_black = new BulletConfig().setItem(EnumAmmoSecret.BMG50_BLACK).setWear(5F).setDoesPenetrate(true).setDamageFalloffByPen(false).setSpectral(true).setDamage(1.5F).setHeadshot(3F).setThresholdNegation(30F).setArmorPiercing(0.35F)
				.setCasing(casing50.clone().setColor(SpentCasing.COLOR_CASE_EQUESTRIAN).register("bmg50black"));
		bmg50_equestrian = new BulletConfig().setItem(EnumAmmoSecret.BMG50_EQUESTRIAN).setDamage(0F).setOnImpact(LAMBDA_BUILDING)
				.setCasing(casing50.clone().setColor(SpentCasing.COLOR_CASE_EQUESTRIAN).register("bmg50equestrian"));
		
		ModItems.gun_amat = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(350).draw(20).inspect(50).crosshair(Crosshair.CIRCLE).scopeTexture(scope).smoke(LAMBDA_SMOKE)
				.rec(new Receiver(0)
						.dmg(30F).delay(25).dry(25).spreadHipfire(0.05F).reload(51).jam(43).sound("hbm:weapon.fire.amat", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 7).addConfigs(bmg50_sp, bmg50_fmj, bmg50_jhp, bmg50_ap, bmg50_du, bmg50_sm, bmg50_he))
						.offset(1, -0.0625 * 1.5, -0.25D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_AMAT))
				.setupStandardConfiguration()
				.anim(LAMBDA_AMAT_ANIMS).orchestra(Orchestras.ORCHESTRA_AMAT)
				).setUnlocalizedName("gun_amat");
		ModItems.gun_amat_subtlety = new ItemGunBaseNT(WeaponQuality.LEGENDARY, new GunConfig()
				.dura(1_000).draw(20).inspect(50).crosshair(Crosshair.CIRCLE).scopeTexture(scope).smoke(LAMBDA_SMOKE)
				.rec(new Receiver(0)
						.dmg(50F).delay(25).dry(25).spreadHipfire(0.05F).reload(51).jam(43).sound("hbm:weapon.fire.amat", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 7).addConfigs(bmg50_equestrian, bmg50_sp, bmg50_fmj, bmg50_jhp, bmg50_ap, bmg50_du, bmg50_sm, bmg50_he))
						.offset(1, -0.0625 * 1.5, -0.25D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_AMAT))
				.setupStandardConfiguration()
				.anim(LAMBDA_AMAT_ANIMS).orchestra(Orchestras.ORCHESTRA_AMAT)
				).setUnlocalizedName("gun_amat_subtlety");
		ModItems.gun_amat_penance = new ItemGunBaseNT(WeaponQuality.LEGENDARY, new GunConfig()
				.dura(5_000).draw(20).inspect(50).crosshair(Crosshair.CIRCLE).scopeTexture(scope_thermal).thermalSights(true).smoke(LAMBDA_SMOKE)
				.rec(new Receiver(0)
						.dmg(45F).delay(25).dry(25).spreadHipfire(0F).reload(51).jam(43).sound("hbm:weapon.silencerShoot", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 7).addConfigs(bmg50_sp, bmg50_fmj, bmg50_jhp, bmg50_ap, bmg50_du, bmg50_sm, bmg50_he, bmg50_black))
						.offset(1, -0.0625 * 1.5, -0.25D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_AMAT))
				.setupStandardConfiguration()
				.anim(LAMBDA_AMAT_ANIMS).orchestra(Orchestras.ORCHESTRA_AMAT)
				).setUnlocalizedName("gun_amat_penance");
		
		ModItems.gun_m2 = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(3_000).draw(10).inspect(31).crosshair(Crosshair.L_CIRCLE).smoke(LAMBDA_SMOKE)
				.rec(new Receiver(0)
						.dmg(7.5F).delay(2).dry(10).auto(true).spread(0.005F).sound("hbm:turret.chekhov_fire", 1.0F, 1.0F)
						.mag(new MagazineBelt().addConfigs(bmg50_sp, bmg50_fmj, bmg50_jhp, bmg50_ap, bmg50_du, bmg50_he))
						.offset(1, -0.0625 * 2.5, -0.25D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_M2))
				.setupStandardConfiguration()
				.anim(LAMBDA_M2_ANIMS).orchestra(Orchestras.ORCHESTRA_M2)
				).setUnlocalizedName("gun_m2");
	}
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_SMOKE = (stack, ctx) -> {
		Lego.handleStandardSmoke(ctx.entity, stack, 2000, 0.05D, 1.1D, 0);
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_AMAT = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil(12.5F, (float) (ctx.getPlayer().getRNG().nextGaussian() * 1));
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_M2 = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil((float) (ctx.getPlayer().getRNG().nextGaussian() * 0.5), (float) (ctx.getPlayer().getRNG().nextGaussian() * 0.5));
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_AMAT_ANIMS = (stack, type) -> {
		double turn = -60;
		double pullAmount = -2.5;
		double side = 4;
		double down = -2;
		double detach = 0.5;
		double apex = 7;
		
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(45, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_FULL))
				.addBus("BIPOD", new BusAnimationSequence().hold(500).addPos(80, 0, 0, 350).addPos(80, 25, 0, 150));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, -0.5, 50, IType.SIN_DOWN).addPos(0, 0, 0, 100, IType.SIN_FULL))
				.addBus("BOLT_TURN", new BusAnimationSequence().hold(250).addPos(0, 0, turn, 150).hold(700).addPos(0, 0, 0, 150))
				.addBus("BOLT_PULL", new BusAnimationSequence().hold(350).addPos(0, 0, pullAmount, 250, IType.SIN_UP).hold(250).addPos(0, 0, 0, 200, IType.LINEAR))
				.addBus("LIFT", new BusAnimationSequence().hold(600).addPos(-3, 0, 0, 150, IType.SIN_DOWN).hold(300).addPos(0, 0, 0, 250, IType.SIN_FULL));
		case CYCLE_DRY: return new BusAnimation()
				.addBus("BOLT_TURN", new BusAnimationSequence().hold(250).addPos(0, 0, turn, 150).hold(700).addPos(0, 0, 0, 150))
				.addBus("BOLT_PULL", new BusAnimationSequence().hold(350).addPos(0, 0, pullAmount, 250, IType.SIN_UP).hold(250).addPos(0, 0, 0, 200, IType.LINEAR))
				.addBus("LIFT", new BusAnimationSequence().hold(600).addPos(-3, 0, 0, 150, IType.SIN_DOWN).hold(300).addPos(0, 0, 0, 250, IType.SIN_FULL));
		case RELOAD: return new BusAnimation()
				.addBus("MAG", new BusAnimationSequence().addPos(0, -10, 0, 350, IType.SIN_UP).addPos(0, 0, 0, 650, IType.SIN_UP))
				.addBus("LIFT", new BusAnimationSequence().hold(1000).addPos(-2, 0, 0, 150, IType.SIN_DOWN).addPos(0, 0, 0, 250, IType.SIN_FULL).hold(450).addPos(-3, 0, 0, 150, IType.SIN_DOWN).hold(300).addPos(0, 0, 0, 250, IType.SIN_FULL))
				.addBus("BOLT_TURN", new BusAnimationSequence().hold(1500).addPos(0, 0, turn, 150).hold(700).addPos(0, 0, 0, 150))
				.addBus("BOLT_PULL", new BusAnimationSequence().hold(1600).addPos(0, 0, pullAmount, 250, IType.SIN_UP).hold(250).addPos(0, 0, 0, 200, IType.LINEAR));
		case JAMMED: return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().hold(250).addPos(-15, 0, 0, 500, IType.SIN_FULL).holdUntil(1650).addPos(0, 0, 0, 500, IType.SIN_FULL))
				.addBus("BOLT_TURN", new BusAnimationSequence().hold(250).addPos(0, 0, turn, 150).holdUntil(1250).addPos(0, 0, 0, 150))
				.addBus("BOLT_PULL", new BusAnimationSequence().hold(350).addPos(0, 0, pullAmount, 250, IType.SIN_UP).addPos(0, 0, 0, 200, IType.LINEAR).addPos(0, 0, pullAmount, 250, IType.SIN_UP).addPos(0, 0, 0, 200, IType.LINEAR));
		case INSPECT: return new BusAnimation()
				.addBus("SCOPE_THROW", new BusAnimationSequence().addPos(0, detach, 0, 100, IType.SIN_FULL).addPos(side, down, 0, 500, IType.SIN_FULL).addPos(side, down - 0.5, 0, 100).addPos(side, apex, 0, 350, IType.SIN_FULL).addPos(side, down - 0.5, 0, 350, IType.SIN_DOWN).addPos(side, down, 0, 100).hold(250).addPos(0, detach, 0, 500, IType.SIN_FULL).addPos(0, 0, 0, 250, IType.SIN_FULL))
				.addBus("SCOPE_SPIN", new BusAnimationSequence().hold(700).addPos(-360, 0, 0, 700));
		}
		
		return null;
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_M2_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(80, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_FULL));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, -0.25, 25).addPos(0, 0, 0, 75));
		}
		
		return null;
	};
}
