package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineDeuterium;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineDeuterium extends Container {

private TileEntityMachineDeuterium nukeBoy;

	private int water;
	private int sulfur;
	private int progress;
	
	public ContainerMachineDeuterium(InventoryPlayer invPlayer, TileEntityMachineDeuterium tedf) {
		
		nukeBoy = tedf;

		this.addSlotToContainer(new Slot(tedf, 0, 8, 90));
		this.addSlotToContainer(new Slot(tedf, 1, 26, 90));
		this.addSlotToContainer(new Slot(tedf, 2, 44, 90));
		this.addSlotToContainer(new Slot(tedf, 3, 80, 54));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 4, 140, 54));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 56));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 56));
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
			
            if (par2 <= 4) {
				if (!this.mergeItemStack(var5, 5, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var5, 3, 4, false))
			{
				if (!this.mergeItemStack(var5, 0, 3, false))
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

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return nukeBoy.isUseableByPlayer(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting par1 = (ICrafting)this.crafters.get(i);
			
			if(this.progress != this.nukeBoy.process)
			{
				par1.sendProgressBarUpdate(this, 0, this.nukeBoy.process);
			}
			
			if(this.water != this.nukeBoy.water)
			{
				par1.sendProgressBarUpdate(this, 1, this.nukeBoy.water);
			}
			
			if(this.sulfur != this.nukeBoy.sulfur)
			{
				par1.sendProgressBarUpdate(this, 2, this.nukeBoy.sulfur);
			}
		}
		
		this.progress = this.nukeBoy.process;
		this.water = this.nukeBoy.water;
		this.sulfur = this.nukeBoy.sulfur;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			nukeBoy.process = j;
		}
		if(i == 1)
		{
			nukeBoy.water = j;
		}
		if(i == 2)
		{
			nukeBoy.sulfur = j;
		}
		if(i == 3)
		{
			nukeBoy.power = j;
		}
	}
}
