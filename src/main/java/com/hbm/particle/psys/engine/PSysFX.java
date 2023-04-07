package com.hbm.particle.psys.engine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * HBM: reinventing the fucking wheel for the 15th time since 2014
 * 
 * @author hbm
 */
@SideOnly(Side.CLIENT)
public abstract class PSysFX {
	
	public World world;
	public double posX;
	public double posY;
	public double posZ;
	public double prevPosX;
	public double prevPosY;
	public double prevPosZ;
	public static double interpPosX;
	public static double interpPosY;
	public static double interpPosZ;
	public AxisAlignedBB boundingBox;
	public int particleAge;
	public int particleMaxAge;
	public boolean isExpired = false;
	public boolean shouldExpireWhenUnloaded = true;
	public boolean isUnloaded = false;
	
	public PSysFX(World world, double x, double y, double z) {
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
	}
	
	public void updateParticle() {
		this.prevPosX = posX;
		this.prevPosY = posY;
		this.prevPosZ = posZ;
		this.isUnloaded = !world.getChunkProvider().chunkExists((int) Math.floor(posX) >> 4, (int) Math.floor(posZ) >> 4);
		
		this.particleAge++;
		
		if(this.particleAge >= this.particleMaxAge) {
			this.expire();
		}
		
		if(this.shouldExpireWhenUnloaded && this.isUnloaded) {
			this.expire();
		}
	}
	
	public abstract void renderParticle();
	
	public AxisAlignedBB getBoundingBox() {
		return this.boundingBox;
	}

	public void setBoundingBox(AxisAlignedBB bb) {
		this.boundingBox = bb;
	}

	protected void setPosToAABB() {
		AxisAlignedBB aabb = this.getBoundingBox();
		this.posX = (aabb.minX + aabb.maxX) / 2.0D;
		this.posY = aabb.minY;
		this.posZ = (aabb.minZ + aabb.maxZ) / 2.0D;
	}
	
	public void expire() {
		this.isExpired = true;
	}
	
	public void setExpireOnUnload(boolean expire) {
		this.shouldExpireWhenUnloaded = expire;
	}
}
