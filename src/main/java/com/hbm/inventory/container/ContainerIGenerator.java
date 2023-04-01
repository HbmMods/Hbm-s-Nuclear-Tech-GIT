package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.tileentity.machine.TileEntityMachineIGenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerIGenerator extends Container {
	
	private TileEntityMachineIGenerator igen;
	
	public ContainerIGenerator(InventoryPlayer invPlayer, TileEntityMachineIGenerator te) {
		
		igen = te;
		
		//Battery
		this.addSlotToContainer(new Slot(te, 0, 8, 134));
		//Water
		this.addSlotToContainer(new Slot(te, 1, 62, 112));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, te, 2, 154, 112));
		//Solid Fuel
		this.addSlotToContainer(new Slot(te, 3, 67, 51));
		this.addSlotToContainer(new Slot(te, 4, 85, 51));
		this.addSlotToContainer(new Slot(te, 5, 67, 87));
		this.addSlotToContainer(new Slot(te, 6, 85, 87));
		//Lubricant
		this.addSlotToContainer(new Slot(te, 7, 132, 33));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, te, 8, 132, 51));
		//Fuel
		this.addSlotToContainer(new Slot(te, 9, 132, 69));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, te, 10, 132, 87));
		//RTG
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 2; j++) {
				this.addSlotToContainer(new Slot(te, 11 + i * 2 + j, 15 + j * 18, 35 + i * 18));
			}
		}
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 71));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 71));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= 20) {
				if(!this.mergeItemStack(var5, 21, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
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
		return igen.isUseableByPlayer(player);
	}
}
