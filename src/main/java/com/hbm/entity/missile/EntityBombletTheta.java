package com.hbm.entity.missile;

import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityNukeExplosionAdvanced;
import com.hbm.entity.particle.EntitySSmokeFX;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBombletTheta extends EntityThrowable {

	double decelY = 0.1D;
	double accelXZ = 0.1D;

	public EntityBombletTheta(World p_i1582_1_) {
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

        Vec3 vector = Vec3.createVectorHelper(motionX, 0, motionZ);
        vector = vector.normalize();
        vector.xCoord *= accelXZ;
        vector.zCoord *= accelXZ;
		this.motionY -= decelY;
		this.motionX -= vector.xCoord;
		this.motionZ -= vector.zCoord;
		
		if(motionY < -0.75D && !worldObj.isRemote && rand.nextInt(10) == 0) {
			EntityBombletSelena selena = new EntityBombletSelena(worldObj);
			selena.posX = this.posX;
			selena.posY = this.posY;
			selena.posZ = this.posZ;
			selena.motionX = rand.nextGaussian();
			selena.motionY = rand.nextGaussian();
			selena.motionZ = rand.nextGaussian();
			selena.decelY = this.decelY;
			selena.accelXZ = this.accelXZ;
			worldObj.spawnEntityInWorld(selena);
		}
        
        this.rotation();
        
        if(this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.air)
        {
    		if(!this.worldObj.isRemote)
    		{
    			ExplosionLarge.explodeFire(worldObj, this.posX + 0.5F, this.posY + 0.5F, this.posZ + 0.5F, 50.0F, true, true, true);
    		}
    		this.setDead();
        }

		if(!this.worldObj.isRemote)
			this.worldObj.spawnEntityInWorld(new EntitySSmokeFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));
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
