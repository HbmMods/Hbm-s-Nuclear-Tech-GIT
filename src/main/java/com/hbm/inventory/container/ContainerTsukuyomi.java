package com.hbm.inventory.container;

import com.hbm.tileentity.turret.TileEntityTsukuyomi;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerTsukuyomi extends Container
{
	private TileEntityTsukuyomi twr;

	public ContainerTsukuyomi(InventoryPlayer invPlayer, TileEntityTsukuyomi te)
	{
		twr = te;
		addSlotToContainer(new Slot(te, 0, 166, 97));
		addSlotToContainer(new Slot(te, 1, 148, 52));
		addSlotToContainer(new Slot(te, 2, 129, 52));
		
		for (int i = 0; i < 3; i++)
			for(int j = 0; j < 9; j++)
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18 + 7, 84 + i * 18 + 49));
		
		for (int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(invPlayer, i, 15 + i * 18, 191));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return twr.isUseableByPlayer(player);
	}

}
