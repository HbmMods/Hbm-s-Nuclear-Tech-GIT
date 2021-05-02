package com.hbm.items.armor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.hbm.handler.ArmorModHandler;
import com.hbm.potion.HbmPotion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;

public class ItemModMilk extends ItemArmorMod {
	
	public ItemModMilk() {
		super(ArmorModHandler.extra, true, true, true, true);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.WHITE + "Removes bad potion effects");
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.WHITE + "  " + stack.getDisplayName() + " (Removes bad potion effects)");
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		
		List<Integer> ints = new ArrayList();

		Iterator iterator = ((Collection) entity.getActivePotionEffects()).iterator();

		while(iterator.hasNext()) {

			PotionEffect eff = (PotionEffect) iterator.next();

			if(HbmPotion.getIsBadEffect(Potion.potionTypes[eff.getPotionID()])) {
				ints.add(eff.getPotionID());
			}
		}

		for(Integer i : ints) {
			entity.removePotionEffect(i);
		}
	}
}
