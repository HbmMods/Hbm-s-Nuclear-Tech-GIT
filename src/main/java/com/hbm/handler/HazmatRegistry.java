package com.hbm.handler;

import java.util.HashMap;

import com.hbm.items.armor.ItemModCladding;
import com.hbm.lib.Library;
import com.hbm.potion.HbmPotion;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HazmatRegistry {
	
	private static HashMap<Item, Float> entries = new HashMap();
	
	public static void registerHazmat(Item item, float resistance) {
		
		entries.put(item, resistance);
	}
	
	public static float getResistance(ItemStack stack) {
		
		if(stack == null)
			return 0;
		
		float cladding = getCladding(stack);
		
		Float f = entries.get(stack.getItem());
		
		if(f != null)
			return f + cladding;
		
		return cladding;
	}
	
	public static float getCladding(ItemStack stack) {

		if(stack.hasTagCompound() && stack.stackTagCompound.getFloat("hfr_cladding") > 0)
			return stack.stackTagCompound.getFloat("hfr_cladding");
		
		if(ArmorModHandler.hasMods(stack)) {
			
			ItemStack[] mods = ArmorModHandler.pryMods(stack);
			ItemStack cladding = mods[ArmorModHandler.cladding];
			
			if(cladding != null && cladding.getItem() instanceof ItemModCladding) {
				return ((ItemModCladding)cladding.getItem()).rad;
			}
		}
		
		return 0;
	}
	
	public static float getResistance(EntityPlayer player) {
		
		float res = 0.0F;
		
		if(player.getUniqueID().toString().equals(Library.Pu_238)) {
			res += 0.4F;
		}
		
		for(int i = 0; i < 4; i++) {
			res += getResistance(player.inventory.armorInventory[i]);
		}
		
		if(player.isPotionActive(HbmPotion.radx))
			res += 0.4F;
		
		return res;
		
	}

}
