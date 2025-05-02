package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.hbm.entity.projectile.EntityBoxcar;
import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.entity.projectile.EntityTorpedo;
import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumCasingType;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.GunState;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmoSecret;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.items.weapon.sedna.mags.MagazineSingleReload;
import com.hbm.items.weapon.sedna.mods.WeaponModManager;
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

public class XFactory44 {

	public static final ResourceLocation scope_lilmac = new ResourceLocation(RefStrings.MODID, "textures/misc/scope_44.png");
	
	public static BulletConfig m44_bp;
	public static BulletConfig m44_sp;
	public static BulletConfig m44_fmj;
	public static BulletConfig m44_jhp;
	public static BulletConfig m44_ap;
	public static BulletConfig m44_express;
	public static BulletConfig m44_equestrian_pip;
	public static BulletConfig m44_equestrian_mn7;
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_BOXCAR = (bullet, mop) -> {
		EntityBoxcar pippo = new EntityBoxcar(bullet.worldObj);
		pippo.posX = mop.hitVec.xCoord;
		pippo.posY = mop.hitVec.yCoord + 50;
		pippo.posZ = mop.hitVec.zCoord;;
		bullet.worldObj.spawnEntityInWorld(pippo);
		bullet.worldObj.playSoundEffect(pippo.posX, pippo.posY + 50, pippo.posZ, "hbm:alarm.trainHorn", 100F, 1F);
		bullet.setDead();
	};
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_TORPEDO = (bullet, mop) -> {
		EntityTorpedo pippo = new EntityTorpedo(bullet.worldObj);
		pippo.posX = mop.hitVec.xCoord;
		pippo.posY = mop.hitVec.yCoord + 50;
		pippo.posZ = mop.hitVec.zCoord;;
		bullet.worldObj.spawnEntityInWorld(pippo);
		//bullet.worldObj.playSoundEffect(pippo.posX, pippo.posY + 50, pippo.posZ, "hbm:alarm.trainHorn", 100F, 1F);
		bullet.setDead();
	};

	public static void init() {
		SpentCasing casing44 = new SpentCasing(CasingType.STRAIGHT).setColor(SpentCasing.COLOR_CASE_BRASS).setupSmoke(1F, 0.5D, 60, 20);
		m44_bp = new BulletConfig().setItem(EnumAmmo.M44_BP).setCasing(EnumCasingType.SMALL, 12).setDamage(0.75F).setBlackPowder(true)
				.setCasing(casing44.clone().register("m44bp"));
		m44_sp = new BulletConfig().setItem(EnumAmmo.M44_SP).setCasing(EnumCasingType.SMALL, 6)
				.setCasing(casing44.clone().register("m44"));
		m44_fmj = new BulletConfig().setItem(EnumAmmo.M44_FMJ).setCasing(EnumCasingType.SMALL, 6).setDamage(0.8F).setThresholdNegation(3F).setArmorPiercing(0.1F)
				.setCasing(casing44.clone().register("m44fmj"));
		m44_jhp = new BulletConfig().setItem(EnumAmmo.M44_JHP).setCasing(EnumCasingType.SMALL, 6).setDamage(1.5F).setHeadshot(1.5F).setArmorPiercing(-0.25F)
				.setCasing(casing44.clone().register("m44jhp"));
		m44_ap = new BulletConfig().setItem(EnumAmmo.M44_AP).setCasing(EnumCasingType.SMALL_STEEL, 6).setDoesPenetrate(true).setDamageFalloffByPen(false).setDamage(1.25F).setThresholdNegation(7.5F).setArmorPiercing(0.15F)
				.setCasing(casing44.clone().setColor(SpentCasing.COLOR_CASE_44).register("m44ap"));
		m44_express = new BulletConfig().setItem(EnumAmmo.M44_EXPRESS).setCasing(EnumCasingType.SMALL, 6).setDoesPenetrate(true).setDamage(1.5F).setThresholdNegation(3F).setArmorPiercing(0.1F).setWear(1.5F)
				.setCasing(casing44.clone().register("m44express"));
		m44_equestrian_pip = new BulletConfig().setItem(EnumAmmoSecret.M44_EQUESTRIAN).setDamage(0F).setOnImpact(LAMBDA_BOXCAR)
				.setCasing(casing44.clone().setColor(SpentCasing.COLOR_CASE_EQUESTRIAN).register("m44equestrianPip"));
		m44_equestrian_mn7 = new BulletConfig().setItem(EnumAmmoSecret.M44_EQUESTRIAN).setDamage(0F).setOnImpact(LAMBDA_TORPEDO)
				.setCasing(casing44.clone().setColor(SpentCasing.COLOR_CASE_EQUESTRIAN).register("m44equestrianMn7"));

		ModItems.gun_henry = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(300).draw(15).inspect(23).reloadSequential(true).crosshair(Crosshair.CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(10F).delay(20).reload(25, 11, 14, 8).jam(45).sound("hbm:weapon.fire.rifle", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 14).addConfigs(m44_bp, m44_sp, m44_fmj, m44_jhp, m44_ap, m44_express))
						.offset(0.75, -0.0625, -0.1875D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_HENRY))
				.setupStandardConfiguration()
				.anim(LAMBDA_HENRY_ANIMS).orchestra(Orchestras.ORCHESTRA_HENRY)
				).setUnlocalizedName("gun_henry");
		ModItems.gun_henry_lincoln = new ItemGunBaseNT(WeaponQuality.B_SIDE, new GunConfig()
				.dura(300).draw(15).inspect(23).reloadSequential(true).crosshair(Crosshair.CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(20F).spreadHipfire(0F).delay(20).reload(25, 11, 14, 8).jam(45).sound("hbm:weapon.fire.rifle", 1.0F, 1.25F)
						.mag(new MagazineSingleReload(0, 14).addConfigs(m44_bp, m44_sp, m44_fmj, m44_jhp, m44_ap, m44_express))
						.offset(0.75, -0.0625, -0.1875D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_HENRY))
				.setupStandardConfiguration()
				.anim(LAMBDA_HENRY_ANIMS).orchestra(Orchestras.ORCHESTRA_HENRY)
				).setUnlocalizedName("gun_henry_lincoln");

		ModItems.gun_heavy_revolver = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(600).draw(10).inspect(23).crosshair(Crosshair.L_CLASSIC).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(15F).delay(14).reload(46).jam(23).sound("hbm:weapon.44Shoot", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 6).addConfigs(m44_bp, m44_sp, m44_fmj, m44_jhp, m44_ap, m44_express))
						.offset(0.75, -0.0625, -0.3125D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_NOPIP))
				.setupStandardConfiguration()
				.anim(LAMBDA_NOPIP_ANIMS).orchestra(Orchestras.ORCHESTRA_NOPIP)
				).setNameMutator(LAMBDA_NAME_NOPIP)
				.setUnlocalizedName("gun_heavy_revolver");
		ModItems.gun_heavy_revolver_lilmac = new ItemGunBaseNT(WeaponQuality.LEGENDARY, new GunConfig()
				.dura(31_000).draw(10).inspect(23).crosshair(Crosshair.L_CLASSIC).scopeTexture(scope_lilmac).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(30F).delay(14).reload(46).jam(23).sound("hbm:weapon.44Shoot", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 6).addConfigs(m44_equestrian_pip, m44_bp, m44_sp, m44_fmj, m44_jhp, m44_ap, m44_express))
						.offset(0.75, -0.0625, -0.3125D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_NOPIP))
				.setupStandardConfiguration()
				.anim(LAMBDA_LILMAC_ANIMS).orchestra(Orchestras.ORCHESTRA_NOPIP)
				).setUnlocalizedName("gun_heavy_revolver_lilmac");
		ModItems.gun_heavy_revolver_protege = new ItemGunBaseNT(WeaponQuality.LEGENDARY, new GunConfig()
				.dura(31_000).draw(10).inspect(23).crosshair(Crosshair.L_CLASSIC).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(30F).delay(14).reload(46).jam(23).sound("hbm:weapon.44Shoot", 1.0F, 0.8F)
						.mag(new MagazineFullReload(0, 6).addConfigs(m44_equestrian_mn7, m44_bp, m44_sp, m44_fmj, m44_jhp, m44_ap, m44_express))
						.offset(0.75, -0.0625, -0.3125D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_NOPIP))
				.setupStandardConfiguration()
				.anim(LAMBDA_LILMAC_ANIMS).orchestra(Orchestras.ORCHESTRA_NOPIP)
				).setUnlocalizedName("gun_heavy_revolver_protege");

		ModItems.gun_hangman = new ItemGunBaseNT(WeaponQuality.LEGENDARY, new GunConfig()
				.dura(600).draw(10).inspect(31).inspectCancel(false).crosshair(Crosshair.CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(25F).delay(10).reload(46).jam(23).sound("hbm:weapon.44Shoot", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 8).addConfigs(m44_bp, m44_sp, m44_fmj, m44_jhp, m44_ap, m44_express))
						.offset(1, -0.0625 * 2.5, -0.25D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_HANGMAN))
				.setupStandardConfiguration().ps(SMACK_A_FUCKER)
				.anim(LAMBDA_HANGMAN_ANIMS).orchestra(Orchestras.ORCHESTRA_HANGMAN)
				).setUnlocalizedName("gun_hangman");
	}
	
	public static Function<ItemStack, String> LAMBDA_NAME_NOPIP = (stack) -> {
		if(WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_SCOPE)) return stack.getUnlocalizedName() + "_scoped";
		return null;
	};
	
	public static BiConsumer<ItemStack, LambdaContext> SMACK_A_FUCKER = (stack, ctx) -> {
		if(ItemGunBaseNT.getState(stack, ctx.configIndex) == GunState.IDLE || ItemGunBaseNT.getLastAnim(stack, ctx.configIndex) == AnimType.CYCLE) {
			ItemGunBaseNT.setIsAiming(stack, false);
			ItemGunBaseNT.setState(stack, ctx.configIndex, GunState.DRAWING);
			ItemGunBaseNT.setTimer(stack, ctx.configIndex, ctx.config.getInspectDuration(stack));
			ItemGunBaseNT.playAnimation(ctx.getPlayer(), stack, AnimType.INSPECT, ctx.configIndex);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_HENRY = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil(5, (float) (ctx.getPlayer().getRNG().nextGaussian() * 1));
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_NOPIP = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil(10, (float) (ctx.getPlayer().getRNG().nextGaussian() * 1.5));
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_HANGMAN = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil(5, (float) (ctx.getPlayer().getRNG().nextGaussian() * 1));
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_HENRY_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(-90, 0, 0, 0).addPos(0, 0, -3, 350, IType.SIN_DOWN))
				.addBus("SIGHT", new BusAnimationSequence().addPos(80, 0, 0, 0).addPos(80, 0, 0, 500).addPos(0, 0, -3, 250, IType.SIN_DOWN));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, 0, 50).addPos(0, 0, -1, 50).addPos(0, 0, 0, 250))
				.addBus("SIGHT", new BusAnimationSequence().addPos(35, 0, 0, 100, IType.SIN_DOWN).addPos(0, 0, 0, 100, IType.SIN_FULL))
				.addBus("LEVER", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(-90, 0, 0, 200).addPos(0, 0, 0, 200))
				.addBus("TURN", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(0, 0, 45, 200, IType.SIN_DOWN).addPos(0, 0, 0, 200, IType.SIN_UP))
				.addBus("HAMMER", new BusAnimationSequence().addPos(30, 0, 0, 50).addPos(30, 0, 0, 550).addPos(0, 0, 0, 200));
		case CYCLE_DRY: return new BusAnimation()
				.addBus("LEVER", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(-90, 0, 0, 200).addPos(0, 0, 0, 200))
				.addBus("TURN", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(0, 0, 45, 200, IType.SIN_DOWN).addPos(0, 0, 0, 200, IType.SIN_UP))
				.addBus("HAMMER", new BusAnimationSequence().addPos(30, 0, 0, 50).addPos(30, 0, 0, 550).addPos(0, 0, 0, 200));
		case RELOAD: return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(-60, 0, 0, 400, IType.SIN_FULL))
				.addBus("TWIST", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, -90, 200, IType.SIN_FULL))
				.addBus("BULLET", new BusAnimationSequence().addPos(0, 0, 0, 700).addPos(3, 0, -6, 0).addPos(0, 0, 1, 300, IType.SIN_FULL).addPos(0, 0, 0, 250, IType.SIN_FULL));
		case RELOAD_CYCLE: return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(-60, 0, 0, 0))
				.addBus("TWIST", new BusAnimationSequence().addPos(0, 0, -90, 0))
				.addBus("BULLET", new BusAnimationSequence().addPos(3, 0, -6, 0).addPos(0, 0, 1, 300, IType.SIN_FULL).addPos(0, 0, 0, 250, IType.SIN_FULL));
		case RELOAD_END:
			boolean empty = ((ItemGunBaseNT) stack.getItem()).getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmountBeforeReload(stack) <= 0;
			return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(-60, 0, 0, 0).addPos(-60, 0, 0, 300).addPos(0, 0, 0, 400, IType.SIN_FULL))
				.addBus("TWIST", new BusAnimationSequence().addPos(0, 0, -90, 0).addPos(0, 0, 0, 200, IType.SIN_FULL))
				.addBus("LEVER", new BusAnimationSequence().addPos(0, 0, 0, 700).addPos(empty ? -90 : 0, 0, 0, 200).addPos(0, 0, 0, 200))
				.addBus("TURN", new BusAnimationSequence().addPos(0, 0, 0, 700).addPos(0, 0, empty ? 45 : 0, 200, IType.SIN_DOWN).addPos(0, 0, 0, 200, IType.SIN_UP));
		case JAMMED: return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(-60, 0, 0, 0).addPos(-60, 0, 0, 300).addPos(0, 0, 0, 400, IType.SIN_FULL))
				.addBus("TWIST", new BusAnimationSequence().addPos(0, 0, -90, 0).addPos(0, 0, 0, 200, IType.SIN_FULL))
				.addBus("LEVER", new BusAnimationSequence().addPos(0, 0, 0, 700).addPos(-90, 0, 0, 200).addPos(0, 0, 0, 200).addPos(0, 0, 0, 500).addPos(-90, 0, 0, 200).addPos(0, 0, 0, 200).addPos(0, 0, 0, 200).addPos(-90, 0, 0, 200).addPos(0, 0, 0, 200))
				.addBus("TURN", new BusAnimationSequence().addPos(0, 0, 0, 700).addPos(0, 0, 45, 200, IType.SIN_DOWN).addPos(0, 0, 0, 200, IType.SIN_UP).addPos(0, 0, 0, 500).addPos(0, 0, 45, 200, IType.SIN_FULL).addPos(0, 0, 45, 600).addPos(0, 0, 0, 200, IType.SIN_FULL));
		case INSPECT: return new BusAnimation()
				.addBus("YEET", new BusAnimationSequence().addPos(0, 2, 0, 200, IType.SIN_DOWN).addPos(0, 0, 0, 200, IType.SIN_UP))
				.addBus("ROLL", new BusAnimationSequence().addPos(0, 0, 360, 400));
		}
		
		return null;
	};
	
	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_NOPIP_ANIMS = (stack, type) -> {
		switch(type) {
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, 0, 50).addPos(0, 0, -3, 50).addPos(0, 0, 0, 250))
				.addBus("HAMMER", new BusAnimationSequence().addPos(0, 0, 1, 50).addPos(0, 0, 1, 400).addPos(0, 0, 0, 200))
				.addBus("DRUM", new BusAnimationSequence().addPos(0, 0, 0, 450).addPos(0, 0, 1, 200));
		case CYCLE_DRY: return new BusAnimation()
				.addBus("HAMMER", new BusAnimationSequence().addPos(0, 0, 1, 50).addPos(0, 0, 1, 300 + 100).addPos(0, 0, 0, 200))
				.addBus("DRUM", new BusAnimationSequence().addPos(0, 0, 0, 450).addPos(0, 0, 1, 200));
		case EQUIP: return new BusAnimation().addBus("ROTATE", new BusAnimationSequence().addPos(90, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_DOWN));
		case RELOAD: return new BusAnimation()
					.addBus("RELAOD_TILT", new BusAnimationSequence().addPos(-15, 0, 0, 100).addPos(65, 0, 0, 100).addPos(45, 0, 0, 50).addPos(0, 0, 0, 200).addPos(0, 0, 0, 1450).addPos(-80, 0, 0, 100).addPos(-80, 0, 0, 100).addPos(0, 0, 0, 200))
					.addBus("RELOAD_CYLINDER", new BusAnimationSequence().addPos(0, 0, 0, 200).addPos(90, 0, 0, 100).addPos(90, 0, 0, 1700).addPos(0, 0, 0, 70))
					.addBus("RELOAD_LIFT", new BusAnimationSequence().addPos(0, 0, 0, 350).addPos(-45, 0, 0, 250).addPos(-45, 0, 0, 350).addPos(-15, 0, 0, 200).addPos(-15, 0, 0, 1050).addPos(0, 0, 0, 100))
					.addBus("RELOAD_JOLT", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(2, 0, 0, 50).addPos(0, 0, 0, 100))
					.addBus("RELOAD_BULLETS", new BusAnimationSequence().addPos(0, 0, 0, 650).addPos(10, 0, 0, 300).addPos(10, 0, 0, 200).addPos(0, 0, 0, 700))
					.addBus("RELOAD_BULLETS_CON", new BusAnimationSequence().addPos(1, 0, 0, 0).addPos(1, 0, 0, 950).addPos(0, 0, 0, 1 ) );
		case INSPECT:
		case JAMMED: return new BusAnimation()
					.addBus("RELAOD_TILT", new BusAnimationSequence().addPos(-15, 0, 0, 100).addPos(65, 0, 0, 100).addPos(45, 0, 0, 50).addPos(0, 0, 0, 200).addPos(0, 0, 0, 200).addPos(-80, 0, 0, 100).addPos(-80, 0, 0, 100).addPos(0, 0, 0, 200))
					.addBus("RELOAD_CYLINDER", new BusAnimationSequence().addPos(0, 0, 0, 200).addPos(90, 0, 0, 100).addPos(90, 0, 0, 450).addPos(0, 0, 0, 70));
		}
		
		return null;
	};
	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_LILMAC_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation().addBus("SPIN", new BusAnimationSequence().addPos(-360, 0, 0, 350));
		}
		
		return LAMBDA_NOPIP_ANIMS.apply(stack, type);
	};
	
	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_HANGMAN_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation().addBus("EQUIP", new BusAnimationSequence().addPos(60, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_DOWN));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, 0, 50).addPos(0, 0, -3, 50).addPos(0, 0, 0, 250));
		case RELOAD: return new BusAnimation()
				.addBus("LID", new BusAnimationSequence().addPos(0, 0, -90, 250).addPos(0, 0, -90, 1500).addPos(0, 0, 0, 250))
				.addBus("MAG", new BusAnimationSequence().addPos(0, 0, 0, 250).addPos(0, -10, 0, 250, IType.SIN_UP).addPos(0, -10, 0, 500).addPos(0, 0, 0, 350, IType.SIN_FULL))
				.addBus("BULLETS", new BusAnimationSequence().addPos(1, 1, 1, 0).addPos(0, 0, 0, 500))
				.addBus("EQUIP", new BusAnimationSequence().addPos(-15, 0, 0, 500, IType.SIN_FULL).addPos(-15, 0, 0, 850).addPos(-25, 0, 0, 100, IType.SIN_DOWN).addPos(0, 0, 0, 350, IType.SIN_FULL))
				.addBus("ROLL", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, 25, 250, IType.SIN_FULL).addPos(0, 0, 25, 1000).addPos(0, 0, 0, 250, IType.SIN_FULL));
		case INSPECT: return new BusAnimation()
				.addBus("TURN", new BusAnimationSequence().addPos(0, 170, 0, 500, IType.SIN_UP).addPos(0, 170, 0, 550).addPos(0, 0, 0, 500, IType.SIN_FULL))
				.addBus("ROLL", new BusAnimationSequence().addPos(0, 0, 110, 500, IType.SIN_FULL).addPos(0, 0, 110, 550).addPos(0, 0, 0, 500, IType.SIN_FULL))
				.addBus("SMACK", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, 1, 150, IType.SIN_DOWN).addPos(0, 0, -3, 150, IType.SIN_UP).addPos(0, 0, 0, 350, IType.SIN_FULL));
		case JAMMED: return new BusAnimation()
				.addBus("LID", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, -90, 250).addPos(0, 0, -90, 300).addPos(0, 0, 0, 250))
				.addBus("MAG", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, 0, 250).addPos(0, -3, 0, 150, IType.SIN_UP).addPos(0, 0, 0, 150, IType.SIN_FULL))
				.addBus("EQUIP", new BusAnimationSequence().addPos(0, 0, 0, 1000).addPos(-10, 0, 0, 100, IType.SIN_DOWN).addPos(0, 0, 0, 350, IType.SIN_FULL))
				.addBus("ROLL", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, 25, 250, IType.SIN_FULL).addPos(0, 0, 25, 300).addPos(0, 0, 0, 250, IType.SIN_FULL));
		}
		
		return null;
	};
}
