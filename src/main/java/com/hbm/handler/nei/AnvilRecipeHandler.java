package com.hbm.handler.nei;

import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.NTMAnvil;
import com.hbm.handler.imc.ICompatNHNEI;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.gui.GUIAnvil;
import com.hbm.inventory.recipes.anvil.AnvilRecipes;
import com.hbm.inventory.recipes.anvil.AnvilRecipes.AnvilConstructionRecipe;
import com.hbm.inventory.recipes.anvil.AnvilRecipes.AnvilOutput;
import com.hbm.inventory.recipes.anvil.AnvilRecipes.OverlayType;
import com.hbm.lib.RefStrings;
import com.hbm.util.ItemStackUtil;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class AnvilRecipeHandler extends TemplateRecipeHandler implements ICompatNHNEI {

	@Override
	public ItemStack[] getMachinesForRecipe() {
		return new ItemStack[]{
				new ItemStack(ModBlocks.anvil_iron),
				new ItemStack(ModBlocks.anvil_lead),
				new ItemStack(ModBlocks.anvil_steel),
				new ItemStack(ModBlocks.anvil_desh),
				new ItemStack(ModBlocks.anvil_saturnite),
				new ItemStack(ModBlocks.anvil_ferrouranium),
				new ItemStack(ModBlocks.anvil_bismuth_bronze),
				new ItemStack(ModBlocks.anvil_arsenic_bronze),
				new ItemStack(ModBlocks.anvil_schrabidate),
				new ItemStack(ModBlocks.anvil_dnt),
				new ItemStack(ModBlocks.anvil_osmiridium),
				new ItemStack(ModBlocks.anvil_murky)};
	}

	@Override
	public String getRecipeID() {
		return "ntmAnvil";
	}

	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
	public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<RecipeTransferRect>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
	public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<Class<? extends GuiContainer>>();

	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {
		
		List<PositionedStack> input = new ArrayList();
		List<PositionedStack> output = new ArrayList();
		PositionedStack anvil;
		int tier;
		OverlayType shape;

		public RecipeSet(List<Object> in, List<Object> out, int tier) {

			//not the prettiest of solutions but certainly the most pleasant to work with
			int inLine = 1;
			int outLine = 1;
			int inOX = 0;
			int inOY = 0;
			int outOX = 0;
			int outOY = 0;
			int anvX = 0;
			int anvY = 31;
			
			if(in.size() == 1 && out.size() == 1) {
				shape = OverlayType.SMITHING;
				inOX = 48;
				inOY = 24;
				outOX = 102;
				outOY = 24;
				anvX = 75;
			} else if(in.size() == 1 && out.size() > 1) {
				shape = OverlayType.RECYCLING;
				outLine = 6;
				inOX = 12;
				inOY = 24;
				outOX = 48;
				outOY = 6;
				anvX = 30;
			} else if(in.size() > 1 && out.size() == 1) {
				shape = OverlayType.CONSTRUCTION;
				inLine = 6;
				inOX = 12;
				inOY = 6;
				outOX = 138;
				outOY = 24;
				anvX = 120;
			} else {
				shape = OverlayType.NONE;
				inLine = 4;
				outLine = 4;
				inOX = 3;
				inOY = 6;
				outOX = 93;
				outOY = 6;
				anvX = 75;
			}
			
			for(int i = 0; i < in.size(); i++) {
				this.input.add(new PositionedStack(in.get(i), inOX + 18 * (i % inLine), inOY + 18 * (i / inLine)));
			}
			
			for(int i = 0; i < out.size(); i++) {
				this.output.add(new PositionedStack(out.get(i), outOX + 18 * (i % outLine), outOY + 18 * (i / outLine)));
			}
			
			this.anvil = new PositionedStack(NTMAnvil.getAnvilsFromTier(tier), anvX, anvY);
			
			this.tier = tier;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, input);
		}

		@Override
		public PositionedStack getResult() {
			return output.get(0);
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> other = new ArrayList();
			other.addAll(output);
			other.add(anvil);
			return getCycledIngredients(cycleticks / 20, other);
		}
	}

	@Override
	public String getRecipeName() {
		return "Anvil";
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if(outputId.equals("ntmAnvil")) {
			List<AnvilConstructionRecipe> recipes = AnvilRecipes.getConstruction();
			
			for(AnvilConstructionRecipe recipe : recipes) {
				this.addRecipeToList(recipe);
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {

		List<AnvilConstructionRecipe> recipes = AnvilRecipes.getConstruction();
		
		for(AnvilConstructionRecipe recipe : recipes) {
			
			for(AnvilOutput out : recipe.output) {
				if(NEIServerUtils.areStacksSameTypeCrafting(out.stack, result)) {
					this.addRecipeToList(recipe);
					break;
				}
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		
		if(inputId.equals("ntmAnvil")) {
			loadCraftingRecipes("ntmAnvil", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {

		List<AnvilConstructionRecipe> recipes = AnvilRecipes.getConstruction();
		
		for(AnvilConstructionRecipe recipe : recipes) {
			
			outer:
			for(AStack in : recipe.input) {
				
				List<ItemStack> stacks = in.extractForNEI();
				for(ItemStack stack : stacks) {
					if(NEIServerUtils.areStacksSameTypeCrafting(stack, ingredient)) {
						this.addRecipeToList(recipe);
						break outer;
					}
				}
			}
		}
	}
	
	private void addRecipeToList(AnvilConstructionRecipe recipe) {
		
		List<Object> ins = new ArrayList();
		for(AStack input : recipe.input) {
			ins.add(input.extractForNEI());
		}
		
		List<Object> outs = new ArrayList();
		for(AnvilOutput output : recipe.output) {
			
			ItemStack stack = output.stack.copy();
			if(output.chance != 1) {
				ItemStackUtil.addTooltipToStack(stack, EnumChatFormatting.RED + "" + (((int)(output.chance * 1000)) / 10D) + "%");
			}
			
			outs.add(stack);
		}
		
		this.arecipes.add(new RecipeSet(ins, outs, recipe.tierLower));
	}

	@Override
	public void loadTransferRects() {
		
		//hey asshole, stop nulling my fucking lists
		transferRectsGui = new LinkedList<RecipeTransferRect>();
		guiGui = new LinkedList<Class<? extends GuiContainer>>();

		transferRectsGui.add(new RecipeTransferRect(new Rectangle(11, 42, 36, 18), "ntmAnvil"));
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(65, 42, 36, 18), "ntmAnvil"));
		
		guiGui.add(GUIAnvil.class);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_anvil.png";
	}

	@Override
	public void drawBackground(int recipe) {
		super.drawBackground(recipe);
		
		RecipeSet set = (RecipeSet) this.arecipes.get(recipe);
		
		switch(set.shape) {
		case NONE:
			drawTexturedModalRect(2, 5, 5, 87, 72, 54);			//in
			drawTexturedModalRect(92, 5, 5, 87, 72, 54);		//out
			drawTexturedModalRect(74, 14, 131, 96, 18, 36);		//operation
			break;
		case SMITHING:
			drawTexturedModalRect(47, 23, 113, 105, 18, 18);	//in
			drawTexturedModalRect(101, 23, 113, 105, 18, 18);	//out
			drawTexturedModalRect(74, 14, 149, 96, 18, 36);		//operation
			break;
		case CONSTRUCTION:
			drawTexturedModalRect(11, 5, 5, 87, 108, 54);		//in
			drawTexturedModalRect(137, 23, 113, 105, 18, 18);	//out
			drawTexturedModalRect(119, 14, 167, 96, 18, 36);	//operation
			break;
		case RECYCLING:
			drawTexturedModalRect(11, 23, 113, 105, 18, 18);	//in
			drawTexturedModalRect(47, 5, 5, 87, 108, 54);		//out
			drawTexturedModalRect(29, 14, 185, 96, 18, 36);		//operation
			break;
		}
	}
}
