package com.hbm.items.weapon.sedna.factory;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockDetonatable;
import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.entity.projectile.EntityDuchessGambit;
import com.hbm.extprop.HbmLivingProps;
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
import com.hbm.items.weapon.sedna.mags.MagazineBelt;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.items.weapon.sedna.mags.MagazineSingleReload;
import com.hbm.items.weapon.sedna.mods.WeaponModManager;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.util.BobMathUtil;
import com.hbm.util.TrackerUtil;
import com.hbm.util.Vec3NT;
import com.hbm.util.DamageResistanceHandler.DamageClass;
import com.hbm.util.EntityDamageUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

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

	public static BulletConfig g12_shredder;
	public static BulletConfig g12_shredder_slug;
	public static BulletConfig g12_shredder_flechette;
	public static BulletConfig g12_shredder_magnum;
	public static BulletConfig g12_shredder_explosive;
	public static BulletConfig g12_shredder_phosphorus;

	public static BulletConfig g12_sub;
	public static BulletConfig g12_sub_slug;
	public static BulletConfig g12_sub_flechette;
	public static BulletConfig g12_sub_magnum;
	public static BulletConfig g12_sub_explosive;
	public static BulletConfig g12_sub_phosphorus;
	
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
	
	public static BulletConfig makeShredderConfig(BulletConfig original, BulletConfig submunition) {
		BulletConfig cfg = new BulletConfig().setBeam().setRenderRotations(false).setLife(5).setDamage(original.damageMult * original.projectilesMax).setupDamageClass(DamageClass.LASER);
		cfg.setItem(original.ammo);
		cfg.setCasing(original.casing);
		cfg.setOnBeamImpact((beam, mop) -> {
			
			int projectiles = submunition.projectilesMin;
			if(submunition.projectilesMax > submunition.projectilesMin) projectiles += beam.worldObj.rand.nextInt(submunition.projectilesMax - submunition.projectilesMin + 1);

			if(mop.typeOfHit == mop.typeOfHit.BLOCK) {

				ForgeDirection dir = ForgeDirection.getOrientation(mop.sideHit);

				mop.hitVec.xCoord += dir.offsetX * 0.1;
				mop.hitVec.yCoord += dir.offsetY * 0.1;
				mop.hitVec.zCoord += dir.offsetZ * 0.1;
				
				spawnPulse(beam.worldObj, mop, beam.rotationYaw, beam.rotationPitch);
				
				List<Entity> blast = beam.worldObj.getEntitiesWithinAABBExcludingEntity(beam, AxisAlignedBB.getBoundingBox(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord).expand(0.75, 0.75, 0.75));
				DamageSource source = BulletConfig.getDamage(beam, beam.getThrower(), DamageClass.LASER);

				for(Entity e : blast) {
					if(!e.isEntityAlive()) continue;
					if(e instanceof EntityLivingBase) {
						EntityDamageUtil.attackEntityFromNT((EntityLivingBase) e, source, beam.damage, true, false, 0D, 0F, 0F);
						if(!e.isEntityAlive()) ConfettiUtil.decideConfetti((EntityLivingBase) e, source);
					} else {
						e.attackEntityFrom(source, beam.damage);
					}
				}
				
				for(int i = 0; i < projectiles; i++) {
					EntityBulletBaseMK4 bullet = new EntityBulletBaseMK4(beam.worldObj, beam.thrower, submunition, beam.damage * submunition.damageMult, 0.2F, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, dir.offsetX, dir.offsetY, dir.offsetZ);
					bullet.worldObj.spawnEntityInWorld(bullet);
				}
			}
			
			if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
				
				spawnPulse(beam.worldObj, mop, beam.rotationYaw, beam.rotationPitch);
				
				for(int i = 0; i < projectiles; i++) {
					Vec3NT vec = new Vec3NT(beam.worldObj.rand.nextGaussian(), beam.worldObj.rand.nextGaussian(), beam.worldObj.rand.nextGaussian()).normalizeSelf();
					EntityBulletBaseMK4 bullet = new EntityBulletBaseMK4(beam.worldObj, beam.thrower, submunition, beam.damage * submunition.damageMult, 0.2F, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, vec.xCoord, vec.yCoord, vec.zCoord);
					bullet.worldObj.spawnEntityInWorld(bullet);
				}
			}
		});
		return cfg;
	}
	
	public static BulletConfig makeShredderSubmunition(BulletConfig original) {
		BulletConfig cfg = original.clone();
		cfg.setRicochetAngle(90).setRicochetCount(3).setVel(0.5F).setLife(50).setupDamageClass(DamageClass.LASER).setOnRicochet(LAMBDA_SHREDDER_RICOCHET);
		return cfg;
	}
	
	//this sucks
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_SHREDDER_RICOCHET = (bullet, mop) -> {
		
		if(mop.typeOfHit == mop.typeOfHit.BLOCK) {
			
			Block b = bullet.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
			if(b.getMaterial() == Material.glass) {
				bullet.worldObj.func_147480_a(mop.blockX, mop.blockY, mop.blockZ, false);
				bullet.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
				return;
			}
			if(b instanceof BlockDetonatable) {
				((BlockDetonatable) b).onShot(bullet.worldObj, mop.blockX, mop.blockY, mop.blockZ);
			}
			if(b == ModBlocks.deco_crt) {
				int meta = bullet.worldObj.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ);
				bullet.worldObj.setBlockMetadataWithNotify(mop.blockX, mop.blockY, mop.blockZ, meta % 4 + 4, 3);
			}

			ForgeDirection dir = ForgeDirection.getOrientation(mop.sideHit);
			Vec3 face = Vec3.createVectorHelper(dir.offsetX, dir.offsetY, dir.offsetZ);
			Vec3 vel = Vec3.createVectorHelper(bullet.motionX, bullet.motionY, bullet.motionZ).normalize();

			double angle = Math.abs(BobMathUtil.getCrossAngle(vel, face) - 90);

			if(angle <= bullet.config.ricochetAngle) {
				
				spawnPulse(bullet.worldObj, mop, bullet.rotationYaw, bullet.rotationPitch);
				
				List<Entity> blast = bullet.worldObj.getEntitiesWithinAABBExcludingEntity(bullet, AxisAlignedBB.getBoundingBox(bullet.posX, bullet.posY, bullet.posZ, bullet.posX, bullet.posY, bullet.posZ).expand(0.5, 0.5, 0.5));
				DamageSource source = BulletConfig.getDamage(bullet, bullet.getThrower(), DamageClass.LASER);
				
				for(Entity e : blast) {
					if(!e.isEntityAlive()) continue;
					if(e instanceof EntityLivingBase) {
						EntityDamageUtil.attackEntityFromNT((EntityLivingBase) e, source, bullet.damage, true, false, 0D, 0F, 0F);
						if(!e.isEntityAlive()) ConfettiUtil.decideConfetti((EntityLivingBase) e, source);
					} else {
						e.attackEntityFrom(source, bullet.damage);
					}
				}
				
				bullet.ricochets++;
				if(bullet.ricochets > bullet.config.maxRicochetCount) {
					bullet.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
					bullet.setDead();
				}
				
				switch(mop.sideHit) {
				case 0: case 1: bullet.motionY *= -1; break;
				case 2: case 3: bullet.motionZ *= -1; break;
				case 4: case 5: bullet.motionX *= -1; break;
				}
				bullet.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
				//send a teleport so the ricochet is more accurate instead of the interp smoothing fucking everything up
				if(bullet.worldObj instanceof WorldServer) TrackerUtil.sendTeleport((WorldServer) bullet.worldObj, bullet);
				return;

			} else {
				bullet.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
				bullet.setDead();
			}
		}
	};
	
	public static void spawnPulse(World world, MovingObjectPosition mop, float yaw, float pitch) {

		double x = mop.hitVec.xCoord;
		double y = mop.hitVec.yCoord;
		double z = mop.hitVec.zCoord;
		
		if(mop.typeOfHit == mop.typeOfHit.BLOCK) {
			if(mop.sideHit == ForgeDirection.UP.ordinal()) { yaw = 0F; pitch = 0F; }
			if(mop.sideHit == ForgeDirection.DOWN.ordinal()) { yaw = 0F; pitch = 0F; }
			if(mop.sideHit == ForgeDirection.NORTH.ordinal()) { yaw = 0F; pitch = 90F; }
			if(mop.sideHit == ForgeDirection.SOUTH.ordinal()) { yaw = 180F; pitch = 90F; }
			if(mop.sideHit == ForgeDirection.EAST.ordinal()) { yaw = 90F; pitch = 90F; }
			if(mop.sideHit == ForgeDirection.WEST.ordinal()) { yaw = 270F; pitch = 90F; }

			ForgeDirection dir = ForgeDirection.getOrientation(mop.sideHit);

			x += dir.offsetX * 0.05;
			y += dir.offsetY * 0.05;
			z += dir.offsetZ * 0.05;
		}

		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "plasmablast");
		data.setFloat("r", 0.5F);
		data.setFloat("g", 0.5F);
		data.setFloat("b", 1.0F);
		data.setFloat("pitch", pitch);
		data.setFloat("yaw", yaw);
		data.setFloat("scale", 0.75F);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z), new TargetPoint(world.provider.dimensionId, x, y, z, 100));
	}
	
	public static void init() {

		float buckshotSpread = 0.035F;
		float magnumSpread = 0.015F;
		g12_bp = new BulletConfig().setItem(EnumAmmo.G12_BP).setCasing(EnumCasingType.SHOTSHELL, 12).setBlackPowder(true).setProjectiles(8).setDamage(0.75F/8F).setSpread(buckshotSpread).setRicochetAngle(15).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(SpentCasing.COLOR_CASE_BRASS, SpentCasing.COLOR_CASE_BRASS).setScale(0.75F).register("12GA_BP"));
		g12_bp_magnum = new BulletConfig().setItem(EnumAmmo.G12_BP_MAGNUM).setCasing(EnumCasingType.SHOTSHELL, 12).setBlackPowder(true).setProjectiles(4).setDamage(0.75F/4F).setSpread(buckshotSpread).setRicochetAngle(25).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(SpentCasing.COLOR_CASE_BRASS, SpentCasing.COLOR_CASE_BRASS).setScale(0.75F).register("12GA_BP_MAGNUM"));
		g12_bp_slug = new BulletConfig().setItem(EnumAmmo.G12_BP_SLUG).setCasing(EnumCasingType.SHOTSHELL, 12).setBlackPowder(true).setDamage(0.75F).setSpread(0.01F).setRicochetAngle(5).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(SpentCasing.COLOR_CASE_BRASS, SpentCasing.COLOR_CASE_BRASS).setScale(0.75F).register("12GA_BP_SLUG"));
		g12 = new BulletConfig().setItem(EnumAmmo.G12).setCasing(EnumCasingType.BUCKSHOT, 6).setProjectiles(8).setDamage(1F/8F).setSpread(buckshotSpread).setRicochetAngle(15).setThresholdNegation(2F).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0xB52B2B, SpentCasing.COLOR_CASE_BRASS).setScale(0.75F).register("12GA"));
		g12_slug = new BulletConfig().setItem(EnumAmmo.G12_SLUG).setCasing(EnumCasingType.BUCKSHOT, 6).setHeadshot(1.5F).setSpread(0.0F).setRicochetAngle(25).setThresholdNegation(4F).setArmorPiercing(0.15F).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0x393939, SpentCasing.COLOR_CASE_BRASS).setScale(0.75F).register("12GA_SLUG"));
		g12_flechette = new BulletConfig().setItem(EnumAmmo.G12_FLECHETTE).setCasing(EnumCasingType.BUCKSHOT, 6).setProjectiles(8).setDamage(1F/8F).setThresholdNegation(5F).setThresholdNegation(3F).setArmorPiercing(0.2F).setSpread(0.025F).setRicochetAngle(5).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0x3C80F0, SpentCasing.COLOR_CASE_BRASS).setScale(0.75F).register("12GA_FLECHETTE"));
		g12_magnum = new BulletConfig().setItem(EnumAmmo.G12_MAGNUM).setCasing(EnumCasingType.BUCKSHOT_ADVANCED, 6).setProjectiles(4).setDamage(2F/4F).setSpread(magnumSpread).setRicochetAngle(15).setThresholdNegation(4F).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0x278400, SpentCasing.COLOR_CASE_12GA).setScale(0.75F).register("12GA_MAGNUM"));
		g12_explosive = new BulletConfig().setItem(EnumAmmo.G12_EXPLOSIVE).setCasing(EnumCasingType.BUCKSHOT_ADVANCED, 6).setDamage(2.5F).setOnImpact(LAMBDA_STANDARD_EXPLODE).setSpread(0F).setRicochetAngle(15).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0xDA4127, SpentCasing.COLOR_CASE_12GA).setScale(0.75F).register("12GA_EXPLOSIVE"));
		g12_phosphorus = new BulletConfig().setItem(EnumAmmo.G12_PHOSPHORUS).setCasing(EnumCasingType.BUCKSHOT_ADVANCED, 6).setProjectiles(8).setDamage(1F/8F).setSpread(magnumSpread).setRicochetAngle(15).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0x910001, SpentCasing.COLOR_CASE_12GA).setScale(0.75F).register("12GA_PHOSPHORUS"))
				.setOnImpact((bullet, mop) -> { if(mop.entityHit != null && mop.entityHit instanceof EntityLivingBase) { HbmLivingProps data = HbmLivingProps.getData((EntityLivingBase) mop.entityHit); if(data.phosphorus < 300) data.phosphorus = 300; } });
		//g12_anthrax = new BulletConfig().setItem(EnumAmmo.G12_ANTHRAX).setProjectiles(8).setDamage(1F/8F).setSpread(0.015F).setRicochetAngle(15).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(0x749300, SpentCasing.COLOR_CASE_12GA).setScale(0.75F).register("12GA_ANTHRAX"));
		g12_equestrian_bj = new BulletConfig().setItem(EnumAmmoSecret.G12_EQUESTRIAN).setDamage(0F).setOnImpact(LAMBDA_BOAT).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(SpentCasing.COLOR_CASE_EQUESTRIAN, SpentCasing.COLOR_CASE_12GA).setScale(0.75F).register("12gaEquestrianBJ"));
		g12_equestrian_tkr = new BulletConfig().setItem(EnumAmmoSecret.G12_EQUESTRIAN).setDamage(0F).setCasing(new SpentCasing(CasingType.SHOTGUN).setColor(SpentCasing.COLOR_CASE_EQUESTRIAN, SpentCasing.COLOR_CASE_12GA).setScale(0.75F).register("12gaEquestrianTKR"));

		BulletConfig[] all = new BulletConfig[] {g12_bp, g12_bp_magnum, g12_bp_slug, g12, g12_slug, g12_flechette, g12_magnum, g12_explosive, g12_phosphorus};

		g12_sub =					makeShredderSubmunition(g12);
		g12_sub_slug =				makeShredderSubmunition(g12_slug);
		g12_sub_flechette =			makeShredderSubmunition(g12_flechette);
		g12_sub_magnum =			makeShredderSubmunition(g12_magnum);
		g12_sub_explosive =			makeShredderSubmunition(g12_explosive);
		g12_sub_phosphorus =		makeShredderSubmunition(g12_phosphorus);
		g12_shredder =				makeShredderConfig(g12, g12_sub);
		g12_shredder_slug =			makeShredderConfig(g12_slug, g12_sub_slug);
		g12_shredder_flechette =	makeShredderConfig(g12_flechette, g12_sub_flechette);
		g12_shredder_magnum =		makeShredderConfig(g12_magnum, g12_sub_magnum);
		g12_shredder_explosive =	makeShredderConfig(g12_explosive, g12_sub_explosive);
		g12_shredder_phosphorus =	makeShredderConfig(g12_phosphorus, g12_sub_phosphorus);
		
		ModItems.gun_maresleg = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(600).draw(10).inspect(39).reloadSequential(true).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(16F).delay(20).reload(22, 10, 13, 0).jam(24).sound("hbm:weapon.fire.shotgun", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 6).addConfigs(all))
						.offset(0.75, -0.0625, -0.1875)
						.setupStandardFire().recoil(LAMBDA_RECOIL_MARESLEG))
				.setupStandardConfiguration()
				.anim(LAMBDA_MARESLEG_ANIMS).orchestra(Orchestras.ORCHESTRA_MARESLEG)
				).setNameMutator(LAMBDA_NAME_MARESLEG)
				.setUnlocalizedName("gun_maresleg");
		ModItems.gun_maresleg_akimbo = new ItemGunBaseNT(WeaponQuality.B_SIDE,
				new GunConfig().dura(600).draw(5).inspect(39).reloadSequential(true).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(16F).spreadHipfire(0F).spreadAmmo(1.35F).delay(20).reload(22, 10, 13, 0).jam(24).sound("hbm:weapon.fire.shotgun", 1.0F, 1.0F)
						.mag(new MagazineSingleReload(0, 6).addConfigs(all))
						.offset(0.75, -0.0625, 0.1875D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_MARESLEG))
				.pp(Lego.LAMBDA_STANDARD_CLICK_PRIMARY).pr(Lego.LAMBDA_STANDARD_RELOAD)
				.decider(GunStateDecider.LAMBDA_STANDARD_DECIDER)
				.anim(LAMBDA_MARESLEG_SHORT_ANIMS).orchestra(Orchestras.ORCHESTRA_MARESLEG_AKIMBO),
				new GunConfig().dura(600).draw(5).inspect(39).reloadSequential(true).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(16F).spreadHipfire(0F).spreadAmmo(1.35F).delay(20).reload(22, 10, 13, 0).jam(24).sound("hbm:weapon.fire.shotgun", 1.0F, 1.0F)
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
						.dmg(32F).spreadAmmo(1.15F).delay(20).reload(22, 10, 13, 0).jam(24).sound("hbm:weapon.fire.shotgun", 1.0F, 1.0F)
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
				.dura(600).draw(20).inspect(39).reloadSequential(true).reloadChangeType(true).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(32F).spreadHipfire(0F).delay(20).reload(5, 10, 10, 10, 0).jam(36).sound("hbm:weapon.shotgunShoot", 1.0F, 1.0F)
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
		ModItems.gun_autoshotgun_shredder = new ItemGunBaseNT(WeaponQuality.B_SIDE, new GunConfig()
				.dura(2_000).draw(10).inspect(33).reloadSequential(true).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(50F).delay(10).auto(true).autoAfterDry(true).dryfireAfterAuto(true).reload(44).jam(19).sound("hbm:weapon.fire.shotgunAuto", 1.0F, 1.0F)
						.mag(new MagazineBelt().addConfigs(g12_shredder, g12_shredder_slug, g12_shredder_flechette, g12_shredder_magnum, g12_shredder_explosive, g12_shredder_phosphorus))
						.offset(0.75, -0.125, -0.25)
						.setupStandardFire().recoil(LAMBDA_RECOIL_AUTOSHOTGUN))
				.setupStandardConfiguration()
				.anim(LAMBDA_SHREDDER_ANIMS).orchestra(Orchestras.ORCHESTRA_SHREDDER)
				).setUnlocalizedName("gun_autoshotgun_shredder");
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
	
	public static Function<ItemStack, String> LAMBDA_NAME_MARESLEG = (stack) -> {
		if(WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_SAWED_OFF)) return stack.getUnlocalizedName() + "_short";
		return null;
	};
	
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
				ItemGunBaseNT.setTimer(stack, index, 20);
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
