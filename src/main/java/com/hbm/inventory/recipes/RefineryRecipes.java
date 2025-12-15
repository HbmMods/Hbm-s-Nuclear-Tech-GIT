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

	public static final int oilds_frac_heavy = 30;
	public static final int oilds_frac_naph = 35;
	public static final int oilds_frac_light = 20;
	public static final int oilds_frac_unsat = 15;
	public static final int crackds_frac_naph = 35;
	public static final int crackds_frac_light = 35;
	public static final int crackds_frac_aroma = 15;
	public static final int crackds_frac_unsat = 15;

	private static Map<FluidType, Quintet<FluidStack, FluidStack, FluidStack, FluidStack, ItemStack>> refinery = new HashMap();
	
	public static HashMap<Object, Object[]> getRefineryRecipe() {

		HashMap<Object, Object[]> recipes = new HashMap<Object, Object[]>();
		
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
		refinery.put(Fluids.HOTOIL_DS, new Quintet(
				new FluidStack(Fluids.HEAVYOIL,		oilds_frac_heavy),
				new FluidStack(Fluids.NAPHTHA_DS,	oilds_frac_naph),
				new FluidStack(Fluids.LIGHTOIL_DS,	oilds_frac_light),
				new FluidStack(Fluids.UNSATURATEDS,	oilds_frac_unsat),
				DictFrame.fromOne(ModItems.oil_tar, EnumTarType.PARAFFIN)
				));
		refinery.put(Fluids.HOTCRACKOIL_DS, new Quintet(
				new FluidStack(Fluids.NAPHTHA_DS,		crackds_frac_naph),
				new FluidStack(Fluids.LIGHTOIL_DS,		crackds_frac_light),
				new FluidStack(Fluids.AROMATICS,		crackds_frac_aroma),
				new FluidStack(Fluids.UNSATURATEDS,		crackds_frac_unsat),
				DictFrame.fromOne(ModItems.oil_tar, EnumTarType.PARAFFIN)
				));
	}
	
	public static Quintet<FluidStack, FluidStack, FluidStack, FluidStack, ItemStack> getRefinery(FluidType oil) {
		return refinery.get(oil);
	}
}
