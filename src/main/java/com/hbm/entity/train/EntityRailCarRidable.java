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
	
	public SeatDummyEntity[] passengerSeats;
	
	public EntityRailCarRidable(World world) {
		super(world);
		this.passengerSeats = new SeatDummyEntity[this.getPassengerSeats().length];
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {
		
		if(worldObj.isRemote) return true;
		
		double nearestDist = Double.POSITIVE_INFINITY;
		int nearestSeat = -1;
		
		Vec3[] seats = getPassengerSeats();
		for(int i = 0; i < seats.length; i++) {
			
			Vec3 seat = seats[i];
			if(seat == null) continue;
			if(passengerSeats[i] != null) continue;
			
			seat.rotateAroundY((float) (-this.rotationYaw * Math.PI / 180));
			double x = posX + seat.xCoord;
			double z = posZ + seat.zCoord;

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
			double x = posX + seat.xCoord;
			double z = posZ + seat.zCoord;

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
			double x = posX + passengerSeat.xCoord;
			double y = posY + passengerSeat.yCoord;
			double z = posZ + passengerSeat.zCoord;
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
						double x = posX + rot.xCoord;
						double y = posY + rot.yCoord;
						double z = posZ + rot.zCoord;
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
			this.riddenByEntity.setPosition(this.posX + offset.xCoord, this.posY + offset.yCoord, this.posZ + offset.zCoord);
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
