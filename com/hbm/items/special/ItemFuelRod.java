package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;

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
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Used in nuclear reactor");
		
		if(this == ModItems.rod_uranium_fuel)
		{
			list.add("Generates " + heat + " heat per tick");
			list.add("Lasts " + lifeTime + " ticks");
		}
		
		if(this == ModItems.rod_dual_uranium_fuel)
		{
			list.add("Generates " + heat + " heat per tick");
			list.add("Lasts " + lifeTime + " ticks");
		}
		
		if(this == ModItems.rod_quad_uranium_fuel)
		{
			list.add("Generates " + heat + " heat per tick");
			list.add("Lasts " + lifeTime + " ticks");
		}
		
		if(this == ModItems.rod_plutonium_fuel)
		{
			list.add("Generates " + heat + " heat per tick");
			list.add("Lasts " + lifeTime + " ticks");
		}
		
		if(this == ModItems.rod_dual_plutonium_fuel)
		{
			list.add("Generates " + heat + " heat per tick");
			list.add("Lasts " + lifeTime + " ticks");
		}
		
		if(this == ModItems.rod_quad_plutonium_fuel)
		{
			list.add("Generates " + heat + " heat per tick");
			list.add("Lasts " + lifeTime + " ticks");
		}
		
		if(this == ModItems.rod_mox_fuel)
		{
			list.add("Generates " + heat + " heat per tick");
			list.add("Lasts " + lifeTime + " ticks");
		}
		
		if(this == ModItems.rod_dual_mox_fuel)
		{
			list.add("Generates " + heat + " heat per tick");
			list.add("Lasts " + lifeTime + " ticks");
		}
		
		if(this == ModItems.rod_quad_mox_fuel)
		{
			list.add("Generates " + heat + " heat per tick");
			list.add("Lasts " + lifeTime + " ticks");
		}
		
		if(this == ModItems.rod_schrabidium_fuel)
		{
			list.add("Generates " + heat + " heat per tick");
			list.add("Lasts " + lifeTime + " ticks");
		}
		
		if(this == ModItems.rod_dual_schrabidium_fuel)
		{
			list.add("Generates " + heat + " heat per tick");
			list.add("Lasts " + lifeTime + " ticks");
		}
		
		if(this == ModItems.rod_quad_schrabidium_fuel)
		{
			list.add("Generates " + heat + " heat per tick");
			list.add("Lasts " + lifeTime + " ticks");
		}
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
