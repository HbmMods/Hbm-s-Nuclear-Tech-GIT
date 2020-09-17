package com.hbm.entity.projectile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityBeamBase extends Entity {

	public EntityBeamBase(World world) {
		super(world);
		this.ignoreFrustumCheck = true;
	}

	public EntityBeamBase(World world, EntityPlayer player) {
		super(world);
		
		this.ignoreFrustumCheck = true;
		this.dataWatcher.updateObject(20, player.getDisplayName());
		
		Vec3 vec = player.getLookVec();
		vec.rotateAroundY(-90F);
		float l = 0.075F;
		vec.xCoord *= l;
		vec.yCoord *= l;
		vec.zCoord *= l;
		
		Vec3 vec0 = player.getLookVec();
		float d = 0.1F;
		vec0.xCoord *= d;
		vec0.yCoord *= d;
		vec0.zCoord *= d;
		
		this.setPosition(player.posX + vec.xCoord + vec0.xCoord, player.posY + player.getEyeHeight() + vec0.yCoord, player.posZ + vec.zCoord + vec0.zCoord);
	}

	@Override
	protected void entityInit() {
        this.dataWatcher.addObject(20, "");
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
