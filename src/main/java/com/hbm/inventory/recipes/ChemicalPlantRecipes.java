package com.hbm.inventory.recipes;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;

public class ChemicalPlantRecipes extends GenericRecipes<GenericRecipe> {
	
	public static final ChemicalPlantRecipes INSTANCE = new ChemicalPlantRecipes();

	@Override public int inputItemLimit() { return 3; }
	@Override public int inputFluidLimit() { return 3; }
	@Override public int outputItemLimit() { return 3; }
	@Override public int outputFluidLimit() { return 3; }

	@Override public String getFileName() { return "hbmChemicalPlant.json"; }
	@Override public GenericRecipe instantiateRecipe(String name) { return new GenericRecipe(name); }

	@Override
	public void registerDefaults() {
		
		this.register(new GenericRecipe("chem.biogas").setup(60, 100).setIcon(ModItems.gas_full, Fluids.BIOGAS.getID())
				.setInputItems(new ComparableStack(ModItems.biomass, 16))
				.setInputFluids(new FluidStack(Fluids.AIR, 4000))
				.setOutputFluids(new FluidStack(Fluids.BIOGAS, 2000)));
		
		this.register(new GenericRecipe("chem.test").setup(60, 100)
				.setInputItems(new ComparableStack(ModItems.biomass, 16))
				.setInputFluids(new FluidStack(Fluids.AIR, 4000))
				.setOutputItems(
						new ChanceOutput(new ItemStack(ModItems.glyphid_meat, 16)),
						new ChanceOutput(new ItemStack(ModItems.ingot_asbestos, 1), 0.5F, 0),
						new ChanceOutputMulti() {{
							pool.add(new ChanceOutput(new ItemStack(ModItems.billet_co60), 1));
							pool.add(new ChanceOutput(new ItemStack(ModItems.billet_cobalt), 5));
						}})
				.setOutputFluids(new FluidStack(Fluids.BIOGAS, 2000)));
		
		for(NTMMaterial mat : Mats.orderedList) {
			if(mat.autogen.contains(MaterialShapes.CASTPLATE)) this.register(new GenericRecipe(mat.getUnlocalizedName() + ".plate").setup(60, 100).setOutputItems(new ChanceOutput(new ItemStack(ModItems.plate_cast, 1, mat.id))));
			if(mat.autogen.contains(MaterialShapes.WELDEDPLATE)) this.register(new GenericRecipe(mat.getUnlocalizedName() + ".weld").setup(60, 100).setOutputItems(new ChanceOutput(new ItemStack(ModItems.plate_welded, 1, mat.id))));
			if(mat.autogen.contains(MaterialShapes.DENSEWIRE)) this.register(new GenericRecipe(mat.getUnlocalizedName() + ".wire").setup(60, 100).setOutputItems(new ChanceOutput(new ItemStack(ModItems.wire_dense, 1, mat.id))));
			if(mat.autogen.contains(MaterialShapes.MECHANISM)) this.register(new GenericRecipe(mat.getUnlocalizedName() + ".mechanism").setup(60, 100).setOutputItems(new ChanceOutput(new ItemStack(ModItems.part_mechanism, 1, mat.id))));
		}
	}
}
