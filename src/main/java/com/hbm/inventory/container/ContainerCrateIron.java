package com.hbm.inventory.container;

import com.hbm.tileentity.machine.storage.TileEntityCrateIron;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

//@invtweaks.api.container.ChestContainer
public class ContainerCrateIron extends ContainerCrateBase {
	
	public ContainerCrateIron(InventoryPlayer invPlayer, TileEntityCrateIron tedf) {
		super(tedf);
		
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(tedf, j + i * 9, 8 + j * 18, 18 + i * 18));
			}
		}
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 20));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 20));
		}
	}
}
