package com.hbm.inventory;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;

import net.minecraft.item.ItemStack;

public class FluidContainerRegistry {
	
	//TODO: somehow incorporate hashmaps into this
	static List<FluidContainer> allContainers = new ArrayList<FluidContainer>();
	
	public static void registerContainer(FluidContainer con) {
		allContainers.add(con);
	}
	
	public static int getFluidContent(ItemStack stack, FluidType type) {
		
		if(stack == null)
			return 0;
		
		ItemStack sta = stack.copy();
		sta.stackSize = 1;
		
		for(FluidContainer container : allContainers) {
			if(container.type.name().equals(type.name()) &&
					ItemStack.areItemStacksEqual(container.fullContainer, sta) &&
					ItemStack.areItemStackTagsEqual(container.fullContainer, sta))
				return container.content;
		}
		
		return 0;
	}
	
	public static FluidType getFluidType(ItemStack stack) {
		
		if(stack == null)
			return FluidType.NONE;
		
		ItemStack sta = stack.copy();
		sta.stackSize = 1;
		
		for(FluidContainer container : allContainers) {
			if(ItemStack.areItemStacksEqual(container.fullContainer, sta) && ItemStack.areItemStackTagsEqual(container.fullContainer, sta))
				return container.type;
		}

		return FluidType.NONE;
	}
	
	public static ItemStack getFullContainer(ItemStack stack, FluidType type) {
		if(stack == null)
			return null;
		
		ItemStack sta = stack.copy();
		sta.stackSize = 1;

		for(FluidContainer container : allContainers) {
			if(ItemStack.areItemStacksEqual(container.emptyContainer, sta) &&  ItemStack.areItemStackTagsEqual(container.emptyContainer, sta) && container.type.name().equals(type.name()))
				return container.fullContainer.copy();
		}
		
		return null;
	}
	
	public static ItemStack getEmptyContainer(ItemStack stack) {
		if(stack == null)
			return null;
		
		ItemStack sta = stack.copy();
		sta.stackSize = 1;

		for(FluidContainer container : allContainers) {
			if(ItemStack.areItemStacksEqual(container.fullContainer, sta) && ItemStack.areItemStackTagsEqual(container.fullContainer, sta))
				return container.emptyContainer == null ? null : container.emptyContainer.copy();
		}
		
		return null;
	}

}
