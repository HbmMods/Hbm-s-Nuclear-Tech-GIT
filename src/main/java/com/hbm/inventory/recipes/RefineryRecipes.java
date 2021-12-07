package com.hbm.inventory.recipes;

import java.util.HashMap;
import java.util.Map;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.Tuple.Quartet;

import net.minecraft.item.ItemStack;

public class RefineryRecipes {

	/// fractions in percent ///
	public static final int oil_frac_heavy = 50;
	public static final int oil_frac_naph = 25;
	public static final int oil_frac_light = 15;
	public static final int oil_frac_petro = 10;

	public static final int heavy_frac_bitu = 30;
	public static final int heavy_frac_smear = 70;
	public static final int smear_frac_heat = 60;
	public static final int smear_frac_lube = 40;
	public static final int napht_frac_heat = 40;
	public static final int napht_frac_diesel = 60;
	public static final int light_frac_diesel = 40;
	public static final int light_frac_kero = 60;
	
	//cracking in percent
	public static final int bitumen_crack_oil = 80;
	public static final int bitumen_crack_petro = 20;
	public static final int smear_crack_napht = 60;
	public static final int smear_crack_petro = 40;
	public static final int gas_crack_petro = 50;
	public static final int diesel_crack_kero = 40;
	public static final int diesel_crack_petro = 30;
	public static final int kero_crack_petro = 60;

	//why didn't i use fluid stacks here? was there a reason?
	private static Map<FluidType, Quartet<FluidType, FluidType, Integer, Integer>> fractions = new HashMap();
	private static Map<FluidType, Quartet<FluidType, FluidType, Integer, Integer>> cracking = new HashMap();
	
	public static Map<Object, Object[]> getRefineryRecipe() {

		Map<Object, Object[]> recipes = new HashMap<Object, Object[]>();
		
		recipes.put(ItemFluidIcon.make(FluidType.HOTOIL, 1000),
				new ItemStack[] {
						ItemFluidIcon.make(FluidType.HEAVYOIL, oil_frac_heavy * 10),
						ItemFluidIcon.make(FluidType.NAPHTHA, oil_frac_naph * 10),
						ItemFluidIcon.make(FluidType.LIGHTOIL, oil_frac_light * 10),
						ItemFluidIcon.make(FluidType.PETROLEUM, oil_frac_petro * 10),
						new ItemStack(ModItems.sulfur, 1) });
		
		return recipes;
	}
	
	public static void registerFractions() {
		fractions.put(FluidType.HEAVYOIL,	new Quartet(FluidType.BITUMEN,		FluidType.SMEAR,		heavy_frac_bitu,	heavy_frac_smear));
		fractions.put(FluidType.SMEAR,		new Quartet(FluidType.HEATINGOIL,	FluidType.LUBRICANT,	smear_frac_heat,	smear_frac_lube));
		fractions.put(FluidType.NAPHTHA,	new Quartet(FluidType.HEATINGOIL,	FluidType.DIESEL,		napht_frac_heat,	napht_frac_diesel));
		fractions.put(FluidType.LIGHTOIL,	new Quartet(FluidType.DIESEL,		FluidType.KEROSENE,		light_frac_diesel,	light_frac_kero));
	}
	
	public static void registerCracking() {
		cracking.put(FluidType.BITUMEN,		new Quartet(FluidType.OIL,			FluidType.PETROLEUM,	bitumen_crack_oil,	bitumen_crack_petro));
		cracking.put(FluidType.SMEAR,		new Quartet(FluidType.NAPHTHA,		FluidType.PETROLEUM,	smear_crack_napht,	smear_crack_petro));
		cracking.put(FluidType.GAS,			new Quartet(FluidType.PETROLEUM,	FluidType.NONE,			gas_crack_petro,	0));
		cracking.put(FluidType.DIESEL,		new Quartet(FluidType.KEROSENE,		FluidType.PETROLEUM,	diesel_crack_kero,	diesel_crack_petro));
		cracking.put(FluidType.KEROSENE,	new Quartet(FluidType.PETROLEUM,	FluidType.NONE,			kero_crack_petro,	0));
	}
	
	public static Quartet<FluidType, FluidType, Integer, Integer> getFractions(FluidType oil) {
		return fractions.get(oil);
	}
	
	public static Quartet<FluidType, FluidType, Integer, Integer> getCracking(FluidType oil) {
		return cracking.get(oil);
	}
}
