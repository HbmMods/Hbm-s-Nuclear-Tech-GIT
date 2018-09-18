package com.hbm.tileentity.machine;

import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemKey;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityLockableBase extends TileEntity {
	
	protected int lock;
	private boolean isLocked = false;

	public boolean isLocked() {
		return isLocked;
	}
	
	public void lock() {
		
		if(lock == 0) {
			MainRegistry.logger.error("A block has been set to locked state before setting pins, this should not happen and may cause errors! " + this.toString());
		}
		
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
