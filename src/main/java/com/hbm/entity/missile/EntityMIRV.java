package com.hbm.entity.missile;

import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityNukeExplosionAdvanced;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityMIRV extends EntityThrowable {

	public EntityMIRV(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
	}
	
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		
		this.motionY -= 0.03;
        
        this.rotation();
        
        if(this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.air)
        {
    		if(!this.worldObj.isRemote)
    		{
    			EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(this.worldObj);
    	    	entity.posX = this.posX;
    	    	entity.posY = this.posY;
    	    	entity.posZ = this.posZ;
    	    	entity.destructionRange = MainRegistry.mirvRadius;
    	    	entity.speed = 25;
    	    	entity.coefficient = 10.0F;
    	    	
    	    	this.worldObj.spawnEntityInWorld(entity);

    			EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(this.worldObj, 1000);
    	    	entity2.posX = this.posX;
    	    	entity2.posY = this.posY - 9;
    	    	entity2.posZ = this.posZ;
    	    	this.worldObj.spawnEntityInWorld(entity2);
    		}
    		this.setDead();
        }
        
        this.worldObj.spawnEntityInWorld(new EntitySmokeFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));
	}
	
	protected void rotation() {
        float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }
	}

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
		
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 25000;
    }

}
