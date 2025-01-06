package com.hbm.handler.nei;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class AshpitHandler extends NEIUniversalHandler {

	public AshpitHandler() {
		super(ModBlocks.machine_ashpit.getLocalizedName(), ModBlocks.machine_ashpit, getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmAshpit";
	}

	public static HashMap getRecipes() {
		
		HashMap<Object, Object> recipes = new HashMap<Object, Object>();

		ItemStack[] ovens = new ItemStack[] {new ItemStack(ModBlocks.heater_firebox), new ItemStack(ModBlocks.heater_oven)};
		ItemStack[] chimneys = new ItemStack[] {new ItemStack(ModBlocks.chimney_brick), new ItemStack(ModBlocks.chimney_industrial)};
		ItemStack[] coals = new ItemStack[] {new ItemStack(Items.coal, 1, 0), new ItemStack(ModItems.lignite), new ItemStack(ModItems.coke)};
		ItemStack[] wood = new ItemStack[] {new ItemStack(Blocks.log), new ItemStack(Blocks.log2), new ItemStack(Blocks.planks), new ItemStack(Blocks.sapling)};
		ItemStack[] misc = new ItemStack[] {new ItemStack(ModItems.solid_fuel), new ItemStack(ModItems.scrap), new ItemStack(ModItems.dust), new ItemStack(ModItems.rocket_fuel)};
		FluidType[] smokes = new FluidType[] {Fluids.SMOKE, Fluids.SMOKE_LEADED, Fluids.SMOKE_POISON};;

		recipes.put(new ItemStack[][] {ovens, coals}, DictFrame.fromOne(ModItems.powder_ash, EnumAshType.COAL));
		recipes.put(new ItemStack[][] {ovens, wood}, DictFrame.fromOne(ModItems.powder_ash, EnumAshType.WOOD));
		recipes.put(new ItemStack[][] {ovens, misc}, DictFrame.fromOne(ModItems.powder_ash, EnumAshType.MISC));

		for(FluidType smoke : smokes) {
			recipes.put(new ItemStack[][] {chimneys, new ItemStack[] {ItemFluidIcon.make(smoke, 2_000)}}, DictFrame.fromOne(ModItems.powder_ash, EnumAshType.FLY));
			recipes.put(new ItemStack[][] {new ItemStack[] {new ItemStack(ModBlocks.chimney_industrial)}, new ItemStack[] {ItemFluidIcon.make(smoke, 8_000)}}, DictFrame.fromOne(ModItems.powder_ash, EnumAshType.SOOT));
		}
		
		return recipes;
	}
}
