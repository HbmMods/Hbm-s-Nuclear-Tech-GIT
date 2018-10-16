package com.hbm.entity.grenade;

import com.hbm.entity.effect.EntityRagingVortex;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGrenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadeIFSpark extends EntityGrenadeBouncyBase {

    public EntityGrenadeIFSpark(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeIFSpark(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityGrenadeIFSpark(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    @Override
    public void explode() {
    	
        if (!this.worldObj.isRemote)
        {
            this.setDead();
    		
    		EntityRagingVortex vortex = new EntityRagingVortex(worldObj, 1.5F);
    		vortex.posX = posX;
    		vortex.posY = posY;
    		vortex.posZ = posZ;
    		worldObj.spawnEntityInWorld(vortex);
        }
    }

	@Override
	protected int getMaxTimer() {
		return ItemGrenade.getFuseTicks(ModItems.grenade_if_spark);
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}
}
