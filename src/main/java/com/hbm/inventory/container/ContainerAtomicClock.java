package com.hbm.inventory.container;

import com.hbm.inventory.SlotUpgrade;
import com.hbm.tileentity.machine.TileEntityAtomicClock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerAtomicClock extends Container
{
	private TileEntityAtomicClock clock;
	public ContainerAtomicClock(InventoryPlayer invPlayer, TileEntityAtomicClock te)
	{
		clock = te;
		
		addSlotToContainer(new Slot(te, 0, 8, 73));
		for (int i = 1; i < 5; i++)
			addSlotToContainer(new SlotUpgrade(te, i, 155 + (i * 18), 20));
		
		for (int i = 0; i < 3; i++)
			for(int j = 0; j < 9; j++)
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18 + 40, 84 + i * 18 + 21));
		
		for (int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(invPlayer, i, 48 + i * 18, 163));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return clock.isUseableByPlayer(player);
	}

}
