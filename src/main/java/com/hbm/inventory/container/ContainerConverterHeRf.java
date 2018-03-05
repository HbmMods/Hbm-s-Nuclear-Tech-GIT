package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityConverterHeRf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;

public class ContainerConverterHeRf extends Container {
	
	private TileEntityConverterHeRf diFurnace;
	
	private int water;
	private int flux;
	
	public ContainerConverterHeRf(InventoryPlayer invPlayer, TileEntityConverterHeRf tedf) {
		
		diFurnace = tedf;
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 1, this.diFurnace.storage.getEnergyStored());
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
		return null;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting par1 = (ICrafting)this.crafters.get(i);
			
			if(this.flux != this.diFurnace.storage.getEnergyStored())
			{
				par1.sendProgressBarUpdate(this, 1, this.diFurnace.storage.getEnergyStored());
			}
		}
		
		this.flux = this.diFurnace.storage.getEnergyStored();
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 1)
		{
			diFurnace.storage.setEnergyStored(j);
		}
	}
}
