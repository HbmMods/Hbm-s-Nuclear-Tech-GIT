package com.hbm.tileentity.machine.rbmk;

import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;

import net.minecraft.item.ItemStack;

public class TileEntityRBMKStorage extends TileEntityRBMKSlottedBase implements IRBMKLoadable {

	public TileEntityRBMKStorage() {
		super(12);
	}

	@Override
	public String getName() {
		return "container.rbmkStorage";
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote && worldObj.getTotalWorldTime() % 10 == 0) {
			
			for(int i = 0; i < slots.length - 1; i++) {
				
				if(slots[i] == null && slots[i + 1] != null) {
					slots[i] = slots[i + 1];
					slots[i + 1] = null;
				}
			}
		}
		
		super.updateEntity();
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.STORAGE;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return itemStack.getItem() instanceof ItemRBMKRod;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	}

	@Override
	public boolean canLoad(ItemStack toLoad) {
		return slots[11] == null;
	}

	@Override
	public void load(ItemStack toLoad) {
		slots[11] = toLoad.copy();
	}

	@Override
	public boolean canUnload() {
		return slots[0] != null;
	}

	@Override
	public ItemStack provideNext() {
		return slots[0];
	}

	@Override
	public void unload() {
		slots[0] = null;
	}
}
