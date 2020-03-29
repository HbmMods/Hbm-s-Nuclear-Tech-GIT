package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityAMSEmitter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAMSEmitter extends Container {

private TileEntityAMSEmitter amsEmitter;

	private int heat;
	private int warning;
	
	public ContainerAMSEmitter(InventoryPlayer invPlayer, TileEntityAMSEmitter tedf) {
		amsEmitter = tedf;

		//Fluid In
		this.addSlotToContainer(new Slot(tedf, 0, 44, 17));
		//Fluid Out
		this.addSlotToContainer(new Slot(tedf, 1, 44, 53));
		//Focus
		this.addSlotToContainer(new Slot(tedf, 2, 80, 53));
		//Battery
		this.addSlotToContainer(new Slot(tedf, 3, 116, 53));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
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
			else
				return null;
			
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
		return amsEmitter.isUseableByPlayer(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting par1 = (ICrafting)this.crafters.get(i);
			
			if(this.heat != this.amsEmitter.heat)
			{
				par1.sendProgressBarUpdate(this, 0, this.amsEmitter.heat);
			}
			
			if(this.warning != this.amsEmitter.warning)
			{
				par1.sendProgressBarUpdate(this, 2, this.amsEmitter.warning);
			}
		}

		this.heat = this.amsEmitter.heat;
		this.warning = this.amsEmitter.warning;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			amsEmitter.heat = j;
		}
		if(i == 2)
		{
			amsEmitter.warning = j;
		}
	}
}
