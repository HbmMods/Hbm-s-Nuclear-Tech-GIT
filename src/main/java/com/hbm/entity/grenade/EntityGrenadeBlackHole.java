package com.hbm.entity.grenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.AmmoHandGrenade;
import com.hbm.items.weapon.ItemGrenade;

public class EntityGrenadeBlackHole extends EntityGrenadeBouncyBase
{
    private static final String __OBFID = "CL_00001722";

    public EntityGrenadeBlackHole(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeBlackHole(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityGrenadeBlackHole(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    @Override
    public void explode() {
    	
        if (!this.worldObj.isRemote)
        {
            this.setDead();
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 1.5F, true);

        	EntityBlackHole bl = new EntityBlackHole(this.worldObj, 1.5F);
        	bl.posX = this.posX;
        	bl.posY = this.posY;
        	bl.posZ = this.posZ;
        	this.worldObj.spawnEntityInWorld(bl);
        }
    }

	@Override
	protected int getMaxTimer() {
		return AmmoHandGrenade.BLACK_HOLE.fuse;
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}
}
