package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityReactorWarp;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerReactorWarp extends Container
{
	private TileEntityReactorWarp warpCore;
	public ContainerReactorWarp(TileEntityReactorWarp te)
	{
		warpCore = te;
		// Battery
		addSlotToContainer(new Slot(te, 0, 44, 106));
		// Core
		addSlotToContainer(new Slot(te, 1, 80, 88));
		// Boosters
		addSlotToContainer(new Slot(te, 2, 44, 88));
		// Catalysts
		addSlotToContainer(new Slot(te, 3, 116, 88));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return warpCore.isUseableByPlayer(player);
	}

}
