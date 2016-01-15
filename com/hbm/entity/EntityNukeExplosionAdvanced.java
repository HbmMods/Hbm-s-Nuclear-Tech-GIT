package com.hbm.entity;

import java.util.HashSet;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.hbm.explosion.ExplosionFleija;
import com.hbm.explosion.ExplosionNukeAdvanced;
import com.hbm.explosion.ExplosionNukeGeneric;

public class EntityNukeExplosionAdvanced extends Entity {
	
	public int age = 0;
	public int destructionRange = 0;
	public ExplosionNukeAdvanced exp;
	public ExplosionNukeAdvanced wst;
	public ExplosionNukeAdvanced vap;
	public ExplosionFleija expl;
	public int speed = 1;
	public float coefficient = 1;
	public boolean did = false;
	public boolean waste = true;

	public EntityNukeExplosionAdvanced(World p_i1582_1_) {
		super(p_i1582_1_);
	}

    @Override
	public void onUpdate() {
        super.onUpdate();
        	
        if(!this.did)
        {
        	if(this.waste)
        	{
            	exp = new ExplosionNukeAdvanced((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, this.destructionRange, this.coefficient, 0);
        		wst = new ExplosionNukeAdvanced((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, (int)(this.destructionRange * 1.4), this.coefficient, 2);
        		vap = new ExplosionNukeAdvanced((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, this.destructionRange * 2, this.coefficient, 1);
        	} else {
            	expl = new ExplosionFleija((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, this.destructionRange, this.coefficient);
        	}
        	
        	this.did = true;
        }
        
        boolean flag = false;
        boolean flag2 = false;
        boolean flag3 = false;
        
        for(int i = 0; i < this.speed; i++)
        {
        	if(waste) {
        		flag = exp.update();
        		flag2 = wst.update();
        		flag3 = vap.update();
        		
        		if(flag3) {
        			this.setDead();
        		}
        	} else {
        		if(expl.update()) {
        			this.setDead();
        		}
        	}
        }
        	
        if(!flag)
        {
        	this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
        	ExplosionNukeGeneric.dealDamage(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, this.destructionRange * 2);
        } else {
        }
        
        age++;
    }

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		
	}
	
	private void removeEntities(World world, int x, int y, int z) {
		float f = 10;
        HashSet hashset = new HashSet();
        int i;
        int j;
        int k;
        double d5;
        double d6;
        double d7;
        boolean isOccupied = false;
        
        i = MathHelper.floor_double(x - f - 1.0D);
        j = MathHelper.floor_double(x + f + 1.0D);
        k = MathHelper.floor_double(y - f - 1.0D);
        int i2 = MathHelper.floor_double(y + f + 1.0D);
        int l = MathHelper.floor_double(z - f - 1.0D);
        int j2 = MathHelper.floor_double(z + f + 1.0D);
        List list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(i, k, l, j, i2, j2));
        Vec3 vec3 = Vec3.createVectorHelper(x, y, z);

        for (int i1 = 0; i1 < list.size(); ++i1)
        {
            Entity entity = (Entity)list.get(i1);
            double d4 = entity.getDistance(x, y, z) / f;

            if (d4 <= 1.0D)
            {
                d5 = entity.posX - x;
                d6 = entity.posY + entity.getEyeHeight() - y;
                d7 = entity.posZ - z;
                double d9 = MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);
                if (d9 < f && (entity instanceof EntityNukeCloudSmall))
                {
                    {
                    	entity.setDead();
                    }
            	}
            }
        }
	}

}