package com.hbm.items.machine;

import java.util.List;

import com.hbm.lib.Library;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class ItemFuelRod extends Item {

	public int lifeTime;
	
	public ItemFuelRod(int life) {
		this.lifeTime = life;
		this.canRepair = false;
	}
	
	public static void setLifeTime(ItemStack stack, int time) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setInteger("life", time);
	}
	
	public static int getLifeTime(ItemStack stack) {
		
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		
		return stack.stackTagCompound.getInteger("life");
	}
    
    public boolean showDurabilityBar(ItemStack stack) {
        return getDurabilityForDisplay(stack) > 0D;
    }
    
    public double getDurabilityForDisplay(ItemStack stack) {
        return (double)getLifeTime(stack) / (double)((ItemFuelRod)stack.getItem()).lifeTime;
    }
}
