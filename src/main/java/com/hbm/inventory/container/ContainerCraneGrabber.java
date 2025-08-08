package com.hbm.inventory.container;

import com.hbm.inventory.SlotPattern;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.items.ModItems;
import com.hbm.tileentity.network.TileEntityCraneGrabber;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCraneGrabber extends ContainerBase {
	
	protected TileEntityCraneGrabber grabber;
	
	public ContainerCraneGrabber(InventoryPlayer invPlayer, TileEntityCraneGrabber grabber) {
		super(invPlayer, grabber);

		this.grabber = grabber;
		
		//filter
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				this.addSlotToContainer(new SlotPattern(grabber, j + i * 3, 40 + j * 18, 17 + i * 18));
			}
		}
		
		//upgrades
		this.addSlotToContainer(new SlotUpgrade(grabber, 9, 121, 23));
		this.addSlotToContainer(new SlotUpgrade(grabber, 10, 121, 47));

		playerInv(invPlayer, 8, 103, 161);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(slot);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			
			if(slot < 9) { //filters
				return null;
			}

			if(slot <= grabber.getSizeInventory() - 1) {
				if(!this.mergeItemStack(var5, grabber.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(var3.getItem() == ModItems.upgrade_stack) {
					 if(!this.mergeItemStack(var5, 9, 10, false))
						 return null;
				} else if(var3.getItem() == ModItems.upgrade_ejector) {
					 if(!this.mergeItemStack(var5, 10, 11, false))
						 return null;
				}
				
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
		
		if(index < 0 || index > 8) {
			return super.slotClick(index, button, mode, player);
		}

		Slot slot = this.getSlot(index);
		
		ItemStack ret = null;
		ItemStack held = player.inventory.getItemStack();
		
		if(slot.getHasStack())
			ret = slot.getStack().copy();
		
		if(button == 1 && mode == 0 && slot.getHasStack()) {
			grabber.nextMode(index);
			return ret;
			
		} else {
			slot.putStack(held);
			grabber.matcher.initPatternStandard(grabber.getWorldObj(), slot.getStack(), index);
			
			return ret;
		}
	}
}
