package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachinePress;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachinePress extends Container {

	private TileEntityMachinePress press;

	private int power;
	private int progress;
	private int burnTime;
	private int maxBurn;

	public ContainerMachinePress(InventoryPlayer invPlayer, TileEntityMachinePress tedf) {

		power = 0;
		progress = 0;
		burnTime = 0;
		maxBurn = 0;

		press = tedf;

		// Coal
		this.addSlotToContainer(new Slot(tedf, 0, 26, 53));
		// Stamp
		this.addSlotToContainer(new Slot(tedf, 1, 80, 17));
		// Input
		this.addSlotToContainer(new Slot(tedf, 2, 80, 53));
		// Output
		this.addSlotToContainer(new SlotMachineOutput(tedf, 3, 140, 35));

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

			if(par2 <= 3) {
				if(!this.mergeItemStack(var5, 4, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!this.mergeItemStack(var5, 2, 3, false))
				if(!this.mergeItemStack(var5, 0, 1, false))
					if(!this.mergeItemStack(var5, 1, 2, false))
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
		return press.isUseableByPlayer(player);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for(int i = 0; i < this.crafters.size(); i++) {
			ICrafting par1 = (ICrafting) this.crafters.get(i);

			if(this.power != this.press.power) {
				par1.sendProgressBarUpdate(this, 0, this.press.power);
			}

			if(this.progress != this.press.progress) {
				par1.sendProgressBarUpdate(this, 1, this.press.progress);
			}

			if(this.burnTime != this.press.burnTime) {
				par1.sendProgressBarUpdate(this, 2, this.press.burnTime);
			}

			if(this.maxBurn != this.press.maxBurn) {
				par1.sendProgressBarUpdate(this, 3, this.press.maxBurn);
			}
		}

		this.power = this.press.power;
		this.progress = this.press.progress;
		this.burnTime = this.press.burnTime;
		this.maxBurn = this.press.maxBurn;
	}

	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0) {
			press.power = j;
		}
		if(i == 1) {
			press.progress = j;
		}
		if(i == 2) {
			press.burnTime = j;
		}
		if(i == 3) {
			press.maxBurn = j;
		}
	}
}
