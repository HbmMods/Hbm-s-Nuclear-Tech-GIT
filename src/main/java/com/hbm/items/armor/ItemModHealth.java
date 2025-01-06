package com.hbm.items.armor;

import java.util.List;

import com.google.common.collect.Multimap;
import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemModHealth extends ItemArmorMod {
	
	float health;

	public ItemModHealth(float health) {
		super(ArmorModHandler.extra, false, true, false, false);
		this.health = health;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		
		String color = "" + (System.currentTimeMillis() % 1000 < 500 ? EnumChatFormatting.RED : EnumChatFormatting.LIGHT_PURPLE);

		list.add(color + "+" + (Math.round(health * 10) * 0.1) + " health");
		list.add("");
		
		if(this == ModItems.black_diamond) {
			list.add(EnumChatFormatting.DARK_GRAY + "Nostalgia");
			list.add("");
		}
		
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		
		String color = "" + (System.currentTimeMillis() % 1000 < 500 ? EnumChatFormatting.RED : EnumChatFormatting.LIGHT_PURPLE);
		
		list.add(color + "  " + stack.getDisplayName() + " (+" + (Math.round(health * 10) * 0.1) + " health)");
	}
	
	@Override
	public Multimap getModifiers(ItemStack armor) {
		Multimap multimap = super.getAttributeModifiers(armor);
		
		multimap.put(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(),
				new AttributeModifier(ArmorModHandler.UUIDs[((ItemArmor)armor.getItem()).armorType], "NTM Armor Mod Health", health, 0));
		
		return multimap;
	}

}
