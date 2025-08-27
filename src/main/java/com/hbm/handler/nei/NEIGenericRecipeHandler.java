package com.hbm.handler.nei;

import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.hbm.config.ClientConfig;
import com.hbm.handler.imc.ICompatNHNEI;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipes.IOutput;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBlueprints;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.lib.RefStrings;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public abstract class NEIGenericRecipeHandler extends TemplateRecipeHandler implements ICompatNHNEI {

	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
	public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<RecipeTransferRect>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
	public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<Class<? extends GuiContainer>>();

	public final String displayName;
	public final GenericRecipes recipeSet;
	public final ItemStack[] machines;
	
	public NEIGenericRecipeHandler(String displayName, GenericRecipes recipeSet, Block... machines) {
		ItemStack[] machineStacks = new ItemStack[machines.length];
		for(int i = 0; i < machines.length; i++) machineStacks[i] = new ItemStack(machines[i]);
		this.displayName = displayName;
		this.recipeSet = recipeSet;
		this.machines = machineStacks;
	}
	
	public NEIGenericRecipeHandler(String displayName, GenericRecipes recipeSet, ItemStack... machines) {
		this.displayName = displayName;
		this.recipeSet = recipeSet;
		this.machines = machines;
	}
	
	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {

		protected GenericRecipe recipe;
		protected PositionedStack[] input;
		protected PositionedStack[] output;
		protected PositionedStack machine;
		protected PositionedStack template;
		
		public RecipeSet(GenericRecipe recipe) {
			this.recipe = recipe;
			int inputSize = 0;
			int outputSize = 0;

			if(recipe.inputItem != null) inputSize += recipe.inputItem.length; // sure a ternary is "less code" but is it more readable? no? thought so
			if(recipe.inputFluid != null) inputSize += recipe.inputFluid.length;
			if(recipe.outputItem != null) outputSize += recipe.outputItem.length;
			if(recipe.outputFluid != null) outputSize += recipe.outputFluid.length;

			int inputOffset = getInputXOffset(recipe, inputSize);
			int outputOffset = getOutputXOffset(recipe, outputSize);
			int machineOffset = getMachineXOffset(recipe);

			this.input = new PositionedStack[inputSize];
			int[][] inPos = getInputSlotPositions(inputSize);
			inputSize = 0; // just gonna reuse this because fuck it why not
			
			if(recipe.inputItem != null) for(int i = 0; i < recipe.inputItem.length; i++) {
				this.input[i] = new PositionedStack(recipe.inputItem[i].extractForNEI(), inPos[i][0] + inputOffset, inPos[i][1]);
				inputSize = i + 1;
			}
			if(recipe.inputFluid != null) for(int i = 0; i < recipe.inputFluid.length; i++) {
				this.input[inputSize + i] = new PositionedStack(ItemFluidIcon.make(recipe.inputFluid[i]), inPos[inputSize + i][0] + inputOffset, inPos[inputSize + i][1]);
			}

			int[][] outPos = getOutputSlotPositions(outputSize);
			this.output = new PositionedStack[outputSize];
			outputSize = 0;
			
			if(recipe.outputItem != null) for(int i = 0; i < recipe.outputItem.length; i++) {
				this.output[i] = new PositionedStack(recipe.outputItem[i].getAllPossibilities(), outPos[i][0] + outputOffset, outPos[i][1]);
				outputSize = i + 1;
			}
			if(recipe.outputFluid != null) for(int i = 0; i < recipe.outputFluid.length; i++) {
				this.output[outputSize + i] = new PositionedStack(ItemFluidIcon.make(recipe.outputFluid[i]), outPos[outputSize + i][0] + outputOffset, outPos[outputSize + i][1]);
			}
			
			if(recipe.isPooled()) {
				String[] pools = recipe.getPools();
				ItemStack[] blueprints = new ItemStack[pools.length];
				for(int i = 0; i < pools.length; i++) blueprints[i] = ItemBlueprints.make(pools[i]);
				this.template = new PositionedStack(blueprints, 75 + machineOffset, 10);
			}
			
			ItemStack[] machineStacks = getMachines(recipe);
			this.machine = new PositionedStack(machineStacks, 75 + machineOffset, template == null ? 31 : 38);
		}

		@Override public List<PositionedStack> getIngredients() { return getCycledIngredients(cycleticks / 20, Arrays.asList(this.input)); }
		@Override public PositionedStack getResult() { return this.output[0]; }

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> other = new ArrayList();
			for(int i = 1; i < this.output.length; i++) other.add(this.output[i]);
			other.add(this.machine);
			if(this.template != null) other.add(this.template);
			return getCycledIngredients(cycleticks / 20, other);
		}
	}

	@Override public String getRecipeName() { return this.displayName; }
	@Override public ItemStack[] getMachinesForRecipe() { return machines; }
	@Override public String getGuiTexture() { return RefStrings.MODID + ":textures/gui/nei/gui_nei.png"; }

	public int getInputXOffset(GenericRecipe recipe, int inputCount) { return 0; }
	public int getOutputXOffset(GenericRecipe recipe, int outputCount) { return 0; }
	public int getMachineXOffset(GenericRecipe recipe) { return 0; }
	public ItemStack[] getMachines(GenericRecipe recipe) { return this.machines; }
	
	// ✨ magic number vomit land ✨
	public int[][] getInputSlotPositions(int count) {

		if(count == 1) return new int[][] { {48, 24} };
		if(count == 2) return new int[][] { {30, 24}, {48, 24} };
		if(count == 3) return new int[][] { {12, 24}, {30, 24}, {48, 24} };
		if(count == 4) return new int[][] { {30, 15}, {48, 15}, {30, 33}, {48, 33} };
		if(count == 5) return new int[][] { {12, 15}, {30, 15}, {48, 15}, {12, 33}, {30, 33} };
		if(count == 6) return new int[][] { {12, 15}, {30, 15}, {48, 15}, {12, 33}, {30, 33}, {48, 33} };
		
		int[][] slots = new int[count][2];
		int cols = (count + 2) / 3;
		
		for(int i = 0; i < count; i++) {
			slots[i][0] = 12 + (i % cols) * 18 - (cols == 4 ? 18 : 0);
			slots[i][1] = 6 + (i / cols) * 18;
		}
		
		return slots;
	}
	
	public int[][] getOutputSlotPositions(int count) {
		switch(count) {
		case 1: return new int[][] {
			{102, 24}
		};
		case 2: return new int[][] {
			{102, 24},
			{120, 24}
		};
		case 3: return new int[][] {
			{102, 24},
			{120, 24},
			{138, 24}
		};
		case 4: return new int[][] {
			{102, 24 - 9},
			{120, 24 - 9},
			{102, 24 + 9},
			{120, 24 + 9}
		};
		case 5: return new int[][] {
			{102, 24 - 9}, {120, 24 - 9},
			{102, 24 + 9}, {120, 24 + 9},
			{138, 24},
		};
		case 6: return new int[][] {
			{102, 6}, {120, 6},
			{102, 24}, {120, 24},
			{102, 42}, {120, 42},
		};
		case 7: return new int[][] {
			{102, 6}, {120, 6},
			{102, 24}, {120, 24},
			{102, 42}, {120, 42},
			{138, 24},
		};
		case 8: return new int[][] {
			{102, 6}, {120, 6},
			{102, 24}, {120, 24},
			{102, 42}, {120, 42},
			{138, 24}, {138, 42},
		};
		}
		return new int[count][2];
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {

		outer: for(Object o : this.recipeSet.recipeOrderedList) {
			GenericRecipe recipe = (GenericRecipe) o;
			boolean hasMatch = false;
			boolean hide = ClientConfig.NEI_HIDE_SECRETS.get();
			
			if(hide && recipe.isPooled()) {
				String[] pools = recipe.getPools();
				for(String pool : pools) if(pool.startsWith(GenericRecipes.POOL_PREFIX_SECRET)) continue outer;
			}
			
			if(hide && recipe.inputItem != null) for(AStack astack : recipe.inputItem) for(ItemStack stack : astack.extractForNEI()) {
				if(ModItems.excludeNEI.contains(stack.getItem())) continue outer;
			}
			if(recipe.outputItem != null) for(IOutput output : recipe.outputItem) for(ItemStack stack : output.getAllPossibilities()) {
				if(hide && ModItems.excludeNEI.contains(stack.getItem())) continue outer;
				if(NEIServerUtils.areStacksSameTypeCrafting(stack, result)) hasMatch = true;
			}
			if(recipe.outputFluid != null) for(FluidStack fluid : recipe.outputFluid) {
				if(areItemsAndMetaEqual(ItemFluidIcon.make(fluid), result)) hasMatch = true;
			}
			
			if(hasMatch) this.arecipes.add(new RecipeSet(recipe));
		}
	}
	
	/** load all */
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if(outputId.equals(getRecipeID())) {
			
			outer: for(Object o : this.recipeSet.recipeOrderedList) {
				GenericRecipe recipe = (GenericRecipe) o;
				boolean hide = ClientConfig.NEI_HIDE_SECRETS.get();
				
				if(hide && recipe.isPooled()) {
					String[] pools = recipe.getPools();
					for(String pool : pools) if(pool.startsWith(GenericRecipes.POOL_PREFIX_SECRET)) continue outer;
				}
				
				if(hide && recipe.inputItem != null) for(AStack astack : recipe.inputItem) for(ItemStack stack : astack.extractForNEI())
					if(ModItems.excludeNEI.contains(stack.getItem())) continue outer;
				if(hide && recipe.outputItem != null) for(IOutput output : recipe.outputItem) for(ItemStack stack : output.getAllPossibilities())
					if(ModItems.excludeNEI.contains(stack.getItem())) continue outer;
				
				this.arecipes.add(new RecipeSet(recipe));
			}
			
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if(inputId.equals(getRecipeID())) {
			loadCraftingRecipes(getRecipeID(), new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {

		outer: for(Object o : this.recipeSet.recipeOrderedList) {
			GenericRecipe recipe = (GenericRecipe) o;
			boolean hasMatch = false;
			boolean hide = ClientConfig.NEI_HIDE_SECRETS.get();
			
			if(hide && recipe.isPooled()) {
				String[] pools = recipe.getPools();
				for(String pool : pools) if(pool.startsWith(GenericRecipes.POOL_PREFIX_SECRET)) continue outer;
			}
			
			if(recipe.inputItem != null) for(AStack astack : recipe.inputItem) for(ItemStack stack : astack.extractForNEI()) {
				if(hide && ModItems.excludeNEI.contains(stack.getItem())) continue outer;
				if(NEIServerUtils.areStacksSameTypeCrafting(stack, ingredient)) hasMatch = true;
			}
			if(recipe.inputFluid != null) for(FluidStack fluid : recipe.inputFluid) {
				if(areItemsAndMetaEqual(ItemFluidIcon.make(fluid), ingredient)) hasMatch = true;
			}
			if(hide && recipe.outputItem != null) for(IOutput output : recipe.outputItem) for(ItemStack stack : output.getAllPossibilities()) {
				if(ModItems.excludeNEI.contains(stack.getItem())) continue outer;
			}
			
			if(hasMatch) this.arecipes.add(new RecipeSet(recipe));
		}
	}

	public static boolean areItemsAndMetaEqual(ItemStack sta1, ItemStack sta2) {
		return sta1.getItem() == sta2.getItem() && sta1.getItemDamage() == sta2.getItemDamage();
	}
	
	@Override
	public void loadTransferRects() {
		transferRectsGui = new LinkedList<RecipeTransferRect>();
		guiGui = new LinkedList<Class<? extends GuiContainer>>();
		transferRects.add(new RecipeTransferRect(new Rectangle(147, 1, 18, 18), getRecipeID()));
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
	}

	@Override
	public void drawBackground(int recipe) {
		super.drawBackground(recipe);
		
		RecipeSet rec = (RecipeSet) this.arecipes.get(recipe);

		for(PositionedStack pos : rec.input) drawTexturedModalRect(pos.relx - 1, pos.rely - 1, 5, 87, 18, 18);
		for(PositionedStack pos : rec.output) drawTexturedModalRect(pos.relx - 1, pos.rely - 1, 5, 87, 18, 18);
		
		if(rec.template == null) {
			drawTexturedModalRect(74 + this.getMachineXOffset(rec.recipe), 14, 59, 87, 18, 36);
		} else {
			drawTexturedModalRect(74 + this.getMachineXOffset(rec.recipe), 7, 77, 87, 18, 50);
		}
	}
}
