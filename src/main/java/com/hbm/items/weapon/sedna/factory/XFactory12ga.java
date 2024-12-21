package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.entity.projectile.EntityDuchessGambit;
import com.hbm.extprop.HbmLivingProps;
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
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmoSecret;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.items.weapon.sedna.mags.MagazineSingleReload;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

public class XFactory12ga {

	public static BulletConfig g12_bp;
	public static BulletConfig g12_bp_magnum;
	public static BulletConfig g12_bp_slug;
	public static BulletConfig g12;
	public static BulletConfig g12_slug;
	public static BulletConfig g12_flechette;
	public static BulletConfig g12_magnum;
	public static BulletConfig g12_explosive;
	public static BulletConfig g12_phosphorus;
	public static BulletConfig g12_anthrax;
	public static BulletConfig g12_equestrian_bj;
	public static BulletConfig g12_equestrian_tkr;
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_EXPLODE = (bullet, mop) -> {
		Lego.standardExplode(bullet, mop, 2F); bullet.setDead();
	};
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_BOAT = (bullet, mop) -> {
		EntityDuchessGambit pippo = new EntityDuchessGambit(bullet.worldObj);
		pippo.posX = mop.hitVec.xCoord;
		pippo.posY = mop.hitVec.yCoord + 50;
		pippo.posZ = mop.hitVec.zCoord;;
		bullet.worldObj.spawnEntityInWorld(pippo);
		bullet.worldObj.playSoundEffect(pippo.posX, pippo.posY + 50, pippo.posZ, "hbm:weapon.boat", 100F, 1F);
		bullet.setDead();
	};
	
	public static void init() {
		
		g12_bp = new BulletConfig().setItem(EnumAmmo.G12_BP).setBlackPowder(true).setProjectiles(8).setDamage(0.5F/8F).setSpread(0.05F).setRicochetAngle(15).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(SpentCasing.COLOR_CASE_BRASS, SpentCasing.COLOR_CASE_BRASS).setScale(0.75F).register("12GA_BP"));
		g12_bp_magnum = new BulletConfig().setItem(EnumAmmo.G12_BP_MAGNUM).setBlackPowder(true).setProjectiles(4).setDamage(0.5F/4F).setSpread(0.05F).setRicochetAngle(25).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(SpentCasing.COLOR_CASE_BRASS, SpentCasing.COLOR_CASE_BRASS).setScale(0.75F).register("12GA_BP_MAGNUM"));
		g12_bp_slug = new BulletConfig().setItem(EnumAmmo.G12_BP_SLUG).setBlackPowder(true).setDamage(0.5F).setSpread(0.01F).setRicochetAngle(5).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(SpentCasing.COLOR_CASE_BRASS, SpentCasing.COLOR_CASE_BRASS).setScale(0.75F).register("12GA_BP_SLUG"));
		g12 = new BulletConfig().setItem(EnumAmmo.G12).setProjectiles(8).setDamage(1F/8F).setSpread(0.05F).setRicochetAngle(15).setThresholdNegation(2F).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0xB52B2B, SpentCasing.COLOR_CASE_BRASS).setScale(0.75F).register("12GA"));
		g12_slug = new BulletConfig().setItem(EnumAmmo.G12_SLUG).setSpread(0.0F).setRicochetAngle(25).setThresholdNegation(4F).setArmorPiercing(0.15F).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0x393939, SpentCasing.COLOR_CASE_BRASS).setScale(0.75F).register("12GA_SLUG"));
		g12_flechette = new BulletConfig().setItem(EnumAmmo.G12_FLECHETTE).setProjectiles(8).setDamage(1F/8F).setThresholdNegation(5F).setThresholdNegation(3F).setArmorPiercing(0.2F).setSpread(0.025F).setRicochetAngle(5).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0x3C80F0, SpentCasing.COLOR_CASE_BRASS).setScale(0.75F).register("12GA_FLECHETTE"));
		g12_magnum = new BulletConfig().setItem(EnumAmmo.G12_MAGNUM).setProjectiles(4).setDamage(2F/4F).setSpread(0.015F).setRicochetAngle(15).setThresholdNegation(4F).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0x278400, SpentCasing.COLOR_CASE_12GA).setScale(0.75F).register("12GA_MAGNUM"));
		g12_explosive = new BulletConfig().setItem(EnumAmmo.G12_EXPLOSIVE).setDamage(2.5F).setOnImpact(LAMBDA_STANDARD_EXPLODE).setSpread(0F).setRicochetAngle(15).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0xDA4127, SpentCasing.COLOR_CASE_12GA).setScale(0.75F).register("12GA_EXPLOSIVE"));
		g12_phosphorus = new BulletConfig().setItem(EnumAmmo.G12_PHOSPHORUS).setProjectiles(8).setDamage(1F/8F).setSpread(0.015F).setRicochetAngle(15).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0x910001, SpentCasing.COLOR_CASE_12GA).setScale(0.75F).register("12GA_PHOSPHORUS"))
				.setOnImpact((bullet, mop) -> { if(mop.entityHit != null && mop.entityHit instanceof EntityLivingBase) { HbmLivingProps data = HbmLivingProps.getData((EntityLivingBase) mop.entityHit); if(data.phosphorus < 300) data.phosphorus = 300; } });
		//g12_anthrax = new BulletConfig().setItem(EnumAmmo.G12_ANTHRAX).setProjectiles(8).setDamage(1F/8F).setSpread(0.015F).setRicochetAngle(15).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0x749300, SpentCasing.COLOR_CASE_12GA).setScale(0.75F).register("12GA_ANTHRAX"));
		g12_equestrian_bj = new BulletConfig().setItem(EnumAmmoSecret.G12_EQUESTRIAN).setDamage(0F).setOnImpact(LAMBDA_BOAT).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(SpentCasing.COLOR_CASE_EQUESTRIAN, SpentCasing.COLOR_CASE_12GA).setScale(0.75F).register("12gaEquestrianBJ"));
		g12_equestrian_tkr = new BulletConfig().setItem(EnumAmmoSecret.G12_EQUESTRIAN).setDamage(0F).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(SpentCasing.COLOR_CASE_EQUESTRIAN, SpentCasing.COLOR_CASE_12GA).setScale(0.75F).register("12gaEquestrianTKR"));

		BulletConfig[] all = new BulletConfig[] {g12_bp, g12_bp_magnum, g12_bp_slug, g12, g12_slug, g12_flechette, g12_magnum, g12_explosive, g12_phosphorus};
		
		ModItems.gun_maresleg = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(600).draw(10).inspect(39).reloadSequential(true).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(16F).delay(20).reload(22, 10, 13, 0).jam(24).sound("hbm:weapon.fire.shotgun", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 6).addConfigs(all))
						.offset(0.75, -0.0625, -0.1875)
						.setupStandardFire().recoil(LAMBDA_RECOIL_MARESLEG))
				.setupStandardConfiguration()
				.anim(LAMBDA_MARESLEG_ANIMS).orchestra(Orchestras.ORCHESTRA_MARESLEG)
				).setUnlocalizedName("gun_maresleg");
		ModItems.gun_maresleg_akimbo = new ItemGunBaseNT(WeaponQuality.B_SIDE,
				new GunConfig().dura(600).draw(5).inspect(39).reloadSequential(true).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(16F).delay(20).reload(22, 10, 13, 0).jam(24).sound("hbm:weapon.fire.shotgun", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 6).addConfigs(all))
						.offset(0.75, -0.0625, 0.1875D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_MARESLEG))
				.pp(Lego.LAMBDA_STANDARD_CLICK_PRIMARY).pr(Lego.LAMBDA_STANDARD_RELOAD)
				.decider(GunStateDecider.LAMBDA_STANDARD_DECIDER)
				.anim(LAMBDA_MARESLEG_SHORT_ANIMS).orchestra(Orchestras.ORCHESTRA_MARESLEG_AKIMBO),
				new GunConfig().dura(600).draw(5).inspect(39).reloadSequential(true).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(12F).delay(20).reload(22, 10, 13, 0).jam(24).sound("hbm:weapon.fire.shotgun", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(1, 6).addConfigs(all))
						.offset(0.75, -0.0625, -0.1875)
						.setupStandardFire().recoil(LAMBDA_RECOIL_MARESLEG))
				.ps(Lego.LAMBDA_STANDARD_CLICK_PRIMARY).pr(Lego.LAMBDA_STANDARD_RELOAD)
				.decider(GunStateDecider.LAMBDA_STANDARD_DECIDER)
				.anim(LAMBDA_MARESLEG_SHORT_ANIMS).orchestra(Orchestras.ORCHESTRA_MARESLEG_AKIMBO)
				).setUnlocalizedName("gun_maresleg_akimbo");
		ModItems.gun_maresleg_broken = new ItemGunBaseNT(WeaponQuality.LEGENDARY, new GunConfig()
				.dura(0).draw(5).inspect(39).reloadSequential(true).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(32F).delay(20).reload(22, 10, 13, 0).jam(24).sound("hbm:weapon.fire.shotgun", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 6).addConfigs(g12_equestrian_tkr, g12_bp, g12_bp_magnum, g12_bp_slug, g12, g12_slug, g12_flechette, g12_magnum, g12_explosive, g12_phosphorus))
						.offset(0.75, -0.0625, -0.1875)
						.canFire(Lego.LAMBDA_STANDARD_CAN_FIRE).fire(Lego.LAMBDA_NOWEAR_FIRE).recoil(LAMBDA_RECOIL_MARESLEG))
				.setupStandardConfiguration()
				.anim(LAMBDA_MARESLEG_SHORT_ANIMS).orchestra(Orchestras.ORCHESTRA_MARESLEG_SHORT)
				).setUnlocalizedName("gun_maresleg_broken");
		
		ModItems.gun_liberator = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(200).draw(20).inspect(21).reloadSequential(true).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(16F).delay(20).rounds(4).reload(25, 15, 7, 0).jam(45).sound("hbm:weapon.fire.shotgunAlt", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 4).addConfigs(all))
						.offset(0.75, -0.0625, -0.1875)
						.setupStandardFire().recoil(LAMBDA_RECOIL_LIBERATOR))
				.setupStandardConfiguration()
				.anim(LAMBDA_LIBERATOR_ANIMS).orchestra(Orchestras.ORCHESTRA_LIBERATOR)
				).setUnlocalizedName("gun_liberator");

		ModItems.gun_spas12 = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(600).draw(20).inspect(39).reloadSequential(true).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(32F).delay(20).reload(5, 10, 10, 10, 0).jam(36).sound("hbm:weapon.shotgunShoot", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 8).addConfigs(all))
						.offset(0.75, -0.0625, -0.1875)
						.setupStandardFire().recoil(LAMBDA_RECOIL_MARESLEG))
				.setupStandardConfiguration().ps(LAMBDA_SPAS_SECONDARY).pt(null)
				.anim(LAMBDA_SPAS_ANIMS).orchestra(Orchestras.ORCHESTRA_SPAS)
				).setUnlocalizedName("gun_spas12");

		ModItems.gun_autoshotgun = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(2_000).draw(10).inspect(33).reloadSequential(true).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(48F).delay(10).auto(true).autoAfterDry(true).dryfireAfterAuto(true).reload(44).jam(19).sound("hbm:weapon.fire.shotgunAuto", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 20).addConfigs(all))
						.offset(0.75, -0.125, -0.25)
						.setupStandardFire().recoil(LAMBDA_RECOIL_AUTOSHOTGUN))
				.setupStandardConfiguration()
				.anim(LAMBDA_SHREDDER_ANIMS).orchestra(Orchestras.ORCHESTRA_SHREDDER)
				).setUnlocalizedName("gun_autoshotgun");
		ModItems.gun_autoshotgun_sexy = new ItemGunBaseNT(WeaponQuality.LEGENDARY, new GunConfig()
				.dura(5_000).draw(10).inspect(33).reloadSequential(true).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(64F).delay(1).auto(true).dryfireAfterAuto(true).reload(44).jam(19).sound("hbm:weapon.fire.shotgunAuto", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 100).addConfigs(g12_equestrian_bj, g12_bp, g12_bp_magnum, g12_bp_slug, g12, g12_slug, g12_flechette, g12_magnum, g12_explosive, g12_phosphorus))
						.offset(0.75, -0.125, -0.25)
						.setupStandardFire().recoil(LAMBDA_RECOIL_SEXY))
				.setupStandardConfiguration()
				.anim(LAMBDA_SEXY_ANIMS).orchestra(Orchestras.ORCHESTRA_SHREDDER_SEXY)
				).setUnlocalizedName("gun_autoshotgun_sexy");
	}
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_MARESLEG = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil(10, (float) (ctx.getPlayer().getRNG().nextGaussian() * 1.5));
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_LIBERATOR = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil(5, (float) (ctx.getPlayer().getRNG().nextGaussian() * 1.5));
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_AUTOSHOTGUN = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil((float) (ctx.getPlayer().getRNG().nextGaussian() * 1.5) + 1.5F, (float) (ctx.getPlayer().getRNG().nextGaussian() * 0.5));
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_SEXY = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil((float) (ctx.getPlayer().getRNG().nextGaussian() * 0.5), (float) (ctx.getPlayer().getRNG().nextGaussian() * 0.5));
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_SPAS_SECONDARY = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		EntityPlayer player = ctx.getPlayer();
		Receiver rec = ctx.config.getReceivers(stack)[0];
		int index = ctx.configIndex;
		GunState state = ItemGunBaseNT.getState(stack, index);
		if(state == GunState.IDLE) {
			if(rec.getCanFire(stack).apply(stack, ctx)) {
				rec.getOnFire(stack).accept(stack, ctx);
				int remaining = rec.getRoundsPerCycle(stack);
				int timeFired = 1;
				for(int i = 0; i < remaining; i++) {
					if(rec.getCanFire(stack).apply(stack, ctx)) {
						rec.getOnFire(stack).accept(stack, ctx);
						timeFired++;
					}
				}
				if(rec.getFireSound(stack) != null) entity.worldObj.playSoundEffect(entity.posX, entity.posY, entity.posZ, rec.getFireSound(stack), rec.getFireVolume(stack), rec.getFirePitch(stack) * (timeFired > 1 ? 0.9F : 1F));
				ItemGunBaseNT.setState(stack, index, GunState.COOLDOWN);
				ItemGunBaseNT.setTimer(stack, index, 10);
			} else {
				if(rec.getDoesDryFire(stack)) {
					ItemGunBaseNT.playAnimation(player, stack, AnimType.CYCLE_DRY, index);
					ItemGunBaseNT.setState(stack, index, GunState.DRAWING);
					ItemGunBaseNT.setTimer(stack, index, rec.getDelayAfterDryFire(stack));
				}
			}
		}
		if(state == GunState.RELOADING) {
			ItemGunBaseNT.setReloadCancel(stack, true);
		}
	};

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
			boolean empty = ((ItemGunBaseNT) stack.getItem()).getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, MainRegistry.proxy.me().inventory) <= 0;
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

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_MARESLEG_SHORT_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(-60, 0, 0, 0).addPos(0, 0, -3, 250, IType.SIN_DOWN));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, 0, 50).addPos(0, 0, -1, 50).addPos(0, 0, 0, 250))
				.addBus("SIGHT", new BusAnimationSequence().addPos(35, 0, 0, 100, IType.SIN_DOWN).addPos(0, 0, 0, 100, IType.SIN_FULL))
				.addBus("LEVER", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(-85, 0, 0, 200).addPos(0, 0, 0, 200))
				.addBus("HAMMER", new BusAnimationSequence().addPos(30, 0, 0, 50).addPos(30, 0, 0, 550).addPos(0, 0, 0, 200))
				.addBus("FLIP", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(360, 0, 0, 400))
				.addBus("SHELL", new BusAnimationSequence().addPos(-20, 0, 0, 0)); //gets rid of the shell in the barrel during cycling
		case CYCLE_DRY: return new BusAnimation()
				.addBus("LEVER", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(-90, 0, 0, 200).addPos(0, 0, 0, 200))
				.addBus("HAMMER", new BusAnimationSequence().addPos(30, 0, 0, 50).addPos(30, 0, 0, 550).addPos(0, 0, 0, 200))
				.addBus("FLIP", new BusAnimationSequence().addPos(0, 0, 0, 600).addPos(360, 0, 0, 400))
				.addBus("SHELL", new BusAnimationSequence().addPos(-20, 0, 0, 0));
		case JAMMED: return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(30, 0, 0, 0).addPos(30, 0, 0, 250).addPos(0, 0, 0, 400, IType.SIN_FULL))
				.addBus("LEVER", new BusAnimationSequence().addPos(-85, 0, 0, 0).addPos(-15, 0, 0, 200).addPos(-15, 0, 0, 650).addPos(-85, 0, 0, 200).addPos(-15, 0, 0, 200).addPos(-15, 0, 0, 200).addPos(-85, 0, 0, 200).addPos(0, 0, 0, 200))
				.addBus("FLAG", new BusAnimationSequence().addPos(1, 1, 1, 0));
		}
		
		return LAMBDA_MARESLEG_ANIMS.apply(stack, type);
	};

	/** This fucking sucks */
	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_LIBERATOR_ANIMS = (stack, type) -> {
		int ammo = ((ItemGunBaseNT) stack.getItem()).getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, MainRegistry.proxy.me().inventory);
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(60, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_DOWN));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, -2.5, 50, IType.SIN_DOWN).addPos(0, 0, 0, 350, IType.SIN_FULL));
		case CYCLE_DRY: return new BusAnimation();
		case RELOAD: if(ammo == 0) return new BusAnimation()
				.addBus("LATCH", new BusAnimationSequence().addPos(15, 0, 0, 100))
				.addBus("BREAK", new BusAnimationSequence().addPos(0, 0, 0, 100).addPos(60, 0, 0, 350, IType.SIN_DOWN))
				.addBus("SHELL1", new BusAnimationSequence().addPos(2, -4, -2, 0).addPos(2, -4, -2, 400).addPos(0, 0, -2, 450, IType.SIN_FULL).addPos(0, 0, 0, 50, IType.SIN_UP))
				.addBus("SHELL2", new BusAnimationSequence().addPos(2, -4, -2, 0))
				.addBus("SHELL3", new BusAnimationSequence().addPos(2, -4, -2, 0))
				.addBus("SHELL4", new BusAnimationSequence().addPos(2, -4, -2, 0));
			if(ammo == 1) return new BusAnimation()
					.addBus("LATCH", new BusAnimationSequence().addPos(15, 0, 0, 100))
					.addBus("BREAK", new BusAnimationSequence().addPos(0, 0, 0, 100).addPos(60, 0, 0, 350, IType.SIN_DOWN))
					.addBus("SHELL1", new BusAnimationSequence().addPos(0, 0, 0, 0))
					.addBus("SHELL2", new BusAnimationSequence().addPos(2, -4, -2, 0).addPos(2, -4, -2, 400).addPos(0, 0, -2, 450, IType.SIN_FULL).addPos(0, 0, 0, 50, IType.SIN_UP))
					.addBus("SHELL3", new BusAnimationSequence().addPos(2, -4, -2, 0))
					.addBus("SHELL4", new BusAnimationSequence().addPos(2, -4, -2, 0));
			if(ammo == 2) return new BusAnimation()
					.addBus("LATCH", new BusAnimationSequence().addPos(15, 0, 0, 100))
					.addBus("BREAK", new BusAnimationSequence().addPos(0, 0, 0, 100).addPos(60, 0, 0, 350, IType.SIN_DOWN))
					.addBus("SHELL1", new BusAnimationSequence().addPos(0, 0, 0, 0))
					.addBus("SHELL2", new BusAnimationSequence().addPos(0, 0, 0, 0))
					.addBus("SHELL3", new BusAnimationSequence().addPos(2, -4, -2, 0).addPos(2, -4, -2, 400).addPos(0, 0, -2, 450, IType.SIN_FULL).addPos(0, 0, 0, 50, IType.SIN_UP))
					.addBus("SHELL4", new BusAnimationSequence().addPos(2, -4, -2, 0));
			if(ammo == 3) return new BusAnimation()
					.addBus("LATCH", new BusAnimationSequence().addPos(15, 0, 0, 100))
					.addBus("BREAK", new BusAnimationSequence().addPos(0, 0, 0, 100).addPos(60, 0, 0, 350, IType.SIN_DOWN))
					.addBus("SHELL1", new BusAnimationSequence().addPos(0, 0, 0, 0))
					.addBus("SHELL2", new BusAnimationSequence().addPos(0, 0, 0, 0))
					.addBus("SHELL3", new BusAnimationSequence().addPos(0, 0, 0, 0))
					.addBus("SHELL4", new BusAnimationSequence().addPos(2, -4, -2, 0).addPos(2, -4, -2, 400).addPos(0, 0, -2, 450, IType.SIN_FULL).addPos(0, 0, 0, 50, IType.SIN_UP));
		case RELOAD_CYCLE:
			if(ammo == 0) return new BusAnimation()
					.addBus("LATCH", new BusAnimationSequence().addPos(15, 0, 0, 0))
					.addBus("BREAK", new BusAnimationSequence().addPos(60, 0, 0, 0))
					.addBus("SHELL1", new BusAnimationSequence().addPos(0, 0, 0, 0))
					.addBus("SHELL2", new BusAnimationSequence().addPos(2, -4, -2, 0).addPos(0, 0, -2, 450, IType.SIN_FULL).addPos(0, 0, 0, 50, IType.SIN_UP))
					.addBus("SHELL3", new BusAnimationSequence().addPos(2, -4, -2, 0))
					.addBus("SHELL4", new BusAnimationSequence().addPos(2, -4, -2, 0));
			if(ammo == 1) return new BusAnimation()
					.addBus("LATCH", new BusAnimationSequence().addPos(15, 0, 0, 0))
					.addBus("BREAK", new BusAnimationSequence().addPos(60, 0, 0, 0))
					.addBus("SHELL1", new BusAnimationSequence().addPos(0, 0, 0, 0))
					.addBus("SHELL2", new BusAnimationSequence().addPos(0, 0, 0, 0))
					.addBus("SHELL3", new BusAnimationSequence().addPos(2, -4, -2, 0).addPos(0, 0, -2, 450, IType.SIN_FULL).addPos(0, 0, 0, 50, IType.SIN_UP))
					.addBus("SHELL4", new BusAnimationSequence().addPos(2, -4, -2, 0));
			if(ammo == 2) return new BusAnimation()
					.addBus("LATCH", new BusAnimationSequence().addPos(15, 0, 0, 0))
					.addBus("BREAK", new BusAnimationSequence().addPos(60, 0, 0, 0))
					.addBus("SHELL1", new BusAnimationSequence().addPos(0, 0, 0, 0))
					.addBus("SHELL2", new BusAnimationSequence().addPos(0, 0, 0, 0))
					.addBus("SHELL3", new BusAnimationSequence().addPos(0, 0, 0, 0))
					.addBus("SHELL4", new BusAnimationSequence().addPos(2, -4, -2, 0).addPos(0, 0, -2, 450, IType.SIN_FULL).addPos(0, 0, 0, 50, IType.SIN_UP));
			return null;
		case RELOAD_END: return new BusAnimation()
				.addBus("LATCH", new BusAnimationSequence().addPos(15, 0, 0, 0).addPos(15, 0, 0, 250).addPos(0, 0, 0, 50))
				.addBus("BREAK", new BusAnimationSequence().addPos(60, 0, 0, 0).addPos(0, 0, 0, 250, IType.SIN_UP))
				.addBus(ammo >= 0 ? "SHELL1" : "NULL", new BusAnimationSequence().addPos(0, 0, 0, 0))
				.addBus(ammo >= 1 ? "SHELL2" : "NULL", new BusAnimationSequence().addPos(0, 0, 0, 0))
				.addBus(ammo >= 2 ? "SHELL3" : "NULL", new BusAnimationSequence().addPos(0, 0, 0, 0))
				.addBus(ammo >= 3 ? "SHELL4" : "NULL", new BusAnimationSequence().addPos(0, 0, 0, 0))
				.addBus(ammo < 0 ? "SHELL1" : "NULL", new BusAnimationSequence().addPos(2, -8, -2, 0))
				.addBus(ammo < 1 ? "SHELL2" : "NULL", new BusAnimationSequence().addPos(2, -8, -2, 0))
				.addBus(ammo < 2 ? "SHELL3" : "NULL", new BusAnimationSequence().addPos(2, -8, -2, 0))
				.addBus(ammo < 3 ? "SHELL4" : "NULL", new BusAnimationSequence().addPos(2, -8, -2, 0));
		case JAMMED: return new BusAnimation()
				.addBus("LATCH", new BusAnimationSequence().addPos(15, 0, 0, 0).addPos(15, 0, 0, 250).addPos(0, 0, 0, 50).addPos(0, 0, 0, 550).addPos(15, 0, 0, 100).addPos(15, 0, 0, 600).addPos(0, 0, 0, 50))
				.addBus("BREAK", new BusAnimationSequence().addPos(60, 0, 0, 0).addPos(0, 0, 0, 250, IType.SIN_UP).addPos(0, 0, 0, 600).addPos(45, 0, 0, 250, IType.SIN_DOWN).addPos(45, 0, 0, 300).addPos(0, 0, 0, 150, IType.SIN_UP))
				.addBus(ammo >= 0 ? "SHELL1" : "NULL", new BusAnimationSequence().addPos(0, 0, 0, 0))
				.addBus(ammo >= 1 ? "SHELL2" : "NULL", new BusAnimationSequence().addPos(0, 0, 0, 0))
				.addBus(ammo >= 2 ? "SHELL3" : "NULL", new BusAnimationSequence().addPos(0, 0, 0, 0))
				.addBus(ammo >= 3 ? "SHELL4" : "NULL", new BusAnimationSequence().addPos(0, 0, 0, 0))
				.addBus(ammo < 0 ? "SHELL1" : "NULL", new BusAnimationSequence().addPos(2, -8, -2, 0))
				.addBus(ammo < 1 ? "SHELL2" : "NULL", new BusAnimationSequence().addPos(2, -8, -2, 0))
				.addBus(ammo < 2 ? "SHELL3" : "NULL", new BusAnimationSequence().addPos(2, -8, -2, 0))
				.addBus(ammo < 3 ? "SHELL4" : "NULL", new BusAnimationSequence().addPos(2, -8, -2, 0));
		case INSPECT: return new BusAnimation()
				.addBus("LATCH", new BusAnimationSequence().addPos(15, 0, 0, 100).addPos(15, 0, 0, 1100).addPos(0, 0, 0, 50))
				.addBus("BREAK", new BusAnimationSequence().addPos(0, 0, 0, 100).addPos(60, 0, 0, 350, IType.SIN_DOWN).addPos(60, 0, 0, 500).addPos(0, 0, 0, 250, IType.SIN_UP))
				.addBus(ammo > 0 ? "SHELL1" : "NULL", new BusAnimationSequence().addPos(0, 0, 0, 0))
				.addBus(ammo > 1 ? "SHELL2" : "NULL", new BusAnimationSequence().addPos(0, 0, 0, 0))
				.addBus(ammo > 2 ? "SHELL3" : "NULL", new BusAnimationSequence().addPos(0, 0, 0, 0))
				.addBus(ammo > 3 ? "SHELL4" : "NULL", new BusAnimationSequence().addPos(0, 0, 0, 0))
				.addBus(ammo < 1 ? "SHELL1" : "NULL", new BusAnimationSequence().addPos(2, -8, -2, 0))
				.addBus(ammo < 2 ? "SHELL2" : "NULL", new BusAnimationSequence().addPos(2, -8, -2, 0))
				.addBus(ammo < 3 ? "SHELL3" : "NULL", new BusAnimationSequence().addPos(2, -8, -2, 0))
				.addBus(ammo < 4 ? "SHELL4" : "NULL", new BusAnimationSequence().addPos(2, -8, -2, 0));
		}
		
		return null;
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_SPAS_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(-60, 0, 0, 0).addPos(0, 0, -3, 500, IType.SIN_DOWN));
		case CYCLE: return ResourceManager.spas_12_anim.get("Fire");
		case CYCLE_DRY: return ResourceManager.spas_12_anim.get("FireDry");
		case ALT_CYCLE: return ResourceManager.spas_12_anim.get("FireAlt");
		case RELOAD:
			boolean empty = ((ItemGunBaseNT) stack.getItem()).getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, MainRegistry.proxy.me().inventory) <= 0;
			return ResourceManager.spas_12_anim.get(empty ? "ReloadEmptyStart" : "ReloadStart");
		case RELOAD_CYCLE: return ResourceManager.spas_12_anim.get("Reload");
		case RELOAD_END: return ResourceManager.spas_12_anim.get("ReloadEnd");
		case JAMMED: return ResourceManager.spas_12_anim.get("Jammed");
		case INSPECT: return ResourceManager.spas_12_anim.get("Inspect");
		}
		
		return null;
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_SHREDDER_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(60, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_DOWN));
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

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_SEXY_ANIMS = (stack, type) -> {
		switch(type) {
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, -1, 50, IType.SIN_DOWN).addPos(0, 0, 0, 150, IType.SIN_FULL))
				.addBus("CYCLE", new BusAnimationSequence().addPos(0, 0, 18, 50));
		case CYCLE_DRY: return new BusAnimation()
				.addBus("CYCLE", new BusAnimationSequence().addPos(0, 0, 18, 50));
		}
		
		return LAMBDA_SHREDDER_ANIMS.apply(stack, type);
	};
}
