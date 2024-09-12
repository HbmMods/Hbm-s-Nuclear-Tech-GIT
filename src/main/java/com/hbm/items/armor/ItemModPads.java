package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemModPads extends ItemArmorMod {

	float damageMod;
	
	public ItemModPads(float damageMod) {
		super(ArmorModHandler.boots_only, false, false, false, true);
		this.damageMod = damageMod;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		if(damageMod != 1F)
			list.add(EnumChatFormatting.RED + "-" + Math.round((1F - damageMod) * 100) + "% fall damage");
		
		if(this == ModItems.pads_static)
			list.add(EnumChatFormatting.DARK_PURPLE + "Passively charges electric armor when walking");
		
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}
	
	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		
		if(this == ModItems.pads_static)
			list.add(EnumChatFormatting.DARK_PURPLE + "  " + stack.getDisplayName() + " (-" + Math.round((1F - damageMod) * 100) + "% fall dmg / passive charge)");
		else
			list.add(EnumChatFormatting.DARK_PURPLE + "  " + stack.getDisplayName() + " (-" + Math.round((1F - damageMod) * 100) + "% fall dmg)");
	}

	@Override
	public void modDamage(LivingHurtEvent event, ItemStack armor) {
		
		if(event.source == DamageSource.fall)
			event.ammount *= damageMod;
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		
		if(!entity.worldObj.isRemote && this == ModItems.pads_static && entity instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) entity;
			
			if(player.distanceWalkedModified != player.prevDistanceWalkedModified) {
				
				if(ArmorFSB.hasFSBArmorIgnoreCharge(player)) {
					
					for(int i = 0; i < 4; i++) {
						
						ItemStack stack = player.inventory.armorInventory[i];
						
						if(stack != null && stack.getItem() instanceof ArmorFSBPowered) {
							
							ArmorFSBPowered powered = (ArmorFSBPowered) stack.getItem();
							
							long charge = powered.drain / 2;
							
							if(charge == 0)
								charge = powered.consumption / 40;
							
							long power = Math.min(powered.getMaxCharge(stack), powered.getCharge(stack) + charge);
							powered.setCharge(stack, power);
						}
					}
				}
			}
		}
	}
}
