package com.hbm.entity.projectile;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableSet;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.entity.projectile.rocketbehavior.IRocketSteeringBehavior;
import com.hbm.entity.projectile.rocketbehavior.IRocketTargetingBehavior;
import com.hbm.entity.projectile.rocketbehavior.RocketSteeringBallisticArc;
import com.hbm.entity.projectile.rocketbehavior.RocketTargetingPredictive;
import com.hbm.items.weapon.ItemAmmoHIMARS;
import com.hbm.items.weapon.ItemAmmoHIMARS.HIMARSRocket;
import com.hbm.main.MainRegistry;
import com.hbm.util.Vec3NT;

import api.hbm.entity.IRadarDetectable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class EntityArtilleryRocket extends EntityThrowableInterp implements IChunkLoader, IRadarDetectable {

	private Ticket loaderTicket;

	//TODO: find satisfying solution for when an entity is unloaded and reloaded, possibly a custom entity lookup using persistent UUIDs
	public Entity targetEntity = null;
	public Vec3 lastTargetPos;

	public IRocketTargetingBehavior targeting;
	public IRocketSteeringBehavior steering;
	public int health = 100;

	public EntityArtilleryRocket(World world) {
		super(world);
		this.ignoreFrustumCheck = true;

		this.targeting = new RocketTargetingPredictive();
		this.steering = new RocketSteeringBallisticArc();
	}

	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, worldObj, Type.ENTITY));
		this.dataWatcher.addObject(10, new Integer(0));
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(this.isEntityInvulnerable()) {
			return false;
		} else {
			if(this.health > 0 && !this.worldObj.isRemote) {
				this.health -= (int) amount;
				
				if(this.health <= 0) {
					this.setDead();
				}
			}
			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}

	public EntityArtilleryRocket setType(int type) {
		this.dataWatcher.updateObject(10, type);
		return this;
	}

	public HIMARSRocket getType() {
		try {
			return ItemAmmoHIMARS.itemTypes[this.dataWatcher.getWatchableObjectInt(10)];
		} catch(Exception ex) {
			return ItemAmmoHIMARS.itemTypes[0];
		}
	}

	public EntityArtilleryRocket setTarget(Entity target) {
		this.targetEntity = target;
		setTarget(target.posX, target.posY - target.yOffset + target.height / 2D, target.posZ);
		return this;
	}

	public EntityArtilleryRocket setTarget(double x, double y, double z) {
		this.lastTargetPos = Vec3.createVectorHelper(x, y, z);
		return this;
	}

	public Vec3 getLastTarget() {
		return this.lastTargetPos;
	}

	@Override
	public void onUpdate() {

		if(worldObj.isRemote) {
			this.lastTickPosX = this.posX;
			this.lastTickPosY = this.posY;
			this.lastTickPosZ = this.posZ;
		}

		super.onUpdate();

		if(!worldObj.isRemote) {
			
			Vec3NT delta = new Vec3NT(this.lastTargetPos.xCoord - this.posX, this.lastTargetPos.yCoord - this.posY, this.lastTargetPos.zCoord - this.posZ);
			double momentum = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ) * motionMult();
			if(delta.lengthVector() <= momentum * 1.5) {
				disableSteering(delta, momentum);
			} else {
				if(this.targeting != null && this.targetEntity != null) this.targeting.recalculateTargetPosition(this, this.targetEntity);
				if(this.steering != null) this.steering.adjustCourse(this, 10D, 15D);
			}

			loadNeighboringChunks((int)Math.floor(posX / 16D), (int)Math.floor(posZ / 16D));
			this.getType().onUpdate(this);
		}
	}

	protected double lastRenderX = 0;
	protected double lastRenderY = 0;
	protected double lastRenderZ = 0;
	protected Vec3NT delta = new Vec3NT(0, 0, 0);
	protected double path = 0D;
	
	// this doesn't work as well as i was hoping it would
	public void onRenderTick(float interp) {

		double x = this.lastTickPosX + (this.posX - this.lastTickPosX) * interp;
		double y = this.lastTickPosY + (this.posY - this.lastTickPosY) * interp;
		double z = this.lastTickPosZ + (this.posZ - this.lastTickPosZ) * interp;

		if(lastRenderX == 0) lastRenderX = x;
		if(lastRenderY == 0) lastRenderY = y;
		if(lastRenderZ == 0) lastRenderZ = z;
		
		delta.setComponents(lastRenderX - posX, lastRenderY - posY, lastRenderZ - posZ);
		double stepSize = 4D;
		path += delta.lengthVector();
		delta.normalizeSelf();
		
		for(; path >= stepSize; path -= stepSize) {
			NBTTagCompound data = new NBTTagCompound();
			data.setDouble("posX", x - delta.xCoord * path);
			data.setDouble("posY", y - delta.yCoord * path);
			data.setDouble("posZ", z - delta.zCoord * path);
			data.setString("type", "exKerosene");
			MainRegistry.proxy.effectNT(data);
		}

		lastRenderX = x;
		lastRenderY = y;
		lastRenderZ = z;
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {

		if(!worldObj.isRemote) {
			this.getType().onImpact(this, mop);
		}
	}

	@Override
	public void init(Ticket ticket) {
		if(!worldObj.isRemote && ticket != null) {
			if(loaderTicket == null) {
				loaderTicket = ticket;
				loaderTicket.bindEntity(this);
				loaderTicket.getModData();
			}
			ForgeChunkManager.forceChunk(loaderTicket, new ChunkCoordIntPair(chunkCoordX, chunkCoordZ));
		}
	}

	List<ChunkCoordIntPair> loadedChunks = new ArrayList<ChunkCoordIntPair>();

	public void loadNeighboringChunks(int newChunkX, int newChunkZ) {
		if(!worldObj.isRemote && loaderTicket != null) {

			for(ChunkCoordIntPair chunk : ImmutableSet.copyOf(loaderTicket.getChunkList())) {
				ForgeChunkManager.unforceChunk(loaderTicket, chunk);
			}

			loadedChunks.clear();
			loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ));
			//loadedChunks.add(new ChunkCoordIntPair(newChunkX + (int) Math.floor((this.posX + this.motionX) / 16D), newChunkZ + (int) Math.floor((this.posZ + this.motionZ) / 16D)));

			for(ChunkCoordIntPair chunk : loadedChunks) {
				ForgeChunkManager.forceChunk(loaderTicket, chunk);
			}
		}
	}

	public void killAndClear() {
		this.setDead();
		this.clearChunkLoader();
	}

	public void clearChunkLoader() {
		if(!worldObj.isRemote && loaderTicket != null) {
			ForgeChunkManager.releaseTicket(loaderTicket);
			this.loaderTicket = null;
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);

		if(this.lastTargetPos == null) {
			this.lastTargetPos = Vec3.createVectorHelper(posX, posY, posZ);
		}

		nbt.setDouble("targetX", this.lastTargetPos.xCoord);
		nbt.setDouble("targetY", this.lastTargetPos.yCoord);
		nbt.setDouble("targetZ", this.lastTargetPos.zCoord);

		nbt.setInteger("type", this.dataWatcher.getWatchableObjectInt(10));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);

		this.lastTargetPos = Vec3.createVectorHelper(nbt.getDouble("targetX"), nbt.getDouble("targetY"), nbt.getDouble("targetZ"));

		this.dataWatcher.updateObject(10, nbt.getInteger("type"));
	}

	@Override
	protected float getAirDrag() {
		return 1.0F;
	}

	@Override
	public double getGravityVelocity() {
		return this.steering != null ? 0D : 0.01D;
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.ARTILLERY;
	}

	@Override
	public int approachNum() {
		return 0; //
	}
	
	public void disableSteering(Vec3NT delta, double momentum) {
		
		if(this.steering != null || this.targeting != null) {
			this.steering = null;
			this.targeting = null;
			
			delta.normalizeSelf();
			motionX = delta.xCoord * momentum / motionMult();
			motionY = delta.yCoord * momentum / motionMult();
			motionZ = delta.zCoord * momentum / motionMult();
		}
	}
}
