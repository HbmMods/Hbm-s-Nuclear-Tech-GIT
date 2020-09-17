package com.hbm.entity.grenade;

import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGrenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadePulse extends EntityGrenadeBouncyBase {

	public EntityGrenadePulse(World p_i1773_1_) {
		super(p_i1773_1_);
	}

    public EntityGrenadePulse(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityGrenadePulse(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

	@Override
    public void explode() {
    	

		if (!this.worldObj.isRemote) {
			this.setDead();
			ExplosionChaos.pulse(this.worldObj, (int) this.posX, (int) this.posY, (int) this.posZ, 7);
    		this.worldObj.playSoundEffect((int)this.posX, (int)this.posY, (int)this.posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
    		ExplosionLarge.spawnShock(worldObj, posX, posY, posZ, 24, 2);
		}
    }

	@Override
	protected int getMaxTimer() {
		return ItemGrenade.getFuseTicks(ModItems.grenade_pulse);
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}
}
