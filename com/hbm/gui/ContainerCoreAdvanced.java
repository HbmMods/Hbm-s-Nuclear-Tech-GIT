package com.hbm.gui;

import com.hbm.blocks.TileEntityCoreAdvanced;
import com.hbm.blocks.TileEntityCoreTitanium;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCoreAdvanced extends Container {
	
	private TileEntityCoreAdvanced diFurnace;
	private int progress;
	private int power;
	
	public ContainerCoreAdvanced(InventoryPlayer invPlayer, TileEntityCoreAdvanced tedf) {
		
		diFurnace = tedf;
		
		//Input Storage
		this.addSlotToContainer(new Slot(tedf, 0, 8, 18));
		this.addSlotToContainer(new Slot(tedf, 1, 26, 18));
		this.addSlotToContainer(new Slot(tedf, 2, 44, 18));
		this.addSlotToContainer(new Slot(tedf, 3, 62, 18));
		this.addSlotToContainer(new Slot(tedf, 4, 80, 18));
		this.addSlotToContainer(new Slot(tedf, 5, 98, 18));
		this.addSlotToContainer(new Slot(tedf, 6, 116, 18));
		this.addSlotToContainer(new Slot(tedf, 7, 134, 18));
		this.addSlotToContainer(new Slot(tedf, 8, 152, 18));
		//Inputs
		this.addSlotToContainer(new Slot(tedf, 9, 8, 54));
		this.addSlotToContainer(new Slot(tedf, 10, 8, 72));
		//Outputs
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 11, 134, 54));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 12, 134, 72));
		//Output Storage
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 13, 8, 108));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 14, 26, 108));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 15, 44, 108));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 16, 62, 108));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 17, 80, 108));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 18, 98, 108));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 19, 116, 108));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 20, 134, 108));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 21, 152, 108));
		//Power Cell
		this.addSlotToContainer(new Slot(tedf, 22, 44, 72));
		//More Inputs
		this.addSlotToContainer(new Slot(tedf, 23, 26, 54));
		this.addSlotToContainer(new Slot(tedf, 24, 26, 72));
		//More Outputs
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 25, 152, 54));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 26, 152, 72));
		
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
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.diFurnace.progress);
		crafting.sendProgressBarUpdate(this, 1, this.diFurnace.power);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
		return null;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return diFurnace.isUseableByPlayer(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting par1 = (ICrafting)this.crafters.get(i);
			
			if(this.progress != this.diFurnace.progress)
			{
				par1.sendProgressBarUpdate(this, 0, this.diFurnace.progress);
			}
			
			if(this.power != this.diFurnace.power)
			{
				par1.sendProgressBarUpdate(this, 1, this.diFurnace.power);
			}
		}
		
		this.progress = this.diFurnace.progress;
		this.power = this.diFurnace.power;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			diFurnace.progress = j;
		}
		if(i == 1)
		{
			diFurnace.power = j;
		}
	}
}
