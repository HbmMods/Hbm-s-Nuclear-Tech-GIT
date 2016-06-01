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
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
		return null;
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
