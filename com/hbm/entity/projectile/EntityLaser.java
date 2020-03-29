package com.hbm.entity.projectile;

import java.util.List;

import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
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
		this.dataWatcher.updateObject(20, player.getDisplayName());
		
		Vec3 vec = player.getLookVec();
		vec.rotateAroundY(-90F);
		float l = 0.25F;
		vec.xCoord *= l;
		vec.yCoord *= l;
		vec.zCoord *= l;
		
		this.setPosition(player.posX + vec.xCoord, player.posY + player.getEyeHeight(), player.posZ + vec.zCoord);
		
	}

	@Override
	protected void entityInit() {
        this.dataWatcher.addObject(20, "");
	}
	
	@Override
	public void onUpdate() {
		
		if(this.ticksExisted > 1)
			this.setDead();
		
		int range = 100;
		
		EntityPlayer player = worldObj.getPlayerEntityByName(this.dataWatcher.getWatchableObjectString(20));
		
		if(player != null) {
			
			//this.setPosition(player.posX, player.posY + player.getEyeHeight(), player.posZ);
			
			MovingObjectPosition pos = Library.rayTrace(player, range, 1);
			
			//worldObj.createExplosion(this, pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord, 1, false);
			
			worldObj.spawnParticle("cloud", pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord, 0, 0, 0);
			worldObj.playSound(pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord, "random.fizz", 1, 1, true);
			
			List<Entity> list = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(pos.hitVec.xCoord - 1, pos.hitVec.yCoord - 1, pos.hitVec.zCoord - 1, pos.hitVec.xCoord + 1, pos.hitVec.yCoord + 1, pos.hitVec.zCoord + 1));
			
			for(Entity e : list)
				e.attackEntityFrom(ModDamageSource.radiation, 5);
		}
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
