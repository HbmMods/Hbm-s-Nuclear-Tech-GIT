package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;

import com.hbm.util.I18nUtil;
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

		if(this == ModItems.scrumpy) {
			list.add(EnumChatFormatting.GOLD + I18nUtil.resolveKeyArray("armorMod.mod.Revive.scrumpy")[0]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("armorMod.mod.Revive.scrumpy")[1]);
		}
		if(this == ModItems.wild_p) {
			list.add(I18nUtil.resolveKey("armorMod.mod.Revive.wild_p"));
		}
		if(this == ModItems.fabsols_vodka) {
			for(String s : I18nUtil.resolveKeyArray("armorMod.mod.Revive.fabsols_vodka"))
				list.add(EnumChatFormatting.ITALIC + s);
		}
		
		list.add("");
		list.add(EnumChatFormatting.GOLD + "" + (stack.getMaxDamage() - stack.getItemDamage()) + I18nUtil.resolveKey("armorMod.mod.Revive"));
		list.add("");
		super.addInformation(stack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {

		list.add(EnumChatFormatting.GOLD + "  " + stack.getDisplayName() + " (" + (stack.getMaxDamage() - stack.getItemDamage()) + I18nUtil.resolveKey("armorMod.mod.Revive") + ")");
	}
}
