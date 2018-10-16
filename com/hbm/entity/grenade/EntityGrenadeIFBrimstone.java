package com.hbm.entity.grenade;

import com.hbm.entity.projectile.EntityBullet;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGrenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityGrenadeIFBrimstone extends EntityGrenadeBouncyBase {

    public EntityGrenadeIFBrimstone(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeIFBrimstone(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityGrenadeIFBrimstone(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }
    
    @Override
    public void onUpdate() {
    	super.onUpdate();
    	
    	if(this.timer > (this.getMaxTimer() * 0.65)) {
    		
    		if(!worldObj.isRemote) {
	    		EntityBullet fragment;
	
	    		fragment = new EntityBullet(worldObj, (EntityPlayer) this.thrower, 3.0F, 35, 45, false, "tauDay");
	    		fragment.setDamage(rand.nextInt(301) + 100);
	
	    		fragment.motionX = rand.nextGaussian();
	    		fragment.motionY = rand.nextGaussian();
	    		fragment.motionZ = rand.nextGaussian();
	    		fragment.shootingEntity = this.thrower;

	    		fragment.posX = posX;
	    		fragment.posY = posY;
	    		fragment.posZ = posZ;
	
	    		fragment.setIsCritical(true);
	
	    		worldObj.spawnEntityInWorld(fragment);
    		}
    	}
    }

    @Override
    public void explode() {
    	
        if (!this.worldObj.isRemote)
        {
            this.setDead();
    		
    		worldObj.newExplosion(this, posX, posY, posZ, 5, false, false);
    		
    		for(int i = 0; i < 100; i++) {
	    		EntityBullet fragment;
	
	    		fragment = new EntityBullet(worldObj, (EntityPlayer) this.thrower, 3.0F, 35, 45, false, "tauDay");
	    		fragment.setDamage(rand.nextInt(301) + 100);
	
	    		fragment.motionX = rand.nextGaussian() * 0.25;
	    		fragment.motionY = rand.nextGaussian() * 0.25;
	    		fragment.motionZ = rand.nextGaussian() * 0.25;
	    		fragment.shootingEntity = this.thrower;

	    		fragment.posX = posX;
	    		fragment.posY = posY;
	    		fragment.posZ = posZ;
	
	    		fragment.setIsCritical(true);
	
	    		worldObj.spawnEntityInWorld(fragment);
    		}
        }
    }

	@Override
	protected int getMaxTimer() {
		return ItemGrenade.getFuseTicks(ModItems.grenade_if_brimstone);
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}
}
