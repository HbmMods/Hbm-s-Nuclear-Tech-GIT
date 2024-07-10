package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.items.ModItems;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineOreSlopper;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerOreSlopper extends Container {
	
	public TileEntityMachineOreSlopper slopper;

	public ContainerOreSlopper(InventoryPlayer player, TileEntityMachineOreSlopper slopper) {
		this.slopper = slopper;

		//Battery
		this.addSlotToContainer(new Slot(slopper, 0, 8, 72));
		//Fluid ID
		this.addSlotToContainer(new Slot(slopper, 1, 26, 72));
		//Input
		this.addSlotToContainer(new Slot(slopper, 2, 71, 27));
		//Outputs
		this.addSlotToContainer(new SlotCraftingOutput(player.player, slopper, 3, 134, 18));
		this.addSlotToContainer(new SlotCraftingOutput(player.player, slopper, 4, 152, 18));
		this.addSlotToContainer(new SlotCraftingOutput(player.player, slopper, 5, 134, 36));
		this.addSlotToContainer(new SlotCraftingOutput(player.player, slopper, 6, 152, 36));
		this.addSlotToContainer(new SlotCraftingOutput(player.player, slopper, 7, 134, 54));
		this.addSlotToContainer(new SlotCraftingOutput(player.player, slopper, 8, 152, 54));
		//Upgrades
		this.addSlotToContainer(new Slot(slopper, 9, 62, 72));
		this.addSlotToContainer(new Slot(slopper, 10, 80, 72));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 122 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 180));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= 10) {
				if(!this.mergeItemStack(var5, 11, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(var3.getItem() == ModItems.bedrock_ore_base) {
					if(!this.mergeItemStack(var5, 2, 3, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof ItemMachineUpgrade) {
					if(!this.mergeItemStack(var5, 9, 11, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(var5, 1, 2, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof IBatteryItem || var3.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(var5, 0, 1, false)) {
						return null;
					}
				} else {
					return null;
				}
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return slopper.isUseableByPlayer(player);
	}
}
