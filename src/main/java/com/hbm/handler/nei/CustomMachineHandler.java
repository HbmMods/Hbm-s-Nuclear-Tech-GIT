package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import codechicken.lib.gui.GuiDraw;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.CustomMachineConfigJSON;
import com.hbm.config.CustomMachineConfigJSON.MachineConfiguration;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.recipes.CustomMachineRecipes;
import com.hbm.inventory.recipes.CustomMachineRecipes.CustomMachineRecipe;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.lib.RefStrings;
import com.hbm.util.ItemStackUtil;
import com.hbm.util.Tuple.Pair;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class CustomMachineHandler extends TemplateRecipeHandler {

	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();

	public MachineConfiguration conf;

	@Override
	public TemplateRecipeHandler newInstance() { // brick by brick, suck my dick
		try {
			return new CustomMachineHandler(conf);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	public CustomMachineHandler(MachineConfiguration conf) {
		super();
		this.conf = conf;
		loadTransferRects();
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
	}

	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {

		List<PositionedStack> inputs = new ArrayList();
		PositionedStack machine;
		List<PositionedStack> outputs = new ArrayList();
		public int flux = 0;
		public int heat = 0;
		public float radiationAmount = 0;
		public String pollutionType;
		public float pollutionAmount = 0;

		public RecipeSet(CustomMachineRecipe recipe) {

			for(int i = 0; i < 3; i++) if(recipe.inputFluids.length > i) inputs.add(new PositionedStack(ItemFluidIcon.make(recipe.inputFluids[i]), 12 + i * 18, 6));
			for(int i = 0; i < 3; i++) if(recipe.inputItems.length > i) inputs.add(new PositionedStack(recipe.inputItems[i].extractForNEI(), 12 + i * 18, 24));
			for(int i = 3; i < 6; i++) if(recipe.inputItems.length > i) inputs.add(new PositionedStack(recipe.inputItems[i].extractForNEI(), 12 + (i - 3) * 18, 42));

			for(int i = 0; i < 3; i++) if(recipe.outputFluids.length > i) outputs.add(new PositionedStack(ItemFluidIcon.make(recipe.outputFluids[i]), 102 + i * 18, 6));

			for(int i = 0; i < 3; i++) if(recipe.outputItems.length > i) {
				Pair<ItemStack, Float> pair = recipe.outputItems[i];
				ItemStack out = pair.getKey().copy();
				if(pair.getValue() != 1) {
					ItemStackUtil.addTooltipToStack(out, EnumChatFormatting.RED + "" + (((int)(pair.getValue() * 1000)) / 10D) + "%");
				}
				outputs.add(new PositionedStack(out, 102 + i * 18, 24));
			}

			for(int i = 3; i < 6; i++) if(recipe.outputItems.length > i) {
				Pair<ItemStack, Float> pair = recipe.outputItems[i];
				ItemStack out = pair.getKey().copy();
				if(pair.getValue() != 1) {
					ItemStackUtil.addTooltipToStack(out, EnumChatFormatting.RED + "" + (((int)(pair.getValue() * 1000)) / 10D) + "%");
				}
				outputs.add(new PositionedStack(out, 102 + (i - 3) * 18, 42));
			}
			
			this.pollutionType = recipe.pollutionType;
			this.pollutionAmount = recipe.pollutionAmount;
			this.radiationAmount = recipe.radiationAmount;
			if(conf.fluxMode) this.flux = recipe.flux;
			if(conf.maxHeat > 0 && recipe.heat > 0) this.heat = recipe.heat;
			
			this.machine = new PositionedStack(new ItemStack(ModBlocks.custom_machine, 1, 100 + CustomMachineConfigJSON.niceList.indexOf(conf)), 75, 42);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, inputs);
		}

		@Override
		public PositionedStack getResult() {
			return outputs.get(0);
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> other = new ArrayList();
			other.addAll(inputs);
			other.add(machine);
			other.addAll(outputs);
			return getCycledIngredients(cycleticks / 20, other);
		}
	}

	@Override
	public String getRecipeName() {
		return conf.localizedName;
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_custom.png";
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {

		if(outputId.equals("ntm_" + conf.unlocalizedName)) {

			List<CustomMachineRecipe> recipes = CustomMachineRecipes.recipes.get(conf.recipeKey);

			if(recipes != null) for(CustomMachineRecipe recipe : recipes) {
				this.arecipes.add(new RecipeSet(recipe));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {

		List<CustomMachineRecipe> recipes = CustomMachineRecipes.recipes.get(conf.recipeKey);

		if(recipes != null) outer:for(CustomMachineRecipe recipe : recipes) {

			for(Pair<ItemStack, Float> stack : recipe.outputItems) {

				if(NEIServerUtils.areStacksSameTypeCrafting(stack.getKey(), result)) {
					this.arecipes.add(new RecipeSet(recipe));
					continue outer;
				}
			}

			for(FluidStack fluid : recipe.outputFluids) {
				ItemStack drop = ItemFluidIcon.make(fluid);

				if(compareFluidStacks(result, drop)) {
					this.arecipes.add(new RecipeSet(recipe));
					continue outer;
				}
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {

		if(inputId.equals("ntm_" + conf.unlocalizedName)) {
			loadCraftingRecipes("ntm_" + conf.unlocalizedName, new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {

		List<CustomMachineRecipe> recipes = CustomMachineRecipes.recipes.get(conf.recipeKey);

		if(recipes != null) outer:for(CustomMachineRecipe recipe : recipes) {

			for(AStack stack : recipe.inputItems) {

				List<ItemStack> stacks = stack.extractForNEI();

				for(ItemStack sta : stacks) {
					if(NEIServerUtils.areStacksSameTypeCrafting(ingredient, sta)) {
						this.arecipes.add(new RecipeSet(recipe));
						continue outer;
					}
				}
			}

			for(FluidStack fluid : recipe.inputFluids) {
				ItemStack drop = ItemFluidIcon.make(fluid);

				if(compareFluidStacks(ingredient, drop)) {
					this.arecipes.add(new RecipeSet(recipe));
					continue outer;
				}
			}
		}
	}

	public static boolean compareFluidStacks(ItemStack sta1, ItemStack sta2) {
		return sta1.getItem() == sta2.getItem() && sta1.getItemDamage() == sta2.getItemDamage();
	}

	@Override
	public void loadTransferRects() {
		if(this.conf == null) return;
		transferRects.clear();
		transferRects.add(new RecipeTransferRect(new Rectangle(65, 23, 36, 18), "ntm_" + conf.unlocalizedName));
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
	}
	@Override
	public void drawExtras(int recipe) {
		RecipeSet Recipe = (RecipeSet) this.arecipes.get(recipe);
		int side = 83;
		if(Recipe.radiationAmount != 0){
			String radiation = "Radiation:" + Recipe.radiationAmount + "";
			GuiDraw.drawString(radiation, 160 - GuiDraw.fontRenderer.getStringWidth(radiation), 63, 0x08FF00);
		}
		if (Recipe.pollutionAmount != 0){
			String pollution = Recipe.pollutionType + ":" + Recipe.pollutionAmount + "";
			GuiDraw.drawString(pollution, 160 - GuiDraw.fontRenderer.getStringWidth(pollution), 75, 0x404040);
		}
		if(conf.fluxMode) {
			String flux = "Flux:" + Recipe.flux + "";
			GuiDraw.drawString(flux, side - GuiDraw.fontRenderer.getStringWidth(flux) / 2, 16, 0x08FF00);
		}
		if(conf.maxHeat>0 && Recipe.heat>0){
			String heat = "Heat:" + Recipe.heat + "";
			GuiDraw.drawString(heat, side - GuiDraw.fontRenderer.getStringWidth(heat) / 2, 8, 0xFF0000);
		}
	}
}
