package com.hbm.handler.nei;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.imc.ICompatNHNEI;
import com.hbm.itempool.ItemPool;
import com.hbm.itempool.ItemPoolsSatellite;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.saveddata.satellites.SatelliteMiner;
import com.hbm.util.ItemStackUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.WeightedRandomChestContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

public class SatelliteHandler extends TemplateRecipeHandler implements ICompatNHNEI {
	@Override
	public ItemStack[] getMachinesForRecipe() {
		return new ItemStack[] {
			new ItemStack(ModBlocks.sat_dock)
		};
	}

	@Override
	public String getRecipeID() {
		return "ntmSatellite";
	}

	@Override
	public String getRecipeName() {
		return "Satellite";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_anvil.png";
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if(outputId.equals("ntmSatellite")) {
			for(Item satelliteItem : new Item[]{ModItems.sat_miner, ModItems.sat_lunar_miner}) {
				String poolName = SatelliteMiner.getCargoForItem(satelliteItem);
				if(poolName == null) {
					continue;
				}
				this.addRecipeToList(satelliteItem, ItemPool.getPool(poolName));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for(Item satelliteItem : new Item[]{ModItems.sat_miner, ModItems.sat_lunar_miner}) {
			String poolName = SatelliteMiner.getCargoForItem(satelliteItem);
			if(poolName == null) {
				continue;
			}
			WeightedRandomChestContent[] pool = ItemPool.getPool(poolName);
			for(WeightedRandomChestContent poolEntry : pool) {
				if(NEIServerUtils.areStacksSameTypeCrafting(poolEntry.theItemId, result)) {
					this.addRecipeToList(satelliteItem, pool);
					break;
				}
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if(inputId.equals("ntmSatellite")) {
			loadCraftingRecipes("ntmSatellite");
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if(ingredient.getItem() == ModItems.sat_miner) {
			this.addRecipeToList(ModItems.sat_miner, ItemPool.getPool(ItemPoolsSatellite.POOL_SAT_MINER));
		} else if(ingredient.getItem() == ModItems.sat_lunar_miner) {
			this.addRecipeToList(ModItems.sat_lunar_miner, ItemPool.getPool(ItemPoolsSatellite.POOL_SAT_LUNAR));
		}
	}


	private void addRecipeToList(Item poolItem, WeightedRandomChestContent[] poolEntries) {
		List<ItemStack> outs = new ArrayList<>();
		int weight = Arrays.stream(poolEntries).mapToInt(poolEntry -> poolEntry.itemWeight).sum();

		for(WeightedRandomChestContent poolEntry : poolEntries) {
			ItemStack stack = poolEntry.theItemId.copy();

			float chance = 100F * poolEntry.itemWeight / weight;
			ItemStackUtil.addTooltipToStack(stack, EnumChatFormatting.RED + "" + ((int)(chance * 10F) / 10F) + "%");

			outs.add(stack);
		}

		this.arecipes.add(new RecipeSet(new ItemStack(poolItem), outs));
	}

	@Override
	public void drawBackground(int recipe) {
		super.drawBackground(recipe);

		drawTexturedModalRect(11, 23, 113, 105, 18, 18);	//in
		drawTexturedModalRect(47, 5, 5, 87, 108, 54);		//out
		drawTexturedModalRect(29, 14, 131, 96, 18, 36);		//operation
	}

	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {
		List<PositionedStack> input = new ArrayList<>();
		List<PositionedStack> output = new ArrayList<>();
		PositionedStack satelliteDock;

		public RecipeSet(Object in, List<ItemStack> out) {
			//not the prettiest of solutions but certainly the most pleasant to work with
			int inLine = 1;
			int outLine = 1;
			int inOX = 0;
			int inOY = 0;
			int outOX = 0;
			int outOY = 0;
			int anvX = 0;
			int anvY = 31;

			outLine = 6;
			inOX = 12;
			inOY = 24;
			outOX = 48;
			outOY = 6;
			anvX = 30;

			this.input.add(new PositionedStack(in, inOX, inOY));

			int overflowCount = out.size() / 18;
			for(int i = 0; i < Math.min(out.size(), 18); i++) {
				ItemStack[] stacks = new ItemStack[overflowCount + 1];
				for(int j = 0; j < overflowCount + 1 && j * 18 + i < out.size(); j++) {
					stacks[j] = out.get(j * 18 + i);
				}
				this.output.add(new PositionedStack(stacks, outOX + 18 * (i % outLine), outOY + 18 * (i / outLine)));
			}

			this.satelliteDock = new PositionedStack(new ItemStack(ModBlocks.sat_dock), anvX, anvY);
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
			ArrayList<PositionedStack> stacks = new ArrayList<>(output);
			stacks.add(satelliteDock);
			return getCycledIngredients(cycleticks / 20, stacks);
		}
	}
}
