package com.hbm.items.armor;

import java.util.List;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.handler.ArmorModHandler;
import com.hbm.potion.HbmPotion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;

public class ItemModAuto extends ItemArmorMod {

	public ItemModAuto() {
		super(ArmorModHandler.extra, false, true, false, false);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		list.add(EnumChatFormatting.BLUE + "Imported from Japsterdam.");
		
		list.add("");
		super.addInformation(stack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.BLUE + "  " + stack.getDisplayName());
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		
		if(!entity.worldObj.isRemote) {
			
			if(HbmLivingProps.getDigamma(entity) >= 5F) {
				ArmorModHandler.removeMod(armor, ArmorModHandler.extra);
				entity.worldObj.playSoundAtEntity(entity, "hbm:item.syringe", 1.0F, 1.0F);
				HbmLivingProps.setDigamma(entity, HbmLivingProps.getDigamma(entity) - 5F);
				entity.addPotionEffect(new PotionEffect(HbmPotion.stability.id, 60 * 20, 0));
				entity.heal(20F);
			}
		}
	}
}
