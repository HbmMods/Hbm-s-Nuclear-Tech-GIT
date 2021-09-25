package com.hbm.inventory;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.annotations.Beta;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.Untested;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemChemistryTemplate;
import com.hbm.items.machine.ItemChemistryTemplate.EnumChemistryTemplate;
import com.hbm.lib.Library;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
@Untested
@Beta
public class ChemPlantRecipesNT
{
	public static HashMap<String, ChemPlantRecipe> recipes = new HashMap<>();
	
	public static void registerChemPlantRecipes()
	{
		recipes.put("CONCRETE", new ChemPlantRecipe()
				.setFluidInput(new FluidStack(2000, FluidType.WATER), null)
				.setItemInput(new ItemStack(Blocks.sand, 8), new ItemStack(Blocks.gravel, 8))
				.setItemOutput(new ItemStack(ModBlocks.concrete_smooth, 4), new ItemStack(ModBlocks.concrete_smooth, 4), new ItemStack(ModBlocks.concrete_smooth, 4), new ItemStack(ModBlocks.concrete_smooth, 4)));
		recipes.put("LF_BASE", new ChemPlantRecipe(400)
				.setFluidInput(new FluidStack(8000, FluidType.COOLANT), new FluidStack(1200, FluidType.OXYGEN))
				.setItemInput(new ItemStack(ModItems.powder_aluminium, 2), new ItemStack(ModItems.powder_bromine), new ItemStack(ModItems.powder_lithium, 3), new ItemStack(ModItems.fluorite, 3))
				.setFluidOutput(new FluidStack(2000, FluidType.SALT), null));
		recipes.put("LF_U", new ChemPlantRecipe(200)
				.setFluidInput(new FluidStack(1000, FluidType.SALT), null)
				.setItemInput(new ItemStack(ModItems.ingot_u235), new ItemStack(ModItems.nugget_beryllium, 3))
				.setFluidOutput(new FluidStack(1000, FluidType.SALT_U), null));
		recipes.put("LF_SA", new ChemPlantRecipe(300)
				.setFluidInput(new FluidStack(1000, FluidType.SAS3), new FluidStack(100, FluidType.MERCURY))
				.setItemInput(new ItemStack(ModItems.powder_nitan_mix, 2), new ItemStack(ModItems.billet_beryllium))
				.setFluidOutput(new FluidStack(1000, FluidType.SAS3_NIT), null));
		recipes.put("RC_U", new ChemPlantRecipe(200)
				.setFluidInput(new FluidStack(1000, FluidType.SALT_U_DP), null)
				.setFluidOutput(new FluidStack(1000, FluidType.SALT), null)
				.setItemOutput(new ItemStack(ModItems.nugget_u235), new ItemStack(ModItems.nuclear_waste_tiny, 8)));
		recipes.put("ALCOHOL", new ChemPlantRecipe(200)
				.setFluidInput(new FluidStack(500, FluidType.PETROLEUM), new FluidStack(2000, FluidType.WATER))
				.setItemInput(new ItemStack(ModItems.sulfur, 2))
				.setFluidOutput(new FluidStack(1000, FluidType.ALCOHOL), null));
		recipes.put("SARIN", new ChemPlantRecipe(1200)
				.setFluidInput(new FluidStack(2500, FluidType.ALCOHOL), new FluidStack(4000, FluidType.ACID))
				.setItemInput(new ItemStack(ModItems.powder_fire, 4), new ItemStack(ModItems.fluorite, 4))
				.setFluidOutput(new FluidStack(1000, FluidType.SARIN), null));
		for (EnumChemistryTemplate chem : EnumChemistryTemplate.values())
		{
			if (recipes.containsKey(chem.toString()))
				continue;
			else
			{
				ItemStack pseudoStack = new ItemStack(ModItems.chemistry_template, 1, chem.ordinal());
				FluidStack[] fIn = MachineRecipes.getFluidInputFromTempate(pseudoStack);
				FluidStack[] fOut = MachineRecipes.getFluidOutputFromTempate(pseudoStack);
				ItemStack[] iIn = new ItemStack[4];
				List<ItemStack> toIn = MachineRecipes.getChemInputFromTempate(pseudoStack);
				if (toIn != null)
					for (int i = 0; i < toIn.size(); i++)
						iIn[i] = Library.carefulCopy(toIn.get(i));
				recipes.put(chem.toString(), new ChemPlantRecipe(ItemChemistryTemplate.getProcessTime(pseudoStack))
						.setItemInput(iIn)
						.setItemOutput(MachineRecipes.getChemOutputFromTempate(pseudoStack))
						.setFluidInput(fIn[0], fIn[1])
						.setFluidOutput(fOut[0], fOut[1]));
			}
		}
	}
	
	@Untested
	@Beta
	public static class ChemPlantRecipe
	{
		public static final ChemPlantRecipe NONE = new ChemPlantRecipe().setFluidInput(new FluidStack(0, FluidType.NONE), new FluidStack(0, FluidType.NONE)).setItemInput(Library.filledArray(ItemStack.class, new ItemStack(ModItems.nothing), 4)).setFluidOutput(new FluidStack(0, FluidType.NONE), new FluidStack(0, FluidType.NONE)).setItemOutput(Library.filledArray(ItemStack.class, new ItemStack(ModItems.nothing), 4));
		public int time = 100;
		public FluidStack[] fIn = new FluidStack[2];
		public ItemStack[] inputs = new ItemStack[4];
		
		public FluidStack[] fOut = new FluidStack[2];
		public ItemStack[] outputs = new ItemStack[4];
		public ChemPlantRecipe()
		{
		}
		public ChemPlantRecipe(int dura)
		{
			time = dura;
		}
		public ChemPlantRecipe setFluidInput(FluidStack f1, @Nullable FluidStack f2)
		{
			fIn[0] = f1;
			fIn[1] = f2;
			return this;
		}
		public ChemPlantRecipe setItemInput(ItemStack...stacks)
		{
			assert stacks.length <= 4 : "Stack array length must be equal or less than 4!";
			
			for (int i = 0; i < stacks.length && i < 4; i++)
				inputs[i] = Library.carefulCopy(stacks[i]);
			
			return this;
		}
		
		public ChemPlantRecipe setFluidOutput(FluidStack f1, @Nullable FluidStack f2)
		{
			fOut[0] = f1;
			fOut[1] = f2;
			return this;
		}
		public ChemPlantRecipe setItemOutput(ItemStack...stacks)
		{
			assert stacks.length <= 4 : "Stack array length must be equal or less than 4!";
			
			for (int i = 0; i < stacks.length && i < 4; i++)
				outputs[i] = Library.carefulCopy(stacks[i]);
			
			return this;
		}
	}
}
