package com.hbm.items.armor;

import com.hbm.handler.ArmorModHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class ItemModNightVision extends ItemArmorMod {
	public ItemModNightVision() {
		super(ArmorModHandler.helmet_only, true, false, false, false);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.AQUA + I18n.format("item.night_vision.description.item"));
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.YELLOW + I18n.format("item.night_vision.description.in_armor", stack.getDisplayName()));
	}

	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		if(!entity.worldObj.isRemote && entity instanceof EntityPlayer && armor.getItem() instanceof ArmorFSBPowered && ArmorFSBPowered.hasFSBArmor((EntityPlayer) entity)) {
			entity.addPotionEffect(new PotionEffect(Potion.nightVision.id, 15 * 20, 0));

			if(entity.getRNG().nextInt(100) == 0) {
				armor.damageItem(1, entity);
			}
		}
	}
}
