package com.hbm.entity.missile;

import com.hbm.calc.EasyLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityMissileBase extends EntityThrowable {

	EasyLocation origin;
	EasyLocation loc0;
	EasyLocation loc1;
	EasyLocation loc2;
	EasyLocation loc3;
	EasyLocation loc4;
	EasyLocation loc5;
	EasyLocation loc6;
	EasyLocation loc7;
	EasyLocation target;
	
	public int phase = 0;
	
	public int targetPoint = 0;
	public int lengthX;
	public int lengthZ;
	public double lengthFlight;
	public int baseHeight = 50;
	public double missileSpeed = 1.5;

	public EntityMissileBase(World p_i1776_1_) {
		super(p_i1776_1_);
		this.ignoreFrustumCheck = true;
	}

	public EntityMissileBase(World p_i1582_1_, int x, int z, double a, double b, double c) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		this.posX = a;
		this.posY = b;
		this.posZ = c;
		
		this.motionY = 0.1;
		
		lengthX = (int) (x - this.posX);
		lengthZ = (int) (z - this.posZ);
		lengthFlight = Math.sqrt(Math.pow(lengthX, 2) + Math.pow(lengthZ, 2));
		
		
		origin = new EasyLocation(this.posX, this.posY, this.posZ);
		
		loc0 = new EasyLocation(this.posX, this.posY + baseHeight, this.posZ);
		loc1 = new EasyLocation(this.posX + lengthX/lengthFlight * 10, this.posY + baseHeight + 20, this.posZ + lengthZ/lengthFlight * 10);
		loc2 = new EasyLocation(this.posX + lengthX/lengthFlight * 30, this.posY + baseHeight + 40, this.posZ + lengthZ/lengthFlight * 30);
		loc3 = new EasyLocation(this.posX + lengthX/lengthFlight * 50, this.posY + baseHeight + 50, this.posZ + lengthZ/lengthFlight * 50);
			
		loc4 = new EasyLocation(x - (lengthX/lengthFlight * 50), this.posY + baseHeight + 50, z - (lengthZ/lengthFlight * 50));
		loc5 = new EasyLocation(x - (lengthX/lengthFlight * 30), this.posY + baseHeight + 40, z - (lengthZ/lengthFlight * 30));
		loc6 = new EasyLocation(x - (lengthX/lengthFlight * 10), this.posY + baseHeight + 20, z - (lengthZ/lengthFlight * 10));
		loc7 = new EasyLocation(x, this.posY + baseHeight, z);

		
		target = new EasyLocation(x, 0, z);
	}
	
	protected void freePizzaGoddammit(EasyLocation loc) {
		double x = loc.posX - this.posX;
		double y = loc.posY - this.posY;
		double z = loc.posZ - this.posZ;
		lengthFlight = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
		
		this.motionX = x / this.lengthFlight * missileSpeed;
		this.motionY = y / this.lengthFlight * missileSpeed;
		this.motionZ = z / this.lengthFlight * missileSpeed;
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
    public void onUpdate()
    {

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        
        this.rotation();
        
        switch(phase)
        {
        case 0:
        	if(loc0 != null)
        	{
        		freePizzaGoddammit(loc0);
        		if(loc0.posX + 2 > this.posX && loc0.posX - 2 < this.posX &&
        			loc0.posY + 2 > this.posY && loc0.posY - 2 < this.posY &&
        			loc0.posZ + 2 > this.posZ && loc0.posZ - 2 < this.posZ)
        		{
        			this.phase = 1;
        		}
        	}
        	break;
        case 1:
        	if(loc1 != null)
        	{
        		freePizzaGoddammit(loc1);
        		if(loc1.posX + 2 > this.posX && loc1.posX - 2 < this.posX &&
        				loc1.posY + 2 > this.posY && loc1.posY - 2 < this.posY &&
        				loc1.posZ + 2 > this.posZ && loc1.posZ - 2 < this.posZ)
        		{
        			this.phase = 2;
        		}
        	}
        	break;
        case 2:
        	if(loc2 != null)
        	{
        		freePizzaGoddammit(loc2);
        		if(loc2.posX + 2 > this.posX && loc2.posX - 2 < this.posX &&
        				loc2.posY + 2 > this.posY && loc2.posY - 2 < this.posY &&
        				loc2.posZ + 2 > this.posZ && loc2.posZ - 2 < this.posZ)
        		{
        			this.phase = 3;
        		}
        	}
        	break;
        case 3:
        	if(loc3 != null)
        	{
        		freePizzaGoddammit(loc3);
        		if(loc3.posX + 2 > this.posX && loc3.posX - 2 < this.posX &&
        				loc3.posY + 2 > this.posY && loc3.posY - 2 < this.posY &&
        				loc3.posZ + 2 > this.posZ && loc3.posZ - 2 < this.posZ)
        		{
        			this.phase = 4;
        		}
        	}
        	break;
        case 4:
        	if(loc4 != null)
        	{
        		freePizzaGoddammit(loc4);
        		if(loc4.posX + 2 > this.posX && loc4.posX - 2 < this.posX &&
        				loc4.posY + 2 > this.posY && loc4.posY - 2 < this.posY &&
        				loc4.posZ + 2 > this.posZ && loc4.posZ - 2 < this.posZ)
        		{
        			this.phase = 5;
        		}
        	}
        	break;
        case 5:
        	if(loc5 != null)
        	{
        		freePizzaGoddammit(loc5);
        		if(loc5.posX + 2 > this.posX && loc5.posX - 2 < this.posX &&
        				loc5.posY + 2 > this.posY && loc5.posY - 2 < this.posY &&
        				loc5.posZ + 2 > this.posZ && loc5.posZ - 2 < this.posZ)
        		{
        			this.phase = 6;
        		}
        	}
        	break;
        case 6:
        	if(loc6 != null)
        	{
        		freePizzaGoddammit(loc6);
        		if(loc6.posX + 2 > this.posX && loc6.posX - 2 < this.posX &&
        				loc6.posY + 2 > this.posY && loc6.posY - 2 < this.posY &&
        				loc6.posZ + 2 > this.posZ && loc6.posZ - 2 < this.posZ)
        		{
        			this.phase = 7;
        		}
        	}
        	break;
        case 7:
        	if(loc7 != null)
        	{
        		freePizzaGoddammit(loc7);
        		if(loc7.posX + 2 > this.posX && loc7.posX - 2 < this.posX &&
        				loc7.posY + 2 > this.posY && loc7.posY - 2 < this.posY &&
        				loc7.posZ + 2 > this.posZ && loc7.posZ - 2 < this.posZ)
        		{
        			this.phase = 8;
        		}
        	}
        	break;
        case 8:
        	if(target != null)
        	{
        		freePizzaGoddammit(target);
        		if(target.posX + 2 > this.posX && target.posX - 2 < this.posX &&
        				target.posY + 2 > this.posY && target.posY - 2 < this.posY &&
        				target.posZ + 2 > this.posZ && target.posZ - 2 < this.posZ)
        		{
        			this.phase = -1;
        		}
        	}
        	break;
        }
        
        if(this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.air && this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.water && this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.flowing_water)
        {
    		if(!this.worldObj.isRemote)
    		{
    			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 5.0F, true);
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
        return distance < 25000;
    }

}
