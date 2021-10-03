package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityReactorAmat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
@Deprecated
public class ContainerReactorAmat extends Container
{
	private TileEntityReactorAmat tileEntity;
	
	public ContainerReactorAmat(InventoryPlayer invPlayer, TileEntityReactorAmat te)
	{
		tileEntity = te;
		// Battery slot
		addSlotToContainer(new Slot(te, 0, 44, 106));
		// Core slot
		addSlotToContainer(new Slot(te, 1, 80, 88));
		// Deuterium in
		addSlotToContainer(new Slot(te, 2, 8, 88));
		// Deuterium out
		addSlotToContainer(new SlotMachineOutput(te, 3, 8, 106));
		// Antimatter in
		addSlotToContainer(new Slot(te, 4, 151, 88));
		// Antimatter out
		addSlotToContainer(new SlotMachineOutput(te, 5, 151, 106));
		// Boosters
		addSlotToContainer(new Slot(te, 6, 44, 88));
		// Catalysts
		addSlotToContainer(new Slot(te, 7, 116, 88));
		
		for (int i = 0; i < 3; i++)
			for(int j = 0; j < 9; j++)
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 60));
		
		for (int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 60));

	}
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return tileEntity.isUseableByPlayer(player);
	}

}
