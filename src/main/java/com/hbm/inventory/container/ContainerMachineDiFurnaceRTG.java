package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.items.machine.ItemRTGPellet;
import com.hbm.tileentity.machine.TileEntityDiFurnaceRTG;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineDiFurnaceRTG extends Container {
	private TileEntityDiFurnaceRTG bFurnace;
	// private int progress;

	public ContainerMachineDiFurnaceRTG(InventoryPlayer playerInv, TileEntityDiFurnaceRTG teIn) {
		bFurnace = teIn;
		// Input
		this.addSlotToContainer(new Slot(teIn, 0, 80, 18));
		this.addSlotToContainer(new Slot(teIn, 1, 80, 54));
		// Output
		this.addSlotToContainer(new SlotCraftingOutput(playerInv.player, teIn, 2, 134, 36));
		// RTG pellets
		this.addSlotToContainer(new Slot(teIn, 3, 22, 18));
		this.addSlotToContainer(new Slot(teIn, 4, 40, 18));
		this.addSlotToContainer(new Slot(teIn, 5, 22, 36));
		this.addSlotToContainer(new Slot(teIn, 6, 40, 36));
		this.addSlotToContainer(new Slot(teIn, 7, 22, 54));
		this.addSlotToContainer(new Slot(teIn, 8, 40, 54));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return bFurnace.isUseableByPlayer(player);
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		
		if(index >= 0 && index < 2 && button == 1 && mode == 0) {
			Slot slot = this.getSlot(index);
			if(!slot.getHasStack() && player.inventory.getItemStack() == null) {
				if(!player.worldObj.isRemote) {
					if(index == 0) bFurnace.sideUpper = (byte) ((bFurnace.sideUpper + 1) % 6);
					if(index == 1) bFurnace.sideLower = (byte) ((bFurnace.sideLower + 1) % 6);
					
					bFurnace.markDirty();
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

			if(par2 <= 8) {
				if(!this.mergeItemStack(var5, 9, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(var5.getItem() instanceof ItemRTGPellet) {
				if(!this.mergeItemStack(var5, 3, 9, false))
					return null;
				
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
}
