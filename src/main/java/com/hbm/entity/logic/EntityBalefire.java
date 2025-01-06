package com.hbm.entity.logic;

import org.apache.logging.log4j.Level;

import com.hbm.config.GeneralConfig;
import com.hbm.explosion.ExplosionBalefire;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.main.MainRegistry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityBalefire extends EntityExplosionChunkloading  {
	
	public int age = 0;
	public int destructionRange = 0;
	public ExplosionBalefire exp;
	public int speed = 1;
	public boolean did = false;

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		age = nbt.getInteger("age");
		destructionRange = nbt.getInteger("destructionRange");
		speed = nbt.getInteger("speed");
		did = nbt.getBoolean("did");
		
    	
		exp = new ExplosionBalefire((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, this.destructionRange);
		exp.readFromNbt(nbt, "exp_");
    	
    	this.did = true;
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("age", age);
		nbt.setInteger("destructionRange", destructionRange);
		nbt.setInteger("speed", speed);
		nbt.setBoolean("did", did);
    	
		if(exp != null)
			exp.saveToNbt(nbt, "exp_");
		
	}

	public EntityBalefire(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(!worldObj.isRemote) loadChunk((int) Math.floor(posX / 16D), (int) Math.floor(posZ / 16D));

		if(!this.did) {
			if(GeneralConfig.enableExtendedLogging && !worldObj.isRemote)
				MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized BF explosion at " + posX + " / " + posY + " / " + posZ + " with strength " + destructionRange + "!");

			exp = new ExplosionBalefire((int) this.posX, (int) this.posY, (int) this.posZ, this.worldObj, this.destructionRange);

			this.did = true;
		}

		speed += 1; // increase speed to keep up with expansion

		boolean flag = false;
		for(int i = 0; i < this.speed; i++) {
			flag = exp.update();

			if(flag) {
				clearChunkLoader();
				this.setDead();
			}
		}

		if(!flag) {
			ExplosionNukeGeneric.dealDamage(this.worldObj, this.posX, this.posY, this.posZ, this.destructionRange * 2);
		}

		age++;
	}
}
