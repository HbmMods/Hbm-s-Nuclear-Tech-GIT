package com.hbm.inventory.recipes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ItemEnums.EnumTarType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.ItemStackUtil;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.Tuple.Quintet;

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
	public static final int coal_frac_coalgas = 30;
	public static final int coal_frac_oil = 70;
	public static final int creo_frac_coaloil = 10;
	public static final int creo_frac_bitu = 90;
	
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
	public static final int wood_crack_aroma = 10;
	public static final int wood_crack_heat = 40;

	private static Map<FluidType, Quintet<FluidStack, FluidStack, FluidStack, FluidStack, ItemStack>> refinery = new HashMap();
	private static Map<FluidType, Pair<FluidStack, FluidStack>> fractions = new HashMap();
	private static Map<FluidType, Pair<FluidStack, FluidStack>> cracking = new HashMap();
	
	public static Map<Object, Object[]> getRefineryRecipe() {

		Map<Object, Object[]> recipes = new HashMap<Object, Object[]>();
		
		for(Entry<FluidType, Quintet<FluidStack, FluidStack, FluidStack, FluidStack, ItemStack>> recipe : refinery.entrySet()) {
			
			Quintet<FluidStack, FluidStack, FluidStack, FluidStack, ItemStack> fluids = recipe.getValue();
			
			recipes.put(ItemFluidIcon.make(recipe.getKey(), 1000),
					new ItemStack[] {
							ItemFluidIcon.make(fluids.getV().type, fluids.getV().fill * 10),
							ItemFluidIcon.make(fluids.getW().type, fluids.getW().fill * 10),
							ItemFluidIcon.make(fluids.getX().type, fluids.getX().fill * 10),
							ItemFluidIcon.make(fluids.getY().type, fluids.getY().fill * 10),
							ItemStackUtil.carefulCopy(fluids.getZ()) });
		}
		
		return recipes;
	}
	
	public static void registerRefinery() {
		refinery.put(Fluids.HOTOIL, new Quintet(
				new FluidStack(Fluids.HEAVYOIL,		oil_frac_heavy),
				new FluidStack(Fluids.NAPHTHA,		oil_frac_naph),
				new FluidStack(Fluids.LIGHTOIL,		oil_frac_light),
				new FluidStack(Fluids.PETROLEUM,	oil_frac_petro),
				new ItemStack(ModItems.sulfur)
				));
		refinery.put(Fluids.HOTCRACKOIL, new Quintet(
				new FluidStack(Fluids.NAPHTHA_CRACK,	crack_frac_naph),
				new FluidStack(Fluids.LIGHTOIL_CRACK,	crack_frac_light),
				new FluidStack(Fluids.AROMATICS,		crack_frac_aroma),
				new FluidStack(Fluids.UNSATURATEDS,		crack_frac_unsat),
				DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRACK)
				));
	}
	
	public static void registerFractions() {
		fractions.put(Fluids.HEAVYOIL,			new Pair(new FluidStack(Fluids.BITUMEN,		heavy_frac_bitu),	new FluidStack(Fluids.SMEAR,		heavy_frac_smear)));
		fractions.put(Fluids.SMEAR,				new Pair(new FluidStack(Fluids.HEATINGOIL,	smear_frac_heat),	new FluidStack(Fluids.LUBRICANT,	smear_frac_lube)));
		fractions.put(Fluids.NAPHTHA,			new Pair(new FluidStack(Fluids.HEATINGOIL,	napht_frac_heat),	new FluidStack(Fluids.DIESEL,		napht_frac_diesel)));
		fractions.put(Fluids.NAPHTHA_CRACK,		new Pair(new FluidStack(Fluids.HEATINGOIL,	ncrack_frac_heat),	new FluidStack(Fluids.DIESEL_CRACK,	ncrack_frac_diesel)));
		fractions.put(Fluids.LIGHTOIL,			new Pair(new FluidStack(Fluids.DIESEL,		light_frac_diesel),	new FluidStack(Fluids.KEROSENE,		light_frac_kero)));
		fractions.put(Fluids.LIGHTOIL_CRACK,	new Pair(new FluidStack(Fluids.KEROSENE,	lcrack_frac_kero),	new FluidStack(Fluids.PETROLEUM,	lcrack_frac_petro)));
		fractions.put(Fluids.COALOIL,			new Pair(new FluidStack(Fluids.COALGAS,		coal_frac_coalgas),	new FluidStack(Fluids.OIL,			coal_frac_oil)));
		fractions.put(Fluids.COALCREOSOTE,		new Pair(new FluidStack(Fluids.COALOIL,		creo_frac_coaloil),	new FluidStack(Fluids.BITUMEN,		creo_frac_bitu)));
	}
	
	public static void registerCracking() {
		cracking.put(Fluids.OIL,			new Pair(new FluidStack(Fluids.CRACKOIL,	oil_crack_oil),		new FluidStack(Fluids.PETROLEUM,	oil_crack_petro)));
		cracking.put(Fluids.BITUMEN,		new Pair(new FluidStack(Fluids.OIL,			bitumen_crack_oil),	new FluidStack(Fluids.AROMATICS,	bitumen_crack_aroma)));
		cracking.put(Fluids.SMEAR,			new Pair(new FluidStack(Fluids.NAPHTHA,		smear_crack_napht),	new FluidStack(Fluids.PETROLEUM,	smear_crack_petro)));
		cracking.put(Fluids.GAS,			new Pair(new FluidStack(Fluids.PETROLEUM,	gas_crack_petro),	new FluidStack(Fluids.UNSATURATEDS,	gas_crack_unsat)));
		cracking.put(Fluids.DIESEL,			new Pair(new FluidStack(Fluids.KEROSENE,	diesel_crack_kero),	new FluidStack(Fluids.PETROLEUM,	diesel_crack_petro)));
		cracking.put(Fluids.DIESEL_CRACK,	new Pair(new FluidStack(Fluids.KEROSENE,	diesel_crack_kero),	new FluidStack(Fluids.PETROLEUM,	diesel_crack_petro)));
		cracking.put(Fluids.KEROSENE,		new Pair(new FluidStack(Fluids.PETROLEUM,	kero_crack_petro),	new FluidStack(Fluids.NONE,			0)));
		cracking.put(Fluids.WOODOIL,		new Pair(new FluidStack(Fluids.HEATINGOIL,	wood_crack_heat),	new FluidStack(Fluids.AROMATICS,	wood_crack_aroma)));
	}
	
	public static Quintet<FluidStack, FluidStack, FluidStack, FluidStack, ItemStack> getRefinery(FluidType oil) {
		return refinery.get(oil);
	}
	
	public static Pair<FluidStack, FluidStack> getFractions(FluidType oil) {
		return fractions.get(oil);
	}
	
	public static Pair<FluidStack, FluidStack> getCracking(FluidType oil) {
		return cracking.get(oil);
	}
	
	protected static Map<FluidType, Pair<FluidStack, FluidStack>> getCrackingRecipes() {
		return cracking;
	}
	
	public static HashMap<Object, Object> getFractionRecipesForNEI() {

		HashMap<Object, Object> recipes = new HashMap();
		
		for(Entry<FluidType, Pair<FluidStack, FluidStack>> recipe : fractions.entrySet()) {
			ItemStack[] out = new ItemStack[] {
					ItemFluidIcon.make(recipe.getValue().getKey()),
					ItemFluidIcon.make(recipe.getValue().getValue())
			};
			
			recipes.put(ItemFluidIcon.make(recipe.getKey(), 100), out);
		}
		
		return recipes;
	}
	
	public static HashMap<Object, Object> getCrackingRecipesForNEI() {

		HashMap<Object, Object> recipes = new HashMap();
		
		for(Entry<FluidType, Pair<FluidStack, FluidStack>> recipe : cracking.entrySet()) {
			ItemStack[] in = new ItemStack[] {
					ItemFluidIcon.make(recipe.getKey(), 100),
					ItemFluidIcon.make(Fluids.STEAM, 200)
			};
			ItemStack[] out = new ItemStack[] {
					ItemFluidIcon.make(recipe.getValue().getKey()),
					ItemFluidIcon.make(recipe.getValue().getValue()),
					ItemFluidIcon.make(Fluids.SPENTSTEAM, 2)
			};
			
			recipes.put(in, recipe.getValue().getValue().type == Fluids.NONE ? new ItemStack[] {ItemFluidIcon.make(recipe.getValue().getKey()), ItemFluidIcon.make(Fluids.SPENTSTEAM, 2)} : out);
		}
		
		return recipes;
	}
}
