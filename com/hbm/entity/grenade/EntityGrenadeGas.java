package com.hbm.entity.grenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.Random;

import com.hbm.entity.particle.EntityGasFX;
import com.hbm.entity.particle.EntityOilSpillFX;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;

public class EntityGrenadeGas extends EntityGrenadeBase
{
    private static final String __OBFID = "CL_00001722";
    Random rand = new Random();

    public EntityGrenadeGas(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeGas(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    @Override
    public void explode() {
    	
        if (!this.worldObj.isRemote)
        {
            this.setDead();
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2.0F, true);
            ExplosionChaos.poison(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 5);
            //for(int i = 0; 0 < 15; i++) {

        	ExplosionLarge.spawnParticles(worldObj, posX, posY, posZ, 15);
        	ExplosionLarge.spawnParticles(worldObj, posX, posY, posZ, 15);
        	ExplosionLarge.spawnParticles(worldObj, posX, posY, posZ, 15);
        
            //}
        }
    }

}
