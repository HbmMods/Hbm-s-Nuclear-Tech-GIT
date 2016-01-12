package com.hbm.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.hbm.blocks.TileEntityDiFurnace;
import com.hbm.blocks.TileEntityMachineCentrifuge;

public class ContainerCentrifuge extends Container {
	
	private TileEntityMachineCentrifuge diFurnace;
	private int dualCookTime;
	private int dualPower;
	
	public ContainerCentrifuge(InventoryPlayer invPlayer, TileEntityMachineCentrifuge tedf) {
		
		diFurnace = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 26, 17));
		this.addSlotToContainer(new Slot(tedf, 1, 26, 53));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 2, 134, 17));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 3, 152, 17));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 4, 134, 53));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 5, 152, 53));
		
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
	
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
		return null;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return diFurnace.isUseableByPlayer(player);
	}
	
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting par1 = (ICrafting)this.crafters.get(i);
			
			if(this.dualCookTime != this.diFurnace.dualCookTime)
			{
				par1.sendProgressBarUpdate(this, 0, this.diFurnace.dualCookTime);
			}
			
			if(this.dualPower != this.diFurnace.dualPower)
			{
				par1.sendProgressBarUpdate(this, 1, this.diFurnace.dualPower);
			}
		}
		
		this.dualCookTime = this.diFurnace.dualCookTime;
		this.dualPower = this.diFurnace.dualPower;
	}
	
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			diFurnace.dualCookTime = j;
		}
		if(i == 1)
		{
			diFurnace.dualPower = j;
		}
	}

}