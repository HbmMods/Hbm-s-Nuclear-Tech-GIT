package com.hbm.entity.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityRequestDrone extends EntityDroneBase {
	
	public ItemStack heldItem;

	public EntityRequestDrone(World world) {
		super(world);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		
		if(nbt.hasKey("held")) {
			NBTTagCompound stack = nbt.getCompoundTag("held");
			this.heldItem = ItemStack.loadItemStackFromNBT(stack);
		}

		this.dataWatcher.updateObject(10, nbt.getByte("app"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		
		if(heldItem != null) {
			NBTTagCompound stack = new NBTTagCompound();
			this.heldItem.writeToNBT(stack);
			nbt.setTag("held", stack);
		}

		nbt.setByte("app", this.dataWatcher.getWatchableObjectByte(10));
	}
}
