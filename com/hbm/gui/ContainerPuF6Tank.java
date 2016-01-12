package com.hbm.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.hbm.blocks.TileEntityMachinePuF6Tank;
import com.hbm.blocks.TileEntityMachineUF6Tank;
import com.hbm.blocks.TileEntityTestNuke;

public class ContainerPuF6Tank extends Container {

	private TileEntityMachinePuF6Tank testNuke;
	private int fillState;
	
	public ContainerPuF6Tank(InventoryPlayer invPlayer, TileEntityMachinePuF6Tank tedf) {
		fillState = 0;
		
		testNuke = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 44, 17));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 1, 44, 53));
		this.addSlotToContainer(new Slot(tedf, 2, 116, 17));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 3, 116, 53));
		
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
	
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.testNuke.fillState);
	}
	
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
		return null;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return testNuke.isUseableByPlayer(player);
	}
	
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting par1 = (ICrafting)this.crafters.get(i);
			
			if(this.fillState != this.testNuke.fillState)
			{
				par1.sendProgressBarUpdate(this, 0, this.testNuke.fillState);
			}
		}
		
		this.fillState = this.testNuke.fillState;
	}
	
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			testNuke.fillState = j;
		}
	}

}