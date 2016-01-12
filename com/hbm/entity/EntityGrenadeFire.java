package com.hbm.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.hbm.explosion.ExplosionChaos;

public class EntityGrenadeFire extends EntityThrowable
{
    private static final String __OBFID = "CL_00001722";
    public Entity shooter;

    public EntityGrenadeFire(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeFire(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityGrenadeFire(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    protected void onImpact(MovingObjectPosition p_70184_1_)
    {
        if (p_70184_1_.entityHit != null)
        {
            byte b0 = 0;

            if (p_70184_1_.entityHit instanceof EntityBlaze)
            {
                b0 = 3;
            }

            p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)b0);
        }

        if (!this.worldObj.isRemote)
        {
            this.setDead();
            ExplosionChaos.frag(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 100, true, this.shooter);
            ExplosionChaos.burn(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 5);
            ExplosionChaos.flameDeath(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 15);
            this.worldObj.playSoundEffect((int)this.posX, (int)this.posY, (int)this.posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
        }
    }
}
