package com.hbm.handler.nei;

import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.handler.imc.ICompatNHNEI;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.util.InventoryUtil;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class NEIUniversalHandler extends TemplateRecipeHandler implements ICompatNHNEI {

	@Override
	public ItemStack[] getMachinesForRecipe() {
		return machine;
	}

	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
	public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<RecipeTransferRect>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
	public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<Class<? extends GuiContainer>>();
	
	/// SETUP ///
	public final String display;
	public final ItemStack[] machine;
	public final HashMap<Object, Object> recipes;
	public HashMap<Object, Object> machineOverrides;
	/// SETUP ///
	public NEIUniversalHandler(String display, ItemStack machine[], HashMap recipes) {
		this.display = display;
		this.machine = machine;
		this.recipes = recipes;
		this.machineOverrides = null;
	}
	
	public NEIUniversalHandler(String display, HashMap recipes, HashMap machines) {
		this(display, (ItemStack[]) null, recipes);
		this.machineOverrides = machines;
	}

	public NEIUniversalHandler(String display, ItemStack machine, HashMap recipes) {	this(display, new ItemStack[]{machine}, recipes); }
	public NEIUniversalHandler(String display, Item machine, HashMap recipes) {			this(display, new ItemStack(machine), recipes); }
	public NEIUniversalHandler(String display, Block machine, HashMap recipes) {		this(display, new ItemStack(machine), recipes); }

	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {
		
		PositionedStack[] input;
		PositionedStack[] output;
		PositionedStack machinePositioned;
		Object originalInputInstance;

		public RecipeSet(ItemStack[][] in, ItemStack[][] out, Object originalInputInstance /* for custom machine lookup */) {
			this.originalInputInstance = originalInputInstance;
			
			input = new PositionedStack[in.length];
			int[][] inPos = NEIUniversalHandler.getInputCoords(in.length);
			for(int i = 0; i < in.length; i++) {
				ItemStack[] sub = in[i];
				this.input[i] = new PositionedStack(sub, inPos[i][0], inPos[i][1]);
			}
			output = new PositionedStack[out.length];
			int[][] outPos = NEIUniversalHandler.getOutputCoords(out.length);
			for(int i = 0; i < out.length; i++) {
				ItemStack[] sub = out[i];
				this.output[i] = new PositionedStack(sub, outPos[i][0], outPos[i][1]);
			}
			
			ItemStack[] m = machine;
			
			if(NEIUniversalHandler.this.machineOverrides != null) {
				Object key = NEIUniversalHandler.this.machineOverrides.get(originalInputInstance);
				
				if(key != null) {
					this.machinePositioned = new PositionedStack(key, 75, 31);
				}
			}
			
			if(machinePositioned == null) this.machinePositioned = new PositionedStack(m, 75, 31);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, Arrays.asList(input));
		}

		@Override
		public PositionedStack getResult() {
			return output[0];
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> other = new ArrayList();
			for(PositionedStack pos : output) {
				other.add(pos);
			}
			other.add(machinePositioned);
			return getCycledIngredients(cycleticks / 20, other);
		}
	}

	@Override
	public String getRecipeName() {
		return this.display;
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei.png";
	}

	@Override
	public void drawBackground(int recipe) {
		super.drawBackground(recipe);
		
		RecipeSet rec = (RecipeSet) this.arecipes.get(recipe);

		int[][] inPos = NEIUniversalHandler.getInputCoords(rec.input.length);
		for(int[] pos : inPos) {
			drawTexturedModalRect(pos[0] - 1, pos[1] - 1, 5, 87, 18, 18);
		}
		int[][] outPos = NEIUniversalHandler.getOutputCoords(rec.output.length);
		for(int[] pos : outPos) {
			drawTexturedModalRect(pos[0] - 1, pos[1] - 1, 5, 87, 18, 18);
		}
		
		drawTexturedModalRect(74, 14, 59, 87, 18, 36);
	}
	
	public static int[][] getInputCoords(int count) {
		
		switch(count) {
		case 1: return new int[][] {
			{48, 24}
		};
		case 2: return new int[][] {
			{48, 24},
			{30, 24}
		};
		case 3: return new int[][] {
			{48, 24},
			{30, 24},
			{12, 24}
		};
		case 4: return new int[][] {
			{48, 24 - 9},
			{30, 24 - 9},
			{48, 24 + 9},
			{30, 24 + 9}
		};
		case 5: return new int[][] {
			{48, 24 - 9},
			{30, 24 - 9},
			{12, 24},
			{48, 24 + 9},
			{30, 24 + 9},
		};
		case 6: return new int[][] {
			{48, 24 - 9},
			{30, 24 - 9},
			{12, 24 - 9},
			{48, 24 + 9},
			{30, 24 + 9},
			{12, 24 + 9}
		};
		case 7: return new int[][] {
			{48, 24 - 18},
			{30, 24 - 9},
			{12, 24 - 9},
			{48, 24},
			{30, 24 + 9},
			{12, 24 + 9},
			{48, 24 + 18}
		};
		case 8: return new int[][] {
			{48, 24 - 18},
			{30, 24 - 18},
			{12, 24 - 9},
			{48, 24},
			{30, 24},
			{12, 24 + 9},
			{48, 24 + 18},
			{30, 24 + 18}
		};
		case 9: return new int[][] {
			{48, 24 - 18},
			{30, 24 - 18},
			{12, 24 - 18},
			{48, 24},
			{30, 24},
			{12, 24},
			{48, 24 + 18},
			{30, 24 + 18},
			{12, 24 + 18}
		};
		}
		
		return new int[count][2];
	}
	
	public static int[][] getOutputCoords(int count) {
		
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
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if(outputId.equals(getKey())) {
			
			outer: for(Entry<Object, Object> recipe : recipes.entrySet()) {
				ItemStack[][] ins = InventoryUtil.extractObject(recipe.getKey());
				ItemStack[][] outs = InventoryUtil.extractObject(recipe.getValue());
				
				for(ItemStack[] array : ins) for(ItemStack stack : array) if(stack.getItem() == ModItems.item_secret) continue outer;
				for(ItemStack[] array : outs) for(ItemStack stack : array) if(stack.getItem() == ModItems.item_secret) continue outer;
				
				this.arecipes.add(new RecipeSet(ins, outs, recipe.getKey()));
			}
			
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		
		outer: for(Entry<Object, Object> recipe : recipes.entrySet()) {
			ItemStack[][] ins = InventoryUtil.extractObject(recipe.getKey());
			ItemStack[][] outs = InventoryUtil.extractObject(recipe.getValue());
			
			for(ItemStack[] array : ins) for(ItemStack stack : array) if(stack.getItem() == ModItems.item_secret) continue outer;
			for(ItemStack[] array : outs) for(ItemStack stack : array) if(stack.getItem() == ModItems.item_secret) continue outer;
			
			match:
			for(ItemStack[] array : outs) {
				for(ItemStack stack : array) {
					if(NEIServerUtils.areStacksSameTypeCrafting(stack, result)) {
						this.arecipes.add(new RecipeSet(ins, outs, recipe.getKey()));
						break match;
					}
				}
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if(inputId.equals(getKey())) {
			loadCraftingRecipes(getKey(), new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		
		outer: for(Entry<Object, Object> recipe : recipes.entrySet()) {
			ItemStack[][] ins = InventoryUtil.extractObject(recipe.getKey());
			ItemStack[][] outs = InventoryUtil.extractObject(recipe.getValue());
			
			for(ItemStack[] array : ins) for(ItemStack stack : array) if(stack.getItem() == ModItems.item_secret) continue outer;
			for(ItemStack[] array : outs) for(ItemStack stack : array) if(stack.getItem() == ModItems.item_secret) continue outer;
			
			match:
			for(ItemStack[] array : ins) {
				for(ItemStack stack : array) {
					if(NEIServerUtils.areStacksSameTypeCrafting(stack, ingredient)) {
						this.arecipes.add(new RecipeSet(ins, outs, recipe.getKey()));
						break match;
					}
				}
			}
		}
	}
	
	@Override
	public void loadTransferRects() {
		transferRectsGui = new LinkedList<RecipeTransferRect>();
		guiGui = new LinkedList<Class<? extends GuiContainer>>();
		transferRects.add(new RecipeTransferRect(new Rectangle(147, 1, 18, 18), getKey()));
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
	}
	
	public abstract String getKey();

	@Override
	public String getRecipeID() {
		return getKey();
	}
}
