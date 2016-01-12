package com.hbm.blocks;

import java.util.HashSet;
import java.util.List;

import com.hbm.entity.EntityNuclearCreeper;
import com.hbm.lib.Library;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class TileEntityDecoBlockAltF extends TileEntity {

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
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox((double)i, (double)k, (double)l, (double)j, (double)i2, (double)j2));
        Vec3 vec3 = Vec3.createVectorHelper(this.xCoord, this.yCoord, this.zCoord);

        for (int i1 = 0; i1 < list.size(); ++i1)
        {
            Entity entity = (Entity)list.get(i1);
            double d4 = entity.getDistance(this.xCoord, this.yCoord, this.zCoord) / (double)4;

            if (d4 <= 1.0D)
            {
                d5 = entity.posX - this.xCoord;
                d6 = entity.posY + (double)entity.getEyeHeight() - this.yCoord;
                d7 = entity.posZ - this.zCoord;
                double d9 = (double)MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);
                if (d9 < wat)
                {
                	if(entity instanceof EntityPlayer) {
                		((EntityPlayer)entity).addPotionEffect(new PotionEffect(Potion.heal.id, 5, 99));
                		((EntityPlayer)entity).addPotionEffect(new PotionEffect(Potion.field_76443_y.id, 5, 99));
                    }
                }
            }
        }

        strength = (int)f;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return this.INFINITE_EXTENT_AABB;
	}
}
