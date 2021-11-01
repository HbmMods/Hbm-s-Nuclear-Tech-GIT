package com.hbm.entity.mob;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.world.World;

public class EntityDuck extends EntityChicken {

	public EntityDuck(World world) {
		super(world);
	}
	
    protected String getLivingSound() {
        return "hbm:entity.ducc";
    }

    protected String getHurtSound() {
        return "hbm:entity.ducc";
    }

    protected String getDeathSound() {
        return "hbm:entity.ducc";
    }

    public EntityDuck createChild(EntityAgeable entity)
    {
        return new EntityDuck(this.worldObj);
    }
}
