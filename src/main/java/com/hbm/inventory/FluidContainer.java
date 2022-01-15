package com.hbm.inventory;

import com.hbm.inventory.fluid.FluidType;

import net.minecraft.item.ItemStack;

public class FluidContainer {
	
	//The full container (e.g. deuterium cell)
	public ItemStack fullContainer;
	//Them empty container (e.g. empty cell)
	public ItemStack emptyContainer;
	//The type of the contained liquid (e.g. deuterium)
	public FluidType type;
	//The amount of liquid stored in mB (e.g. 1000)
	public int content;
	
	public FluidContainer(ItemStack full, ItemStack empty, FluidType type, int amount) {
		fullContainer = full;
		emptyContainer = empty;
		this.type = type;
		content = amount;
	}

}
