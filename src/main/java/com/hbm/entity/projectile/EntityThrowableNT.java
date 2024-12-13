package com.hbm.entity.projectile;

import java.util.List;

import com.hbm.util.TrackerUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * Near-identical copy of EntityThrowable but deobfuscated & untangled
 * @author hbm
 *
 */
public abstract class EntityThrowableNT extends Entity implements IProjectile {
	
	private int stuckBlockX = -1;
	private int stuckBlockY = -1;
	private int stuckBlockZ = -1;
	private Block stuckBlock;
	protected boolean inGround;
	public int throwableShake;
	protected EntityLivingBase thrower;
	private String throwerName;
	public int ticksInGround;
	private int ticksInAir;

	public EntityThrowableNT(World world) {
		super(world);
		this.setSize(0.25F, 0.25F);
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(2, Byte.valueOf((byte)0));
	}
	
	public void setStuckIn(int side) {
		this.dataWatcher.updateObject(2, (byte) side);
	}
	
	public int getStuckIn() {
		return this.dataWatcher.getWatchableObjectByte(2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double dist) {
		
		double perimeter = this.boundingBox.getAverageEdgeLength() * 4.0D;
		perimeter *= 64.0D;
		return dist < perimeter * perimeter;
	}

	public EntityThrowableNT(World world, EntityLivingBase thrower) {
		super(world);
		this.thrower = thrower;
		this.setSize(0.25F, 0.25F);
		this.setLocationAndAngles(thrower.posX, thrower.posY + (double) thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw, thrower.rotationPitch);
		this.posX -= (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		this.posY -= 0.1D;
		this.posZ -= (double) (MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		float velocity = 0.4F;
		this.motionX = (double) (-MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * velocity);
		this.motionZ = (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * velocity);
		this.motionY = (double) (-MathHelper.sin((this.rotationPitch + this.throwAngle()) / 180.0F * (float) Math.PI) * velocity);
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.throwForce(), 1.0F);
	}

	public EntityThrowableNT(World world, double x, double y, double z) {
		super(world);
		this.ticksInGround = 0;
		this.setSize(0.25F, 0.25F);
		this.setPosition(x, y, z);
		this.yOffset = 0.0F;
	}

	protected float throwForce() {
		return 1.5F;
	}

	protected double headingForceMult() {
		return 0.0075D;
	}

	protected float throwAngle() {
		return 0.0F;
	}

	protected double motionMult() {
		return 1.0D;
	}

	@Override
	public void setThrowableHeading(double motionX, double motionY, double motionZ, float velocity, float inaccuracy) {
		float throwLen = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
		motionX /= (double) throwLen;
		motionY /= (double) throwLen;
		motionZ /= (double) throwLen;
		motionX += this.rand.nextGaussian() * headingForceMult() * (double) inaccuracy;
		motionY += this.rand.nextGaussian() * headingForceMult() * (double) inaccuracy;
		motionZ += this.rand.nextGaussian() * headingForceMult() * (double) inaccuracy;
		motionX *= (double) velocity;
		motionY *= (double) velocity;
		motionZ *= (double) velocity;
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
		float hyp = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(motionY, (double) hyp) * 180.0D / Math.PI);
		this.ticksInGround = 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z) {
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;

		if(this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float hyp = MathHelper.sqrt_double(x * x + z * z);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y, (double) hyp) * 180.0D / Math.PI);
		}
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();

		if(this.throwableShake > 0) {
			--this.throwableShake;
		}

		if(this.inGround) {
			if(this.worldObj.getBlock(this.stuckBlockX, this.stuckBlockY, this.stuckBlockZ) == this.stuckBlock) {
				++this.ticksInGround;

				if(this.groundDespawn() > 0 && this.ticksInGround == this.groundDespawn()) {
					this.setDead();
				}

				return;
				
			} else {

				this.inGround = false;
				this.motionX *= (double) (this.rand.nextFloat() * 0.2F);
				this.motionY *= (double) (this.rand.nextFloat() * 0.2F);
				this.motionZ *= (double) (this.rand.nextFloat() * 0.2F);
				this.ticksInGround = 0;
				this.ticksInAir = 0;
			}
			
		} else {
			++this.ticksInAir;
	
			Vec3 pos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			Vec3 nextPos = Vec3.createVectorHelper(this.posX + this.motionX * motionMult(), this.posY + this.motionY * motionMult(), this.posZ + this.motionZ * motionMult());
			MovingObjectPosition mop = null;
			if(!this.isSpectral()) mop = this.worldObj.func_147447_a(pos, nextPos, false, true, false);
			pos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			nextPos = Vec3.createVectorHelper(this.posX + this.motionX * motionMult(), this.posY + this.motionY * motionMult(), this.posZ + this.motionZ * motionMult());
	
			if(mop != null) {
				nextPos = Vec3.createVectorHelper(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
			}
			
			if(!this.worldObj.isRemote && this.doesImpactEntities()) {
				
				Entity hitEntity = null;
				List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX * motionMult(), this.motionY * motionMult(), this.motionZ * motionMult()).expand(1.0D, 1.0D, 1.0D));
				double nearest = 0.0D;
				EntityLivingBase thrower = this.getThrower();
				MovingObjectPosition nonPenImpact = null;
	
				for(int j = 0; j < list.size(); ++j) {
					Entity entity = (Entity) list.get(j);
					
					if(entity.canBeCollidedWith() && (entity != thrower || this.ticksInAir >= this.selfDamageDelay()) && entity.isEntityAlive()) {
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
			}
			
			if(!this.onGround) {
				float hyp = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
				this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
		
				for(this.rotationPitch = (float) (Math.atan2(this.motionY, (double) hyp) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
		
				while(this.rotationPitch - this.prevRotationPitch >= 180.0F) this.prevRotationPitch += 360.0F;
				while(this.rotationYaw - this.prevRotationYaw < -180.0F) this.prevRotationYaw -= 360.0F;
				while(this.rotationYaw - this.prevRotationYaw >= 180.0F) this.prevRotationYaw += 360.0F;
		
				this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
				this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			}
			
			float drag = this.getAirDrag();
			double gravity = this.getGravityVelocity();
	
			this.posX += this.motionX * motionMult();
			this.posY += this.motionY * motionMult();
			this.posZ += this.motionZ * motionMult();
	
			if(this.isInWater()) {
				for(int i = 0; i < 4; ++i) {
					float f = 0.25F;
					this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double) f, this.posY - this.motionY * (double) f, this.posZ - this.motionZ * (double) f, this.motionX, this.motionY, this.motionZ);
				}
	
				drag = this.getWaterDrag();
			}
	
			this.motionX *= (double) drag;
			this.motionY *= (double) drag;
			this.motionZ *= (double) drag;
			this.motionY -= gravity;
			this.setPosition(this.posX, this.posY, this.posZ);
		}
	}
	
	public boolean doesImpactEntities() {
		return true;
	}
	
	public boolean doesPenetrate() {
		return false;
	}
	
	public boolean isSpectral() {
		return false;
	}
	
	public int selfDamageDelay() {
		return 5;
	}
	
	public void getStuck(int x, int y, int z, int side) {
		this.stuckBlockX = x;
		this.stuckBlockY = y;
		this.stuckBlockZ = z;
		this.stuckBlock = worldObj.getBlock(x, y, z);
		this.inGround = true;
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		this.setStuckIn(side);
		TrackerUtil.sendTeleport(worldObj, this);
	}
	
	public double getGravityVelocity() {
		return 0.03D;
	}
	
	protected abstract void onImpact(MovingObjectPosition mop);

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setShort("xTile", (short) this.stuckBlockX);
		nbt.setShort("yTile", (short) this.stuckBlockY);
		nbt.setShort("zTile", (short) this.stuckBlockZ);
		nbt.setByte("inTile", (byte) Block.getIdFromBlock(this.stuckBlock));
		nbt.setByte("shake", (byte) this.throwableShake);
		nbt.setByte("inGround", (byte) (this.inGround ? 1 : 0));

		if((this.throwerName == null || this.throwerName.length() == 0) && this.thrower != null && this.thrower instanceof EntityPlayer) {
			this.throwerName = this.thrower.getCommandSenderName();
		}

		nbt.setString("ownerName", this.throwerName == null ? "" : this.throwerName);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		this.stuckBlockX = nbt.getShort("xTile");
		this.stuckBlockY = nbt.getShort("yTile");
		this.stuckBlockZ = nbt.getShort("zTile");
		this.stuckBlock = Block.getBlockById(nbt.getByte("inTile") & 255);
		this.throwableShake = nbt.getByte("shake") & 255;
		this.inGround = nbt.getByte("inGround") == 1;
		this.throwerName = nbt.getString("ownerName");

		if(this.throwerName != null && this.throwerName.length() == 0) {
			this.throwerName = null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}
	
	public void setThrower(EntityLivingBase thrower) {
		this.thrower = thrower;
	}

	public EntityLivingBase getThrower() {
		if(this.thrower == null && this.throwerName != null && this.throwerName.length() > 0) {
			this.thrower = this.worldObj.getPlayerEntityByName(this.throwerName);
		}
		return this.thrower;
	}
	
	/* ================================== Additional Getters =====================================*/

	protected float getAirDrag() {
		return 0.99F;
	}
	
	protected float getWaterDrag() {
		return 0.8F;
	}
	
	protected int groundDespawn() {
		return 1200;
	}
}
