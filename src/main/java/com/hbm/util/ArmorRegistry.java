package com.hbm.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.hbm.handler.ArmorModHandler;

import api.hbm.item.IGasMask;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
	/**
	 * Type of hazard <br><br>
	 * <li>{@link #GAS_CHLORINE}<br>
	 * <li>{@link #GAS_MONOXIDE}<br>
	 * <li>{@link #PARTICLE_COARSE}<br>
	 * <li>{@link #PARTICLE_FINE}<br>
	 * <li>{@link #NERVE_AGENT}<br>
	 * <li>{@link #GAS_CORROSIVE}<br>
	 * <li>{@link #SAND}<br>
	 * <li>{@link #LIGHT}
	 * @author HBM
	 * 
	 */
	public static enum HazardClass
	{
		/**also attacks eyes -> no half mask**/
		GAS_CHLORINE("hazard.gasChlorine"),
		/**only affects lungs**/
		GAS_MONOXIDE("hazard.gasMonoxide"),
		/**SA**/
		GAS_INERT("hazard.gasInert"),
		/**only affects lungs**/
		PARTICLE_COARSE("hazard.particleCoarse"),
		/**only affects lungs***/
		PARTICLE_FINE("hazard.particleFine"),
		/**no half masks**/
		BACTERIA("hazard.bacteria"),
		/**aggressive nerve agent, also attacks skin**/
		NERVE_AGENT("hazard.nerveAgent"),
		/**corrosive substance, also attacks skin***/
		GAS_CORROSIVE("hazard.corrosive"),
		/**blinding sand particles**/
		SAND("hazard.sand"),
		/**blinding light**/
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
