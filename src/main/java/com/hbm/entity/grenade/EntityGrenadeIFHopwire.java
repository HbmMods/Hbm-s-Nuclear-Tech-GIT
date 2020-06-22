package com.hbm.entity.grenade;

import com.hbm.entity.effect.EntityVortex;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGrenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadeIFHopwire extends EntityGrenadeBouncyBase {

    public EntityGrenadeIFHopwire(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeIFHopwire(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityGrenadeIFHopwire(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    @Override
    public void explode() {
    	
        if (!this.worldObj.isRemote)
        {
            this.setDead();
    		
    		EntityVortex vortex = new EntityVortex(worldObj, 0.75F);
    		vortex.posX = posX;
    		vortex.posY = posY;
    		vortex.posZ = posZ;
    		worldObj.spawnEntityInWorld(vortex);
        }
    }

	@Override
	protected int getMaxTimer() {
		return ItemGrenade.getFuseTicks(ModItems.grenade_if_hopwire);
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}
}
