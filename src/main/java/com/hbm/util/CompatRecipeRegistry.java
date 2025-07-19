package com.hbm.util;

import java.util.Arrays;

import com.hbm.interfaces.Untested;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.*;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.*;
import com.hbm.inventory.recipes.AmmoPressRecipes.AmmoPressRecipe;
import com.hbm.inventory.recipes.ArcFurnaceRecipes.ArcFurnaceRecipe;
import com.hbm.inventory.recipes.ArcWelderRecipes.ArcWelderRecipe;
import com.hbm.inventory.recipes.BreederRecipes.BreederRecipe;
import com.hbm.inventory.recipes.CrucibleRecipes.CrucibleRecipe;
import com.hbm.inventory.recipes.CrystallizerRecipes.CrystallizerRecipe;
import com.hbm.inventory.recipes.ElectrolyserFluidRecipes.ElectrolysisRecipe;
import com.hbm.inventory.recipes.ElectrolyserMetalRecipes.ElectrolysisMetalRecipe;
import com.hbm.inventory.recipes.ExposureChamberRecipes.ExposureChamberRecipe;
import com.hbm.inventory.recipes.ParticleAcceleratorRecipes.ParticleAcceleratorRecipe;
import com.hbm.inventory.recipes.PedestalRecipes.PedestalExtraCondition;
import com.hbm.inventory.recipes.PedestalRecipes.PedestalRecipe;
import com.hbm.inventory.recipes.PyroOvenRecipes.PyroOvenRecipe;
import com.hbm.inventory.recipes.RotaryFurnaceRecipes.RotaryFurnaceRecipe;
import com.hbm.inventory.recipes.ChemplantRecipes.ChemRecipe;
import com.hbm.inventory.recipes.CompressorRecipes.CompressorRecipe;
import com.hbm.inventory.recipes.SolderingRecipes.SolderingRecipe;
import com.hbm.inventory.recipes.anvil.AnvilRecipes;
import com.hbm.inventory.recipes.anvil.AnvilRecipes.AnvilConstructionRecipe;
import com.hbm.inventory.recipes.anvil.AnvilRecipes.AnvilOutput;
import com.hbm.inventory.recipes.anvil.AnvilRecipes.OverlayType;
import com.hbm.items.machine.ItemStamp.StampType;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.Tuple.Triplet;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Public methods for registering recipes. Method signature should never change, only the body to reflect any changes to recipes/machines themselves.
 * Recipe creation is either 1:1 with the original SerializableRecipe or even simpler (in the case of the compressor, two FluidStacks instead of a ton of loose numbers).
 * Call these with a registered IRecipeRegisterListener, otherwise the recipe loading/reloading will break the custom recipes.
 * @author hbm
 */
@Untested
public class CompatRecipeRegistry {

	public static void registerPress(StampType stamp, AStack input, ItemStack output) {
		PressRecipes.recipes.put(new Pair(input, stamp), output);
	}

	/** Same loose rules as BlastFurnaceRecipes, valid inputs are Items, Blocks, ItemStacks, ComparableStacks, Strings (for oredict) and DictFrames */
	public static void registerBlastFurnace(Object[] inputs, ItemStack output) {
		if(inputs.length != 2) return;
		BlastFurnaceRecipes.addRecipe(inputs[0], inputs[1], output);
	}

	public static void registerShredder(AStack input, ItemStack output) {
		for(ItemStack allItems : input.extractForNEI()) {
			ComparableStack comp = new ComparableStack(allItems);
			ShredderRecipes.shredderRecipes.put(comp, output);
			if (ShredderRecipes.neiShredderRecipes != null)
				ShredderRecipes.neiShredderRecipes.put(comp, output);
		}
	}

	/** Items should strictly be categorized as pcb, topping or solder. An item that is used as a topping in one recipe should not be a pcb in another.
	 * This is because the soldering station's item IO will automatically place items based on this category, and having items in more than one category would break it. */
	public static void registerSoldering(ItemStack output, int time, long power, FluidStack fluid, AStack[] toppings, AStack[] pcb, AStack[] solder) {
		SolderingRecipes.recipes.add(new SolderingRecipe(output, time, power, fluid, copyFirst(toppings, 3), copyFirst(pcb, 2), copyFirst(solder, 1)));
	}

	/** Chemplant recipes need unique IDs, game will crash when an ID collision is detected! */
	public static void registerChemplant(int id, String name, int duration, AStack[] inputItems, FluidStack[] inputFluids, ItemStack[] outputItems, FluidStack[] outputFluids) {
		ChemRecipe recipe = new ChemRecipe(id, name, duration);
		if(inputItems != null) recipe.inputItems(copyFirst(inputItems, 4));
		if(inputFluids != null) recipe.inputFluids(copyFirst(inputFluids, 2));
		if(outputItems != null) recipe.outputItems(copyFirst(outputItems, 4));
		if(outputFluids != null) recipe.outputFluids(copyFirst(outputFluids, 2));
		ChemplantRecipes.recipes.add(recipe);
	}

	/** Either solid or liquid output can be null */
	public static void registerCombination(AStack input, ItemStack output, FluidStack fluid) {
		if(output == null && fluid == null) return;
		Object o = input instanceof OreDictStack ? ((OreDictStack) input).name : input;
		CombinationRecipes.recipes.put(o, new Pair(output, fluid));
	}

	/** Crucible recipes need unique IDs, game will crash when an ID collision is detected! */
	public static void registerCrucible(int index, String name, int frequency, ItemStack icon, MaterialStack[] input, MaterialStack[] output) {
		CrucibleRecipe recipe = new CrucibleRecipe(index, name, frequency, icon).inputs(input).outputs(output);
		CrucibleRecipes.recipes.add(recipe);
	}

	public static void registerCentrifuge(AStack input, ItemStack[] outputs) {
		CentrifugeRecipes.recipes.put(input, copyFirst(outputs, 4));
	}

	public static void registerCrystallizer(AStack input, ItemStack output, int time, float productivity, FluidStack fluid) {
		CrystallizerRecipe recipe = new CrystallizerRecipe(output, time).prod(productivity);
		CrystallizerRecipes.registerRecipe(input instanceof OreDictStack ? ((OreDictStack) input).name : input, recipe, fluid);
	}

	/** Fractions always use 100mB of input fluid per operation. None of the outputs can be null. */
	public static void registerFraction(FluidType input, FluidStack[] output) {
		if(output.length != 2) return;
		FractionRecipes.fractions.put(input, new Pair(output[0], output[1]));
	}

	/** Cracking always uses 100mB of input fluid and 200mB of steam per operation. None of the outputs can be null. */
	public static void registerCracking(FluidType input, FluidStack[] output) {
		if(output.length != 2) return;
		CrackingRecipes.cracking.put(input, new Pair(output[0], output[1]));
	}

	/** Reforming always uses 100mB of input fluid per operation. None of the outputs can be null. */
	public static void registerReforming(FluidType input, FluidStack[] output) {
		output = copyFirst(output, 3);
		if(output.length < 3) return;
		ReformingRecipes.recipes.put(input, new Triplet(output[0], output[1], output[2]));
	}

	/** Hydrotreating always uses 100mB of input fluid per operation. None of the outputs can be null. */
	public static void registerHydrotreating(FluidType input, FluidStack hydrogen, FluidStack[] output) {
		output = copyFirst(output, 2);
		if(output.length < 2) return;
		HydrotreatingRecipes.recipes.put(input, new Triplet(hydrogen, output[0], output[1]));
	}

	public static void registerLiquefaction(AStack input, FluidStack output) {
		LiquefactionRecipes.recipes.put(input instanceof OreDictStack ? ((OreDictStack) input).name : input, output);
	}

	public static void registerSolidifying(FluidStack input, ItemStack output) {
		SolidificationRecipes.recipes.put(input.type, new Pair(input.fill, output));
	}

	public static void registerCoker(FluidStack input, ItemStack output, FluidStack fluid) {
		CokerRecipes.recipes.put(input.type, new Triplet(input.fill, output, fluid));
	}

	/** Registers a coker recipe based on the standardized fluid to coke values */
	public static void registerCokerAuto(FluidType input, FluidType output) {
		CokerRecipes.registerAuto(input, output);
	}

	public static void registerPyro(FluidStack inputFluid, AStack inputItem, FluidStack outputFluid, ItemStack outputItem, int duration) {
		PyroOvenRecipes.recipes.add(new PyroOvenRecipe(duration).in(inputFluid).in(inputItem).out(outputFluid).out(outputItem));
	}

	/** Registers a pyro oven recipe based on the standardized fluid to solid fuel values */
	public static void registerPyroAuto(FluidType input) {
		PyroOvenRecipes.registerSFAuto(input);
	}

	/** Breeding reactor does not handle OreDictStacks */
	public static void registerBreeder(ComparableStack input, ItemStack output, int flux) {
		BreederRecipes.recipes.put(input, new BreederRecipe(output, flux));
	}

	public static void registerCyclotron(ComparableStack box, AStack target, ItemStack output, int antimatter) {
		CyclotronRecipes.recipes.put(new Pair(box, target), new Pair(output, antimatter));
	}

	/** Fuel pools do not handle OreDictStacks */
	public static void registerFuelPool(ComparableStack input, ItemStack output) {
		FuelPoolRecipes.recipes.put(input, output);
	}

	//TBI mixer

	public static void registerOutgasser(AStack input, ItemStack output, FluidStack fluid) {
		OutgasserRecipes.recipes.put(input, new Pair(output, fluid));
	}

	public static void registerCompressor(FluidStack input, FluidStack output, int time) {
		CompressorRecipes.recipes.put(new Pair(input.type, input.pressure), new CompressorRecipe(input.fill, output, time));
	}

	/** Byproduct array can be null, fluid output length must be 2 */
	public static void registerElectrolyzerFluid(FluidStack input, FluidStack[] output, ItemStack[] byproduct, int time) {
		output = copyFirst(output, 2);
		if(output.length < 2) return;
		if(byproduct != null) byproduct = copyFirst(byproduct, 3);

		ElectrolyserFluidRecipes.recipes.put(input.type, new ElectrolysisRecipe(input.fill, output[0], output[1], time, byproduct));
	}

	/** Output array length must be 2, outputs can be null. Byproduct array can be null. */
	public static void registerElectrolyzerMetal(AStack input, MaterialStack[] output, ItemStack[] byproduct, int time) {
		output = copyFirst(output, 2);
		if(byproduct != null) byproduct = copyFirst(byproduct, 6);

		ElectrolyserMetalRecipes.recipes.put(input, new ElectrolysisMetalRecipe(output[0], output[1], time, byproduct));
	}

	public static void registerArcWelder(ItemStack output, int time, long power, FluidStack fluid, AStack[] inputs) {
		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(output, time, power, fluid, copyFirst(inputs, 3)));
	}

	public static void registerRotaryFurnace(MaterialStack output, int time, int steam, FluidStack fluid, AStack[] inputs) {
		RotaryFurnaceRecipes.recipes.add(new RotaryFurnaceRecipe(output, time, steam, fluid, copyFirst(inputs, 3)));
	}

	/** Particles will always perform 8 recipes */
	public static void registerExposureChamber(AStack particle, AStack input, ItemStack output) {
		ExposureChamberRecipes.recipes.add(new ExposureChamberRecipe(particle, input, output));
	}

	/** Input needs two AStacks, output can take 1-2 ItemStacks. If the same recipe with different
	 * momentum should yield different results, register the lower momentum recipes first. */
	public static void registerParticleAccelerator(AStack[] input, int momentum, ItemStack[] output) {
		input = copyFirst(input, 2);
		if(input.length < 2) return;
		output = copyFirst(output, 2);
		if(output.length < 1) return;
		ParticleAcceleratorRecipes.recipes.add(new ParticleAcceleratorRecipe(input[0], input[1], momentum, output[0], output.length > 1 ? output[1] : null));
	}

	public static void registerAmmoPress(ItemStack output, AStack[] input) {
		if(input.length != 9) return;
		AmmoPressRecipes.recipes.add(new AmmoPressRecipe(output, input));
	}

	public static void registerAssembler(ItemStack output, AStack[] input, int time) {
		AssemblerRecipes.makeRecipe(new ComparableStack(output), copyFirst(input, 12), time);
	}

	/** Registers an assembler recipe but with the template only being obtainable via the specified folders */
	public static void registerAssembler(ItemStack output, AStack[] input, int time, Item... folder) {
		AssemblerRecipes.makeRecipe(new ComparableStack(output), copyFirst(input, 12), time, folder);
	}

	public static void registerAnvilConstruction(AStack[] input, AnvilOutput[] output, int tier, int overlayIndex) {
		AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(input, output).setTier(tier).setOverlay(EnumUtil.grabEnumSafely(OverlayType.class, overlayIndex)));
	}

	public static void registerAnvilConstruction(AStack[] input, AnvilOutput[] output, int tierLower, int tierUpper, int overlayIndex) {
		AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(input, output).setTierRange(tierLower, tierUpper).setOverlay(EnumUtil.grabEnumSafely(OverlayType.class, overlayIndex)));
	}

	public static void registerPedestal(ItemStack output, AStack[] input) {
		registerPedestal(output, input, 0);
	}

	public static void registerPedestal(ItemStack output, AStack[] input, int condition) {
		input = copyFirst(input, 9);
		if(input.length < 9) return;
		PedestalRecipes.recipes.add(new PedestalRecipe(output, input).extra(EnumUtil.grabEnumSafely(PedestalExtraCondition.class, condition)));
	}

	/** Either output or fluid can be null */
	public static void registerArcFurnace(AStack input, ItemStack output, MaterialStack fluid) {
		if(output == null && fluid == null) return;
		ArcFurnaceRecipes.recipeList.add(new Pair(input, new ArcFurnaceRecipe().solid(output).fluid(fluid)));
	}

	/////////////////////////////////////////////////////////////////////////////////////////

	/** If the supplied array exceeds the specified length, creates a copy and trunkates the array. Otherwise, returns the original array */
	private static <T> T[] copyFirst(T[] array, int amount) {
		if(array.length <= amount) return array;
		return Arrays.copyOf(array, amount);
	}
}
