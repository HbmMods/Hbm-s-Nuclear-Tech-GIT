package com.hbm.tileentity.machine;

import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemKey;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityLockableBase extends TileEntity {
	
	private int lock;
	private boolean isLocked = false;

	public boolean isLocked() {
		return isLocked;
	}
	
	public void lock() {
		isLocked = true;
	}
	
	public void setPins(int pins) {
		lock = pins;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		lock = nbt.getInteger("lock");
		isLocked = nbt.getBoolean("isLocked");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("lock", lock);
		nbt.setBoolean("isLocked", isLocked);
	}

	public boolean canAccess(EntityPlayer player) {
		
		if(!isLocked) {
			return true;
		} else {
			ItemStack stack = player.getHeldItem();
			
			if(stack != null && stack.getItem() instanceof ItemKey &&
					ItemKey.getPins(stack) == this.lock) {
	        	worldObj.playSoundAtEntity(player, "hbm:block.lockOpen", 1.0F, 1.0F);
				return true;
			}
			
			if(stack != null && stack.getItem() == ModItems.key_red) {
	        	worldObj.playSoundAtEntity(player, "hbm:block.lockOpen", 1.0F, 1.0F);
				return true;
			}
		}
		
		return false;
	}

}
