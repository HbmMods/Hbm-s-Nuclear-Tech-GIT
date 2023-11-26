package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemModMorningGlory extends ItemArmorMod {

	public ItemModMorningGlory() {
		super(ArmorModHandler.extra, true, true, true, true);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.LIGHT_PURPLE + "5% chance to apply resistance when hit, wither immunity");
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.LIGHT_PURPLE + "  " + stack.getDisplayName() + " (5% for resistance, wither immunity)");
	}
	
	@Override
	public void modDamage(LivingHurtEvent event, ItemStack armor) {
		
		if(!event.entity.worldObj.isRemote && event.entity.worldObj.rand.nextInt(20) == 0) {
			event.entityLiving.addPotionEffect(new PotionEffect(Potion.resistance.id, 100, 4));
		}
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		
		if(!entity.worldObj.isRemote && entity.isPotionActive(Potion.wither.id)) {
			entity.removePotionEffect(Potion.wither.id);
		}
	}
}
