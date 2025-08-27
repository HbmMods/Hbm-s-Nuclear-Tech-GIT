package com.hbm.inventory.container;

import com.hbm.inventory.SlotPattern;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntityMachineAutocrafter;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAutocrafter extends ContainerBase {

	private TileEntityMachineAutocrafter autocrafter;

	public ContainerAutocrafter(InventoryPlayer invPlayer, TileEntityMachineAutocrafter tedf) {
		super(invPlayer, tedf);
		autocrafter = tedf;

		/* TEMPLATE */
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				this.addSlotToContainer(new SlotPattern(tedf, j + i * 3, 44 + j * 18, 22 + i * 18));
			}
		}
		this.addSlotToContainer(new SlotPattern(tedf, 9, 116, 40, true));

		/* RECIPE */
		addSlots(tedf,10, 44, 86, 3, 3);

		this.addSlotToContainer(new Slot(tedf, 19, 116, 104));
		
		//Battery
		this.addSlotToContainer(new Slot(tedf, 20, 17, 99));

		playerInv(invPlayer,8,158,216);
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		
		//L/R: 0
		//M3: 3
		//SHIFT: 1
		//DRAG: 5

		if(index < 0 || index > 9) {
			return super.slotClick(index, button, mode, player);
		}

		Slot slot = this.getSlot(index);
		
		ItemStack ret = null;
		ItemStack held = player.inventory.getItemStack();
		
		if(slot.getHasStack())
			ret = slot.getStack().copy();
		
		//Don't allow any interaction for the template's output
		if(index == 9) {
			
			if(button == 1 && mode == 0 && slot.getHasStack()) {
				autocrafter.nextTemplate();
				this.detectAndSendChanges();
			}
			
			return ret;
		}
		
		if(button == 1 && mode == 0 && slot.getHasStack()) {
			autocrafter.nextMode(index);
			return ret;
			
		} else {
	
			slot.putStack(held);
			autocrafter.matcher.initPatternSmart(autocrafter.getWorldObj(), slot.getStack(), index);
			autocrafter.updateTemplateGrid();
			
			return ret;
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack rStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			rStack = stack.copy();

			if(index <= 20 && index >= 10) {
				if(!this.mergeItemStack(stack, 21, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(index > 20){
				
				if(rStack.getItem() instanceof IBatteryItem || rStack.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(stack, 20, 21, false)) return null;
				} else {
					return null;
				}
			}

			if(stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return rStack;
	}
}
