package com.hbm.inventory.container;

import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotTakeOnly;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.inventory.fluid.Fluids;
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
		this.addSlotToContainer(new Slot(tile, 0, 17, 18));
		this.addSlotToContainer(new Slot(tile, 1, 17, 36));
		this.addSlotToContainer(new Slot(tile, 2, 17, 54));
		//Targets
		this.addSlotToContainer(new Slot(tile, 3, 107, 18));
		this.addSlotToContainer(new Slot(tile, 4, 107, 36));
		this.addSlotToContainer(new Slot(tile, 5, 107, 54));
		//Output
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tile, 6, 143, 18));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tile, 7, 143, 36));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tile, 8, 143, 54));
		//AMAT In
		this.addSlotToContainer(new Slot(tile, 9, 143, 90));
		//AMAT Out
		this.addSlotToContainer(new SlotTakeOnly(tile, 10, 143, 108));
		//Coolant In
		this.addSlotToContainer(new Slot(tile, 11, 62, 72));
		//Coolant Out
		this.addSlotToContainer(new SlotTakeOnly(tile, 12, 62, 90));
		//Battery
		this.addSlotToContainer(new Slot(tile, 13, 62, 108));
		//Upgrades
		this.addSlotToContainer(new SlotUpgrade(tile, 14, 17, 90));
		this.addSlotToContainer(new SlotUpgrade(tile, 15, 17, 108));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 56));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 56));
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
					if(!this.mergeItemStack(stack, 13, 14, true))
						return null;
					
				} else if(FluidContainerRegistry.getFluidContent(stack, Fluids.COOLANT) > 0) {
					if(!this.mergeItemStack(stack, 11, 12, true))
						return null;
					
				} else if(FluidContainerRegistry.getFullContainer(stack, Fluids.AMAT) != null) {
					if(!this.mergeItemStack(stack, 9, 10, true))
						return null;
					
				} else if(stack.getItem() instanceof ItemMachineUpgrade) {
					if(!this.mergeItemStack(stack, 14, 15, true))
						if(!this.mergeItemStack(stack, 15, 16, true))
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
