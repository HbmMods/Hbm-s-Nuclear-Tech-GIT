package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineAssembler extends Container {

private TileEntityMachineAssembler assembler;
	
	public ContainerMachineAssembler(InventoryPlayer invPlayer, TileEntityMachineAssembler te) {
		assembler = te;

		//Battery
		this.addSlotToContainer(new Slot(te, 0, 80, 18));
		//Upgrades
		this.addSlotToContainer(new Slot(te, 1, 152, 18));
		this.addSlotToContainer(new Slot(te, 2, 152, 36));
		this.addSlotToContainer(new Slot(te, 3, 152, 54));
		//Schematic
		this.addSlotToContainer(new Slot(te, 4, 80, 54));
		//Output
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, te, 5, 134, 90));
		//Input
		this.addSlotToContainer(new Slot(te, 6, 8, 18));
		this.addSlotToContainer(new Slot(te, 7, 26, 18));
		this.addSlotToContainer(new Slot(te, 8, 8, 36));
		this.addSlotToContainer(new Slot(te, 9, 26, 36));
		this.addSlotToContainer(new Slot(te, 10, 8, 54));
		this.addSlotToContainer(new Slot(te, 11, 26, 54));
		this.addSlotToContainer(new Slot(te, 12, 8, 72));
		this.addSlotToContainer(new Slot(te, 13, 26, 72));
		this.addSlotToContainer(new Slot(te, 14, 8, 90));
		this.addSlotToContainer(new Slot(te, 15, 26, 90));
		this.addSlotToContainer(new Slot(te, 16, 8, 108));
		this.addSlotToContainer(new Slot(te, 17, 26, 108));
		
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
		ItemStack rStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			rStack = stack.copy();
			SlotCraftingOutput.checkAchievements(player, stack);
			
            if (index <= 17) {
				if (!this.mergeItemStack(stack, 18, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(rStack.getItem() instanceof IBatteryItem || rStack.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(stack, 0, 1, false)) return null;
				} else if(rStack.getItem() instanceof ItemMachineUpgrade) {
					if(!this.mergeItemStack(stack, 1, 4, false)) return null;
				} else if(rStack.getItem() instanceof ItemAssemblyTemplate) {
					if(!this.mergeItemStack(stack, 4, 5, false)) return null;
				} else {
					if(!this.mergeItemStack(stack, 6, 18, false)) return null;
				}
			}
			
			if(stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if(stack.stackSize == rStack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, rStack);
		}

		return rStack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return assembler.isUseableByPlayer(player);
	}
}
