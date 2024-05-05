package com.hbm.inventory.container;

import com.hbm.inventory.SlotDeprecated;
import com.hbm.inventory.SlotTakeOnly;
import com.hbm.tileentity.machine.oil.TileEntityMachineVacuumDistill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineVacuumDistill extends Container {

	private TileEntityMachineVacuumDistill distill;
	
	public ContainerMachineVacuumDistill(InventoryPlayer invPlayer, TileEntityMachineVacuumDistill tedf) {
		
		distill = tedf;
		
		//Battery
		this.addSlotToContainer(new Slot(tedf, 0, 26, 90));
		//Canister Input (removed, requires pressurization)
		this.addSlotToContainer(new SlotDeprecated(tedf, 1, 44, 90));
		//Canister Output (same as above)
		this.addSlotToContainer(new SlotDeprecated(tedf, 2, 44, 108));
		//Heavy Oil Input
		this.addSlotToContainer(new Slot(tedf, 3, 80, 90));
		//Heavy Oil Output
		this.addSlotToContainer(new SlotTakeOnly(tedf, 4, 80, 108));
		//Nahptha Input
		this.addSlotToContainer(new Slot(tedf, 5, 98, 90));
		//Nahptha Output
		this.addSlotToContainer(new SlotTakeOnly(tedf, 6, 98, 108));
		//Light Oil Input
		this.addSlotToContainer(new Slot(tedf, 7, 116, 90));
		//Light Oil Output
		this.addSlotToContainer(new SlotTakeOnly(tedf, 8, 116, 108));
		//Petroleum Input
		this.addSlotToContainer(new Slot(tedf, 9, 134, 90));
		//Petroleum Output
		this.addSlotToContainer(new SlotTakeOnly(tedf, 10, 134, 108));
		//Fluid ID
		this.addSlotToContainer(new Slot(tedf, 11, 26, 108));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 156 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 214));
		}
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= 10) {
				if(!this.mergeItemStack(var5, 11, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!this.mergeItemStack(var5, 0, 1, false))
				if(!this.mergeItemStack(var5, 1, 2, false))
					if(!this.mergeItemStack(var5, 3, 4, false))
						if(!this.mergeItemStack(var5, 5, 6, false))
							if(!this.mergeItemStack(var5, 7, 8, false))
								if(!this.mergeItemStack(var5, 9, 10, false)) {
									return null;
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
		return distill.isUseableByPlayer(player);
	}
}
