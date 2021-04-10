package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemModBathwater extends ItemArmorMod {

	public ItemModBathwater() {
		super(ArmorModHandler.extra, true, true, true, true);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		
		String color = "" + (System.currentTimeMillis() % 1000 < 500 ? EnumChatFormatting.BLUE : EnumChatFormatting.LIGHT_PURPLE);
		
		if(this == ModItems.bathwater_mk2)
			color = "" + (System.currentTimeMillis() % 1000 < 500 ? EnumChatFormatting.GREEN : EnumChatFormatting.YELLOW);

		list.add(color + "Inflicts poison on the attacker");
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		
		String color = "" + (System.currentTimeMillis() % 1000 < 500 ? EnumChatFormatting.BLUE : EnumChatFormatting.LIGHT_PURPLE);
		
		if(this == ModItems.bathwater_mk2)
			color = "" + (System.currentTimeMillis() % 1000 < 500 ? EnumChatFormatting.GREEN : EnumChatFormatting.YELLOW);
		
		list.add(color + "  " + stack.getDisplayName() + " (Poisons attackers)");
	}
	
	@Override
	public void modDamage(LivingHurtEvent event, ItemStack armor) {
		
		if(!event.entityLiving.worldObj.isRemote) {
			
			if(event.source instanceof EntityDamageSource) {
				
				Entity attacker = ((EntityDamageSource)event.source).getEntity();
				
				if(attacker instanceof EntityLivingBase) {
					
					if(this == ModItems.bathwater)
						((EntityLivingBase)attacker).addPotionEffect(new PotionEffect(Potion.poison.id, 200, 2));
					
					if(this == ModItems.bathwater_mk2)
						((EntityLivingBase)attacker).addPotionEffect(new PotionEffect(Potion.wither.id, 200, 4));
				}
			}
		}
	}
}
