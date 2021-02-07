package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemModRevive extends ItemArmorMod {

	public ItemModRevive(int durability) {
		super(ArmorModHandler.extra, false, false, true, false);
		this.setMaxDamage(durability);
	}
    
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.GOLD + "But how did you survive?");
		list.add(EnumChatFormatting.RED + "I was drunk.");
		
		list.add("");
		super.addInformation(stack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		
		list.add(EnumChatFormatting.GOLD + "  " + stack.getDisplayName() + " (" + (stack.getMaxDamage() - stack.getItemDamage()) + " revives left)");
	}

}
