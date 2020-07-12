package com.hbm.handler.guncfg;

import java.util.List;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.ArmorUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.interfaces.IBulletUpdateBehavior;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;

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
	
	/// configs should never be loaded manually due to syncing issues: use the syncing util and pass the UID in the DW of the bullet to make the client load the config correctly ////
	
	public static BulletConfiguration getTestConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = ModItems.gun_revolver_ammo;
		bullet.velocity = 5.0F;
		bullet.spread = 0.05F;
		bullet.wear = 10;
		bullet.dmgMin = 15;
		bullet.dmgMax = 17;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 10;
		bullet.HBRC = 2;
		bullet.LBRC = 90;
		bullet.bounceMod = 0.8;
		bullet.doesPenetrate = true;
		bullet.doesBreakGlass = true;
		bullet.style = 0;
		bullet.plink = 1;
		
		return bullet;
		
	}
	
	/// STANDARD CONFIGS ///
	//do not include damage or ammo
	public static BulletConfiguration standardBulletConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 5.0F;
		bullet.spread = 0.005F;
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
	
	public static BulletConfiguration standardBuckshotConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 5.0F;
		bullet.spread = 0.05F;
		bullet.wear = 10;
		bullet.bulletsMin = 5;
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
		bullet.spread = 0.005F;
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
		
		return bullet;
	}
	
	public static BulletConfiguration standardGrenadeConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 2.0F;
		bullet.spread = 0.005F;
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
	
	public static BulletConfiguration standardNukeConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 3.0F;
		bullet.spread = 0.005F;
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
		bullet.nuke = 35;
		bullet.style = BulletConfiguration.STYLE_NUKE;
		bullet.plink = BulletConfiguration.PLINK_GRENADE;
		
		return bullet;
	}
	
	public static IBulletImpactBehavior getPhosphorousEffect(final int radius, final int duration, final int count, final double motion) {
		
		IBulletImpactBehavior impact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
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
			}
		};
		
		return impact;
	}
	
	public static IBulletImpactBehavior getGasEffect(final int radius, final int duration) {
		
		IBulletImpactBehavior impact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
				List<Entity> hit = bullet.worldObj.getEntitiesWithinAABBExcludingEntity(bullet, AxisAlignedBB.getBoundingBox(bullet.posX - radius, bullet.posY - radius, bullet.posZ - radius, bullet.posX + radius, bullet.posY + radius, bullet.posZ + radius));
				
				for(Entity e : hit) {
					
					if(!Library.isObstructed(bullet.worldObj, bullet.posX, bullet.posY, bullet.posZ, e.posX, e.posY + e.getEyeHeight(), e.posZ)) {
						
						if(e instanceof EntityLivingBase) {
							
							if(e instanceof EntityPlayer && ArmorUtil.checkForGasMask((EntityPlayer) e))
								continue;

							PotionEffect eff0 = new PotionEffect(Potion.poison.id, duration, 2, true);
							PotionEffect eff1 = new PotionEffect(Potion.digSlowdown.id, duration, 2, true);
							PotionEffect eff2 = new PotionEffect(Potion.weakness.id, duration, 4, true);
							eff0.getCurativeItems().clear();
							eff1.getCurativeItems().clear();
							eff2.getCurativeItems().clear();
							((EntityLivingBase)e).addPotionEffect(eff0);
							((EntityLivingBase)e).addPotionEffect(eff1);
							((EntityLivingBase)e).addPotionEffect(eff2);
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
	
	public static IBulletUpdateBehavior getLaserSteering() {
		
		IBulletUpdateBehavior onUpdate = new IBulletUpdateBehavior() {

			@Override
			public void behaveUpdate(EntityBulletBase bullet) {
				
				if(bullet.shooter == null || !(bullet.shooter instanceof EntityPlayer))
					return;
				
				if(Vec3.createVectorHelper(bullet.posX - bullet.shooter.posX, bullet.posY - bullet.shooter.posY, bullet.posZ - bullet.shooter.posZ).lengthVector() > 100)
					return;
				
				MovingObjectPosition mop = Library.rayTrace((EntityPlayer)bullet.shooter, 200, 1);
				
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

}
