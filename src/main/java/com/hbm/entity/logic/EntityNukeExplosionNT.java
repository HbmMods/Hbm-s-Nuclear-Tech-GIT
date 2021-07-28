package com.hbm.entity.logic;

import com.hbm.explosion.nt.IExplosionLogic;
import com.hbm.explosion.nt.Mark5;
import com.hbm.explosion.nt.Mark5Ausf2;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityNukeExplosionNT extends Entity {
	
	private IExplosionLogic explosion;
	
	public EntityNukeExplosionNT(World world) {
		super(world);
	}
	
	public EntityNukeExplosionNT loadLogic(IExplosionLogic explosion) {
		this.explosion = explosion;
		return this;
	}
	
	@Override
	public void onUpdate() {

		if(!worldObj.isRemote) {
			if(this.explosion == null || this.explosion.isDone()) {
				System.out.println(this.ticksExisted + " explosion done.");
				this.setDead();
				return;
			}
			
			this.explosion.updateLogic();
		}
	}
	
	public static EntityNukeExplosionNT statFacMKV(World world, double x, double y, double z, float power) {
		EntityNukeExplosionNT essploshun = new EntityNukeExplosionNT(world);
		essploshun.setPositionAndRotation(x, y, z, 0, 0);
		essploshun.loadLogic(new Mark5Ausf2(world, power, (float) x, (float) y, (float) z));
		return essploshun;
	}

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) { }

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) { }
}
