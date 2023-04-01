package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineAssemfac;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAssemfac extends Container {
	
	private TileEntityMachineAssemfac assemfac;

	public ContainerAssemfac(InventoryPlayer playerInv, TileEntityMachineAssemfac tile) {
		assemfac = tile;

		//Battery
		this.addSlotToContainer(new Slot(tile, 0, 234, 218));
		
		for(int i = 0; i < 4; i++) {
			this.addSlotToContainer(new SlotUpgrade(tile, 1 + i, 5, 172 + i * 18));
		}
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 2; j++) {
				int offX = 7 + j * 118;
				int offY = 14 + i * 38;
				int startIndex = 5 + (i * 2 + j) * 14;
				
				for(int k = 0; k < 2; k++) {
					for(int l = 0; l < 6; l++) {
						this.addSlotToContainer(new Slot(tile, startIndex + k * 6 + l, offX + l * 16, offY + k * 16));
					}
				}
			}
		}
		
		for(int i = 0; i < 8; i++) {
			this.addSlotToContainer(new Slot(tile, 17 + i * 14, 106, 13 + i * 19 - (i % 2 == 1 ? 1 : 0)));
			this.addSlotToContainer(new SlotCraftingOutput(playerInv.player, tile, 18 + i * 14, 234, 13 + i * 16));
		}
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 34 + j * 18, 174 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(playerInv, i, 34 + i * 18, 232));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return assemfac.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return null;
	}
}
