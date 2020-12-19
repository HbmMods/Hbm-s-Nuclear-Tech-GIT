package com.hbm.items.special;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemHot extends Item {

	public static int heat;
	
	public ItemHot(int heat) {
		
		this.heat = heat;
	}
	
	public static ItemStack heatUp(ItemStack stack) {
		
		if(!(stack.getItem() instanceof ItemHot))
			return stack;
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setInteger("heat", heat);
		return stack;
	}
	
	public static double getHeat(ItemStack stack) {
		
		if(!(stack.getItem() instanceof ItemHot))
			return 0;
		
		if(!stack.hasTagCompound())
			return 0;
		
		int h = stack.stackTagCompound.getInteger("heat");
		
		return (double)h / (double)heat;
	}

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
    	
    	if(pass == 0)
    		return 0xffffff;
    	
    	double heat = getHeat(stack);
    	
    	int r = 0xff;
    	int g = (int)(0xff - heat * 0x40);
    	int b = (int)(0xff - heat * 0xff);
    	
    	return (r << 16) | (g << 8) | b;
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return false;
    }
}
