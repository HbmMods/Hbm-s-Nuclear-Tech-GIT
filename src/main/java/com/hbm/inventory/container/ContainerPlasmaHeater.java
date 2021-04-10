package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachinePlasmaHeater;
import com.hbm.tileentity.machine.TileEntityMicrowave;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPlasmaHeater extends Container {

	private TileEntityMachinePlasmaHeater microwave;

	public ContainerPlasmaHeater(InventoryPlayer invPlayer, TileEntityMachinePlasmaHeater tedf) {

		microwave = tedf;

		this.addSlotToContainer(new Slot(tedf, 0, 8, 53));
		this.addSlotToContainer(new Slot(tedf, 1, 44, 17));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 2, 44, 53));
		this.addSlotToContainer(new Slot(tedf, 3, 152, 17));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 4, 152, 53));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= 4) {
				if(!this.mergeItemStack(var5, 5, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				if(!this.mergeItemStack(var5, 0, 1, true))
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
		return microwave.isUseableByPlayer(player);
	}
}
