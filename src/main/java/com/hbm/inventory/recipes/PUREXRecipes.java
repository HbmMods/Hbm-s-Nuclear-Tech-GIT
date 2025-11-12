package com.hbm.inventory.recipes;

import static com.hbm.inventory.OreDictManager.*;

import java.util.ArrayList;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemPWRFuel.EnumPWRFuel;
import com.hbm.items.machine.ItemWatzPellet.EnumWatzType;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class PUREXRecipes extends GenericRecipes<GenericRecipe> {
	
	public static final PUREXRecipes INSTANCE = new PUREXRecipes();

	@Override public int inputItemLimit() { return 3; }
	@Override public int inputFluidLimit() { return 3; }
	@Override public int outputItemLimit() { return 6; }
	@Override public int outputFluidLimit() { return 1; }

	@Override public String getFileName() { return "hbmPUREX.json"; }
	@Override public GenericRecipe instantiateRecipe(String name) { return new GenericRecipe(name); }

	@Override
	public void registerDefaults() {

		long zirnoxPower = 1_000;
		long platePower = 1_500;
		long pwrPower = 2_500;
		long watzPower = 10_000;
		long vitrification = 1_000;

		// ZIRNOX
		String autoZirnox = "autoswitch.zirnox";
		this.register(new GenericRecipe("purex.zirnoxnu").setup(100, zirnoxPower).setNameWrapper("purex.recycle").setGroup(autoZirnox, this)
				.inputItems(new ComparableStack(ModItems.waste_natural_uranium))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_u238, 1),
						new ItemStack(ModItems.nugget_pu_mix, 2),
						new ItemStack(ModItems.nugget_pu239, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 2))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.zirnoxmeu").setup(100, zirnoxPower).setNameWrapper("purex.recycle").setGroup(autoZirnox, this)
				.inputItems(new ComparableStack(ModItems.waste_uranium))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_u238, 1),
						new ItemStack(ModItems.nugget_pu_mix, 2),
						new ItemStack(ModItems.nugget_pu239, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 2))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.zirnoxthmeu").setup(100, zirnoxPower).setNameWrapper("purex.recycle").setGroup(autoZirnox, this)
				.inputItems(new ComparableStack(ModItems.waste_thorium))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_u238, 1),
						new ItemStack(ModItems.nugget_th232, 1),
						new ItemStack(ModItems.nugget_u233, 2),
						new ItemStack(ModItems.nuclear_waste_tiny, 2))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.zirnoxmox").setup(100, zirnoxPower).setNameWrapper("purex.recycle").setGroup(autoZirnox, this)
				.inputItems(new ComparableStack(ModItems.waste_mox))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_pu_mix, 1),
						new ItemStack(ModItems.nugget_technetium, 1),
						new ItemStack(ModItems.nugget_u238, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 3))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.zirnoxmep").setup(100, zirnoxPower).setNameWrapper("purex.recycle").setGroup(autoZirnox, this)
				.inputItems(new ComparableStack(ModItems.waste_plutonium))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_pu_mix, 1),
						new ItemStack(ModItems.nugget_pu_mix, 1),
						new ItemStack(ModItems.nugget_technetium, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 3))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.zirnoxheu233").setup(100, zirnoxPower).setNameWrapper("purex.recycle").setGroup(autoZirnox, this)
				.inputItems(new ComparableStack(ModItems.waste_u233))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_u235, 1),
						new ItemStack(ModItems.nugget_neptunium, 1),
						new ItemStack(ModItems.nugget_technetium, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 3))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.zirnoxheu235").setup(100, zirnoxPower).setNameWrapper("purex.recycle").setGroup(autoZirnox, this)
				.inputItems(new ComparableStack(ModItems.waste_u235))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_pu238, 1),
						new ItemStack(ModItems.nugget_neptunium, 1),
						new ItemStack(ModItems.nugget_technetium, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 3))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.zirnoxles").setup(100, zirnoxPower).setNameWrapper("purex.recycle").setGroup(autoZirnox, this)
				.inputItems(new ComparableStack(ModItems.waste_schrabidium))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_beryllium, 2),
						new ItemStack(ModItems.nugget_pu239, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 2))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.zirnoxzfbmox").setup(100, zirnoxPower).setNameWrapper("purex.recycle").setGroup(autoZirnox, this)
				.inputItems(new ComparableStack(ModItems.waste_zfb_mox))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_zirconium, 3),
						new ItemStack(ModItems.nugget_technetium, 1),
						new ItemStack(ModItems.nugget_pu_mix, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 1))
				.setIconToFirstIngredient());

		// Plate Fuel
		String autoPlate = "autoswitch.plate";
		this.register(new GenericRecipe("purex.platemox").setup(100, platePower).setNameWrapper("purex.recycle").setGroup(autoPlate, this)
				.inputItems(new ComparableStack(ModItems.waste_plate_mox))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.powder_sr90_tiny, 1),
						new ItemStack(ModItems.nugget_pu_mix, 3),
						new ItemStack(ModItems.powder_cs137_tiny, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 4))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.platepu238be").setup(100, platePower).setNameWrapper("purex.recycle").setGroup(autoPlate, this)
				.inputItems(new ComparableStack(ModItems.waste_plate_pu238be))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_beryllium, 1),
						new ItemStack(ModItems.nugget_pu238, 1),
						new ItemStack(ModItems.powder_coal_tiny, 2),
						new ItemStack(ModItems.nugget_lead, 2))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.platepu239").setup(100, platePower).setNameWrapper("purex.recycle").setGroup(autoPlate, this)
				.inputItems(new ComparableStack(ModItems.waste_plate_pu239))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_pu240, 2),
						new ItemStack(ModItems.nugget_technetium, 1),
						new ItemStack(ModItems.powder_cs137_tiny, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 5))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.platera226be").setup(100, platePower).setNameWrapper("purex.recycle").setGroup(autoPlate, this)
				.inputItems(new ComparableStack(ModItems.waste_plate_ra226be))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_beryllium, 2),
						new ItemStack(ModItems.nugget_polonium, 2),
						new ItemStack(ModItems.powder_coal_tiny, 1),
						new ItemStack(ModItems.nugget_lead, 1))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.platesa326").setup(100, platePower).setNameWrapper("purex.recycle").setGroup(autoPlate, this)
				.inputItems(new ComparableStack(ModItems.waste_plate_sa326))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_solinium, 1),
						new ItemStack(ModItems.powder_neodymium_tiny, 1),
						new ItemStack(ModItems.nugget_tantalium, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 6))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.plateu233").setup(100, platePower).setNameWrapper("purex.recycle").setGroup(autoPlate, this)
				.inputItems(new ComparableStack(ModItems.waste_plate_u233))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_u235, 1),
						new ItemStack(ModItems.powder_i131_tiny, 1),
						new ItemStack(ModItems.powder_sr90_tiny, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 6))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.plateu235").setup(100, platePower).setNameWrapper("purex.recycle").setGroup(autoPlate, this)
				.inputItems(new ComparableStack(ModItems.waste_plate_u235))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_neptunium, 1),
						new ItemStack(ModItems.nugget_pu238, 1),
						new ItemStack(ModItems.nugget_technetium, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 6))
				.setIconToFirstIngredient());
		
		// PWR
		String autoPWR = "autoswitch.pwr";
		this.register(new GenericRecipe("purex.pwrmeu").setup(100, pwrPower).setNameWrapper("purex.recycle").setGroup(autoPWR, this)
				.inputItems(new ComparableStack(ModItems.pwr_fuel_depleted, 1, EnumPWRFuel.MEU))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_u238, 3),
						new ItemStack(ModItems.nugget_plutonium, 4),
						new ItemStack(ModItems.nugget_technetium, 2),
						new ItemStack(ModItems.nuclear_waste_tiny, 3))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.pwrheu233").setup(100, pwrPower).setNameWrapper("purex.recycle").setGroup(autoPWR, this)
				.inputItems(new ComparableStack(ModItems.pwr_fuel_depleted, 1, EnumPWRFuel.HEU233))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_u235, 3),
						new ItemStack(ModItems.nugget_pu238, 3),
						new ItemStack(ModItems.nugget_technetium, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 5))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.pwrheu235").setup(100, pwrPower).setNameWrapper("purex.recycle").setGroup(autoPWR, this)
				.inputItems(new ComparableStack(ModItems.pwr_fuel_depleted, 1, EnumPWRFuel.HEU235))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_neptunium, 3),
						new ItemStack(ModItems.nugget_pu238, 3),
						new ItemStack(ModItems.nugget_technetium, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 5))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.pwrmen").setup(100, pwrPower).setNameWrapper("purex.recycle").setGroup(autoPWR, this)
				.inputItems(new ComparableStack(ModItems.pwr_fuel_depleted, 1, EnumPWRFuel.MEN))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_u238, 3),
						new ItemStack(ModItems.nugget_pu239, 4),
						new ItemStack(ModItems.nugget_technetium, 2),
						new ItemStack(ModItems.nuclear_waste_tiny, 3))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.pwrhen237").setup(100, pwrPower).setNameWrapper("purex.recycle").setGroup(autoPWR, this)
				.inputItems(new ComparableStack(ModItems.pwr_fuel_depleted, 1, EnumPWRFuel.HEN237))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_pu238, 2),
						new ItemStack(ModItems.nugget_pu239, 4),
						new ItemStack(ModItems.nugget_technetium, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 5))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.pwrmox").setup(100, pwrPower).setNameWrapper("purex.recycle").setGroup(autoPWR, this)
				.inputItems(new ComparableStack(ModItems.pwr_fuel_depleted, 1, EnumPWRFuel.MOX))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_u238, 3),
						new ItemStack(ModItems.nugget_pu240, 4),
						new ItemStack(ModItems.nugget_technetium, 2),
						new ItemStack(ModItems.nuclear_waste_tiny, 3))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.pwrmep").setup(100, pwrPower).setNameWrapper("purex.recycle").setGroup(autoPWR, this)
				.inputItems(new ComparableStack(ModItems.pwr_fuel_depleted, 1, EnumPWRFuel.MEP))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_lead, 2),
						new ItemStack(ModItems.nugget_pu_mix, 4),
						new ItemStack(ModItems.nugget_technetium, 2),
						new ItemStack(ModItems.nuclear_waste_tiny, 3))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.pwrhep239").setup(100, pwrPower).setNameWrapper("purex.recycle").setGroup(autoPWR, this)
				.inputItems(new ComparableStack(ModItems.pwr_fuel_depleted, 1, EnumPWRFuel.HEP239))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_pu_mix, 2),
						new ItemStack(ModItems.nugget_pu240, 4),
						new ItemStack(ModItems.nugget_technetium, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 5))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.pwrhep241").setup(100, pwrPower).setNameWrapper("purex.recycle").setGroup(autoPWR, this)
				.inputItems(new ComparableStack(ModItems.pwr_fuel_depleted, 1, EnumPWRFuel.HEP241))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_lead, 3),
						new ItemStack(ModItems.nugget_zirconium, 2),
						new ItemStack(ModItems.nugget_technetium, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 6))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.pwrmea").setup(100, pwrPower).setNameWrapper("purex.recycle").setGroup(autoPWR, this)
				.inputItems(new ComparableStack(ModItems.pwr_fuel_depleted, 1, EnumPWRFuel.MEA))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_lead, 3),
						new ItemStack(ModItems.nugget_zirconium, 2),
						new ItemStack(ModItems.nugget_technetium, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 6))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.pwrhea242").setup(100, pwrPower).setNameWrapper("purex.recycle").setGroup(autoPWR, this)
				.inputItems(new ComparableStack(ModItems.pwr_fuel_depleted, 1, EnumPWRFuel.HEA242))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_lead, 3),
						new ItemStack(ModItems.nugget_zirconium, 2),
						new ItemStack(ModItems.nugget_technetium, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 6))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.pwrhes326").setup(100, pwrPower).setNameWrapper("purex.recycle").setGroup(autoPWR, this)
				.inputItems(new ComparableStack(ModItems.pwr_fuel_depleted, 1, EnumPWRFuel.HES326))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_solinium, 3),
						new ItemStack(ModItems.nugget_lead, 2),
						new ItemStack(ModItems.nugget_euphemium, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 6))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.pwrhes327").setup(100, pwrPower).setNameWrapper("purex.recycle").setGroup(autoPWR, this)
				.inputItems(new ComparableStack(ModItems.pwr_fuel_depleted, 1, EnumPWRFuel.HES327))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_australium, 4),
						new ItemStack(ModItems.nugget_lead, 1),
						new ItemStack(ModItems.nugget_euphemium, 1),
						new ItemStack(ModItems.nuclear_waste_tiny, 6))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.pwrbfbam").setup(100, pwrPower).setNameWrapper("purex.recycle").setGroup(autoPWR, this)
				.inputItems(new ComparableStack(ModItems.pwr_fuel_depleted, 1, EnumPWRFuel.BFB_AM_MIX))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_am_mix, 9),
						new ItemStack(ModItems.nugget_pu_mix, 2),
						new ItemStack(ModItems.nugget_bismuth, 6),
						new ItemStack(ModItems.nuclear_waste_tiny, 1))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.pwrbfpu241").setup(100, pwrPower).setNameWrapper("purex.recycle").setGroup(autoPWR, this)
				.inputItems(new ComparableStack(ModItems.pwr_fuel_depleted, 1, EnumPWRFuel.BFB_PU241))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_pu241, 9),
						new ItemStack(ModItems.nugget_pu_mix, 2),
						new ItemStack(ModItems.nugget_bismuth, 6),
						new ItemStack(ModItems.nuclear_waste_tiny, 1))
				.setIconToFirstIngredient());

		// Molten Salt
		this.register(new GenericRecipe("purex.thoriumsalt").setup(100, 10_000).setIcon(ModItems.fluid_icon, Fluids.THORIUM_SALT.getID())
				.inputFluids(new FluidStack(Fluids.THORIUM_SALT_DEPLETED, 16_000))
				.inputItems(new OreDictStack(TH232.nugget(), 2))
				.outputFluids(new FluidStack(Fluids.THORIUM_SALT, 16_000))
				.outputItems(
						new ChanceOutput(new ItemStack(ModItems.nugget_u233, 1), 0.5F),
						new ChanceOutput(new ItemStack(ModItems.nuclear_waste_tiny, 1), 0.25F)));
		
		// Watz
		String autoWatz = "autoswitch.watz";
		this.register(new GenericRecipe("purex.watzschrab").setup(60, watzPower).setNameWrapper("purex.recycle").setGroup(autoWatz, this)
				.inputItems(new ComparableStack(ModItems.watz_pellet_depleted, 1, EnumWatzType.SCHRABIDIUM))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_solinium, 15),
						new ItemStack(ModItems.nugget_euphemium, 3),
						new ItemStack(ModItems.nuclear_waste, 2))
				.outputFluids(new FluidStack(Fluids.WATZ, 1_000))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.watzhes").setup(60, watzPower).setNameWrapper("purex.recycle").setGroup(autoWatz, this)
				.inputItems(new ComparableStack(ModItems.watz_pellet_depleted, 1, EnumWatzType.HES))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_solinium, 17),
						new ItemStack(ModItems.nugget_euphemium, 1),
						new ItemStack(ModItems.nuclear_waste, 2))
				.outputFluids(new FluidStack(Fluids.WATZ, 1_000))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.watzmes").setup(60, watzPower).setNameWrapper("purex.recycle").setGroup(autoWatz, this)
				.inputItems(new ComparableStack(ModItems.watz_pellet_depleted, 1, EnumWatzType.MES))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_solinium, 12),
						new ItemStack(ModItems.nugget_tantalium, 6),
						new ItemStack(ModItems.nuclear_waste, 2))
				.outputFluids(new FluidStack(Fluids.WATZ, 1_000))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.watzles").setup(60, watzPower).setNameWrapper("purex.recycle").setGroup(autoWatz, this)
				.inputItems(new ComparableStack(ModItems.watz_pellet_depleted, 1, EnumWatzType.LES))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_solinium, 9),
						new ItemStack(ModItems.nugget_tantalium, 9),
						new ItemStack(ModItems.nuclear_waste, 2))
				.outputFluids(new FluidStack(Fluids.WATZ, 1_000))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.watzhen").setup(60, watzPower).setNameWrapper("purex.recycle").setGroup(autoWatz, this)
				.inputItems(new ComparableStack(ModItems.watz_pellet_depleted, 1, EnumWatzType.HEN))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_pu239, 12),
						new ItemStack(ModItems.nugget_technetium, 6),
						new ItemStack(ModItems.nuclear_waste, 2))
				.outputFluids(new FluidStack(Fluids.WATZ, 1_000))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.watzmeu").setup(60, watzPower).setNameWrapper("purex.recycle").setGroup(autoWatz, this)
				.inputItems(new ComparableStack(ModItems.watz_pellet_depleted, 1, EnumWatzType.MEU))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_pu239, 12),
						new ItemStack(ModItems.nugget_bismuth, 6),
						new ItemStack(ModItems.nuclear_waste, 2))
				.outputFluids(new FluidStack(Fluids.WATZ, 1_000))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.watzmep").setup(60, watzPower).setNameWrapper("purex.recycle").setGroup(autoWatz, this)
				.inputItems(new ComparableStack(ModItems.watz_pellet_depleted, 1, EnumWatzType.MEP))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_pu241, 12),
						new ItemStack(ModItems.nugget_bismuth, 6),
						new ItemStack(ModItems.nuclear_waste, 2))
				.outputFluids(new FluidStack(Fluids.WATZ, 1_000))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.watzlead").setup(60, watzPower).setNameWrapper("purex.recycle").setGroup(autoWatz, this)
				.inputItems(new ComparableStack(ModItems.watz_pellet_depleted, 1, EnumWatzType.LEAD))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_lead, 6),
						new ItemStack(ModItems.nugget_bismuth, 12),
						new ItemStack(ModItems.nuclear_waste, 2))
				.outputFluids(new FluidStack(Fluids.WATZ, 1_000))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.watzboron").setup(60, watzPower).setNameWrapper("purex.recycle").setGroup(autoWatz, this)
				.inputItems(new ComparableStack(ModItems.watz_pellet_depleted, 1, EnumWatzType.BORON))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.powder_coal_tiny, 12),
						new ItemStack(ModItems.nugget_co60, 6),
						new ItemStack(ModItems.nuclear_waste, 2))
				.outputFluids(new FluidStack(Fluids.WATZ, 1_000))
				.setIconToFirstIngredient());

		this.register(new GenericRecipe("purex.watzdu").setup(60, watzPower).setNameWrapper("purex.recycle").setGroup(autoWatz, this)
				.inputItems(new ComparableStack(ModItems.watz_pellet_depleted, 1, EnumWatzType.DU))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
				.outputItems(new ItemStack(ModItems.nugget_polonium, 12),
						new ItemStack(ModItems.nugget_pu238, 6),
						new ItemStack(ModItems.nuclear_waste, 2))
				.outputFluids(new FluidStack(Fluids.WATZ, 1_000))
				.setIconToFirstIngredient());

		ArrayList<ItemStack> naquadriaNuggets = OreDictionary.getOres("nuggetNaquadria");
		if(naquadriaNuggets.size() != 0) {
			ItemStack nuggetNQR = naquadriaNuggets.get(0);
			ItemStack copy = nuggetNQR.copy();
			copy.stackSize = 12;

			this.register(new GenericRecipe("purex.watznaqadah").setup(60, watzPower).setNameWrapper("purex.recycle").setGroup(autoWatz, this)
					.inputItems(new ComparableStack(ModItems.watz_pellet_depleted, 1, EnumWatzType.NQD))
					.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
					.outputItems(copy,
							new ItemStack(ModItems.nugget_euphemium, 6),
							new ItemStack(ModItems.nuclear_waste, 2))
					.outputFluids(new FluidStack(Fluids.WATZ, 1_000))
					.setIconToFirstIngredient());

			this.register(new GenericRecipe("purex.watznaqadria").setup(60, watzPower).setNameWrapper("purex.recycle").setGroup(autoWatz, this)
					.inputItems(new ComparableStack(ModItems.watz_pellet_depleted, 1, EnumWatzType.NQR))
					.inputFluids(new FluidStack(Fluids.KEROSENE, 500), new FluidStack(Fluids.NITRIC_ACID, 250))
					.outputItems(new ItemStack(ModItems.nugget_co60, 12),
							new ItemStack(ModItems.nugget_euphemium, 6),
							new ItemStack(ModItems.nuclear_waste, 2))
					.outputFluids(new FluidStack(Fluids.WATZ, 1_000))
					.setIconToFirstIngredient());
		}
		
		//ICF
		this.register(new GenericRecipe("purex.icf").setup(300, 10_000).setNameWrapper("purex.recycle")
				.inputItems(new ComparableStack(ModItems.icf_pellet_depleted))
				.outputItems(new ItemStack(ModItems.icf_pellet_empty, 1),
						new ItemStack(ModItems.pellet_charged, 1),
						new ItemStack(ModItems.pellet_charged, 1),
						new ItemStack(ModItems.powder_iron, 1))
				.setIconToFirstIngredient());

		/// Vitrification
		this.register(new GenericRecipe("purex.vitliquid").setup(100, vitrification)
				.inputItems(new ComparableStack(ModBlocks.sand_lead))
				.inputFluids(new FluidStack(Fluids.WASTEFLUID, 1_000))
				.outputItems(new ItemStack(ModItems.nuclear_waste_vitrified)));
		
		this.register(new GenericRecipe("purex.vitgaseous").setup(100, vitrification)
				.inputItems(new ComparableStack(ModBlocks.sand_lead))
				.inputFluids(new FluidStack(Fluids.WASTEGAS, 1_000))
				.outputItems(new ItemStack(ModItems.nuclear_waste_vitrified)));
		
		this.register(new GenericRecipe("purex.vitsolid").setup(300, vitrification)
				.inputItems(new ComparableStack(ModBlocks.sand_lead), new ComparableStack(ModItems.nuclear_waste, 4))
				.outputItems(new ItemStack(ModItems.nuclear_waste_vitrified, 4)));
		
		// Schrabidium
		this.register(new GenericRecipe("purex.schraranium").setup(200, 1_000).setNameWrapper("purex.schrab")
				.inputItems(new ComparableStack(ModItems.ingot_schraranium))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 2_000), new FluidStack(Fluids.NITRIC_ACID, 1_000))
				.outputItems(new ItemStack(ModItems.nugget_schrabidium, 3),
						new ItemStack(ModItems.nugget_uranium, 3),
						new ItemStack(ModItems.nugget_neptunium, 2))
				.setIconToFirstIngredient());

		String autoSchrab = "autoswitch.schrab";
		this.register(new GenericRecipe("purex.schrabzirnox").setup(200, 50_000).setNameWrapper("purex.schrab").setGroup(autoSchrab, this)
				.inputItems(new ComparableStack(ModItems.waste_plutonium))
				.inputFluids(new FluidStack(Fluids.SOLVENT, 4_000), new FluidStack(Fluids.SCHRABIDIC, 500))
				.outputItems(new ItemStack(ModItems.powder_schrabidium, 1),
						new ItemStack(ModItems.nugget_technetium, 3),
						new ItemStack(ModItems.nuclear_waste_tiny, 4))
				.setIconToFirstIngredient());
		this.register(new GenericRecipe("purex.schrabpwr").setup(200, 50_000).setNameWrapper("purex.schrab").setGroup(autoSchrab, this)
				.inputItems(new ComparableStack(ModItems.pwr_fuel_depleted, 1, EnumPWRFuel.MEP))
				.inputFluids(new FluidStack(Fluids.SOLVENT, 4_000), new FluidStack(Fluids.SCHRABIDIC, 500))
				.outputItems(new ItemStack(ModItems.powder_schrabidium, 1),
						new ItemStack(ModItems.nugget_technetium, 3),
						new ItemStack(ModItems.nuclear_waste_tiny, 4))
				.setIconToFirstIngredient());
		this.register(new GenericRecipe("purex.schrabmen").setup(200, 50_000).setNameWrapper("purex.schrab").setGroup(autoSchrab, this)
				.inputItems(new ComparableStack(ModItems.pwr_fuel_depleted, 1, EnumPWRFuel.MEN))
				.inputFluids(new FluidStack(Fluids.SOLVENT, 4_000), new FluidStack(Fluids.SCHRABIDIC, 500))
				.outputItems(new ItemStack(ModItems.powder_schrabidium, 1),
						new ItemStack(ModItems.nugget_technetium, 3),
						new ItemStack(ModItems.nuclear_waste_tiny, 4))
				.setIconToFirstIngredient());
	}
}
