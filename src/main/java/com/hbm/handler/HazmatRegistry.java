package com.hbm.handler;

import java.util.HashMap;

import com.hbm.items.ModItems;
import com.hbm.items.armor.ItemModCladding;
import com.hbm.lib.Library;
import com.hbm.potion.HbmPotion;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HazmatRegistry {
	
	public static void registerHazmats() {

		//assuming coefficient of 10
		//real coefficient turned out to be 5
		//oops
		
		double helmet = 0.2D;
		double chest = 0.4D;
		double legs = 0.3D;
		double boots = 0.1D;

		double iron = 0.0225D; // 5%
		double gold = 0.0225D; // 5%
		double steel = 0.045D; // 10%
		double titanium = 0.045D; // 10%
		double alloy = 0.07D; // 15%
		double cobalt = 0.125D; // 25%
		
		double hazYellow = 0.3D; // 50%
		double hazRed = 0.7D; // 80%
		double hazGray = 1.3D; // 95%
		double paa = 1.3D; // 95%
		double liquidator = 2D; // 99%

		double t45 = 1D; // 90%
		double ajr = 1.3D; // 95%
		double bj = 1D; // 90%
		double hev = 1.3D; // 95%
		double fau = 4D; // 99.99%
		double dns = 5D; // 99.999%
		double security = 0.825D; // 85%
		double star = 1D; // 90%
		double cmb = 1.3D; // 95%
		double schrab = 2.3D; // 99.5%
		double euph = 10D; // <100%
		
		HazmatRegistry.registerHazmat(ModItems.hazmat_helmet, hazYellow * helmet);
		HazmatRegistry.registerHazmat(ModItems.hazmat_plate, hazYellow * chest);
		HazmatRegistry.registerHazmat(ModItems.hazmat_legs, hazYellow * legs);
		HazmatRegistry.registerHazmat(ModItems.hazmat_boots, hazYellow * boots);

		HazmatRegistry.registerHazmat(ModItems.hazmat_helmet_red, hazRed * helmet);
		HazmatRegistry.registerHazmat(ModItems.hazmat_plate_red, hazRed * chest);
		HazmatRegistry.registerHazmat(ModItems.hazmat_legs_red, hazRed * legs);
		HazmatRegistry.registerHazmat(ModItems.hazmat_boots_red, hazRed * boots);

		HazmatRegistry.registerHazmat(ModItems.hazmat_helmet_grey, hazGray * helmet);
		HazmatRegistry.registerHazmat(ModItems.hazmat_plate_grey, hazGray * chest);
		HazmatRegistry.registerHazmat(ModItems.hazmat_legs_grey, hazGray * legs);
		HazmatRegistry.registerHazmat(ModItems.hazmat_boots_grey, hazGray * boots);

		HazmatRegistry.registerHazmat(ModItems.liquidator_helmet, liquidator * helmet);
		HazmatRegistry.registerHazmat(ModItems.liquidator_plate, liquidator * chest);
		HazmatRegistry.registerHazmat(ModItems.liquidator_legs, liquidator * legs);
		HazmatRegistry.registerHazmat(ModItems.liquidator_boots, liquidator * boots);

		HazmatRegistry.registerHazmat(ModItems.t45_helmet, t45 * helmet);
		HazmatRegistry.registerHazmat(ModItems.t45_plate, t45 * chest);
		HazmatRegistry.registerHazmat(ModItems.t45_legs, t45 * legs);
		HazmatRegistry.registerHazmat(ModItems.t45_boots, t45 * boots);

		HazmatRegistry.registerHazmat(ModItems.ajr_helmet, ajr * helmet);
		HazmatRegistry.registerHazmat(ModItems.ajr_plate, ajr * chest);
		HazmatRegistry.registerHazmat(ModItems.ajr_legs, ajr * legs);
		HazmatRegistry.registerHazmat(ModItems.ajr_boots, ajr * boots);
		HazmatRegistry.registerHazmat(ModItems.ajro_helmet, ajr * helmet);
		HazmatRegistry.registerHazmat(ModItems.ajro_plate, ajr * chest);
		HazmatRegistry.registerHazmat(ModItems.ajro_legs, ajr * legs);
		HazmatRegistry.registerHazmat(ModItems.ajro_boots, ajr * boots);

		HazmatRegistry.registerHazmat(ModItems.bj_helmet, bj * helmet);
		HazmatRegistry.registerHazmat(ModItems.bj_plate, bj * chest);
		HazmatRegistry.registerHazmat(ModItems.bj_plate_jetpack, bj * chest);
		HazmatRegistry.registerHazmat(ModItems.bj_legs, bj * legs);
		HazmatRegistry.registerHazmat(ModItems.bj_boots, bj * boots);

		HazmatRegistry.registerHazmat(ModItems.hev_helmet, hev * helmet);
		HazmatRegistry.registerHazmat(ModItems.hev_plate, hev * chest);
		HazmatRegistry.registerHazmat(ModItems.hev_legs, hev * legs);
		HazmatRegistry.registerHazmat(ModItems.hev_boots, hev * boots);

		HazmatRegistry.registerHazmat(ModItems.fau_helmet, fau * helmet);
		HazmatRegistry.registerHazmat(ModItems.fau_plate, fau * chest);
		HazmatRegistry.registerHazmat(ModItems.fau_legs, fau * legs);
		HazmatRegistry.registerHazmat(ModItems.fau_boots, fau * boots);

		HazmatRegistry.registerHazmat(ModItems.dns_helmet, dns * helmet);
		HazmatRegistry.registerHazmat(ModItems.dns_plate, dns * chest);
		HazmatRegistry.registerHazmat(ModItems.dns_legs, dns * legs);
		HazmatRegistry.registerHazmat(ModItems.dns_boots, dns * boots);

		HazmatRegistry.registerHazmat(ModItems.paa_plate, paa * chest);
		HazmatRegistry.registerHazmat(ModItems.paa_legs, paa * legs);
		HazmatRegistry.registerHazmat(ModItems.paa_boots, paa * boots);

		HazmatRegistry.registerHazmat(ModItems.hazmat_paa_helmet, paa * helmet);
		HazmatRegistry.registerHazmat(ModItems.hazmat_paa_plate, paa * chest);
		HazmatRegistry.registerHazmat(ModItems.hazmat_paa_legs, paa * legs);
		HazmatRegistry.registerHazmat(ModItems.hazmat_paa_boots, paa * boots);

		HazmatRegistry.registerHazmat(ModItems.security_helmet, security * helmet);
		HazmatRegistry.registerHazmat(ModItems.security_plate, security * chest);
		HazmatRegistry.registerHazmat(ModItems.security_legs, security * legs);
		HazmatRegistry.registerHazmat(ModItems.security_boots, security * boots);

		HazmatRegistry.registerHazmat(ModItems.starmetal_helmet, star * helmet);
		HazmatRegistry.registerHazmat(ModItems.starmetal_plate, star * chest);
		HazmatRegistry.registerHazmat(ModItems.starmetal_legs, star * legs);
		HazmatRegistry.registerHazmat(ModItems.starmetal_boots, star * boots);

		HazmatRegistry.registerHazmat(ModItems.jackt, 0.1);
		HazmatRegistry.registerHazmat(ModItems.jackt2, 0.1);

		HazmatRegistry.registerHazmat(ModItems.gas_mask, 0.07);
		HazmatRegistry.registerHazmat(ModItems.gas_mask_m65, 0.095);

		HazmatRegistry.registerHazmat(ModItems.steel_helmet, steel * helmet);
		HazmatRegistry.registerHazmat(ModItems.steel_plate, steel * chest);
		HazmatRegistry.registerHazmat(ModItems.steel_legs, steel * legs);
		HazmatRegistry.registerHazmat(ModItems.steel_boots, steel * boots);

		HazmatRegistry.registerHazmat(ModItems.titanium_helmet, titanium * helmet);
		HazmatRegistry.registerHazmat(ModItems.titanium_plate, titanium * chest);
		HazmatRegistry.registerHazmat(ModItems.titanium_legs, titanium * legs);
		HazmatRegistry.registerHazmat(ModItems.titanium_boots, titanium * boots);

		HazmatRegistry.registerHazmat(ModItems.cobalt_helmet, cobalt * helmet);
		HazmatRegistry.registerHazmat(ModItems.cobalt_plate, cobalt * chest);
		HazmatRegistry.registerHazmat(ModItems.cobalt_legs, cobalt * legs);
		HazmatRegistry.registerHazmat(ModItems.cobalt_boots, cobalt * boots);

		HazmatRegistry.registerHazmat(Items.iron_helmet, iron * helmet);
		HazmatRegistry.registerHazmat(Items.iron_chestplate, iron * chest);
		HazmatRegistry.registerHazmat(Items.iron_leggings, iron * legs);
		HazmatRegistry.registerHazmat(Items.iron_boots, iron * boots);

		HazmatRegistry.registerHazmat(Items.golden_helmet, gold * helmet);
		HazmatRegistry.registerHazmat(Items.golden_chestplate, gold * chest);
		HazmatRegistry.registerHazmat(Items.golden_leggings, gold * legs);
		HazmatRegistry.registerHazmat(Items.golden_boots, gold * boots);

		HazmatRegistry.registerHazmat(ModItems.alloy_helmet, alloy * helmet);
		HazmatRegistry.registerHazmat(ModItems.alloy_plate, alloy * chest);
		HazmatRegistry.registerHazmat(ModItems.alloy_legs, alloy * legs);
		HazmatRegistry.registerHazmat(ModItems.alloy_boots, alloy * boots);

		HazmatRegistry.registerHazmat(ModItems.cmb_helmet, cmb * helmet);
		HazmatRegistry.registerHazmat(ModItems.cmb_plate, cmb * chest);
		HazmatRegistry.registerHazmat(ModItems.cmb_legs, cmb * legs);
		HazmatRegistry.registerHazmat(ModItems.cmb_boots, cmb * boots);

		HazmatRegistry.registerHazmat(ModItems.schrabidium_helmet, schrab * helmet);
		HazmatRegistry.registerHazmat(ModItems.schrabidium_plate, schrab * chest);
		HazmatRegistry.registerHazmat(ModItems.schrabidium_legs, schrab * legs);
		HazmatRegistry.registerHazmat(ModItems.schrabidium_boots, schrab * boots);

		HazmatRegistry.registerHazmat(ModItems.euphemium_helmet, euph * helmet);
		HazmatRegistry.registerHazmat(ModItems.euphemium_plate, euph * chest);
		HazmatRegistry.registerHazmat(ModItems.euphemium_legs, euph * legs);
		HazmatRegistry.registerHazmat(ModItems.euphemium_boots, euph * boots);
	}
	
	private static HashMap<Item, Double> entries = new HashMap();
	
	public static void registerHazmat(Item item, double resistance) {
		
		entries.put(item, resistance);
	}
	
	public static double getResistance(ItemStack stack) {
		
		if(stack == null)
			return 0;
		
		double cladding = getCladding(stack);
		
		Double f = entries.get(stack.getItem());
		
		if(f != null)
			return f + cladding;
		
		return cladding;
	}
	
	public static double getCladding(ItemStack stack) {

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
			res += 0.2F;
		
		return res;
		
	}

}
