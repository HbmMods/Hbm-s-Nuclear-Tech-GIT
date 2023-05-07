package com.hbm.entity.train;

import com.hbm.blocks.rail.IRailNTM;
import com.hbm.blocks.rail.IRailNTM.RailContext;
import com.hbm.blocks.rail.IRailNTM.TrackGauge;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityRailCarBase extends Entity {
	
	public boolean isOnRail = true;
	private int turnProgress;
	private double trainX;
	private double trainY;
	private double trainZ;
	private double trainYaw;
	private double trainPitch;
	private float movementYaw;
	@SideOnly(Side.CLIENT) private double velocityX;
	@SideOnly(Side.CLIENT) private double velocityY;
	@SideOnly(Side.CLIENT) private double velocityZ;

	public EntityRailCarBase(World world) {
		super(world);
	}

	@Override protected void entityInit() { }
	@Override protected void readEntityFromNBT(NBTTagCompound nbt) { }
	@Override protected void writeEntityToNBT(NBTTagCompound nbt) { }

	@Override
	public boolean canBePushed() {
		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}
	
	@Override
	public void onUpdate() {

		if(this.worldObj.isRemote) {
			
			if(this.turnProgress > 0) {
				this.prevRotationYaw = this.rotationYaw;
				double x = this.posX + (this.trainX - this.posX) / (double) this.turnProgress;
				double y = this.posY + (this.trainY - this.posY) / (double) this.turnProgress;
				double z = this.posZ + (this.trainZ - this.posZ) / (double) this.turnProgress;
				double yaw = MathHelper.wrapAngleTo180_double(this.trainYaw - (double) this.rotationYaw);
				this.rotationYaw = (float) ((double) this.rotationYaw + yaw / (double) this.turnProgress);
				this.rotationPitch = (float) ((double) this.rotationPitch + (this.trainPitch - (double) this.rotationPitch) / (double) this.turnProgress);
				--this.turnProgress;
				this.setPosition(x, y, z);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			} else {
				this.setPosition(this.posX, this.posY, this.posZ);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			}
		} else {
			
			BlockPos anchor = this.getCurentAnchorPos();
			Vec3 corePos = getRelPosAlongRail(anchor, this.getCurrentSpeed());
			
			if(corePos == null) {
				this.derail();
			} else {
				this.setPosition(corePos.xCoord, corePos.yCoord, corePos.zCoord);
				anchor = this.getCurentAnchorPos(); //reset origin to new position
				Vec3 frontPos = getRelPosAlongRail(anchor, this.getLengthSpan());
				Vec3 backPos = getRelPosAlongRail(anchor, -this.getLengthSpan());

				if(frontPos == null || backPos == null) {
					this.derail();
				} else {
					this.prevRotationYaw = this.rotationYaw;
					this.rotationYaw = this.movementYaw = generateYaw(frontPos, backPos);
					this.motionX = this.rotationYaw / 360D; // hijacking this crap for easy syncing
					this.velocityChanged = true;
				}
			}
		}
	}
	
	public Vec3 getRelPosAlongRail(BlockPos anchor, double distanceToCover) {
		
		float yaw = this.rotationYaw;
		
		if(distanceToCover < 0) {
			distanceToCover *= -1;
			yaw += 180;
		}
		
		Vec3 next = Vec3.createVectorHelper(posX, posY, posZ);
		int it = 0;
		
		do {
			
			it++;
			
			if(it > 30) {
				worldObj.createExplosion(this, posX, posY, posZ, 5F, false);
				this.derail();
				return null;
			}
			
			int x = anchor.getX();
			int y = anchor.getY();
			int z = anchor.getZ();
			Block block = worldObj.getBlock(x, y, z);
			
			Vec3 rot = Vec3.createVectorHelper(0, 0, 1);
			rot.rotateAroundY((float) (-yaw * Math.PI / 180D));
			
			if(block instanceof IRailNTM) {
				IRailNTM rail = (IRailNTM) block;
				
				if(it == 1) {
					next = rail.getTravelLocation(worldObj, x, y, z, next.xCoord, next.yCoord, next.zCoord, rot.xCoord, rot.yCoord, rot.zCoord, 0, new RailContext());
				}
				
				boolean flip = distanceToCover < 0;
				
				if(rail.getGauge(worldObj, x, y, z) == this.getGauge()) {
					RailContext info = new RailContext();
					Vec3 prev = next;
					next = rail.getTravelLocation(worldObj, x, y, z, prev.xCoord, prev.yCoord, prev.zCoord, rot.xCoord, rot.yCoord, rot.zCoord, distanceToCover, info);
					distanceToCover = info.overshoot;
					anchor = info.pos;
					
					yaw = generateYaw(next, prev) * (flip ? -1 : 1);
					
				} else {
					return null;
				}
			} else {
				return null;
			}
			
		} while(distanceToCover != 0); //if there's still length to cover, keep going
		
		return next;
	}
	
	public float generateYaw(Vec3 front, Vec3 back) {
		double deltaX = front.xCoord - back.xCoord;
		double deltaZ = front.zCoord - back.zCoord;
		double radians = -Math.atan2(deltaX, deltaZ);
		return (float) MathHelper.wrapAngleTo180_double(radians * 180D / Math.PI);
	}

	/** Returns the amount of blocks that the train should move per tick */
	public abstract double getCurrentSpeed();
	/** Returns the gauge of this train */
	public abstract TrackGauge getGauge();
	/** Returns the length between the core and one of the bogies */
	public abstract double getLengthSpan();
	
	/** Returns the "true" position of the train, i.e. the block it wants to snap to */
	public BlockPos getCurentAnchorPos() {
		return new BlockPos(posX, posY, posZ);
	}
	
	public void derail() {
		isOnRail = false;
		this.setDead();
	}
	
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double posX, double posY, double posZ, float yaw, float pitch, int turnProg) {
		this.trainX = posX;
		this.trainY = posY;
		this.trainZ = posZ;
		//this.trainYaw = (double) yaw;
		this.trainPitch = (double) pitch;
		this.turnProgress = turnProg + 2;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
		this.trainYaw = this.movementYaw;
	}

	@SideOnly(Side.CLIENT)
	public void setVelocity(double mX, double mY, double mZ) {
		this.movementYaw = (float) this.motionX * 360F;
		this.velocityX = this.motionX = mX;
		this.velocityY = this.motionY = mY;
		this.velocityZ = this.motionZ = mZ;
	}
}
