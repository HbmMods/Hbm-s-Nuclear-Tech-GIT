package com.hbm.inventory.container;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.machine.ItemFluidIdentifier;
import com.hbm.tileentity.machine.TileEntityMachineTurbineGas;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineTurbineGas extends Container {
	
	private TileEntityMachineTurbineGas turbinegas;
	
	public ContainerMachineTurbineGas(InventoryPlayer invPlayer, TileEntityMachineTurbineGas te) {
		
		turbinegas = te;
		
		//Battery
		this.addSlotToContainer(new Slot(te, 0, 8, 109));
		//Fluid ID
		this.addSlotToContainer(new Slot(te, 1, 36, 17));
		
		for(int i = 0; i < 3; i++) { 
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 141 + i * 18)); //player's inventory
			}
		}

		for(int i = 0; i < 9; i++) { 
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 199)); //shit in the hotbar
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) { //shit for shift clicking that works and idk how
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= 1) { //checks if the item is in the battery or fluidID slot
				if(!this.mergeItemStack(var5, 2, this.inventorySlots.size(), true)) {
					return null;
				}
				
			} else if(var5.getItem() instanceof IBatteryItem) { //only yeets batteries in the battery slot

				if(!this.mergeItemStack(var5, 0, 1, true))
					return null;
				
			} else if(var5.getItem() instanceof ItemFluidIdentifier) { 
				
				FluidType type = ItemFluidIdentifier.getType(var5);
				if (type != Fluids.GAS && type != Fluids.PETROLEUM && type != Fluids.LPG ) //doesn't let you yeet random identifiers in the identifier slot
					return null;

				if(!this.mergeItemStack(var5, 1, 2, true))
					return null;
				
			} else {
				return null;
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return turbinegas.isUseableByPlayer(player);
	}
}