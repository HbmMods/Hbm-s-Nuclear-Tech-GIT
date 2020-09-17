package com.hbm.tileentity.deco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;

public class TileEntityYellowBarrel extends TileEntity {

	/*@Override
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
                	if(entity instanceof EntityLivingBase)
                		Library.applyRadiation((EntityLivingBase)entity, 80, 24, 60, 19);
                }
            }
        }

        strength = (int)f;
    }*/
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
