package com.hbm.entity.grenade;

import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.logic.EntityNukeExplosionAdvanced;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadeASchrab extends EntityGrenadeBase
{
    private static final String __OBFID = "CL_00001722";

    public EntityGrenadeASchrab(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeASchrab(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    @Override
    public void explode() {

        if (!this.worldObj.isRemote)
        {
            this.setDead();
			if (!this.worldObj.isRemote) {
				this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ,
						"random.explode", 100.0f, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);

				EntityNukeExplosionAdvanced entity = new EntityNukeExplosionAdvanced(this.worldObj);
				entity.posX = this.posX;
				entity.posY = this.posY;
				entity.posZ = this.posZ;
				entity.destructionRange = MainRegistry.aSchrabRadius;
				entity.speed = 25;
				entity.coefficient = 1.0F;
				entity.waste = false;

				this.worldObj.spawnEntityInWorld(entity);
	    		
	    		EntityCloudFleija cloud = new EntityCloudFleija(this.worldObj, MainRegistry.aSchrabRadius);
	    		cloud.posX = this.posX;
	    		cloud.posY = this.posY;
	    		cloud.posZ = this.posZ;
	    		this.worldObj.spawnEntityInWorld(cloud);
			}
        }
    }
}
