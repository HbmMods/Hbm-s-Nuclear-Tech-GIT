package com.hbm.entity.train;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
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
			double y = posY + seat.yCoord;
			double z = posZ + seat.zCoord;
			double dist = Vec3.createVectorHelper(player.posX - x, player.posY - y, player.posZ - z).lengthVector();
			
			if(dist < nearestDist) {
				nearestDist = dist;
				nearestSeat = i;
			}
		}

		if(this.riddenByEntity == null) {
			Vec3 seat = getRiderSeatPosition();
			seat.rotateAroundY((float) (-this.rotationYaw * Math.PI / 180));
			double x = posX + seat.xCoord;
			double y = posY + seat.yCoord;
			double z = posZ + seat.zCoord;
			double dist = Vec3.createVectorHelper(player.posX - x, player.posY - y, player.posZ - z).lengthVector();
	
			if(dist < nearestDist) {
				nearestDist = dist;
				nearestSeat = -1;
			}
		}
		
		if(nearestDist > 20) return true;
		
		if(nearestSeat == -1) {
			player.mountEntity(this);
		} else {
			SeatDummyEntity dummySeat = new SeatDummyEntity(worldObj);
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
						seat.updateRiderPosition();
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
	
	public static class SeatDummyEntity extends Entity {
		public SeatDummyEntity(World world) { super(world); this.setSize(0.5F, 0.1F);}
		@Override protected void entityInit() { }
		@Override protected void writeEntityToNBT(NBTTagCompound nbt) { }
		@Override public boolean writeToNBTOptional(NBTTagCompound nbt) { return false; }
		@Override public void readEntityFromNBT(NBTTagCompound nbt) { this.setDead(); }

		@Override
		public void updateRiderPosition() {
			if(this.riddenByEntity != null) {
				this.riddenByEntity.setPosition(this.posX, this.posY + 1, this.posZ);
			}
		}
	}
}
