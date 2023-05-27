package com.hbm.entity.train;

import com.hbm.util.BobMathUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityRailCarRidable extends EntityRailCarCargo {
	
	public double engineSpeed;
	public SeatDummyEntity[] passengerSeats;
	
	public EntityRailCarRidable(World world) {
		super(world);
		this.passengerSeats = new SeatDummyEntity[this.getPassengerSeats().length];
	}

	/** Returns the linear speed added per tick when using powered movement */
	public abstract double getPoweredAcceleration();
	/** A mulitplier used on the speed either is there is no player in the train or if the parking brake is active */
	public abstract double getPassivBrake();
	/** The parking brake can be toggled, assuming a player is present, otherwise it is implicitly ON */
	public abstract boolean shouldUseEngineBrake(EntityPlayer player);
	/** The max speed the engine can provide in both directions */
	public abstract double getMaxPoweredSpeed();
	/** Whether the engine is turned on */
	public abstract boolean canAccelerate();
	/** Called every tick if acceleration is successful */
	public void consumeFuel() { }
	
	/** An additive to the engine's speed yielding the total speed, caused by uneven surfaces */
	public double getGravitySpeed() {
		return 0D;
	}

	@Override
	public double getCurrentSpeed() { // in its current form, only call once per tick
		
		if(this.riddenByEntity instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) this.riddenByEntity;
			
			if(this.canAccelerate()) {
				if(player.moveForward > 0) {
					engineSpeed += this.getPoweredAcceleration();
					this.consumeFuel();
				} else if(player.moveForward < 0) {
					engineSpeed -= this.getPoweredAcceleration();
					this.consumeFuel();
				} else {
					if(this.shouldUseEngineBrake(player)) {
						engineSpeed *= this.getPassivBrake();
					} else {
						this.consumeFuel();
					}
				}
			} else {
				engineSpeed *= this.getPassivBrake();
			}
			
		} else {
			engineSpeed *= this.getPassivBrake();
		}
		
		double maxSpeed = this.getMaxPoweredSpeed();
		engineSpeed = MathHelper.clamp_double(engineSpeed, -maxSpeed, maxSpeed);
		
		return engineSpeed + this.getGravitySpeed();
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {
		
		if(super.interactFirst(player)) return true;
		if(worldObj.isRemote) return true;
		
		double nearestDist = Double.POSITIVE_INFINITY;
		int nearestSeat = -1;
		
		Vec3[] seats = getPassengerSeats();
		for(int i = 0; i < seats.length; i++) {
			
			Vec3 seat = seats[i];
			if(seat == null) continue;
			if(passengerSeats[i] != null) continue;
			
			seat.rotateAroundY((float) (-this.rotationYaw * Math.PI / 180));
			double x = renderX + seat.xCoord;
			double z = renderZ + seat.zCoord;

			double deltaX = player.posX - x;
			double deltaZ = player.posZ - z;
			double radians = -Math.atan2(deltaX, deltaZ);
			double degrees = MathHelper.wrapAngleTo180_double(radians * 180D / Math.PI - 90);
			double dist = Math.abs(BobMathUtil.angularDifference(degrees, player.rotationYaw));
			
			if(dist < nearestDist) {
				nearestDist = dist;
				nearestSeat = i;
			}
		}

		if(this.riddenByEntity == null) {
			Vec3 seat = getRiderSeatPosition();
			seat.rotateAroundY((float) (-this.rotationYaw * Math.PI / 180));
			double x = renderX + seat.xCoord;
			double z = renderZ + seat.zCoord;

			double deltaX = player.posX - x;
			double deltaZ = player.posZ - z;
			double radians = -Math.atan2(deltaX, deltaZ);
			double degrees = MathHelper.wrapAngleTo180_double(radians * 180D / Math.PI - 90);
			double dist = Math.abs(BobMathUtil.angularDifference(degrees, player.rotationYaw));
	
			if(dist < nearestDist) {
				nearestDist = dist;
				nearestSeat = -1;
			}
		}
		
		if(nearestDist > 180) return true;
		
		if(nearestSeat == -1) {
			player.mountEntity(this);
		} else {
			SeatDummyEntity dummySeat = new SeatDummyEntity(worldObj, this);
			Vec3 passengerSeat = this.getPassengerSeats()[nearestSeat];
			passengerSeat.rotateAroundY((float) (-this.rotationYaw * Math.PI / 180));
			double x = renderX + passengerSeat.xCoord;
			double y = renderY + passengerSeat.yCoord;
			double z = renderZ + passengerSeat.zCoord;
			dummySeat.setPosition(x, y - 1, z);
			passengerSeats[nearestSeat] = dummySeat;
			worldObj.spawnEntityInWorld(dummySeat);
			player.mountEntity(dummySeat);
		}

		return true;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!worldObj.isRemote) {
			
			Vec3[] seats = this.getPassengerSeats();
			for(int i = 0; i < passengerSeats.length; i++) {
				SeatDummyEntity seat = passengerSeats[i];

				if(seat != null) {
					if(seat.riddenByEntity == null) {
						passengerSeats[i] = null;
						seat.setDead();
					} else {
						Vec3 rot = seats[i];
						rot.rotateAroundY((float) (-this.rotationYaw * Math.PI / 180));
						double x = renderX + rot.xCoord;
						double y = renderY + rot.yCoord;
						double z = renderZ + rot.zCoord;
						seat.setPosition(x, y - 1, z);
					}
				}
			}
		}
	}

	@Override
	public void updateRiderPosition() {
		
		Vec3 offset = getRiderSeatPosition();
		offset.rotateAroundY((float) (-this.rotationYaw * Math.PI / 180));
		
		if(this.riddenByEntity != null) {
			this.riddenByEntity.setPosition(this.renderX + offset.xCoord, this.renderY + offset.yCoord, this.renderZ + offset.zCoord);
		}
	}

	/** Returns a Vec3 showing the relative position from the driver to the core */
	public abstract Vec3 getRiderSeatPosition();
	
	public abstract Vec3[] getPassengerSeats();
	
	/** Dynamic seats generated when a player clicks near a seat-spot, moves and rotates with the train as one would expect. */
	public static class SeatDummyEntity extends Entity {

		private int turnProgress;
		private double trainX;
		private double trainY;
		private double trainZ;
		public EntityRailCarBase train;

		public SeatDummyEntity(World world) { super(world); this.setSize(0.5F, 0.1F);}
		public SeatDummyEntity(World world, EntityRailCarBase train) {
			this(world);
			this.train = train;
			if(train != null) this.dataWatcher.updateObject(3, train.getEntityId());
		}
		
		@Override protected void entityInit() { this.dataWatcher.addObject(3, new Integer(0)); }
		@Override protected void writeEntityToNBT(NBTTagCompound nbt) { }
		@Override public boolean writeToNBTOptional(NBTTagCompound nbt) { return false; }
		@Override public void readEntityFromNBT(NBTTagCompound nbt) { this.setDead(); }
		
		@Override public void onUpdate() {
			if(!worldObj.isRemote) {
				if(this.train == null || this.train.isDead) {
					this.setDead();
				}
			} else {
				
				if(this.turnProgress > 0) {
					this.prevRotationYaw = this.rotationYaw;
					double x = this.posX + (this.trainX - this.posX) / (double) this.turnProgress;
					double y = this.posY + (this.trainY - this.posY) / (double) this.turnProgress;
					double z = this.posZ + (this.trainZ - this.posZ) / (double) this.turnProgress;
					--this.turnProgress;
					this.setPosition(x, y, z);
				} else {
					this.setPosition(this.posX, this.posY, this.posZ);
				}
			}
		}
		
		@Override @SideOnly(Side.CLIENT) public void setPositionAndRotation2(double posX, double posY, double posZ, float yaw, float pitch, int turnProg) {
			this.trainX = posX;
			this.trainY = posY;
			this.trainZ = posZ;
			this.turnProgress = turnProg + 2;
		}

		@Override
		public void updateRiderPosition() {
			if(this.riddenByEntity != null) {
				this.riddenByEntity.setPosition(this.posX, this.posY + 1, this.posZ);
			}
		}
	}
}
