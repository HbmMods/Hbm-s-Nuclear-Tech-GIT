package com.hbm.entity.projectile;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeGeneric;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBurningFOEQ extends EntityThrowable {

	public EntityBurningFOEQ(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
	}
	
	@Override
	public void onUpdate() {


		this.lastTickPosX = this.prevPosX = posX;
		this.lastTickPosY = this.prevPosY = posY;
		this.lastTickPosZ = this.prevPosZ = posZ;
		this.setPosition(posX + this.motionX, posY + this.motionY, posZ + this.motionZ);
		
		/*this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;*/
        
		if(motionY > -4)
			motionY -= 0.1;
		
        this.rotation();
        
        if(this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.air)
        {
    		if(!this.worldObj.isRemote) {
    			for(int i = 0; i < 25; i++)
    				ExplosionLarge.explode(worldObj, this.posX + 0.5F + rand.nextGaussian() * 5, this.posY + 0.5F + rand.nextGaussian() * 5, this.posZ + 0.5F + rand.nextGaussian() * 5, 10.0F, rand.nextBoolean(), false, false);
    			ExplosionNukeGeneric.waste(worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 35);
    		}
    		this.setDead();
        }
        
        //if(!this.worldObj.isRemote) {
        //	Vec3 vec = Vec3.createVectorHelper(motionX, motionY, motionZ);
        //	vec.normalize();
        //	worldObj.spawnEntityInWorld(new EntityDSmokeFX(worldObj, posX/* - vec.xCoord * 30*/ + rand.nextGaussian() * 0.5, posY/* - vec.yCoord * 30*/ + rand.nextGaussian() * 0.5, posZ/* - vec.zCoord * 30*/ + rand.nextGaussian() * 0.5, 0, 0, 0));
        //}
	}
	
	public void rotation() {
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
        return distance < 100000;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_)
    {
        return 15728880;
    }

    @Override
	public float getBrightness(float p_70013_1_)
    {
        return 1.0F;
    }
}
