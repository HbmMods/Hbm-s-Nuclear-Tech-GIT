package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotSmelting;
import com.hbm.tileentity.machine.TileEntityFurnaceBrick;
import com.hbm.util.InventoryUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerFurnaceBrick extends Container {

	private TileEntityFurnaceBrick furnace;

	public ContainerFurnaceBrick(InventoryPlayer invPlayer, TileEntityFurnaceBrick tedf) {
		furnace = tedf;

		//input
		this.addSlotToContainer(new Slot(tedf, 0, 62, 35));
		//fuel
		this.addSlotToContainer(new Slot(tedf, 1, 35, 17));
		//output
		this.addSlotToContainer(new SlotSmelting(invPlayer.player, tedf, 2, 116, 35));
		//ash
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 3, 35, 53));

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
			} else {
				
				if(!TileEntityFurnace.isItemFuel(var5)) {
					if(!InventoryUtil.mergeItemStack(this.inventorySlots, var5, 0, 1, false))
						return null;
				} else {
					if(!this.mergeItemStack(var5, 1, 2, false) && !this.mergeItemStack(var5, 0, 1, false)) return null;
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
		return furnace.isUseableByPlayer(player);
	}
}
