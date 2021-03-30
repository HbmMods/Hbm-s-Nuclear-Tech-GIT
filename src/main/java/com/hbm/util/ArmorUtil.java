package com.hbm.util;

import com.hbm.handler.ArmorModHandler;
import com.hbm.handler.HazmatRegistry;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.potion.HbmPotion;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ArmorUtil {

	public static boolean checkArmor(EntityPlayer player, Item helmet, Item plate, Item legs, Item boots) {
		
		if(player.inventory.armorInventory[0] != null && 
				player.inventory.armorInventory[0].getItem() == boots && 
				player.inventory.armorInventory[1] != null && 
				player.inventory.armorInventory[1].getItem() == legs && 
				player.inventory.armorInventory[2] != null && 
				player.inventory.armorInventory[2].getItem() == plate && 
				player.inventory.armorInventory[3] != null && 
				player.inventory.armorInventory[3].getItem() == helmet)
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean checkArmorPiece(EntityPlayer player, Item armor, int slot)
	{
		if(player.inventory.armorInventory[slot] != null &&
				player.inventory.armorInventory[slot].getItem() == armor) 
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean checkArmorNull(EntityPlayer player, int slot)
	{
		if(player.inventory.armorInventory[slot] == null) 
		{
			return true;
		}
		
		return false;
	}
	
	public static void damageSuit(EntityPlayer player, int slot, int amount) {
		
		if(player.inventory.armorInventory[slot] == null)
			return;
		
		int j = player.inventory.armorInventory[slot].getItemDamage();
		player.inventory.armorInventory[slot].setItemDamage(j += amount);

		if(player.inventory.armorInventory[slot].getItemDamage() >= player.inventory.armorInventory[slot].getMaxDamage())
		{
			player.inventory.armorInventory[slot] = null;
		}
	}
	
	public static boolean checkForHazmat(EntityPlayer player) {
		
		if(checkArmor(player, ModItems.hazmat_helmet, ModItems.hazmat_plate, ModItems.hazmat_legs, ModItems.hazmat_boots) || 
				checkArmor(player, ModItems.hazmat_helmet_red, ModItems.hazmat_plate_red, ModItems.hazmat_legs_red, ModItems.hazmat_boots_red) || 
				checkArmor(player, ModItems.hazmat_helmet_grey, ModItems.hazmat_plate_grey, ModItems.hazmat_legs_grey, ModItems.hazmat_boots_grey) || 
				checkArmor(player, ModItems.t45_helmet, ModItems.t45_plate, ModItems.t45_legs, ModItems.t45_boots) || 
				checkArmor(player, ModItems.schrabidium_helmet, ModItems.schrabidium_plate, ModItems.schrabidium_legs, ModItems.schrabidium_boots) || 
				checkForHaz2(player)) {
			
			return true;
		}
		
		if(player.isPotionActive(HbmPotion.mutation))
			return true;
		
		return false;
	}
	
	public static boolean checkForHaz2(EntityPlayer player) {
		
		if(checkArmor(player, ModItems.hazmat_paa_helmet, ModItems.hazmat_paa_plate, ModItems.hazmat_paa_legs, ModItems.hazmat_paa_boots) || 
				checkArmor(player, ModItems.liquidator_helmet, ModItems.liquidator_plate, ModItems.liquidator_legs, ModItems.liquidator_boots) || 
				checkArmor(player, ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots))
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean checkForAsbestos(EntityPlayer player) {
		
		if(checkArmor(player, ModItems.asbestos_helmet, ModItems.asbestos_plate, ModItems.asbestos_legs, ModItems.asbestos_boots))
			return true;

		return false;
	}
	
	public static boolean checkForDigamma(EntityPlayer player) {
		
		if(checkArmor(player, ModItems.fau_helmet, ModItems.fau_plate, ModItems.fau_legs, ModItems.fau_boots))
			return true;
		
		if(player.isPotionActive(HbmPotion.stability.id))
			return true; 
		
		return false;
	}
	
	public static boolean checkForFaraday(EntityPlayer player) {
		
		ItemStack[] armor = player.inventory.armorInventory;
		
		if(armor[0] == null || armor[1] == null || armor[2] == null || armor[3] == null) return false;
		
		if(isFaradayArmor(armor[0]) && isFaradayArmor(armor[1]) && isFaradayArmor(armor[2]) && isFaradayArmor(armor[3]))
			return true;
		
		return false;
	}
	
	public static final String[] metals = new String[] {
			"chainmail",
			"iron",
			"silver",
			"gold",
			"platinum",
			"tin",
			"lead",
			"liquidator",
			"schrabidium",
			"euphemium",
			"steel",
			"cmb",
			"titanium",
			"alloy",
			"copper",
			"bronze",
			"electrum",
			"t45",
			"bj",
			"starmetal",
			"hazmat", //also count because rubber is insulating
			"rubber",
			"hev",
			"ajr",
			"spacesuit"
	};
	
	public static boolean isFaradayArmor(ItemStack item) {
		
		String name = item.getUnlocalizedName();
		
		for(String metal : metals) {
			
			if(name.toLowerCase().contains(metal))
				return true;
		}
		
		if(HazmatRegistry.getCladding(item) > 0)
			return true;
		
		return false;
	}
	
	public static boolean checkForGasMask(EntityPlayer player) {

		if(checkArmorPiece(player, ModItems.hazmat_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.hazmat_helmet_red, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.hazmat_helmet_grey, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.hazmat_paa_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.liquidator_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.gas_mask, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.gas_mask_m65, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.t45_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.ajr_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.hev_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.schrabidium_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.euphemium_helmet, 3))
		{
			return true;
		}
		
		if(player.isPotionActive(HbmPotion.mutation))
			return true;
		
		ItemStack helmet = player.getCurrentArmor(3);
		if(helmet != null && ArmorModHandler.hasMods(helmet)) {
			
			ItemStack mods[] = ArmorModHandler.pryMods(helmet);
			
			if(mods[ArmorModHandler.helmet_only] != null && mods[ArmorModHandler.helmet_only].getItem() == ModItems.attachment_mask)
				return true;
		}
		
		return false;
	}
	
	public static boolean checkForMonoMask(EntityPlayer player) {

		if(checkArmorPiece(player, ModItems.gas_mask_mono, 3))
			return true;
		
		if(checkArmorPiece(player, ModItems.liquidator_helmet, 3))
			return true;

		if(player.isPotionActive(HbmPotion.mutation))
			return true;
		
		ItemStack helmet = player.getCurrentArmor(3);
		if(helmet != null && ArmorModHandler.hasMods(helmet)) {
			
			ItemStack mods[] = ArmorModHandler.pryMods(helmet);
			
			if(mods[ArmorModHandler.helmet_only] != null && mods[ArmorModHandler.helmet_only].getItem() == ModItems.attachment_mask_mono)
				return true;
		}
		
		return false;
	}
	
	public static boolean checkForGoggles(EntityPlayer player) {

		if(checkArmorPiece(player, ModItems.goggles, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.hazmat_helmet_red, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.hazmat_helmet_grey, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.liquidator_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.gas_mask, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.t45_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.ajr_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.bj_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.hev_helmet, 3))
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean checkForFiend(EntityPlayer player) {
		
		return checkArmorPiece(player, ModItems.jackt, 2) && Library.checkForHeld(player, ModItems.shimmer_sledge);
	}
	
	public static boolean checkForFiend2(EntityPlayer player) {
		
		return checkArmorPiece(player, ModItems.jackt2, 2) && Library.checkForHeld(player, ModItems.shimmer_axe);
	}
}
