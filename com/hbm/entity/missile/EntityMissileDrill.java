package com.hbm.entity.missile;

import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionLarge;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityMissileDrill extends EntityMissileBase {

	public EntityMissileDrill(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileDrill(World p_i1582_1_, int x, int z, double a, double b, double c) {
		super(p_i1582_1_, x, z, a, b, c);
		this.baseHeight = 100;
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
        
        this.worldObj.spawnEntityInWorld(new EntitySmokeFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));
        
        if(this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.air)
        {
    		if(!this.worldObj.isRemote)
    		{
    			for(int i = 0; i < 30; i++)
    			{
    				this.worldObj.createExplosion(this, this.posX, this.posY - i, this.posZ, 10F, true);
    			}
    			ExplosionLarge.spawnParticles(worldObj, this.posX, this.posY, this.posZ, 25);
    			ExplosionLarge.spawnShrapnels(worldObj, this.posX, this.posY, this.posZ, 12);
    			ExplosionLarge.spawnRubble(worldObj, this.posX, this.posY, this.posZ, 12);
    		}
		this.setDead();
        }
    }
}
