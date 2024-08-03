package com.hbm.tileentity;

import com.hbm.interfaces.IControlReceiver;

import com.hbm.interfaces.ICopiable;
import com.hbm.module.ModulePatternMatcher;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public interface IControlReceiverFilter extends IControlReceiver, ICopiable {
	
	void nextMode(int i);

	/*
	default ModulePatternMatcher getMatcher(){

	}*/

	@Override
	default void receiveControl(NBTTagCompound data) {
		if(data.hasKey("slot")) {
			setFilterContents(data);
		}
	}

	/**
	 * Expects the implementor to be a tile entity and an IInventory
	 * @param nbt
	 */
	default void setFilterContents(NBTTagCompound nbt) {
		TileEntity tile = (TileEntity) this;
		IInventory inv = (IInventory) this;
		int slot = nbt.getInteger("slot");
		inv.setInventorySlotContents(slot, new ItemStack(Item.getItemById(nbt.getInteger("id")), 1, nbt.getInteger("meta")));
		nextMode(slot);
		tile.getWorldObj().markTileEntityChunkModified(tile.xCoord, tile.yCoord, tile.zCoord, tile);
	}
	/**
	 * Used for the copy tool
	 * @return The start and end (start inclusive, end exclusive) of the filter slots of the TE
	 */
	int[] getFilterSlots();

    @Override
	default NBTTagCompound getSettings() {
		IInventory inv = (IInventory) this;
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList tags = new NBTTagList();
		int count = 0;
		for (int i = getFilterSlots()[0]; i < getFilterSlots()[1]; i++) {
			NBTTagCompound slotNBT = new NBTTagCompound();
			if(inv.getStackInSlot(i) != null) {
				slotNBT.setByte("slot", (byte) count);
				inv.getStackInSlot(i).writeToNBT(slotNBT);
				tags.appendTag(slotNBT);
			}
			count++;
		}
		nbt.setTag("items", tags);

		return nbt;
	}

	@Override
	default void pasteSettings(NBTTagCompound nbt) {
		TileEntity tile = (TileEntity) this;
		IInventory inv = (IInventory) this;
		NBTTagList items = nbt.getTagList("items", 10);
		int listSize = items.tagCount();
		if(listSize > 0) {
			int count = 0;
			for (int i = getFilterSlots()[0]; i < getFilterSlots()[1]; i++) {
				if (i < listSize) {
					NBTTagCompound slotNBT = items.getCompoundTagAt(count);
					byte slot = slotNBT.getByte("slot");
					ItemStack loadedStack = ItemStack.loadItemStackFromNBT(slotNBT);
					if (loadedStack != null) {
						inv.setInventorySlotContents(slot + getFilterSlots()[0], ItemStack.loadItemStackFromNBT(slotNBT));
						nextMode(slot);
						tile.getWorldObj().markTileEntityChunkModified(tile.xCoord, tile.yCoord, tile.zCoord, tile);
					}
				}
				count++;
			}

		}
	}
}
