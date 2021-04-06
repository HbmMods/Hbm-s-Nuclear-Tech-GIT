package com.hbm.entity.projectile;

import java.lang.reflect.Field;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.RedBarrel;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.entity.particle.EntityTSmokeFX;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ArmorUtil;
import com.hbm.util.BobMathUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBulletBase extends Entity implements IProjectile {
	
	private BulletConfiguration config;
	public EntityLivingBase shooter;
	public float overrideDamage;

	public EntityBulletBase(World world) {
		super(world);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
	}

	public EntityBulletBase(World world, int config) {
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
	
	public EntityBulletBase(World world, int config, EntityLivingBase entity) {
		super(world);
		this.config = BulletConfigSyncingUtil.pullConfig(config);
		this.dataWatcher.updateObject(18, config);
		shooter = entity;

		this.setLocationAndAngles(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, entity.rotationYaw, entity.rotationPitch);
		
		this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		this.setPosition(this.posX, this.posY, this.posZ);
		
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));

		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);

		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 1.0F, this.config.spread);
		
		this.dataWatcher.updateObject(16, (byte)this.config.style);
		this.dataWatcher.updateObject(17, (byte)this.config.trail);
	}

	public EntityBulletBase(World world, int config, EntityLivingBase entity, EntityLivingBase target, float motion, float deviation) {
		super(world);
		
		this.config = BulletConfigSyncingUtil.pullConfig(config);
		this.dataWatcher.updateObject(18, config);
		this.shooter = entity;

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
	public void setThrowableHeading(double moX, double moY, double moZ, float mult1, float mult2) {
		
		float f2 = MathHelper.sqrt_double(moX * moX + moY * moY + moZ * moZ);
		moX /= f2;
		moY /= f2;
		moZ /= f2;
		moX += this.rand.nextGaussian() * mult2;
		moY += this.rand.nextGaussian() * mult2;
		moZ += this.rand.nextGaussian() * mult2;
		moX *= mult1;
		moY *= mult1;
		moZ *= mult1;
		this.motionX = moX;
		this.motionY = moY;
		this.motionZ = moZ;
		
		float f3 = MathHelper.sqrt_double(moX * moX + moZ * moZ);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(moX, moZ) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(moY, f3) * 180.0D / Math.PI);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double x, double y, double z, float r0, float r1, int i) {
		this.setPosition(x, y, z);
		this.setRotation(r0, r1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z) {
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt_double(x * x + z * z);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y, f) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
		}
	}

	@Override
	protected void entityInit() {
		//style
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		//trail
		this.dataWatcher.addObject(17, Byte.valueOf((byte) 0));
		//bullet config sync
		this.dataWatcher.addObject(18, Integer.valueOf((int) 0));
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		
		if(config == null)
			config = BulletConfigSyncingUtil.pullConfig(dataWatcher.getWatchableObjectInt(18));

		if(config == null){
			this.setDead();
			return;
		}
		
		if(config.maxAge == 0) {
			this.setDead();
			return;
		}
		
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
		}

		/// ZONE 1 START ///
		//entity and block collision, plinking
		
		/// ZONE 2 START ///
		//entity detection
        Vec3 vecOrigin = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 vecDestination = Vec3.createVectorHelper(this.posX + this.motionX * this.config.velocity, this.posY + this.motionY * this.config.velocity, this.posZ + this.motionZ * this.config.velocity);
        MovingObjectPosition movement = this.worldObj.func_147447_a(vecOrigin, vecDestination, false, true, false);
        vecOrigin = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        vecDestination = Vec3.createVectorHelper(this.posX + this.motionX * this.config.velocity, this.posY + this.motionY * this.config.velocity, this.posZ + this.motionZ * this.config.velocity);

		Entity victim = null;
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX * this.config.velocity, this.motionY * this.config.velocity, this.motionZ * this.config.velocity).expand(1.0D, 1.0D, 1.0D));
		
		double d0 = 0.0D;
		int i;
		float f1;

		for (i = 0; i < list.size(); ++i) {
			Entity entity1 = (Entity) list.get(i);

			if (entity1.canBeCollidedWith() && (entity1 != this.shooter)) {
				f1 = 0.3F;
				AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(f1, f1, f1);
				MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vecOrigin, vecDestination);

				if (movingobjectposition1 != null) {
					double d1 = vecOrigin.distanceTo(movingobjectposition1.hitVec);

					if (d1 < d0 || d0 == 0.0D) {
						victim = entity1;
						d0 = d1;
					}
				}
			}
		}

		if (victim != null) {
			movement = new MovingObjectPosition(victim);
		}
		
		/// ZONE 2 END ///

		boolean didBounce = false;
		
        if (movement != null) {
        	
        	//handle entity collision
        	if(movement.entityHit != null) {

				DamageSource damagesource = null;
				
				if (this.shooter == null) {
					damagesource = ModDamageSource.causeBulletDamage(this, this);
				} else {
					damagesource = ModDamageSource.causeBulletDamage(this, shooter);
				}

    			if(!worldObj.isRemote) {
	        		if(!config.doesPenetrate)
	        			onEntityImpact(victim);
	        		else
	        			onEntityHurt(victim);
    			}
				
				float damage = rand.nextFloat() * (config.dmgMax - config.dmgMin) + config.dmgMin;
				
				if(overrideDamage != 0)
					damage = overrideDamage;
				
        		if(!victim.attackEntityFrom(damagesource, damage)) {

					try {
						Field lastDamage = ReflectionHelper.findField(EntityLivingBase.class, "lastDamage", "field_110153_bc");
						
						float dmg = (float) damage + lastDamage.getFloat(victim);
						
						victim.attackEntityFrom(damagesource, dmg);
					} catch (Exception x) { }
        		}
        		
        	//handle block collision
        	} else if(worldObj.getBlock(movement.blockX, movement.blockY, movement.blockZ).getMaterial() != Material.air) {
        		
        		boolean hRic = rand.nextInt(100) < config.HBRC;
        		boolean doesRic = config.doesRicochet || hRic;
        		
        		if(!config.isSpectral && !doesRic)
        			this.onBlockImpact(movement.blockX, movement.blockY, movement.blockZ);
        		
        		if(doesRic) {
        			
        			Vec3 face = null;
                	
                	switch(movement.sideHit) {
                	case 0:
                		face = Vec3.createVectorHelper(0, -1, 0); break;
                	case 1:
                		face = Vec3.createVectorHelper(0, 1, 0); break;
                	case 2:
                		face = Vec3.createVectorHelper(0, 0, 1); break;
                	case 3:
                		face = Vec3.createVectorHelper(0, 0, -1); break;
                	case 4:
                		face = Vec3.createVectorHelper(-1, 0, 0); break;
                	case 5:
                		face = Vec3.createVectorHelper(1, 0, 0); break;
                	}
                	
                	if(face != null) {
                		
                		Vec3 vel = Vec3.createVectorHelper(motionX, motionY, motionZ);
                		vel.normalize();

                		boolean lRic = rand.nextInt(100) < config.LBRC;
                		double angle = Math.abs(BobMathUtil.getCrossAngle(vel, face) - 90);
                		
                		if(hRic || (angle <= config.ricochetAngle && lRic)) {
                        	switch(movement.sideHit) {
                        	case 0:
                        	case 1:
                        		motionY *= -1; break;
                        	case 2:
                        	case 3:
                        		motionZ *= -1; break;
                        	case 4:
                        	case 5:
                        		motionX *= -1; break;
                        	}

                        	if(config.plink == 1)
                        		worldObj.playSoundAtEntity(this, "hbm:weapon.ricochet", 0.25F, 1.0F);
                        	if(config.plink == 2)
                        		worldObj.playSoundAtEntity(this, "hbm:weapon.gBounce", 1.0F, 1.0F);
                        	
                        	onRicochet(movement.blockX, movement.blockY, movement.blockZ);
                        	
                		} else {
                			if(!worldObj.isRemote)
                				onBlockImpact(movement.blockX, movement.blockY, movement.blockZ);
                		}

                        this.posX += (movement.hitVec.xCoord - this.posX) * 0.6;
                        this.posY += (movement.hitVec.yCoord - this.posY) * 0.6;
                        this.posZ += (movement.hitVec.zCoord - this.posZ) * 0.6;

                        this.motionX *= config.bounceMod;
                        this.motionY *= config.bounceMod;
                        this.motionZ *= config.bounceMod;
                        
                		didBounce = true;
                	}
        		}
        	}
        	
        }
		
		/// ZONE 1 END ///

        if(!didBounce) {
        	motionY -= config.gravity;
			this.posX += this.motionX * this.config.velocity;
			this.posY += this.motionY * this.config.velocity;
			this.posZ += this.motionZ * this.config.velocity;
			this.setPosition(this.posX, this.posY, this.posZ);
        }
        
        /// SPECIAL UPDATE BEHAVIOR ///
        if(this.config.bUpdate != null)
        	this.config.bUpdate.behaveUpdate(this);

		float f2;
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
		f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
		{
			;
		}

		while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}
		
		while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}
		
		while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}
		
		if(this.ticksExisted > config.maxAge)
			this.setDead();
		
		if(worldObj.isRemote && !config.vPFX.isEmpty()) {
			
			double motion = Math.min(Vec3.createVectorHelper(motionX, motionY, motionZ).lengthVector(), 0.1);
			
			for(double d = 0; d < motion; d += 0.0625) {
				
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setString("type", "vanillaExt");
				nbt.setString("mode", config.vPFX);
				nbt.setDouble("posX", (this.lastTickPosX - this.posX) * d + this.posX);
				nbt.setDouble("posY", (this.lastTickPosY - this.posY) * d + this.posY);
				nbt.setDouble("posZ", (this.lastTickPosZ - this.posZ) * d + this.posZ);
				MainRegistry.proxy.effectNT(nbt);
			}
		}

		//this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		//this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
	}
	
	//for when a bullet dies by hitting a block
	private void onBlockImpact(int bX, int bY, int bZ) {
		
		if(config.bImpact != null)
			config.bImpact.behaveBlockHit(this, bX, bY, bZ);
		
		if(!worldObj.isRemote && !config.liveAfterImpact)
			this.setDead();
		
		if(config.incendiary > 0 && !this.worldObj.isRemote) {
			if(worldObj.rand.nextInt(3) == 0 && worldObj.getBlock((int)posX, (int)posY, (int)posZ) == Blocks.air) worldObj.setBlock((int)posX, (int)posY, (int)posZ, Blocks.fire);
			if(worldObj.rand.nextInt(3) == 0 && worldObj.getBlock((int)posX + 1, (int)posY, (int)posZ) == Blocks.air) worldObj.setBlock((int)posX + 1, (int)posY, (int)posZ, Blocks.fire);
			if(worldObj.rand.nextInt(3) == 0 && worldObj.getBlock((int)posX - 1, (int)posY, (int)posZ) == Blocks.air) worldObj.setBlock((int)posX - 1, (int)posY, (int)posZ, Blocks.fire);
			if(worldObj.rand.nextInt(3) == 0 && worldObj.getBlock((int)posX, (int)posY + 1, (int)posZ) == Blocks.air) worldObj.setBlock((int)posX, (int)posY + 1, (int)posZ, Blocks.fire);
			if(worldObj.rand.nextInt(3) == 0 && worldObj.getBlock((int)posX, (int)posY - 1, (int)posZ) == Blocks.air) worldObj.setBlock((int)posX, (int)posY - 1, (int)posZ, Blocks.fire);
			if(worldObj.rand.nextInt(3) == 0 && worldObj.getBlock((int)posX, (int)posY, (int)posZ + 1) == Blocks.air) worldObj.setBlock((int)posX, (int)posY, (int)posZ + 1, Blocks.fire);
			if(worldObj.rand.nextInt(3) == 0 && worldObj.getBlock((int)posX, (int)posY, (int)posZ - 1) == Blocks.air) worldObj.setBlock((int)posX, (int)posY, (int)posZ - 1, Blocks.fire);
		}

		if(config.emp > 0)
			ExplosionNukeGeneric.empBlast(this.worldObj, (int)(this.posX + 0.5D), (int)(this.posY + 0.5D), (int)(this.posZ + 0.5D), config.emp);
		
		if(config.emp > 3) {
			if (!this.worldObj.isRemote) {
				
	    		EntityEMPBlast cloud = new EntityEMPBlast(this.worldObj, config.emp);
	    		cloud.posX = this.posX;
	    		cloud.posY = this.posY + 0.5F;
	    		cloud.posZ = this.posZ;
	    		
				this.worldObj.spawnEntityInWorld(cloud);
			}
		}
		
		if(config.jolt > 0 && !worldObj.isRemote)
    		ExplosionLarge.jolt(worldObj, posX, posY, posZ, config.jolt, 150, 0.25);
		
		if(config.explosive > 0 && !worldObj.isRemote)
			worldObj.newExplosion(this, posX, posY, posZ, config.explosive, config.incendiary > 0, config.blockDamage);
		
		if(config.shrapnel > 0 && !worldObj.isRemote)
			ExplosionLarge.spawnShrapnels(worldObj, posX, posY, posZ, config.shrapnel);
		
		if(config.chlorine > 0 && !worldObj.isRemote) {
			ExplosionChaos.spawnChlorine(worldObj, posX, posY, posZ, config.chlorine, 1.5, 0);
        	worldObj.playSoundEffect((double)(posX + 0.5F), (double)(posY + 0.5F), (double)(posZ + 0.5F), "random.fizz", 5.0F, 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F);
		}
		
		if(config.rainbow > 0 && !worldObj.isRemote) {
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 100.0f, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);

			EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(this.worldObj);
			entity.posX = this.posX;
			entity.posY = this.posY;
			entity.posZ = this.posZ;
			entity.destructionRange = config.rainbow;
			entity.speed = 25;
			entity.coefficient = 1.0F;
			entity.waste = false;

			this.worldObj.spawnEntityInWorld(entity);
	    		
	    	EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(this.worldObj, config.rainbow);
	    	cloud.posX = this.posX;
	    	cloud.posY = this.posY;
	    	cloud.posZ = this.posZ;
	    	this.worldObj.spawnEntityInWorld(cloud);
		}
		
		if(config.nuke > 0 && !worldObj.isRemote) {
	    	worldObj.spawnEntityInWorld(EntityNukeExplosionMK4.statFac(worldObj, config.nuke, posX, posY, posZ).mute());
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "muke");
			if(MainRegistry.polaroidID == 11 || rand.nextInt(100) == 0) data.setBoolean("balefire", true);
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, posX, posY + 0.5, posZ), new TargetPoint(dimension, posX, posY, posZ, 250));
			worldObj.playSoundEffect(posX, posY, posZ, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
		}
		
		if(config.destroysBlocks && !worldObj.isRemote) {
			if(worldObj.getBlock(bX, bY, bZ).getBlockHardness(worldObj, bX, bY, bZ) <= 120)
    			worldObj.func_147480_a(bX, bY, bZ, false);
		} else if(config.doesBreakGlass && !worldObj.isRemote) {
			if(worldObj.getBlock(bX, bY, bZ) == Blocks.glass || 
					worldObj.getBlock(bX, bY, bZ) == Blocks.glass_pane || 
					worldObj.getBlock(bX, bY, bZ) == Blocks.stained_glass || 
					worldObj.getBlock(bX, bY, bZ) == Blocks.stained_glass_pane)
				worldObj.func_147480_a(bX, bY, bZ, false);
			
			if(worldObj.getBlock(bX, bY, bZ) == ModBlocks.red_barrel)
				((RedBarrel) ModBlocks.red_barrel).explode(worldObj, bX, bY, bZ);
		}
	}
	
	//for when a bullet dies by hitting a block
	private void onRicochet(int bX, int bY, int bZ) {
		
		if(config.bRicochet != null)
			config.bRicochet.behaveBlockRicochet(this, bX, bY, bZ);
	}
	
	//for when a bullet dies by hitting an entity
	private void onEntityImpact(Entity e) {
		onEntityHurt(e);
		onBlockImpact(-1, -1, -1);
		
		if(config.bHit != null)
			config.bHit.behaveEntityHit(this, e);
	}
	
	//for when a bullet hurts an entity, not necessarily dying
	private void onEntityHurt(Entity e) {
		
		if(config.bHurt != null)
			config.bHurt.behaveEntityHurt(this, e);
		
		if(config.incendiary > 0 && !worldObj.isRemote) {
			e.setFire(config.incendiary);
		}
		
		if(config.leadChance > 0 && !worldObj.isRemote && worldObj.rand.nextInt(100) < config.leadChance && e instanceof EntityLivingBase) {
			((EntityLivingBase)e).addPotionEffect(new PotionEffect(HbmPotion.lead.id, 10 * 20, 0));
		}
		
		if(e instanceof EntityLivingBase && config.effects != null && !config.effects.isEmpty() && !worldObj.isRemote) {
			
			for(PotionEffect effect : config.effects) {
				((EntityLivingBase)e).addPotionEffect(new PotionEffect(effect));
			}
		}
		
		if(config.instakill && e instanceof EntityLivingBase && !worldObj.isRemote) {
			
			if(!(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.isCreativeMode))
				((EntityLivingBase)e).setHealth(0.0F);
		}
		
		if(config.caustic > 0 && e instanceof EntityPlayer){
			ArmorUtil.damageSuit((EntityPlayer)e, 0, config.caustic);
			ArmorUtil.damageSuit((EntityPlayer)e, 1, config.caustic);
			ArmorUtil.damageSuit((EntityPlayer)e, 2, config.caustic);
			ArmorUtil.damageSuit((EntityPlayer)e, 3, config.caustic);
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		
		int cfg = nbt.getInteger("config");
		this.config = BulletConfigSyncingUtil.pullConfig(cfg);
		
		if(this.config == null) {
			this.setDead();
			return;
		}
		
		this.overrideDamage = nbt.getFloat("damage");
		
		this.dataWatcher.updateObject(18, cfg);
		
		this.dataWatcher.updateObject(16, (byte)this.config.style);
		this.dataWatcher.updateObject(17, (byte)this.config.trail);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		
		nbt.setInteger("config", dataWatcher.getWatchableObjectInt(18));
		
		nbt.setFloat("damage", this.overrideDamage);
	}

}
