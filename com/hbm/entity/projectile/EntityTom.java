package com.hbm.entity.projectile;

import com.hbm.entity.logic.EntityTomBlast;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityTom extends EntityThrowable {

	public EntityTom(World p_i1582_1_) {
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
        
		motionY = -0.5;
        
        if(this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.air)
        {
    		if(!this.worldObj.isRemote) {
    			EntityTomBlast tom = new EntityTomBlast(worldObj);
    			tom.posX = posX;
    			tom.posY = posY;
    			tom.posZ = posZ;
    			tom.destructionRange = 500;
    			worldObj.spawnEntityInWorld(tom);
    		}
    		this.setDead();
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
