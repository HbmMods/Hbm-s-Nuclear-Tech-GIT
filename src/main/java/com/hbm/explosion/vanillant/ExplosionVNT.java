package com.hbm.explosion.vanillant;

import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

/**
 * Time to over-engineer this into fucking oblivion so that I never have to write a vanilla-esque explosion class ever again
 * @author hbm
 *
 */
public class ExplosionVNT {

	//explosions only need one of these, in the unlikely event that we do need to combine different types we can just write a wrapper that acts as a chainloader
	private IBlockAllocator blockAllocator;
	private IEntityAllocator entityAllocator;
	private IBlockProcessor blockProcessor;
	private IEntityProcessor entityProcessor;
	//since we want to reduce each effect to the bare minimum (sound, particles, etc. being separate) we definitely need multiple most of the time
	private IExplosionSFX[] sfx;
	
	protected World world;
	protected double posX;
	protected double posY;
	protected double posZ;
	protected float size;
	public Entity exploder;
	
	public ExplosionVNT(World world, double x, double y, double z, float size) {
		this(world, x, y, z, size, null);
	}
	
	public ExplosionVNT(World world, double x, double y, double z, float size, Entity exploder) {
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.size = size;
		this.exploder = exploder;
	}
	
	public void explode() {
		
		boolean processBlocks = blockAllocator != null && blockProcessor != null;
		boolean processEntities = entityAllocator != null && entityProcessor != null;
		
		HashSet<ChunkPosition> affectedBlocks = null;
		HashMap<Entity, Vec3> affectedEntities = null;
		
		if(processBlocks) affectedBlocks = blockAllocator.allocate(this, world, posX, posY, posZ, size);
		if(processEntities) affectedEntities = entityAllocator.allocate(this, world, posX, posY, posZ, size);
		
		if(processBlocks) blockProcessor.process(this, world, posX, posY, posZ, affectedBlocks);
		if(processEntities) entityProcessor.process(this, world, posX, posY, posZ, affectedEntities);
		
		if(sfx != null) {
			for(IExplosionSFX fx : sfx) {
				fx.doEffect(this, world, posX, posY, posZ, size);
			}
		}
	}
	
	public ExplosionVNT setBlockAllocator(IBlockAllocator blockAllocator) {
		this.blockAllocator = blockAllocator;
		return this;
	}
	public ExplosionVNT setEntityAllocator(IEntityAllocator entityAllocator) {
		this.entityAllocator = entityAllocator;
		return this;
	}
	public ExplosionVNT setBlockProcessor(IBlockProcessor blockProcessor) {
		this.blockProcessor = blockProcessor;
		return this;
	}
	public ExplosionVNT setEntityprocessor(IEntityProcessor entityProcessor) {
		this.entityProcessor = entityProcessor;
		return this;
	}
	public ExplosionVNT setSFX(IExplosionSFX... sfx) {
		this.sfx = sfx;
		return this;
	}
}
