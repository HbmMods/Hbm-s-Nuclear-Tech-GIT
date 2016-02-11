package com.hbm.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IFactory {
	
	boolean isStructureValid(World world);
	
	public int getPowerScaled(int i);
	
	public int getProgressScaled(int i);
	
	public boolean isProcessable(ItemStack item);
}
