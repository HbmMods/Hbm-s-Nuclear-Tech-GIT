package com.hbm.inventory.recipes;

import static com.hbm.inventory.fluid.Fluids.*;

import java.util.HashMap;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.items.ItemEnums.EnumTarType;
import com.hbm.items.ModItems;
import com.hbm.util.Tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SolidificationRecipes {
	
	private static HashMap<FluidType, Pair<Integer, ItemStack>> recipes = new HashMap();
	
	public static void register() {
		registerRecipe(WATER, 1000, Blocks.ice);
		registerRecipe(LAVA, 1000, Blocks.obsidian);
		
		//temporary recipes with incorrect quantities
		registerRecipe(OIL, 1000, DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE));
		registerRecipe(BITUMEN, 1000, DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE));
		registerRecipe(HEATINGOIL, 1000, ModItems.solid_fuel);
	}

	private static void registerRecipe(FluidType type, int quantity, Item output) { registerRecipe(type, quantity, new ItemStack(output)); }
	private static void registerRecipe(FluidType type, int quantity, Block output) { registerRecipe(type, quantity, new ItemStack(output)); }
	private static void registerRecipe(FluidType type, int quantity, ItemStack output) {
		recipes.put(type, new Pair<Integer, ItemStack>(quantity, output));
	}
	
	public static Pair<Integer, ItemStack> getOutput(FluidType type) {
		return recipes.get(type);
	}
}
