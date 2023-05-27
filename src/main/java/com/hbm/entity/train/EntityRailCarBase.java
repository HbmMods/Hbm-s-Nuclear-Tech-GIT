package com.hbm.entity.train;

import java.util.List;

import com.hbm.blocks.rail.IRailNTM;
import com.hbm.blocks.rail.IRailNTM.RailContext;
import com.hbm.blocks.rail.IRailNTM.TrackGauge;
import com.hbm.items.ModItems;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityRailCarBase extends Entity {

	public boolean isOnRail = true;
	private int turnProgress;
	/* Clientside position that should be approached with smooth interpolation */
	private double trainX;
	private double trainY;
	private double trainZ;
	private double trainYaw;
	private double trainPitch;
	private float movementYaw;
	@SideOnly(Side.CLIENT) private double velocityX;
	@SideOnly(Side.CLIENT) private double velocityY;
	@SideOnly(Side.CLIENT) private double velocityZ;
	/* "Actual" position with offset directly between the front and back pos, won't match the standard position on curves */
	public double lastRenderX;
	public double lastRenderY;
	public double lastRenderZ;
	public double renderX;
	public double renderY;
	public double renderZ;

	public EntityRailCarBase coupledFront;
	public EntityRailCarBase coupledBack;
	
	public boolean initDummies = false;
	public BoundingBoxDummyEntity[] dummies = new BoundingBoxDummyEntity[0];

	public EntityRailCarBase(World world) {
		super(world);
	}

	@Override protected void entityInit() { }
	@Override protected void readEntityFromNBT(NBTTagCompound nbt) { }
	@Override protected void writeEntityToNBT(NBTTagCompound nbt) { }

	@Override
	public boolean interactFirst(EntityPlayer player) {
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.coupling_tool) {
			
			List<EntityRailCarBase> intersecting = worldObj.getEntitiesWithinAABB(EntityRailCarBase.class, this.boundingBox.expand(2D, 0D, 2D));
			
			for(EntityRailCarBase neighbor : intersecting) {
				if(neighbor == this) continue;
				if(neighbor.getGauge() != this.getGauge()) continue;

				TrainCoupling closestOwnCoupling = null;
				TrainCoupling closestNeighborCoupling = null;
				double closestDist = Double.POSITIVE_INFINITY;
				
				for(TrainCoupling ownCoupling : TrainCoupling.values()) {
					for(TrainCoupling neighborCoupling : TrainCoupling.values()) {
						Vec3 ownPos = this.getCouplingPos(ownCoupling);
						Vec3 neighborPos = neighbor.getCouplingPos(neighborCoupling);
						if(ownPos != null && neighborPos != null) {
							Vec3 delta = Vec3.createVectorHelper(ownPos.xCoord - neighborPos.xCoord, ownPos.yCoord - neighborPos.yCoord, ownPos.zCoord - neighborPos.zCoord);
							double length = delta.lengthVector();
							
							if(length < 1 && length < closestDist) {
								closestDist = length;
								closestOwnCoupling = ownCoupling;
								closestNeighborCoupling = neighborCoupling;
							}
						}
					}
				}
				
				if(closestOwnCoupling != null && closestNeighborCoupling != null) {
					if(this.getCoupledTo(closestOwnCoupling) != null) continue;
					if(neighbor.getCoupledTo(closestNeighborCoupling) != null) continue;
					this.couple(closestOwnCoupling, neighbor);
					neighbor.couple(closestNeighborCoupling, this);
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public void onUpdate() {

		if(this.worldObj.isRemote) {

			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			
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

			/*
			 * TODO: move movement into the world tick event handler.
			 * step 1: detect linked trains, move linked units (LTUs) as one later
			 * step 2: move LTUs together using coupling rules (important to happen first, consistency has to be achieved before major movement)
			 * step 3: move LTUs based on their engine and gravity speed
			 * step 4: move LTUs based on collisions between LTUs (important to happen last, collision is most important)
			 */
			BlockPos anchor = this.getCurentAnchorPos();
			Vec3 frontPos = getRelPosAlongRail(anchor, this.getLengthSpan());
			Vec3 backPos = getRelPosAlongRail(anchor, -this.getLengthSpan());

			this.lastRenderX = this.renderX;
			this.lastRenderY = this.renderY;
			this.lastRenderZ = this.renderZ;
			
			if(frontPos != null && backPos != null) {
				this.renderX = (frontPos.xCoord + backPos.xCoord) / 2D;
				this.renderY = (frontPos.yCoord + backPos.yCoord) / 2D;
				this.renderZ = (frontPos.zCoord + backPos.zCoord) / 2D;
			}
			
		} else {
			
			DummyConfig[] definitions = this.getDummies();
			
			if(!this.initDummies) {
				this.dummies = new BoundingBoxDummyEntity[definitions.length];
				
				for(int i = 0; i < definitions.length; i++) {
					DummyConfig def = definitions[i];
					BoundingBoxDummyEntity dummy = new BoundingBoxDummyEntity(worldObj, this, def.width, def.height);
					Vec3 rot = Vec3.createVectorHelper(def.offset.xCoord, def.offset.yCoord, def.offset.zCoord);
					rot.rotateAroundY((float) (-this.rotationYaw * Math.PI / 180));
					double x = posX + rot.xCoord;
					double y = posY + rot.yCoord;
					double z = posZ + rot.zCoord;
					dummy.setPosition(x, y, z);
					worldObj.spawnEntityInWorld(dummy);
					this.dummies[i] = dummy;
				}
				
				this.initDummies = true;
			}
			
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
					return;
				} else {
					this.renderX = (frontPos.xCoord + backPos.xCoord) / 2D;
					this.renderY = (frontPos.yCoord + backPos.yCoord) / 2D;
					this.renderZ = (frontPos.zCoord + backPos.zCoord) / 2D;
					this.prevRotationYaw = this.rotationYaw;
					this.rotationYaw = this.movementYaw = generateYaw(frontPos, backPos);
					this.motionX = this.rotationYaw / 360D; // hijacking this crap for easy syncing
					this.velocityChanged = true;
				}
			}
			
			for(int i = 0; i < definitions.length; i++) {
				DummyConfig def = definitions[i];
				BoundingBoxDummyEntity dummy = dummies[i];
				Vec3 rot = Vec3.createVectorHelper(def.offset.xCoord, def.offset.yCoord, def.offset.zCoord);
				rot.rotateAroundY((float) (-this.rotationYaw * Math.PI / 180));
				double x = renderX + rot.xCoord;
				double y = renderY + rot.yCoord;
				double z = renderZ + rot.zCoord;
				dummy.setSize(def.width, def.height); // TEMP
				dummy.setPosition(x, y, z);
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
	/* Returns a collision box, usually smaller than the entity's AABB for rendering, which is used for colliding trains */
	public AxisAlignedBB getCollisionBox() {
		return this.boundingBox;
	}
	
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
	
	/** Invisible entities that make up the dynamic bounding structure of the train, moving as the train rotates. */
	public static class BoundingBoxDummyEntity extends Entity {

		private int turnProgress;
		private double trainX;
		private double trainY;
		private double trainZ;
		public EntityRailCarBase train;
		
		public BoundingBoxDummyEntity(World world) { this(world, null, 1F, 1F); }
		public BoundingBoxDummyEntity(World world, EntityRailCarBase train, float width, float height) {
			super(world);
			this.setSize(width, height);
			this.train = train;
			if(train != null) this.dataWatcher.updateObject(3, train.getEntityId());
		}
		
		@Override protected void setSize(float width, float height) {
			super.setSize(width, height);
			this.dataWatcher.updateObject(4, width);
			this.dataWatcher.updateObject(5, height);
		}
		
		@Override protected void entityInit() {
			this.dataWatcher.addObject(3, new Integer(0));
			this.dataWatcher.addObject(4, new Float(1F));
			this.dataWatcher.addObject(5, new Float(1F));
		}
		
		@Override protected void writeEntityToNBT(NBTTagCompound nbt) { }
		@Override public boolean writeToNBTOptional(NBTTagCompound nbt) { return false; }
		@Override public void readEntityFromNBT(NBTTagCompound nbt) { this.setDead(); }
		@Override public boolean canBePushed() { return true; }
		@Override public boolean canBeCollidedWith() { return !this.isDead; }
		
		@Override public boolean attackEntityFrom(DamageSource source, float amount) { if(train != null) return train.attackEntityFrom(source, amount); return super.attackEntityFrom(source, amount); }
		@Override public boolean interactFirst(EntityPlayer player) { if(train != null) return train.interactFirst(player); return super.interactFirst(player); }
		
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
				
				this.setSize(this.dataWatcher.getWatchableObjectFloat(4), this.dataWatcher.getWatchableObjectFloat(5));
			}
		}
		
		@Override @SideOnly(Side.CLIENT) public void setPositionAndRotation2(double posX, double posY, double posZ, float yaw, float pitch, int turnProg) {
			this.trainX = posX;
			this.trainY = posY;
			this.trainZ = posZ;
			this.turnProgress = turnProg + 2;
		}
	}
	
	public DummyConfig[] getDummies() {
		return new DummyConfig[0];
	}
	
	public static class DummyConfig {
		public Vec3 offset;
		public float width;
		public float height;
		
		public DummyConfig(float width, float height, Vec3 offset) {
			this.width = width;
			this.height = height;
			this.offset = offset;
		}
	}
	
	public static enum TrainCoupling {
		FRONT,
		BACK
	}
	
	public double getCouplingDist(TrainCoupling coupling) {
		return 0D;
	}
	
	public Vec3 getCouplingPos(TrainCoupling coupling) {
		double dist = this.getCouplingDist(coupling);
		
		if(dist <= 0) return null;
		
		Vec3 rot = Vec3.createVectorHelper(0, 0, dist);
		rot.rotateAroundY((float) (-this.rotationYaw * Math.PI / 180D));
		rot.xCoord += this.renderX;
		rot.yCoord += this.renderY;
		rot.zCoord += this.renderZ;
		return rot;
	}
	
	public EntityRailCarBase getCoupledTo(TrainCoupling coupling) {
		return coupling == TrainCoupling.FRONT ? this.coupledFront : coupling == TrainCoupling.BACK ? this.coupledBack : null;
	}
	
	public void couple(TrainCoupling coupling, EntityRailCarBase to) {
		if(coupling == TrainCoupling.FRONT) this.coupledFront = to;
		if(coupling == TrainCoupling.BACK) this.coupledBack = to;
	}
}
