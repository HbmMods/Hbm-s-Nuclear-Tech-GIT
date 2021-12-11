package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.tileentity.machine.TileEntityElectrolysisCell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerElectrolysisCell extends Container {
	
	private TileEntityElectrolysisCell cell;

	public ContainerElectrolysisCell(InventoryPlayer invPlayer, TileEntityElectrolysisCell te) {
		cell = te;
		
		//Battery slot
		this.addSlotToContainer(new Slot(te, 0, 5, 208));
		//Crystal input
		this.addSlotToContainer(new Slot(te, 1, 44, 18));
		//Water Input
		this.addSlotToContainer(new Slot(te, 2, 85, 18));
		this.addSlotToContainer(new SlotMachineOutput(te, 3, 131, 18));
		//Upgrade slots
		this.addSlotToContainer(new SlotUpgrade(te, 5, 160, 9));
		this.addSlotToContainer(new SlotUpgrade(te, 6, 179, 9));
		//Acid Input
		this.addSlotToContainer(new Slot(te, 7, 69, 55));
		this.addSlotToContainer(new SlotMachineOutput(te, 8, 87, 55));
		//Niter input
		this.addSlotToContainer(new Slot(te, 9, 69, 74));
		//Hydrogen Output
		this.addSlotToContainer(new Slot(te, 10, 133, 45));
		this.addSlotToContainer(new SlotMachineOutput(te, 11, 179, 45));
		//Oxygen Output
		this.addSlotToContainer(new Slot(te, 10, 133, 63));
		this.addSlotToContainer(new SlotMachineOutput(te, 11, 179, 63));
		//Primary Casting Slot
		this.addSlotToContainer(new SlotMachineOutput(te, 12, 72, 121));
		//Secondary Casting Slot
		this.addSlotToContainer(new SlotMachineOutput(te, 13, 122, 121));
		//side product slots
		this.addSlotToContainer(new SlotMachineOutput(te, 14, 153, 98));
		this.addSlotToContainer(new SlotMachineOutput(te, 15, 153, 116));
		this.addSlotToContainer(new SlotMachineOutput(te, 16, 171, 98));
		this.addSlotToContainer(new SlotMachineOutput(te, 17, 171, 116));
		
		
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 36 + j * 18, 147 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 36 + i * 18, 205));
		}
		
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		{
			ItemStack var3 = null;
			Slot var4 = (Slot) this.inventorySlots.get(par2);
			
			if (var4 != null && var4.getHasStack())
			{
				ItemStack var5 = var4.getStack();
				var3 = var5.copy();
				
				if (par2 <= 5) {
					if (!this.mergeItemStack(var5, 6, this.inventorySlots.size(), true))
					{
						return null;
					}
				}
				else if (!this.mergeItemStack(var5, 0, 2, false))
				{
					return null;
				}
				
				if (var5.stackSize == 0)
				{
					var4.putStack((ItemStack) null);
				}
				else
				{
					var4.onSlotChanged();
				}
			}
			
			return var3;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return cell.isUseableByPlayer(player);
	}

}