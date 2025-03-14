package com.hbm.entity.grenade;

import com.hbm.entity.effect.EntityMist;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityDisperserCanister extends EntityGrenadeBase {
	
	public EntityDisperserCanister(World world) {
		super(world);
	}

	public EntityDisperserCanister(World world, EntityLivingBase living) {
		super(world, living);
	}

	public EntityDisperserCanister(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public EntityDisperserCanister setFluid(int id) {
		this.dataWatcher.updateObject(12, id);
		return this;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(12, 0);
		this.dataWatcher.addObject(13, 0);
	}

	public EntityDisperserCanister setType(int id) {
		this.dataWatcher.updateObject(13, id);
		return this;
	}

	public FluidType getFluid() {
		return Fluids.fromID(this.dataWatcher.getWatchableObjectInt(12));
	}

	public Item getType() {
		return Item.getItemById(this.dataWatcher.getWatchableObjectInt(13));
	}

	@Override
	public void explode() {
		if(!worldObj.isRemote) {
			EntityMist mist = new EntityMist(worldObj);
			mist.setType(getFluid());
			mist.setPosition(posX, posY, posZ);
			mist.setArea(10, 5);
			mist.setDuration(80);
			worldObj.spawnEntityInWorld(mist);
			this.setDead();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("fluid", this.dataWatcher.getWatchableObjectInt(12));
		nbt.setInteger("item", this.dataWatcher.getWatchableObjectInt(13));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.dataWatcher.updateObject(12, nbt.getInteger("fluid"));
		this.dataWatcher.updateObject(13, nbt.getInteger("item"));

	}
}
