package com.hbm.inventory.container;

import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntityMachineRadarNT;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineRadarNT extends Container {

	private TileEntityMachineRadarNT radar;

	public ContainerMachineRadarNT(InventoryPlayer invPlayer, TileEntityMachineRadarNT tedf) {
		this.radar = tedf;
		
		for(int i = 0; i < 8; i++) this.addSlotToContainer(new Slot(tedf, i, 26 + i * 18, 17));

		this.addSlotToContainer(new Slot(tedf, 8, 26, 44));
		this.addSlotToContainer(new Slot(tedf, 9, 152, 44));

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

			if(par2 <= 9) {
				if(!this.mergeItemStack(var5, 10, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(var3.getItem() instanceof IBatteryItem || var3.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(var5, 9, 10, false)) {
						return null;
					}
				} else {
					if(!this.mergeItemStack(var5, 0, 9, false)) {
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
		return radar.isUseableByPlayer(player);
	}
}
