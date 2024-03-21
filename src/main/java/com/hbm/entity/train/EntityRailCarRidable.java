package com.hbm.entity.train;

import java.util.ArrayList;
import java.util.List;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

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
		
		int nearestSeat = this.getNearestSeat(player);
		
		if(nearestSeat == -1) {
			player.mountEntity(this);
		} else if(nearestSeat >= 0) {
			SeatDummyEntity dummySeat = new SeatDummyEntity(worldObj, this, nearestSeat);
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
	
	public int getNearestSeat(EntityPlayer player) {
		
		double nearestDist = Double.POSITIVE_INFINITY;
		int nearestSeat = -3;
		
		Vec3[] seats = getPassengerSeats();
		Vec3 look = player.getLook(2);
		look.xCoord += player.posX;
		look.yCoord += player.posY + player.eyeHeight - player.yOffset;
		look.zCoord += player.posZ;
		
		for(int i = 0; i < seats.length; i++) {
			
			Vec3 seat = seats[i];
			if(seat == null) continue;
			if(passengerSeats[i] != null) continue;
			
			seat.rotateAroundY((float) (-this.rotationYaw * Math.PI / 180));
			double x = renderX + seat.xCoord;
			double y = renderY + seat.yCoord;
			double z = renderZ + seat.zCoord;

			Vec3 delta = Vec3.createVectorHelper(look.xCoord - x, look.yCoord - y, look.zCoord - z);
			double dist = delta.lengthVector();
			
			if(dist < nearestDist) {
				nearestDist = dist;
				nearestSeat = i;
			}
		}

		if(this.riddenByEntity == null) {
			Vec3 seat = getRiderSeatPosition();
			seat.rotateAroundY((float) (-this.rotationYaw * Math.PI / 180));
			double x = renderX + seat.xCoord;
			double y = renderY + seat.yCoord;
			double z = renderZ + seat.zCoord;

			Vec3 delta = Vec3.createVectorHelper(look.xCoord - x, look.yCoord - y, look.zCoord - z);
			double dist = delta.lengthVector();
	
			if(dist < nearestDist) {
				nearestDist = dist;
				nearestSeat = -1;
			}
		}
		
		if(nearestDist > 180) return -2;
		
		return nearestSeat;
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
						rot.rotateAroundX((float) (this.rotationPitch * Math.PI / 180));
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
		offset.rotateAroundX((float) (this.rotationPitch * Math.PI / 180));
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
		public EntityRailCarRidable train;

		public SeatDummyEntity(World world) { super(world); this.setSize(0.5F, 0.1F);}
		public SeatDummyEntity(World world, EntityRailCarRidable train, int index) {
			this(world);
			this.train = train;
			if(train != null) this.dataWatcher.updateObject(3, train.getEntityId());
			this.dataWatcher.updateObject(4, index);
		}
		
		@Override protected void entityInit() { this.dataWatcher.addObject(3, new Integer(0)); this.dataWatcher.addObject(4, new Integer(0)); }
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
				
				if(train == null) {
					int eid = this.dataWatcher.getWatchableObjectInt(3);
					Entity entity = worldObj.getEntityByID(eid);
					if(entity instanceof EntityRailCarRidable) {
						train = (EntityRailCarRidable) entity;
					}
				}
				
				//fallback for when train is null
				if(train == null) {
					this.riddenByEntity.setPosition(posX, posY + 1, posZ);
					return;
				}
				
				//doing it like this instead of with the position directly removes any discrepancies caused by entity tick order
				//mmhmhmhm silky smooth
				int index = this.dataWatcher.getWatchableObjectInt(4);
				Vec3 rot = this.train.getPassengerSeats()[index];
				rot.rotateAroundX((float) (train.rotationPitch * Math.PI / 180));
				rot.rotateAroundY((float) (-train.rotationYaw * Math.PI / 180));
				double x = train.renderX + rot.xCoord;
				double y = train.renderY + rot.yCoord;
				double z = train.renderZ + rot.zCoord;
				this.riddenByEntity.setPosition(x, y, z);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void printHook(RenderGameOverlayEvent.Pre event, World world, int x, int y, int z) {
		List<String> text = new ArrayList();
		/*text.add("LTU: " + this.ltu);
		text.add("Front: " + this.coupledFront);
		text.add("Back: " + this.coupledBack);*/
		text.add("Nearest seat: " + this.getNearestSeat(MainRegistry.proxy.me()));
		//ILookOverlay.printGeneric(event, this.getClass().getSimpleName() + " " + this.hashCode(), 0xffff00, 0x404000, text);
	}
}
