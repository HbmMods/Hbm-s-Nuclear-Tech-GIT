package com.hbm.inventory.recipes;

import static com.hbm.inventory.OreDictManager.*;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.NBTStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.items.BrokenItem;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;

import net.minecraft.item.ItemStack;

public class PrecAssRecipes extends GenericRecipes<GenericRecipe> {

	public static final PrecAssRecipes INSTANCE = new PrecAssRecipes();

	@Override public int inputItemLimit() { return 9; }
	@Override public int inputFluidLimit() { return 1; }
	@Override public int outputItemLimit() { return 9; }
	@Override public int outputFluidLimit() { return 1; }

	@Override public String getFileName() { return "hbmPrecisionAssembly.json"; }
	@Override public GenericRecipe instantiateRecipe(String name) { return new GenericRecipe(name); }

	@Override
	public void registerDefaults() {
		
		registerPair(new GenericRecipe("precass.controller").setup(400, 15_000L)
				.inputItems(new ComparableStack(ModItems.circuit, 32, EnumCircuitType.CHIP),
						new ComparableStack(ModItems.circuit, 32, EnumCircuitType.CAPACITOR),
						new ComparableStack(ModItems.circuit, 16, EnumCircuitType.CAPACITOR_TANTALIUM),
						new ComparableStack(ModItems.circuit, 1, EnumCircuitType.CONTROLLER_CHASSIS),
						new ComparableStack(ModItems.upgrade_speed_1),
						new OreDictStack(PB.wireFine(), 16))
				.inputFluids(new FluidStack(Fluids.PERFLUOROMETHYL, 1_000)),
				DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CONTROLLER), 10, 25);
	}
	
	public void registerPair(GenericRecipe recipe, ItemStack output, int chance, int reclaim) {
		recipe.outputItems(new ChanceOutputMulti(
				new ChanceOutput(output, chance),
				new ChanceOutput(BrokenItem.make(output), 100 - chance)
				));
		
		this.register(recipe);
		
		float fReclaim = reclaim / 100F;
		
		IOutput[] recycle = new IOutput[recipe.inputItem.length];
		for(int i = 0; i < recycle.length; i++) {
			ItemStack stack = recipe.inputItem[i].extractForNEI().get(0).copy();
			int stackSize = (int) (recipe.inputItem[i].stacksize * fReclaim);
			if(stackSize > 0) {
				stack.stackSize = stackSize;
				recycle[i] = new ChanceOutput(stack);
			} else {
				recycle[i] = new ChanceOutput(stack, fReclaim);
			}
		}
		
		FluidStack[] fluid = recipe.inputFluid != null ? new FluidStack[1] : null;
		if(fluid != null) {
			fluid[0] = new FluidStack(recipe.inputFluid[0].type, (int) Math.round(recipe.inputFluid[0].fill * fReclaim));
		}
		
		this.register(new GenericRecipe(recipe.getInternalName() + ".recycle").setup(recipe.duration, recipe.power).setNameWrapper("precass.recycle")
				.setIcon(BrokenItem.make(output))
				.inputItems(new NBTStack(BrokenItem.make(output)))
				.outputItems(recycle)
				.outputFluids(fluid));
	}
}
