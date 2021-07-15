package com.hbm.inventory;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class AnvilRecipes {

	private static List<AnvilSmithingRecipe> smithingRecipes = new ArrayList();
	private static List<AnvilConstructionRecipe> constructionRecipes = new ArrayList();
	
	public static void register() {
		registerSmithing();
		registerConstruction();
	}
	
	public static void registerSmithing() {
		
		smithingRecipes.add(new AnvilSmithingRecipe(2, new ItemStack(ModItems.plate_steel, 2), new OreDictStack("ingotSteel"), new OreDictStack("ingotSteel")));

		Block[] anvils = new Block[]{ModBlocks.anvil_iron, ModBlocks.anvil_lead};
		
		for(Block anvil : anvils) {
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_bismuth, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_bismuth, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_dnt, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_dineutronium, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_ferrouranium, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_u238, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_meteorite, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_meteorite, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_schrabidate, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_schrabidate, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_starmetal, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_starmetal, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_steel, 1), new ComparableStack(anvil), new OreDictStack("ingotSteel", 10)));
		}
		
		smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModItems.gun_ar15, 1), new ComparableStack(ModItems.gun_thompson), new ComparableStack(ModItems.pipe_lead)));
	}
	
	public static void registerConstruction() {
		constructionRecipes.add(new AnvilConstructionRecipe(
				new OreDictStack("ingotIron"),
				new AnvilOutput(new ItemStack(ModItems.plate_iron))
		).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new OreDictStack("ingotGold"),
				new AnvilOutput(new ItemStack(ModItems.plate_gold))
		).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new OreDictStack("ingotCopper"),
				new AnvilOutput(new ItemStack(ModItems.plate_copper))
		).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new OreDictStack("ingotLead"),
				new AnvilOutput(new ItemStack(ModItems.plate_lead))
		).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new OreDictStack("ingotSteel"),
				new AnvilOutput(new ItemStack(ModItems.plate_steel))
		).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModBlocks.barrel_tcalloy),
				new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModItems.ingot_titanium, 2)),
						new AnvilOutput(new ItemStack(ModItems.ingot_tcalloy, 4)),
						new AnvilOutput(new ItemStack(ModItems.ingot_tcalloy, 1), 0.50F),
						new AnvilOutput(new ItemStack(ModItems.ingot_tcalloy, 1), 0.25F)
				}
		).setTier(3));
		
		for(int i = 0; i < 8; i++)
			constructionRecipes.add(new AnvilConstructionRecipe(
					new OreDictStack("plateCopper"),
					new AnvilOutput(new ItemStack(ModItems.wire_copper, 8))
			).setTier(1));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new OreDictStack("plateGold"),
				new AnvilOutput(new ItemStack(ModItems.wire_gold, 8))
		).setTierRange(1, 4));
	}
	
	public static List<AnvilSmithingRecipe> getSmithing() {
		return smithingRecipes;
	}
	
	public static List<AnvilConstructionRecipe> getConstruction() {
		return constructionRecipes;
	}
	
	public static class AnvilSmithingRecipe {
		
		int tier;
		ItemStack output;
		AStack left;
		AStack right;
		boolean shapeless = false;
		
		public AnvilSmithingRecipe(int tier, ItemStack out, AStack left, AStack right) {
			this.tier = tier;
			this.output = out;
			this.left = left;
			this.right = right;
		}
		
		public AnvilSmithingRecipe makeShapeless() {
			this.shapeless = true;
			return this;
		}
		
		public boolean matches(ItemStack left, ItemStack right) {
			return matchesInt(left, right) != -1;
		}
		
		public int matchesInt(ItemStack left, ItemStack right) {
			
			if(doesStackMatch(left, this.left) && doesStackMatch(right, this.right))
				return 0;
			
			if(shapeless) {
				return doesStackMatch(right, this.left) && doesStackMatch(left, this.right) ? 1 : -1;
			}
			
			return -1;
		}
		
		public boolean doesStackMatch(ItemStack input, AStack recipe) {
			return recipe.matchesRecipe(input);
		}
		
		public ItemStack getOutput(ItemStack left, ItemStack right) {
			return output.copy();
		}
		
		public int amountConsumed(int index, boolean mirrored) {
			
			if(index == 0)
				return mirrored ? right.stacksize : left.stacksize;
			if(index == 1)
				return mirrored ? left.stacksize : right.stacksize;
			
			return 0;
		}
	}
	
	public static class AnvilConstructionRecipe {
		public List<AStack> input = new ArrayList();
		public List<AnvilOutput> output = new ArrayList();
		int tierLower = 0;
		int tierUpper = -1;
		OverlayType overlay = OverlayType.NONE;
		
		public AnvilConstructionRecipe(AStack input, AnvilOutput output) {
			this.input.add(input);
			this.output.add(output);
			this.setOverlay(OverlayType.SMITHING); //preferred overlay for 1:1 conversions is smithing
		}
		
		public AnvilConstructionRecipe(AStack[] input, AnvilOutput output) {
			for(AStack stack : input) this.input.add(stack);
			this.output.add(output);
			this.setOverlay(OverlayType.CONSTRUCTION); //preferred overlay for many:1 conversions is construction
		}
		
		public AnvilConstructionRecipe(AStack input, AnvilOutput[] output) {
			this.input.add(input);
			for(AnvilOutput out : output) this.output.add(out);
			this.setOverlay(OverlayType.RECYCLING); //preferred overlay for 1:many conversions is recycling
		}
		
		public AnvilConstructionRecipe(AStack[] input, AnvilOutput[] output) {
			for(AStack stack : input) this.input.add(stack);
			for(AnvilOutput out : output) this.output.add(out);
			this.setOverlay(OverlayType.NONE); //no preferred overlay for many:many conversions
		}
		
		public AnvilConstructionRecipe setTier(int tier) {
			this.tierLower = tier;
			return this;
		}
		
		public AnvilConstructionRecipe setTierRange(int lower, int upper) {
			this.tierLower = lower;
			this.tierUpper = upper;
			return this;
		}
		
		public boolean isTierValid(int tier) {
			if(this.tierUpper == -1)
				return tier >= this.tierLower;
			
			return tier >= this.tierLower && tier <= this.tierUpper;
		}
		
		public AnvilConstructionRecipe setOverlay(OverlayType overlay) {
			this.overlay = overlay;
			return this;
		}
		
		public OverlayType getOverlay() {
			return this.overlay;
		}
		
		public ItemStack getDisplay() {
			switch(this.overlay) {
			case NONE: return this.output.get(0).stack.copy();
			case CONSTRUCTION: return this.output.get(0).stack.copy();
			case SMITHING: return this.output.get(0).stack.copy();
			case RECYCLING:
				for(AStack stack : this.input) {
					if(stack instanceof ComparableStack)
						return ((ComparableStack)stack).toStack();
				}
				return this.output.get(0).stack.copy();
			default: return new ItemStack(Items.iron_pickaxe);
			}
		}
	}
	
	public static class AnvilOutput {
		public ItemStack stack;
		public float chance;
		
		public AnvilOutput(ItemStack stack) {
			this(stack, 1F);
		}
		
		public AnvilOutput(ItemStack stack, float chance) {
			this.stack = stack;
			this.chance = chance;
		}
	}
	
	public static enum OverlayType {
		NONE,
		CONSTRUCTION,
		RECYCLING,
		SMITHING;
	}
}
