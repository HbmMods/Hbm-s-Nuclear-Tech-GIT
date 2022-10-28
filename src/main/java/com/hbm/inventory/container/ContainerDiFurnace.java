package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityDiFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDiFurnace extends Container {

	private TileEntityDiFurnace diFurnace;

	public ContainerDiFurnace(InventoryPlayer invPlayer, TileEntityDiFurnace tedf) {

		diFurnace = tedf;

		this.addSlotToContainer(new Slot(tedf, 0, 80, 18));
		this.addSlotToContainer(new Slot(tedf, 1, 80, 54));
		this.addSlotToContainer(new Slot(tedf, 2, 8, 36));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 3, 134, 36));

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
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		
		if(index >= 0 && index < 3 && button == 1 && mode == 0) {
			Slot slot = this.getSlot(index);
			if(!slot.getHasStack()) {
				if(!player.worldObj.isRemote) {
					if(index == 0) diFurnace.sideUpper = (byte) ((diFurnace.sideUpper + 1) % 6);
					if(index == 1) diFurnace.sideLower = (byte) ((diFurnace.sideLower + 1) % 6);
					if(index == 2) diFurnace.sideFuel = (byte) ((diFurnace.sideFuel + 1) % 6);
					
					diFurnace.markDirty();
				}
				return null;
			}
		}
		
		return super.slotClick(index, button, mode, player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= 3) {
				if(!this.mergeItemStack(var5, 4, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!this.mergeItemStack(var5, 0, 3, false)) {
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
