package com.hbm.entity.projectile;

import com.hbm.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityLaser extends Entity {

	public EntityLaser(World world) {
		super(world);
		this.ignoreFrustumCheck = true;
	}

	public EntityLaser(World world, EntityPlayer player) {
		super(world);
		this.ignoreFrustumCheck = true;
		Vec3 vec1 = player.getLook(1.0F);
		vec1.rotateAroundY(-90);
		Vec3 vec2 = player.getLook(1.0F);
		double x = player.posX + (vec1.xCoord * 0.3) + (vec2.xCoord * 0.4);
		double y = player.posY + (vec1.yCoord * 0.3) + (vec2.yCoord * 0.4) + player.eyeHeight;
		double z = player.posZ + (vec1.zCoord * 0.3) + (vec2.zCoord * 0.4);
		this.setPlayerCoord((float)x, (float)y, (float)z);
	}

	@Override
	protected void entityInit() {
        this.dataWatcher.addObject(20, Float.valueOf((float)0));
        this.dataWatcher.addObject(21, Float.valueOf((float)0));
        this.dataWatcher.addObject(22, Float.valueOf((float)0));
	}
	
	@Override
	public void onUpdate() {
		if(!worldObj.isRemote && this.ticksExisted > 1)
			this.setDead();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
	}
	
	private void setPlayerCoord(float x, float y, float z) {
		this.dataWatcher.updateObject(20, x);
		this.dataWatcher.updateObject(21, y);
		this.dataWatcher.updateObject(22, z);
	}
	
	public float[] getPlayerCoord() {
		return new float[]{ this.dataWatcher.getWatchableObjectFloat(20),
				this.dataWatcher.getWatchableObjectFloat(21),
				this.dataWatcher.getWatchableObjectFloat(22) };
	}

}
