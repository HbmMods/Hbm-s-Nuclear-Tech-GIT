package com.hbm.items.special;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class ItemFusionShield extends Item {
	
	public long maxDamage;
	public int maxTemp;
	
	public ItemFusionShield(long maxDamage, int maxTemp) {
		this.maxDamage = maxDamage;
		this.maxTemp = maxTemp;
	}
	
	public static long getShieldDamage(ItemStack stack) {
		
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		
		return stack.stackTagCompound.getLong("damage");
	}

	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		long damage = getShieldDamage(stack);
		int percent = (int) ((maxDamage - damage) * 100 / maxDamage);

		list.add("Durability: " + (maxDamage - damage) + "/" + maxDamage + " (" + percent + "%)");
		
		list.add("Melting point: " + EnumChatFormatting.RED + "" + maxTemp + "°C");
	}
	
	public static void setShieldDamage(ItemStack stack, long damage) {
		
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		stack.stackTagCompound.setLong("damage", damage);
	}
	
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return (double)getShieldDamage(stack) / (double)maxDamage;
    }
    
    public boolean showDurabilityBar(ItemStack stack)
    {
        return getDurabilityForDisplay(stack) != 0;
    }

}
