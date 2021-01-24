package com.hbm.items.armor;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.ArmorModHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemModInsert extends ItemArmorMod {
	
	float damageMod;
	float projectileMod;
	float explosionMod;

	public ItemModInsert(float damageMod, float projectileMod, float explosionMod) {
		super(ArmorModHandler.kevlar, false, true, false, false);
		this.damageMod = damageMod;
		this.projectileMod = projectileMod;
		this.explosionMod = explosionMod;
	}
    
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		if(damageMod != 1F)
			list.add(EnumChatFormatting.RED + "-" + Math.round((1F - damageMod) * 100) + "% damage");
		if(projectileMod != 1F)
			list.add(EnumChatFormatting.YELLOW + "-" + Math.round((1F - projectileMod) * 100) + "% projectile damage");
		if(explosionMod != 1F)
			list.add(EnumChatFormatting.YELLOW + "-" + Math.round((1F - explosionMod) * 100) + "% explosion damage");
		
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		
		List<String> desc = new ArrayList();

		if(damageMod != 1F)
			desc.add("-" + Math.round((1F - damageMod) * 100) + "% dmg");
		if(projectileMod != 1F)
			desc.add("-" + Math.round((1F - projectileMod) * 100) + "% proj");
		if(explosionMod != 1F)
			desc.add("-" + Math.round((1F - explosionMod) * 100) + "% exp");
		
		String join = String.join(" / ", desc);
		
		list.add(EnumChatFormatting.DARK_PURPLE + "  " + stack.getDisplayName() + " (" + join + ")");
	}

	@Override
	public void modDamage(LivingHurtEvent event, ItemStack armor) {
		
		event.ammount *= damageMod;
		
		if(event.source.isProjectile())
			event.ammount *= projectileMod;
		
		if(event.source.isExplosion())
			event.ammount *= explosionMod;
	}

}
