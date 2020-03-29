package com.hbm.items.special;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemFuelRod extends ItemRadioactive {

	public int lifeTime;
	public int heat;
	
	public ItemFuelRod(int life, int heat) {
		this.lifeTime = life;
		this.heat = heat;
		this.setMaxDamage(100);
		this.canRepair = false;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Used in nuclear reactor");
		
		list.add("Generates " + heat + " heat per tick");
		list.add("Lasts " + lifeTime + " ticks");
		
		super.addInformation(itemstack, player, list, bool);
	}
	
	public static void setLifeTime(ItemStack stack, int time) {
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setInteger("life", time);
	}
	
	public static void updateDamage(ItemStack stack) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.setItemDamage((int)((double)getLifeTime(stack) / (double)((ItemFuelRod)stack.getItem()).lifeTime * 100D));
	}
	
	public static int getLifeTime(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		
		return stack.stackTagCompound.getInteger("life");
	}

}
