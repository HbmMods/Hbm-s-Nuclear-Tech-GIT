package com.hbm.tileentity;

import com.hbm.interfaces.IControlReceiver;

import com.hbm.interfaces.ICopiable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
		NBTTagCompound stack = nbt.getCompoundTag("stack");
		ItemStack item = ItemStack.loadItemStackFromNBT(stack);
		inv.setInventorySlotContents(slot, item);
		nextMode(slot);
		tile.getWorldObj().markTileEntityChunkModified(tile.xCoord, tile.yCoord, tile.zCoord, tile);
	}
	/**
	 * Used for the copy tool
	 * @return The start and end (start inclusive, end exclusive) of the filter slots of the TE
	 */
	int[] getFilterSlots();

    @Override
	default NBTTagCompound getSettings(World world, int x, int y, int z) {
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
	default void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {
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
					//whether the filter info came from a router
					boolean router = nbt.hasKey("modes") && slot > index * 5 && slot < index * + 5;

					if (loadedStack != null && index < listSize && (slot < getFilterSlots()[1] || router)) {
						inv.setInventorySlotContents(slot + getFilterSlots()[0], ItemStack.loadItemStackFromNBT(slotNBT));
						nextMode(slot);
						tile.getWorldObj().markTileEntityChunkModified(tile.xCoord, tile.yCoord, tile.zCoord, tile);
					}
				}
				count++;
			}
		}
	}

	@Override
	default String[] infoForDisplay(World world, int x, int y, int z) {
		return new String[] { "copytool.filter" };
	}
}
