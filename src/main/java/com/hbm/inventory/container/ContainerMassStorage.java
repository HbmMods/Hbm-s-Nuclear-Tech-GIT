package com.hbm.inventory.container;

import com.hbm.inventory.SlotTakeOnly;
import com.hbm.inventory.SlotPattern;
import com.hbm.tileentity.machine.storage.TileEntityMassStorage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMassStorage extends ContainerBase {
	
	private TileEntityMassStorage storage;

	public ContainerMassStorage(InventoryPlayer invPlayer, TileEntityMassStorage te) {
		super(invPlayer,te);

		this.storage = te;
		this.storage.openInventory();

		this.addSlotToContainer(new Slot(storage, 0, 61, 17));
		this.addSlotToContainer(new SlotPattern(storage, 1, 61, 53));
		this.addSlotToContainer(new SlotTakeOnly(storage, 2, 61, 89));

		playerInv(invPlayer,8,139,197);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 == 0 || par2 == 2) {
				if(!this.mergeItemStack(var5, storage.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!this.mergeItemStack(var5, 0, 1, false)) {
				return null;
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}

			var4.onPickupFromSlot(player, var5);
		}

		return var3;
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		
		//L/R: 0
		//M3: 3
		//SHIFT: 1
		//DRAG: 5

		if(index != 1) {
			return super.slotClick(index, button, mode, player);
		}

		Slot slot = this.getSlot(index);
		
		ItemStack ret = null;
		ItemStack held = player.inventory.getItemStack();
		
		if(slot.getHasStack())
			ret = slot.getStack().copy();
		
		//Don't allow for a type change when the thing isn't empty
		if(storage.getStockpile() > 0)
			return ret;

		slot.putStack(held != null ? held.copy() : null);
		
		if(slot.getHasStack()) {
			slot.getStack().stackSize = 1;
		}
		
		slot.onSlotChanged();
		
		return ret;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return storage.isUseableByPlayer(player);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer p_75134_1_) {
		super.onContainerClosed(p_75134_1_);
		this.storage.closeInventory();
	}
}
