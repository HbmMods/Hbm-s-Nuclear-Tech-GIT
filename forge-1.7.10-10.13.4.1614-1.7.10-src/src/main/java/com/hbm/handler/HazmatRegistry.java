package com.hbm.handler;

import java.util.HashMap;

import com.hbm.potion.HbmPotion;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HazmatRegistry {
	
	public static final HazmatRegistry instance = new HazmatRegistry();
	private static HashMap<Item, Float> entries = new HashMap();
	
	private class HazmatEntry {
		
		private Item item;
		private float resistance;
		
		private HazmatEntry(Item item, float resistance) {
			this.item = item;
			this.resistance = resistance;
		}
	}
	
	public void registerHazmat(Item item, float resistance) {
		
		entries.put(item, resistance);
	}
	
	public float getResistance(ItemStack stack) {
		
		if(stack == null)
			return 0;
		
		float cladding = getCladding(stack);
		
		Float f = entries.get(stack.getItem());
		
		if(f != null)
			return f + cladding;
		
		return cladding;
	}
	
	public float getCladding(ItemStack stack) {

		if(stack.hasTagCompound() && stack.stackTagCompound.getFloat("hfr_cladding") > 0)
			return stack.stackTagCompound.getFloat("hfr_cladding");
		
		return 0;
	}
	
	public float getResistance(EntityPlayer player) {
		
		float res = 0.0F;
		
		for(int i = 0; i < 4; i++) {
			res += getResistance(player.inventory.armorInventory[i]);
		}
		
		if(player.isPotionActive(HbmPotion.radx))
			res += 0.4F;
		
		return res;
		
	}

}
