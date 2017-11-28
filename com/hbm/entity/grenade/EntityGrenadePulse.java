package com.hbm.entity.grenade;

import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadePulse extends EntityGrenadeBase {
	
	private static final String __OBFID = "CL_00001722";

	public EntityGrenadePulse(World p_i1773_1_) {
		super(p_i1773_1_);
	}

    public EntityGrenadePulse(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
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
}
