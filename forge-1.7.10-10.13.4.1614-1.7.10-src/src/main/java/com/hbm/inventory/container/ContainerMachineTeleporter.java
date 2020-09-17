package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityMachineTeleporter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;

public class ContainerMachineTeleporter extends Container {
	
	private TileEntityMachineTeleporter diFurnace;
	
	private int x;
	private int y;
	private int z;
	
	public ContainerMachineTeleporter(InventoryPlayer invPlayer, TileEntityMachineTeleporter tedf) {
		
		diFurnace = tedf;
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 1, this.diFurnace.targetX);
		crafting.sendProgressBarUpdate(this, 2, this.diFurnace.targetY);
		crafting.sendProgressBarUpdate(this, 3, this.diFurnace.targetZ);
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

			if(this.x != this.diFurnace.targetX)
			{
				par1.sendProgressBarUpdate(this, 1, this.diFurnace.targetX);
			}
			if(this.y != this.diFurnace.targetY)
			{
				par1.sendProgressBarUpdate(this, 2, this.diFurnace.targetY);
			}
			if(this.z != this.diFurnace.targetZ)
			{
				par1.sendProgressBarUpdate(this, 3, this.diFurnace.targetZ);
			}
		}

		this.x = this.diFurnace.targetX;
		this.y = this.diFurnace.targetY;
		this.z = this.diFurnace.targetZ;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 1)
		{
			diFurnace.targetX = j;
		}
		if(i == 2)
		{
			diFurnace.targetY = j;
		}
		if(i == 3)
		{
			diFurnace.targetZ = j;
		}
	}
}
