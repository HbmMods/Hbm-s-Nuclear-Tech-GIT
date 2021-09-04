package com.hbm.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class ArmorRegistry {

	public static HashMap<Item, List<HazardClass>> hazardClasses = new HashMap();
	
	public static void registerHazard(Item item, HazardClass... hazards) {
		hazardClasses.put(item, Arrays.asList(hazards));
	}
	
	public static boolean hasProtection(EntityPlayer player, int slot, HazardClass clazz) {
		
		if(ArmorUtil.checkArmorNull(player, slot))
			return false;
		
		List<HazardClass> list = hazardClasses.get(player.inventory.armorInventory[slot].getItem());
		
		if(list == null)
			return false;
		
		return list.contains(clazz);
	}
	
	public static enum HazardClass {
		GAS_CHLORINE("hazard.gasChlorine"),
		GAS_MONOXIDE("hazard.gasMonoxide"),
		GAS_INERT("hazard.gasInert"),
		PARTICLE_COARSE("hazard.particleCoarse"),
		PARTICLE_FINE("hazard.particleFine"),
		BACTERIA("hazard.bacteria");
		
		public final String lang;
		
		private HazardClass(String lang) {
			this.lang = lang;
		}
	}
	
	/*public static enum ArmorClass {
		MASK_FILTERED,
		MASK_OXY,
		GOGGLES,
		HAZMAT_HEAT,
		HAZMAT_RADIATION,
		HAZMAT_BIO;
	}*/
}
