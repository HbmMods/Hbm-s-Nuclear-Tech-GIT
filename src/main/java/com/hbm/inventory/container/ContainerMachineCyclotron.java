package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineCyclotron;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineCyclotron extends Container {

	private TileEntityMachineCyclotron cyclotron;
	
	public ContainerMachineCyclotron(InventoryPlayer invPlayer, TileEntityMachineCyclotron tile) {
		
		cyclotron = tile;
		
		//Input
		this.addSlotToContainer(new Slot(tile, 0, 11, 18));
		this.addSlotToContainer(new Slot(tile, 1, 11, 36));
		this.addSlotToContainer(new Slot(tile, 2, 11, 54));
		//Targets
		this.addSlotToContainer(new Slot(tile, 3, 101, 18));
		this.addSlotToContainer(new Slot(tile, 4, 101, 36));
		this.addSlotToContainer(new Slot(tile, 5, 101, 54));
		//Output
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tile, 6, 131, 18));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tile, 7, 131, 36));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tile, 8, 131, 54));
		//Battery
		this.addSlotToContainer(new Slot(tile, 9, 168, 83));
		//Upgrades
		this.addSlotToContainer(new SlotUpgrade(tile, 10, 60, 81));
		this.addSlotToContainer(new SlotUpgrade(tile, 11, 78, 81));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 15 + j * 18, 133 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 15 + i * 18, 191));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		
		ItemStack var3 = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			var3 = stack.copy();

			if(index <= 15) {
				if(!this.mergeItemStack(stack, 16, this.inventorySlots.size(), true)) {
					return null;
				}
				
			} else {
				
				if(stack.getItem() instanceof IBatteryItem || stack.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(stack, 9, 10, true))
						return null;
					
				} else if(stack.getItem() instanceof ItemMachineUpgrade) {
					if(!this.mergeItemStack(stack, 10, 11, true))
						if(!this.mergeItemStack(stack, 11, 12, true))
							return null;
					
				} else {
					
					if(stack.getItem() == ModItems.part_lithium ||
							stack.getItem() == ModItems.part_beryllium ||
							stack.getItem() == ModItems.part_carbon ||
							stack.getItem() == ModItems.part_copper ||
							stack.getItem() == ModItems.part_plutonium) {
						
						if(!this.mergeItemStack(stack, 0, 3, true))
							return null;
					} else {
						
						if(!this.mergeItemStack(stack, 3, 6, true))
							return null;
					}
				}
			}

			if(stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return cyclotron.isUseableByPlayer(player);
	}
}
