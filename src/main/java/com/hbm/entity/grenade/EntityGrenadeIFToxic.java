package com.hbm.entity.grenade;

import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGrenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadeIFToxic extends EntityGrenadeBouncyBase {

    public EntityGrenadeIFToxic(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeIFToxic(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityGrenadeIFToxic(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    @Override
    public void explode() {
    	
        if (!this.worldObj.isRemote)
        {
            this.setDead();
    		
    		ExplosionLarge.jolt(worldObj, posX, posY, posZ, 3, 200, 0.25);
    		ExplosionLarge.explode(worldObj, posX, posY, posZ, 2, true, true, true);
    		ExplosionChaos.poison(worldObj, (int)posX, (int)posY, (int)posZ, 12);
    		ExplosionNukeGeneric.waste(worldObj, (int)posX, (int)posY, (int)posZ, 12);
        }
    }

	@Override
	protected int getMaxTimer() {
		return ItemGrenade.getFuseTicks(ModItems.grenade_if_toxic);
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}
}
