package com.hbm.entity.projectile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.logic.IChunkLoader;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class EntityBulletBaseMK4CL extends EntityBulletBaseMK4 implements IChunkLoader {

	private Ticket loaderTicket;
	private List<ChunkCoordIntPair> loadedChunks = new ArrayList<ChunkCoordIntPair>();

	public EntityBulletBaseMK4CL(World world) {
		super(world);
	}

	public EntityBulletBaseMK4CL(EntityLivingBase entity, BulletConfig config, float damage, float spread, double sideOffset, double heightOffset, double forwardOffset) {
		super(entity, config, damage, spread, sideOffset, heightOffset, forwardOffset);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, worldObj, Type.ENTITY));
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
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!worldObj.isRemote) loadNeighboringChunks((int)Math.floor(posX / 16D), (int)Math.floor(posZ / 16D));
	}

	@Override
	public void setDead() {
		super.setDead();
		clearChunkLoader();
	}
	
	public void clearChunkLoader() {
		if(!worldObj.isRemote && loaderTicket != null) {
			for(ChunkCoordIntPair chunk : loadedChunks) {
				ForgeChunkManager.unforceChunk(loaderTicket, chunk);
			}
		}
	}

	public void loadNeighboringChunks(int newChunkX, int newChunkZ) {
		if(!worldObj.isRemote && loaderTicket != null) {
			
			clearChunkLoader();
			loadedChunks.clear();
			loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ));

			for(ChunkCoordIntPair chunk : loadedChunks) {
				ForgeChunkManager.forceChunk(loaderTicket, chunk);
			}
		}
	}
}
