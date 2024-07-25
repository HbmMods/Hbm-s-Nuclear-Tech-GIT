package com.hbm.items.armor;

import com.hbm.handler.ArmorModHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ItemModNightVisionMK2 extends ItemModNightVision {
	public ItemModNightVisionMK2() {
		super();
	}

	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		if(!entity.worldObj.isRemote && entity instanceof EntityPlayer && armor.getItem() instanceof ArmorFSBPowered && ArmorFSBPowered.hasFSBArmor((EntityPlayer) entity)) {
			entity.addPotionEffect(new PotionEffect(Potion.nightVision.id, 15 * 20, 0));
		}
	}
}
