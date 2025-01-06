package com.hbm.util;

import api.hbm.item.IGasMask;
import com.hbm.handler.ArmorModHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ArmorRegistry {

	public static HashMap<Item, ArrayList<HazardClass>> hazardClasses = new HashMap();

	public static void registerHazard(Item item, HazardClass... hazards) {
		hazardClasses.put(item, new ArrayList<HazardClass>(Arrays.asList(hazards)));
	}

	public static boolean hasAllProtection(EntityLivingBase entity, int slot, HazardClass... clazz) {

		if(ArmorUtil.checkArmorNull(entity, slot))
			return false;

		List<HazardClass> list = getProtectionFromItem(entity.getEquipmentInSlot(slot + 1), entity);
		return list.containsAll(Arrays.asList(clazz));
	}

	public static boolean hasAnyProtection(EntityLivingBase entity, int slot, HazardClass... clazz) {

		if(ArmorUtil.checkArmorNull(entity, slot))
			return false;

		List<HazardClass> list = getProtectionFromItem(entity.getEquipmentInSlot(slot + 1), entity);

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

		List<HazardClass> list = getProtectionFromItem(entity.getEquipmentInSlot(slot + 1), entity);

		if(list == null)
			return false;

		return list.contains(clazz);
	}

	public static List<HazardClass> getProtectionFromItem(ItemStack stack, EntityLivingBase entity) {

		List<HazardClass> prot = new ArrayList();

		Item item = stack.getItem();

		//if the item has HazardClasses assigned to it, add those
		if(hazardClasses.containsKey(item))
			prot.addAll(hazardClasses.get(item));

		if(item instanceof IGasMask) {
			IGasMask mask = (IGasMask) item;
			ItemStack filter = mask.getFilter(stack, entity);

			if(filter != null) {
				//add the HazardClasses from the filter, then remove the ones blacklisted by the mask
				List<HazardClass> filProt = hazardClasses.get(filter.getItem());

				for(HazardClass c : mask.getBlacklist(stack, entity))
					filProt.remove(c);

				prot.addAll(filProt);
			}
		}

		if(ArmorModHandler.hasMods(stack)) {

			ItemStack[] mods = ArmorModHandler.pryMods(stack);

			for(ItemStack mod : mods) {

				//recursion! run the exact same procedure on every mod, in case future mods will have filter support
				if(mod != null)
					prot.addAll(getProtectionFromItem(mod, entity));
			}
		}

		return prot;
	}

	public static enum HazardClass {
		GAS_LUNG("hazard.gasChlorine"),				//also attacks eyes -> no half mask
		GAS_MONOXIDE("hazard.gasMonoxide"),				//only affects lungs
		GAS_INERT("hazard.gasInert"),					//SA
		PARTICLE_COARSE("hazard.particleCoarse"),		//only affects lungs
		PARTICLE_FINE("hazard.particleFine"),			//only affects lungs
		BACTERIA("hazard.bacteria"),					//no half masks
		//NERVE_AGENT("hazard.nerveAgent"),				//aggressive nerve agent, also attacks skin
		GAS_BLISTERING("hazard.corrosive"),				//corrosive substance, also attacks skin
		SAND("hazard.sand"),							//blinding sand particles
		LIGHT("hazard.light");							//blinding light

		public final String lang;

		private HazardClass(String lang) {
			this.lang = lang;
		}
	}
}
