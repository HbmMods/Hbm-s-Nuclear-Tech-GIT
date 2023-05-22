package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineAssembler extends Container {

private TileEntityMachineAssembler assembler;
	
	public ContainerMachineAssembler(InventoryPlayer invPlayer, TileEntityMachineAssembler te) {
		assembler = te;

		//Battery
				this.addSlotToContainer(new Slot(te, 0, 152, 18));
		//Upgrades
		this.addSlotToContainer(new Slot(te, 1, 224, 18));
		this.addSlotToContainer(new Slot(te, 2, 224, 36));
		this.addSlotToContainer(new Slot(te, 3, 224, 54));
		//Schematic
		this.addSlotToContainer(new Slot(te, 4, 152, 54));
		//Output was 134 90
		this.addSlotToContainer(new SlotMachineOutput(te, 5, 206, 90));
		//Input Added 24 more slots - LordWeeder
		int index = 6;
		for(int i = 0; i < 6; i++)
			for(int j = 0; j < 6; j++) {
				this.addSlotToContainer(new Slot(te, index, 8 + i*18, 18 + j*18));
				index++;
			}
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 48 + j * 18, 84 + i * 18 + 56));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 48 + i * 18, 142 + 56));
		}
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2)
    {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);
		
		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			SlotCraftingOutput.checkAchievements(p_82846_1_, var5);
			
			//Since I added 24 input slots - LordWeeder
            if (par2 <= (17 + 24)) {
				if (!this.mergeItemStack(var5, 18 + 24, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var5, 6, 18 + 24, false))
				if (!this.mergeItemStack(var5, 0, 4, false))
					return null;
			
			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}

			if(var5.stackSize == var3.stackSize) {
				return null;
			}

			var4.onPickupFromSlot(p_82846_1_, var3);
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return assembler.isUseableByPlayer(player);
	}
}
