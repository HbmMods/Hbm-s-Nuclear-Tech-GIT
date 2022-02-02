package com.hbm.inventory.recipes;

import java.util.HashMap;
import java.util.Map;

import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ItemEnums.EnumTarType;
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
	public static final int crack_frac_naph = 40;
	public static final int crack_frac_light = 30;
	public static final int crack_frac_aroma = 15;
	public static final int crack_frac_unsat = 15;

	public static final int heavy_frac_bitu = 30;
	public static final int heavy_frac_smear = 70;
	public static final int smear_frac_heat = 60;
	public static final int smear_frac_lube = 40;
	public static final int napht_frac_heat = 40;
	public static final int napht_frac_diesel = 60;
	public static final int light_frac_diesel = 40;
	public static final int light_frac_kero = 60;

	public static final int ncrack_frac_heat = 30;
	public static final int ncrack_frac_diesel = 70;
	public static final int lcrack_frac_kero = 70;
	public static final int lcrack_frac_petro = 30;
	public static final int coal_frac_coalgas = 70;
	public static final int coal_frac_natgas = 30;
	
	//cracking in percent
	public static final int oil_crack_oil = 80;
	public static final int oil_crack_petro = 20;
	public static final int bitumen_crack_oil = 80;
	public static final int bitumen_crack_aroma = 20;
	public static final int smear_crack_napht = 60;
	public static final int smear_crack_petro = 40;
	public static final int gas_crack_petro = 30;
	public static final int gas_crack_unsat = 20;
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
		
		recipes.put(ItemFluidIcon.make(Fluids.HOTCRACKOIL, 1000),
				new ItemStack[] {
						ItemFluidIcon.make(Fluids.NAPHTHA_CRACK, crack_frac_naph * 10),
						ItemFluidIcon.make(Fluids.LIGHTOIL_CRACK, crack_frac_light * 10),
						ItemFluidIcon.make(Fluids.AROMATICS, crack_frac_aroma * 10),
						ItemFluidIcon.make(Fluids.UNSATURATEDS, crack_frac_unsat * 10),
						DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRACK) });
		
		/*recipes.put(ItemFluidIcon.make(Fluids.HOTCRACKOIL, 1000),
				new ItemStack[] {
						ItemFluidIcon.make(Fluids.NAPHTHA_CRACK, oil_frac_heavy * 10),	//fractionates into crack diesel and heating oil
						ItemFluidIcon.make(Fluids.LIGHTOIL_CRACK, oil_frac_naph * 10),	//fractionates into kerosene and petroleum
						ItemFluidIcon.make(Fluids.AROMATICS, oil_frac_light * 10),		//used for making bakelite and TNT
						ItemFluidIcon.make(Fluids.UNSATURATEDS, oil_frac_petro * 10),	//for bakelite and perhaps acetylene torches
						DictFrame.fromOne(ModItems.coke, EnumCokeType.PETROLEUM) });*/
		
		return recipes;
	}
	
	public static void registerFractions() {
		fractions.put(Fluids.HEAVYOIL,			new Quartet(Fluids.BITUMEN,		Fluids.SMEAR,			heavy_frac_bitu,	heavy_frac_smear));
		fractions.put(Fluids.SMEAR,				new Quartet(Fluids.HEATINGOIL,	Fluids.LUBRICANT,		smear_frac_heat,	smear_frac_lube));
		fractions.put(Fluids.NAPHTHA,			new Quartet(Fluids.HEATINGOIL,	Fluids.DIESEL,			napht_frac_heat,	napht_frac_diesel));
		fractions.put(Fluids.NAPHTHA_CRACK,		new Quartet(Fluids.HEATINGOIL,	Fluids.DIESEL_CRACK,	ncrack_frac_heat,	ncrack_frac_diesel));
		fractions.put(Fluids.LIGHTOIL,			new Quartet(Fluids.DIESEL,		Fluids.KEROSENE,		light_frac_diesel,	light_frac_kero));
		fractions.put(Fluids.LIGHTOIL_CRACK,	new Quartet(Fluids.KEROSENE,	Fluids.PETROLEUM,		lcrack_frac_kero,	lcrack_frac_petro));
		fractions.put(Fluids.COALOIL,			new Quartet(Fluids.COALGAS,		Fluids.GAS,				coal_frac_coalgas,	coal_frac_natgas));
	}
	
	public static void registerCracking() {
		cracking.put(Fluids.OIL,		new Quartet(Fluids.CRACKOIL,	Fluids.PETROLEUM,		oil_crack_oil,		oil_crack_petro));
		cracking.put(Fluids.BITUMEN,	new Quartet(Fluids.OIL,			Fluids.AROMATICS,		bitumen_crack_oil,	bitumen_crack_aroma));
		cracking.put(Fluids.SMEAR,		new Quartet(Fluids.NAPHTHA,		Fluids.PETROLEUM,		smear_crack_napht,	smear_crack_petro));
		cracking.put(Fluids.GAS,		new Quartet(Fluids.PETROLEUM,	Fluids.UNSATURATEDS,	gas_crack_petro,	gas_crack_unsat));
		cracking.put(Fluids.DIESEL,		new Quartet(Fluids.KEROSENE,	Fluids.PETROLEUM,		diesel_crack_kero,	diesel_crack_petro));
		cracking.put(Fluids.KEROSENE,	new Quartet(Fluids.PETROLEUM,	Fluids.NONE,			kero_crack_petro,	0));
	}
	
	public static Quartet<FluidType, FluidType, Integer, Integer> getFractions(FluidType oil) {
		return fractions.get(oil);
	}
	
	public static Quartet<FluidType, FluidType, Integer, Integer> getCracking(FluidType oil) {
		return cracking.get(oil);
	}
}
