package com.hbm.entity.projectile;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
		this.dataWatcher.updateObject(20, player.getUniqueID().toString());
	}

	@Override
	protected void entityInit() {
        this.dataWatcher.addObject(20, "");
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

    @Override
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_)
    {
        return 15728880;
    }

    @Override
	public float getBrightness(float p_70013_1_)
    {
        return 1.0F;
    }

}
