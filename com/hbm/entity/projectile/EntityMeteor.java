package com.hbm.entity.projectile;

import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.main.MainRegistry;
import com.hbm.world.Meteorite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityMeteor extends EntityThrowable {

	public EntityMeteor(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
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
		
		this.motionY -= 0.03;
		if(motionY < -2.5)
			motionY = -2.5;
        
        if(this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.air)
        {
            if(!this.worldObj.isRemote)
    		{
    			worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 5 + rand.nextFloat(), true);
    			if(MainRegistry.enableMeteorTails)
    				ExplosionLarge.spawnParticles(worldObj, posX, posY, posZ, ExplosionLarge.cloudFunction(20));
    			(new Meteorite()).generate(worldObj, rand, (int)Math.round(this.posX - 0.5D), (int)Math.round(this.posY - 0.5D), (int)Math.round(this.posZ - 0.5D));
    		}
            this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "hbm:entity.oldExplosion", 10000.0F, 0.5F + this.rand.nextFloat() * 0.1F);
    		this.setDead();
        }
        
        if(MainRegistry.enableMeteorTails) {
        	this.worldObj.spawnEntityInWorld(new EntitySmokeFX(this.worldObj, this.posX, this.posY + 1.5D, this.posZ, 0.0, 0.0, 0.0));
        	for(int i = 0; i < 10; i++)
        		this.worldObj.spawnEntityInWorld(new EntityGasFlameFX(this.worldObj, this.posX + rand.nextDouble() * 3 - 1.5, this.posY + 1.5D + rand.nextDouble() * 3 - 1.5, this.posZ + rand.nextDouble() * 3 - 1.5, 0.0, 0.1, 0.0));
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
