package com.hbm.items.armor;

import java.util.List;

import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemModCard extends ItemArmorMod {
	
	public ItemModCard() {
		super(ArmorModHandler.helmet_only, true, true, false, false);
		this.setCreativeTab(null);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		if(this == ModItems.card_aos) {
			list.add(EnumChatFormatting.RED + "Top of the line!");
			list.add(EnumChatFormatting.RED + "Guns now have a 33% chance to not consume ammo.");
		}
		if(this == ModItems.card_qos) {
			list.add(EnumChatFormatting.RED + "Power!");
			list.add(EnumChatFormatting.RED + "Adds a 33% chance to tank damage with no cap.");
		}
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.RED + stack.getDisplayName());
	}
	
	@Override
	public void modDamage(LivingHurtEvent event, ItemStack armor) {
		if(this == ModItems.card_qos && event.entityLiving.getRNG().nextInt(3) == 0 && event.entityLiving instanceof EntityPlayer) {
			HbmPlayerProps.plink((EntityPlayer) event.entityLiving, "random.break", 0.5F, 1.0F + event.entityLiving.getRNG().nextFloat() * 0.5F);
			event.ammount = 0;
			event.setCanceled(true);
		}
	}
}
