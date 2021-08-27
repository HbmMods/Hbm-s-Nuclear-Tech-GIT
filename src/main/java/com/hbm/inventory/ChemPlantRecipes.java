package com.hbm.inventory;

import java.awt.MultipleGradientPaint.ColorSpaceType;
import java.util.HashMap;

import javax.annotation.Nullable;

import com.google.common.annotations.Beta;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.Untested;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemChemistryTemplate.EnumChemistryTemplate;
import com.hbm.lib.Library;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
@Untested
@Beta
public class ChemPlantRecipes
{
	public static HashMap<EnumChemistryTemplate, ChemPlantRecipe> recipes = new HashMap<>();
	
	public static void registerChemPlantRecipes()
	{
		recipes.put(EnumChemistryTemplate.CONCRETE, new ChemPlantRecipe()
				.setFluidInput(new FluidStack(2000, FluidType.WATER), null)
				.setItemInput(new ItemStack(Blocks.sand, 8), new ItemStack(Blocks.gravel, 8))
				.setItemOutput(new ItemStack(ModBlocks.concrete_smooth, 4), new ItemStack(ModBlocks.concrete_smooth, 4), new ItemStack(ModBlocks.concrete_smooth, 4), new ItemStack(ModBlocks.concrete_smooth, 4)));
		recipes.put(EnumChemistryTemplate.LF_BASE, new ChemPlantRecipe(400)
				.setFluidInput(new FluidStack(8000, FluidType.COOLANT), new FluidStack(1200, FluidType.OXYGEN))
				.setItemInput(new ItemStack(ModItems.powder_aluminium, 2), new ItemStack(ModItems.powder_bromine), new ItemStack(ModItems.powder_lithium, 3), new ItemStack(ModItems.fluorite, 3))
				.setFluidOutput(new FluidStack(2000, FluidType.SALT), null));
		recipes.put(EnumChemistryTemplate.LF_U, new ChemPlantRecipe(200)
				.setFluidInput(new FluidStack(1000, FluidType.SALT), null)
				.setItemInput(new ItemStack(ModItems.ingot_u235), new ItemStack(ModItems.nugget_beryllium, 3))
				.setFluidOutput(new FluidStack(1000, FluidType.SALT_U), null));
		recipes.put(EnumChemistryTemplate.LF_SA, new ChemPlantRecipe(300)
				.setFluidInput(new FluidStack(1000, FluidType.SAS3), new FluidStack(100, FluidType.MERCURY))
				.setItemInput(new ItemStack(ModItems.powder_nitan_mix))
				.setFluidOutput(new FluidStack(1000, FluidType.SAS3_NIT), null));
	}
	
	@Untested
	@Beta
	public static class ChemPlantRecipe
	{
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
