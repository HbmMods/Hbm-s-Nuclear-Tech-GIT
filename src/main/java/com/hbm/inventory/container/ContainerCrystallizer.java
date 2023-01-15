package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.machine.TileEntityMachineCrystallizer;

import api.hbm.energy.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCrystallizer extends Container {

	private TileEntityMachineCrystallizer diFurnace;

	public ContainerCrystallizer(InventoryPlayer invPlayer, TileEntityMachineCrystallizer tedf) {
		diFurnace = tedf;

		//Input
		this.addSlotToContainer(new Slot(tedf, 0, 62, 45));
		//Battery
		this.addSlotToContainer(new Slot(tedf, 1, 152, 72));
		//Output
		this.addSlotToContainer(new SlotMachineOutput(tedf, 2, 113, 45));
		//Fluid slots
		this.addSlotToContainer(new Slot(tedf, 3, 17, 18));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 4, 17, 54));
		//Upgrades
		this.addSlotToContainer(new SlotUpgrade(tedf, 5, 80, 18));
		this.addSlotToContainer(new SlotUpgrade(tedf, 6, 98, 18));
		//Fluid ID
		this.addSlotToContainer(new Slot(tedf, 7, 35, 72));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 122 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 180));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			SlotMachineOutput.checkAchievements(p_82846_1_, var5);

			if(par2 <= diFurnace.getSizeInventory() - 1) {
				if(!this.mergeItemStack(var5, diFurnace.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(var3.getItem() instanceof IBatteryItem) {
					if(!this.mergeItemStack(var5, 1, 2, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(var5, 7, 8, false)) {
						return null;
					}
				} else {
					if(!this.mergeItemStack(var5, 0, 1, false)) {
						return null;
					}
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
		return diFurnace.isUseableByPlayer(player);
	}
}
