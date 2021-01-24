package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;

public class ItemModServos extends ItemArmorMod {

	public ItemModServos() {
		super(ArmorModHandler.servos, false, true, true, false);
	}
    
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		
		if(this == ModItems.servo_set) {
			list.add(EnumChatFormatting.DARK_PURPLE + "Chestplate: Haste I / Damage II");
			list.add(EnumChatFormatting.DARK_PURPLE + "Leggings: Speed I / Jump II");
		}
		if(this == ModItems.servo_set_desh) {
			list.add(EnumChatFormatting.DARK_PURPLE + "Chestplate: Haste III / Damage III");
			list.add(EnumChatFormatting.DARK_PURPLE + "Leggings: Speed II / Jump III");
		}
		
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		
		ItemArmor item = (ItemArmor)armor.getItem();
		
		if(item.armorType == 1) {

			if(this == ModItems.servo_set) {
				list.add(EnumChatFormatting.DARK_PURPLE + "  " + stack.getDisplayName() + " (Haste I / Damage II)");
			}
			if(this == ModItems.servo_set_desh) {
				list.add(EnumChatFormatting.DARK_PURPLE + "  " + stack.getDisplayName() + " (Haste III / Damage III)");
			}
		}
		
		if(item.armorType == 2) {

			if(this == ModItems.servo_set) {
				list.add(EnumChatFormatting.DARK_PURPLE + "  " + stack.getDisplayName() + " (Speed I / Jump II)");
			}
			if(this == ModItems.servo_set_desh) {
				list.add(EnumChatFormatting.DARK_PURPLE + "  " + stack.getDisplayName() + " (Speed II / Jump III)");
			}
		}
	}
	
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		
		ItemArmor item = (ItemArmor)armor.getItem();
		
		if(item.armorType == 1) {

			if(this == ModItems.servo_set) {
				entity.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 60, 0));
				entity.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 60, 1));
			}
			if(this == ModItems.servo_set_desh) {
				entity.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 60, 2));
				entity.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 60, 2));
			}
		}
		
		if(item.armorType == 2) {

			if(this == ModItems.servo_set) {
				entity.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 60, 0));
				entity.addPotionEffect(new PotionEffect(Potion.jump.id, 60, 1));
			}
			if(this == ModItems.servo_set_desh) {
				entity.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 60, 1));
				entity.addPotionEffect(new PotionEffect(Potion.jump.id, 60, 2));
			}
		}
	}

}
