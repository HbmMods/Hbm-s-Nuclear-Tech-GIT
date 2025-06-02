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
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack result = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		// Refill instantly if needed, then do regular slot behavior
		if(index == 2 && slot != null && !slot.getHasStack()) {
			slot.putStack(storage.quickExtract());
		}

		if(slot != null && slot.getHasStack()) {
			ItemStack initial = slot.getStack();
			result = initial.copy();

			if(index == 0 || index == 2) {
				if(!this.mergeItemStack(initial, storage.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				// Try to insert instantly, then fall back to regular slot behavior
				if(!storage.quickInsert(initial) && !this.mergeItemStack(initial, 0, 1, false)) {
					return null;
				}
			}

			if(initial.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			slot.onPickupFromSlot(player, initial);
		}

		return result;
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

		slot.putStack(held);
		
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
