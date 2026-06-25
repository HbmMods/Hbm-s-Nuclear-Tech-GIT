package com.hbm.tileentity.network.pneumatic;

import com.hbm.inventory.container.ContainerPneumoStorageImporter;
import com.hbm.inventory.gui.GUIPneumoStorageImporter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TileEntityPneumoStorageImporter extends TileEntityPneumaticMachineBase {
	
	public int[] delay = new int[9];
	public int[] SLOT_ACCESS = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};

	public TileEntityPneumoStorageImporter() {
		super(9);
	}

	@Override
	public String getName() {
		return "container.pneumoStorageImporter";
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		super.setInventorySlotContents(i, stack);
		
		if(stack != null) this.delay[i] = Math.max(this.delay[i], 1);
	}

	@Override public boolean isItemValidForSlot(int slot, ItemStack stack) { return true; }
	@Override public int[] getAccessibleSlotsFromSide(int side) { return SLOT_ACCESS; }

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote) {
			
			if(this.cache != null && !this.cache.hasExpired) for(int i = 0; i < 9; i++) {
				if(this.delay[i] > 0) {
					this.delay[i]--;
					continue;
				}
				ItemStack stack = slots[i];
				if(stack == null) continue;
				
				int leftover = (int) this.cache.addItemsAndReturnQuantity(stack, stack.stackSize);
				if(leftover == stack.stackSize) {
					this.delay[i] = 100;
				} else {
					this.decrStackSize(i, stack.stackSize - leftover);
				}
			}
		}
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerPneumoStorageImporter(player.inventory, this); }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIPneumoStorageImporter(player.inventory, this); }
}
