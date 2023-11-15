package com.hbm.items.armor;

import java.util.List;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.handler.ArmorModHandler;

import com.hbm.util.I18nUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemModQuartz extends ItemArmorMod {

	public ItemModQuartz() {
		super(ArmorModHandler.extra, true, true, true, true);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		
		list.add(EnumChatFormatting.DARK_GRAY + I18nUtil.resolveKeyArray("armorMod.mod.Quartz")[0]);
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}
	
	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.DARK_GRAY + "  " + stack.getDisplayName() + I18nUtil.resolveKeyArray("armorMod.mod.Quartz")[1]);
	}
	
	@Override
	public void modDamage(LivingHurtEvent event, ItemStack armor) {
		
		if(!event.entityLiving.worldObj.isRemote) {
			float rad = HbmLivingProps.getRadiation(event.entityLiving);
			rad = Math.max(rad - 10, 0);
			HbmLivingProps.setRadiation(event.entityLiving, rad);
		}
	}
}
