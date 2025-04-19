package com.hbm.entity.mob;

import com.hbm.entity.effect.EntityMist;
import com.hbm.inventory.fluid.Fluids;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityCreeperPhosgene extends EntityCreeper {

	public EntityCreeperPhosgene(World world) {
		super(world);
		this.fuseTime = 20; //ehehehehehe
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		
		if(!source.isDamageAbsolute() && !source.isUnblockable()) {
			amount -= 4F;
		}
		
		if(amount < 0) return false;
		
		return super.attackEntityFrom(source, amount);
	}
	
	@Override
	public boolean getCanSpawnHere() {
		return super.getCanSpawnHere() && this.dimension == 0;
	}

	@Override
	public void func_146077_cc() {
		
		if(!this.worldObj.isRemote) {
			this.setDead();
			
			worldObj.createExplosion(this, posX, posY + this.height / 2, posZ, 2F, false);
			EntityMist mist = new EntityMist(worldObj);
			mist.setType(Fluids.PHOSGENE);
			mist.setPosition(posX, posY, posZ);
			mist.setArea(10, 5);
			mist.setDuration(150);
			worldObj.spawnEntityInWorld(mist);
		}
	}
}
