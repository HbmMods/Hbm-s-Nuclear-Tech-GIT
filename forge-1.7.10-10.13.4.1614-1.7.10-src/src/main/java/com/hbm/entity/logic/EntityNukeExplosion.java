/*package com.hbm.entity;

import com.hbm.explosion.ExplosionNukeGeneric;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityNukeExplosion extends Entity {
	
	public int age = 0;
	public int action = 0;
	public int rangeOfDestruction = 100;

	public EntityNukeExplosion(World p_i1582_1_) {
		super(p_i1582_1_);
	}

    public void onUpdate() {
        super.onUpdate();
        
        if(this.action > this.rangeOfDestruction)
        {
        	this.setDead();
        }
        if(this.age % 10 == 0)
        {
        	this.action++;
        	ExplosionNukeGeneric.detonateTestBomb(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, this.action);
        }
        
        this.age++;
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

}*/

package com.hbm.entity.logic;

import com.hbm.explosion.ExplosionNukeGeneric;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityNukeExplosion extends Entity {
	
	public int age = 0;
	public double action = 0;
	public int counter = 0;
	public int destructionRange = 0;
	public int vaporRange = 0;
	public int wasteRange = 0;
	public int damageRange = 0;
	final int steps = 35;

	public EntityNukeExplosion(World p_i1582_1_) {
		super(p_i1582_1_);
	}

    @Override
	public void onUpdate() {
        super.onUpdate();
        
        if(this.counter >= steps)
        {
        	this.action = this.wasteRange / 20 * this.counter;
        	ExplosionNukeGeneric.waste(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, (int)this.action);
        	
        	this.setDead();
        }
        
        {
        	this.counter++;
        	this.action = this.destructionRange / steps * this.counter;
        	ExplosionNukeGeneric.detonateTestBomb(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, (int)this.action);
        	
        	this.action = this.vaporRange / steps * this.counter;
        	ExplosionNukeGeneric.vapor(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, (int)this.action);
        	
        	this.action = this.damageRange / steps * this.counter;
        	ExplosionNukeGeneric.dealDamage(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, (int)this.action);
        	
        }
        
        this.age++;
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

}