package com.hbm.entity.logic;

import com.hbm.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public abstract class EntityExplosionChunkloading extends Entity implements IChunkLoader {

	private Ticket loaderTicket;
	private ChunkCoordIntPair loadedChunk;

	public EntityExplosionChunkloading(World world) {
		super(world);
	}

	@Override
	protected void entityInit() {
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

	public void loadChunk(int x, int z) {
		
		if(this.loadedChunk == null) {
			this.loadedChunk = new ChunkCoordIntPair(x, z);
			ForgeChunkManager.forceChunk(loaderTicket, loadedChunk);
		}
	}
	
	public void clearChunkLoader() {
		if(!worldObj.isRemote && loaderTicket != null && loadedChunk != null) {
			ForgeChunkManager.releaseTicket(loaderTicket);
			this.loaderTicket = null;
		}
	}
}
