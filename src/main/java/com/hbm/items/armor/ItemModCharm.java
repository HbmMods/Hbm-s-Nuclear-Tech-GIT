package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemModCharm extends ItemArmorMod {

	public ItemModCharm() {
		super(ArmorModHandler.helmet_only, true, true, false, false);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.AQUA + I18nUtil.resolveKey("armorMod.bless"));

		if(this == ModItems.protection_charm) {
			for(String s : I18nUtil.resolveKeyArray("item.protection_charm.desc")) {
				list.add(EnumChatFormatting.AQUA + s);
			}
		}
		if(this == ModItems.meteor_charm) {
			for(String s : I18nUtil.resolveKeyArray("item.meteor_charm.desc")) {
				list.add(EnumChatFormatting.AQUA + s);
			}
		}

		super.addInformation(stack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.GOLD + "  " + stack.getDisplayName());
	}

	@Override
	public void modDamage(LivingHurtEvent event, ItemStack armor) {

		if(event.source == ModDamageSource.broadcast) {

			if(this == ModItems.protection_charm)
				event.ammount *= 0.5F;
			if(this == ModItems.meteor_charm)
				event.ammount = 0F;
		}
	}
}
