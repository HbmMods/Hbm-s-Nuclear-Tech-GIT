package com.hbm.entity.projectile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletHitBehavior;
import com.hbm.interfaces.IBulletHurtBehavior;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.interfaces.IBulletRicochetBehavior;
import com.hbm.interfaces.IBulletUpdateBehavior;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.main.MainRegistry;
import com.hbm.util.Tuple.Pair;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBulletBaseNT extends EntityThrowableInterp {
	
	private BulletConfiguration config;
	public float overrideDamage;

	public double prevRenderX;
	public double prevRenderY;
	public double prevRenderZ;
	public final List<Pair<Vec3, Double>> trailNodes = new ArrayList();
	
	public BulletConfiguration getConfig() {
		return config;
	}

	public EntityBulletBaseNT(World world) {
		super(world);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
	}

	public EntityBulletBaseNT(World world, int config) {
		super(world);
		this.config = BulletConfigSyncingUtil.pullConfig(config);
		this.dataWatcher.updateObject(18, config);
		this.renderDistanceWeight = 10.0D;
		
		if(this.config == null) {
			this.setDead();
			return;
		}
		
		this.dataWatcher.updateObject(16, (byte)this.config.style);
		this.dataWatcher.updateObject(17, (byte)this.config.trail);
		
		this.setSize(0.5F, 0.5F);
	}
	
	public EntityBulletBaseNT(World world, int config, EntityLivingBase entity) {
		super(world);
		this.config = BulletConfigSyncingUtil.pullConfig(config);
		this.dataWatcher.updateObject(18, config);
		thrower = entity;
		
		ItemStack gun = entity.getHeldItem();
		boolean offsetShot = true;
		
		if(gun != null && gun.getItem() instanceof ItemGunBase) {
			GunConfiguration cfg = ((ItemGunBase) gun.getItem()).mainConfig;
			
			if(cfg != null && cfg.hasSights && entity.isSneaking()) {
				offsetShot = false;
			}
		}

		this.setLocationAndAngles(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, entity.rotationYaw, entity.rotationPitch);
		
		if(offsetShot) {
			double sideOffset = 0.16D;
			
			this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * sideOffset;
			this.posY -= 0.1D;
			this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * sideOffset;
		} else {
			this.posY -= 0.1D;
		}
		this.setPosition(this.posX, this.posY, this.posZ);
		
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));

		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);

		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 1.0F, this.config.spread * (offsetShot ? 1F : 0.25F));
		
		this.dataWatcher.updateObject(16, (byte)this.config.style);
		this.dataWatcher.updateObject(17, (byte)this.config.trail);
	}

	public EntityBulletBaseNT(World world, int config, EntityLivingBase entity, EntityLivingBase target, float motion, float deviation) {
		super(world);
		
		this.config = BulletConfigSyncingUtil.pullConfig(config);
		this.dataWatcher.updateObject(18, config);
		this.thrower = entity;

		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);

		this.posY = entity.posY + entity.getEyeHeight() - 0.10000000149011612D;
		double d0 = target.posX - entity.posX;
		double d1 = target.boundingBox.minY + target.height / 3.0F - this.posY;
		double d2 = target.posZ - entity.posZ;
		double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);

		if (d3 >= 1.0E-7D) {
			float f2 = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
			float f3 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
			double d4 = d0 / d3;
			double d5 = d2 / d3;
			this.setLocationAndAngles(entity.posX + d4, this.posY, entity.posZ + d5, f2, f3);
			this.yOffset = 0.0F;
			this.setThrowableHeading(d0, d1, d2, motion, deviation);
		}
		
		this.dataWatcher.updateObject(16, (byte)this.config.style);
		this.dataWatcher.updateObject(17, (byte)this.config.trail);
	}
	
	@Override
	public void onUpdate() {
		
		if(config == null) config = BulletConfigSyncingUtil.pullConfig(dataWatcher.getWatchableObjectInt(18));

		if(config == null){
			this.setDead();
			return;
		}
		
		if(worldObj.isRemote && config.style == config.STYLE_TAU) {
			if(trailNodes.isEmpty()) {
				this.ignoreFrustumCheck = true;
				trailNodes.add(new Pair<Vec3, Double>(Vec3.createVectorHelper(-motionX * 2, -motionY * 2, -motionZ * 2), 0D));
			} else {
				trailNodes.add(new Pair<Vec3, Double>(Vec3.createVectorHelper(0, 0, 0), 1D));
			}
		}
		
		if(worldObj.isRemote && this.config.blackPowder && this.ticksExisted == 1) {
			
			for(int i = 0; i < 15; i++) {
				double mod = rand.nextDouble();
				this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ,
						(this.motionX + rand.nextGaussian() * 0.05) * mod,
						(this.motionY + rand.nextGaussian() * 0.05) * mod,
						(this.motionZ + rand.nextGaussian() * 0.05) * mod);
			}
			
			double mod = 0.5;
			this.worldObj.spawnParticle("flame", this.posX + this.motionX * mod, this.posY + this.motionY * mod, this.posZ + this.motionZ * mod, 0, 0, 0);
		}
		
		if(!worldObj.isRemote) {
			
			if(config.maxAge == 0) {
				if(this.config.bUpdate != null) this.config.bntUpdate.behaveUpdate(this);
				this.setDead();
				return;
			}
			
			if(this.config.bUpdate != null) this.config.bntUpdate.behaveUpdate(this);
		}
		
		super.onUpdate();
		
		if(worldObj.isRemote && !config.vPFX.isEmpty()) {
			
			Vec3 vec = Vec3.createVectorHelper(posX - prevPosX, posY - prevPosY, posZ - prevPosZ);
			double motion = Math.max(vec.lengthVector(), 0.1);
			vec = vec.normalize();
			
			for(double d = 0; d < motion; d += 0.5) {
				
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setString("type", "vanillaExt");
				nbt.setString("mode", config.vPFX);
				nbt.setDouble("posX", this.posX - vec.xCoord * d);
				nbt.setDouble("posY", this.posY - vec.yCoord * d);
				nbt.setDouble("posZ", this.posZ - vec.zCoord * d);
				MainRegistry.proxy.effectNT(nbt);
			}
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		
		if(mop.typeOfHit == mop.typeOfHit.BLOCK) {
			
		}
	}

	@Override
	public boolean doesPenetrate() {
		return this.config.doesPenetrate;
	}

	@Override
	public boolean isSpectral() {
		return this.config.isSpectral;
	}
	
	public IBulletHurtBehavior bHurt;
	public IBulletHitBehavior bHit;
	public IBulletRicochetBehavior bRicochet;
	public IBulletImpactBehavior bImpact;
	public IBulletUpdateBehavior bUpdate;
	
	public interface IBulletHurtBehaviorNT { public void behaveEntityHurt(EntityBulletBaseNT bullet, Entity hit); }
	public interface IBulletHitBehaviorNT { public void behaveEntityHit(EntityBulletBaseNT bullet, Entity hit); }
	public interface IBulletRicochetBehaviorNT { public void behaveBlockRicochet(EntityBulletBaseNT bullet, int x, int y, int z); }
	public interface IBulletImpactBehaviorNT { public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z); }
	public interface IBulletUpdateBehaviorNT { public void behaveUpdate(EntityBulletBaseNT bullet); }
}
