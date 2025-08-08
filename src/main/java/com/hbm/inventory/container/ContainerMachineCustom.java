package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotPattern;
import com.hbm.tileentity.machine.TileEntityCustomMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineCustom extends Container {
	
	private TileEntityCustomMachine custom;

	public ContainerMachineCustom(InventoryPlayer playerInv, TileEntityCustomMachine tile) {
		custom = tile;
		
		//Input
		this.addSlotToContainer(new Slot(tile, 0, 150, 72));
		//Fluid IDs
		for(int i = 0; i < tile.inputTanks.length; i++) {
			this.addSlotToContainer(new Slot(tile, 1 + i, 8 + 18 * i, 54));
		}
		//Item inputs
		if(tile.config.itemInCount > 0) this.addSlotToContainer(new Slot(tile, 4, 8, 72));
		if(tile.config.itemInCount > 1) this.addSlotToContainer(new Slot(tile, 5, 26, 72));
		if(tile.config.itemInCount > 2) this.addSlotToContainer(new Slot(tile, 6, 44, 72));
		if(tile.config.itemInCount > 3) this.addSlotToContainer(new Slot(tile, 7, 8, 90));
		if(tile.config.itemInCount > 4) this.addSlotToContainer(new Slot(tile, 8, 26, 90));
		if(tile.config.itemInCount > 5) this.addSlotToContainer(new Slot(tile, 9, 44, 90));
		//Templates
		if(tile.config.itemInCount > 0) this.addSlotToContainer(new SlotPattern(tile, 10, 8, 108));
		if(tile.config.itemInCount > 1) this.addSlotToContainer(new SlotPattern(tile, 11, 26, 108));
		if(tile.config.itemInCount > 2) this.addSlotToContainer(new SlotPattern(tile, 12, 44, 108));
		if(tile.config.itemInCount > 3) this.addSlotToContainer(new SlotPattern(tile, 13, 8, 126));
		if(tile.config.itemInCount > 4) this.addSlotToContainer(new SlotPattern(tile, 14, 26, 126));
		if(tile.config.itemInCount > 5) this.addSlotToContainer(new SlotPattern(tile, 15, 44, 126));
		//Output
		if(tile.config.itemOutCount > 0) this.addSlotToContainer(new SlotCraftingOutput(playerInv.player, tile, 16, 78, 72));
		if(tile.config.itemOutCount > 1) this.addSlotToContainer(new SlotCraftingOutput(playerInv.player, tile, 17, 96, 72));
		if(tile.config.itemOutCount > 2) this.addSlotToContainer(new SlotCraftingOutput(playerInv.player, tile, 18, 114, 72));
		if(tile.config.itemOutCount > 3) this.addSlotToContainer(new SlotCraftingOutput(playerInv.player, tile, 19, 78, 90));
		if(tile.config.itemOutCount > 4) this.addSlotToContainer(new SlotCraftingOutput(playerInv.player, tile, 20, 96, 90));
		if(tile.config.itemOutCount > 5) this.addSlotToContainer(new SlotCraftingOutput(playerInv.player, tile, 21, 114, 90));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 174 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 232));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return custom.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return null;
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		
		//L/R: 0
		//M3: 3
		//SHIFT: 1
		//DRAG: 5

		//TODO: shoot whoever at mojang wrote the container code
		if(index < 0 || index >= this.inventorySlots.size() || !(this.inventorySlots.get(index) instanceof SlotPattern)) {
			return super.slotClick(index, button, mode, player);
		}
		
		Slot slot = this.getSlot(index);
		index = ((Slot) this.inventorySlots.get(index)).getSlotIndex();
		
		ItemStack ret = null;
		ItemStack held = player.inventory.getItemStack();
		
		if(slot.getHasStack())
			ret = slot.getStack().copy();
		
		if(button == 1 && mode == 0 && slot.getHasStack()) {
			custom.matcher.nextMode(player.worldObj, slot.getStack(), index - 10);
			return ret;
			
		} else {
	
			slot.putStack(held);
			custom.matcher.initPatternSmart(player.worldObj, slot.getStack(), index - 10);
			
			return ret;
		}
	}
}
