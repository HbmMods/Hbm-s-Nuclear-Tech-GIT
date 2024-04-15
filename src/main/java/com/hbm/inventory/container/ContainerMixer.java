package com.hbm.inventory.container;

import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineMixer;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMixer extends Container {
	
	private TileEntityMachineMixer mixer;
	
	public ContainerMixer(InventoryPlayer player, TileEntityMachineMixer mixer) {
		this.mixer = mixer;

		//Battery
		this.addSlotToContainer(new Slot(mixer, 0, 23, 77));
		//Item Input
		this.addSlotToContainer(new Slot(mixer, 1, 43, 77));
		//Fluid ID
		this.addSlotToContainer(new Slot(mixer, 2, 117, 77));
		//Upgrades
		this.addSlotToContainer(new Slot(mixer, 3, 137, 24));
		this.addSlotToContainer(new Slot(mixer, 4, 137, 42));

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
				
				if(var3.getItem() instanceof IBatteryItem) {
					if(!this.mergeItemStack(var5, 0, 1, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(var5, 2, 3, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof ItemMachineUpgrade) {
					if(!this.mergeItemStack(var5, 3, 4, false)) {
						return null;
					}
				} else {
					if(!this.mergeItemStack(var5, 1, 2, false)) {
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
		return mixer.isUseableByPlayer(player);
	}
}
