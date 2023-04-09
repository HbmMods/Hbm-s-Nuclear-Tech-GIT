package com.hbm.entity.grenade;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityGrenadeFlare extends EntityThrowable
{
    public Entity shooter;

    public EntityGrenadeFlare(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeFlare(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityGrenadeFlare(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }
    
    @Override
	public void onUpdate() {
    	super.onUpdate();
    	if(this.ticksExisted > 250)
    	{
    		this.setDead();
    	}
    }

    @Override
	protected void onImpact(MovingObjectPosition p_70184_1_)
    {
    	this.motionX = 0;
    	this.motionY = 0;
    	this.motionZ = 0;
    }
}
