package com.hbm.entity.grenade;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import com.hbm.explosion.ExplosionChaos;

public class EntityGrenadeFrag extends EntityGrenadeBase
{
    private static final String __OBFID = "CL_00001722";
    public Entity shooter;

    public EntityGrenadeFrag(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeFrag(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    @Override
    public void explode() {
    	
        if (!this.worldObj.isRemote)
        {
        	if(this.isBurning())
        	{
        		this.setDead();
        		ExplosionChaos.frag(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 100, true, this.shooter);
                ExplosionChaos.burn(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 5);
                ExplosionChaos.flameDeath(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 15);
        		this.worldObj.playSoundEffect((int)this.posX, (int)this.posY, (int)this.posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
        	} else {
        		this.setDead();
        		ExplosionChaos.frag(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 100, false, this.shooter);
        		this.worldObj.playSoundEffect((int)this.posX, (int)this.posY, (int)this.posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
        	}
        }
    }
}
