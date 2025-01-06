package com.hbm.entity.grenade;

import com.hbm.entity.effect.EntityMist;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityDisperserCanister extends EntityGrenadeBase {
	
	public EntityDisperserCanister(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityDisperserCanister(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
	}

	public EntityDisperserCanister(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
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
