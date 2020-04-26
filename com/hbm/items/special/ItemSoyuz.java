package com.hbm.items.special;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemSoyuz extends Item {
	
	public ItemSoyuz() {
        this.setHasSubtypes(true);
	}
    
    @Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
    	list.add(new ItemStack(item, 1, 0));
    	list.add(new ItemStack(item, 1, 1));
    	list.add(new ItemStack(item, 1, 2));
    }
    
    @Override
	public EnumRarity getRarity(ItemStack stack) {
    	
    	if(stack.getItemDamage() == 0)
    		return EnumRarity.uncommon;
    	
    	if(stack.getItemDamage() == 1)
    		return EnumRarity.rare;
    	
    	if(stack.getItemDamage() == 2)
    		return EnumRarity.epic;
    	
		return EnumRarity.common;
    }

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		list.add("Skin:");
		
		switch(stack.getItemDamage()) {
		case 0: list.add(EnumChatFormatting.GOLD + "Original"); break;
		case 1: list.add(EnumChatFormatting.BLUE + "Luna Space Center"); break;
		case 2: list.add(EnumChatFormatting.GREEN + "Post War"); break;
		}
	}

}
