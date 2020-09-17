package com.hbm.handler.guncfg;

import java.util.List;
import java.util.Random;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.interfaces.IBulletUpdateBehavior;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

public class GunNPCFactory {

	public static BulletConfiguration getMaskmanOrb() {
		
		BulletConfiguration bullet = new BulletConfiguration();

		bullet.ammo = ModItems.coin_maskman;
		bullet.velocity = 0.25F;
		bullet.spread = 0.000F;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.dmgMin = 100;
		bullet.dmgMax = 100;
		bullet.gravity = 0.0D;
		bullet.maxAge = 60;
		bullet.doesRicochet = false;
		bullet.ricochetAngle = 0;
		bullet.HBRC = 0;
		bullet.LBRC = 0;
		bullet.bounceMod = 1.0;
		bullet.doesPenetrate = false;
		bullet.doesBreakGlass = false;
		bullet.style = BulletConfiguration.STYLE_ORB;
		bullet.trail = 1;
		bullet.explosive = 1.5F;
		
		bullet.bUpdate = new IBulletUpdateBehavior() {

			@Override
			public void behaveUpdate(EntityBulletBase bullet) {
				
				if(bullet.worldObj.isRemote)
					return;
				
				if(bullet.ticksExisted % 10 != 5)
					return;
				
				List<EntityPlayer> players = bullet.worldObj.getEntitiesWithinAABB(EntityPlayer.class, bullet.boundingBox.expand(50, 50, 50));
				
				for(EntityPlayer player : players) {
					
					Vec3 motion = Vec3.createVectorHelper(player.posX - bullet.posX, (player.posY + player.getEyeHeight()) - bullet.posY, player.posZ - bullet.posZ);
					motion = motion.normalize();
					
					EntityBulletBase bolt = new EntityBulletBase(bullet.worldObj, BulletConfigSyncingUtil.MASKMAN_BOLT);
					bolt.shooter = bullet.shooter;
					bolt.setPosition(bullet.posX, bullet.posY, bullet.posZ);
					bolt.setThrowableHeading(motion.xCoord, motion.yCoord, motion.zCoord, 0.5F, 0.05F);
					bullet.worldObj.spawnEntityInWorld(bolt);
				}
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getMaskmanBolt() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.coin_maskman;
		bullet.spread = 0.0F;
		bullet.dmgMin = 15;
		bullet.dmgMax = 20;
		bullet.wear = 10;
		bullet.leadChance = 0;
		bullet.explosive = 0.5F;
		bullet.setToBolt(BulletConfiguration.BOLT_LACUNAE);
		bullet.vPFX = "reddust";
		
		return bullet;
	}
	
	public static BulletConfiguration getMaskmanBullet() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.coin_maskman;
		bullet.spread = 0.0F;
		bullet.dmgMin = 5;
		bullet.dmgMax = 10;
		bullet.wear = 10;
		bullet.leadChance = 15;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;
		bullet.vPFX = "bluedust";
		
		return bullet;
	}
	
	public static BulletConfiguration getMaskmanTracer() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.coin_maskman;
		bullet.spread = 0.0F;
		bullet.dmgMin = 15;
		bullet.dmgMax = 20;
		bullet.wear = 10;
		bullet.leadChance = 0;
		bullet.setToBolt(BulletConfiguration.BOLT_NIGHTMARE);
		bullet.vPFX = "reddust";
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
				if(bullet.worldObj.isRemote)
					return;
				
				EntityBulletBase meteor = new EntityBulletBase(bullet.worldObj, BulletConfigSyncingUtil.MASKMAN_METEOR);
				meteor.setPosition(bullet.posX, bullet.posY + 30 + meteor.worldObj.rand.nextInt(10), bullet.posZ);
				meteor.motionY = -1D;
				meteor.shooter = bullet.shooter;
				bullet.worldObj.spawnEntityInWorld(meteor);
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getMaskmanRocket() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = ModItems.coin_maskman;
		bullet.gravity = 0.1D;
		bullet.velocity = 1.0F;
		bullet.dmgMin = 15;
		bullet.dmgMax = 20;
		bullet.blockDamage = false;
		bullet.explosive = 5.0F;
		bullet.style = BulletConfiguration.STYLE_ROCKET;
		
		return bullet;
	}
	
	public static BulletConfiguration getMaskmanMeteor() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = ModItems.coin_maskman;
		bullet.gravity = 0.1D;
		bullet.velocity = 1.0F;
		bullet.dmgMin = 20;
		bullet.dmgMax = 30;
		bullet.incendiary = 3;
		bullet.explosive = 2.5F;
		bullet.style = BulletConfiguration.STYLE_METEOR;
		
		bullet.bUpdate = new IBulletUpdateBehavior() {

			@Override
			public void behaveUpdate(EntityBulletBase bullet) {
				
				if(!bullet.worldObj.isRemote)
					return;
				
				Random rand = bullet.worldObj.rand;
				
				for(int i = 0; i < 5; i++) {
					NBTTagCompound nbt = new NBTTagCompound();
					nbt.setString("type", "vanillaExt");
					nbt.setString("mode", "flame");
					nbt.setDouble("posX", bullet.posX + rand.nextDouble() * 0.5 - 0.25);
					nbt.setDouble("posY", bullet.posY + rand.nextDouble() * 0.5 - 0.25);
					nbt.setDouble("posZ", bullet.posZ + rand.nextDouble() * 0.5 - 0.25);
					MainRegistry.proxy.effectNT(nbt);
				}
			}
		};
		
		return bullet;
	}

}
