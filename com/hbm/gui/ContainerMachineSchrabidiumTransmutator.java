package com.hbm.gui;

import com.hbm.blocks.TileEntityMachineDeuterium;
import com.hbm.blocks.TileEntityMachineSchrabidiumTransmutator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineSchrabidiumTransmutator extends Container {

private TileEntityMachineSchrabidiumTransmutator nukeBoy;

	private int power;
	private int water;
	private int sulfur;
	private int progress;
	
	public ContainerMachineSchrabidiumTransmutator(InventoryPlayer invPlayer, TileEntityMachineSchrabidiumTransmutator tedf) {
		
		nukeBoy = tedf;

		this.addSlotToContainer(new Slot(tedf, 0, 44, 63));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 1, 134, 63));
		this.addSlotToContainer(new Slot(tedf, 2, 26, 18));
		this.addSlotToContainer(new Slot(tedf, 3, 8, 108));
		
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
			
            if (par2 <= 3) {
				if (!this.mergeItemStack(var5, 4, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 1, false))
			{
				if (!this.mergeItemStack(var5, 3, 4, false))
					if (!this.mergeItemStack(var5, 2, 3, false))
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
			
			if(this.power != this.nukeBoy.process)
			{
				par1.sendProgressBarUpdate(this, 0, this.nukeBoy.process);
			}
			
			if(this.power != this.nukeBoy.power)
			{
				par1.sendProgressBarUpdate(this, 1, this.nukeBoy.power);
			}
		}
		
		this.power = this.nukeBoy.power;
		this.progress = this.nukeBoy.process;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			nukeBoy.process = j;
		}
		if(i == 1)
		{
			nukeBoy.power = j;
		}
	}
}
