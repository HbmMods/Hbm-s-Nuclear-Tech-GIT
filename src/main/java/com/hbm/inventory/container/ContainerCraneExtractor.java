package com.hbm.inventory.container;

import com.hbm.inventory.SlotPattern;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.items.ModItems;
import com.hbm.tileentity.network.TileEntityCraneExtractor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCraneExtractor extends ContainerBase {
	
	protected TileEntityCraneExtractor extractor;
	
	public ContainerCraneExtractor(InventoryPlayer invPlayer, TileEntityCraneExtractor extractor) {
		super(invPlayer, extractor);
		this.extractor = extractor;
		
		//filter
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				this.addSlotToContainer(new SlotPattern(extractor, j + i * 3, 71 + j * 18, 17 + i * 18));
			}
		}
		
		//buffer
		addSlots(extractor,9,8,17,3,3);

		//upgrades
		this.addSlotToContainer(new SlotUpgrade(extractor, 18, 152, 23));
		this.addSlotToContainer(new SlotUpgrade(extractor, 19, 152, 47));

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

			if(slot <= extractor.getSizeInventory() - 1) {
				if(!this.mergeItemStack(var5, extractor.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(var3.getItem() == ModItems.upgrade_stack) {
					 if(!this.mergeItemStack(var5, 18, 19, false))
						 return null;
				} else if(var3.getItem() == ModItems.upgrade_ejector) {
					 if(!this.mergeItemStack(var5, 19, 20, false))
						 return null;
				} else if(!this.mergeItemStack(var5, 9, extractor.getSizeInventory(), false)) {
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
			extractor.nextMode(index);
			return ret;
			
		} else {
			slot.putStack(held != null ? held.copy() : null);
			
			if(slot.getHasStack()) {
				slot.getStack().stackSize = 1;
			}
			
			slot.onSlotChanged();
			extractor.matcher.initPatternStandard(extractor.getWorldObj(), slot.getStack(), index);
			
			return ret;
		}
	}
}
