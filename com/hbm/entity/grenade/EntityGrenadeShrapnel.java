package com.hbm.entity.grenade;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;

public class EntityGrenadeShrapnel extends EntityGrenadeBase
{
    private static final String __OBFID = "CL_00001722";
    public Entity shooter;

    public EntityGrenadeShrapnel(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeShrapnel(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    @Override
    public void explode() {
    	
        if (!this.worldObj.isRemote)
        {
        	this.setDead();
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2.0F, true);
        	for(int i = 0; i < 5; i++) {
        		ExplosionLarge.spawnShrapnels(worldObj, this.posX, this.posY, this.posZ, 5);
        	}
        }
    }
}
