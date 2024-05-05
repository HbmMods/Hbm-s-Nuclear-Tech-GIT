package com.hbm.entity.train;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.rail.IRailNTM;
import com.hbm.blocks.rail.IRailNTM.MoveContext;
import com.hbm.blocks.rail.IRailNTM.RailCheckType;
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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public abstract class EntityRailCarBase extends Entity implements ILookOverlay {

	public LogicalTrainUnit ltu;
	public int ltuIndex = 0;
	public boolean isOnRail = true;
	private int turnProgress;
	/* Clientside position that should be approached with smooth interpolation */
	private double trainX;
	private double trainY;
	private double trainZ;
	private double trainYaw;
	private double trainPitch;
	private float movementYaw;
	private float movementPitch;
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
	public double cachedSpeed;

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
					if(this.ltu != null) this.ltu.dissolveTrain();
					if(neighbor.ltu != null) neighbor.ltu.dissolveTrain();
					player.swingItem();
					
					player.addChatComponentMessage(new ChatComponentText("Coupled " + this.hashCode() + " (" + closestOwnCoupling.name() + ") to " + neighbor.hashCode() + " (" + closestNeighborCoupling.name() + ")"));
					
					return true;
				}
			}
		}
		
		//DEBUG
		if(this.ltu != null) {
			
			String id = Integer.toHexString(ltu.hashCode());
			
			for(EntityRailCarBase train : ltu.trains) {

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "debug");
				data.setInteger("color", 0x0000ff);
				data.setFloat("scale", 1.5F);
				data.setString("text", id + " (#" + train.ltuIndex + ")");
				//PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, train.posX, train.posY + 1, train.posZ), new TargetPoint(this.dimension, train.posX, train.posY + 1, train.posZ, 50));
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
			
			BlockPos anchor = this.getCurrentAnchorPos();
			Vec3 frontPos = getRelPosAlongRail(anchor, this.getLengthSpan(), new MoveContext(RailCheckType.FRONT, this.getCollisionSpan() - this.getLengthSpan()));
			Vec3 backPos = getRelPosAlongRail(anchor, -this.getLengthSpan(), new MoveContext(RailCheckType.BACK, this.getCollisionSpan() - this.getLengthSpan()));

			this.lastRenderX = this.renderX;
			this.lastRenderY = this.renderY;
			this.lastRenderZ = this.renderZ;
			
			if(frontPos != null && backPos != null) {
				this.renderX = (frontPos.xCoord + backPos.xCoord) / 2D;
				this.renderY = (frontPos.yCoord + backPos.yCoord) / 2D;
				this.renderZ = (frontPos.zCoord + backPos.zCoord) / 2D;
			} else {
				this.renderX = posX;
				this.renderY = posY;
				this.renderZ = posZ;
			}
			
		} else {
			
			if(!this.isOnRail) {
				if(this.coupledFront != null) this.coupledFront.couple(this.coupledFront.getCouplingFrom(this), null);
				if(this.coupledBack != null) this.coupledBack.couple(this.coupledBack.getCouplingFrom(this), null);
				this.coupledFront = null;
				this.coupledBack = null;
			}

			if(this.coupledFront != null && this.coupledFront.isDead) {
				this.coupledFront = null;
				if(this.ltu != null) this.ltu.dissolveTrain();
			}
			if(this.coupledBack != null && this.coupledBack.isDead) {
				this.coupledBack = null;
				if(this.ltu != null) this.ltu.dissolveTrain();
			}
			
			if(this.ltu == null && (this.coupledFront == null || this.coupledBack == null) && this.isOnRail) {
				LogicalTrainUnit.generateTrain(this);
			}
			
			if(!this.isOnRail) {
				Vec3 motion = Vec3.createVectorHelper(0, 0, this.cachedSpeed);
				motion.rotateAroundY((float) (-this.rotationYaw * Math.PI / 180D));
				this.moveEntity(motion.xCoord, motion.yCoord - 0.04, motion.zCoord);
				this.renderX = posX;
				this.renderY = posY;
				this.renderZ = posZ;
				this.cachedSpeed *= 0.95D;
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
					dummy.setSize(def.width, def.height);
					worldObj.spawnEntityInWorld(dummy);
					this.dummies[i] = dummy;
				}
				
				this.initDummies = true;
			}
			
			if(renderY != 0) {
				for(int i = 0; i < definitions.length; i++) {
					DummyConfig def = definitions[i];
					BoundingBoxDummyEntity dummy = dummies[i];
					Vec3 rot = Vec3.createVectorHelper(def.offset.xCoord, def.offset.yCoord, def.offset.zCoord);
					rot.rotateAroundX((float) (this.rotationPitch * Math.PI / 180D));
					rot.rotateAroundY((float) (-this.rotationYaw * Math.PI / 180));
					double x = renderX + rot.xCoord;
					double y = renderY + rot.yCoord;
					double z = renderZ + rot.zCoord;
					dummy.setPosition(x, y, z);
				}
			}
		}
	}
	
	public Vec3 getRelPosAlongRail(BlockPos anchor, double distanceToCover, MoveContext context) {
		return getRelPosAlongRail(anchor, distanceToCover, this.getGauge(), this.worldObj, Vec3.createVectorHelper(posX, posY, posZ), this.rotationYaw, context);
	}
	
	public static Vec3 getRelPosAlongRail(BlockPos anchor, double distanceToCover, TrackGauge gauge, World worldObj, Vec3 next, float yaw, MoveContext context) {
		
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
					next = rail.getTravelLocation(worldObj, x, y, z, next.xCoord, next.yCoord, next.zCoord, rot.xCoord, rot.yCoord, rot.zCoord, 0, new RailContext(), context);
				}
				
				boolean flip = distanceToCover < 0;
				
				if(rail.getGauge(worldObj, x, y, z) == gauge) {
					RailContext info = new RailContext();
					Vec3 prev = next;
					next = rail.getTravelLocation(worldObj, x, y, z, prev.xCoord, prev.yCoord, prev.zCoord, rot.xCoord, rot.yCoord, rot.zCoord, distanceToCover, info, context);
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
		//for(LogicalTrainUnit ltu : ltus) ltu.combineWagons();
		
		/* Move carts with unified speed */
		//for(LogicalTrainUnit ltu : ltus) ltu.moveTrain();
		
		for(LogicalTrainUnit ltu : ltus) {
			
			double speed = ltu.getTotalSpeed() + ltu.pushForce;
			
			if(Math.abs(speed) < 0.001) speed = 0;
			
			for(EntityRailCarBase car : ltu.trains) car.cachedSpeed = speed;
			
			if(ltu.trains.length == 1) {

				EntityRailCarBase train = ltu.trains[0];
				
				BlockPos anchor = new BlockPos(train.posX, train.posY, train.posZ);
				Vec3 newPos = train.getRelPosAlongRail(anchor, speed, new MoveContext(RailCheckType.CORE, 0));
				if(newPos == null) {
					train.derail();
					ltu.dissolveTrain();
					continue;
				}
				train.setPosition(newPos.xCoord, newPos.yCoord, newPos.zCoord);
				anchor = train.getCurrentAnchorPos();
				Vec3 frontPos = train.getRelPosAlongRail(anchor, train.getLengthSpan(), new MoveContext(RailCheckType.FRONT, train.getCollisionSpan() - train.getLengthSpan()));
				Vec3 backPos = train.getRelPosAlongRail(anchor, -train.getLengthSpan(), new MoveContext(RailCheckType.BACK, train.getCollisionSpan() - train.getLengthSpan()));

				if(frontPos == null || backPos == null) {
					train.derail();
					ltu.dissolveTrain();
					continue;
				} else {
					ltu.setRenderPos(train, frontPos, backPos);
				}

				//ltu.pushForce *= 0.95;
				ltu.pushForce = 0;
				ltu.collideTrain(speed);
				
				continue;
			}
			
			if(speed == 0) {
				ltu.combineWagons();
			} else {
				ltu.moveTrainByApproach(speed);
			}
			
			//ltu.pushForce *= 0.95;
			ltu.pushForce = 0;
			ltu.collideTrain(speed);
		}
	}

	/** Returns the amount of blocks that the train should move per tick */
	public abstract double getCurrentSpeed();
	public abstract double getMaxRailSpeed();
	/** Returns the gauge of this train */
	public abstract TrackGauge getGauge();
	/** Returns the length between the core and one of the bogies */
	public abstract double getLengthSpan();
	/** Returns the length between the core and the collision points */
	public abstract double getCollisionSpan();
	/** Returns a collision box, usually smaller than the entity's AABB for rendering, which is used for colliding trains */
	/*public AxisAlignedBB getCollisionBox() {
		return this.boundingBox;
	}*/
	/** Returns a collision box used for block collisions when derailed */
	/*@Override public AxisAlignedBB getBoundingBox() {
		return this.boundingBox;
	}*/
	
	/** Returns the "true" position of the train, i.e. the block it wants to snap to */
	public BlockPos getCurrentAnchorPos() {
		return new BlockPos(posX, posY + 0.25, posZ);
	}
	
	public void derail() {
		isOnRail = false;
		//this.setDead();
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
		this.trainPitch = this.movementPitch;
	}

	@SideOnly(Side.CLIENT)
	public void setVelocity(double mX, double mY, double mZ) {
		this.movementYaw = (float) this.motionX * 360F;
		this.movementPitch = (float) this.motionY * 360F;
		this.velocityX = this.motionX = mX;
		this.velocityY = this.motionY = mY;
		this.velocityZ = this.motionZ = mZ;
	}
	
	/** Invisible entities that make up the dynamic bounding structure of the train, moving as the train rotates. */
	public static class BoundingBoxDummyEntity extends Entity implements ILookOverlay {

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
		@Override
		public void printHook(Pre event, World world, int x, int y, int z) {
			Entity e = worldObj.getEntityByID(this.dataWatcher.getWatchableObjectInt(3));
			if(e instanceof EntityRailCarBase) {
				((EntityRailCarBase) e).printHook(event, world, x, y, z);
			}
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
		
		protected double pushForce;
		protected EntityRailCarBase trains[];
		
		/** Assumes that the train is an endpoint, i.e. that only one coupling is in use */
		public static LogicalTrainUnit generateTrain(EntityRailCarBase train) {
			List<EntityRailCarBase> links = new ArrayList();
			Set<EntityRailCarBase> brake = new HashSet();
			LogicalTrainUnit ltu = new LogicalTrainUnit();
			
			if(train.coupledFront == null && train.coupledBack == null) {
				ltu.trains = new EntityRailCarBase[] {train};
				train.ltu = ltu;
				train.ltuIndex = 0;
				return ltu;
			}
			
			EntityRailCarBase current = train;
			EntityRailCarBase next = null;
			
			do {
				next = null;

				if(current.coupledFront != null && !brake.contains(current.coupledFront)) next = current.coupledFront;
				if(current.coupledBack != null && !brake.contains(current.coupledBack)) next = current.coupledBack;
				
				links.add(current);
				brake.add(current);
				
				current = next;
				
			} while(next != null);
			
			ltu.trains = new EntityRailCarBase[links.size()];
			for(int i = 0; i < ltu.trains.length; i++) {
				ltu.trains[i] = links.get(i);
				ltu.trains[i].ltu = ltu;
				ltu.trains[i].ltuIndex = i;
			}
			
			return ltu;
		}
		
		/** Removes the LTU from all wagons */
		public void dissolveTrain() {
			for(EntityRailCarBase train : trains) {
				train.ltu = null;
				train.ltuIndex = 0;
			}
		}
		
		/** Find the center fo the train, then moves all wagons towards that center until the coupling points roughly touch */
		public void combineWagons() {
			
			if(trains.length <= 1) return;
			
			boolean odd = trains.length % 2 == 1;
			int centerIndex = odd ? trains.length / 2 : trains.length / 2 - 1;
			EntityRailCarBase center = trains[centerIndex];
			EntityRailCarBase prev = center;
			
			for(int i = centerIndex - 1; i >= 0; i--) {
				EntityRailCarBase next = trains[i];
				moveWagonTo(prev, next);
				prev = next;
			}
			
			prev = center;
			for(int i = centerIndex + 1; i < trains.length; i++) {
				EntityRailCarBase next = trains[i];
				moveWagonTo(prev, next);
				prev = next;
			}
		}
		
		/** Moves one wagon to ne next until the coupling points roughly touch */
		public void moveWagonTo(EntityRailCarBase moveTo, EntityRailCarBase moving) {
			TrainCoupling prevCouple = moveTo.getCouplingFrom(moving);
			TrainCoupling nextCouple = moving.getCouplingFrom(moveTo);
			Vec3 prevLoc = moveTo.getCouplingPos(prevCouple);
			Vec3 nextLoc = moving.getCouplingPos(nextCouple);
			Vec3 delta = Vec3.createVectorHelper(prevLoc.xCoord - nextLoc.xCoord, 0, prevLoc.zCoord - nextLoc.zCoord);
			double len = delta.lengthVector();
			//len *= 0.25; //suspension, causes movements to be less rigid
			len = (len / (0.5D / (len * len) + 1D)); //smart suspension
			BlockPos anchor = new BlockPos(moving.posX, moving.posY, moving.posZ);
			Vec3 trainPos = Vec3.createVectorHelper(moving.posX, moving.posY, moving.posZ);
			float yaw = EntityRailCarBase.generateYaw(prevLoc, nextLoc);
			Vec3 newPos = EntityRailCarBase.getRelPosAlongRail(anchor, len, moving.getGauge(), moving.worldObj, trainPos, yaw, new MoveContext(RailCheckType.CORE, 0));
			moving.setPosition(newPos.xCoord, newPos.yCoord, newPos.zCoord);
			anchor = moving.getCurrentAnchorPos(); //reset origin to new position
			Vec3 frontPos = moving.getRelPosAlongRail(anchor, moving.getLengthSpan(), new MoveContext(RailCheckType.FRONT, moving.getCollisionSpan() - moving.getLengthSpan()));
			Vec3 backPos = moving.getRelPosAlongRail(anchor, -moving.getLengthSpan(), new MoveContext(RailCheckType.BACK, moving.getCollisionSpan() - moving.getLengthSpan()));

			if(frontPos == null || backPos == null) {
				moving.derail();
				this.dissolveTrain();
				return;
			} else {
				setRenderPos(moving, frontPos, backPos);
			}
		}
		
		/** Generates the speed of the train, then moves the rain along the rail */
		@Deprecated public void moveTrain() {
			
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
			
			this.moveTrainBy(totalSpeed);
		}
		
		/** Moves the entire train along the rail by a certain speed */
		@Deprecated public void moveTrainBy(double totalSpeed) {
			
			for(EntityRailCarBase train : this.trains) {
				
				BlockPos anchor = train.getCurrentAnchorPos();
				Vec3 corePos = train.getRelPosAlongRail(anchor, totalSpeed, new MoveContext(RailCheckType.CORE, 0));
				
				if(corePos == null) {
					train.derail();
					this.dissolveTrain();
					return;
				} else {
					train.setPosition(corePos.xCoord, corePos.yCoord, corePos.zCoord);
					anchor = train.getCurrentAnchorPos(); //reset origin to new position
					Vec3 frontPos = train.getRelPosAlongRail(anchor, train.getLengthSpan(), new MoveContext(RailCheckType.FRONT, 0));
					Vec3 backPos = train.getRelPosAlongRail(anchor, -train.getLengthSpan(), new MoveContext(RailCheckType.BACK, 0));

					if(frontPos == null || backPos == null) {
						train.derail();
						this.dissolveTrain();
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
		
		/** Returns the total speed of the LTU, negative if it is backwards compared to the arbitrary "front" wagon */
		public double getTotalSpeed() {
			
			EntityRailCarBase prev = trains[0];
			double totalSpeed = 0;
			double maxSpeed = Double.POSITIVE_INFINITY;
			//if the first car is in reverse, flip all subsequent cars as well
			boolean reverseTheReverse = prev.getCouplingFrom(null) == TrainCoupling.BACK;
			
			if(trains.length == 1) {
				return prev.getCurrentSpeed();
			}
			
			for(EntityRailCarBase train : this.trains) {
				//if the car's linked indices are the wrong way, it is in reverse and speed applies negatively
				boolean reverse = false;

				EntityRailCarBase conFront = train.getCoupledTo(TrainCoupling.FRONT);
				EntityRailCarBase conBack = train.getCoupledTo(TrainCoupling.BACK);

				if(conFront != null && conFront.ltuIndex > train.ltuIndex) reverse = true;
				if(conBack != null && conBack.ltuIndex < train.ltuIndex) reverse = true;
				
				reverse ^= reverseTheReverse;
				
				double speed = train.getCurrentSpeed();
				if(reverse) speed *= -1;
				totalSpeed += speed;
				maxSpeed = Math.min(maxSpeed, train.getMaxRailSpeed());
				prev = train;
			}
			
			if(Math.abs(totalSpeed) > maxSpeed) {
				totalSpeed = maxSpeed * Math.signum(totalSpeed);
			}
			
			return totalSpeed;
		}
		
		/*
		 * This method has no rhyme or reason behind it. Nothing of this was calculated, instead it was an old system that worked with older constraints,
		 * which was retrofitted with a slightly newer system and beaten into submission for two consecutive hours until it yielded the results it should.
		 * Booleans are flipped back and forth based on seemingly random conditions, numbers are inverted and then inverted again and finally smashed into
		 * the rail system in the hopes that it would make trains work. My apologies extend towards Bob in the future who will inevitably have to rewrite this
		 * abhorrence because of some constraint change which will cause the entire system to break. Part of me wishes to never touch the train code ever again,
		 * to abandon the idea and to ban the annoying people on Discord who keep asking about it. Another part wants me to slam my head against this project
		 * until either it or my skull gives way; and considering I got this far, it appears as if this side is the one that is winning.
		 */
		/** Determines the "front" wagon based on the movement and moves it, then moves all other wagons towards that */
		public void moveTrainByApproach(double speed) {
			EntityRailCarBase previous = null;
			EntityRailCarBase first = this.trains[0];
			boolean forward = speed > 0;
			boolean order = forward ^ first.getCouplingFrom(null) == TrainCoupling.BACK;
			
			for(int i = order ? 0 : this.trains.length - 1; order ? i < this.trains.length : i >= 0; i += order ? 1 : -1) {
				EntityRailCarBase current = this.trains[i];
				
				if(previous == null) {
					
					if(first == current) speed *= -1;
					
					boolean inReverse = first.getCouplingFrom(null) == current.getCouplingFrom(null);
					int sigNum = inReverse ? 1 : -1;
					BlockPos anchor = current.getCurrentAnchorPos();
					
					Vec3 frontPos = current.getRelPosAlongRail(anchor, (speed + current.getLengthSpan()) * -sigNum, new MoveContext(RailCheckType.FRONT, current.getCollisionSpan() - current.getLengthSpan()));
					
					if(frontPos == null) {
						current.derail();
						this.dissolveTrain();
						return;
					} else {
						anchor = current.getCurrentAnchorPos(); //reset origin to new position
						Vec3 corePos = current.getRelPosAlongRail(anchor, speed * -sigNum, new MoveContext(RailCheckType.CORE, 0));
						current.setPosition(corePos.xCoord, corePos.yCoord, corePos.zCoord);
						Vec3 backPos = current.getRelPosAlongRail(anchor, (speed - current.getLengthSpan()) * -sigNum, new MoveContext(RailCheckType.BACK, current.getCollisionSpan() - current.getLengthSpan()));

						if(frontPos == null || backPos == null) {
							current.derail();
							this.dissolveTrain();
							return;
						} else {
							setRenderPos(current, inReverse ? backPos : frontPos, inReverse ? frontPos : backPos);
						}
					}
					
				} else {
					this.moveWagonTo(previous, current);
				}
				
				previous = current;
			}
		}
		
		/** Uses the front and back bogey positions to set the render pos and angles of a wagon */
		public void setRenderPos(EntityRailCarBase current, Vec3 frontPos, Vec3 backPos) {
			current.renderX = (frontPos.xCoord + backPos.xCoord) / 2D;
			current.renderY = (frontPos.yCoord + backPos.yCoord) / 2D;
			current.renderZ = (frontPos.zCoord + backPos.zCoord) / 2D;
			current.prevRotationYaw = current.rotationYaw;
			current.rotationYaw = current.movementYaw = generateYaw(frontPos, backPos);
			Vec3 delta = Vec3.createVectorHelper(frontPos.xCoord - backPos.xCoord, frontPos.yCoord - backPos.yCoord, frontPos.zCoord - backPos.zCoord);
			current.rotationPitch = current.movementPitch = (float) (Math.asin(delta.yCoord / delta.lengthVector()) * 180D / Math.PI);
			current.motionX = current.rotationYaw / 360D; // hijacking this crap for easy syncing
			current.motionY = current.rotationPitch / 360D;
			current.velocityChanged = true;
		}
		
		public void collideTrain(double speed) {
			EntityRailCarBase collidingTrain = speed > 0 ? trains[0] : trains[trains.length - 1];
			List<EntityRailCarBase> intersect = collidingTrain.worldObj.getEntitiesWithinAABB(EntityRailCarBase.class, collidingTrain.boundingBox.expand(1, 1, 1));
			EntityRailCarBase collidesWith = null;
			
			for(EntityRailCarBase train : intersect) {
				if(train.ltu != null && train.ltu != this) {
					collidesWith = train;
					break;
				}
			}
			
			if(collidesWith == null) return;
			
			Vec3 delta = Vec3.createVectorHelper(collidingTrain.posX - collidesWith.posX, 0, collidingTrain.posZ - collidesWith.posZ);
			double totalSpan = collidingTrain.getCollisionSpan() + collidesWith.getCollisionSpan();
			double diff = delta.lengthVector();
			if(diff > totalSpan) return;
			double push = (totalSpan - diff);
			
			//PacketDispatcher.wrapper.sendToAllAround(new PlayerInformPacket(ChatBuilder.start("" + collidesWith.ltuIndex + " " + collidingTrain.ltuIndex).color(EnumChatFormatting.RED).flush(), 1),
			//		new TargetPoint(collidingTrain.dimension, collidingTrain.posX, collidingTrain.posY + 1, collidingTrain.posZ, 50));
			
			EntityRailCarBase[][] whatever = new EntityRailCarBase[][] {{collidingTrain, collidesWith}, {collidesWith, collidingTrain}};
			for(EntityRailCarBase[] array : whatever) {
				LogicalTrainUnit ltu = array[0].ltu;
				if(ltu.trains.length == 1) {
					Vec3 rot = Vec3.createVectorHelper(0, 0, array[0].getCollisionSpan());
					rot.rotateAroundX((float) (array[0].rotationPitch * Math.PI / 180D));
					rot.rotateAroundY((float) (-array[0].rotationYaw * Math.PI / 180));
					Vec3 forward = Vec3.createVectorHelper(array[1].posX - (array[0].posX + rot.xCoord), 0, array[1].posZ - (array[0].posZ + rot.zCoord));
					Vec3 backward = Vec3.createVectorHelper(array[1].posX - (array[0].posX - rot.xCoord), 0, array[1].posZ - (array[0].posZ - rot.zCoord));
					
					if(forward.lengthVector() > backward.lengthVector()) {
						ltu.pushForce += push;
					} else {
						ltu.pushForce -= push;
					}
				} else {
					
					if(array[0].ltuIndex < ltu.trains.length / 2) {
						ltu.pushForce -= push;
					} else {
						ltu.pushForce += push;
					}
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void printHook(RenderGameOverlayEvent.Pre event, World world, int x, int y, int z) {
		//List<String> text = new ArrayList();
		//ILookOverlay.printGeneric(event, this.getClass().getSimpleName() + " " + this.hashCode(), 0xffff00, 0x404000, text);
	}
}
