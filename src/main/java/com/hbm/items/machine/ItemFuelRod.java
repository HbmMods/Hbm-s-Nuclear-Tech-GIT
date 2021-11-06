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
	public int heat;
	
	public ItemFuelRod(int life, int heat) {
		super();
		this.lifeTime = life;
		this.heat = heat;
		this.canRepair = false;
		
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add(EnumChatFormatting.YELLOW + "[Reactor Fuel Rod]");
		
		list.add(EnumChatFormatting.DARK_AQUA + "  Generates " + heat + " heat per tick");
		list.add(EnumChatFormatting.DARK_AQUA + "  Lasts " + Library.getShortNumber(lifeTime) + " ticks");
		
		super.addInformation(itemstack, player, list, bool);
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
        return true;
    }
    
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return (double)getLifeTime(stack) / (double)((ItemFuelRod)stack.getItem()).lifeTime;
    }

}
