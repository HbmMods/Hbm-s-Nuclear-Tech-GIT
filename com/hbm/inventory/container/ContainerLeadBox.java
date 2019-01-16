package com.hbm.inventory.container;

import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemLeadBox;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerLeadBox extends Container {

	public ContainerLeadBox(InventoryPlayer invPlayer) {
		
		/*IInventory inv = 

		this.addSlotToContainer(new Slot(inv, 0, 71, 18));
		this.addSlotToContainer(new Slot(inv, 1, 71, 18));
		this.addSlotToContainer(new Slot(inv, 2, 71, 18));
		this.addSlotToContainer(new Slot(inv, 3, 71, 18));
		this.addSlotToContainer(new Slot(inv, 4, 71, 18));
		this.addSlotToContainer(new Slot(inv, 5, 71, 18));
		this.addSlotToContainer(new Slot(inv, 6, 71, 18));
		this.addSlotToContainer(new Slot(inv, 7, 71, 18));
		this.addSlotToContainer(new Slot(inv, 8, 71, 18));
		this.addSlotToContainer(new Slot(inv, 9, 71, 18));
		this.addSlotToContainer(new Slot(inv, 10, 71, 18));
		this.addSlotToContainer(new Slot(inv, 11, 71, 18));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 20));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 20));
		}*/
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

}
