package com.hbm.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class ArmorRegistry {

	public static HashMap<Item, List<HazardClass>> hazardClasses = new HashMap();
	
	public static void registerHazard(Item item, HazardClass... hazards) {
		hazardClasses.put(item, Arrays.asList(hazards));
	}
	
	public static boolean hasAllProtection(EntityLivingBase entity, int slot, HazardClass... clazz) {
		
		if(ArmorUtil.checkArmorNull(entity, slot))
			return false;
		
		List<HazardClass> list = hazardClasses.get(entity.getEquipmentInSlot(slot + 1).getItem());
		
		if(list == null)
			return false;
		
		return list.containsAll(Arrays.asList(clazz));
	}
	
	public static boolean hasAnyProtection(EntityLivingBase entity, int slot, HazardClass... clazz) {
		
		if(ArmorUtil.checkArmorNull(entity, slot))
			return false;
		
		List<HazardClass> list = hazardClasses.get(entity.getEquipmentInSlot(slot + 1).getItem());
		
		if(list == null)
			return false;
		
		for(HazardClass haz : clazz) {
			if(list.contains(haz)) return true;
		}
		
		return false;
	}
	
	public static boolean hasProtection(EntityLivingBase entity, int slot, HazardClass clazz) {
		
		if(ArmorUtil.checkArmorNull(entity, slot))
			return false;
		
		List<HazardClass> list = hazardClasses.get(entity.getEquipmentInSlot(slot + 1).getItem());
		
		if(list == null)
			return false;
		
		return list.contains(clazz);
	}
	
	public static enum HazardClass {
		GAS_CHLORINE("hazard.gasChlorine"),				//also attacks eyes -> no half mask
		GAS_MONOXIDE("hazard.gasMonoxide"),				//only affects lungs
		GAS_INERT("hazard.gasInert"),					//SA
		PARTICLE_COARSE("hazard.particleCoarse"),		//only affects lungs
		PARTICLE_FINE("hazard.particleFine"),			//only affects lungs
		BACTERIA("hazard.bacteria"),					//no half masks
		NERVE_AGENT("hazard.nerveAgent"),				//aggressive nerve agent, also attacks skin
		GAS_CORROSIVE("hazard.corrosive"),				//corrosive substance, also attacks skin
		LIGHT("hazard.light");
		
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
