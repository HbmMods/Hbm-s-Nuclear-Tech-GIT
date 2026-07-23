package com.hbm.inventory.container;

import com.hbm.inventory.SlotTakeOnly;
import com.hbm.items.machine.ItemSatChip;
import com.hbm.tileentity.machine.TileEntityMachineSatDock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSatDock extends Container {
	
	private final TileEntityMachineSatDock tileSatelliteDock;
	
	public ContainerSatDock(InventoryPlayer invPlayer, TileEntityMachineSatDock tesd) {
		tileSatelliteDock = tesd;

		//Storage
		this.addSlotToContainer(new SlotTakeOnly(tesd, 0, 71, 18));
		this.addSlotToContainer(new SlotTakeOnly(tesd, 1, 71 + 18, 18));
		this.addSlotToContainer(new SlotTakeOnly(tesd, 2, 71 + 18 * 2, 18));
		this.addSlotToContainer(new SlotTakeOnly(tesd, 3, 71 + 18 * 3, 18));
		this.addSlotToContainer(new SlotTakeOnly(tesd, 4, 71 + 18 * 4, 18));
		this.addSlotToContainer(new SlotTakeOnly(tesd, 5, 71, 36));
		this.addSlotToContainer(new SlotTakeOnly(tesd, 6, 71 + 18, 36));
		this.addSlotToContainer(new SlotTakeOnly(tesd, 7, 71 + 18 * 2, 36));
		this.addSlotToContainer(new SlotTakeOnly(tesd, 8, 71 + 18 * 3, 36));
		this.addSlotToContainer(new SlotTakeOnly(tesd, 9, 71 + 18 * 4, 36));
		this.addSlotToContainer(new SlotTakeOnly(tesd, 10, 71, 54));
		this.addSlotToContainer(new SlotTakeOnly(tesd, 11, 71 + 18, 54));
		this.addSlotToContainer(new SlotTakeOnly(tesd, 12, 71 + 18 * 2, 54));
		this.addSlotToContainer(new SlotTakeOnly(tesd, 13, 71 + 18 * 3, 54));
		this.addSlotToContainer(new SlotTakeOnly(tesd, 14, 71 + 18 * 4, 54));
		//Chip
		this.addSlotToContainer(new Slot(tesd, 15, 26, 36) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof ItemSatChip;
			}
		});
		
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
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);
		
		if (var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			
            if (par2 <= 15) {
				if (!this.mergeItemStack(var5, 16, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if (!this.mergeItemStack(var5, 0, 15, false)) {
				return null;
			}
			
			if (var5.stackSize == 0) {
				var4.putStack(null);
			} else {
				var4.onSlotChanged();
			}
		}
		
		return var3;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileSatelliteDock.isUseableByPlayer(player);
	}
}
