package com.hbm.items.armor;

import java.util.List;

import com.google.common.collect.Multimap;
import com.hbm.handler.ArmorModHandler;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemModIron extends ItemArmorMod {

	public ItemModIron() {
		super(ArmorModHandler.cladding, true, true, true, true);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.WHITE + "+0.5 knockback resistance");
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.WHITE + "  " + stack.getDisplayName() + " (+0.5 knockback resistence)");
	}
	
	@Override
	public Multimap getModifiers(ItemStack armor) {
		Multimap multimap = super.getItemAttributeModifiers();
		
		multimap.put(SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(),
				new AttributeModifier(ArmorModHandler.UUIDs[((ItemArmor)armor.getItem()).armorType], "NTM Armor Mod Knockback", 0.5, 0));
		
		return multimap;
	}
}
