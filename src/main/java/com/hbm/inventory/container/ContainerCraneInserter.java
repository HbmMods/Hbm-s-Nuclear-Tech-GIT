package com.hbm.inventory.container;

import com.hbm.tileentity.network.TileEntityCraneInserter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCraneInserter extends Container {
	
	protected TileEntityCraneInserter inserter;
	
	public ContainerCraneInserter(InventoryPlayer invPlayer, TileEntityCraneInserter inserter) {
		this.inserter = inserter;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 7; j++) {
				this.addSlotToContainer(new Slot(inserter, j + i * 7, 8 + j * 18, 17 + i * 18));
			}
		}

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 103 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 161));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= inserter.getSizeInventory() - 1) {
				if(!this.mergeItemStack(var5, inserter.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!this.mergeItemStack(var5, 0, inserter.getSizeInventory(), false)) {
				return null;
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}

			var4.onPickupFromSlot(p_82846_1_, var5);
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inserter.isUseableByPlayer(player);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for(int i = 0; i < this.crafters.size(); i++) {
			((ICrafting)this.crafters.get(i)).sendProgressBarUpdate(this, 0, inserter.destroyer ? 1 : 0);
		}
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int target, int value) {
		switch(target) {
			case 0: inserter.destroyer = value != 0; break;
		}
	}
}
