package com.hbm.entity.grenade;

import com.hbm.explosion.ExplosionNukeGeneric;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityGrenadeLemon extends EntityThrowable
{
    private static final String __OBFID = "CL_00001722";

    public EntityGrenadeLemon(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeLemon(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityGrenadeLemon(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    @Override
	protected void onImpact(MovingObjectPosition p_70184_1_)
    {
        if (p_70184_1_.entityHit != null)
        {
            byte b0 = 0;

            if (p_70184_1_.entityHit instanceof EntityBlaze)
            {
                b0 = 3;
            }

            p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), b0);
        }

        if (!this.worldObj.isRemote)
        {
            this.setDead();
			this.worldObj.newExplosion((Entity)null, (float)this.posX, (float)this.posY, (float)this.posZ, 5.0F, true, true);
        }
    }
}
