package com.hbm.entity.grenade;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGenericGrenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityGrenadeImpactGeneric extends EntityGrenadeBase implements IGenericGrenade {

	public EntityGrenadeImpactGeneric(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityGrenadeImpactGeneric(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
	}

	public EntityGrenadeImpactGeneric(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}
	
	public EntityGrenadeImpactGeneric setType(ItemGenericGrenade grenade) {
		this.dataWatcher.updateObject(12, Item.getIdFromItem(grenade));
		return this;
	}
	
	@Override
	public ItemGenericGrenade getGrenade() {
		ItemGenericGrenade gren = (ItemGenericGrenade) Item.getItemById(this.dataWatcher.getWatchableObjectInt(12));
		return gren != null ? gren : (ItemGenericGrenade) ModItems.grenade_kyiv;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(12, 0);
	}

	@Override
	public void explode() {

		if(!this.worldObj.isRemote && getGrenade() != null) {
			getGrenade().explode(this, this.getThrower(), worldObj, posX, posY, posZ);
			this.setDead();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("grenade", this.dataWatcher.getWatchableObjectInt(12));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.dataWatcher.updateObject(12, nbt.getInteger("grenade"));
	}
}
