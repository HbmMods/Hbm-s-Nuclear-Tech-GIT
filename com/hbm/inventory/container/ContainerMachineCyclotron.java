package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineCyclotron;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineCyclotron extends Container {

	private TileEntityMachineCyclotron testNuke;
	private int progress;
	private int power;
	
	public ContainerMachineCyclotron(InventoryPlayer invPlayer, TileEntityMachineCyclotron tedf) {
		progress = 0;
		power = 0;
		
		testNuke = tedf;
		
		//Input
		this.addSlotToContainer(new Slot(tedf, 0, 8, 18));
		this.addSlotToContainer(new Slot(tedf, 1, 8, 36));
		this.addSlotToContainer(new Slot(tedf, 2, 8, 54));
		//Targets
		this.addSlotToContainer(new Slot(tedf, 3, 80, 72));
		this.addSlotToContainer(new Slot(tedf, 4, 98, 72));
		this.addSlotToContainer(new Slot(tedf, 5, 116, 72));
		//Tech
		this.addSlotToContainer(new Slot(tedf, 6, 8, 81));
		this.addSlotToContainer(new Slot(tedf, 7, 26, 81));
		this.addSlotToContainer(new Slot(tedf, 8, 44, 81));
		//Battery
		this.addSlotToContainer(new Slot(tedf, 9, 152, 108));
		//Cell
		this.addSlotToContainer(new Slot(tedf, 10, 8, 108));
		//Output
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 11, 44, 108));
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 12, 62, 108));
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 13, 80, 108));
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 14, 98, 108));
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 15, 116, 108));
		
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
		crafting.sendProgressBarUpdate(this, 0, this.testNuke.progress);
		crafting.sendProgressBarUpdate(this, 1, this.testNuke.power);
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
			
            if (par2 <= 15) {
				if (!this.mergeItemStack(var5, 16, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 16, false))
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
		return testNuke.isUseableByPlayer(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting par1 = (ICrafting)this.crafters.get(i);

			if(this.progress != this.testNuke.progress)
			{
				par1.sendProgressBarUpdate(this, 0, this.testNuke.progress);
			}
			if(this.power != this.testNuke.power)
			{
				par1.sendProgressBarUpdate(this, 1, this.testNuke.power);
			}
		}

		this.progress = this.testNuke.progress;
		this.power = this.testNuke.power;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			testNuke.progress = j;
		}
		if(i == 1)
		{
			testNuke.power = j;
		}
	}
}
