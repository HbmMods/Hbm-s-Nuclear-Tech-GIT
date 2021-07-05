package com.hbm.handler.nei;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.hbm.inventory.MachineRecipes;
import com.hbm.inventory.gui.GUIMachineArcFurnace;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;
// TODO what is this??!!
public class ArcFurnaceRecipeHandler extends TemplateRecipeHandler
{
	public Item[] electrodes = new Item[] { ModItems.arc_electrode, ModItems.arc_electrode_desh };
	public Random rand = new Random();
	public class ArcSet extends TemplateRecipeHandler.CachedRecipe
	{
		PositionedStack input;
		PositionedStack output;
		PositionedStack electrodeSlot1;
		PositionedStack electrodeSlot2;
		PositionedStack electrodeSlot3;

		public ArcSet(ItemStack input, ItemStack output)
		{
			input.stackSize = 1;
			this.input = new PositionedStack(input, 56, 17);
			this.output = new PositionedStack(output, 116, 35);
		}
		
		@Override
		public PositionedStack getResult()
		{
			return output;
		}
		
		@Override
		public List<PositionedStack> getIngredients()
		{
			return getCycledIngredients(cycleticks / 48, Arrays.asList(new PositionedStack[] {input}));
		}
		
		@Override
		public List<PositionedStack> getOtherStacks()
		{
			return getCycledIngredients(cycleticks / 48, Arrays.asList(new PositionedStack[] {electrodeSlot1, electrodeSlot2, electrodeSlot3}));
		}
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results)
	{
		if (outputId.equals("arcsmelting") && getClass() == ArcFurnaceRecipeHandler.class)
		{
			Map<Object, Object> recipes = MachineRecipes.instance().getArcFurnaceRecipes();
			for (Entry<Object, Object> recipe : recipes.entrySet())
				this.arecipes.add(new ArcSet((ItemStack)recipe.getKey(), (ItemStack)recipe.getValue()));
		}
		else
			super.loadCraftingRecipes(outputId, results);
	}
	
	@Override
	public void loadCraftingRecipes(ItemStack result)
	{
		Map<Object, Object> recipes = MachineRecipes.instance().getArcFurnaceRecipes();
		for (Entry<Object, Object> recipe : recipes.entrySet())
			if (NEIServerUtils.areStacksSameType((ItemStack)recipe.getValue(), result))
				this.arecipes.add(new ArcSet((ItemStack)recipe.getKey(), (ItemStack)recipe.getValue()));
	}
	
	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients)
	{
		if (inputId.equals("arcsmelting") && getClass() == ArcFurnaceRecipeHandler.class)
			loadCraftingRecipes("arcsmelting", new Object[0]);
		else
			super.loadUsageRecipes(inputId, ingredients);
	}
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient)
	{
		Map<Object, Object> recipes = MachineRecipes.instance().getArcFurnaceRecipes();
		for (Entry<Object, Object> recipe : recipes.entrySet())
			if (NEIServerUtils.areStacksSameType((ItemStack)recipe.getKey(), ingredient))
				this.arecipes.add(new ArcSet((ItemStack)recipe.getKey(), (ItemStack)recipe.getValue()));
	}
		
	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GUIMachineArcFurnace.class;
	}
	
	public Item getElectrode()
	{
		return electrodes[rand.nextInt(1)];
	}
	
	@Override
	public String getRecipeName()
	{
		return I18nUtil.resolveKey("container.arcFurnace");
	}

	@Override
	public String getGuiTexture()
	{
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_arc.png";
	}

}
