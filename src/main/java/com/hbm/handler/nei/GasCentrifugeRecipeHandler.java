package com.hbm.handler.nei;

import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.imc.ICompatNHNEI;
import com.hbm.inventory.gui.GUIMachineGasCent;
import com.hbm.inventory.recipes.GasCentrifugeRecipes;
import com.hbm.inventory.recipes.MachineRecipes;
import com.hbm.lib.RefStrings;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class GasCentrifugeRecipeHandler extends TemplateRecipeHandler implements ICompatNHNEI {
	@Override
	public ItemStack[] getMachinesForRecipe() {
		return new ItemStack[]{
				new ItemStack(ModBlocks.machine_gascent)};
	}
	@Override
	public String getRecipeID() {
		return "gascentprocessing";
	}
	public static ArrayList<Fuel> fuels;

	public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		List<PositionedStack> output = new ArrayList();
		boolean isHighSpeed;
		int centNumber;

		public SmeltingSet(ItemStack input, ItemStack[] results, boolean isHighSpeed, int centNumber) {
			input.stackSize = 1;
			this.input = new PositionedStack(input, 52 - 5, 35 - 11);
			this.isHighSpeed = isHighSpeed;
			this.centNumber = centNumber;
			
			for(byte i = 0; i < results.length; i++) {
				this.output.add(new PositionedStack(results[i], i % 2 == 0 ? 134 - 5 : 152 - 5, i < 2 ? 26 - 11 : 44 - 11 ));
			}
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, Arrays.asList(new PositionedStack[] { input }));
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(fuels.get((cycleticks / 48) % fuels.size()).stack);
			stacks.addAll(output);
			return stacks;
		}

		@Override
		public PositionedStack getResult() {
			return output.get(0);
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
		return "Gas Centrifuge";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_centrifuge_gas.png";
	}

	@Override
	public TemplateRecipeHandler newInstance() {
		if(fuels == null || fuels.isEmpty())
			fuels = new ArrayList<Fuel>();
		for(ItemStack i : MachineRecipes.instance().getBatteries()) {
			fuels.add(new Fuel(i));
		}
		return super.newInstance();
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if((outputId.equals("gascentprocessing")) && getClass() == GasCentrifugeRecipeHandler.class) {
			Map<Object, Object[]> recipes = GasCentrifugeRecipes.getGasCentrifugeRecipes();
			for(Map.Entry<Object, Object[]> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet((ItemStack) recipe.getKey(), (ItemStack[]) recipe.getValue()[0], (boolean) recipe.getValue()[1], (int) recipe.getValue()[2]));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<Object, Object[]> recipes = GasCentrifugeRecipes.getGasCentrifugeRecipes();
		for(Map.Entry<Object, Object[]> recipe : recipes.entrySet()) {
			if(NEIServerUtils.areStacksSameType(((ItemStack[]) recipe.getValue()[0])[0], result) || NEIServerUtils.areStacksSameType(((ItemStack[]) recipe.getValue()[0])[1], result)
					|| NEIServerUtils.areStacksSameType(((ItemStack[]) recipe.getValue()[0])[2], result) || NEIServerUtils.areStacksSameType(((ItemStack[]) recipe.getValue()[0])[3], result))
				this.arecipes.add(new SmeltingSet((ItemStack) recipe.getKey(), (ItemStack[]) recipe.getValue()[0], (boolean) recipe.getValue()[1], (int) recipe.getValue()[2]));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if((inputId.equals("gascentprocessing")) && getClass() == GasCentrifugeRecipeHandler.class) {
			loadCraftingRecipes("gascentprocessing", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object, Object[]> recipes = GasCentrifugeRecipes.getGasCentrifugeRecipes();
		for(Map.Entry<Object, Object[]> recipe : recipes.entrySet()) {
			if(compareFluidStacks(ingredient, (ItemStack) recipe.getKey()))
				this.arecipes.add(new SmeltingSet((ItemStack) recipe.getKey(), (ItemStack[]) recipe.getValue()[0], (boolean) recipe.getValue()[1], (int) recipe.getValue()[2]));
		}
	}

	private boolean compareFluidStacks(ItemStack sta1, ItemStack sta2) {
		return sta1.getItem() == sta2.getItem() && sta1.getItemDamage() == sta2.getItemDamage();
	}

	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(3, 51 - 45, 176, 0, 16, 34, 480, 7);
		
		SmeltingSet set = (SmeltingSet) this.arecipes.get(recipe);
		
		drawProgressBar(79 - 5, 28 - 11, 208, 0, 44, 37, set.isHighSpeed ? 150 - 70 : 150, 0);
		
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		
		String centrifuges = set.centNumber + " G. Cents";
		fontRenderer.drawString(centrifuges, (50 - fontRenderer.getStringWidth(centrifuges) / 2), 21 - 11, 65280);
	}
	
	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
	public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<RecipeTransferRect>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
	public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<Class<? extends GuiContainer>>();

	@Override
	public void loadTransferRects() {
		transferRectsGui = new LinkedList<RecipeTransferRect>();
		guiGui = new LinkedList<Class<? extends GuiContainer>>();
		
		transferRects.add(new RecipeTransferRect(new Rectangle(79 - 5, 26 - 11, 44, 40), "gascentprocessing"));
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(70 - 5, 36 - 11, 36, 12), "gascentprocessing"));
		
		guiGui.add(GUIMachineGasCent.class);
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}
	
	@Override
	public void drawBackground(int recipe) {
		super.drawBackground(recipe);
		
		SmeltingSet set = (SmeltingSet) this.arecipes.get(recipe);
		
		if(set.isHighSpeed)
			drawTexturedModalRect(30 - 5, 35 - 11, 192, 0, 16, 16);
	}
}
