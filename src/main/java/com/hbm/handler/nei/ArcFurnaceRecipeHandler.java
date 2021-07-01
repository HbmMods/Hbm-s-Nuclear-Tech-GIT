package com.hbm.handler.nei;

import java.awt.Container;
import java.util.List;
import java.util.Random;

import com.hbm.inventory.container.ContainerArmorTable;
import com.hbm.inventory.container.ContainerMachineArcFurnace;
import com.hbm.inventory.gui.GUIMachineArcFurnace;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.item.Item;
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
