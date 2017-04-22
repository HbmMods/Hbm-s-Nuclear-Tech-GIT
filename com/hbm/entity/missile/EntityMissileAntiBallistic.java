package com.hbm.entity.missile;

import com.hbm.calc.EasyLocation;
import com.hbm.entity.particle.EntityDSmokeFX;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionChaos;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityMissileAntiBallistic extends EntityMissileBase {
	
	EasyLocation missile;
	Entity missile0;

	public EntityMissileAntiBallistic(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileAntiBallistic(World p_i1582_1_, int x, int z, double a, double b, double c) {
		super(p_i1582_1_, x, z, a, b, c);
	}
	
	@Override
    public void onUpdate()
    {
		this.baseHeight = 35;

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        
        if(missile0 == null)
        {
        	missile0 = ExplosionChaos.getHomingTarget(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 25, this);
        }
        if(missile0 != null)
        {
        	missile = new EasyLocation(missile0.posX, missile0.posY, missile0.posZ);
        	this.phase = -1;
        }
        
        this.rotation();
        
        switch(phase)
        {
        case -1:
        	if(missile0 != null)
        	{
        		freePizzaGoddammit(missile);
        		this.missileSpeed = 3;
        		if(missile0.posX + 2 > this.posX && missile0.posX - 2 < this.posX &&
        				missile0.posY + 2 > this.posY && missile0.posY - 2 < this.posY &&
        				missile0.posZ + 2 > this.posZ && missile0.posZ - 2 < this.posZ)
            		{
            			if(!this.worldObj.isRemote)
            			{
            				this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 10.0F, true);
            			}
            			this.setDead();
            			missile0.setDead();
            			//ExplosionChaos.delMissiles(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 5, (Entity)this);
            		}
        	}
            break;
        
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
        
        this.worldObj.spawnEntityInWorld(new EntityDSmokeFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));
        
        if(this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.air)
        {
    		if(!this.worldObj.isRemote)
    		{
    			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 10.0F, true);
    		}
		this.setDead();
        }
    }

}
