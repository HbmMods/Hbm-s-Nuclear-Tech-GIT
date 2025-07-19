package com.hbm.entity.projectile;

import java.util.List;

import com.hbm.items.weapon.sedna.BulletConfig;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBulletBeamBase extends Entity implements IEntityAdditionalSpawnData {
	
	public EntityLivingBase thrower;
	public BulletConfig config;
	public float damage;
	public double headingX;
	public double headingY;
	public double headingZ;
	public double beamLength;

	public EntityBulletBeamBase(World world) {
		super(world);
		this.ignoreFrustumCheck = true;
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
		this.isImmuneToFire = true;
	}
	
	public EntityLivingBase getThrower() { return this.thrower; }
	
	public EntityBulletBeamBase(EntityLivingBase entity, BulletConfig config, float baseDamage, float angularInaccuracy, double sideOffset, double heightOffset, double frontOffset) {
		this(entity.worldObj);
		
		this.thrower = entity;
		this.setBulletConfig(config);
		
		this.damage = baseDamage * this.config.damageMult;
		
		this.setLocationAndAngles(thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw + (float) rand.nextGaussian() * angularInaccuracy, thrower.rotationPitch + (float) rand.nextGaussian() * angularInaccuracy);
		
		Vec3 offset = Vec3.createVectorHelper(sideOffset, heightOffset, frontOffset);
		offset.rotateAroundX(-this.rotationPitch / 180F * (float) Math.PI);
		offset.rotateAroundY(-this.rotationYaw / 180F * (float) Math.PI);

		this.posX += offset.xCoord;
		this.posY += offset.yCoord;
		this.posZ += offset.zCoord;
		
		this.setPosition(this.posX, this.posY, this.posZ);
		
		this.headingX = (double) (-MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI));
		this.headingZ = (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI));
		this.headingY = (double) (-MathHelper.sin((this.rotationPitch) / 180.0F * (float) Math.PI));
		
		double range = 250D;
		this.headingX *= range;
		this.headingY *= range;
		this.headingZ *= range;
		
		performHitscan();
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(3, Integer.valueOf(0));
	}
	
	public void setBulletConfig(BulletConfig config) {
		this.config = config;
		this.dataWatcher.updateObject(3, config.id);
	}
	
	public BulletConfig getBulletConfig() {
		int id = this.dataWatcher.getWatchableObjectInt(3);
		if(id < 0 || id > BulletConfig.configs.size()) return null;
		return BulletConfig.configs.get(id);
	}
	
	@Override
	public void onUpdate() {
		
		if(config == null) config = this.getBulletConfig();

		if(config == null){
			this.setDead();
			return;
		}
		
		if(config.onUpdate != null) config.onUpdate.accept(this);
		
		super.onUpdate();
		
		if(!worldObj.isRemote && this.ticksExisted > config.expires) this.setDead();
	}
	
	protected void performHitscan() {
		
		Vec3 pos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		Vec3 nextPos = Vec3.createVectorHelper(this.posX + this.headingX, this.posY + this.headingY, this.posZ + this.headingZ);
		MovingObjectPosition mop = null;
		if(!this.isSpectral()) mop = this.worldObj.func_147447_a(pos, nextPos, false, true, false);
		pos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		nextPos = Vec3.createVectorHelper(this.posX + this.headingX, this.posY + this.headingY, this.posZ + this.headingZ);

		if(mop != null) {
			nextPos = Vec3.createVectorHelper(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
		}
		
		if(!this.worldObj.isRemote && this.doesImpactEntities()) {
			
			Entity hitEntity = null;
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.headingX, this.headingY, this.headingZ).expand(1.0D, 1.0D, 1.0D));
			double nearest = 0.0D;
			MovingObjectPosition nonPenImpact = null;

			for(int j = 0; j < list.size(); ++j) {
				Entity entity = (Entity) list.get(j);
				
				if(entity.canBeCollidedWith() && entity != thrower && entity.isEntityAlive()) {
					double hitbox = 0.3F;
					AxisAlignedBB aabb = entity.boundingBox.expand(hitbox, hitbox, hitbox);
					MovingObjectPosition hitMop = aabb.calculateIntercept(pos, nextPos);

					if(hitMop != null) {
						
						// if penetration is enabled, run impact for all intersecting entities
						if(this.doesPenetrate()) {
							this.onImpact(new MovingObjectPosition(entity, hitMop.hitVec));
						} else {
							
							double dist = pos.distanceTo(hitMop.hitVec);
	
							if(dist < nearest || nearest == 0.0D) {
								hitEntity = entity;
								nearest = dist;
								nonPenImpact = hitMop;
							}
						}
					}
				}
			}

			// if not, only run it for the closest MOP
			if(!this.doesPenetrate() && hitEntity != null) {
				mop = new MovingObjectPosition(hitEntity, nonPenImpact.hitVec);
			}
		}

		if(mop != null) {
			if(mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ) == Blocks.portal) {
				this.setInPortal();
			} else {
				this.onImpact(mop);
			}

			Vec3 vec = Vec3.createVectorHelper(mop.hitVec.xCoord - posX, mop.hitVec.yCoord - posY, mop.hitVec.zCoord - posZ);
			this.beamLength = vec.lengthVector();
		} else {
			Vec3 vec = Vec3.createVectorHelper(nextPos.xCoord - posX, nextPos.yCoord - posY, nextPos.zCoord - posZ);
			this.beamLength = vec.lengthVector();
		}
		
	}

	
	protected void onImpact(MovingObjectPosition mop) {
		if(!worldObj.isRemote) {
			if(this.config.onImpactBeam != null) this.config.onImpactBeam.accept(this, mop);
		}
	}
	
	public boolean doesImpactEntities() { return this.config.impactsEntities; }
	public boolean doesPenetrate() { return this.config.doesPenetrate; }
	public boolean isSpectral() { return this.config.isSpectral; }

	@Override @SideOnly(Side.CLIENT) public float getShadowSize() { return 0.0F; }
	
	@Override protected void writeEntityToNBT(NBTTagCompound nbt) { }
	@Override public boolean writeToNBTOptional(NBTTagCompound nbt) { return false; }
	@Override public void readEntityFromNBT(NBTTagCompound nbt) { this.setDead(); }

	@Override public void writeSpawnData(ByteBuf buf) {
		buf.writeDouble(beamLength);
		buf.writeFloat(rotationYaw);
		buf.writeFloat(rotationPitch);
	}
	@Override public void readSpawnData(ByteBuf buf) {
		this.beamLength = buf.readDouble();
		this.rotationYaw = buf.readFloat();
		this.rotationPitch = buf.readFloat();
	}
	
	@Override @SideOnly(Side.CLIENT) public boolean canRenderOnFire() { return false; }
}
