package com.hbm.items.special;

import java.util.List;

import com.hbm.handler.ArmorModHandler;
import com.hbm.items.armor.ItemArmorMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemCladding extends ItemArmorMod {
	
	public float rad;
	
	public ItemCladding(float rad) {
		super(ArmorModHandler.cladding, true, true, true, true);
		this.rad = rad;
	}
    
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.YELLOW + "Adds " + rad + " rad-resistance to armor pieces.");
	}
}
