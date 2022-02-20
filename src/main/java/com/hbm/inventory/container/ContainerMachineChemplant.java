package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineChemplantNew;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineChemplant extends Container {

	private TileEntityMachineChemplantNew nukeBoy;

	public ContainerMachineChemplant(InventoryPlayer invPlayer, TileEntityMachineChemplantNew tedf) {
		nukeBoy = tedf;

		// Battery
		this.addSlotToContainer(new Slot(tedf, 0, 80, 18));
		// Upgrades
		this.addSlotToContainer(new Slot(tedf, 1, 116, 18));
		this.addSlotToContainer(new Slot(tedf, 2, 116, 36));
		this.addSlotToContainer(new Slot(tedf, 3, 116, 54));
		// Schematic
		this.addSlotToContainer(new Slot(tedf, 4, 80, 54));
		// Outputs
		this.addSlotToContainer(new SlotMachineOutput(tedf, 5, 134, 90));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 6, 152, 90));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 7, 134, 108));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 8, 152, 108));
		// Fluid Output In
		this.addSlotToContainer(new Slot(tedf, 9, 134, 54));
		this.addSlotToContainer(new Slot(tedf, 10, 152, 54));
		// Fluid Outputs Out
		this.addSlotToContainer(new SlotMachineOutput(tedf, 11, 134, 72));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 12, 152, 72));
		// Input
		this.addSlotToContainer(new Slot(tedf, 13, 8, 90));
		this.addSlotToContainer(new Slot(tedf, 14, 26, 90));
		this.addSlotToContainer(new Slot(tedf, 15, 8, 108));
		this.addSlotToContainer(new Slot(tedf, 16, 26, 108));
		// Fluid Input In
		this.addSlotToContainer(new Slot(tedf, 17, 8, 54));
		this.addSlotToContainer(new Slot(tedf, 18, 26, 54));
		// Fluid Input Out
		this.addSlotToContainer(new SlotMachineOutput(tedf, 19, 8, 72));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 20, 26, 72));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 56));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 56));
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

			if(par2 <= 20) {
				if(!this.mergeItemStack(var5, 21, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!this.mergeItemStack(var5, 4, 5, false))
				if(!this.mergeItemStack(var5, 13, 19, false))
					return null;

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
		return nukeBoy.isUseableByPlayer(player);
	}
}
