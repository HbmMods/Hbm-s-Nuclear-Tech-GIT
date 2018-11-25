package com.hbm.handler;

import java.util.ArrayList;
import java.util.List;

import com.hbm.potion.HbmPotion;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class HazmatRegistry {
	
	public static final HazmatRegistry instance = new HazmatRegistry();
	private static List<HazmatEntry> entries = new ArrayList();
	
	private class HazmatEntry {
		
		private Item item;
		private float resistance;
		
		private HazmatEntry(Item item, float resistance) {
			this.item = item;
			this.resistance = resistance;
		}
	}
	
	public void registerHazmat(Item item, float resistance) {
		
		entries.add(new HazmatEntry(item, resistance));
	}
	
	public float getResistance(Item item) {
		
		for(HazmatEntry entry : entries) {
			
			if(entry.item == item)
				return entry.resistance;
		}
		
		return 0.0F;
	}
	
	public float getResistance(EntityPlayer player) {
		
		float res = 0.0F;
		
		for(int i = 0; i < 4; i++) {
			if(player.inventory.armorInventory[i] != null) {
				res += getResistance(player.inventory.armorInventory[i].getItem());
			}
		}
		
		if(player.isPotionActive(HbmPotion.radx))
			res += 0.4F;
		
		return res;
		
	}

}
