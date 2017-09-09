package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityMachineMiningDrill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineMiningDrill extends Container {

private TileEntityMachineMiningDrill nukeBoy;

	private int power;
	private int warning;
	
	public ContainerMachineMiningDrill(InventoryPlayer invPlayer, TileEntityMachineMiningDrill tedf) {
		
		nukeBoy = tedf;

		//Battery
		this.addSlotToContainer(new Slot(tedf, 0, 44, 53));
		//Outputs
		this.addSlotToContainer(new Slot(tedf, 1, 80, 17));
		this.addSlotToContainer(new Slot(tedf, 2, 98, 17));
		this.addSlotToContainer(new Slot(tedf, 3, 116, 17));
		this.addSlotToContainer(new Slot(tedf, 4, 80, 35));
		this.addSlotToContainer(new Slot(tedf, 5, 98, 35));
		this.addSlotToContainer(new Slot(tedf, 6, 116, 35));
		this.addSlotToContainer(new Slot(tedf, 7, 80, 53));
		this.addSlotToContainer(new Slot(tedf, 8, 98, 53));
		this.addSlotToContainer(new Slot(tedf, 9, 116, 53));
		//Upgrades
		this.addSlotToContainer(new Slot(tedf, 10, 152, 17));
		this.addSlotToContainer(new Slot(tedf, 11, 152, 35));
		this.addSlotToContainer(new Slot(tedf, 12, 152, 53));
		
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
			
            if (par2 <= 12) {
				if (!this.mergeItemStack(var5, 13, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 13, false))
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
			
			if(this.power != this.nukeBoy.power)
			{
				par1.sendProgressBarUpdate(this, 0, this.nukeBoy.power);
			}
			if(this.warning != this.nukeBoy.warning)
			{
				par1.sendProgressBarUpdate(this, 1, this.nukeBoy.warning);
			}
		}

		this.power = this.nukeBoy.power;
		this.warning = this.nukeBoy.warning;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			nukeBoy.power = j;
		}
		if(i == 1)
		{
			nukeBoy.warning = j;
		}
	}
}
