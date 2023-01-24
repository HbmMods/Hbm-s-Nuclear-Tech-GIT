package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineCentrifuge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCentrifuge extends Container {

	private TileEntityMachineCentrifuge diFurnace;

	public ContainerCentrifuge(InventoryPlayer invPlayer, TileEntityMachineCentrifuge tedf) {

		diFurnace = tedf;

		this.addSlotToContainer(new Slot(tedf, 0, 36, 50));
		this.addSlotToContainer(new Slot(tedf, 1, 9, 50));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 2, 63, 50));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 3, 83, 50));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 4, 103, 50));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 5, 123, 50));
		this.addSlotToContainer(new SlotUpgrade(tedf, 6, 149, 22));
		this.addSlotToContainer(new SlotUpgrade(tedf, 7, 149, 40));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 104 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 162));
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

			if(par2 <= 6) {
				if(!this.mergeItemStack(var5, 6, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!this.mergeItemStack(var5, 0, 2, false)) {
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
		return diFurnace.isUseableByPlayer(player);
	}

}