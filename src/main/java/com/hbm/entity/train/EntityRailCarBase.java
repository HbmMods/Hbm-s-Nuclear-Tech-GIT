package com.hbm.entity.train;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.blocks.rail.IRailNTM;
import com.hbm.blocks.rail.IRailNTM.RailContext;
import com.hbm.blocks.rail.IRailNTM.TrackGauge;
import com.hbm.items.ModItems;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
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

	public LogicalTrainUnit ltu;
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
					if(this.ltu != null) this.ltu.dissolve();
					if(neighbor.ltu != null) neighbor.ltu.dissolve();
					player.swingItem();
					return true;
				}
			}
		}
		
		if(this.ltu != null) {
			
			String id = Integer.toHexString(ltu.hashCode());
			
			for(EntityRailCarBase train : ltu.trains) {

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "debug");
				data.setInteger("color", 0x0000ff);
				data.setFloat("scale", 1.5F);
				data.setString("text", id);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, train.posX, train.posY + 1, train.posZ), new TargetPoint(this.dimension, train.posX, train.posY + 1, train.posZ, 50));
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

			if(this.coupledFront != null && this.coupledFront.isDead) {
				this.coupledFront = null;
				if(this.ltu != null) this.ltu.dissolve();
			}
			if(this.coupledBack != null && this.coupledBack.isDead) {
				this.coupledBack = null;
				if(this.ltu != null) this.ltu.dissolve();
			}
			
			if(this.ltu == null && (this.coupledFront == null || this.coupledBack == null)) {
				LogicalTrainUnit.generate(this);
			}
			
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
		return getRelPosAlongRail(anchor, distanceToCover, this.getGauge(), this.worldObj, Vec3.createVectorHelper(posX, posY, posZ), this.rotationYaw);
	}
	
	public static Vec3 getRelPosAlongRail(BlockPos anchor, double distanceToCover, TrackGauge gauge, World worldObj, Vec3 next, float yaw) {
		
		if(distanceToCover < 0) {
			distanceToCover *= -1;
			yaw += 180;
		}
		
		int it = 0;
		
		do {
			
			it++;
			
			if(it > 30) {
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
				
				if(rail.getGauge(worldObj, x, y, z) == gauge) {
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
	
	public static float generateYaw(Vec3 front, Vec3 back) {
		double deltaX = front.xCoord - back.xCoord;
		double deltaZ = front.zCoord - back.zCoord;
		double radians = -Math.atan2(deltaX, deltaZ);
		return (float) MathHelper.wrapAngleTo180_double(radians * 180D / Math.PI);
	}
	
	public static void updateMotion(World world) {
		Set<LogicalTrainUnit> ltus = new HashSet();
		
		/* gather all LTUs */
		for(Object o : world.loadedEntityList) {
			if(o instanceof EntityRailCarBase) {
				EntityRailCarBase train = (EntityRailCarBase) o;
				if(train.ltu != null) ltus.add(train.ltu);
			}
		}
		
		/* Move carts together with links */
		for(LogicalTrainUnit ltu : ltus) ltu.combineLinks();
		
		/* Move carts with unified speed */
		for(LogicalTrainUnit ltu : ltus) ltu.moveLinks();
	}

	/** Returns the amount of blocks that the train should move per tick */
	public abstract double getCurrentSpeed();
	public abstract double getMaxRailSpeed();
	/** Returns the gauge of this train */
	public abstract TrackGauge getGauge();
	/** Returns the length between the core and one of the bogies */
	public abstract double getLengthSpan();
	/** Returns a collision box, usually smaller than the entity's AABB for rendering, which is used for colliding trains */
	public AxisAlignedBB getCollisionBox() {
		return this.boundingBox;
	}
	/** Returns a collision box used for block collisions when derailed */
	/*@Override public AxisAlignedBB getBoundingBox() {
		return this.boundingBox;
	}*/
	
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
		
		if(coupling == TrainCoupling.BACK) dist *= -1;
		
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
	
	public TrainCoupling getCouplingFrom(EntityRailCarBase coupledTo) {
		return coupledTo == this.coupledFront ? TrainCoupling.FRONT : coupledTo == this.coupledBack ? TrainCoupling.BACK : null;
	}
	
	public void couple(TrainCoupling coupling, EntityRailCarBase to) {
		if(coupling == TrainCoupling.FRONT) this.coupledFront = to;
		if(coupling == TrainCoupling.BACK) this.coupledBack = to;
	}
	
	public static class LogicalTrainUnit {
		
		protected EntityRailCarBase trains[];
		
		/** Assumes that the train is an endpoint, i.e. that only one coupling is in use */
		public static LogicalTrainUnit generate(EntityRailCarBase train) {
			List<EntityRailCarBase> links = new ArrayList();
			Set<EntityRailCarBase> brake = new HashSet();
			links.add(train);
			brake.add(train);
			LogicalTrainUnit ltu = new LogicalTrainUnit();
			
			if(train.coupledFront == null && train.coupledFront == null) {
				ltu.trains = new EntityRailCarBase[] {train};
				train.ltu = ltu;
				return ltu;
			}
			
			EntityRailCarBase prevCar = train;
			EntityRailCarBase nextCar = train.coupledBack == null ? train.coupledFront : train.coupledBack;
			
			while(nextCar != null) {
				links.add(nextCar);
				brake.add(nextCar);
				
				EntityRailCarBase currentCar = nextCar;
				nextCar = nextCar.coupledBack == prevCar ? nextCar.coupledFront : nextCar.coupledBack;
				prevCar = currentCar;
				
				if(brake.contains(nextCar)) {
					break;
				}
			}
			
			ltu.trains = new EntityRailCarBase[links.size()];
			
			for(int i = 0; i < ltu.trains.length; i++) {
				ltu.trains[i] = links.get(i);
				ltu.trains[i].ltu = ltu;
			}
			
			return ltu;
		}
		
		public void dissolve() {
			for(EntityRailCarBase train : trains) {
				train.ltu = null;
			}
		}
		
		public void combineLinks() {
			
			if(trains.length <= 1) return;
			
			boolean odd = trains.length % 2 == 1;
			int centerIndex = odd ? trains.length / 2 : trains.length / 2 - 1;
			EntityRailCarBase center = trains[centerIndex];
			EntityRailCarBase prev = center;
			
			for(int i = centerIndex - 1; i >= 0; i--) {
				EntityRailCarBase next = trains[i];
				moveTo(prev, next);
				prev = next;
			}
			
			prev = center;
			for(int i = centerIndex + 1; i < trains.length; i++) {
				EntityRailCarBase next = trains[i];
				moveTo(prev, next);
				prev = next;
			}
		}
		
		public static void moveTo(EntityRailCarBase prev, EntityRailCarBase next) {
			TrainCoupling prevCouple = prev.getCouplingFrom(next);
			TrainCoupling nextCouple = next.getCouplingFrom(prev);
			Vec3 prevLoc = prev.getCouplingPos(prevCouple);
			Vec3 nextLoc = next.getCouplingPos(nextCouple);
			Vec3 delta = Vec3.createVectorHelper(prevLoc.xCoord - nextLoc.xCoord, 0, prevLoc.zCoord - nextLoc.zCoord);
			double len = delta.lengthVector();
			len *= 0.25D; //suspension, causes movements to be less rigid
			BlockPos anchor = new BlockPos(next.posX, next.posY, next.posZ);
			Vec3 trainPos = Vec3.createVectorHelper(next.posX, next.posY, next.posZ);
			float yaw = EntityRailCarBase.generateYaw(prevLoc, nextLoc);
			Vec3 newPos = EntityRailCarBase.getRelPosAlongRail(anchor, len, next.getGauge(), next.worldObj, trainPos, yaw);
			next.setPosition(newPos.xCoord, newPos.yCoord, newPos.zCoord);
		}
		
		public void moveLinks() {
			
			EntityRailCarBase prev = trains[0];
			TrainCoupling dir = prev.getCouplingFrom(null);
			double totalSpeed = 0;
			double maxSpeed = Double.POSITIVE_INFINITY;
			
			for(EntityRailCarBase train : this.trains) {
				boolean con = train.getCouplingFrom(prev) == dir;
				double speed = train.getCurrentSpeed();
				if(!con) speed *= -1;
				totalSpeed += speed;
				maxSpeed = Math.min(maxSpeed, train.getMaxRailSpeed());
				prev = train;
			}
			
			if(Math.abs(totalSpeed) > maxSpeed) {
				totalSpeed = maxSpeed * Math.signum(totalSpeed);
			}
			
			for(EntityRailCarBase train : this.trains) {
				
				BlockPos anchor = train.getCurentAnchorPos();
				Vec3 corePos = train.getRelPosAlongRail(anchor, totalSpeed);
				
				if(corePos == null) {
					train.derail();
					this.dissolve();
					return;
				} else {
					train.setPosition(corePos.xCoord, corePos.yCoord, corePos.zCoord);
					anchor = train.getCurentAnchorPos(); //reset origin to new position
					Vec3 frontPos = train.getRelPosAlongRail(anchor, train.getLengthSpan());
					Vec3 backPos = train.getRelPosAlongRail(anchor, -train.getLengthSpan());

					if(frontPos == null || backPos == null) {
						train.derail();
						this.dissolve();
						return;
					} else {
						train.renderX = (frontPos.xCoord + backPos.xCoord) / 2D;
						train.renderY = (frontPos.yCoord + backPos.yCoord) / 2D;
						train.renderZ = (frontPos.zCoord + backPos.zCoord) / 2D;
						train.prevRotationYaw = train.rotationYaw;
						train.rotationYaw = train.movementYaw = generateYaw(frontPos, backPos);
						train.motionX = train.rotationYaw / 360D; // hijacking this crap for easy syncing
						train.velocityChanged = true;
					}
				}
			}
		}
	}
}
