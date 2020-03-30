package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityCoreStabilizer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerCoreStabilizer extends Container {

	private TileEntityCoreStabilizer nukeBoy;
	
	public ContainerCoreStabilizer(InventoryPlayer invPlayer, TileEntityCoreStabilizer tedf) {
		
		nukeBoy = tedf;
		
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
	public boolean canInteractWith(EntityPlayer player) {
		return nukeBoy.isUseableByPlayer(player);
	}
}
