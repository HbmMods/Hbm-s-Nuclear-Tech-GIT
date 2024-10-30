package com.hbm.items.weapon.sedna.factory;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import com.hbm.entity.effect.EntityFireLingering;
import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.impl.ItemGunChemthrower;
import com.hbm.items.weapon.sedna.mags.MagazineFluid;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.particle.helper.FlameCreator;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class XFactoryFlamer {

	public static BulletConfig flame_diesel;
	public static BulletConfig flame_gas;
	public static BulletConfig flame_napalm;
	public static BulletConfig flame_balefire;
	public static BulletConfig flame_digamma;

	public static Consumer<EntityBulletBaseMK4> LAMBDA_FIRE = (bullet) -> {
		if(bullet.worldObj.isRemote && MainRegistry.proxy.me().getDistanceToEntity(bullet) < 100) FlameCreator.composeEffectClient(bullet.worldObj, bullet.posX, bullet.posY - 0.125, bullet.posZ, FlameCreator.META_FIRE);
	};
	public static Consumer<EntityBulletBaseMK4> LAMBDA_BALEFIRE = (bullet) -> {
		if(bullet.worldObj.isRemote && MainRegistry.proxy.me().getDistanceToEntity(bullet) < 100) FlameCreator.composeEffectClient(bullet.worldObj, bullet.posX, bullet.posY - 0.125, bullet.posZ, FlameCreator.META_BALEFIRE);
	};
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_IGNITE_FIRE = (bullet, mop) -> {
		if(mop.entityHit instanceof EntityLivingBase) {
			HbmLivingProps props = HbmLivingProps.getData((EntityLivingBase) mop.entityHit);
			if(props.fire < 100) props.fire = 100;
		}
	};
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_IGNITE_BALEFIRE = (bullet, mop) -> {
		if(mop.entityHit instanceof EntityLivingBase) {
			HbmLivingProps props = HbmLivingProps.getData((EntityLivingBase) mop.entityHit);
			if(props.balefire < 200) props.balefire = 200;
		}
	};
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_LINGER_DIESEL = (bullet, mop) -> { if(!igniteIfPossible(bullet, mop)) spawnFire(bullet, mop, 2F, 1F, 100, EntityFireLingering.TYPE_DIESEL); };
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_LINGER_GAS = (bullet, mop) -> { igniteIfPossible(bullet, mop); };
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_LINGER_NAPALM = (bullet, mop) -> { if(!igniteIfPossible(bullet, mop)) spawnFire(bullet, mop, 2.5F, 1F, 200, EntityFireLingering.TYPE_DIESEL); };
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_LINGER_BALEFIRE = (bullet, mop) -> { spawnFire(bullet, mop, 3F, 1F, 300, EntityFireLingering.TYPE_BALEFIRE); };
	
	public static boolean igniteIfPossible(EntityBulletBaseMK4 bullet, MovingObjectPosition mop) {
		if(mop.typeOfHit == mop.typeOfHit.BLOCK) {
			World world = bullet.worldObj;
			Block b = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
			ForgeDirection dir = ForgeDirection.getOrientation(mop.sideHit);
			if(b.isFlammable(world, mop.blockX, mop.blockY, mop.blockZ, dir.getOpposite())) {
				if(world.getBlock(mop.blockX + dir.offsetX, mop.blockY + dir.offsetY, mop.blockZ + dir.offsetZ).isAir(world, mop.blockX + dir.offsetX, mop.blockY + dir.offsetY, mop.blockZ + dir.offsetZ)) {
					world.setBlock(mop.blockX + dir.offsetX, mop.blockY + dir.offsetY, mop.blockZ + dir.offsetZ, Blocks.fire);
					return true;
				}
			}
			bullet.setDead();
		}
		return false;
	}
	
	public static void spawnFire(EntityBulletBaseMK4 bullet, MovingObjectPosition mop, float width, float height, int duration, int type) {
		if(mop.typeOfHit == mop.typeOfHit.BLOCK) {
			List<EntityFireLingering> fires = bullet.worldObj.getEntitiesWithinAABB(EntityFireLingering.class,
					AxisAlignedBB.getBoundingBox(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord).expand(width / 2 + 0.5, height / 2 + 0.5, width / 2 + 0.5));
			if(fires.isEmpty()) {
				EntityFireLingering fire = new EntityFireLingering(bullet.worldObj).setArea(width, height).setDuration(duration).setType(type);
				fire.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
				bullet.worldObj.spawnEntityInWorld(fire);
			}
			bullet.setDead();
		}
	}

	public static void init() {
		flame_diesel = new BulletConfig().setItem(EnumAmmo.FLAME_DIESEL).setLife(100).setVel(1F).setGrav(0.02D).setReloadCount(300).setOnUpdate(LAMBDA_FIRE).setOnRicochet(LAMBDA_LINGER_DIESEL);
		flame_gas = new BulletConfig().setItem(EnumAmmo.FLAME_GAS).setLife(10).setSpread(0.05F).setVel(1F).setGrav(0.0D).setReloadCount(300).setOnUpdate(LAMBDA_FIRE).setOnRicochet(LAMBDA_LINGER_GAS);
		flame_napalm = new BulletConfig().setItem(EnumAmmo.FLAME_NAPALM).setLife(200).setVel(1F).setGrav(0.02D).setReloadCount(300).setOnUpdate(LAMBDA_FIRE).setOnRicochet(LAMBDA_LINGER_NAPALM);
		flame_balefire = new BulletConfig().setItem(EnumAmmo.FLAME_BALEFIRE).setLife(200).setVel(1F).setGrav(0.02D).setReloadCount(300).setOnUpdate(LAMBDA_BALEFIRE).setOnRicochet(LAMBDA_LINGER_BALEFIRE);
		
		ModItems.gun_flamer = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(20_000).draw(10).inspect(17).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(10F).delay(1).auto(true).reload(90).jam(17)
						.mag(new MagazineFullReload(0, 300).addConfigs(flame_diesel, flame_gas, flame_napalm, flame_balefire))
						.offset(0.75, -0.0625, -0.25D)
						.setupStandardFire().recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.setupStandardConfiguration()
				.anim(LAMBDA_FLAMER_ANIMS).orchestra(Orchestras.ORCHESTRA_FLAMER)
				).setUnlocalizedName("gun_flamer");
		
		ModItems.gun_chemthrower = new ItemGunChemthrower(WeaponQuality.A_SIDE, new GunConfig()
				.dura(90_000).draw(10).inspect(17).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.delay(1).auto(true)
						.mag(new MagazineFluid(0, 3_000))
						.offset(0.75, -0.0625, -0.25D)
						.canFire(ItemGunChemthrower.LAMBDA_CAN_FIRE).fire(ItemGunChemthrower.LAMBDA_FIRE))
				.pp(Lego.LAMBDA_STANDARD_CLICK_PRIMARY).decider(GunStateDecider.LAMBDA_STANDARD_DECIDER)
				.anim(LAMBDA_CHEMTHROWER_ANIMS).orchestra(Orchestras.ORCHESTRA_CHEMTHROWER)
				).setUnlocalizedName("gun_chemthrower");
	}

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_FLAMER_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(-45, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_DOWN));
		case RELOAD: return ResourceManager.flamethrower_anim.get("Reload");
		case INSPECT:
		case JAMMED: return new BusAnimation()
				.addBus("ROTATE", new BusAnimationSequence().addPos(0, 0, 45, 250, IType.SIN_FULL).addPos(0, 0, 45, 350).addPos(0, 0, -15, 150, IType.SIN_FULL).addPos(0, 0, 0, 100, IType.SIN_FULL));
		}
		
		return null;
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_CHEMTHROWER_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(-45, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_DOWN));
		}
		
		return null;
	};
}
