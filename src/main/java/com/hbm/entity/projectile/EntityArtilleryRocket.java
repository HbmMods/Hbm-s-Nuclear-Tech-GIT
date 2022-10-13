package com.hbm.entity.projectile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.logic.IChunkLoader;
import com.hbm.entity.projectile.rocketbehavior.IRocketSteeringBehavior;
import com.hbm.entity.projectile.rocketbehavior.IRocketTargetingBehavior;
import com.hbm.items.weapon.ItemAmmoHIMARS;
import com.hbm.items.weapon.ItemAmmoHIMARS.HIMARSRocket;
import com.hbm.main.MainRegistry;

import api.hbm.entity.IRadarDetectable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
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
	private Entity targetEntity = null;
	private Vec3 lastTargetPos;
	
	private IRocketTargetingBehavior targeting;
	private IRocketSteeringBehavior steering;
	
	public EntityArtilleryRocket(World world) {
		super(world);
		this.ignoreFrustumCheck = true;
	}

	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, worldObj, Type.ENTITY));
		this.dataWatcher.addObject(10, new Integer(0));
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
		super.onUpdate();
		
		if(!worldObj.isRemote) {
			
			if(this.targetEntity != null) this.targeting.recalculateTargetPosition(this, this.targetEntity);
			this.steering.adjustCourse(this);
			
			loadNeighboringChunks((int)Math.floor(posX / 16D), (int)Math.floor(posZ / 16D));
			this.getType().onUpdate(this);
		}
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
			
			clearChunkLoader();

			loadedChunks.clear();
			loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ));
			loadedChunks.add(new ChunkCoordIntPair(newChunkX + (int) Math.ceil((this.posX + this.motionX) / 16D), newChunkZ + (int) Math.ceil((this.posZ + this.motionZ) / 16D)));

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
			for(ChunkCoordIntPair chunk : loadedChunks) {
				ForgeChunkManager.unforceChunk(loaderTicket, chunk);
			}
		}
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.ARTILLERY;
	}
}
