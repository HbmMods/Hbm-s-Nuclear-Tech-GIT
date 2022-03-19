package com.hbm.entity.grenade;

import com.hbm.items.weapon.ItemGenericGrenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityGrenadeBouncyGeneric extends EntityGrenadeBouncyBase implements IGenericGrenade {

	public EntityGrenadeBouncyGeneric(World world) {
		super(world);
	}

	public EntityGrenadeBouncyGeneric(World world, EntityLivingBase living) {
		super(world, living);
	}

	public EntityGrenadeBouncyGeneric(World world, double x, double y, double z) {
		super(world, x, y, z);
	}
	
	public EntityGrenadeBouncyGeneric setType(ItemGenericGrenade grenade) {
		this.dataWatcher.updateObject(12, Item.getIdFromItem(grenade));
		return this;
	}

	@Override
	public ItemGenericGrenade getGrenade() {
		return (ItemGenericGrenade) Item.getItemById(this.dataWatcher.getWatchableObjectInt(12));
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(12, 0);
	}

	@Override
	public void explode() {
		getGrenade().explode(worldObj, posX, posY, posZ);
		this.setDead();
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("grenade", this.dataWatcher.getWatchableObjectInt(12));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.dataWatcher.updateObject(12, nbt.getInteger("grenade"));
	}

	@Override
	protected int getMaxTimer() {
		return getGrenade().getMaxTimer();
	}

	@Override
	protected double getBounceMod() {
		return getGrenade().getBounceMod();
	}
}
