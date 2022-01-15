package com.hbm.inventory.recipes;

import java.util.HashMap;
import java.util.Map;

import com.hbm.handler.FluidTypeHandler.FluidTypeTheOldOne;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
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
		
		recipes.put(ItemFluidIcon.make(Fluids.HOTOIL, 1000),
				new ItemStack[] {
						ItemFluidIcon.make(Fluids.HEAVYOIL, oil_frac_heavy * 10),
						ItemFluidIcon.make(Fluids.NAPHTHA, oil_frac_naph * 10),
						ItemFluidIcon.make(Fluids.LIGHTOIL, oil_frac_light * 10),
						ItemFluidIcon.make(Fluids.PETROLEUM, oil_frac_petro * 10),
						new ItemStack(ModItems.sulfur, 1) });
		
		return recipes;
	}
	
	public static void registerFractions() {
		fractions.put(FluidTypeTheOldOne.HEAVYOIL,	new Quartet(FluidTypeTheOldOne.BITUMEN,		FluidTypeTheOldOne.SMEAR,		heavy_frac_bitu,	heavy_frac_smear));
		fractions.put(FluidTypeTheOldOne.SMEAR,		new Quartet(FluidTypeTheOldOne.HEATINGOIL,	FluidTypeTheOldOne.LUBRICANT,	smear_frac_heat,	smear_frac_lube));
		fractions.put(FluidTypeTheOldOne.NAPHTHA,	new Quartet(FluidTypeTheOldOne.HEATINGOIL,	FluidTypeTheOldOne.DIESEL,		napht_frac_heat,	napht_frac_diesel));
		fractions.put(FluidTypeTheOldOne.LIGHTOIL,	new Quartet(FluidTypeTheOldOne.DIESEL,		FluidTypeTheOldOne.KEROSENE,		light_frac_diesel,	light_frac_kero));
	}
	
	public static void registerCracking() {
		cracking.put(FluidTypeTheOldOne.BITUMEN,		new Quartet(FluidTypeTheOldOne.OIL,			FluidTypeTheOldOne.PETROLEUM,	bitumen_crack_oil,	bitumen_crack_petro));
		cracking.put(FluidTypeTheOldOne.SMEAR,		new Quartet(FluidTypeTheOldOne.NAPHTHA,		FluidTypeTheOldOne.PETROLEUM,	smear_crack_napht,	smear_crack_petro));
		cracking.put(FluidTypeTheOldOne.GAS,			new Quartet(FluidTypeTheOldOne.PETROLEUM,	FluidTypeTheOldOne.NONE,			gas_crack_petro,	0));
		cracking.put(FluidTypeTheOldOne.DIESEL,		new Quartet(FluidTypeTheOldOne.KEROSENE,		FluidTypeTheOldOne.PETROLEUM,	diesel_crack_kero,	diesel_crack_petro));
		cracking.put(FluidTypeTheOldOne.KEROSENE,	new Quartet(FluidTypeTheOldOne.PETROLEUM,	FluidTypeTheOldOne.NONE,			kero_crack_petro,	0));
	}
	
	public static Quartet<FluidType, FluidType, Integer, Integer> getFractions(FluidType oil) {
		return fractions.get(oil);
	}
	
	public static Quartet<FluidType, FluidType, Integer, Integer> getCracking(FluidType oil) {
		return cracking.get(oil);
	}
}
