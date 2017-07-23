package com.hbm.tileentity;

import java.util.HashSet;
import java.util.List;

import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.lib.Library;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class TileEntityYellowBarrel extends TileEntity {

	@Override
	public void updateEntity() {
		int strength = 4;
		float f = strength;
        HashSet hashset = new HashSet();
        int i;
        int j;
        int k;
        double d5;
        double d6;
        double d7;
        double wat = 4*2;
        boolean isOccupied = false;
        

        strength *= 2.0F;
        i = MathHelper.floor_double(this.xCoord - wat - 1.0D);
        j = MathHelper.floor_double(this.xCoord + wat + 1.0D);
        k = MathHelper.floor_double(this.yCoord - wat - 1.0D);
        int i2 = MathHelper.floor_double(this.yCoord + wat + 1.0D);
        int l = MathHelper.floor_double(this.zCoord - wat - 1.0D);
        int j2 = MathHelper.floor_double(this.zCoord + wat + 1.0D);
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(i, k, l, j, i2, j2));
        Vec3 vec3 = Vec3.createVectorHelper(this.xCoord, this.yCoord, this.zCoord);

        for (int i1 = 0; i1 < list.size(); ++i1)
        {
            Entity entity = (Entity)list.get(i1);
            double d4 = entity.getDistance(this.xCoord, this.yCoord, this.zCoord) / 4;

            if (d4 <= 1.0D)
            {
                d5 = entity.posX - this.xCoord;
                d6 = entity.posY + entity.getEyeHeight() - this.yCoord;
                d7 = entity.posZ - this.zCoord;
                double d9 = MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);
                if (d9 < wat)
                {
                	if(entity instanceof EntityPlayer && Library.checkForHazmat((EntityPlayer)entity))
                	{
                		/*Library.damageSuit(((EntityPlayer)entity), 0);
                		Library.damageSuit(((EntityPlayer)entity), 1);
                		Library.damageSuit(((EntityPlayer)entity), 2);
                		Library.damageSuit(((EntityPlayer)entity), 3);*/
                		
                	} else if(entity instanceof EntityCreeper) {
                		EntityNuclearCreeper creep = new EntityNuclearCreeper(this.worldObj);
                		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
                		//creep.setRotationYawHead(((EntityCreeper)entity).rotationYawHead);
                		if(!entity.isDead)
                			if(!worldObj.isRemote)
                				worldObj.spawnEntityInWorld(creep);
                		entity.setDead();
                	} else if(entity instanceof EntityVillager) {
                		EntityZombie creep = new EntityZombie(this.worldObj);
                		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
                		entity.setDead();
                		if(!this.worldObj.isRemote)
                		this.worldObj.spawnEntityInWorld(creep);
                	} else if(entity instanceof EntityLivingBase && !(entity instanceof EntityNuclearCreeper) && !(entity instanceof EntityMooshroom) && !(entity instanceof EntityZombie))
                    {
                    	((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.getId(), 2 * 60 * 20, 2));
                    	((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.wither.getId(), 20, 4));
                    	((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 1 * 60 * 20, 1));
                    }
                }
            }
        }

        strength = (int)f;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
