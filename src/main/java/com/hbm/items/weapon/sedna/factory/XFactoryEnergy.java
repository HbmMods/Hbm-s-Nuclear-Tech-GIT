package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.entity.effect.EntityFireLingering;
import com.hbm.entity.projectile.EntityBulletBeamBase;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.mags.MagazineBelt;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.util.DamageResistanceHandler.DamageClass;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class XFactoryEnergy {
	
	public static final ResourceLocation scope_luna = new ResourceLocation(RefStrings.MODID, "textures/misc/scope_luna.png");

	public static BulletConfig energy_tesla;
	public static BulletConfig energy_tesla_overcharge;

	public static BulletConfig energy_las;
	public static BulletConfig energy_las_overcharge;
	public static BulletConfig energy_las_ir;
	
	public static BiConsumer<EntityBulletBeamBase, MovingObjectPosition> LAMBDA_LIGHTNING_HIT = (beam, mop) -> {
		
		if(mop.typeOfHit == mop.typeOfHit.BLOCK) {
			ForgeDirection dir = ForgeDirection.getOrientation(mop.sideHit);
			mop.hitVec.xCoord += dir.offsetX * 0.5;
			mop.hitVec.yCoord += dir.offsetY * 0.5;
			mop.hitVec.zCoord += dir.offsetZ * 0.5;
		}
		
		ExplosionVNT vnt = new ExplosionVNT(beam.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 2F, beam.getThrower());
		vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, beam.damage).setDamageClass(beam.config.dmgClass));
		vnt.setPlayerProcessor(new PlayerProcessorStandard());
		vnt.explode();
		beam.worldObj.playSoundEffect(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, "hbm:entity.ufoBlast", 5.0F, 0.9F + beam.worldObj.rand.nextFloat() * 0.2F);
		beam.worldObj.playSoundEffect(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, "fireworks.blast", 5.0F, 0.5F);

		float yaw = beam.worldObj.rand.nextFloat() * 180F;
		for(int i = 0; i < 3; i++) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "plasmablast");
			data.setFloat("r", 0.5F);
			data.setFloat("g", 0.5F);
			data.setFloat("b", 1.0F);
			data.setFloat("pitch", -60F + 60F * i);
			data.setFloat("yaw", yaw);
			data.setFloat("scale", 2F);
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord),
					new TargetPoint(beam.worldObj.provider.dimensionId, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 100));
		}
		
		if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
			if(mop.entityHit instanceof EntityLivingBase) {
				((EntityLivingBase) mop.entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 60, 9));
				((EntityLivingBase) mop.entityHit).addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 60, 9));
			}
		}
	};
	
	public static BiConsumer<EntityBulletBeamBase, MovingObjectPosition> LAMBDA_IR_HIT = (beam, mop) -> {
		BulletConfig.LAMBDA_STANDARD_BEAM_HIT.accept(beam, mop);
		
		if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
			if(mop.entityHit instanceof EntityLivingBase) {
				EntityLivingBase living = (EntityLivingBase) mop.entityHit;
				HbmLivingProps props = HbmLivingProps.getData(living);
				if(props.fire < 100) props.fire = 100;
			}
		}

		if(mop.typeOfHit == mop.typeOfHit.BLOCK) {
			World world = beam.worldObj;
			Block b = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
			ForgeDirection dir = ForgeDirection.getOrientation(mop.sideHit);
			if(b.isFlammable(world, mop.blockX, mop.blockY, mop.blockZ, dir.getOpposite())) {
				if(world.getBlock(mop.blockX + dir.offsetX, mop.blockY + dir.offsetY, mop.blockZ + dir.offsetZ).isAir(world, mop.blockX + dir.offsetX, mop.blockY + dir.offsetY, mop.blockZ + dir.offsetZ)) {
					world.setBlock(mop.blockX + dir.offsetX, mop.blockY + dir.offsetY, mop.blockZ + dir.offsetZ, Blocks.fire);
					return;
				}
			}
			
			EntityFireLingering fire = new EntityFireLingering(beam.worldObj).setArea(2, 1).setDuration(100).setType(EntityFireLingering.TYPE_DIESEL);
			fire.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
			beam.worldObj.spawnEntityInWorld(fire);
		}
	};
	
	public static void init() {

		energy_tesla = new BulletConfig().setItem(EnumAmmo.CAPACITOR).setupDamageClass(DamageClass.ELECTRIC).setBeam().setSpread(0.0F).setLife(5).setRenderRotations(false).setDoesPenetrate(true)
				.setOnBeamImpact(LAMBDA_LIGHTNING_HIT);
		energy_tesla_overcharge = new BulletConfig().setItem(EnumAmmo.CAPACITOR_OVERCHARGE).setupDamageClass(DamageClass.ELECTRIC).setBeam().setSpread(0.0F).setLife(5).setRenderRotations(false).setDoesPenetrate(true)
				.setDamage(1.5F).setOnBeamImpact(LAMBDA_LIGHTNING_HIT);

		energy_las = new BulletConfig().setItem(EnumAmmo.CAPACITOR).setupDamageClass(DamageClass.LASER).setBeam().setSpread(0.0F).setLife(5).setRenderRotations(false).setOnBeamImpact(BulletConfig.LAMBDA_STANDARD_BEAM_HIT);
		energy_las_overcharge = new BulletConfig().setItem(EnumAmmo.CAPACITOR_OVERCHARGE).setupDamageClass(DamageClass.LASER).setBeam().setSpread(0.0F).setLife(5).setRenderRotations(false).setDoesPenetrate(true).setOnBeamImpact(BulletConfig.LAMBDA_STANDARD_BEAM_HIT);
		energy_las_ir = new BulletConfig().setItem(EnumAmmo.CAPACITOR_IR).setupDamageClass(DamageClass.FIRE).setBeam().setSpread(0.0F).setLife(5).setRenderRotations(false).setOnBeamImpact(LAMBDA_IR_HIT);

		ModItems.gun_tesla_cannon = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(2_000).draw(10).inspect(33).reloadSequential(true).crosshair(Crosshair.CIRCLE)
				.rec(new Receiver(0)
						.dmg(35F).delay(20).reload(44).jam(19).sound("hbm:weapon.fire.tesla", 1.0F, 1.0F)
						.mag(new MagazineBelt().addConfigs(energy_tesla, energy_tesla_overcharge))
						.offset(0.75, 0, -0.375).offsetScoped(0.75, 0, -0.25)
						.setupStandardFire().recoil(LAMBDA_RECOIL_ENERGY))
				.setupStandardConfiguration()
				.anim(LAMBDA_TESLA_ANIMS).orchestra(Orchestras.ORCHESTRA_TESLA)
				).setUnlocalizedName("gun_tesla_cannon");

		ModItems.gun_lasrifle = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(2_000).draw(10).inspect(26).reloadSequential(true).crosshair(Crosshair.CIRCLE).scopeTexture(scope_luna)
				.rec(new Receiver(0)
						.dmg(50F).delay(8).reload(44).jam(36).sound("hbm:weapon.fire.laser", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 24).addConfigs(energy_las, energy_las_overcharge, energy_las_ir))
						.offset(0.75, -0.0625 * 1.5, -0.1875)
						.setupStandardFire().recoil(LAMBDA_RECOIL_ENERGY))
				.setupStandardConfiguration()
				.anim(LAMBDA_LASRIFLE).orchestra(Orchestras.ORCHESTRA_LASRIFLE)
				).setUnlocalizedName("gun_lasrifle");
	}
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_ENERGY = (stack, ctx) -> { };

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_TESLA_ANIMS = (stack, type) -> {
		int amount = ((ItemGunBaseNT) stack.getItem()).getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, MainRegistry.proxy.me().inventory);
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(60, 0, 0, 0).addPos(0, 0, 0, 1000, IType.SIN_DOWN));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, ItemGunBaseNT.getIsAiming(stack) ? -0.5 : -1, 100, IType.SIN_DOWN).addPos(0, 0, 0, 250, IType.SIN_FULL))
				.addBus("CYCLE", new BusAnimationSequence().addPos(0, 0, 0, 150).addPos(0, 0, 22.5, 350))
				.addBus("COUNT", new BusAnimationSequence().addPos(amount, 0, 0, 0));
		case CYCLE_DRY: return new BusAnimation()
				.addBus("CYCLE", new BusAnimationSequence().addPos(0, 0, 0, 150).addPos(0, 0, 22.5, 350));
		case INSPECT: return new BusAnimation()
				.addBus("YOMI", new BusAnimationSequence().addPos(8, -4, 0, 0).addPos(4, -1, 0, 500, IType.SIN_DOWN).addPos(4, -1, 0, 1000).addPos(6, -6, 0, 500, IType.SIN_UP))
				.addBus("SQUEEZE", new BusAnimationSequence().addPos(1, 1, 1, 0).addPos(1, 1, 1, 750).addPos(1, 1, 0.5, 125).addPos(1, 1, 1, 125));
		}
		
		return null;
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_LASRIFLE = (stack, type) -> {
		int amount = ((ItemGunBaseNT) stack.getItem()).getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, MainRegistry.proxy.me().inventory);
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(60, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_DOWN));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, -0.5, 50, IType.SIN_DOWN).addPos(0, 0, 0, 150, IType.SIN_FULL))
				.addBus("CYCLE", new BusAnimationSequence().addPos(0, 0, 0, 150).addPos(0, 0, 22.5, 350))
				.addBus("COUNT", new BusAnimationSequence().addPos(amount, 0, 0, 0));
		case RELOAD: return new BusAnimation()
				.addBus("LEVER", new BusAnimationSequence().addPos(-90, 0, 0, 350, IType.SIN_UP).addPos(-90, 0, 0, 1500).addPos(0, 0, 0, 350, IType.SIN_UP))
				.addBus("MAG", new BusAnimationSequence().addPos(0, 0, 0, 350).addPos(0, -5, 0, 350, IType.SIN_UP).addPos(0, -5, 0, 500).addPos(0, -0.25, 0, 500, IType.SIN_FULL).addPos(0, -0.25, 0, 150).addPos(0, 0, 0, 350))
				.addBus("EQUIP", new BusAnimationSequence().addPos(0, 0, 0, 1700).addPos(-2, 0, 0, 100, IType.SIN_DOWN).addPos(0, 0, 0, 100, IType.SIN_FULL));
		case JAMMED: return new BusAnimation()
				.addBus("LEVER", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(-90, 0, 0, 350, IType.SIN_UP).addPos(-90, 0, 0, 600).addPos(0, 0, 0, 350, IType.SIN_UP))
				.addBus("MAG", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, 0, 350).addPos(0, -2, 0, 200, IType.SIN_UP).addPos(0, -0.25, 0, 250, IType.SIN_FULL).addPos(0, -0.25, 0, 150).addPos(0, 0, 0, 350))
				.addBus("EQUIP", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, 0, 800).addPos(-2, 0, 0, 100, IType.SIN_DOWN).addPos(0, 0, 0, 100, IType.SIN_FULL));
		case INSPECT: return new BusAnimation()
				.addBus("LEVER", new BusAnimationSequence().addPos(-90, 0, 0, 350, IType.SIN_UP).addPos(-90, 0, 0, 600).addPos(0, 0, 0, 350, IType.SIN_UP))
				.addBus("MAG", new BusAnimationSequence().addPos(0, 0, 0, 350).addPos(0, -2, 0, 200, IType.SIN_UP).addPos(0, -0.25, 0, 250, IType.SIN_FULL).addPos(0, -0.25, 0, 150).addPos(0, 0, 0, 350))
				.addBus("EQUIP", new BusAnimationSequence().addPos(0, 0, 0, 800).addPos(-2, 0, 0, 100, IType.SIN_DOWN).addPos(0, 0, 0, 100, IType.SIN_FULL));
		}
		
		return null;
	};
}
