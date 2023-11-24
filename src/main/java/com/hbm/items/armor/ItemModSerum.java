package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import com.hbm.util.I18nUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;

public class ItemModSerum extends ItemArmorMod {

	public ItemModSerum() {
		super(ArmorModHandler.extra, true, true, true, true);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		
		list.add(EnumChatFormatting.GREEN + I18nUtil.resolveKeyArray("armorMod.mod.Serum")[0]);
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}
	
	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.BLUE + "  " + stack.getDisplayName() + I18nUtil.resolveKeyArray("armorMod.mod.Serum")[1]);
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		
		if(!entity.worldObj.isRemote && entity.isPotionActive(Potion.poison.id)) {
			entity.removePotionEffect(Potion.poison.id);
			entity.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 100, 4));
		}
	}
}
