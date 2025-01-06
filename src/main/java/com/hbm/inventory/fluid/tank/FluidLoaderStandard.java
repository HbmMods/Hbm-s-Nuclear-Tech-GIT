package com.hbm.inventory.fluid.tank;

import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.fluid.FluidType;

import net.minecraft.item.ItemStack;

public class FluidLoaderStandard extends FluidLoadingHandler {

	@Override
	public boolean fillItem(ItemStack[] slots, int in, int out, FluidTank tank) {
		
		if(tank.pressure != 0) return false;
		
		if(slots[in] == null)
			return true;
		
		FluidType type = tank.getTankType();
		ItemStack full = FluidContainerRegistry.getFullContainer(slots[in], type);
		
		if(full != null && slots[in] != null && tank.getFill() - FluidContainerRegistry.getFluidContent(full, type) >= 0) {
			
			if(slots[out] == null) {
				
				tank.setFill(tank.getFill() - FluidContainerRegistry.getFluidContent(full, type));
				slots[out] = full.copy();
				slots[in].stackSize--;
				if(slots[in].stackSize <= 0) {
					slots[in] = null;
				}
				
			} else if(slots[out] != null && slots[out].getItem() == full.getItem() && slots[out].getItemDamage() == full.getItemDamage() && slots[out].stackSize < slots[out].getMaxStackSize()) {
				
				tank.setFill(tank.getFill() - FluidContainerRegistry.getFluidContent(full, type));
				slots[in].stackSize--;
				
				if(slots[in].stackSize <= 0) {
					slots[in] = null;
				}
				slots[out].stackSize++;
			}
		}
		
		return false;
	}

	@Override
	public boolean emptyItem(ItemStack[] slots, int in, int out, FluidTank tank) {
		
		if(slots[in] == null)
			return true;
		
		FluidType type = tank.getTankType();
		int amount = FluidContainerRegistry.getFluidContent(slots[in], type);
		
		if(amount > 0 && tank.getFill() + amount <= tank.maxFluid) {
			
			ItemStack emptyContainer = FluidContainerRegistry.getEmptyContainer(slots[in]);
			
			if(slots[out] == null) {
				
				tank.setFill(tank.getFill() + amount);
				slots[out] = emptyContainer;
				
				slots[in].stackSize--;
				if(slots[in].stackSize <= 0) {
					slots[in] = null;
				}
				
			} else if(slots[out] != null && (emptyContainer == null || (slots[out].getItem() == emptyContainer.getItem() && slots[out].getItemDamage() == emptyContainer.getItemDamage() && slots[out].stackSize < slots[out].getMaxStackSize()))) {
				
				tank.setFill(tank.getFill() + amount);
				slots[in].stackSize--;
				
				if(slots[in].stackSize <= 0) {
					slots[in] = null;
				}
				
				if(emptyContainer != null) {
					slots[out].stackSize++;
				}
			}
			
			return true;
		}
		
		return false;
	}

}
