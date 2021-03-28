package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.inventory.CrystallizerRecipes;
import com.hbm.inventory.MachineRecipes;
import com.hbm.inventory.gui.GUICrystallizer;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.tileentity.machine.TileEntityMachineCrystallizer;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class CrystallizerRecipeHandler extends TemplateRecipeHandler {

	public static ArrayList<Fuel> batteries;

	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack acid;
		PositionedStack result;

		public RecipeSet(Object input, ItemStack result) {
			this.input = new PositionedStack(input, 75, 24);
			this.acid = new PositionedStack(ItemFluidIcon.addQuantity(new ItemStack(ModItems.fluid_icon, 1, FluidType.ACID.ordinal()), TileEntityMachineCrystallizer.acidRequired), 39, 24);
			this.result = new PositionedStack(result, 135, 24);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, Arrays.asList(input, acid));
		}

		@Override
		public PositionedStack getOtherStack() {
			return batteries.get((cycleticks / 48) % batteries.size()).stack;
		}

		@Override
		public PositionedStack getResult() {
			return result;
		}
	}

	public static class Fuel {
		public Fuel(ItemStack ingred) {

			this.stack = new PositionedStack(ingred, 3, 42, false);
		}

		public PositionedStack stack;
	}
    
	@Override
	public String getRecipeName() {
		return "Ore Acidizer";
	}

	@Override
	public String getGuiTexture() {
		return GUICrystallizer.texture.toString();
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if ((outputId.equals("hbm_crystallizer")) && getClass() == CrystallizerRecipeHandler.class) {
			
			Map<Object, Object> recipes = CrystallizerRecipes.getRecipes();
			
			for (Map.Entry<Object, Object> recipe : recipes.entrySet()) {
				this.arecipes.add(new RecipeSet(recipe.getKey(), (ItemStack)recipe.getValue()));
			}
			
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {

		Map<Object, Object> recipes = CrystallizerRecipes.getRecipes();
		
		for (Map.Entry<Object, Object> recipe : recipes.entrySet()) {
			
			if (NEIServerUtils.areStacksSameTypeCrafting((ItemStack)recipe.getValue(), result))
				this.arecipes.add(new RecipeSet(recipe.getKey(), (ItemStack)recipe.getValue()));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		
		if ((inputId.equals("hbm_crystallizer")) && getClass() == CrystallizerRecipeHandler.class) {
			
			loadCraftingRecipes("hbm_crystallizer", new Object[0]);
			
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {

		Map<Object, Object> recipes = CrystallizerRecipes.getRecipes();
		
		for (Map.Entry<Object, Object> recipe : recipes.entrySet()) {
			
			if(NEIServerUtils.areStacksSameTypeCrafting(ingredient, ItemFluidIcon.addQuantity(
							new ItemStack(ModItems.fluid_icon, 1, FluidType.ACID.ordinal()), TileEntityMachineCrystallizer.acidRequired))) {
				
				if(recipe.getKey() instanceof ItemStack) {
					this.arecipes.add(new RecipeSet(recipe.getKey(), (ItemStack)recipe.getValue()));
					
				} else if (recipe.getKey() instanceof ArrayList) {
					for(Object o : (ArrayList)recipe.getKey()) {
						ItemStack stack = (ItemStack)o;
						this.arecipes.add(new RecipeSet(stack, (ItemStack) recipe.getValue()));
					}
				}
				
			} else if(recipe.getKey() instanceof ItemStack) {

				if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()))
					this.arecipes.add(new RecipeSet(recipe.getKey(), (ItemStack)recipe.getValue()));
				
			} else if (recipe.getKey() instanceof ArrayList) {
				
				for(Object o : (ArrayList)recipe.getKey()) {
					ItemStack stack = (ItemStack)o;

					if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, stack))
						this.arecipes.add(new RecipeSet(stack, (ItemStack) recipe.getValue()));
				}
			}
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GUICrystallizer.class;
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(104 - 5, 34 - 11, 24, 18), "hbm_crystallizer"));
	}

	@Override
	public void drawExtras(int recipe) {

		drawProgressBar(99, 23, 192, 0, 22, 16, 600, 0);
		drawProgressBar(3, 6, 176, 0, 16, 34, 60, 7);
	}

	@Override
	public TemplateRecipeHandler newInstance() {

		if(batteries == null)
			batteries = new ArrayList<Fuel>();

		for(ItemStack i : MachineRecipes.instance().getBatteries()) {
			batteries.add(new Fuel(i));
		}
		return super.newInstance();
	}

}
