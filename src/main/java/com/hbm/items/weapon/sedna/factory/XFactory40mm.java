package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.entity.effect.EntityFireLingering;
import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectWeapon;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.mags.MagazineSingleReload;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class XFactory40mm {

	public static BulletConfig g26_flare;
	public static BulletConfig g40_he;
	public static BulletConfig g40_heat;
	public static BulletConfig g40_demo;
	public static BulletConfig g40_inc;
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_EXPLODE = (bullet, mop) -> {
		Lego.standardExplode(bullet, mop, 5F); bullet.setDead();
	};
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_EXPLODE_HEAT = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3) return;
		Lego.standardExplode(bullet, mop, 2.5F); bullet.setDead();
	};
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_EXPLODE_DEMO = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3) return;
		ExplosionVNT vnt = new ExplosionVNT(bullet.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 5F);
		vnt.setBlockAllocator(new BlockAllocatorStandard());
		vnt.setBlockProcessor(new BlockProcessorStandard());
		vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, bullet.damage));
		vnt.setPlayerProcessor(new PlayerProcessorStandard());
		vnt.setSFX(new ExplosionEffectWeapon(10, 2.5F, 1F));
		vnt.explode();
		bullet.setDead();
	};
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_EXPLODE_INC = (bullet, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.ENTITY && bullet.ticksExisted < 3) return;
		World world = bullet.worldObj;
		Lego.standardExplode(bullet, mop, 3F);
		EntityFireLingering fire = new EntityFireLingering(world).setArea(5, 2).setDuration(200).setType(EntityFireLingering.TYPE_DIESEL);
		fire.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
		world.spawnEntityInWorld(fire);
		bullet.setDead();
		for(int dx = -1; dx <= 1; dx++) {
			for(int dy = -1; dy <= 1; dy++) {
				for(int dz = -2; dz <= 2; dz++) {
					int x = (int) Math.floor(mop.hitVec.xCoord) + dx;
					int y = (int) Math.floor(mop.hitVec.yCoord) + dy;
					int z = (int) Math.floor(mop.hitVec.zCoord) + dz;
					if(world.getBlock(x, y, z).isAir(bullet.worldObj, x, y, z)) for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
						if(world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ).isFlammable(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir.getOpposite())) {
							world.setBlock(x, y, z, Blocks.fire);
							break;
						}
					}
				}
			}
		}
	};
	
	public static void init() {
		
		g26_flare = new BulletConfig().setItem(EnumAmmo.G26_FLARE).setLife(100).setVel(2F).setGrav(0.035D).setRenderRotations(false).setCasing(new SpentCasing(CasingType.STRAIGHT).setColor(0x9E1616).setScale(2F).register("G40Flare"));
		
		BulletConfig g40_base = new BulletConfig().setLife(200).setVel(2F).setGrav(0.035D);
		g40_he = g40_base.clone().setItem(EnumAmmo.G40_HE).setOnImpact(LAMBDA_STANDARD_EXPLODE).setCasing(new SpentCasing(CasingType.STRAIGHT).setColor(0x777777).setScale(2, 2F, 1.5F).register("g40"));
		g40_heat = g40_base.clone().setItem(EnumAmmo.G40_HEAT).setOnImpact(LAMBDA_STANDARD_EXPLODE_HEAT).setCasing(new SpentCasing(CasingType.STRAIGHT).setColor(0x5E6854).setScale(2, 2F, 1.5F).register("g40heat"));
		g40_demo = g40_base.clone().setItem(EnumAmmo.G40_DEMO).setOnImpact(LAMBDA_STANDARD_EXPLODE_DEMO).setCasing(new SpentCasing(CasingType.STRAIGHT).setColor(0xE30000).setScale(2, 2F, 1.5F).register("g40demo"));
		g40_inc = g40_base.clone().setItem(EnumAmmo.G40_INC).setOnImpact(LAMBDA_STANDARD_EXPLODE_INC).setCasing(new SpentCasing(CasingType.STRAIGHT).setColor(0xE86F20).setScale(2, 2F, 1.5F).register("g40inc"));

		ModItems.gun_flaregun = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(100).draw(7).inspect(39).crosshair(Crosshair.L_CIRCUMFLEX).smoke(LAMBDA_SMOKE)
				.rec(new Receiver(0)
						.dmg(15F).delay(20).reload(28).jam(33).sound("hbm:weapon.hkShoot", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 1).addConfigs(g26_flare))
						.offset(0.75, -0.0625, -0.1875D)
						.setupStandardFire().recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.setupStandardConfiguration()
				.anim(LAMBDA_FLAREGUN_ANIMS).orchestra(Orchestras.ORCHESTRA_FLAREGUN)
				).setUnlocalizedName("gun_flaregun");
		
		ModItems.gun_congolake = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(400).draw(7).inspect(39).reloadSequential(true).crosshair(Crosshair.L_CIRCUMFLEX).smoke(LAMBDA_SMOKE)
				.rec(new Receiver(0)
						.dmg(30F).delay(24).reload(16, 16, 16, 0).jam(0).sound("hbm:weapon.glShoot", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 4).addConfigs(g40_he, g40_heat, g40_demo, g40_inc))
						.offset(0.75, -0.0625, -0.1875D)
						.setupStandardFire().recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.setupStandardConfiguration()
				.anim(LAMBDA_CONGOLAKE_ANIMS).orchestra(Orchestras.ORCHESTRA_CONGOLAKE)
				).setUnlocalizedName("gun_congolake");
	}
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_SMOKE = (stack, ctx) -> {
		Lego.handleStandardSmoke(ctx.entity, stack, 1500, 0.025D, 1.05D, 0);
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_FLAREGUN_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(-90, 0, 0, 0).addPos(0, 0, 0, 350, IType.SIN_DOWN));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, 0, 50).addPos(0, 0, -3, 50).addPos(0, 0, 0, 250))
				.addBus("HAMMER", new BusAnimationSequence().addPos(15, 0, 0, 50).addPos(15, 0, 0, 550).addPos(0, 0, 0, 100));
		case CYCLE_DRY: return new BusAnimation()
				.addBus("HAMMER", new BusAnimationSequence().addPos(15, 0, 0, 50).addPos(15, 0, 0, 550).addPos(0, 0, 0, 100));
		case RELOAD: return new BusAnimation()
				.addBus("OPEN", new BusAnimationSequence().addPos(45, 0, 0, 200, IType.SIN_FULL).addPos(45, 0, 0, 750).addPos(0, 0, 0, 200, IType.SIN_UP))
				.addBus("SHELL", new BusAnimationSequence().addPos(4, -8, -4, 0).addPos(4, -8, -4, 200).addPos(0, 0, -5, 500, IType.SIN_DOWN).addPos(0, 0, 0, 200, IType.SIN_UP))
				.addBus("FLIP", new BusAnimationSequence().addPos(0, 0, 0, 200).addPos(25, 0, 0, 200, IType.SIN_DOWN).addPos(25, 0, 0, 800).addPos(0, 0, 0, 200, IType.SIN_DOWN));
		case JAMMED: return new BusAnimation()
				.addBus("OPEN", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(45, 0, 0, 200, IType.SIN_FULL).addPos(45, 0, 0, 500).addPos(0, 0, 0, 200, IType.SIN_UP))
				.addBus("FLIP", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, 0, 200).addPos(25, 0, 0, 200, IType.SIN_DOWN).addPos(25, 0, 0, 550).addPos(0, 0, 0, 200, IType.SIN_DOWN));
		case INSPECT: return new BusAnimation()
				.addBus("FLIP", new BusAnimationSequence().addPos(-360 * 3, 0, 0, 1500, IType.SIN_FULL));
		}
		
		return null;
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_CONGOLAKE_ANIMS = (stack, type) -> {
		int ammo = ((ItemGunBaseNT) stack.getItem()).getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, MainRegistry.proxy.me().inventory);
		switch(type) {
		case EQUIP: return ResourceManager.congolake_anim.get("Equip");
		case CYCLE: return ResourceManager.congolake_anim.get(ammo <= 1 ? "FireEmpty" : "Fire");
		case RELOAD: return ResourceManager.congolake_anim.get(ammo == 0 ? "ReloadEmpty": "ReloadStart");
		case RELOAD_CYCLE: return ResourceManager.congolake_anim.get("Reload");
		case RELOAD_END: return ResourceManager.congolake_anim.get("ReloadEnd");
		case JAMMED: return ResourceManager.congolake_anim.get("Jammed");
		case INSPECT: return ResourceManager.congolake_anim.get("Inspect");
		}
		
		return null;
	};
}
