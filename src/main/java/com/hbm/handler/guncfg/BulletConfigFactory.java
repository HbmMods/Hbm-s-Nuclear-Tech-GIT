package com.hbm.handler.guncfg;

import java.util.List;

import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.entity.projectile.EntityBulletBaseNT.*;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.explosion.ExplosionNukeSmall.MukeParams;
import com.hbm.handler.BulletConfiguration;
import com.hbm.lib.Library;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.BobMathUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class BulletConfigFactory {
	
	public static final float defaultSpread = 0.005F;
	
	/// STANDARD CONFIGS ///
	//do not include damage or ammo
	public static BulletConfiguration standardBulletConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 5.0F;
		bullet.spread = defaultSpread;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 5;
		bullet.HBRC = 2;
		bullet.LBRC = 95;
		bullet.bounceMod = 0.8;
		bullet.doesPenetrate = true;
		bullet.doesBreakGlass = true;
		bullet.destroysBlocks = false;
		bullet.style = BulletConfiguration.STYLE_NORMAL;
		bullet.plink = BulletConfiguration.PLINK_BULLET;
		bullet.leadChance = 5;
		
		return bullet;
	}
	public static BulletConfiguration standardPistolConfig() {
		BulletConfiguration bullet = standardBulletConfig();
		bullet.style = BulletConfiguration.STYLE_PISTOL;
		bullet.plink = BulletConfiguration.PLINK_BULLET;
		return bullet;
	}
	
	public static BulletConfiguration standardBuckshotConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 5.0F;
		bullet.spread = defaultSpread * 10F;
		bullet.wear = 10;
		bullet.bulletsMin = 6;
		bullet.bulletsMax = 8;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 5;
		bullet.HBRC = 10;
		bullet.LBRC = 95;
		bullet.bounceMod = 0.8;
		bullet.doesPenetrate = false;
		bullet.doesBreakGlass = true;
		bullet.style = BulletConfiguration.STYLE_PELLET;
		bullet.plink = BulletConfiguration.PLINK_BULLET;
		bullet.leadChance = 10;
		
		return bullet;
	}
	
	public static BulletConfiguration standardRocketConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 2.0F;
		bullet.spread = defaultSpread;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0.005D;
		bullet.maxAge = 300;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 10;
		bullet.HBRC = 2;
		bullet.LBRC = 100;
		bullet.bounceMod = 0.8;
		bullet.doesPenetrate = false;
		bullet.doesBreakGlass = false;
		bullet.explosive = 5.0F;
		bullet.style = BulletConfiguration.STYLE_ROCKET;
		bullet.plink = BulletConfiguration.PLINK_GRENADE;
		bullet.vPFX = "smoke";
		
		return bullet;
	}
	
	public static BulletConfiguration standardGrenadeConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 2.0F;
		bullet.spread = defaultSpread;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0.035D;
		bullet.maxAge = 300;
		bullet.doesRicochet = false;
		bullet.ricochetAngle = 0;
		bullet.HBRC = 0;
		bullet.LBRC = 0;
		bullet.bounceMod = 1.0;
		bullet.doesPenetrate = false;
		bullet.doesBreakGlass = false;
		bullet.explosive = 2.5F;
		bullet.style = BulletConfiguration.STYLE_GRENADE;
		bullet.plink = BulletConfiguration.PLINK_GRENADE;
		bullet.vPFX = "smoke";
		
		return bullet;
	}
	
	public static BulletConfiguration standardShellConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 3.0F;
		bullet.spread = defaultSpread;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0.005D;
		bullet.maxAge = 300;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 10;
		bullet.HBRC = 2;
		bullet.LBRC = 100;
		bullet.bounceMod = 0.8;
		bullet.doesPenetrate = false;
		bullet.doesBreakGlass = false;
		bullet.style = BulletConfiguration.STYLE_GRENADE;
		bullet.plink = BulletConfiguration.PLINK_GRENADE;
		bullet.vPFX = "smoke";
		
		return bullet;
	}
	
	public static BulletConfiguration standardNukeConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 3.0F;
		bullet.spread = defaultSpread;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.dmgMin = 1000;
		bullet.dmgMax = 1000;
		bullet.gravity = 0.025D;
		bullet.maxAge = 300;
		bullet.doesRicochet = false;
		bullet.ricochetAngle = 0;
		bullet.HBRC = 0;
		bullet.LBRC = 0;
		bullet.bounceMod = 1.0;
		bullet.doesPenetrate = true;
		bullet.doesBreakGlass = false;
		bullet.style = BulletConfiguration.STYLE_NUKE;
		bullet.plink = BulletConfiguration.PLINK_GRENADE;
		
		return bullet;
	}
	
	/*
	 * Sizes:
	 * 0 - safe
	 * 1 - tot
	 * 2 - small
	 * 3 - medium
	 * 4 - big
	 */
	public static void nuclearExplosion(Entity entity, int x, int y, int z, MukeParams params) {
		
		if(!entity.worldObj.isRemote) {

			double posX = entity.posX;
			double posY = entity.posY + 0.5;
			double posZ = entity.posZ;
			
			if(y >= 0) {
				posX = x + 0.5;
				posY = y + 1.5;
				posZ = z + 0.5;
			}
			
			ExplosionNukeSmall.explode(entity.worldObj, posX, posY, posZ, params);
		}
	}
	
	public static void makeFlechette(BulletConfiguration bullet) {

		bullet.bntImpact = (bulletnt, x, y, z, sideHit) -> {
			bulletnt.getStuck(x, y, z, sideHit);
		};
	}
	
	public static IBulletImpactBehaviorNT getPhosphorousEffect(final int radius, final int duration, final int count, final double motion, float hazeChance) {
		
		IBulletImpactBehaviorNT impact = new IBulletImpactBehaviorNT() {

			@Override
			public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit) {
				
				List<Entity> hit = bullet.worldObj.getEntitiesWithinAABBExcludingEntity(bullet, AxisAlignedBB.getBoundingBox(bullet.posX - radius, bullet.posY - radius, bullet.posZ - radius, bullet.posX + radius, bullet.posY + radius, bullet.posZ + radius));
				
				for(Entity e : hit) {
					
					if(!Library.isObstructed(bullet.worldObj, bullet.posX, bullet.posY, bullet.posZ, e.posX, e.posY + e.getEyeHeight(), e.posZ)) {
						e.setFire(5);
						
						if(e instanceof EntityLivingBase) {
							
							PotionEffect eff = new PotionEffect(HbmPotion.phosphorus.id, duration, 0, true);
							eff.getCurativeItems().clear();
							((EntityLivingBase)e).addPotionEffect(eff);
						}
					}
				}
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaburst");
				data.setString("mode", "flame");
				data.setInteger("count", count);
				data.setDouble("motion", motion);
				
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, bullet.posX, bullet.posY, bullet.posZ), new TargetPoint(bullet.dimension, bullet.posX, bullet.posY, bullet.posZ, 50));
				
				if(bullet.worldObj.rand.nextFloat() < hazeChance) {
				NBTTagCompound haze = new NBTTagCompound();
				haze.setString("type", "haze");
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(haze, bullet.posX, bullet.posY, bullet.posZ), new TargetPoint(bullet.dimension, bullet.posX, bullet.posY, bullet.posZ, 150));
				}
			}
		};
		
		return impact;
	}
	
	public static IBulletImpactBehaviorNT getGasEffect(final int radius, final int duration) {
		
		IBulletImpactBehaviorNT impact = new IBulletImpactBehaviorNT() {

			@Override
			public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit) {
				
				List<Entity> hit = bullet.worldObj.getEntitiesWithinAABBExcludingEntity(bullet, AxisAlignedBB.getBoundingBox(bullet.posX - radius, bullet.posY - radius, bullet.posZ - radius, bullet.posX + radius, bullet.posY + radius, bullet.posZ + radius));
				
				for(Entity e : hit) {
					
					if(!Library.isObstructed(bullet.worldObj, bullet.posX, bullet.posY, bullet.posZ, e.posX, e.posY + e.getEyeHeight(), e.posZ)) {
						
						if(e instanceof EntityLivingBase) {
							
							EntityLivingBase entity = (EntityLivingBase) e;
							
							if(ArmorRegistry.hasAllProtection(entity, 3, HazardClass.GAS_LUNG))
								continue;

							PotionEffect eff0 = new PotionEffect(Potion.poison.id, duration, 2, true);
							PotionEffect eff1 = new PotionEffect(Potion.digSlowdown.id, duration, 2, true);
							PotionEffect eff2 = new PotionEffect(Potion.weakness.id, duration, 4, true);
							PotionEffect eff3 = new PotionEffect(Potion.wither.id, (int)Math.ceil(duration * 0.1), 0, true);
							eff0.getCurativeItems().clear();
							eff1.getCurativeItems().clear();
							eff2.getCurativeItems().clear();
							eff3.getCurativeItems().clear();
							entity.addPotionEffect(eff0);
							entity.addPotionEffect(eff1);
							entity.addPotionEffect(eff2);
							entity.addPotionEffect(eff3);
						}
					}
				}
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaburst");
				data.setString("mode", "cloud");
				data.setInteger("count", 15);
				data.setDouble("motion", 0.1D);
				
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, bullet.posX, bullet.posY, bullet.posZ), new TargetPoint(bullet.dimension, bullet.posX, bullet.posY, bullet.posZ, 50));
			}
		};
		
		return impact;
	}
	
	public static IBulletUpdateBehaviorNT getLaserSteering() {
		
		IBulletUpdateBehaviorNT onUpdate = new IBulletUpdateBehaviorNT() {

			@Override
			public void behaveUpdate(EntityBulletBaseNT bullet) {
				
				if(bullet.getThrower() == null || !(bullet.getThrower() instanceof EntityPlayer))
					return;
				
				if(Vec3.createVectorHelper(bullet.posX - bullet.getThrower().posX, bullet.posY - bullet.getThrower().posY, bullet.posZ - bullet.getThrower().posZ).lengthVector() > 100)
					return;
				
				MovingObjectPosition mop = Library.rayTrace((EntityPlayer)bullet.getThrower(), 200, 1);
				
				if(mop == null || mop.hitVec == null)
					return;
				
				Vec3 vec = Vec3.createVectorHelper(mop.hitVec.xCoord - bullet.posX, mop.hitVec.yCoord - bullet.posY, mop.hitVec.zCoord - bullet.posZ);
				
				if(vec.lengthVector() < 3)
					return;
				
				vec = vec.normalize();
				
				double speed = Vec3.createVectorHelper(bullet.motionX, bullet.motionY, bullet.motionZ).lengthVector();

				bullet.motionX = vec.xCoord * speed;
				bullet.motionY = vec.yCoord * speed;
				bullet.motionZ = vec.zCoord * speed;
			}
			
		};
		
		return onUpdate;
	}
	
	public static IBulletUpdateBehaviorNT getHomingBehavior(final double range, final double angle) {

		IBulletUpdateBehaviorNT onUpdate = new IBulletUpdateBehaviorNT() {

			@Override
			public void behaveUpdate(EntityBulletBaseNT bullet) {
				
				if(bullet.worldObj.isRemote)
					return;
				
				if(bullet.worldObj.getEntityByID(bullet.getEntityData().getInteger("homingTarget")) == null) {
					chooseTarget(bullet);
				}
				
				Entity target = bullet.worldObj.getEntityByID(bullet.getEntityData().getInteger("homingTarget"));
				
				if(target != null) {
					
					Vec3 delta = Vec3.createVectorHelper(target.posX - bullet.posX, target.posY + target.height / 2 - bullet.posY, target.posZ - bullet.posZ);
					delta = delta.normalize();
					double vel = Vec3.createVectorHelper(bullet.motionX, bullet.motionY, bullet.motionZ).lengthVector();

					bullet.motionX = delta.xCoord * vel;
					bullet.motionY = delta.yCoord * vel;
					bullet.motionZ = delta.zCoord * vel;
				}
			}
			
			private void chooseTarget(EntityBulletBaseNT bullet) {
				
				List<EntityLivingBase> entities = bullet.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, bullet.boundingBox.expand(range, range, range));
				
				Vec3 mot = Vec3.createVectorHelper(bullet.motionX, bullet.motionY, bullet.motionZ);
				
				EntityLivingBase target = null;
				double targetAngle = angle;
				
				for(EntityLivingBase e : entities) {
					
					if(!e.isEntityAlive() || e == bullet.getThrower())
						continue;
					
					Vec3 delta = Vec3.createVectorHelper(e.posX - bullet.posX, e.posY + e.height / 2 - bullet.posY, e.posZ - bullet.posZ);
					
					if(bullet.worldObj.func_147447_a(Vec3.createVectorHelper(bullet.posX, bullet.posY, bullet.posZ), Vec3.createVectorHelper(e.posX, e.posY + e.height / 2, e.posZ), false, true, false) != null)
						continue;
					
					double dist = e.getDistanceSqToEntity(bullet);
					
					if(dist < range * range) {
						
						double deltaAngle = BobMathUtil.getCrossAngle(mot, delta);
					
						if(deltaAngle < targetAngle) {
							//Checks if the bullet is not already inside the entity's bounding box, so it doesn't pick the same target
							if(bullet.getConfig().doesPenetrate && bullet.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, bullet.boundingBox.expand(2, 2, 2)) == null) {
								continue;
							}
							target = e;
							targetAngle = deltaAngle;
						}
					}
				}
				
				if(target != null) {
					bullet.getEntityData().setInteger("homingTarget", target.getEntityId());
				}
			}
		};
		
		return onUpdate;
	}
	/** Resets the bullet's target **/
	public static IBulletHurtBehaviorNT getPenHomingBehavior(){
		return (bullet, hit) -> bullet.getEntityData().setInteger("homingTarget", 0);
	}

}
